package com.linxi.weatherjob02;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.Iterator;

/**
 * @author: linxi
 * Date: 2022/8/25
 * Time: 19:11
 * Description:
 */
public class Weather02Reducer extends Reducer<Text, IntWritable, Text, Text> {
    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Reducer<Text, IntWritable, Text, Text>.Context context) throws IOException, InterruptedException {

        int max = -100, min = 100;
        Iterator<IntWritable> iterator = values.iterator();
        while(iterator.hasNext()){
            int temp = iterator.next().get();
            max = Math.max(temp, max);
            min = Math.min(temp, min);
        }
        Text outValue = new Text(String.format("最高温度[%s], 最低温度[%s]", max, min));
        context.write(key, outValue);
    }
}
