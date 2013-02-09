package pp.trendservice;

import java.util.Calendar;

/**
 * @ThreadSafe
 * @author Pavel Polushkin
 */
public enum Period {

    MINUTE {
        @Override
        public long getPeriodStartTime(long time) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(time);
            calendar.set(Calendar.MILLISECOND, 0);
            calendar.set(Calendar.SECOND, 0);
            return calendar.getTimeInMillis();
        }
        @Override
        public long getNextPeriodStartTime(long time) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(time);
            calendar.set(Calendar.MILLISECOND, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.add(Calendar.MINUTE, 1);
            return calendar.getTimeInMillis();
        }
    },
    HOUR {
        @Override
        public long getPeriodStartTime(long time) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(time);
            calendar.set(Calendar.MILLISECOND, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MINUTE, 0);
            return calendar.getTimeInMillis();

        }
        @Override
        public long getNextPeriodStartTime(long time) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(time);
            calendar.set(Calendar.MILLISECOND, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.add(Calendar.HOUR, 1);
            return calendar.getTimeInMillis();
        }
    },
    DAY {
        @Override
        public long getPeriodStartTime(long time) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(time);
            calendar.set(Calendar.MILLISECOND, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            return calendar.getTimeInMillis();

        }
        @Override
        public long getNextPeriodStartTime(long time) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(time);
            calendar.set(Calendar.MILLISECOND, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.add(Calendar.DATE, 1);            
            return calendar.getTimeInMillis();
        }
    };

    public boolean isPeriodStartTime(long time) {
        return getPeriodStartTime(time) == time;
    }

    public abstract long getPeriodStartTime(long time);

    public abstract long getNextPeriodStartTime(long time);

}
