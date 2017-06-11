package jp.iwmat.sfw.mvc

import scala.annotation.Annotation

sealed trait Route(path: String) extends Annotation
class OPTIONS(path: String) extends Route(path)
class GET(path: String) extends Route(path)
class HEAD(path: String) extends Route(path)
class POST(path: String) extends Route(path)
class PUT(path: String) extends Route(path)
class DELETE(path: String) extends Route(path)
class TRACE(path: String) extends Route(path)
class CONNECT(path: String) extends Route(path)
