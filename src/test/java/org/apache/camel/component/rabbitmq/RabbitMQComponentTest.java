package org.apache.camel.component.rabbitmq;

import org.apache.camel.CamelContext;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * @author Stephen Samuel
 */
public class RabbitMQComponentTest {

    private CamelContext context = Mockito.mock(CamelContext.class);

    @Test
    public void testPropertiesSet() throws Exception {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("username", "coldplay");
        params.put("password", "chrism");
        params.put("autoAck", true);
        params.put("virtualHost", "vman");
        params.put("threadPoolSize", 515);
        params.put("portNumber", 14123);
        params.put("hostname", "special.host");
        params.put("queueName", "queuey");

        String uri = "couchdb:http://localhost:14/db";
        String remaining = "http://localhost:14/db";

        RabbitMQEndpoint endpoint = new RabbitMQComponent(context).createEndpoint(uri, remaining, params);
        assertEquals("special.host", endpoint.getHostname());
        assertEquals("chrism", endpoint.getPassword());
        assertEquals("coldplay", endpoint.getUsername());
        assertEquals("queuey", endpoint.getQueueName());
        assertEquals("vman", endpoint.getVirtualHost());
        assertEquals(14123, endpoint.getPortNumber());
        assertEquals(515, endpoint.getThreadPoolSize());
        assertEquals(true, endpoint.isAutoAck());
    }
}
