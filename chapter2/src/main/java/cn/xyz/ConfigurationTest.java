package cn.xyz;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.AbstractApplicationContext;

import java.beans.PropertyEditor;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created by fanchengwei on 2018/1/18.
 */
@Configuration
public class ConfigurationTest {
    //ApplicationContext
    //BeanDefinition
    //BeanWrapper
    //PropertyEditor
    @Bean(initMethod = "")
    public Car getCar(){
        return new Car();
    }

    public static void main(String[] args) {
        Locale locale = Locale.getDefault();
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(locale);
        double num = 123857.88;
        System.out.println(numberFormat.format(num));

        Date date = new Date();
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM,locale);
        System.out.println(dateFormat.format(date));

        System.out.println(new GregorianCalendar().getTime());

        AbstractApplicationContext
    }
}
