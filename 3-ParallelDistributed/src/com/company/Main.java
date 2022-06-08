package com.company;

import org.apache.activemq.ActiveMQConnectionFactory;
import javax.jms.*;

public class Main {
    private static final String URL = "tcp://localhost:61616";
    private static final String QUEUE_NAME = "testQueue";

    public static void main(String[] args) throws JMSException {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(URL);
        Connection connection = connectionFactory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination destination = session.createQueue(QUEUE_NAME);
        MessageProducer producer = session.createProducer(destination);
        TextMessage message = session.createTextMessage("Hello World!");
        producer.send(message);
        connection.close();
    }
}