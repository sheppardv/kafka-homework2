package homework2

import java.util.Properties

import io.circe.generic.auto._
import io.circe.syntax._
import org.apache.kafka.clients.producer.{
  KafkaProducer,
  ProducerConfig,
  ProducerRecord
}
import org.apache.kafka.common.serialization.StringSerializer

class Producer(topic: String, brokers: String) {

  val producer = new KafkaProducer[String, String](configuration)

  val ghClient = new GithubClient

  private def configuration: Properties = {
    val props = new Properties()
    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, brokers)
    props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
              classOf[StringSerializer].getCanonicalName)
    props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
              classOf[StringSerializer].getCanonicalName)
    props
  }

  def sendMessages(): Unit = {
    while (true) {
      val events = ghClient.getNormalizedEvents
      println(s"Found ${events.length} gh events ...")
      events.map { event =>
        val message = event.asJson.noSpaces
        val key = event.id.toString
        val record = new ProducerRecord[String, String](topic, key, message)
        producer.send(record)
      }
      Thread.sleep(3000)
    }

    producer.close()
  }

}
