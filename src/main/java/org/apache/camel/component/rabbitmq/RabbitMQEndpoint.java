package org.apache.camel.component.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Envelope;
import org.apache.camel.Consumer;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.Producer;
import org.apache.camel.impl.DefaultEndpoint;
import org.apache.camel.impl.DefaultExchange;
import org.apache.camel.impl.DefaultMessage;

import java.io.IOException;
import java.util.concurrent.ExecutorService;

/**
 * @author Stephen Samuel
 */
public class RabbitMQEndpoint extends DefaultEndpoint {

    private String username;
    private String password;
    private String virtualHost;
    private String hostname;
    private int threadPoolSize = 10;
    private int portNumber;
    private boolean autoAck;
    private String queueName;

    public int getThreadPoolSize() {
        return threadPoolSize;
    }

    public void setThreadPoolSize(int threadPoolSize) {
        this.threadPoolSize = threadPoolSize;
    }

    public boolean isAutoAck() {
        return autoAck;
    }

    public void setAutoAck(boolean autoAck) {
        this.autoAck = autoAck;
    }

    public String getQueueName() {
        return queueName;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }

    public RabbitMQEndpoint() {
    }

    public RabbitMQEndpoint(String endpointUri, String remaining, RabbitMQComponent component) throws Exception {
        super(endpointUri, component);
    }

    public Exchange createRabbitExchange(Envelope envelope) {
        Exchange exchange = new DefaultExchange(getCamelContext(), getExchangePattern());

        Message message = new DefaultMessage();
        exchange.setIn(message);

        message.setHeader(RabbitMQConstants.ROUTING_KEY, envelope.getRoutingKey());
        message.setHeader(RabbitMQConstants.EXCHANGE_NAME, envelope.getExchange());
        message.setHeader(RabbitMQConstants.DELIVERY_TAG, envelope.getDeliveryTag());

        return exchange;
    }

    @Override
    public Consumer createConsumer(Processor processor) throws Exception {
        RabbitMQConsumer consumer = new RabbitMQConsumer(this, processor);
        configureConsumer(consumer);
        return consumer;
    }

    public Connection connect(ExecutorService executor) throws IOException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername(getUsername());
        factory.setPassword(getPassword());
        factory.setVirtualHost(getVirtualHost());
        factory.setHost(getHostname());
        factory.setPort(getPortNumber());
        Connection conn = factory.newConnection(executor);
        Channel channel = conn.createChannel();
        channel.close();
        conn.close();
        return conn;
    }

    @Override
    public Producer createProducer() throws Exception {
        return new RabbitMQProducer(this);
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    public int getPortNumber() {
        return portNumber;
    }

    public String getHostname() {
        return hostname;
    }

    public String getVirtualHost() {
        return virtualHost;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setVirtualHost(String virtualHost) {
        this.virtualHost = virtualHost;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public void setPortNumber(int portNumber) {
        this.portNumber = portNumber;
    }
}
