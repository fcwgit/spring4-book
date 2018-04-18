package cn.xyz;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;

/**
 * Created by fanchengwei on 2018/1/16.
 */
//管理Bean生命周期的接口
public class Car implements BeanFactoryAware ,BeanNameAware,InitializingBean,DisposableBean {
    private String brand;
    private String color;
    private int maxSpeed;
    private String name;
    private BeanFactory beanFactory;
    private String beanName;

    public void setBrand(String brand) {
        System.out.println("Car调用setBrand设置属性");
        this.brand = brand;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        System.out.println("Car调用setColor设置属性");
        this.color = color;
    }

    public void setMaxSpeed(int maxSpeed) {
        System.out.println("Car调用setMaxSpeed设置属性");
        this.maxSpeed = maxSpeed;
    }

    public int getMaxSpeed() {
        return maxSpeed;
    }

    public void introduce(){
        System.out.println("brand:" + brand + ";color:" + color + ";maxSpeed:" + maxSpeed);
    }

    public Car() {
        System.out.println("Car调用Car()构造函数");
    }

    //BeanFactoryAware接口方法
    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        System.out.println("Car调用BeanFactoryAware.setBeanFactory");
        this.beanFactory = beanFactory;
    }

    //BeanNameAware接口方法
    @Override
    public void setBeanName(String name) {
        System.out.println("Car调用BeanNameAware.setBeanName");
        this.beanName = name;
    }

    //DisposableBean接口方法
    @Override
    public void destroy() throws Exception {
        System.out.println("Car调用DisposableBean.destroy");
    }

    //InitializingBean接口方法
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("Car调用InitializingBean.afterPropertiesSet");

    }

    public void myInit(){
        System.out.println("Car调用init-method所指定的myInit，将maxSpeed设置为240");
        this.maxSpeed = 240;
    }

    public void myDestroy(){
        System.out.println("Car调用destroy-method所指定的myDestroy");
    }


}
