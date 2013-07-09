package org.apache.camel.component.rabbitmq;

import org.apache.camel.Endpoint;
import org.apache.camel.EndpointInject;
import org.apache.camel.component.mock.MockEndpoint;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static junit.framework.TestCase.assertTrue;

/**
 * @author Stephen Samuel
 */
public class RabbitMQIntTest {

    private static final Logger logger = LoggerFactory.getLogger(RabbitMQIntTest.class);

    @EndpointInject(uri = "rabbitmq:localhost:5672/myqueue")
    private Endpoint from;

    @EndpointInject(uri = "mock:result")
    private MockEndpoint to;

    @Test
    public void fail() {
        assertTrue(false);
    }
}
