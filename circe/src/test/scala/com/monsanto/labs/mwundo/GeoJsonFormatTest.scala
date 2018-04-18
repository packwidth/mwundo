package com.monsanto.labs.mwundo

import com.monsanto.labs.mwundo.GeoJson._
import com.monsanto.labs.mwundo.GeoJsonFormats._
import org.scalatest.{FunSpec, Matchers, ParallelTestExecution}
import io.circe._
import io.circe.syntax._

class GeoJsonFormatTest extends FunSpec with Matchers with ParallelTestExecution {

  private def marshalAndUnmarshal[T](t: T)(implicit encoder: Encoder[T], decoder: Decoder[T]) = {
    val json = t.asJson
    val result = json.as[T] match {
      case Right(res) => res
      case Left(x) => fail(x.message)
    }

    println(json.toString())
    result should be (t)
    json.toString() should be (result.asJson.toString())
  }

  describe("Circe GeoJson serialization") {
    it("should marshal and unmarshal Coordinate") {
      val x = Coordinate(0.1, 2.0)
      marshalAndUnmarshal(x)
    }

    it("should marshal and unmarshal Point") {
      val x = Point(Coordinate(0.1, 2.0))
      marshalAndUnmarshal(x)
    }

    it("should marshal and unmarshal MultiPoint") {
      val x = MultiPoint(Seq(Coordinate(0.1, 2.0), Coordinate(1.1, 2.1)))
      marshalAndUnmarshal(x)
    }

    it("should marshal and unmarshal LineString") {
      val x = LineString(Seq(Coordinate(0.1, 2.0), Coordinate(1.1, 2.1)))
      marshalAndUnmarshal(x)
    }

    it("should marshal and unmarshal MultiLineString") {
      val x = MultiLineString(Seq(
        Seq(Coordinate(0.1, 2.0), Coordinate(1.1, 2.1)),
        Seq(Coordinate(0.1, 2.0), Coordinate(1.1, 2.1))
      ))
      marshalAndUnmarshal(x)
    }

    it("should marshal and unmarshal Polygon") {
      val x = Polygon(Seq(
        Seq(Coordinate(0.1, 2.0), Coordinate(1.1, 2.1)),
        Seq(Coordinate(0.1, 2.0), Coordinate(1.1, 2.1))
      ))
      marshalAndUnmarshal(x)
    }

    it("should marshal and unmarshal MultiPolygon") {
      val x = MultiPolygon(Seq(
        Seq(
          Seq(Coordinate(0.1, 2.0), Coordinate(1.1, 2.1)),
          Seq(Coordinate(0.1, 2.0), Coordinate(1.1, 2.1))),
        Seq(
          Seq(Coordinate(0.1, 2.0), Coordinate(1.1, 2.1)),
          Seq(Coordinate(0.1, 2.0), Coordinate(1.1, 2.1)))
      ))
      marshalAndUnmarshal(x)
    }
  }
}

