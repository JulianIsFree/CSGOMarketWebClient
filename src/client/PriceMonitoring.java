package client;

import requests.getorders.OrderInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class PriceMonitoring {
    //TODO: write code
    private class TimeWithPrice {
        final long time;
        final int price;
        TimeWithPrice(long time, int price) {
            this.time = time;
            this.price = price;
        }
    }

    private final long startingTime;

    private List<TimeWithPrice> orderInfos;
    private Stack<Integer> priceStory;

    public PriceMonitoring(int startPrice) {
        orderInfos = new ArrayList<>();
        priceStory = new Stack<>();
        priceStory.push(startPrice);

        this.startingTime = System.currentTimeMillis();
    }

    public void add(OrderInfo orderInfo) {
//        orderInfos.add(orderInfo);
        update();
        recalculate();
    }

    private void recalculate() {
    }

    private void update() {
    }

    public int suggestedPrice() {
        return 0;
    }
}
