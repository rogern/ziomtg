name := "ZioMtg"

version := "0.1"

scalaVersion := "2.13.4"

val zioVersion = "1.0.13"

libraryDependencies ++= Seq(
  "dev.zio" %% "zio-streams" % zioVersion,
  "dev.zio" %% "zio-json" % "0.2.0-M3",
  "dev.zio" %% "zio-test" % zioVersion % Test,
  "dev.zio" %% "zio-test-sbt" % zioVersion % Test
)
testFrameworks += new TestFramework("zio.test.sbt.ZTestFramework")
