package com.linxi.weatherjob03;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author: linxi
 * Date: 2022/8/26
 * Time: 16:32
 * Description:
 */
public class Weather03Mapper extends Mapper<LongWritable, Text, Weather, IntWritable> {

    @Override
    protected void map(LongWritable key, Text value, Context context)
            throws IOException, InterruptedException {
        // 跳过第一行
        if (0 == key.get()) {
            return;
        }
        // 替换特殊字符，并按逗号分隔
        String[] lineValues = value.toString().replaceAll("\"", "").split(",");
        // 过滤垃圾数据
        if (lineValues.length == 11) {
            // 初始化 Weather 对象
            Weather weather = new Weather();
            weather.setId(Integer.parseInt(lineValues[0]));
            weather.setProvince(lineValues[1]);
            weather.setCity(lineValues[2]);
            weather.setAreaCode(Integer.parseInt(lineValues[3]));
            weather.setWea(lineValues[4]);
            weather.setTemperature(Integer.parseInt(lineValues[5]));
            weather.setWindDirection(lineValues[6]);
            weather.setWindPower(lineValues[7]);
            weather.setHumidity(Integer.parseInt(lineValues[8]));
            weather.setReportTime(LocalDateTime.parse(lineValues[9],
                    DateTimeFormatter.ofPattern("d/M/yyyy HH:mm:ss")));
            weather.setCreateTime(LocalDateTime.parse(lineValues[10],
                    DateTimeFormatter.ofPattern("d/M/yyyy HH:mm:ss")));
            // 写出 weather 对象和温度
            context.write(weather, new IntWritable(weather.getTemperature()));
        }
    }

}