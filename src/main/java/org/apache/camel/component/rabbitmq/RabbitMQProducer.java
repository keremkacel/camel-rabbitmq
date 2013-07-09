package org.apache.camel.component.rabbitmq;

import ly.bit.nsq.NSQProducer;
import org.apache.camel.Endpoint;
import org.apache.camel.Exchange;
import org.apache.camel.impl.DefaultProducer;

/**
 * @author Stephen Samuel
 */
public class RabbitMQProducer extends DefaultProducer {

    public RabbitMQProducer(Endpoint endpoint) {
        super(endpoint);
        NSQProducer producer = new NSQProducer("http://127.0.0.1:4151", "testTopic");
    }

    @Override
    public void process(Exchange exchange) throws Exception {
    }
}
