import net.rgsw.ptg.noise.Noise2D;
import net.rgsw.ptg.noise.opensimplex.FractalOpenSimplex2D;
import net.rgsw.ptg.noise.opensimplex.InverseFractalOpenSimplex2D;
import net.rgsw.ptg.noise.opensimplex.OpenSimplex2D;
import net.rgsw.ptg.noise.perlin.FractalPerlin2D;
import net.rgsw.ptg.noise.perlin.InverseFractalPerlin2D;
import net.rgsw.ptg.noise.perlin.Perlin2D;
import net.rgsw.ptg.noise.perlin.RepetitivePerlin2D;
import net.rgsw.ptg.noise.util.NoiseMath;
import net.rgsw.ptg.region.CachingRegionContext;
import net.rgsw.ptg.region.Region;
import net.rgsw.ptg.region.RegionContext;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public final class NoiseExport {
    private NoiseExport() {
    }

    public static void main( String[] args ) {
        int seed = 616249123;
        exportNoise( new File( "exports/perlin.png" ), 256, 256, new Perlin2D( seed ), 16 );
        exportNoise( new File( "exports/opensimplex.png" ), 256, 256, new OpenSimplex2D( seed ), 16 );
        exportNoise( new File( "exports/fractalperlin.png" ), 256, 256, new FractalPerlin2D( seed, 8 ), 16 );
        exportNoise( new File( "exports/fractalopensimplex.png" ), 256, 256, new FractalOpenSimplex2D( seed, 8 ), 16 );
        exportNoise( new File( "exports/inversefractalperlin.png" ), 256, 256, new InverseFractalPerlin2D( seed, 8 ), 16 );
        exportNoise( new File( "exports/inversefractalopensimplex.png" ), 256, 256, new InverseFractalOpenSimplex2D( seed, 8 ), 16 );
        exportNoise( new File( "exports/repetitiveperlin.png" ), 256, 256, new RepetitivePerlin2D( seed, 8 ), 16 );
        exportNoise( new File( "exports/random.png" ), 256, 256, Noise2D.random( seed ), 16 );

        RegionContext<?> ctx = new CachingRegionContext( 25, 55122121599L );
        Region reg = ctx.randomF( - 1, 1, 3417L ).zoomFuzzy( 2 ).zoom( 3 ).smooth().buildRegion();
        exportNoise( new File( "exports/region.png" ), 256, 256, Noise2D.region( reg ), 1 );
    }

    public static void exportNoise( File file, int width, int height, Noise2D noise, double scale ) {
        System.out.println( "Exporting: " + file );

        file.getParentFile().mkdirs();

        BufferedImage image = new BufferedImage( width, height, BufferedImage.TYPE_INT_ARGB );
        for( int x = 0; x < width; x++ ) {
            for( int y = 0; y < height; y++ ) {
                double n = NoiseMath.unlerp( - 1, 1, noise.generate( x / scale, y / scale ) );
                int v = (int) NoiseMath.clamp( 0, 255, n * 255 );
                int col = 0xFF000000 | v << 16 | v << 8 | v;
                image.setRGB( x, y, col );
            }
        }

        try {
            ImageIO.write( image, "PNG", file );
        } catch( IOException exc ) {
            throw new RuntimeException( exc );
        }
    }
}
