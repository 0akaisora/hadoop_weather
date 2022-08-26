package com.linxi.weatherjob03;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/**
 * 作业主类
 */
public class Weather03Job {

    public static void main(String[] args)
            throws IOException, InterruptedException, ClassNotFoundException {
        // 加载配置文件
        Configuration conf = new Configuration(true);
        // 本地模式运行（如果上传至服务器执行，则需要注释该行代码）
        conf.set("mapreduce.framework.name", "local");
        // 创建作业
        Job job = Job.getInstance(conf);
        // 设置作业主类
        job.setJarByClass(Weather03Job.class);
        // 设置作业名称
        job.setJobName("yjx-weather03-" + System.currentTimeMillis());
        // 设置 Reduce 的数量
        job.setNumReduceTasks(2);
        // 设置切片大小
        // FileInputFormat.setMinInputSplitSize(job, 256 * 1024 * 1024);
        // FileInputFormat.setMaxInputSplitSize(job, 64 * 1024 * 1024);
        // 设置分区器和分组器
        job.setPartitionerClass(WeatherPartitioner03.class);
        job.setGroupingComparatorClass(WeatherGroupingComparator03.class);
        // 设置数据的输入路径（需要计算的数据从哪里读）
        FileInputFormat.setInputPaths(job, new Path("/yjx/weather.csv"));
        // 设置数据的输出路径（计算后的数据输出到哪里）
        FileOutputFormat.setOutputPath(job, new Path("/yjx/result/" + job.getJobName()));
        // 设置 Map 的输出的 Key 和 Value 的类型
        job.setMapOutputKeyClass(Weather.class);
        job.setMapOutputValueClass(IntWritable.class);
        // 设置 Map 和 Reduce 的处理类
        job.setMapperClass(Weather03Mapper.class);
        job.setReducerClass(WeatherMap03Reducer.class);
        // 将作业提交到集群并等待完成
        job.waitForCompletion(true);
    }

}
