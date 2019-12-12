# Noise
A basic noise generator library written for the [Modernity project](https://github.com/RedGalaxyDev/TheModernity). Can be used
in any other mod project that needs noise generators. Can also be used for other purposes than Minecraft.

## Features
- Perlin Noise (`Perlin2D` and `Perlin3D`)
- Open Simplex Noise (`OpenSimplex2D` and `OpenSimplex3D`)
- Fractal Perlin Noise (`FractalPerlin2D` and `FractalPerlin3D`)
- Fractal Open Simplex Noise (`FractalOpenSimplex2D` and `FractalOpenSimplex3D`)
- Inverse-Fractal Perlin Noise (`InverseFractalPerlin2D` and `InverseFractalPerlin3D`)
- Inverse-Fractal Open Simplex Noise (`InverseFractalOpenSimplex2D` and `InverseFractalOpenSimplex3D`)
- General noise classes for custom implementations (`Noise2D` and `Noise3D`)
- General noise interfaces with methods to combine, transform and modify noise fields (`INoise2D` and `INoise3D`)
- Hash functions for seed-coordinate pairs in 1D, 2D, 3D and 4D
- High performance!

The Inverse-Fractal generators generate by scaling up the fractal multiplier, instead of scaling down. This is how the noise
generators of vanilla Minecraft work.

## Install using gradle
For installing via gradle, you need a GitHub account with a personal access token. Personal access tokens can be obtained
at `User Settings -> Developer settings -> Personal Access Tokens`. The permission `read:packages` must be granted to your
token to be able to download the package.

```groovy
repositories {
    maven {
        url uri( "https://maven.pkg.github.com/RedGalaxySW/Noise" )
        credentials {
            // Make sure you have these properties defined in your private 'gradle.properties' file
            username = gprUser.toLowerCase() // Your github username
            password = gprKey.toLowerCase()  // Your access token key (not your password)
        }
    }
}
```
