package com.company;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class DistributedParallelQueueProducer {
    private final float[][] mat1; // float[row][col]
    private final float[][] mat2; // float[col][row]
    public float[][] res;
    private final int numberOfConsumers;

    private Connection calculationConnection;
    private Session calculationSession;
    private MessageProducer calculationMessageProducer;

    private Connection resultConnection;
    private Session resultSession;
    private MessageConsumer resultMessageConsumer;


    public DistributedParallelQueueProducer(float[][] mat1, float[][] mat2, int numberOfConsumers) {
        this.mat1 = mat1;
        this.mat2 = mat2;
        this.numberOfConsumers = numberOfConsumers;
        res = new float[mat1.length][mat2.length];
    }


    private void connectCalculationQueue() {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(JMSMagic.SERVER_CONNECTION);
        connectionFactory.setTrustAllPackages(true);
        try {
            calculationConnection = connectionFactory.createConnection();
            calculationConnection.start();
            calculationSession = calculationConnection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Queue queue = calculationSession.createQueue(JMSMagic.QUEUE_NAME_CALCULATIONS);
            calculationMessageProducer = calculationSession.createProducer(queue);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    private void connectResultQueue() {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(JMSMagic.SERVER_CONNECTION);
        connectionFactory.setTrustAllPackages(true);
        try {
            resultConnection = connectionFactory.createConnection();
            resultConnection.start();
            resultSession = resultConnection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Queue queue = resultSession.createQueue(JMSMagic.QUEUE_NAME_RESULTS);
            resultMessageConsumer = resultSession.createConsumer(queue);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public void fillQueue() {
        for (int i = 0; i < res.length; i++) {
            for (int j = 0; j < res.length; j++) {
                try {
                    ToCalculate toCalculate = new ToCalculate(mat1[i], mat2[j], i, j, res.length);
                    ObjectMessage message = calculationSession.createObjectMessage(toCalculate);
                    calculationMessageProducer.send(message);
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        }
        // CREATE STOP MESSAGES
        for (int i = 0; i < numberOfConsumers; i++) {
            try {
                ToCalculate toCalculate = new ToCalculate(mat1[0], mat2[0], -1, -1, -1);
                ObjectMessage message = calculationSession.createObjectMessage(toCalculate);
                calculationMessageProducer.send(message);
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }

    public float[][] multiplyMatrices() {

        // START ALL CONSUMERS
//        for (int i = 0; i < numberOfConsumers; i++) {
//            System.out.println("starting consumer...");
//            new JMSConsumer().run();
//        }

        // FILL QUEUE
        connectCalculationQueue();
        fillQueue();
        try {
            calculationConnection.close();
        } catch (JMSException e) {
            e.printStackTrace();
        }

        connectResultQueue();
        // Read new queue
        // Set all results
        try {
            for (int i = 0; i < res.length; i++) {
                for (int j = 0; j < res[0].length; j++) {
                    Result result = (Result) ((ObjectMessage) resultMessageConsumer.receive()).getObject();
                    res[result.getI()][result.getJ()] = result.getSum();
                }
            }
            resultConnection.close();
        } catch (JMSException e) {
            e.printStackTrace();
        }

        try {
            resultConnection.close();
        } catch (JMSException e) {
            e.printStackTrace();
        }

        return res;
    }
}
