package core.game.model

import core.game.creatures.Creature
import model.{Race, Status}

object Model {
  type Action = (CreatureInFight, CreatureInFight) => Unit
  type MassAction = (CreatureInFight, List[CreatureInFight]) => Unit

  case class FightId(value: Int) extends AnyVal
  case class CreatureId(value: Int) extends AnyVal

  case class CreatureInFight(fightId: Int, team: Int, creature: Creature)
      extends Creature {
    val id: CreatureId = creature.id
    val lvl: Int = creature.lvl
    val race: Race = creature.race
    var hp: Int = creature.hp
    var maxHp: Int = creature.maxHp
    var attack: Int = creature.attack
    var status: Status = creature.status
  }
}
