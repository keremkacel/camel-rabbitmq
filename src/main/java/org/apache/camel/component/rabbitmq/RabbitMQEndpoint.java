package org.apache.camel.component.rabbitmq;

import org.apache.camel.Consumer;
import org.apache.camel.Processor;
import org.apache.camel.Producer;
import org.apache.camel.impl.DefaultEndpoint;

import java.net.URI;

/**
 * @author Stephen Samuel
 */
public class RabbitMQEndpoint extends DefaultEndpoint {

    public RabbitMQEndpoint() {
    }

    public RabbitMQEndpoint(String endpointUri, String remaining, RabbitMQComponent component) throws Exception {
        super(endpointUri, component);
        URI uri = new URI(remaining);
    }

    @Override
    public Consumer createConsumer(Processor processor) throws Exception {
        RabbitMQConsumer consumer = new RabbitMQConsumer(this, processor);
        configureConsumer(consumer);
        return consumer;
    }

    @Override
    public Producer createProducer() throws Exception {
        return new RabbitMQProducer(this);
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
