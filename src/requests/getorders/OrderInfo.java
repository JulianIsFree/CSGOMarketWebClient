package requests.getorders;

import org.json.JSONObject;

public class OrderInfo {
    public static String i_classid = "i_classid";
    public static String i_instanceid = "i_instanceid";
    public static String i_market_hash_name = "i_market_hash_name";
    public static String i_market_name = "i_market_name";
    public static String o_price = "o_price";
    public static String o_state = "o_state";

    public final long classId;
    public final int instanceId;
    public final String marketHashName;
    public final String marketName;
    public final int price;
    public final String state;

    public OrderInfo(long classId, int instanceId, String marketHashName, String marketName, int price, String state) {
        this.classId = classId;
        this.instanceId = instanceId;
        this.marketHashName = marketHashName;
        this.marketName = marketName;
        this.price = price;
        this.state = state;
    }

    public static OrderInfo createOrderInfo(JSONObject object) {
        return new OrderInfo(object.getLong(i_classid), object.getInt(i_instanceid), object.getString(i_market_hash_name),
                object.getString(i_market_name), object.getInt(o_price), object.getString(o_state));
    }

    /**
     * We compare only classId and instanceId in order to check if these OrderInfo objects are about
     * same thing on market.
     * @param o, it's clear
     * @return whatever they are equal or not in terms described above
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof  OrderInfo))
            return false;

        OrderInfo orderInfo = (OrderInfo)o;
        return  classId == orderInfo.classId &&
                instanceId == orderInfo.instanceId;
    }
}
