package xyz.hyperreal.polyline

import math._


object Polyline {

  // from https://developers.google.com/maps/documentation/utilities/polylinealgorithm
  // their example: [(38.5, -120.2), (40.7, -120.95), (43.252, -126.453)] ~> "_p~iF~ps|U_ulLnnqC_mqNvxq`@"

  val PRECISION = 100000D

  def encode( coords: List[(Double, Double)] ) = {
    def encode( coords: List[(Double, Double)], plat: Int, plong: Int, acc: String ): String =
      coords match {
        case Nil => acc
        case (lat, long) :: tail =>
          val (elat, nlat) = encodeValue( lat, plat )
          val (elong, nlong) = encodeValue( long, plong )

          encode( tail, nlat, nlong, acc + elat + elong )
      }

    encode( coords, 0, 0, "" )
  }

  def encodeValue( v: Double, prev: Int ) = {
    require( -180 <= v && v <= 180, s"coordinate value out of range: $v" )

    val c = round( v*PRECISION ).toInt  // steps 2 and 3
    var n = (c - prev) << 1             // step 4

    if (v < 0)                          // step 5
      n = ~n

    val buf = new StringBuilder

    do {
      var m = n&0x1F                    // step 6

      n >>>= 5                          // step 7 (going from right to left)

      if (n > 0)                        // step 8
        m |= 0x20

      m += 63                           // steps 9-10
      buf += m.toChar                   // step 11
    } while (n > 0)                     // until there are no more bits (variable length output)

    (buf.toString, c)
  }

  def decode( coords: String ) = {
    def decode( coords: List[Char], plat: Int, plong: Int, acc: List[(Double, Double)] ): List[(Double, Double)] =
      coords match {
        case Nil => acc
        case _ =>
          val (rem, nlat, lat) = decodeValue( coords, plat )
          val (rem1, nlong, long) = decodeValue( rem, plong )

          decode( rem1, nlat, nlong, acc :+ (lat, long) )
      }

    decode( coords.toList, 0, 0, Nil )
  }

  def decodeValue( chars: List[Char], prev: Int ) = {
    def decodeValue( chars: List[Char], acc: Int, shift: Int ): (List[Char], Int, Double) = {
      chars match {
        case Nil => sys.error( "unexpected end of character list" )
        case c :: tail =>
          var byte = c - 63

          if (byte >= 0x20)
            decodeValue( tail, acc | ((byte - 0x20) << shift), shift + 5 )
          else {
            val n = acc | (byte << shift)
            val m = (if ((n&1) > 0) ~n >> 1 else n >> 1) + prev

            (tail, m, m/PRECISION)
          }
      }
    }

    decodeValue( chars, 0, 0 )
  }

}