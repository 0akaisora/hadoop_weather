package com.linxi.weatherjob03;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

import java.time.format.DateTimeFormatter;

/**
 * @author: linxi
 * Date: 2022/8/26
 * Time: 17:30
 * Description:
 */
public class WeatherGroupingComparator03 extends WritableComparator {
    public WeatherGroupingComparator03() {
        super(Weather.class, true);
    }
    @Override
    public int compare(WritableComparable a, WritableComparable b) {
        Weather w1 = (Weather) a;
        Weather w2 = (Weather) b;
        // 先比较省
        int result = w1.getProvince().compareTo(w2.getProvince());
        if (result != 0) {
            return result;
        }
        // 再比较区
        result = w1.getCity().compareTo(w2.getCity());
        if (result != 0) {
            return result;
        }
        // 再比较年月
        return DateTimeFormatter.ofPattern("yyyyMM").format(w1.getReportTime())
                .compareTo(DateTimeFormatter.ofPattern("yyyyMM").format(w2.getReportTime()));
    }
}
