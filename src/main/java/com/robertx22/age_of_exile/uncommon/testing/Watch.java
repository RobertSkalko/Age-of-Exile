package com.robertx22.age_of_exile.uncommon.testing;

import org.apache.commons.lang3.time.StopWatch;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class Watch {

    public StopWatch stop = new StopWatch();
    public int min = 0;
    public TimeUnit unit = TimeUnit.MICROSECONDS;
    public Long time;

    public Watch() {
        stop.start();
    }

    public Watch min(int min) {
        this.min = min;
        return this;
    }

    public Watch(TimeUnit unit, int min) {

        this.min = min;
        this.unit = unit;

        stop = new StopWatch();
        stop.start();

    }

    public String getPrint() {

        if (!stop.isStopped()) {
            stop.stop();
        }
        Long time = stop.getTime(unit);
        if (time > min) {
            return "Action took: " + time + " " + unit.name()
                .toLowerCase(Locale.ROOT);

        }
        return "";

    }

    public void print(String str) {

        stop.stop();
        time = stop.getTime(unit);
        if (time >= min) {
            System.out.println(str + "Action took: " + time + " " + unit.name()
                .toLowerCase(Locale.ROOT));

        }
    }

    public void print() {

        stop.stop();
        time = stop.getTime(unit);
        if (time >= min) {
            System.out.println("Action took: " + time + " " + unit.name()
                .toLowerCase(Locale.ROOT));

        }
    }

}
