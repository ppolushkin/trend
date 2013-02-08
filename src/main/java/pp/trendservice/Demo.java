package pp.trendservice;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class Demo {

    private static ApplicationContext context = new ClassPathXmlApplicationContext("spring-config.xml");

    private static ITrendBarService service  = (TrendBarService) context.getBean("trend_service");

    private static long ms = System.currentTimeMillis();
    private static Date minuteAgo = new Date(ms - 60000);
    private static Date now = new Date(ms);

	public static void main(String[] args) {

        Symbol eurUsd = Symbol.createByShortName("EURUSD");

        Quote quote1 = new Quote(minuteAgo, eurUsd, BigDecimal.valueOf(1.25));
        Quote quote2 = new Quote(now, eurUsd, BigDecimal.valueOf(1));

        service.process(quote1);
        service.process(quote2);

        List<TrendBarValue> values = service.getValues(eurUsd, Period.MINUTE, new Date(ms - 120000));

        for(TrendBarValue v: values) {
            System.out.println(v.getSymbol().getShortName());
            System.out.println(v.getPeriod().name());
            System.out.println(new Date(v.getTime()));
            System.out.println("Open price: " + v.getOpenPrice());
            System.out.println("Min price: " + v.getMinPrice());
            System.out.println("Max price: " + v.getMaxPrice());
            System.out.println("Close price: " + v.getClosePrice());
        }
	}
}