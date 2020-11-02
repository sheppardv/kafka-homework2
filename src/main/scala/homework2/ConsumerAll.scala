package homework2

import java.io.FileWriter
import io.circe.syntax._
import io.circe.generic.auto._

import homework2.GithubClient.NormalizedEvent

class ConsumerAll(brokers: String, topic: String, groupId: String)
    extends Consumer(brokers, topic, groupId) {

  def processEvent(event: NormalizedEvent, offset: Long): Unit = {
    writeFile("events.log", event.asJson.noSpaces + "\n")
    println(s"Received event with offset $offset: $event")
  }

  def writeFile(filename: String, s: String): Unit = {
    val fw = new FileWriter(filename, true) ;
    fw.write(s) ;
    fw.close()
  }
}
