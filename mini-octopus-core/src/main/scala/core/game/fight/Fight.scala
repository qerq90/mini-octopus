package core.game.fight

import core.game.creatures.Creature
import core.game.model.Model.{
  Action,
  CreatureId,
  CreatureInFight,
  FightId,
  MassAction
}

case class Fight(
    id: FightId,
    participants: List[CreatureInFight],
    ids: Map[Int, Int]) {

  private def findParticipantById(id: CreatureId): Option[CreatureInFight] =
    participants.find(_.id.value == id.value)

  private def findOpponent(id: Int): Option[CreatureInFight] =
    ids
      .get(id)
      .flatMap(opponentId => participants.find(_.fightId == opponentId))

  def makeAction(id: CreatureId, action: Action): Unit = for {
    participant <- findParticipantById(id)
    opponent <- findOpponent(participant.fightId)
    _ = action(participant, opponent)
    _ = println(s"$participant made action against $opponent")
  } yield ()

  def massAction(id: CreatureId, action: MassAction): Unit = for {
    participant <- findParticipantById(id)
    everyoneExceptParticipant = participants.filterNot(_.id.value == id.value)
    _ = action(participant, everyoneExceptParticipant)
    _ = println(s"$participant made mass action on $everyoneExceptParticipant")
  } yield ()

  def getInfo: String = {
    participants
      //TODO Add other cases with CreatureInFight(_, _, _: Mob/Hero)
      .map { case creature: Creature =>
        s"""
         |Creature Info:
         |Name - "${creature.lvl}" lvl  ${creature.attack} attack ${creature.hp} hp""".stripMargin
      }
      .mkString("\n")
  }
}

object Fight {

  def apply(id: FightId, p1: List[Creature], p2: List[Creature]): Fight = {
    val p1Team =
      p1.map(c => CreatureInFight(creature = c, fightId = nextId, team = 1))
    val p2Team =
      p2.map(c => CreatureInFight(creature = c, fightId = nextId, team = 2))
    val ids: Map[Int, Int] = p1Team
      .zip(p2Team)
      .flatMap { case (c1, c2) =>
        List((c1.fightId, c2.fightId), (c2.fightId, c1.fightId))
      }
      .toMap

    new Fight(id, participants = p1Team ++ p2Team, ids = ids)
  }

  private val nums: LazyList[Int] = 1 #:: nums.map(_ + 1)
  private val numsIterator = nums.iterator
  private def nextId: Int = numsIterator.next
}
