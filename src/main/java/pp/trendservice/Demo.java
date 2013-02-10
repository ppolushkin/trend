package pp.trendservice;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import pp.trendservice.service.AsyncTrendBarService;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Demo application. Note that you can use TrendBarService implementation almost same way as AsyncTrendBarService.
 */
public class Demo {

    private static ApplicationContext context = new ClassPathXmlApplicationContext("spring-config.xml");

    private static long ms = System.currentTimeMillis();
    private static Date minuteAgo = new Date(ms - 60000);
    private static Date now = new Date(ms);

    public static void main(String[] args) {
        //ITrendBarService service  = (TrendBarService) context.getBean("trend_service");
        AsyncTrendBarService service = (AsyncTrendBarService) context.getBean("async_trend_service");

        Symbol eurUsd = Symbol.getInstance("EURUSD");
        Quote quote1 = new Quote(minuteAgo, eurUsd, BigDecimal.valueOf(1.25));
        Quote quote2 = new Quote(now, eurUsd, BigDecimal.valueOf(1));

        service.process(quote1);
        service.process(quote2);

        List<TrendBarValue> values;
        do {
            values = service.getValues(eurUsd, Period.MINUTE, new Date(ms - 120000));
            Thread.yield();
        } while (values.isEmpty());

        for (TrendBarValue v : values) {
            System.out.println(v.getSymbol().getShortName());
            System.out.println(v.getPeriod().name());
            System.out.println(new Date(v.getTime()));
            System.out.println("Open price: " + v.getOpenPrice());
            System.out.println("Min price: " + v.getMinPrice());
            System.out.println("Max price: " + v.getMaxPrice());
            System.out.println("Close price: " + v.getClosePrice());
        }

        service.stop();
    }
}
