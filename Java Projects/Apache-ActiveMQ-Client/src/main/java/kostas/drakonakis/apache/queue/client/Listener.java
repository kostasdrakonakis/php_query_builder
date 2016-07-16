package kostas.drakonakis.apache.queue.client;


public class Listener {
    public static void main(String[] args) {
        ActiveMQQueueReceiver receiver = new ActiveMQQueueReceiver("tcp://localhost:61616", "admin", "admin");
        try {
            receiver.startReceivingTextMessage("Text Message Queue");
            receiver.startReceivingTextMessageTopic("Text Message Topic");
            receiver.startReceivingMapMessageTopic("Map Message Topic");
            receiver.startReceivingMapMessage("Map Message Queue");
            receiver.startReceivingObjectMessage("Object Message Queue");
        } catch (Exception ex) {
            System.out.println("Exception during receival in main class" + ex.getMessage());
        }
    }
}
