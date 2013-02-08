package pp.trendservice;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Pavel Polushkin
 */
public class Quote {

    private final long time;

    private final Symbol symbol;

    private final BigDecimal price;

    public Quote(Date date, Symbol symbol, BigDecimal price) {
        this.time = date.getTime();
        this.symbol = symbol;
        this.price = price;
    }

    public long getTime() {
        return time;
    }

    public Symbol getSymbol() {
        return symbol;
    }

    public BigDecimal getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Quote)) return false;

        Quote quote = (Quote) o;

        if (time != quote.time) return false;
        if (price != null ? !price.equals(quote.price) : quote.price != null) return false;
        if (symbol != null ? !symbol.equals(quote.symbol) : quote.symbol != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (time ^ (time >>> 32));
        result = 31 * result + (symbol != null ? symbol.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        return result;
    }
}
