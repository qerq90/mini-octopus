import sbt._

object Versions {
	val enumeratumVersion = "1.7.0"
	val zioVersion = "1.0.9"
    val sttpVersion = "3.4.1"
}

object Dependencies {
	import Versions._

	val enumeratum = "com.beachape" %% "enumeratum" % enumeratumVersion

    val zio = "dev.zio" %% "zio" % zioVersion
	val sttp = "com.softwaremill.sttp.client3" %% "async-http-client-backend-zio1" % sttpVersion
    val configZIO = "dev.zio" %% "zio-config" % zioVersion
	val typesafeConfigZIO = "dev.zio" %% "zio-config-typesafe" % zioVersion
	val magnoliaConfigZIO = "dev.zio" %% "zio-config-magnolia" % zioVersion
}