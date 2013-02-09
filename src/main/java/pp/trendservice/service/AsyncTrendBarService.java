package pp.trendservice.service;

import pp.trendservice.*;
import pp.trendservice.dao.ITrendBarValueDao;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * @ThreadSafe
 * @author Pavel Polushkin
 */
public class AsyncTrendBarService implements ITrendBarService {

    private final ExecutorService executor;

    private final Map<String, TransientTrendBarValue> transientValues;

    private final ITrendBarValueDao dao;

    public AsyncTrendBarService(ITrendBarValueDao dao, int queueSize) {
        this.dao = dao;
        transientValues = new ConcurrentHashMap<String, TransientTrendBarValue>();
        executor = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(queueSize));
    }

    public void process(Quote quote) {
        executor.execute(new ProcessQuoteTask(quote));
    }

    private class ProcessQuoteTask implements Runnable {

        private final Quote quote;

        private ProcessQuoteTask(Quote quote) {
            this.quote = quote;
        }

        public void run() {
            Symbol symbol = quote.getSymbol();

            System.out.println("Processing quote " + quote.getTime() + " by " + Thread.currentThread().getName());

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
        return dao.getValues(symbol, period, fromDate.getTime());
    }

    public List<TrendBarValue> getValues(Symbol symbol, Period period, Date fromDate, Date toDate) {
        return dao.getValues(symbol, period, fromDate.getTime(), toDate.getTime());
    }

    public void stop() {
        executor.shutdown();
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
