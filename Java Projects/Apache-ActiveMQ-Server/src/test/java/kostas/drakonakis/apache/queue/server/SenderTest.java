/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kostas.drakonakis.apache.queue.server;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class SenderTest {

    public SenderTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
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

    @After
    public void tearDown() {
    }

    /**
     * Test of main method, of class Sender.
     */
    @Test
    public void testMain() {
        System.out.println("main");
        String[] args = null;
        Sender.main(args);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

}
