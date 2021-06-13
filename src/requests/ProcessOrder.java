package requests;

public class ProcessOrder extends MarketRequest {
    public ProcessOrder(long classId, int instanceId, int price, String key) {
        super("ProcessOrder", key, Long.toString(classId), Integer.toString(instanceId), Integer.toString(price));
    }
}