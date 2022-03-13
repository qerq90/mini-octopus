import sbt._

object Versions {
  val enumeratumVersion = "1.7.0"
  val zioVersion = "1.0.9"
  val zioJsonVersion = "0.1.5"
  val zioMagicVersion = "0.3.11"
  val ZHTTPVersion = "1.0.0.0-RC24"
  val doobieVersion = "0.13.4"
  val zioInteropCatsVersion = "2.1.4.0"

  val sttpVersion = "3.4.1"
  val scalaTestVersion = "3.0.8"
}

object Dependencies {
  import Versions._

  val enumeratum = "com.beachape" %% "enumeratum" % enumeratumVersion

  val zio = "dev.zio" %% "zio" % zioVersion
  val zioMagic = "io.github.kitlangton" %% "zio-magic" % zioMagicVersion
  val ZHTTP = "io.d11" %% "zhttp" % ZHTTPVersion

  val sttp =
    "com.softwaremill.sttp.client3" %% "async-http-client-backend-zio1" % sttpVersion
  val jsonZIO = "dev.zio" %% "zio-json" % zioJsonVersion
  val magnoliaConfigZIO = "dev.zio" %% "zio-config-magnolia" % zioVersion
  val configZIO = "dev.zio" %% "zio-config" % zioVersion
  val typesafeConfigZIO = "dev.zio" %% "zio-config-typesafe" % zioVersion

  val doobieCore = "org.tpolecat" %% "doobie-core" % doobieVersion
  val doobiePostgress = "org.tpolecat" %% "doobie-postgres" % doobieVersion
  val doobieHikari = "org.tpolecat" %% "doobie-hikari" % doobieVersion
  val zioInteropCats = "dev.zio" %% "zio-interop-cats" % zioInteropCatsVersion

  val scalaTest = "org.scalatest" %% "scalatest" % scalaTestVersion
}
