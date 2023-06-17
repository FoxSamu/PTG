/*
 * Copyright (c) 2023 Runefox Dev
 * Licensed under Apache 2.0 license
 */

import dev.runefox.ptg.noise.Noise2D;
import dev.runefox.ptg.noise.cell.*;
import dev.runefox.ptg.noise.discrete.*;
import dev.runefox.ptg.noise.opensimplex.*;
import dev.runefox.ptg.noise.perlin.*;
import dev.runefox.ptg.noise.simplex.*;
import dev.runefox.ptg.noise.util.NoiseMath;
import dev.runefox.ptg.noise.value.*;
import dev.runefox.ptg.noise.voronoi.*;
import dev.runefox.ptg.noise.worley.*;
import dev.runefox.ptg.region.LazyRegionContext;
import dev.runefox.ptg.region.Region;
import dev.runefox.ptg.region.RegionBuilder;
import dev.runefox.ptg.region.RegionContext;
import dev.runefox.ptg.region.layer.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public final class NoiseExport {
    private NoiseExport() {
    }

    public static void main(String[] args) {
        int seed = 617259123;
        exportNoise(new File("exports/perlin.png"), 256, 256, new Perlin2D(seed), 16);
        exportNoise(new File("exports/value.png"), 256, 256, new Value2D(seed), 16);
        exportNoise(new File("exports/discrete.png"), 256, 256, new Discrete2D(seed), 16);
        exportNoise(new File("exports/simplex.png"), 256, 256, new Simplex2D(seed), 16);
        exportNoise(new File("exports/opensimplex.png"), 256, 256, new OpenSimplex2D(seed), 16);
        exportNoise(new File("exports/worley.png"), 256, 256, new Worley2D(seed), 16);
        exportNoise(new File("exports/voronoi.png"), 256, 256, new Voronoi2D(seed), 16);
        exportNoise(new File("exports/cell.png"), 256, 256, new Cell2D(seed), 16);
        exportNoise(new File("exports/fractalperlin.png"), 256, 256, new FractalPerlin2D(seed, 8), 16);
        exportNoise(new File("exports/fractalvalue.png"), 256, 256, new FractalValue2D(seed, 8), 16);
        exportNoise(new File("exports/fractaldiscrete.png"), 256, 256, new FractalDiscrete2D(seed, 8), 16);
        exportNoise(new File("exports/fractalsimplex.png"), 256, 256, new FractalSimplex2D(seed, 8), 16);
        exportNoise(new File("exports/fractalopensimplex.png"), 256, 256, new FractalOpenSimplex2D(seed, 8), 16);
        exportNoise(new File("exports/fractalworley.png"), 256, 256, new FractalWorley2D(seed, 8), 16);
        exportNoise(new File("exports/fractalvoronoi.png"), 256, 256, new FractalVoronoi2D(seed, 8), 16);
        exportNoise(new File("exports/fractalcell.png"), 256, 256, new FractalCell2D(seed, 8), 16);
        exportNoise(new File("exports/inversefractalperlin.png"), 256, 256, new InverseFractalPerlin2D(seed, 8), 1);
        exportNoise(new File("exports/inversefractalvalue.png"), 256, 256, new InverseFractalValue2D(seed, 8), 1);
        exportNoise(new File("exports/inversefractaldiscrete.png"), 256, 256, new InverseFractalDiscrete2D(seed, 8), 1);
        exportNoise(new File("exports/inversefractalsimplex.png"), 256, 256, new InverseFractalSimplex2D(seed, 8), 1);
        exportNoise(new File("exports/inversefractalopensimplex.png"), 256, 256, new InverseFractalOpenSimplex2D(seed, 8), 1);
        exportNoise(new File("exports/inversefractalworley.png"), 256, 256, new InverseFractalWorley2D(seed, 8), 1);
        exportNoise(new File("exports/inversefractalvoronoi.png"), 256, 256, new InverseFractalVoronoi2D(seed, 8), 1);
        exportNoise(new File("exports/inversefractalcell.png"), 256, 256, new InverseFractalCell2D(seed, 8), 1);
        exportNoise(new File("exports/repetitiveperlin.png"), 256, 256, new RepetitivePerlin2D(seed, 8), 16);
        exportNoise(new File("exports/repetitivevalue.png"), 256, 256, new RepetitiveValue2D(seed, 8), 16);
        exportNoise(new File("exports/repetitivediscrete.png"), 256, 256, new RepetitiveDiscrete2D(seed, 8), 16);
        exportNoise(new File("exports/repetitiveworley.png"), 256, 256, new RepetitiveWorley2D(seed, 8), 16);
        exportNoise(new File("exports/repetitivevoronoi.png"), 256, 256, new RepetitiveVoronoi2D(seed, 8), 16);
        exportNoise(new File("exports/repetitivecell.png"), 256, 256, new RepetitiveCell2D(seed, 8), 16);
        exportNoise(new File("exports/repetitivefractalperlin.png"), 256, 256, new RepetitiveFractalPerlin2D(seed, 8, 8), 16);
        exportNoise(new File("exports/repetitivefractalvalue.png"), 256, 256, new RepetitiveFractalValue2D(seed, 8, 8), 16);
        exportNoise(new File("exports/repetitivefractaldiscrete.png"), 256, 256, new RepetitiveFractalDiscrete2D(seed, 8, 8), 16);
        exportNoise(new File("exports/repetitivefractalworley.png"), 256, 256, new RepetitiveFractalWorley2D(seed, 8, 8), 16);
        exportNoise(new File("exports/repetitivefractalvoronoi.png"), 256, 256, new RepetitiveFractalVoronoi2D(seed, 8, 8), 16);
        exportNoise(new File("exports/repetitivefractalcell.png"), 256, 256, new RepetitiveFractalCell2D(seed, 8, 8), 16);

        exportNoise(new File("exports/3d/perlin.png"), 256, 256, Noise2D.from3D(new Perlin3D(seed), 0.2), 16);
        exportNoise(new File("exports/3d/value.png"), 256, 256, Noise2D.from3D(new Value3D(seed), 0.2), 16);
        exportNoise(new File("exports/3d/discrete.png"), 256, 256, Noise2D.from3D(new Discrete3D(seed), 0.2), 16);
        exportNoise(new File("exports/3d/simplex.png"), 256, 256, Noise2D.from3D(new Simplex3D(seed), 0.2), 16);
        exportNoise(new File("exports/3d/opensimplex.png"), 256, 256, Noise2D.from3D(new OpenSimplex3D(seed), 0.2), 16);
        exportNoise(new File("exports/3d/worley.png"), 256, 256, Noise2D.from3D(new Worley3D(seed), 0.2), 16);
        exportNoise(new File("exports/3d/voronoi.png"), 256, 256, Noise2D.from3D(new Voronoi3D(seed), 0.2), 16);
        exportNoise(new File("exports/3d/cell.png"), 256, 256, Noise2D.from3D(new Cell3D(seed), 0.2), 16);
        exportNoise(new File("exports/3d/fractalperlin.png"), 256, 256, Noise2D.from3D(new FractalPerlin3D(seed, 8), 0.2), 16);
        exportNoise(new File("exports/3d/fractalvalue.png"), 256, 256, Noise2D.from3D(new FractalValue3D(seed, 8), 0.2), 16);
        exportNoise(new File("exports/3d/fractaldiscrete.png"), 256, 256, Noise2D.from3D(new FractalDiscrete3D(seed, 8), 0.2), 16);
        exportNoise(new File("exports/3d/fractalsimplex.png"), 256, 256, Noise2D.from3D(new FractalSimplex3D(seed, 8), 0.2), 16);
        exportNoise(new File("exports/3d/fractalopensimplex.png"), 256, 256, Noise2D.from3D(new FractalOpenSimplex3D(seed, 8), 0.2), 16);
        exportNoise(new File("exports/3d/fractalworley.png"), 256, 256, Noise2D.from3D(new FractalWorley3D(seed, 8), 0.2), 16);
        exportNoise(new File("exports/3d/fractalvoronoi.png"), 256, 256, Noise2D.from3D(new FractalVoronoi3D(seed, 8), 0.2), 16);
        exportNoise(new File("exports/3d/fractalcell.png"), 256, 256, Noise2D.from3D(new FractalCell3D(seed, 8), 0.2), 16);
        exportNoise(new File("exports/3d/inversefractalperlin.png"), 256, 256, Noise2D.from3D(new InverseFractalPerlin3D(seed, 8), 0.2), 1);
        exportNoise(new File("exports/3d/inversefractalvalue.png"), 256, 256, Noise2D.from3D(new InverseFractalValue3D(seed, 8), 0.2), 1);
        exportNoise(new File("exports/3d/inversefractaldiscrete.png"), 256, 256, Noise2D.from3D(new InverseFractalDiscrete3D(seed, 8), 0.2), 1);
        exportNoise(new File("exports/3d/inversefractalsimplex.png"), 256, 256, Noise2D.from3D(new InverseFractalSimplex3D(seed, 8), 0.2), 1);
        exportNoise(new File("exports/3d/inversefractalopensimplex.png"), 256, 256, Noise2D.from3D(new InverseFractalOpenSimplex3D(seed, 8), 0.2), 1);
        exportNoise(new File("exports/3d/inversefractalworley.png"), 256, 256, Noise2D.from3D(new InverseFractalWorley3D(seed, 8), 0.2), 1);
        exportNoise(new File("exports/3d/inversefractalvoronoi.png"), 256, 256, Noise2D.from3D(new InverseFractalVoronoi3D(seed, 8), 0.2), 1);
        exportNoise(new File("exports/3d/inversefractalcell.png"), 256, 256, Noise2D.from3D(new InverseFractalCell3D(seed, 8), 0.2), 1);
        exportNoise(new File("exports/3d/repetitiveperlin.png"), 256, 256, Noise2D.from3D(new RepetitivePerlin3D(seed, 8), 0.2), 16);
        exportNoise(new File("exports/3d/repetitivevalue.png"), 256, 256, Noise2D.from3D(new RepetitiveValue3D(seed, 8), 0.2), 16);
        exportNoise(new File("exports/3d/repetitivediscrete.png"), 256, 256, Noise2D.from3D(new RepetitiveDiscrete3D(seed, 8), 0.2), 16);
        exportNoise(new File("exports/3d/repetitiveworley.png"), 256, 256, Noise2D.from3D(new RepetitiveWorley3D(seed, 8), 0.2), 16);
        exportNoise(new File("exports/3d/repetitivevoronoi.png"), 256, 256, Noise2D.from3D(new RepetitiveVoronoi3D(seed, 8), 0.2), 16);
        exportNoise(new File("exports/3d/repetitivecell.png"), 256, 256, Noise2D.from3D(new RepetitiveCell3D(seed, 8), 0.2), 16);
        exportNoise(new File("exports/3d/repetitivefractalperlin.png"), 256, 256, Noise2D.from3D(new RepetitiveFractalPerlin3D(seed, 8, 8), 0.2), 16);
        exportNoise(new File("exports/3d/repetitivefractalvalue.png"), 256, 256, Noise2D.from3D(new RepetitiveFractalValue3D(seed, 8, 8), 0.2), 16);
        exportNoise(new File("exports/3d/repetitivefractaldiscrete.png"), 256, 256, Noise2D.from3D(new RepetitiveFractalDiscrete3D(seed, 8, 8), 0.2), 16);
        exportNoise(new File("exports/3d/repetitivefractalworley.png"), 256, 256, Noise2D.from3D(new RepetitiveFractalWorley3D(seed, 8, 8), 0.2), 16);
        exportNoise(new File("exports/3d/repetitivefractalvoronoi.png"), 256, 256, Noise2D.from3D(new RepetitiveFractalVoronoi3D(seed, 8, 8), 0.2), 16);
        exportNoise(new File("exports/3d/repetitivefractalcell.png"), 256, 256, Noise2D.from3D(new RepetitiveFractalCell3D(seed, 8, 8), 0.2), 16);

        exportNoise(new File("exports/random.png"), 256, 256, Noise2D.random(seed), 16);

        RegionContext<?> ctx = new LazyRegionContext(25, 55122121599L);
        Region reg = ctx.randomF(-1, 1, 3417L)
                        .zoomFuzzy(2)
                        .zoom(3)
                        .smooth()
                        .buildRegion();
        exportNoise(new File("exports/region.png"), 256, 256, Noise2D.region(reg), 1);

        int[] blackWhite = {0x000000, 0xFFFFFF};
        int[] redGreenBlueYellow = {0xFF0000, 0xFFFF00, 0x00FF00, 0x0000FF};

        exportLayerExample("zoom", ctx.pick(redGreenBlueYellow), ZoomLayer.INSTANCE, 16, 8);
        exportLayerExample("fuzzy_zoom", ctx.pick(redGreenBlueYellow), FuzzyZoomLayer.INSTANCE, 16, 8);
        exportLayerExample("voronoi_zoom", ctx.pick(redGreenBlueYellow), VoronoiZoomLayer.INSTANCE, 16, 8);
        exportLayerExample("cell_zoom", ctx.pick(redGreenBlueYellow), CellZoomLayer.INSTANCE, 16, 8);
        exportFloatLayerExample("interpolate_zoom", ctx.pickF(0, 1), InterpolateZoomLayer.FLOAT, 16, 8);
        exportLayerExample("smooth", ctx.pick(0xFF0000, 0xFF0000, 0xFFFF00, 0x0000FF), SmoothingLayer.INSTANCE, 16, 8);
        exportLayerExample("replace", ctx.pick(redGreenBlueYellow), new ReplaceLayer(0x00FF00, 0x00FFFF), 16, 8);
        exportLayerExample("replace_merge", ctx.pick(redGreenBlueYellow), ctx.pick(blackWhite, 13125), new ReplaceMergeLayer(0x00FF00), 16, 8);

        exportLayerExample("zoom_small", ctx.pick(redGreenBlueYellow).zoom(3), ZoomLayer.INSTANCE, 128, 1);
        exportLayerExample("fuzzy_zoom_small", ctx.pick(redGreenBlueYellow).zoom(3), FuzzyZoomLayer.INSTANCE, 128, 1);
        exportLayerExample("voronoi_zoom_small", ctx.pick(redGreenBlueYellow).zoom(3), VoronoiZoomLayer.INSTANCE, 128, 1);
        exportLayerExample("cell_zoom_small", ctx.pick(redGreenBlueYellow).zoom(3), CellZoomLayer.INSTANCE, 128, 1);
        exportFloatLayerExample("interpolate_zoom_small", ctx.pickF(0, 1).zoom(3), InterpolateZoomLayer.FLOAT, 128, 1);

        exportOutlineLayerExample("outline_small", ctx.pick(blackWhite).zoom(2), OutlineLayer.INSTANCE, 128, 1);

        exportLayerExample("random", ctx.pick(redGreenBlueYellow), 16, 8);
        exportFloatLayerExample("float_random", ctx.randomF(0, 1), 16, 8);
        exportLayerExample("static", ctx.value(0xFF0000), 16, 8);
        exportFloatLayerExample("noise", ctx.noise(new FractalOpenSimplex2D(seed, 8, 8, 4).lerp(0, 1)), 16, 8);
        exportFloatLayerExample("noise_small", ctx.noise(new FractalOpenSimplex2D(seed, 8, 8, 4).lerp(0, 1)).zoom(2), 128, 1);
    }

    public static void exportLayerExample(String type, RegionBuilder<?, ?> factory, TransformerLayer layer, int size, int px) {
        Region before = factory.buildRegion();
        Region after = factory.transform(layer).buildRegion();

        exportRegion(new File("exports/regions/" + type + "_before.png"), size * px, size * px, before, px);
        exportRegion(new File("exports/regions/" + type + "_after.png"), size * px, size * px, after, px);
    }

    public static void exportOutlineLayerExample(String type, RegionBuilder<?, ?> factory, TransformerLayer layer, int size, int px) {
        Region before = factory.buildRegion();
        Region after = factory.transform(layer).replace(1, 0xFFFFFF).zoom().buildRegion();

        exportRegion(new File("exports/regions/" + type + "_before.png"), size * px, size * px, before, px);
        exportRegion(new File("exports/regions/" + type + "_after.png"), size * px, size * px, after, px);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static void exportLayerExample(String type, RegionBuilder<?, ?> factory1, RegionBuilder<?, ?> factory2, MergerLayer layer, int size, int px) {
        Region before1 = factory1.buildRegion();
        Region before2 = factory2.buildRegion();
        Region after = ((RegionBuilder) factory1).merge(layer, factory2).buildRegion();

        exportRegion(new File("exports/regions/" + type + "_before1.png"), size * px, size * px, before1, px);
        exportRegion(new File("exports/regions/" + type + "_before2.png"), size * px, size * px, before2, px);
        exportRegion(new File("exports/regions/" + type + "_after.png"), size * px, size * px, after, px);
    }

    public static void exportLayerExample(String type, RegionBuilder<?, ?> factory, int size, int px) {
        Region reg = factory.buildRegion();

        exportRegion(new File("exports/regions/" + type + ".png"), size * px, size * px, reg, px);
    }

    public static void exportFloatLayerExample(String type, RegionBuilder<?, ?> factory, TransformerLayer layer, int size, int px) {
        Region before = factory.buildRegion();
        Region after = factory.transform(layer).buildRegion();

        exportFloatRegion(new File("exports/regions/" + type + "_before.png"), size * px, size * px, before, px);
        exportFloatRegion(new File("exports/regions/" + type + "_after.png"), size * px, size * px, after, px);
    }

    public static void exportFloatLayerExample(String type, RegionBuilder<?, ?> factory, int size, int px) {
        Region reg = factory.buildRegion();

        exportFloatRegion(new File("exports/regions/" + type + ".png"), size * px, size * px, reg, px);
    }

    public static void exportNoise(File file, int width, int height, Noise2D noise, double scale) {
        System.out.println("Exporting: " + file);

        file.getParentFile().mkdirs();

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                double n = NoiseMath.unlerp(-1, 1, noise.generate(x / scale, y / scale));
                int v = (int) NoiseMath.clamp(0, 255, n * 255);
                int col = 0xFF000000 | v << 16 | v << 8 | v;
                image.setRGB(x, y, col);
            }
        }

        try {
            ImageIO.write(image, "PNG", file);
        } catch (IOException exc) {
            throw new RuntimeException(exc);
        }
    }

    public static void exportFloatRegion(File file, int width, int height, Region region, int scale) {
        System.out.println("Exporting: " + file);

        file.getParentFile().mkdirs();

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                double n = region.getFPValue(x / scale, y / scale);
                int v = (int) NoiseMath.clamp(0, 255, n * 255);
                int col = 0xFF000000 | v << 16 | v << 8 | v;
                image.setRGB(x, y, col);
            }
        }

        try {
            ImageIO.write(image, "PNG", file);
        } catch (IOException exc) {
            throw new RuntimeException(exc);
        }
    }


    public static void exportRegion(File file, int width, int height, Region noise, int scale) {
        System.out.println("Exporting: " + file);

        file.getParentFile().mkdirs();

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int v = noise.getValue(x / scale, y / scale);
                int col = 0xFF000000 | v;
                image.setRGB(x, y, col);
            }
        }

        try {
            ImageIO.write(image, "PNG", file);
        } catch (IOException exc) {
            throw new RuntimeException(exc);
        }
    }
}
