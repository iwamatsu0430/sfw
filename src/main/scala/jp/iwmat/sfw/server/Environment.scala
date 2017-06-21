package jp.iwmat.sfw.server

import ciris._

case class Environment(
  mode: Mode,
  port: Int
)

object Environment {

  def load(): Environment = {
    loadConfig(
      prop[String](Constants.properties.mode),
      prop[Int](Constants.properties.port)
    ) {
      (modeStr, port) =>
        Environment(
          Mode.valueOf(modeStr),
          port
        )
    }.right.getOrElse(
      Environment(
        Mode.Development,
        Constants.defaults.port
      )
    )
  }
}
