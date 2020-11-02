package homework2

import io.circe.generic.auto._
import sttp.client3._
import sttp.client3.circe._

import homework2.GithubClient.{GithubEvent, NormalizedEvent}

final class GithubClient {
  def getEvents: List[GithubEvent] = {
    val eventsUri = uri"https://api.github.com/events"
    val request =
      basicRequest.get(eventsUri).response(asJson[List[GithubEvent]].getRight)

    val backend = HttpURLConnectionBackend()
    val response = request.send(backend)
    response.body
  }

  def getNormalizedEvents: Seq[NormalizedEvent] =
    getEvents.map(_.toNormalized)
}

object GithubClient {
  final case class GithubEvent(
      id: String,
      `type`: String,
      actor: Actor,
      repo: Repo,
      created_at: String,
  )

  final case class Actor(
      id: Long,
      url: String,
  )

  final case class Repo(
      id: Long,
      url: String,
  )

  final case class NormalizedEvent(
      id: Long,
      `type`: String,
      actorId: Long,
      actorUrl: String,
      repoId: Long,
      repoUrl: String,
      createdAt: String,
  )

  implicit final class EventToNormalized(val e: GithubEvent) extends AnyVal {
    def toNormalized: NormalizedEvent =
      NormalizedEvent(
        id = e.id.toLong,
        `type` = e.`type`,
        actorId = e.actor.id,
        actorUrl = e.actor.url,
        repoId = e.repo.id,
        repoUrl = e.repo.url,
        createdAt = e.created_at
      )
  }
}
