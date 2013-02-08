package pp.trendservice;

import java.util.Currency;

/**
 * @author Pavel Polushkin
 */
public class Symbol {

    private final Currency baseCurrency;

    private final Currency quoteCurrency;

    public Symbol(String baseCurrencyIso4317Code, String quoteCurrencyIso4317Code) {
         this(Currency.getInstance(baseCurrencyIso4317Code), Currency.getInstance(quoteCurrencyIso4317Code));
    }

    public Symbol(Currency baseCurrency, Currency quoteCurrency) {
        if ((baseCurrency == null) || (quoteCurrency == null)) {
            throw new IllegalArgumentException("baseCurrency and quoteCurrency can't be null");
        }
        if(baseCurrency.equals(quoteCurrency)) {
            throw new IllegalArgumentException("baseCurrency and quoteCurrency should be different");
        }
        this.baseCurrency = baseCurrency;
        this.quoteCurrency = quoteCurrency;
    }

    public Currency getBaseCurrency() {
        return baseCurrency;
    }

    public Currency getQuoteCurrency() {
        return quoteCurrency;
    }

    public String getShortName() {
        return baseCurrency.getCurrencyCode() + quoteCurrency.getCurrencyCode();
    }

    public static Symbol createByShortName(String shortName) {
        if(shortName.trim().length() != 6) {
            throw new IllegalArgumentException("Short name should contains 6 chars");
        }
        return new Symbol(shortName.substring(0,3), shortName.substring(3,6));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Symbol)) return false;

        Symbol symbol = (Symbol) o;

        if (!baseCurrency.equals(symbol.baseCurrency)) return false;
        if (!quoteCurrency.equals(symbol.quoteCurrency)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = baseCurrency.hashCode();
        result = 31 * result + quoteCurrency.hashCode();
        return result;
    }
}
