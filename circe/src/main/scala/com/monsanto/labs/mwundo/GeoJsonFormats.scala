package com.monsanto.labs.mwundo

import com.monsanto.labs.mwundo.GeoJson._
import io.circe._
import io.circe.generic.semiauto._

object GeoJsonFormats {
  implicit val coordinateDecoder: Decoder[Coordinate] = (c: HCursor) => {
    c.as[Array[BigDecimal]] match {
      case Right(coords) if coords.length == 2 => Right(Coordinate(coords(0), coords(1)))
      case Right(_) => Left(DecodingFailure("Must have length of 2 coordinates.", c.history))
      case _ => Left(DecodingFailure("Decoding error.", c.history))
    }
  }

  implicit val coordinateEncoder: Encoder[Coordinate] = (a: Coordinate) => {
    Json.arr(Json.fromBigDecimal(a.x), Json.fromBigDecimal(a.y))
  }

  implicit val pointDecoder: Decoder[Point] = deriveDecoder[Point]
  implicit val pointEncoder: Encoder[Point] = deriveEncoder[Point]

  implicit val multiPointDecoder: Decoder[MultiPoint] = deriveDecoder[MultiPoint]
  implicit val multiPointEncoder: Encoder[MultiPoint] = deriveEncoder[MultiPoint]

  implicit val lineStringDecoder: Decoder[LineString] = deriveDecoder[LineString]
  implicit val lineStringEncoder: Encoder[LineString] = deriveEncoder[LineString]

  implicit val multiLineStringDecoder: Decoder[MultiLineString] = deriveDecoder[MultiLineString]
  implicit val multiLineStringEncoder: Encoder[MultiLineString] = deriveEncoder[MultiLineString]

  implicit val polygonDecoder: Decoder[Polygon] = deriveDecoder[Polygon]
  implicit val polygonEncoder: Encoder[Polygon] = deriveEncoder[Polygon]

  implicit val multiPolygonDecoder: Decoder[MultiPolygon] = deriveDecoder[MultiPolygon]
  implicit val multiPolygonEncoder: Encoder[MultiPolygon] = deriveEncoder[MultiPolygon]

  //implicit val geometryCollectionDecoder: Decoder[GeometryCollection] = deriveDecoder[GeometryCollection]
  //implicit val geometryCollectionEncoder: Encoder[GeometryCollection] = deriveEncoder[GeometryCollection]

}
