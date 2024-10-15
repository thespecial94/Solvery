package ru.magomed.solverycourse.utils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class DateUtils {
    public static int getMinutesDiffDates(LocalDateTime localDateTime1, LocalDateTime localDateTime2) {
        Date date1 = Date.from(localDateTime1.atZone(ZoneId.systemDefault()).toInstant());
        Date date2 = Date.from(localDateTime2.atZone(ZoneId.systemDefault()).toInstant());
        long diff = date1.getTime() - date2.getTime();
        return Math.abs((int)TimeUnit.MINUTES.convert(diff, TimeUnit.MILLISECONDS));
    }
}
