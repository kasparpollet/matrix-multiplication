package com.company;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class JMSConsumer {
    public static void main(String[] args) {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(JMSMagic.SERVER_CONNECTION);
        // From versions 5.12.2 and 5.13.1 ActiveMQ does not trust the content of ObjectMessages. The following
        // line is a dirty trick to let ActiveMQ trust the content. In real life one should specify the package(s)
        // that can be trusted. See: https://activemq.apache.org/objectmessage.html
//        connectionFactory.setTrustAllPackages(true);
        try {
            Connection connection = connectionFactory.createConnection();
            connection.start();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Queue queue = session.createQueue(JMSMagic.QUEUE_NAME);
            MessageConsumer messageConsumer = session.createConsumer(queue);

            System.out.println("Processing messages.");
            boolean keepProcessing;
            do {
                PrivateMessage message = (PrivateMessage) ((ObjectMessage) messageConsumer.receive()).getObject();
                System.out.printf("Received [%s]\n", message.getMessage());
                keepProcessing = !"STOP".equals(message.getMessage());
            } while (keepProcessing);

            System.out.println("Stopped processing messages.");
            connection.close();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
