package pp.trendservice;

import org.junit.Test;

import java.util.Currency;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Pavel Polushkin
 */
public class SymbolTest {

    @Test(expected = IllegalArgumentException.class)
    public void getInstance_with_wrong_argument_should_throw_exception() {
        Symbol.getInstance("wrong");
    }

    @Test(expected = IllegalArgumentException.class)
    public void getInstance_with_wrong_6_char_argument_should_throw_exception() {
        Symbol.getInstance("XXXYYY");
    }
    @Test(expected = IllegalArgumentException.class)
    public void getInstance_with_null_argument_should_throw_exception() {
        Symbol.getInstance(null);
    }

    @Test
    public void getInstance_with_correct_argument_return_same_instance() {
        Symbol instance1 = Symbol.getInstance("USDEUR");
        Symbol instance2 = Symbol.getInstance("USDEUR");
        assertTrue(instance1 == instance2);
    }

}
