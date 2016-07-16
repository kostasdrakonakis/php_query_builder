/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kostas.drakonakis.apache.queue.client;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Matrix
 */
public class ListenerTest {
    
    public ListenerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
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
    
    @After
    public void tearDown() {
    }

    /**
     * Test of main method, of class Listener.
     */
    @Test
    public void testMain() {
        System.out.println("main");
        String[] args = null;
        Listener.main(args);
    }
    
}
