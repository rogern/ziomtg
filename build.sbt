name := "ZioMtg"

version := "0.1"

scalaVersion := "2.13.4"

val zioVersion       = "1.0.13"
val zioConfigVersion = "2.0.3"

libraryDependencies ++= Seq(
  "dev.zio" %% "zio-streams"         % zioVersion,
  "dev.zio" %% "zio-config"          % zioConfigVersion,
  "dev.zio" %% "zio-config"          % zioConfigVersion,
  "dev.zio" %% "zio-config-magnolia" % zioConfigVersion,
  "dev.zio" %% "zio-config-typesafe" % zioConfigVersion,
  "dev.zio" %% "zio-json"            % "0.2.0-M3",
  "dev.zio" %% "zio-test"            % zioVersion % Test,
  "dev.zio" %% "zio-test-sbt"        % zioVersion % Test
)
testFrameworks += new TestFramework("zio.test.sbt.ZTestFramework")
