package core.creatures

import model.Status

trait Creature {
  var hp: Int
  var maxHp: Int
  var attack: Int
  var state: Status
}