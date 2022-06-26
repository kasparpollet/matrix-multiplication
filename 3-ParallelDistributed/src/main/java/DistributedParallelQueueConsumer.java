import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class DistributedParallelQueueConsumer {
    private Connection calculationConnection;
    private Session calculationSession;
    private MessageConsumer calculationMessageConsumer;
    private final ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(JMSConstants.SERVER_CONNECTION);

    private Connection resultConnection;
    private Session resultSession;
    private MessageProducer resultMessageProducer;

    private void connectCalculationQueue() {
        connectionFactory.setTrustAllPackages(true);
        try {
            calculationConnection = connectionFactory.createConnection();
            calculationConnection.start();
            calculationSession = calculationConnection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Queue queue = calculationSession.createQueue(JMSConstants.QUEUE_NAME_CALCULATIONS);
            calculationMessageConsumer = calculationSession.createConsumer(queue);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    private void connectResultQueue() {
        connectionFactory.setTrustAllPackages(true);
        try {
            resultConnection = connectionFactory.createConnection();
            resultConnection.start();
            resultSession = resultConnection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Queue queue = resultSession.createQueue(JMSConstants.QUEUE_NAME_RESULTS);
            resultMessageProducer = resultSession.createProducer(queue);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        boolean keepProcessing = false;

        connectCalculationQueue();
        connectResultQueue();
        try {
            do {
                ToCalculate toCalculate = (ToCalculate) ((ObjectMessage) calculationMessageConsumer.receive()).getObject();

                keepProcessing = toCalculate.getI() == -1;
                if (keepProcessing) break;
                float sum = toCalculate.calculate();

                ObjectMessage result = resultSession.createObjectMessage(new Result(toCalculate.getI(), toCalculate.getJ(), sum));
                resultMessageProducer.send(result);
            } while (true);

            System.out.println("Stopped processing messages.");
            calculationConnection.close();
            resultConnection.close();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
