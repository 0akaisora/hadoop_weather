package com.linxi.weatherjob01;

import com.linxi.weatherjob02.Weather02Mapper;
import com.linxi.weatherjob02.Weather02Reducer;
import com.linxi.weatherjob02.WeatherJob02;
import org.apache.hadoop.conf.Configuration;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/**
 * @author: linxi
 * Date: 2022/8/25
 * Time: 17:26
 * Description:
 */
public class WeatherJob01 {
    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {
        // 获取配置文件
        Configuration configuration = new Configuration(true);
        // 本地模式运行
        configuration.set("mapreduce.framework.name", "local");
        // 创建Job
        Job job = Job.getInstance(configuration);
        // 设置Job的基本信息
        job.setJarByClass(WeatherJob02.class);
        job.setJobName("WeatherJob01" + System.currentTimeMillis());
        job.setNumReduceTasks(2);
        // 设置Job的输入和输出路径
        FileInputFormat.setInputPaths(job, new Path("/yjx/weather.txt"));
        FileOutputFormat.setOutputPath(job, new Path("/yjx/result/" + job.getJobName()));
        // 设置Job的Map和Reduce
        job.setMapperClass(Weather02Mapper.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);

        job.setReducerClass(Weather02Reducer.class);

        // 提交任务
        job.waitForCompletion(true);

    }
}
