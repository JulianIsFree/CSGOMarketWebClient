package requests;

public class RemoveOrder extends ProcessOrder {
    public RemoveOrder(long classId, int instanceId, String key) {
        super(classId, instanceId, 0, key);
    }
}
