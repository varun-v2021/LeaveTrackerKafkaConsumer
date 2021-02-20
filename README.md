# LeaveTrackerKafkaConsumer

This is an independent microservice which listens to kafka Topic same as that of LeaveTracker service

They both get the messages as they are part of same consumer group which is set using

configMap.put(ConsumerConfig.GROUP_ID_CONFIG, ApplicationConstant.GROUP_ID_JSON);

Make sure before running the microservices, zookeeper, kafka and topic are running.

(For Windows make sure JAVA_HOME is properly set and execute below commands from cygwin)

./zookeeper-server-start.sh ../config/zookeeper.properties

./kafka-server-start.sh ../config/server.properties

./kafka-topics.sh -create -zookeeper localhost:2181 -replication-factor 1 -partitions 2 -topic leave-tracker-topic-3

To observe the demo of kafka working, standup both LeaveTracker and LeaveTrackerKafkaConsumer applcations.

KAFKA SEND message Steps:

Execute this multiple times and observe console of both microservices
POST http://localhost:8080/leaveTracker/users/login having payload and observe app console, it will be processed by both the kafka listeners

{ "mobile": 9123491236, "password":"1999" }
