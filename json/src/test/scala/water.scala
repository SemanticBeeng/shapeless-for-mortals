// Copyright (C) 2015 - 2016 Sam Halliday
// License: http://www.apache.org/licenses/LICENSE-2.0
package s4m.brucelee

import Predef.{ any2stringadd => _, _ }

import org.scalatest._
import s4m.json._
import spray.json._

package api {
  sealed trait Receptacle
  case class Glass(a: String) extends Receptacle
  case class Bottle(a: Int) extends Receptacle
  case class Teapot(a: Boolean) extends Receptacle
}

package formats {
  import api._
  object MyFormats {
    import FamilyFormats._
    implicit val ReceptacleF = JsonFormat[Receptacle]
  }
}

package app {
  class DemoSpec extends FlatSpec with Matchers {
    import api._
    import formats.MyFormats._

    "Receptable" should "be magically marshalled" in {
      (Glass("foo"): Receptacle).toJson shouldBe """{ "type":"Glass", "a":"foo" }""".parseJson
      (Bottle(99): Receptacle).toJson shouldBe """{ "type":"Bottle", "a":99 }""".parseJson
      (Teapot(true): Receptacle).toJson shouldBe """{ "type":"Teapot", "a":true }""".parseJson
    }

  }
}
