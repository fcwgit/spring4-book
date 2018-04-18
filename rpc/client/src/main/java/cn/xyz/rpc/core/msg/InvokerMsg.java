package cn.xyz.rpc.core.msg;

import java.io.Serializable;

/**
 * Created by fanchengwei on 2018/1/23.
 */
public class InvokerMsg implements Serializable{

    private static final long serialVersionUID = 6107248156149683068L;
    private String className;
    private String methodName;
    private Class<?>[] parames;
    private Object[] values;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Class<?>[] getParames() {
        return parames;
    }

    public void setParames(Class<?>[] parames) {
        this.parames = parames;
    }

    public Object[] getValues() {
        return values;
    }

    public void setValues(Object[] values) {
        this.values = values;
    }
}
