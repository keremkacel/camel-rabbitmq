package org.apache.camel.component.rabbitmq;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Connection;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.impl.DefaultMessage;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.concurrent.ExecutorService;

import static org.junit.Assert.assertEquals;

/**
 * @author Stephen Samuel
 */
public class RabbitMQProducerTest {

    private RabbitMQEndpoint endpoint = Mockito.mock(RabbitMQEndpoint.class);
    private Exchange exchange = Mockito.mock(Exchange.class);
    private Message message = new DefaultMessage();
    private Connection conn = Mockito.mock(Connection.class);

    @Before
    public void before() throws IOException {
        Mockito.when(exchange.getIn()).thenReturn(message);
        Mockito.when(endpoint.connect(Matchers.any(ExecutorService.class))).thenReturn(conn);
        Mockito.when(conn.createChannel()).thenReturn(null);
    }

    @Test
    public void testPropertiesUsesContentTypeHeader() throws IOException {
        RabbitMQProducer producer = new RabbitMQProducer(endpoint);
        message.setHeader(RabbitMQConstants.CONTENT_TYPE, "application/json");
        AMQP.BasicProperties props = producer.buildProperties(exchange).build();
        assertEquals("application/json", props.getContentType());
    }

    @Test
    public void testPropertiesUsesCorrelationHeader() throws IOException {
        RabbitMQProducer producer = new RabbitMQProducer(endpoint);
        message.setHeader(RabbitMQConstants.CORRELATIONID, "124544");
        AMQP.BasicProperties props = producer.buildProperties(exchange).build();
        assertEquals("124544", props.getCorrelationId());
    }

    @Test
    public void testPropertiesUsesUserIdHeader() throws IOException {
        RabbitMQProducer producer = new RabbitMQProducer(endpoint);
        message.setHeader(RabbitMQConstants.USERID, "abcd");
        AMQP.BasicProperties props = producer.buildProperties(exchange).build();
        assertEquals("abcd", props.getUserId());
    }

    @Test
    public void testPropertiesUsesMessageIdHeader() throws IOException {
        RabbitMQProducer producer = new RabbitMQProducer(endpoint);
        message.setHeader(RabbitMQConstants.MESSAGE_ID, "abvasweaqQQ");
        AMQP.BasicProperties props = producer.buildProperties(exchange).build();
        assertEquals("abvasweaqQQ", props.getMessageId());
    }

    @Test
    public void testPropertiesUsesDeliveryModeHeader() throws IOException {
        RabbitMQProducer producer = new RabbitMQProducer(endpoint);
        message.setHeader(RabbitMQConstants.DELIVERY_MODE, "444");
        AMQP.BasicProperties props = producer.buildProperties(exchange).build();
        assertEquals(444, props.getDeliveryMode().intValue());
    }

    @Test
    public void testPropertiesUsesClusterIdHeader() throws IOException {
        RabbitMQProducer producer = new RabbitMQProducer(endpoint);
        message.setHeader(RabbitMQConstants.CLUSTERID, "abtasg5r");
        AMQP.BasicProperties props = producer.buildProperties(exchange).build();
        assertEquals("abtasg5r", props.getClusterId());
    }

    @Test
    public void testPropertiesUsesReplyToHeader() throws IOException {
        RabbitMQProducer producer = new RabbitMQProducer(endpoint);
        message.setHeader(RabbitMQConstants.REPLY_TO, "bbbbdfgdfg");
        AMQP.BasicProperties props = producer.buildProperties(exchange).build();
        assertEquals("bbbbdfgdfg", props.getReplyTo());
    }

    @Test
    public void testPropertiesUsesPriorityHeader() throws IOException {
        RabbitMQProducer producer = new RabbitMQProducer(endpoint);
        message.setHeader(RabbitMQConstants.PRIORITY, "15");
        AMQP.BasicProperties props = producer.buildProperties(exchange).build();
        assertEquals(15, props.getPriority().intValue());
    }

    @Test
    public void testPropertiesUsesExpirationHeader() throws IOException {
        RabbitMQProducer producer = new RabbitMQProducer(endpoint);
        message.setHeader(RabbitMQConstants.EXPIRATION, "thursday");
        AMQP.BasicProperties props = producer.buildProperties(exchange).build();
        assertEquals("thursday", props.getExpiration());
    }

    @Test
    public void testPropertiesUsesTypeHeader() throws IOException {
        RabbitMQProducer producer = new RabbitMQProducer(endpoint);
        message.setHeader(RabbitMQConstants.TYPE, "sometype");
        AMQP.BasicProperties props = producer.buildProperties(exchange).build();
        assertEquals("sometype", props.getType());
    }

    @Test
    public void testPropertiesUsesContentEncodingHeader() throws IOException {
        RabbitMQProducer producer = new RabbitMQProducer(endpoint);
        message.setHeader(RabbitMQConstants.CONTENT_ENCODING, "qwergghdfdfgdfgg");
        AMQP.BasicProperties props = producer.buildProperties(exchange).build();
        assertEquals("qwergghdfdfgdfgg", props.getContentEncoding());
    }

    @Test
    public void testPropertiesAppIdHeader() throws IOException {
        RabbitMQProducer producer = new RabbitMQProducer(endpoint);
        message.setHeader(RabbitMQConstants.APP_ID, "qweeqwe");
        AMQP.BasicProperties props = producer.buildProperties(exchange).build();
        assertEquals("qweeqwe", props.getAppId());
    }

    @Test
    public void testPropertiesUsesTimestampHeader() throws IOException {
        RabbitMQProducer producer = new RabbitMQProducer(endpoint);
        message.setHeader(RabbitMQConstants.TIMESTAMP, "12345123");
        AMQP.BasicProperties props = producer.buildProperties(exchange).build();
        assertEquals(12345123, props.getTimestamp().getTime());
    }
}
