package pp.trendservice;

import org.junit.Test;

import java.util.Currency;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * @author Pavel Polushkin
 */
public class SymbolTest {

    @Test(expected = IllegalArgumentException.class)
    public void constructorArgTest1() {
        new Symbol(null, Currency.getInstance("USD"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorArgTest2() {
        new Symbol(Currency.getInstance("USD"), null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorArgTest3() {
        new Symbol(Currency.getInstance("USD"), Currency.getInstance("USD"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorArgTest4() {
        new Symbol("WRONG", "USD");
    }

    @Test
    public void equalsTest() {
        Symbol usdeur1 = new Symbol("USD", "EUR");
        Symbol usdeur2 = new Symbol(Currency.getInstance("USD"), Currency.getInstance("EUR"));
        assertEquals(usdeur1, usdeur2);
    }

    @Test
    public void equalsTest2() {
        Symbol usdeur = new Symbol("USD", "EUR");
        Symbol eurusd = new Symbol("EUR", "USD");
        assertFalse(usdeur.equals(eurusd));
    }

    @Test
    public void testShortName() {
        Symbol usdeur = new Symbol("USD", "EUR");
        assertEquals("USDEUR", usdeur.getShortName());
    }

    @Test
    public void testCreateByShortName() {
        Symbol usdeur = new Symbol("USD", "EUR");
        String shortName = usdeur.getShortName();
        assertEquals(usdeur, Symbol.createByShortName(shortName));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateByShortNameWrongArg() {
        Symbol.createByShortName("AAA");
    }

}
