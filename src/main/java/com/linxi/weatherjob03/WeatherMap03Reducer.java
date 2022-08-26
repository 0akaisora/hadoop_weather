package com.linxi.weatherjob03;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: linxi
 * Date: 2022/8/26
 * Time: 17:04
 * Description:
 */
public class WeatherMap03Reducer extends Reducer <Weather, IntWritable, Text, NullWritable>{
    @Override
    protected void reduce(Weather key, Iterable<IntWritable> values, Reducer<Weather, IntWritable, Text, NullWritable>.Context context) throws IOException, InterruptedException {
        // 只需要把温度最高的三天过滤出来
        // 初始化天气容器存储三天的数据
        Map<String, String> weatherMap = new HashMap<>();
        for (IntWritable value : values) {
            // 拼接 outKey
            StringBuffer outKey = new StringBuffer();
            outKey.append(key.getProvince())
                    .append(key.getCity())
                    .append(DateTimeFormatter.ofPattern("yyyyMMdd").format(key.getReportTime()));
            // 判断集合中是否存在该数据
            if (weatherMap.containsKey(outKey.toString())) {
                continue;
            }
            System.out.println(outKey.toString() + " 温度[" + value.get() + "]");
            weatherMap.put(outKey.toString(), "温度[" + value.get() + "]");
            // 判断集合的长度是否大于 3
            if (weatherMap.size() == 3) {
                break;
            }
        }
        // 写出数据
        for (Map.Entry<String, String> entry : weatherMap.entrySet()) {
            context.write(new Text(entry.getKey() + entry.getValue()), NullWritable.get());
        }
    }
}
