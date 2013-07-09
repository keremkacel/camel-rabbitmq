package org.apache.camel.component.rabbitmq;

import org.apache.camel.Consumer;
import org.apache.camel.Processor;
import org.apache.camel.Producer;
import org.apache.camel.impl.DefaultEndpoint;

/**
 * @author Stephen Samuel
 */
public class RabbitMQEndpoint extends DefaultEndpoint {

    private String username;
    private String password;
    private String virtualHost;
    private String hostname;
    private int portNumber;

    public RabbitMQEndpoint() {
    }

    public RabbitMQEndpoint(String endpointUri, String remaining, RabbitMQComponent component) throws Exception {
        super(endpointUri, component);
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
