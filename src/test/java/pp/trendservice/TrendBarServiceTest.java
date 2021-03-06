package pp.trendservice;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import pp.trendservice.dao.ITrendBarValueDao;
import pp.trendservice.service.TrendBarService;

import java.math.BigDecimal;
import java.util.Date;

import static org.mockito.Mockito.*;
import static pp.trendservice.TestUtil.time;

/**
 * @author Pavel Polushkin
 */
public class TrendBarServiceTest {

    private static ITrendBarValueDao dao;

    private static Symbol usdEur = Symbol.getInstance("USDEUR");

    private static Symbol jpyEur = Symbol.getInstance("JPYEUR");

    @Before
    public void prepare() {
        dao = mock(ITrendBarValueDao.class);
    }

    @Test
    public void process_quotes_only_inside_one_minute_interval_should_not_cause_dao_save() {
        TrendBarService service = new TrendBarService(dao);

        service.process(new Quote(new Date(time(2013, 1, 6, 0, 1, 1)), usdEur, BigDecimal.valueOf(1)));
        verify(dao, never()).save(Matchers.<TrendBarValue>any());

        service.process(new Quote(new Date(time(2013, 1, 6, 0, 1, 15)), usdEur, BigDecimal.valueOf(1)));
        verify(dao, never()).save(Matchers.<TrendBarValue>any());
    }

    @Test
    public void process_2_quotes_in_different_minutes_interval_should_cause_1_dao_save() {
        TrendBarService service = new TrendBarService(dao);

        service.process(new Quote(new Date(time(2013, 1, 6, 0, 1, 1)), usdEur, BigDecimal.valueOf(1)));
        service.process(new Quote(new Date(time(2013, 1, 6, 0, 2, 0)), usdEur, BigDecimal.valueOf(2)));

        TrendBarValue expected = new TrendBarValue(
                usdEur,
                Period.MINUTE,
                time(2013, 1, 6, 0, 1, 0),
                BigDecimal.valueOf(1),
                BigDecimal.valueOf(1),
                BigDecimal.valueOf(1),
                BigDecimal.valueOf(1));

        verify(dao, times(1)).save(Matchers.<TrendBarValue>any());
        verify(dao, times(1)).save(expected);
    }

    @Test
    public void process_2_quotes_in_different_days_interval_should_cause_3_dao_save() {
        TrendBarService service = new TrendBarService(dao);
        service.process(new Quote(new Date(time(2013, 1, 6, 0, 0, 0)), usdEur, BigDecimal.valueOf(1)));
        service.process(new Quote(new Date(time(2013, 1, 7, 0, 0, 0)), usdEur, BigDecimal.valueOf(0.75)));

        verify(dao, times(3)).save(Matchers.<TrendBarValue>any());
    }

    @Test
    public void process_of_different_symbol_quotes_should_not_cause_dao_save() {
        TrendBarService service = new TrendBarService(dao);
        service.process(new Quote(new Date(time(2013, 1, 1, 0, 0, 0)), usdEur, BigDecimal.valueOf(1)));
        service.process(new Quote(new Date(time(2014, 1, 1, 0, 0, 0)), jpyEur, BigDecimal.valueOf(1)));
        verify(dao, times(0)).save(Matchers.<TrendBarValue>any());
    }

    @Test
    public void calling_get_values_should_cause_dao_getValues_call() {
        TrendBarService service = new TrendBarService(dao);

        Date date = new Date();
        service.getValues(usdEur, Period.MINUTE, date);
        verify(dao, times(1)).getValues(usdEur, Period.MINUTE, date.getTime());
    }

    @Test
    public void calling_get_values_should_cause_dao_getValues_call2() {
        TrendBarService service = new TrendBarService(dao);

        Date date1 = new Date();
        Date date2 = new Date();
        service.getValues(usdEur, Period.MINUTE, date1, date2);
        verify(dao, times(1)).getValues(usdEur, Period.MINUTE, date1.getTime(), date2.getTime());
    }

}
