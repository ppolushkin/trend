package pp.trendservice;

import java.math.BigDecimal;

/**
 * @Immutable
 * @author Pavel Polushkin
 */
public class TrendBarValue {

    private final Symbol symbol;

    private final Period period;

    private final long time;

    private final BigDecimal openPrice, minPrice, maxPrice, closePrice;

    public TrendBarValue(Symbol symbol, Period period, long time, BigDecimal openPrice, BigDecimal minPrice, BigDecimal maxPrice, BigDecimal closePrice) {
        validateParameters(time, period, symbol, openPrice, minPrice, maxPrice, closePrice);
        this.time = time;
        this.symbol = symbol;
        this.period = period;
        this.openPrice = openPrice;
        this.closePrice = closePrice;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
    }

    public long getTime() {
        return time;
    }

    public Symbol getSymbol() {
        return symbol;
    }

    public Period getPeriod() {
        return period;
    }

    public BigDecimal getOpenPrice() {
        return openPrice;
    }

    public BigDecimal getClosePrice() {
        return closePrice;
    }

    public BigDecimal getMinPrice() {
        return minPrice;
    }

    public BigDecimal getMaxPrice() {
        return maxPrice;
    }

    private void validateParameters(long time, Period period, Symbol symbol, BigDecimal openPrice, BigDecimal minPrice, BigDecimal maxPrice, BigDecimal closePrice) {
        if (period == null) {
            throw new IllegalArgumentException("period can't be null");
        }
        if (symbol == null) {
            throw new IllegalArgumentException("symbol can't be null");
        }
        if (openPrice == null) {
            throw new IllegalArgumentException("openPrice can't be null");
        }
        if (minPrice == null) {
            throw new IllegalArgumentException("minPrice can't be null");
        }
        if (maxPrice == null) {
            throw new IllegalArgumentException("maxPrice can't be null");
        }
        if (closePrice == null) {
            throw new IllegalArgumentException("closePrice can't be null");
        }

        if(!period.isPeriodStartTime(time)) {
            throw new IllegalArgumentException("date parameter should start period " + period.name());
        }

        if(minPrice.compareTo(maxPrice) > 0) {
            throw new IllegalArgumentException("min price can't be bigger than max price");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TrendBarValue)) return false;

        TrendBarValue that = (TrendBarValue) o;

        if (time != that.time) return false;
        if (!closePrice.equals(that.closePrice)) return false;
        if (!maxPrice.equals(that.maxPrice)) return false;
        if (!minPrice.equals(that.minPrice)) return false;
        if (!openPrice.equals(that.openPrice)) return false;
        if (period != that.period) return false;
        if (!symbol.equals(that.symbol)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (time ^ (time >>> 32));
        result = 31 * result + symbol.hashCode();
        result = 31 * result + period.hashCode();
        result = 31 * result + openPrice.hashCode();
        result = 31 * result + closePrice.hashCode();
        result = 31 * result + minPrice.hashCode();
        result = 31 * result + maxPrice.hashCode();
        return result;
    }
}
