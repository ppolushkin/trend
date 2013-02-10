package pp.trendservice;

import java.util.Currency;
import java.util.HashMap;

/**
 * The class is designed so that there's never more than one
 * <code>Symbol</code> instance for any given symbol.
 *
 * @Immutable
 * @author Pavel Polushkin
 */
public class Symbol {

    private final Currency baseCurrency;

    private final Currency quoteCurrency;

    private static final HashMap<String, Symbol> instances = new HashMap<String, Symbol>();

    private Symbol(String baseCurrencyIso4317Code, String quoteCurrencyIso4317Code) {
         this(Currency.getInstance(baseCurrencyIso4317Code), Currency.getInstance(quoteCurrencyIso4317Code));
    }

    private Symbol(Currency baseCurrency, Currency quoteCurrency) {
        if ((baseCurrency == null) || (quoteCurrency == null)) {
            throw new IllegalArgumentException("baseCurrency and quoteCurrency can't be null");
        }
        if(baseCurrency.equals(quoteCurrency)) {
            throw new IllegalArgumentException("baseCurrency and quoteCurrency should be different");
        }
        this.baseCurrency = baseCurrency;
        this.quoteCurrency = quoteCurrency;
    }

    public static Symbol getInstance(String shortName) {
        synchronized (instances) {
            Symbol instance = instances.get(shortName);
            if(instance == null) {
                if(shortName ==null || shortName.trim().length() != 6) {
                    throw new IllegalArgumentException("Short name should contains 6 chars");
                }
                instance = new Symbol(shortName.substring(0,3), shortName.substring(3,6));
                instances.put(shortName, instance);
            }
            return instance;
        }
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

}
