import client.AutoBuyer;

public class Main {
    public static void main(String[] args) {
        if (args.length != 4) {
            System.out.println("Enter classId, instanceId, price and key separated by space");
            return;
        }
        long classId = Long.valueOf(args[0]);
        int instanceId = Integer.valueOf(args[1]);
        int price = Integer.valueOf(args[2]);
        String key = args[3];

        AutoBuyer bot = new AutoBuyer(classId, instanceId, price, key, 500);
        new Thread(bot).start();
    }
}
