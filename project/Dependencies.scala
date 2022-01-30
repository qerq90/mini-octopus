import sbt._

object Versions {
	val enumeratumVersion = "1.7.0"
}

object Dependencies {
	import Versions._
	
	val enumeratum = "com.beachape" %% "enumeratum" % enumeratumVersion
}