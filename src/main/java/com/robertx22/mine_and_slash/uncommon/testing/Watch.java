package com.robertx22.mine_and_slash.uncommon.testing;

import org.apache.commons.lang3.time.StopWatch;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class Watch {

    public StopWatch stop = new StopWatch();
    int min = 0;
    public TimeUnit unit = TimeUnit.MICROSECONDS;

    public Watch() {
        stop = new StopWatch();
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
        Long time = stop.getTime(unit);
        if (time > min) {
            System.out.println(str + "Action took: " + time + " " + unit.name()
                .toLowerCase(Locale.ROOT));

        }
    }

    public void print() {

        stop.stop();
        Long time = stop.getTime(unit);
        if (time > min) {
            System.out.println("Action took: " + time + " " + unit.name()
                .toLowerCase(Locale.ROOT));

        }
    }

}
