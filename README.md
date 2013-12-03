##RabbitMQ Camel Component 

*Part of the standard Camel distribution as of Camel 2.12 - Updates are now being commited to the Camel repository and not to this one. See [Camel website](http://camel.apache.org/rabbitmq.html)*

The **rabbitmq:** component allows you produce and consume messages from RabbitMQ instances using the standard RabbitMQ AMQP client.

####How to use
Maven users will need to add the following dependency to their pom.xml for this component:
```xml
<dependency>
    <groupId>org.apache.camel</groupId>
    <artifactId>camel-rabbitmq</artifactId>
    <version>x.x.x</version>
    <!-- use the same version as your Camel core version -->
</dependency>
```

####URI format
```
rabbitmq:http://hostname[:port]/exchangeName?[options...]://name[?options]
```

- Where hostname is the hostname of the running rabbitmq instance or cluster. 
- Port is optional and if not specified then defaults to the RabbitMQ client default (5672). 
- The exchange name determines which exchange produced messages will sent to. In the case of consumers, the exchange name determines which exchange the queue will bind to.

####Options
| Property  | Default|	 Description|
|-----------|--------|--------------|
|autoAck	|true	| If messages should be auto acknowledged|
|queue	|random uuid	| The queue to receive messages from|
|routingKey	|null	| The routing key to use when binding a consumer queue to the exchange. For producer routing keys, you set the header (see header section)
|threadPoolSize	|10|	 The consumer uses a Thread Pool Executor with a fixed number of threads. This setting allows you to set that number of threads.
|username|	null|	 username in case of authenticated access
|password|	null|	 password for authenticated access
|vhost|	/|	 the vhost for the channel


####Headers

The following headers are set on exchanges when consuming messages.

|Property	| Description |
|-----------|--------|
|rabbitmq.ROUTING_KEY	| The routing key that was used to receive the message, or the routing key that will be used when producing a message
|rabbitmq.EXCHANGE_NAME	| The exchange the message was received from
|rabbitmq.DELIVERY_TAG|	 The rabbitmq delivery tag of the received message

The following headers are used by the producer. If these are set on the camel exchange then they will be set on the RabbitMQ message.

|Property	| Description |
|-----------|--------|
|rabbitmq.ROUTING_KEY	| The routing key that will be used when sending the message
|rabbitmq.EXCHANGE_NAME	| The exchange the message was received from, or sent to
|rabbitmq.CONTENT_TYPE	| The contentType to set on the RabbitMQ message
|rabbitmq.PRIORITY	| The priority header to set on the RabbitMQ message
|rabbitmq.CORRELATIONID	| The correlationId to set on the RabbitMQ message
|rabbitmq.MESSAGE_ID	| The message id to set on the RabbitMQ message
|rabbitmq.DELIVERY_MODE	 |If the message should be persistent or not
|rabbitmq.USERID	| The userId to set on the RabbitMQ message
|rabbitmq.CLUSTERID	| The clusterId to set on the RabbitMQ message
|rabbitmq.REPLY_TO	| The replyTo to set on the RabbitMQ message
|rabbitmq.CONTENT_ENCODING	| The contentEncoding to set on the RabbitMQ message
|rabbitmq.TYPE	| The type to set on the RabbitMQ message
|rabbitmq.EXPIRATION	| The expiration to set on the RabbitMQ message
|rabbitmq.TIMESTAMP|	 The timestamp to set on the RabbitMQ message
|rabbitmq.APP_ID	| The appId to set on the RabbitMQ message

Headers are set by the consumer once the message is received. The producer will also set the headers for downstream processors once the exchange has taken place. Any headers set prior to production that the producer sets will be overriden.

####Message Body

The component will use the camel exchange in body as the rabbit mq message body. The camel exchange in object must be convertible 
to a byte array. Otherwise the producer will throw an exception of unsupported body type. 

####Samples

To receive messages from a queue that is bound to an exchange A with the routing key B:
```
from("rabbitmq://localhost/A?routingKey=B")
```

To receive messages from a queue with a single thread with auto acknowledge disabled.
```
from("rabbitmq://localhost/A?routingKey=B&threadPoolSize=1&autoAck=false")
```

To send messages to an exchange called C
```
...to("rabbitmq://localhost/B")
```


## License
```
This software is licensed under the Apache 2 license, quoted below.

Copyright 2013 Stephen Samuel
Original code Copyright 2006 Google Inc.

Licensed under the Apache License, Version 2.0 (the "License"); you may not
use this file except in compliance with the License. You may obtain a copy of
the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
License for the specific language governing permissions and limitations under
the License.
```
