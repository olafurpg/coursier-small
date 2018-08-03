package tests

import utest._
import com.geirsson.coursiersmall._

object DependencySuite extends TestSuite {
  val a = new Dependency("a", "a", "a")
  val b = new Dependency("b", "b", "b")
  val tests = Tests {
    * - assert(a != new Dependency("a", "a", "b"))
    * - assert(a != new Dependency("a", "b", "a"))
    * - assert(a != new Dependency("b", "a", "a"))
    * - assert(Set(a, a).toList == List(a))
    * - assert(Set(a, b).toList == List(a, b))
  }
}
