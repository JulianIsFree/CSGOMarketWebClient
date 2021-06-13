package requests.getorders;

import requests.MarketRequest;

public class GetOrders extends MarketRequest {

    public GetOrders(String key) {
        this("", key);
    }

    public GetOrders(int page, String key) {
        this(Integer.toString(page), key);
    }

    private GetOrders(String page, String key) {
        super("GetOrders", key, page);
    }

    public static void main(String[] args) {
        GetOrders getOrders0 = new GetOrders("hello");
        GetOrders getOrders1 = new GetOrders(1,"hello");
        System.out.println(getOrders0.toString());
        System.out.println(getOrders1.toString());
    }
}
