/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kostas.drakonakis.apache.queue.server;

import java.util.logging.Level;
import java.util.logging.Logger;
import static org.testng.Assert.*;

/**
 *
 * @author Matrix
 */
public class SenderNGTest {
    
    public SenderNGTest() {
    }

    @org.testng.annotations.BeforeClass
    public static void setUpClass() throws Exception {
    }

    @org.testng.annotations.AfterClass
    public static void tearDownClass() throws Exception {
    }

    @org.testng.annotations.BeforeMethod
    public void setUpMethod() throws Exception {
        ActiveMQQueueSender sender = new ActiveMQQueueSender("tcp://localhost:61616", "admin", "admin");
        try {
            sender.sendTextMessage("Test TextMessage Queue", "Hello Gandalf the Gray");
            sender.sendMapMessage("Test MapMessage Queue");
            sender.sendObjectMessage("Test ObjectMessage Queue");
            sender.sendTextMessageTopic("Test TextMessage Topic", "Hello from Windows Server with Topic Junit Test");
            sender.sendMapMessageTopic("Test MapMessage topic");
        } catch (Exception ex) {
            Logger.getLogger(SenderTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @org.testng.annotations.AfterMethod
    public void tearDownMethod() throws Exception {
    }

    /**
     * Test of main method, of class Sender.
     */
    @org.testng.annotations.Test
    public void testMain() {
        System.out.println("main");
        String[] args = null;
        Sender.main(args);
    }
    
}
