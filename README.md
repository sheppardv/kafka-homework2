### Kafka training App
One Producer Two Consumers 

### How to run 
`mvn clean package`

`java -cp "target/kafka-homework2-1.0-SNAPSHOT-jar-with-dependencies.jar" homework2.Main <type> <brokers> <topic>`

where 

`<type>` is `Producer`, `ConsumerFiltered` or `ConsumerAll`

`<brokers>` is default to `:9092,localhost:9192`

`<topic>` is default to `github-events`

e.g. run `ConsumerAll` and `ConsumerFiltered`, after that run `Producer`