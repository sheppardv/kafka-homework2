package homework2

object Main extends App {

  val appType = args
    .lift(0)
    .getOrElse(throw new IllegalArgumentException("Missing App Type"))

  val brokers = args.lift(1).getOrElse(":9092,localhost:9192")
  val topic = args.lift(2).getOrElse("github-events")

  appType match {
    case "ConsumerAll" =>
      val groupId = args.lift(3).getOrElse("github-events-all")
      val consumer =
        new ConsumerAll(brokers = brokers, topic = topic, groupId = groupId)
      consumer.receiveMessages()

    case "ConsumerFiltered" =>
      val groupId = args.lift(3).getOrElse("github-events-filtered")

      val consumer =
        new ConsumerFiltered(brokers = brokers,
                             topic = topic,
                             groupId = groupId)
      consumer.receiveMessages()

    case "Producer" =>
      val producer = new Producer(brokers = brokers, topic = topic)
      producer.sendMessages()

    case other =>
      throw new IllegalArgumentException(s"Unknown App Type `$other`")
  }
}
