package com.company;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class JMSProducer {

    public static void main(String[] args) {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(JMSMagic.SERVER_CONNECTION);
        try {
            Connection connection = connectionFactory.createConnection();
            connection.start();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Queue queue = session.createQueue(JMSMagic.QUEUE_NAME);
            MessageProducer messageProducer = session.createProducer(queue);

            String[] messages = {"efef", "efef"};
            for (String text : messages) {
                ObjectMessage message = session.createObjectMessage(new PrivateMessage(text, 0));
                messageProducer.send(message);
            }

            connection.close();
        } catch (JMSException e) {
            e.printStackTrace();
        }

    }
}
