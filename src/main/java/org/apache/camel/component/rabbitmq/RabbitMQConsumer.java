package org.apache.camel.component.rabbitmq;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Envelope;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.impl.DefaultConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Stephen Samuel
 */
public class RabbitMQConsumer extends DefaultConsumer {

    private static final Logger logger = LoggerFactory.getLogger(RabbitMQConsumer.class);

    private final RabbitMQEndpoint endpoint;
    private final ExecutorService executor = Executors.newFixedThreadPool(20);
    private Connection conn;
    private Channel channel;

    public RabbitMQEndpoint getEndpoint() {
        return endpoint;
    }

    public RabbitMQConsumer(RabbitMQEndpoint endpoint, Processor processor) {
        super(endpoint, processor);
        this.endpoint = endpoint;
    }

    @Override
    protected void doStart() throws Exception {
        super.doStart();
        log.info("Starting RabbitMQ consumer");
        conn = endpoint.connect(executor);
        channel = conn.createChannel();
        channel.basicConsume(endpoint.getQueueName(), endpoint.isAutoAck(), new RabbitConsumer(this, channel));
    }

    @Override
    protected void doStop() throws Exception {
        super.doStop();
        log.info("Stopping RabbitMQ consumer");
        if (conn != null)
            conn.close();
        executor.shutdown();
    }
}

class RabbitConsumer extends com.rabbitmq.client.DefaultConsumer {

    private static final Logger logger = LoggerFactory.getLogger(RabbitConsumer.class);

    private final RabbitMQConsumer consumer;
    private final Channel channel;

    /**
     * Constructs a new instance and records its association to the passed-in channel.
     *
     * @param channel the channel to which this consumer is attached
     */
    public RabbitConsumer(RabbitMQConsumer consumer, Channel channel) {
        super(channel);
        this.consumer = consumer;
        this.channel = channel;
    }

    @Override
    public void handleDelivery(String consumerTag,
                               Envelope envelope,
                               AMQP.BasicProperties properties,
                               byte[] body)
            throws IOException {

        Exchange exchange = consumer.getEndpoint().createRabbitExchange(envelope);
        logger.trace("Created exchange [exchange={}]", new Object[]{exchange});

        try {
            consumer.getProcessor().process(exchange);

            long deliveryTag = envelope.getDeliveryTag();
            logger.trace("Ackknowleding receipt [delivery_tag={}]", deliveryTag);
            channel.basicAck(deliveryTag, false);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}