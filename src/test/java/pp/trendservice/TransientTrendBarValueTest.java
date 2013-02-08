package pp.trendservice;

import org.junit.BeforeClass;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Pavel Polushkin
 */
public class TransientTrendBarValueTest {

    private static Quote quote;
    private static Symbol quoteSymbol;
    private static Period period;
    private static long quoteTime = 1;
    private static long periodStartTime = 2;
    private static long nextPeriodStartTime = 3;
    private static BigDecimal quotePrice = BigDecimal.valueOf(1);

    @BeforeClass
    public static void beforeClass() {
        quote = mock(Quote.class);
        quoteSymbol = mock(Symbol.class);
        period = mock(Period.class);

        when(quote.getPrice()).thenReturn(quotePrice);
        when(quote.getSymbol()).thenReturn(quoteSymbol);
        when(quote.getTime()).thenReturn(quoteTime);

        when(period.getPeriodStartTime(quoteTime)).thenReturn(periodStartTime);
        when(period.getNextPeriodStartTime(quoteTime)).thenReturn(nextPeriodStartTime);
        when(period.isPeriodStartTime(periodStartTime)).thenReturn(true);
    }

    @Test
    public void testConstructor() {
        TransientTrendBarValue value = new TransientTrendBarValue(quote, period);
        assertEquals(quoteSymbol, value.getSymbol());
        assertEquals(periodStartTime, value.getStartTime());
        assertEquals(nextPeriodStartTime, value.getNextPeriodTime());
        assertEquals(quotePrice, value.getOpenPrice());
        assertEquals(quotePrice, value.getMinPrice());
        assertEquals(quotePrice, value.getMaxPrice());
        assertEquals(quotePrice, value.getClosePrice());
    }

    @Test
    public void testUpdatePrices() {
        TransientTrendBarValue value = new TransientTrendBarValue(quote, period);
        value.updatePrices(BigDecimal.valueOf(2));
        value.updatePrices(BigDecimal.valueOf(0.5));
        assertEquals(quotePrice, value.getOpenPrice());
        assertEquals(BigDecimal.valueOf(0.5), value.getMinPrice());
        assertEquals(BigDecimal.valueOf(2), value.getMaxPrice());
        assertEquals(BigDecimal.valueOf(0.5), value.getClosePrice());
    }

    @Test
    public void testToTrendBarValue() {
        TransientTrendBarValue transientValue = new TransientTrendBarValue(quote, period);
        TrendBarValue value = transientValue.toTrendBarValue();
        assertEquals(quoteSymbol, value.getSymbol());
        assertEquals(periodStartTime, value.getTime());
        assertEquals(quotePrice, value.getOpenPrice());
        assertEquals(quotePrice, value.getMinPrice());
        assertEquals(quotePrice, value.getMaxPrice());
        assertEquals(quotePrice, value.getClosePrice());
    }

}
