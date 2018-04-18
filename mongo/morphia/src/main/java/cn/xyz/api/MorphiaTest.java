package cn.xyz.api;

import com.mongodb.MongoClient;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Query;

/**
 * Created by fanchengwei on 2018/2/2.
 */
public class MorphiaTest {
    public static void main(String[] args) {
        Morphia morphia = new Morphia();
        Datastore datastore = morphia.createDatastore(new MongoClient("127.0.0.1",27017),"foobar");
        //Hotel hotel = new Hotel();
        //hotel.setHotelName("tom");
        //hotel.setHotelAddress("科技中心");
        //
        //Key<Hotel> key = datastore.save(hotel);
        //System.out.println(key.toString());

        Query<Hotel> query = datastore.createQuery(Hotel.class);
        System.out.println(query.get());
    }
}
