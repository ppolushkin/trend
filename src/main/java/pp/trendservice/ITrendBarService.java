package pp.trendservice;

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
