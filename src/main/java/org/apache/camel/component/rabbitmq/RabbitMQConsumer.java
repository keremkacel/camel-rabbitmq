package org.apache.camel.component.rabbitmq;

import org.apache.camel.Endpoint;
import org.apache.camel.Processor;
import org.apache.camel.impl.DefaultConsumer;

/**
 * @author Stephen Samuel
 */
public class RabbitMQConsumer extends DefaultConsumer {

    public RabbitMQConsumer(Endpoint endpoint, Processor processor) {
        super(endpoint, processor);
    }

    @Override
    protected void doStart() throws Exception {
        super.doStart();
        log.info("Starting RabbitMQ consumer");
    }

    @Override
    protected void doStop() throws Exception {
        super.doStop();
        log.info("Stopping RabbitMQ consumer");
    }
}
