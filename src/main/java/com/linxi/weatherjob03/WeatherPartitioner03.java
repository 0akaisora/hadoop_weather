package com.linxi.weatherjob03;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Partitioner;

import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * @author: linxi
 * Date: 2022/8/26
 * Time: 17:08
 * Description:
 */
public class WeatherPartitioner03 extends Partitioner<Weather, IntWritable> {
    @Override
    public int getPartition(Weather weather, IntWritable intWritable, int numPartitions) {
        int hashcode = Objects.hash(weather.getProvince(),
                weather.getCity(),
                DateTimeFormatter.ofPattern("yyyyMM").format(weather.getReportTime()));
        return Math.abs(hashcode) % numPartitions;
    }
}
