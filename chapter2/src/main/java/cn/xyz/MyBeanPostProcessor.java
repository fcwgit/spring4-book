package cn.xyz;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * Created by fanchengwei on 2018/1/16.
 */
public class MyBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if ("car".equals(beanName)){
            Car car = (Car)bean;
            if (car.getColor() == null){
                System.out.println("调用MyBeanPostProcessor.postProcessBeforeInitialization，color为空，设置默认为黑色");
                car.setColor("黑色");
            }
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if ("car".equals(beanName)){
            Car car = (Car)bean;
            if (car.getMaxSpeed() >= 200){
                System.out.println("调用MyBeanPostProcessor.postProcessAfterInitialization，将maxSpeed调整为200");
                car.setMaxSpeed(200);
            }
        }
        return bean;
    }
}
