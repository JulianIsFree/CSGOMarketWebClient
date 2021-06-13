package client;

import requests.ProcessOrder;
import requests.RemoveOrder;
import requests.getorders.GetOrders;
import requests.getorders.GetOrdersResponse;
import requests.getorders.OrderInfo;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

public class AutoBuyer implements Runnable {
    private final long classId;
    private final int instanceId;
    private final int price;
    private final int requestFrequency;
    private final String key;

    private AtomicBoolean isWork;
    /**
     * Creating task to check order's status and applying new order every time order is gone
     * @param classId it's clear
     * @param instanceId it' clear
     * @param requestFrequency in milliseconds, can't be less than 200 milliseconds due to
     *                         API restrictions
     */
    public AutoBuyer(long classId, int instanceId, int price, String key, int requestFrequency) {
        assert (requestFrequency >= 200);

        this.classId = classId;
        this.instanceId = instanceId;
        this.price = price;
        this.key = key;
        this.requestFrequency = requestFrequency;

        this.isWork = new AtomicBoolean(true);
    }


    @Override
    public void run() {
        RemoveOrder removeOrder = new RemoveOrder(classId, instanceId, key);
        try {
            removeOrder.sendRequest();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        GetOrders getOrders = new GetOrders(key);
        ProcessOrder processOrder = new ProcessOrder(classId, instanceId, price, key);
        final OrderInfo targetThing = new OrderInfo(classId, instanceId, "", "", price, "");
        while (isWork.get()) {
            try {
                GetOrdersResponse ordersResponse = GetOrdersResponse.createResponseFromJSON(getOrders.sendRequest());
                boolean contains = ordersResponse.orders.contains(targetThing);

                if (!contains) {
                    processOrder.sendRequest();
                    System.out.println("Sending new order request...");
                    System.out.println("classId: " + classId + ", instanceId: " + instanceId + ", price: " + price);
                    System.out.println();
                }

                Thread.sleep(requestFrequency);
            } catch (IOException e) {
                //If error 502
                System.out.println("Error 502 occurred: bad gateway. Problem with server response");
            } catch (Exception e) {
                e.printStackTrace();
                stop();
            }
        }
    }

    public void stop() {
        isWork.set(false);
    }

    public boolean isWork() {
        return isWork.get();
    }

    /*
    Testing one iteration from run
     */
    public static void main(String[] args) {
        long classId = 2727227113L;
        int instanceId = 0;
        String key = "MqmuvwQpl1zcjqNdQyOtx53DDa7PcjI";
        int price = 1800;

        RemoveOrder removeOrder = new RemoveOrder(classId, instanceId, key);
        ProcessOrder trashThing = new ProcessOrder(4418618853L, 0, 1, key);
        try {
            removeOrder.sendRequest();
            trashThing.sendRequest();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        GetOrders getOrders = new GetOrders(key);
        ProcessOrder processOrder = new ProcessOrder(classId, instanceId, price, key);
        final OrderInfo targetThing = new OrderInfo(classId, instanceId, "", "", price, "");
        try {
            GetOrdersResponse ordersResponse = GetOrdersResponse.createResponseFromJSON(getOrders.sendRequest());
            boolean contains = ordersResponse.orders.contains(targetThing);

            if (!contains) {
                processOrder.sendRequest();
                System.out.println("Sending new order request...");
                System.out.println("classId: " + classId + ", instanceId: " + instanceId + ", price: " + price);
                System.out.println();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
