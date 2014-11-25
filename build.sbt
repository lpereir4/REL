organization := "fr.splayce"

name := "REL"

version := "0.3.2-SNAPSHOT"

scalaVersion := "2.11.4"

crossScalaVersions := Seq("2.9.3", "2.10.4")

libraryDependencies <<= (scalaVersion, libraryDependencies) { (sv, deps) =>
	deps :+ (sv match {
	    case "2.11.4" => "org.specs2" %% "specs2" % "2.3.13" % "test"
		case "2.10.4" => "org.specs2" %% "specs2" % "2.3.13" % "test"
		case _        => "org.specs2" %% "specs2" % "1.12.4.1" % "test"
	})
}

scalacOptions <<= scalaVersion map { v: String =>
  val default = Seq("-deprecation", "-unchecked", "-encoding", "UTF8")
  if (v.startsWith("2.9."))
    default
  else
    default ++ Seq("-feature", "-language:postfixOps", "-language:implicitConversions")
}

publishTo <<= version { (v: String) =>
  val url = "http://integration.imaginatio.fr:2000/nexus/content/repositories/"
  val realm = "Sonatype Nexus Repository Manager"
  if (v.trim.endsWith("SNAPSHOT"))
    Some(realm + "releases" at url + "snapshots")
  else
    Some(realm + "snapshots" at url + "releases")
}

publishArtifact in Test := false

publishMavenStyle := true

pomIncludeRepository := { _ => false }

credentials += Credentials(Path.userHome / ".ivy2" / ".nexus.credentials")

unmanagedClasspath in Compile += Attributed.blank(new java.io.File("doesnotexist"))
