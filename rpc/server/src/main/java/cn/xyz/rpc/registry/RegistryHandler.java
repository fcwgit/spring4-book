package cn.xyz.rpc.registry;

import cn.xyz.rpc.core.msg.InvokerMsg;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 处理整个注册中心的业务逻辑
 * Created by fanchengwei on 2018/1/23.
 */
public class RegistryHandler extends ChannelInboundHandlerAdapter {
    //在注册中心注册的服务需要一个容器存放
    private ConcurrentHashMap<String,Object> registryMap = new ConcurrentHashMap<String,Object>();
    private List<String> classCache = new ArrayList<String>();

    public RegistryHandler(){
        scanClass("cn.xyz.rpc.provider");
        doRegistry();
    }

    private void scanClass(String packageName){
        URL url = this.getClass().getClassLoader().getResource(packageName.replaceAll("\\.","/"));
        File dir = new File(url.getFile());
        for (File file : dir.listFiles()){
            if (file.isDirectory()){
                scanClass(packageName + file.getName());
            }else {
                String className = packageName + "." + file.getName().replace(".class","");
                classCache.add(className);
            }
        }
    }

    /**
     * 把扫描到的class实例化，放到map中，这就是注册过程
     * 注册的服务名称，就叫接口名字
     */
    private void doRegistry(){
        if (classCache.size() == 0){
            return;
        }
        for (String className:classCache){
            try {
                Class<?> clazz = Class.forName(className);
                Class<?> interfaces = clazz.getInterfaces()[0];
                registryMap.put(interfaces.getName(),clazz.newInstance());
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Object result = new Object();
        System.out.println("111111111");

        InvokerMsg request = (InvokerMsg)msg;
        if (registryMap.containsKey(request.getClassName())){
            Object clazz = registryMap.get(request.getClassName());
            Method method = clazz.getClass().getMethod(request.getMethodName(),request.getParames());
            result = method.invoke(clazz,request.getValues());
        }
        ctx.writeAndFlush(result);
        ctx.close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
