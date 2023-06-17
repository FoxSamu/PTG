# PTG - A minimal procedural generation library
This project provides utilities for the generation of random terrains. It includes:
- A collection of noise generators in 2D and 3D
  - Perlin noise
  - Simplex noise
  - OpenSimplex noise
  - Fractal variants of both Perlin and OpenSimplex noise
  - Simple value-based noises
- Region generators in 2D: they generate random shapes for biomes and other region-specific values, based on the legacy layered biome generator system of Minecraft (before 1.18).
- High performance!

## Install using Gradle
For installing via Gradle, you need to add my Maven repository to your repositories.
```groovy
repositories {
    mavenCentral()
    maven {
        url "https://maven.shadew.net/"
    }
}

dependencies {
    compile 'dev.runefox:ptg:3.0'
}
```
The latest version depends on the Fastutil library, version 8.2.1. This dependency is automatically included with the PTG library, but this requires that the Maven Central Repository is declared as a repository in your buildscript.

## Experimental features
There are several experimental features implemented. These features may not work as expected or may be slow. Use cautiously. Such features are:
- Voronoi, Worley and Cell noise (all based on Voronoi); these can be excessively slow especially in higher dimensions.
- Repetitive noise; seamlessly repeating noise, but can look odd and is not available for Simplex and OpenSimplex noise.

## Changelog
### 3.0
- Group ID and root package name are now `dev.runefox` instead of `net.shadew`.
- Added original Simplex noise.
- Version number no longer includes patch number if it's 0.

Previous versions were not tracked.

## License
This project is licensed under the Apache License 2.0. See `LICENSE`.
