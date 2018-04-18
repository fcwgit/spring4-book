package cn.xyz;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;


/**
 * Created by fanchengwei on 2018/1/16.
 */
public class BeanLifeCycle {
    private static void lifeCycleInBeanFactory(){
        //下面两句装载配置文件并启动容器
        Resource resource = new ClassPathResource("bean-context.xml");
        BeanFactory beanFactory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader((DefaultListableBeanFactory)beanFactory);
        reader.loadBeanDefinitions(resource);

        //向容器中注册MyBeanPostProcessor后处理器
        ((ConfigurableBeanFactory)beanFactory).addBeanPostProcessor(new MyBeanPostProcessor());

        //向容器中注册MyInstantiationAwareBeanPostProcessor后处理器
        ((ConfigurableBeanFactory)beanFactory).addBeanPostProcessor(new MyInstantiationAwareBeanPostProcessor());

        //((ConfigurableBeanFactory)beanFactory).addBeanPostProcessor(new MyBeanFactoryPostProcessor());
        //第一次从容器中获取car，将触发容器实例化该Bean，将引发Bean生命周期方法的调用
        Car car1 = (Car)beanFactory.getBean("car");
        car1.introduce();
        car1.setColor("red");

        //第二次从容器中取car，直接从缓存池中获取
        Car car2 = (Car)beanFactory.getBean("car");

        //查看car1和car2是否指向同一个引用
        System.out.println("car1==car2:" + (car1==car2));




        System.out.println("===============================");
        Bike bike = (Bike)beanFactory.getBean("bike");
        System.out.println(bike);




        //关闭容器
        ((DefaultListableBeanFactory)beanFactory).destroySingletons();
    }

    public static void main(String[] args) {
        lifeCycleInBeanFactory();
    }
}
