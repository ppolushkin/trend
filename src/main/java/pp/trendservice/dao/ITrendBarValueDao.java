package pp.trendservice.dao;

import pp.trendservice.Period;
import pp.trendservice.Symbol;
import pp.trendservice.TrendBarValue;

import java.util.List;

/**
 * @author Pavel Polushkin
 */
public interface ITrendBarValueDao {

    void save(TrendBarValue value);

    List<TrendBarValue> getValues(Symbol symbol, Period period, long fromDate);    

    List<TrendBarValue> getValues(Symbol symbol, Period period, long fromDate, long toDate);

}
