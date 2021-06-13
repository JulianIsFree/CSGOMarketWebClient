package requests.getorders;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

public class GetOrdersResponse {

    public final boolean success;
    public final List<OrderInfo> orders;

    public GetOrdersResponse(boolean success, List<OrderInfo> orders) {
        this.success = success;
        this.orders = orders;
    }

    public static GetOrdersResponse createResponseFromJSON(JSONObject result) throws Exception {
        boolean success = result.getBoolean("success");
        if (success) {
            LinkedList<OrderInfo> orders = new LinkedList<>();

            try {
                if (!result.getString("Orders").equals("No orders")) {
                    throw new Exception("Couldn't perform GetOrders request: \"success\" is true, but " +
                            "\"Orders\" is not equals to \"No orders\" and not an array");
                }
            } catch (org.json.JSONException e) {
                JSONArray notParsedOrders = result.getJSONArray("Orders");
                for (int i = 0; i < notParsedOrders.length(); ++i) {
                    JSONObject object = notParsedOrders.getJSONObject(i);
                    orders.add(OrderInfo.createOrderInfo(object));
                }
            }


            return new GetOrdersResponse(true, orders);
        }

        throw new Exception("Couldn't perform GetOrders request: \"success\" is false");
    }
}
