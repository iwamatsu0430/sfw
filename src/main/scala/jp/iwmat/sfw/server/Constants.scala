package jp.iwmat.sfw.server

object Constants {

  object classDir {
    val target = "target/"
    val prefix = "scala-"
    val classes = "classes"
  }

  object classFile {
    val extension = ".class"
  }

  object defaults {
    val port: Int = 9000

    object threadPools {
      val main: Int = 1
      val client: Int = 4
    }
  }

  object properties {
    val clientThreadPools: String = "sfw.clientThreadPools"
    val controllers: String = "sfw.controllers"
    val mode: String = "sfw.mode"
    val port: String = "sfw.port"
  }
}
