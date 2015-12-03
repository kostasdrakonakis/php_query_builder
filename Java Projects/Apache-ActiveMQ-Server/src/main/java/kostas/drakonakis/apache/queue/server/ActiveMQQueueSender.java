package kostas.drakonakis.apache.queue.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import org.apache.activemq.ActiveMQConnectionFactory;

/**
 *
 * @author Kostas
 */
public class ActiveMQQueueSender {

    private String brokerUrl, username, password, object = "A String is an object.";
    private ConnectionFactory connectionFactory;
    private Connection connection = null;
    private Session session = null;
    private Destination queue;
    private MessageProducer producer;
    private TextMessage message;
    ObjectMessage objectMessage = null;
    private Topic topic;
    private MapMessage mapMessageTopic, mapMessageQueue;

    public ActiveMQQueueSender(final String brokerUrl, String username, String password) {
        this.brokerUrl = brokerUrl;
        this.username = username;
        this.password = password;

    }

    public void sendTextMessage(String queueName, String text) throws Exception {
        try {
            initConnection(queueName);
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
            message = session.createTextMessage(text);
            //send the message to the queue
            producer.send(message);
        } catch (Exception e) {
            System.out.println("Exception while sending message to the queue" + e.getMessage());
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
                if (session != null) {
                    session.close();
                }
            }
        }
    }

    public void sendMapMessage(String queueName) throws Exception {
        try {
            initConnection(queueName);
            mapMessageQueue = session.createMapMessage();
            mapMessageQueue.setIntProperty("Age", 24);
            mapMessageQueue.setStringProperty("Full Name", "Konstantinos Drakonakis");
            mapMessageQueue.setStringProperty("Height", "178cm");

            List<String> dataQueue = new ArrayList<String>();
            dataQueue.add("Company");
            dataQueue.add("Project");
            mapMessageQueue.setObject("data", dataQueue);

            Map<String, Object> specsQueue = new HashMap<String, Object>();
            specsQueue.put("data", dataQueue);
            mapMessageQueue.setObject("specs", specsQueue);
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
            producer.send(mapMessageQueue);
        } catch (Exception e) {
            System.out.println("Exception while sending map message to the queue" + e.getMessage());
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
                if (session != null) {
                    session.close();
                }
            }
        }
    }
    
    public void sendMapMessageTopic(String topicName) throws Exception {
        try {
            initConnectionTopic(topicName);
            mapMessageTopic = session.createMapMessage();
            mapMessageTopic.setIntProperty("Age", 24);
            mapMessageTopic.setStringProperty("Full Name", "Konstantinos Drakonakis");
            mapMessageTopic.setStringProperty("Height", "178cm");

            List<String> data = new ArrayList<String>();
            data.add("Company Topic");
            data.add("Project Topic");
            mapMessageTopic.setObject("data", data);

            Map<String, Object> specs = new HashMap<String, Object>();
            specs.put("data", data);
            mapMessageTopic.setObject("specs", specs);
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
            producer.send(mapMessageTopic);
        } catch (Exception e) {
            System.out.println("Exception while sending map message to the queue" + e.getMessage());
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
                if (session != null) {
                    session.close();
                }
            }
        }
    }

    public void sendObjectMessage(String queueName) throws Exception {
        try {
            initConnection(queueName);
            objectMessage = session.createObjectMessage();
            objectMessage.setObject(object);

            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
            producer.send(objectMessage);
        } catch (Exception e) {
            System.out.println("Exception while sending map message to the queue" + e.getMessage());
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
                if (session != null) {
                    session.close();
                }
            }
        }
    }
    
    public void sendTextMessageTopic(String topicName, String text) throws JMSException{
            try{
                initConnectionTopic(topicName);
            
            message = session.createTextMessage(text);
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
            producer.send(message);
            connection.close();
            }catch(Exception e){
                e.printStackTrace();
            }
    }
    
    public void initConnectionTopic(String topicName) throws JMSException {
            //get connection factory
            connectionFactory = new ActiveMQConnectionFactory(username, password, brokerUrl);
            //create a connection
            connection = connectionFactory.createConnection();
            connection.start();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            //create queue(unless it already exists)
            topic = session.createTopic(topicName);
            //create the producer and set the destination queue
            producer = session.createProducer(topic);
    }
    
    public void initConnection(String queueName) throws JMSException {
            //get connection factory
            connectionFactory = new ActiveMQConnectionFactory(username, password, brokerUrl);
            //create a connection
            connection = connectionFactory.createConnection();
            connection.start();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            //create queue(unless it already exists)
            queue = session.createQueue(queueName);
            //create the producer and set the destination queue
            producer = session.createProducer(queue);
    }

}
