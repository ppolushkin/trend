package pp.trendservice;

import java.math.BigDecimal;

/**
 * @author Pavel Polushkin
 */
public class TransientTrendBarValue {

    private final Symbol symbol;

    private final Period period;

    private final long startTime, nextPeriodTime;

    private final BigDecimal openPrice;
    
    private BigDecimal minPrice, maxPrice, closePrice;

    public TransientTrendBarValue(Quote quote, Period period) {
        this.period = period;
        this.symbol = quote.getSymbol();
        this.startTime = period.getPeriodStartTime(quote.getTime());
        this.nextPeriodTime = period.getNextPeriodStartTime(quote.getTime());
        this.openPrice = quote.getPrice();
        this.minPrice = openPrice;
        this.maxPrice = openPrice;
        this.closePrice = openPrice;
    }

    public void updatePrices(BigDecimal price) {
        if(minPrice.compareTo(price) > 0) {
            minPrice = price;
        }

        if(maxPrice.compareTo(price) < 0) {
            maxPrice = price;
        }
        closePrice = price;
    }

    public long getNextPeriodTime() {
        return nextPeriodTime;
    }

    public Symbol getSymbol() {
        return symbol;
    }

    public Period getPeriod() {
        return period;
    }

    public long getStartTime() {
        return startTime;
    }

    public BigDecimal getOpenPrice() {
        return openPrice;
    }

    public BigDecimal getMinPrice() {
        return minPrice;
    }

    public BigDecimal getMaxPrice() {
        return maxPrice;
    }

    public BigDecimal getClosePrice() {
        return closePrice;
    }

    public TrendBarValue toTrendBarValue() {
        return new TrendBarValue(symbol, period, startTime, openPrice, minPrice, maxPrice, closePrice);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TransientTrendBarValue)) return false;

        TransientTrendBarValue that = (TransientTrendBarValue) o;

        if (nextPeriodTime != that.nextPeriodTime) return false;
        if (startTime != that.startTime) return false;
        if (closePrice != null ? !closePrice.equals(that.closePrice) : that.closePrice != null) return false;
        if (maxPrice != null ? !maxPrice.equals(that.maxPrice) : that.maxPrice != null) return false;
        if (minPrice != null ? !minPrice.equals(that.minPrice) : that.minPrice != null) return false;
        if (openPrice != null ? !openPrice.equals(that.openPrice) : that.openPrice != null) return false;
        if (period != that.period) return false;
        if (symbol != null ? !symbol.equals(that.symbol) : that.symbol != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = symbol != null ? symbol.hashCode() : 0;
        result = 31 * result + (period != null ? period.hashCode() : 0);
        result = 31 * result + (int) (startTime ^ (startTime >>> 32));
        result = 31 * result + (int) (nextPeriodTime ^ (nextPeriodTime >>> 32));
        result = 31 * result + (openPrice != null ? openPrice.hashCode() : 0);
        result = 31 * result + (minPrice != null ? minPrice.hashCode() : 0);
        result = 31 * result + (maxPrice != null ? maxPrice.hashCode() : 0);
        result = 31 * result + (closePrice != null ? closePrice.hashCode() : 0);
        return result;
    }
}
