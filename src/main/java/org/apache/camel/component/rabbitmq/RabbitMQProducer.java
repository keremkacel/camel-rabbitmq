package org.apache.camel.component.rabbitmq;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import org.apache.camel.Exchange;
import org.apache.camel.impl.DefaultProducer;

import java.io.IOException;
import java.util.concurrent.Executors;

/**
 * @author Stephen Samuel
 */
public class RabbitMQProducer extends DefaultProducer {

    private Connection conn;
    private Channel channel;

    public RabbitMQProducer(RabbitMQEndpoint endpoint) throws IOException {
        super(endpoint);
        this.conn = endpoint.connect(Executors.newSingleThreadExecutor());
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
        AMQP.BasicProperties.Builder properties = buildProperties(exchange);

        channel.basicPublish(exchangeName,
                key == null ? "" : key.toString(),
                properties.build(),
                messageBodyBytes);
    }

    AMQP.BasicProperties.Builder buildProperties(Exchange exchange) {
        AMQP.BasicProperties.Builder properties = new AMQP.BasicProperties.Builder();

        final Object contentType = exchange.getIn().getHeader(RabbitMQConstants.CONTENT_TYPE);
        if (contentType != null)
            properties.contentType(contentType.toString());

        final Object priority = exchange.getIn().getHeader(RabbitMQConstants.PRIORITY);
        if (priority != null)
            properties.priority(Integer.parseInt(priority.toString()));

        final Object messageId = exchange.getIn().getHeader(RabbitMQConstants.MESSAGE_ID);
        if (messageId != null)
            properties.messageId(messageId.toString());

        final Object clusterId = exchange.getIn().getHeader(RabbitMQConstants.CLUSTERID);
        if (clusterId != null)
            properties.clusterId(clusterId.toString());

        final Object replyTo = exchange.getIn().getHeader(RabbitMQConstants.REPLY_TO);
        if (replyTo != null)
            properties.replyTo(replyTo.toString());

        final Object correlationId = exchange.getIn().getHeader(RabbitMQConstants.CORRELATIONID);
        if (correlationId != null)
            properties.correlationId(correlationId.toString());

        final Object deliveryMode = exchange.getIn().getHeader(RabbitMQConstants.DELIVERY_MODE);
        if (deliveryMode != null)
            properties.deliveryMode(Integer.parseInt(deliveryMode.toString()));

        final Object userId = exchange.getIn().getHeader(RabbitMQConstants.USERID);
        if (userId != null)
            properties.userId(userId.toString());

        final Object type = exchange.getIn().getHeader(RabbitMQConstants.TYPE);
        if (type != null)
            properties.type(type.toString());

        final Object CONTENT_ENCODING = exchange.getIn().getHeader(RabbitMQConstants.CONTENT_ENCODING);
        if (CONTENT_ENCODING != null)
            properties.contentEncoding(CONTENT_ENCODING.toString());

        final Object EXPIRATION = exchange.getIn().getHeader(RabbitMQConstants.EXPIRATION);
        if (EXPIRATION != null)
            properties.expiration(EXPIRATION.toString());

        return properties;
    }
}
