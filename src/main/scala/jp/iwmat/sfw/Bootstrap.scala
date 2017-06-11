package jp.iwmat.sfw

import jp.iwmat.sfw.server.SFWServer

object Bootstrap {
  def main(args: Array[String]): Unit = {
    SFWServer.start()
  }
}
