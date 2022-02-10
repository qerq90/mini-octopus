package core.config

import com.typesafe.config.ConfigFactory
import izumi.reflect.Tag
import zio.{Has, ULayer}
import zio.config.magnolia.{Descriptor, descriptor}
import zio.config.typesafe.TypesafeConfig

class Config[T : Descriptor : Tag] {
  val automaticDescription = descriptor[T]

  def getConfig: ULayer[Has[T]] = TypesafeConfig.fromTypesafeConfig(
    ConfigFactory.parseResources("resources/application.conf"),
    automaticDescription
  ).orDie
}

object Config {
  def makeConfig[T : Descriptor : Tag]: ULayer[Has[T]] =
    new Config[T].getConfig
}
