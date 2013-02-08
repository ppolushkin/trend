package pp.trendservice;

import org.junit.BeforeClass;
import org.junit.Test;

import java.math.BigDecimal;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 *
 * @author Pavel Polushkin
 */
public class TrendBarValueTest {

    private static Symbol symbol;

    private static Period period;

    private static long correctTime = 1;

    private static long incorrectTime = 2;

    @BeforeClass
    public static void beforeClass() {
        period = mock(Period.class);
        when(period.isPeriodStartTime(correctTime)).thenReturn(true);
        when(period.isPeriodStartTime(incorrectTime)).thenReturn(false);

        symbol = mock(Symbol.class);
    }

    @Test
    public void testCorrectConstructor() {
        new TrendBarValue(symbol, period, correctTime, BigDecimal.valueOf(0), BigDecimal.valueOf(0), BigDecimal.valueOf(0), BigDecimal.valueOf(0));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullPeriodConstructor() {
        new TrendBarValue(symbol, null, correctTime, BigDecimal.valueOf(0), BigDecimal.valueOf(0), BigDecimal.valueOf(0), BigDecimal.valueOf(0));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullSymbolConstructor() {
        new TrendBarValue(null, period, correctTime, BigDecimal.valueOf(0), BigDecimal.valueOf(0), BigDecimal.valueOf(0), BigDecimal.valueOf(0));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullOpenPriceConstructor() {
        new TrendBarValue(symbol, period, correctTime, null, BigDecimal.valueOf(0), BigDecimal.valueOf(0), BigDecimal.valueOf(0));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullMinPriceConstructor() {
        new TrendBarValue(symbol, period, correctTime, BigDecimal.valueOf(0), null, BigDecimal.valueOf(0), BigDecimal.valueOf(0));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullMaxPriceConstructor() {
        new TrendBarValue(symbol, period, correctTime, BigDecimal.valueOf(0), BigDecimal.valueOf(0), null, BigDecimal.valueOf(0));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullClosePriceConstructor() {
        new TrendBarValue(symbol, period, correctTime, BigDecimal.valueOf(0), BigDecimal.valueOf(0), BigDecimal.valueOf(0), null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIncorrectTime() {
        new TrendBarValue(symbol, period, incorrectTime, BigDecimal.valueOf(0), BigDecimal.valueOf(0), BigDecimal.valueOf(0), BigDecimal.valueOf(0));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMinMaxPriceParameters() {
        new TrendBarValue(symbol, period, correctTime, BigDecimal.valueOf(0), BigDecimal.valueOf(1), BigDecimal.valueOf(0), BigDecimal.valueOf(0));
    }

}
