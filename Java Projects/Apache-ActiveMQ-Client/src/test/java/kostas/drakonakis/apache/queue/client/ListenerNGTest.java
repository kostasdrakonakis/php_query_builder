/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kostas.drakonakis.apache.queue.client;

import static org.testng.Assert.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author Matrix
 */
public class ListenerNGTest {
    
    public ListenerNGTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
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

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
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
