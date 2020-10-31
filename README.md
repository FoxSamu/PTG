# PTG - A minimal procedural generation library
This project introduces utilities for the generation of random terrains. It includes:
- Noise generators in 2D and 3D
  - Perlin and OpenSimplex noise
  - Fractal variants of both Perlin and OpenSimplex noise
  - Repetitive Perlin noise (OpenSimplex coming soon)
- Region generators: they generate random shapes for biomes and other region-specific values, based on the layered biome generator system of Minecraft.
- High performance!

## Install using gradle
For installing via gradle, you need to add the ModMaven repository. I'll soon be adding it to Maven Central.
```groovy
repositories {
    mavenCentral()
    maven {
        url uri( "http://maven.shadew.net/" )
    }
}

dependencies {
    compile 'net.shadew:ptg:1.2.1'
}
```
The latest version depends on the Fastutil library, version 8.2.1. This dependency is automatically included with the PTG library.

## License
This project is licensed under the Apache License 2.0. See `LICENSE`.
