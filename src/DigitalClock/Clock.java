package DigitalClock;

import java.time.LocalTime;

class Clock {
    private final String hour, min, sec;

    Clock() {
        LocalTime time = LocalTime.now();
        hour = String.format("%02d", time.getHour());
        min = String.format("%02d", time.getMinute());
        sec = String.format("%02d", time.getSecond());
    }

    public String getHour() {
        return hour;
    }

    public String getMin() {
        return min;
    }

    public String getSec() {
        return sec;
    }
}