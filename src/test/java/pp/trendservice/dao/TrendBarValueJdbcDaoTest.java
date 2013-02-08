package pp.trendservice.dao;

import org.junit.BeforeClass;
import org.junit.Test;
import pp.trendservice.Period;
import pp.trendservice.Symbol;
import pp.trendservice.TrendBarValue;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static pp.trendservice.TestUtil.time;

/**
 * @author Pavel Polushkin
 */
public class TrendBarValueJdbcDaoTest {

    private static Symbol usdEur = new Symbol("USD", "EUR");

    private static Symbol jpyEur = new Symbol("JPY", "EUR");

    private static ITrendBarValueDao dao;

    private static TrendBarValue v1;

    private static long ts1 = time(2013, 1, 1, 0, 0, 0);

    private static TrendBarValue v2;

    private static long ts2 = time(2013, 1, 1, 0, 1, 0);

    @BeforeClass
    public static void beforeClass() {
        dao = new TrendBarValueJdbcDao("org.h2.Driver", "jdbc:h2:mem:trend_db;DB_CLOSE_DELAY=-1;MODE=Oracle", "sa", "", true);

        v1 = new TrendBarValue(usdEur,
                Period.MINUTE,
                ts1,
                BigDecimal.valueOf(0.75),
                BigDecimal.valueOf(0.5),
                BigDecimal.valueOf(1.25),
                BigDecimal.valueOf(1));
        dao.save(v1);

        v2 = new TrendBarValue(usdEur,
                Period.MINUTE,
                ts2,
                BigDecimal.valueOf(1.25),
                BigDecimal.valueOf(0.75),
                BigDecimal.valueOf(2),
                BigDecimal.valueOf(1));
        dao.save(v2);

    }

    @Test
    public void testSuccessGetValues() {
        List<TrendBarValue> list = dao.getValues(usdEur, Period.MINUTE, ts1, ts2);
        assertEquals(2, list.size());
        assertEquals(v1, list.get(0));
        assertEquals(v2, list.get(1));

        list = dao.getValues(usdEur, Period.MINUTE, ts1);
        assertEquals(2, list.size());
        assertEquals(v1, list.get(0));
        assertEquals(v2, list.get(1));
    }

    @Test
    public void testSuccessGetValues2() {
        List<TrendBarValue> list = dao.getValues(usdEur, Period.MINUTE, ts1, ts1 + 1);
        assertEquals(1, list.size());
        assertEquals(v1, list.get(0));

        list = dao.getValues(usdEur, Period.MINUTE, ts1 + 1);
        assertEquals(1, list.size());
        assertEquals(v2, list.get(0));
    }

    @Test
    public void testWrongSymbol() {
        List<TrendBarValue> list = dao.getValues(jpyEur, Period.MINUTE, ts1, ts2);
        assertEquals(0, list.size());

        list = dao.getValues(jpyEur, Period.MINUTE, ts1);
        assertEquals(0, list.size());
    }

    @Test
    public void testWrongPeriod() {
        List<TrendBarValue> list = dao.getValues(usdEur, Period.HOUR, ts1, ts2);
        assertEquals(0, list.size());

        list = dao.getValues(usdEur, Period.HOUR, ts1);
        assertEquals(0, list.size());
    }

    @Test
    public void testWrongInterval() {
        List<TrendBarValue> list = dao.getValues(usdEur, Period.HOUR, ts1 - 5, ts1 - 4);
        assertEquals(0, list.size());

        list = dao.getValues(usdEur, Period.HOUR, ts2 + 1);
        assertEquals(0, list.size());
    }


}
