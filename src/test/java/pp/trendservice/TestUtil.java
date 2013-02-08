package pp.trendservice;

import java.util.Calendar;

/**
 * @author Pavel Polushkin
 */
public class TestUtil {

    private static Calendar calendar = Calendar.getInstance();

    public static long time(int year, int month, int date, int hourOfDay, int minute, int second) {
        calendar.set(year, month, date, hourOfDay, minute, second);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }

}
