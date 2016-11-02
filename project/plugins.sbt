resolvers += Resolver.url(
  "tpolecat-sbt-plugin-releases",
  url("http://dl.bintray.com/content/tpolecat/sbt-plugin-releases") )(
  Resolver.ivyStylePatterns
)

addSbtPlugin("com.jsuereth" % "sbt-pgp" % "1.0.0")
addSbtPlugin("org.tpolecat" % "tut-plugin" % "0.4.6")
addSbtPlugin("org.scala-js" % "sbt-scalajs" % "0.6.13")

addSbtPlugin("io.get-coursier" % "sbt-coursier" % "1.0.0-M14-3")
