package com.linxi.weatherjob02;

import org.apache.hadoop.io.RawComparator;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

/**
 * @author: linxi
 * Date: 2022/8/25
 * Time: 20:29
 * Description:
 */
public class WeatherGroupingComparator extends WritableComparator {

    public WeatherGroupingComparator(){
        super(Text.class, true);
    }

    @Override
    public int compare(WritableComparable a, WritableComparable b) {
        Text t1 = (Text) a;
        Text t2 = (Text) b;
        int result = t1.compareTo(t2);
        return result;
    }
}
