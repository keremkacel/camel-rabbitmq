package org.apache.camel.component.rabbitmq;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import org.apache.camel.Exchange;
import org.apache.camel.impl.DefaultProducer;

import java.io.IOException;

/**
 * @author Stephen Samuel
 */
public class RabbitMQProducer extends DefaultProducer {

    private Connection conn;
    private Channel channel;

    public RabbitMQProducer(RabbitMQEndpoint endpoint) throws IOException {
        super(endpoint);
        this.conn = endpoint.connect(null);
        this.channel = conn.createChannel();
    }

    public void shutdown() throws IOException {
        conn.close();
    }

    @Override
    public void process(Exchange exchange) throws Exception {

        Object key = exchange.getIn().getHeader(RabbitMQConstants.ROUTING_KEY);
        String exchangeName = exchange.getIn().getHeader(RabbitMQConstants.EXCHANGE_NAME).toString();
        byte[] messageBodyBytes = exchange.getIn().getBody(byte[].class);

        AMQP.BasicProperties.Builder properties = new AMQP.BasicProperties.Builder();
        if (exchange.getIn().getHeader(RabbitMQConstants.CONTENT_TYPE) != null)
            properties.contentType(exchange.getIn().getHeader(RabbitMQConstants.CONTENT_TYPE).toString());
        if (exchange.getIn().getHeader(RabbitMQConstants.PRIORITY) != null)
            properties.priority(Integer.parseInt(exchange.getIn().getHeader(RabbitMQConstants.PRIORITY).toString()));

        channel.basicPublish(exchangeName,
                key == null ? "" : key.toString(),
                properties.build(),
                messageBodyBytes);

    }
}
