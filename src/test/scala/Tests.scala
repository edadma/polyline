package xyz.hyperreal.polyline

import org.scalatest._
import prop.PropertyChecks


class Tests extends FreeSpec with PropertyChecks with Matchers {
	
	"example" in {
		val example = List((38.5, -120.2), (40.7, -120.95), (43.252, -126.453))

		decode( encode(example) ) shouldBe example
	}
	
}