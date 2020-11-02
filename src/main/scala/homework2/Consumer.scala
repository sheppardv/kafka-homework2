package homework2

import java.util.Properties

import homework2.GithubClient.NormalizedEvent
import io.circe.generic.auto._
import io.circe.parser.decode
import org.apache.kafka.clients.consumer.{
  ConsumerConfig,
  ConsumerRecords,
  KafkaConsumer
}
import org.apache.kafka.common.serialization.StringDeserializer

import scala.jdk.CollectionConverters._

abstract class Consumer(brokers: String, topic: String, groupId: String) {
  val FILTER_EVENT_TYPE = "PushEvent"

  val consumer = new KafkaConsumer[String, String](configuration)
  consumer.subscribe(List(topic).asJava)

  private def configuration: Properties = {
    val props = new Properties()
    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, brokers)
    props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
              classOf[StringDeserializer].getCanonicalName)
    props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
              classOf[StringDeserializer].getCanonicalName)
    props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId)
    props
  }

  def receiveMessages(): Unit = {
    while (true) {
      val records: ConsumerRecords[String, String] = consumer.poll(1000)
      records.asScala.foreach { record =>
        val event: NormalizedEvent = decode[NormalizedEvent](record.value)
          .getOrElse(throw new IllegalArgumentException("Can't parse event"))

        processEvent(event, record.offset())
      }
    }
  }

  def processEvent(event: NormalizedEvent, offset: Long): Unit
}
