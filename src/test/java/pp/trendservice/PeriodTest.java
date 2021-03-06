package pp.trendservice;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static pp.trendservice.TestUtil.time;

/**
 * @author Pavel Polushkin
 */
public class PeriodTest {

    @Test
    public void getPeriodStartTime_for_MINUTE() {
        assertEquals(time(2013, 1, 6, 2, 22, 0),
                Period.MINUTE.getPeriodStartTime(time(2013, 1, 6, 2, 22, 1))
        );
    }

    @Test
    public void getNextPeriodStartTime_for_MINUTE() {
        assertEquals(time(2013, 1, 6, 2, 23, 0),
                Period.MINUTE.getNextPeriodStartTime(time(2013, 1, 6, 2, 22, 1))
        );
    }

    @Test
    public void isStartMinutePeriod_for_MINUTE() {
        assertTrue(Period.MINUTE.isPeriodStartTime(time(2013, 1, 6, 2, 22, 0)));
        assertFalse(Period.MINUTE.isPeriodStartTime(time(2013, 1, 6, 2, 22, 4)));
    }

    @Test
    public void getPeriodStartTime_for_HOUR() {
        assertEquals(time(2013, 1, 6, 2, 0, 0),
                Period.HOUR.getPeriodStartTime(time(2013, 1, 6, 2, 22, 1))
        );
    }

    @Test
    public void getNextPeriodStartTime_for_HOUR() {
        assertEquals(time(2013, 1, 6, 3, 0, 0),
                Period.HOUR.getNextPeriodStartTime(time(2013, 1, 6, 2, 22, 1))
        );
    }

    @Test
    public void isStartHourPeriod_for_HOUR() {
        assertTrue(Period.HOUR.isPeriodStartTime(time(2013, 1, 6, 2, 0, 0)));
        assertFalse(Period.HOUR.isPeriodStartTime(time(2013, 1, 6, 2, 22, 4)));
    }

    @Test
    public void getPeriodStartTime_for_DAY() {
        assertEquals(time(2013, 1, 6, 0, 0, 0),
                Period.DAY.getPeriodStartTime(time(2013, 1, 6, 2, 22, 1))
        );
    }

    @Test
    public void getNextPeriodStartTime_for_DAY() {
        assertEquals(time(2013, 1, 7, 0, 0, 0),
                Period.DAY.getNextPeriodStartTime(time(2013, 1, 6, 2, 22, 1))
        );
    }

    @Test
    public void isPeriodStartTime_for_DAY() {
        assertTrue(Period.DAY.isPeriodStartTime(time(2013, 1, 6, 0, 0, 0)));
        assertFalse(Period.DAY.isPeriodStartTime(time(2013, 1, 6, 2, 22, 4)));
    }

}
