package pp.trendservice.service;

import pp.trendservice.Period;
import pp.trendservice.Quote;
import pp.trendservice.Symbol;
import pp.trendservice.TrendBarValue;

import java.util.Date;
import java.util.List;

/**
 * @author Pavel Polushkin
 */
public interface ITrendBarService {

    void process(Quote quote);

    List<TrendBarValue> getValues(Symbol symbol, Period period, Date from);

    List<TrendBarValue> getValues(Symbol symbol, Period period, Date from, Date to);

}
