package kostas.drakonakis.apache.queue.client;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.TextMessage;
import org.apache.activemq.ActiveMQConnectionFactory;

public class ActiveMQQueueReceiver {

    private String brokerUrl, username, password;
    private ConnectionFactory connectionFactory;
    private Connection connection = null;
    private Session session = null;
    private Destination queue;
    private MessageConsumer consumer;
    private TextMessage textMessage;
    private MapMessage messageMap, messageMapQueue;
    private ObjectMessage objectMessageQueue;

    public ActiveMQQueueReceiver(final String brokerUrl, String username, String password) {
        this.brokerUrl = brokerUrl;
        this.username = username;
        this.password = password;

    }

    public void startReceivingTextMessage(String queueName) throws Exception {
        try {
            //get connection factory
            connectionFactory = new ActiveMQConnectionFactory(username, password, brokerUrl);
            //create a connection
            connection = connectionFactory.createConnection();
            connection.start();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            //create queue(unless it already exists)
            queue = session.createQueue(queueName);
            consumer = session.createConsumer(queue);

            MessageListener listener = new MessageListener() {
                @Override
                public void onMessage(Message msg) {
                    if (msg instanceof TextMessage) {

                        try {
                            textMessage = (TextMessage) msg;
                            System.out.println("Message Received and is : " + textMessage.getText());
                        } catch (JMSException ex) {
                            System.out.println("Exception caught during message receival: " + ex.getMessage());
                        }
                    }
                }
            };

            consumer.setMessageListener(listener);

            System.in.read();
            System.in.read();
            consumer.close();
            connection.close();
            session.close();
        } catch (Exception e) {
            System.out.println("Exception while sending message to the queue" + e.getMessage());
            throw e;
        }
    }

    public void startReceivingTextMessageTopic(String topicName) throws Exception {
        try {
            //get connection factory
            connectionFactory = new ActiveMQConnectionFactory(username, password, brokerUrl);
            //create a connection
            connection = connectionFactory.createConnection();
            connection.start();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            //create queue(unless it already exists)
            queue = session.createTopic(topicName);
            consumer = session.createConsumer(queue);

            MessageListener listener = new MessageListener() {
                @Override
                public void onMessage(Message msg) {
                    if (msg instanceof TextMessage) {

                        try {
                            textMessage = (TextMessage) msg;
                            System.out.println("Message Received and is : " + textMessage.getText());
                        } catch (JMSException ex) {
                            System.out.println("Exception caught during message receival: " + ex.getMessage());
                        }
                    }
                }
            };

            consumer.setMessageListener(listener);

            System.in.read();
            System.in.read();
            consumer.close();
            connection.close();
            session.close();
        } catch (Exception e) {
            System.out.println("Exception while sending message to the queue" + e.getMessage());
            throw e;
        }
    }
    
    public void startReceivingObjectMessage(String queueName) throws Exception {
        try {
            //get connection factory
            connectionFactory = new ActiveMQConnectionFactory(username, password, brokerUrl);
            //create a connection
            connection = connectionFactory.createConnection();
            connection.start();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            //create queue(unless it already exists)
            queue = session.createQueue(queueName);
            consumer = session.createConsumer(queue);
            consumer = session.createConsumer(queue);
            objectMessageQueue = (ObjectMessage) consumer.receive();
            if (objectMessageQueue instanceof ObjectMessage) {
                ObjectMessage object = objectMessageQueue;
                System.out.println("The contents of ObjectMessage Queue are: " + object.toString());
            }
            System.in.read();
            consumer.close();
            connection.close();
            session.close();
        } catch (Exception e) {
            System.out.println("Exception while sending message to the queue" + e.getMessage());
            throw e;
        }
    }
    
    
    public void startReceivingMapMessageTopic(String topicName) throws Exception {
        try {
            //get connection factory
            connectionFactory = new ActiveMQConnectionFactory(username, password, brokerUrl);
            //create a connection
            connection = connectionFactory.createConnection();
            connection.start();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            //create destination(unless it already exists)
            queue = session.createTopic(topicName);
            consumer = session.createConsumer(queue);
            messageMap = (MapMessage) consumer.receive();
            if (messageMap instanceof MapMessage) {
                MapMessage m = messageMap;
                System.out.println("The contents of MapMessage Topic are: " + "\nProperties: " + "\n"
                        + "\n"
                        + "Age: " + m.getStringProperty("Age")
                        + "\n"
                        + "Full Name: " + m.getStringProperty("Full Name")
                        + "\n"
                        + "Height: " + m.getStringProperty("Height") + ""
                        + "\n"
                        + "\n"
                        + "Company: " + m.getObject("data") + ""
                        + "\n"
                        + "Project: " + m.getObject("specs"));
            }

            System.in.read();
            consumer.close();
            connection.close();
            session.close();
        } catch (Exception e) {
            System.out.println("Exception while sending message to the queue" + e.getMessage());
            throw e;
        }
    }
    
    public void startReceivingMapMessage(String queueName) throws Exception {
        try {
            //get connection factory
            connectionFactory = new ActiveMQConnectionFactory(username, password, brokerUrl);
            //create a connection
            connection = connectionFactory.createConnection();
            connection.start();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            //create destination(unless it already exists)
            queue = session.createQueue(queueName);
            consumer = session.createConsumer(queue);
            messageMapQueue = (MapMessage) consumer.receive();
            if (messageMapQueue instanceof MapMessage) {
                MapMessage my = messageMapQueue;
                System.out.println("The contents of MapMessage Queue are: " + "\nProperties: " + "\n"
                        + "\n"
                        + "Age: " + my.getStringProperty("Age")
                        + "\n"
                        + "Full Name: " + my.getStringProperty("Full Name")
                        + "\n"
                        + "Height: " + my.getStringProperty("Height") + ""
                        + "\n"
                        + "\n"
                        + "Company: " + my.getObject("data") + ""
                        + "\n"
                        + "Project: " + my.getObject("specs"));
            }

            System.in.read();
            consumer.close();
            connection.close();
            session.close();
        } catch (Exception e) {
            System.out.println("Exception while sending message to the queue" + e.getMessage());
            throw e;
        }
    }

}
