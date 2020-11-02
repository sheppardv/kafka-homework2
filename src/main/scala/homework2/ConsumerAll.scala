package homework2

import homework2.GithubClient.NormalizedEvent

class ConsumerAll(brokers: String, topic: String, groupId: String)
    extends Consumer(brokers, topic, groupId) {

  def processEvent(event: NormalizedEvent, offset: Long): Unit = {
    println(s"Received event with offset $offset: $event")
  }
}
