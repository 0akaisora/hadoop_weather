package com.linxi.weatherjob02;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author: linxi
 * Date: 2022/8/25
 * Time: 19:10
 * Description:
 */
public class Weather02Mapper extends Mapper<LongWritable, Text, Text, IntWritable> {
    @Override
    protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, IntWritable>.Context context) throws IOException, InterruptedException {
        String [] strings = value.toString().split(",");

        try {
            if(strings != null && strings.length == 11){
                StringBuffer outKey = new StringBuffer();
                outKey.append(strings[1]);
                outKey.append(strings[2]);
                outKey.append(":");
                Date datetime = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(strings[9]);
                String str_datetime = new SimpleDateFormat("yyyyMMdd").format(datetime);
                outKey.append(new Text(str_datetime));

                Text textOutKey = new Text(outKey.toString());
                IntWritable outValue = new IntWritable(Integer.parseInt(strings[5]));
                context.write(textOutKey, outValue);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
}
