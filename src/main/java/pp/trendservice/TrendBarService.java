package pp.trendservice;

import pp.trendservice.dao.ITrendBarValueDao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author Pavel Polushkin
 */
public class TrendBarService implements ITrendBarService {

    private ITrendBarValueDao dao;

    private final Map<String, TransientTrendBarValue> transientValues;

    private final Object processLock = new Object();

    private final Object readLock = new Object();

    public TrendBarService(ITrendBarValueDao dao) {
        this.dao = dao;
        transientValues = new HashMap<String, TransientTrendBarValue>();
    }

    public void process(Quote quote) {
        synchronized (processLock) {
            Symbol symbol = quote.getSymbol();
            for (Period period : Period.values()) {
                TransientTrendBarValue transientValue = get(symbol, period);
                if (transientValue == null) {
                    put(symbol, period, new TransientTrendBarValue(quote, period));
                } else {
                    if (quote.getTime() < transientValue.getNextPeriodTime()) {
                        transientValue.updatePrices(quote.getPrice());
                    } else {
                        persist(transientValue.toTrendBarValue());
                        put(symbol, period, new TransientTrendBarValue(quote, period));
                    }
                }
            }
        }
    }

    public List<TrendBarValue> getValues(Symbol symbol, Period period, Date fromDate) {
        synchronized (readLock) {
            return dao.getValues(symbol, period, fromDate.getTime());
        }
    }

    public List<TrendBarValue> getValues(Symbol symbol, Period period, Date fromDate, Date toDate) {
        synchronized (readLock) {
            return dao.getValues(symbol, period, fromDate.getTime(), toDate.getTime());
        }
    }

    private TransientTrendBarValue get(Symbol symbol, Period period) {
        return transientValues.get(key(symbol, period));
    }

    private void put(Symbol symbol, Period period, TransientTrendBarValue state) {
        transientValues.put(key(symbol, period), state);
    }

    private static String key(Symbol symbol, Period period) {
        return symbol.getShortName() + period.name();
    }

    private void persist(TrendBarValue state) {
        dao.save(state);
    }

}
