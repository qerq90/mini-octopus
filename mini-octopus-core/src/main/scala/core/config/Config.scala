package core.config

import com.typesafe.config
import com.typesafe.config.ConfigFactory
import izumi.reflect.Tag
import zio.{Has, ULayer}
import zio.config.magnolia.{descriptor, Descriptor}
import zio.config.typesafe.TypesafeConfig

class Config[T: Descriptor: Tag] {
  val automaticDescription = descriptor[T]

  // local > application
  val resources = List(
    "application.local.conf",
    "application.conf"
  )

  def getConfig: ULayer[Has[T]] = TypesafeConfig
    .fromTypesafeConfig(
      parseConfig,
      automaticDescription
    )
    .orDie

  def parseConfig: config.Config = {
    resources.foldLeft(ConfigFactory.empty) { (config, resource) =>
      config.withFallback(ConfigFactory.parseResources(resource))
    }
  }

}

object Config {

  def makeConfig[T: Descriptor: Tag]: ULayer[Has[T]] =
    new Config[T].getConfig
}
