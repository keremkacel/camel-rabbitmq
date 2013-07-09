package org.apache.camel.component.rabbitmq;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.apache.camel.Processor;
import org.apache.camel.impl.DefaultConsumer;

import java.io.IOException;

/**
 * @author Stephen Samuel
 */
public class RabbitMQConsumer extends DefaultConsumer {

    private final RabbitMQEndpoint endpoint;

    public RabbitMQConsumer(RabbitMQEndpoint endpoint, Processor processor) {
        super(endpoint, processor);
        this.endpoint = endpoint;
    }

    @Override
    protected void doStart() throws Exception {
        super.doStart();
        connect();
        log.info("Starting RabbitMQ consumer");
    }

    private void connect() throws IOException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername(endpoint.getUsername());
        factory.setPassword(endpoint.getPassword());
        factory.setVirtualHost(endpoint.getVirtualHost());
        factory.setHost(endpoint.getHostname());
        factory.setPort(endpoint.getPortNumber());
        Connection conn = factory.newConnection();
    }

    @Override
    protected void doStop() throws Exception {
        super.doStop();
        log.info("Stopping RabbitMQ consumer");
    }
}
