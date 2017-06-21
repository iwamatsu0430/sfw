package jp.iwmat.sfw.server

enum class Mode
object Mode {
  def values: Seq[Mode] = Seq(Test, Development, Production)
  def valueOf(value: String): Mode = value match {
    case "test" | "Test" => Test
    case "production" | "Production" | "prod" | "Prod" => Production
    case _ => Development
  }

  case Test
  case Development
  case Production
}
