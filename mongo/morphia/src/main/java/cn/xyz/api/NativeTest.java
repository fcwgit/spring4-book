package cn.xyz.api;

import com.mongodb.*;

/**
 * Created by fanchengwei on 2018/2/2.
 */
public class NativeTest {
    public static void main(String[] args) {
        //只是一个配置信息
        Mongo mongo = new Mongo("127.0.0.1",27017);

        //建立连接
        DB db = new DB(mongo,"foobar");
        //获取集合列表名
        //System.out.println(db.getCollectionNames());

        DBCollection collection = db.getCollection("t_member");
        DBCursor cursor = collection.find();
        for (DBObject dbObject : cursor){
            System.out.println(dbObject);
        }
    }
}
