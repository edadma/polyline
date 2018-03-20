polyline
========

*polyline* is an implementation of the Google Maps path encoding/decoding algorithm in Scala.

Example
-------

This example is from https://developers.google.com/maps/documentation/utilities/polylinealgorithm.

type the following import to use the functions:

    import xyz.hyperreal.polyline._

### Encoding

To encode the path (38.5, -120.2), (40.7, -120.95), (43.252, -126.453), type

    encode( List((38.5, -120.2), (40.7, -120.95), (43.252, -126.453)) )

which returns the encoded path string "_p~iF~ps|U_ulLnnqC_mqNvxq`@".

### Decoding

To decode the above encoded path, type

	decode( "_p~iF~ps|U_ulLnnqC_mqNvxq`@" )

which returns the original path list.

License
-------

*polyline* is distributed under the ISC License, meaning that you are free to use it in your free or proprietary software.

Usage
-----

Add the following to your `build.sbt` file to use *Energize* in your SBT project:

    resolvers += "Hyperreal Repository" at "https://dl.bintray.com/edadma/maven"

    libraryDependencies += "xyz.hyperreal" %% "polyline" % "0.1"

