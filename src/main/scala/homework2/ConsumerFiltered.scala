package homework2

import homework2.GithubClient.NormalizedEvent

class ConsumerFiltered(brokers: String, topic: String, groupId: String)
    extends Consumer(brokers, topic, groupId) {

  def processEvent(event: NormalizedEvent, offset: Long): Unit = {
    if (event.`type` == FILTER_EVENT_TYPE) {
      println(s"Received $FILTER_EVENT_TYPE with offset $offset: $event")
    }
  }
}
