package kostas.drakonakis.apache.queue.server;

import java.util.Date;

public class Sender {

    public static void main(String[] args) {
        //connect to the default broker url
        ActiveMQQueueSender sender = new ActiveMQQueueSender("tcp://localhost:61616", "admin", "admin");
        try {
            
            sender.sendTextMessage("Text Message Queue", "Hello from Windows Server with Queue TextMessage " + new Date());
            sender.sendMapMessage("Map Message Queue");
            sender.sendObjectMessage("Object Message Queue");
            sender.sendTextMessageTopic("Text Message Topic", "Hello from Windows Server with Topic TextMessage " + new Date());
            sender.sendMapMessageTopic("Map Message Topic");
        } catch (Exception ex) {
            System.out.println("Exception during" + ex.getMessage());
        }

    }
}
