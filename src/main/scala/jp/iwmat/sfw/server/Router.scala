package jp.iwmat.sfw.server

import java.io.File
import java.net.URLClassLoader

import collection.JavaConversions._

import com.typesafe.config.ConfigFactory
import scalaz.\/

import jp.iwmat.sfw.mvc._
import jp.iwmat.sfw.http.HttpMethod

case class Router(actions: Seq[ActionMethod]) {

  def find(path: String): Option[Action] = {
    ???
  }

}

object Router {

  def load(): Err[_] \/ Router = {
    load(Constants.classDir.target, Constants.properties.controllers)
  }

  def load(targetDir: String, controllersKey: String): Err[_] \/ Router = {
    val classPaths = createClassPaths(targetDir)
    val classLoader = createClassLoader(classPaths)
    val actions = loadControllerPackages(controllersKey).flatMap { classPath =>
      val cls = classLoader.loadClass(classPath)
      cls.getDeclaredMethods
        .filter(_.getDeclaredAnnotations.length > 0)
        .flatMap { method =>
          val a: Option[Err[_] \/ ActionMethod] = method.getDeclaredAnnotations
            .collect {
              case a: OPTIONS => (HttpMethod.OPTIONS, a.path)
              case a: HEAD => (HttpMethod.HEAD, a.path)
              case a: GET => (HttpMethod.GET, a.path)
              case a: POST => (HttpMethod.POST, a.path)
              case a: PUT => (HttpMethod.PUT, a.path)
              case a: DELETE => (HttpMethod.DELETE, a.path)
              case a: TRACE => (HttpMethod.TRACE, a.path)
              case a: CONNECT => (HttpMethod.CONNECT, a.path)
              case a: PATCH => (HttpMethod.PATCH, a.path)
            }
            .headOption
            .map { (httpMethod, path) =>
              ActionMethod.of(cls, method, httpMethod, path)
            }
          val b: Seq[Err[_] \/ ActionMethod] = Seq() // a.toSeq
          b
      }

      ???
    }
    // Router(actions)
    ???
  }

  def createClassPaths(targetDir: String): Seq[File] = {
    new File(targetDir)
      .listFiles
      .withFilter(_.getName.startsWith(Constants.classDir.prefix))
      .map(new File(_, Constants.classDir.classes))
  }

  def createClassLoader(classPaths: Seq[File]): URLClassLoader = {
    new URLClassLoader(
      classPaths.map(_.toURI.toURL).toArray,
      this.getClass.getClassLoader
    )
  }

  def loadControllerPackages(key: String): List[String] = {
    ConfigFactory.load()
      .getStringList(key)
      .toList
  }
}
