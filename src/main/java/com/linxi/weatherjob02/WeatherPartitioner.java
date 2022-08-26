package com.linxi.weatherjob02;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

import java.util.Objects;

/**
 * @author: linxi
 * Date: 2022/8/25
 * Time: 20:15
 * Description:  maptask 执行完后进行分区
 */

public class WeatherPartitioner extends Partitioner<Text, IntWritable> {  // 泛型类型为maptask的outkey与outvalue的类型

    @Override
    public int getPartition(Text text, IntWritable intWritable, int numPartitions) {
        int hashcode = Objects.hashCode(text.toString());
        hashcode = Math.abs(hashcode);
        int PartitionNumber = hashcode % numPartitions;
        return PartitionNumber;
    }
}
