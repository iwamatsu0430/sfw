package jp.iwmat.sfw

import scalaz.\/

import jp.iwmat.sfw.server.Err
import jp.iwmat.sfw.server.err.serverErr.ThrowableWrapper

package object syntax {

  def tryE[T](t: => T): Err[Throwable] \/ T = {
    \/.fromTryCatchNonFatal(t).leftMap(ThrowableWrapper _)
  }
}
