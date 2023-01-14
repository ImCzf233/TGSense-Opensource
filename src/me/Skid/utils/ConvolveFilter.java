package me.Skid.utils;

import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Point2D.Double;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.Kernel;
import java.util.Hashtable;

public class ConvolveFilter extends AbstractBufferedImageOp {

    public static int ZERO_EDGES = 0;
    public static int CLAMP_EDGES = 1;
    public static int WRAP_EDGES = 2;
    protected Kernel kernel;
    protected boolean alpha;
    protected boolean premultiplyAlpha;
    private int edgeAction;

    public ConvolveFilter() {
        this(new float[9]);
    }

    public ConvolveFilter(float[] matrix) {
        this(new Kernel(3, 3, matrix));
    }

    public ConvolveFilter(int rows, int cols, float[] matrix) {
        this(new Kernel(cols, rows, matrix));
    }

    public ConvolveFilter(Kernel kernel) {
        this.kernel = null;
        this.alpha = true;
        this.premultiplyAlpha = true;
        this.edgeAction = ConvolveFilter.CLAMP_EDGES;
        this.kernel = kernel;
    }

    public void setKernel(Kernel kernel) {
        this.kernel = kernel;
    }

    public Kernel getKernel() {
        return this.kernel;
    }

    public void setEdgeAction(int edgeAction) {
        this.edgeAction = edgeAction;
    }

    public int getEdgeAction() {
        return this.edgeAction;
    }

    public void setUseAlpha(boolean useAlpha) {
        this.alpha = useAlpha;
    }

    public boolean getUseAlpha() {
        return this.alpha;
    }

    public void setPremultiplyAlpha(boolean premultiplyAlpha) {
        this.premultiplyAlpha = premultiplyAlpha;
    }

    public boolean getPremultiplyAlpha() {
        return this.premultiplyAlpha;
    }

    public BufferedImage filter(BufferedImage src, BufferedImage dst) {
        int width = src.getWidth();
        int height = src.getHeight();

        if (dst == null) {
            dst = this.createCompatibleDestImage(src, (ColorModel) null);
        }

        int[] inPixels = new int[width * height];
        int[] outPixels = new int[width * height];

        this.getRGB(src, 0, 0, width, height, inPixels);
        if (this.premultiplyAlpha) {
            ImageMath.premultiply(inPixels, 0, inPixels.length);
        }

        convolve(this.kernel, inPixels, outPixels, width, height, this.alpha, this.edgeAction);
        if (this.premultiplyAlpha) {
            ImageMath.unpremultiply(outPixels, 0, outPixels.length);
        }

        this.setRGB(dst, 0, 0, width, height, outPixels);
        return dst;
    }

    public BufferedImage createCompatibleDestImage(BufferedImage src, ColorModel dstCM) {
        if (dstCM == null) {
            dstCM = src.getColorModel();
        }

        return new BufferedImage(dstCM, dstCM.createCompatibleWritableRaster(src.getWidth(), src.getHeight()), dstCM.isAlphaPremultiplied(), (Hashtable) null);
    }

    public Rectangle2D getBounds2D(BufferedImage src) {
        return new Rectangle(0, 0, src.getWidth(), src.getHeight());
    }

    public Point2D getPoint2D(Point2D srcPt, Point2D dstPt) {
        if (dstPt == null) {
            dstPt = new Double();
        }

        ((Point2D) dstPt).setLocation(srcPt.getX(), srcPt.getY());
        return (Point2D) dstPt;
    }

    public RenderingHints getRenderingHints() {
        return null;
    }

    public static void convolve(Kernel kernel, int[] inPixels, int[] outPixels, int width, int height, int edgeAction) {
        convolve(kernel, inPixels, outPixels, width, height, true, edgeAction);
    }

    public static void convolve(Kernel kernel, int[] inPixels, int[] outPixels, int width, int height, boolean alpha, int edgeAction) {
        if (kernel.getHeight() == 1) {
            convolveH(kernel, inPixels, outPixels, width, height, alpha, edgeAction);
        } else if (kernel.getWidth() == 1) {
            convolveV(kernel, inPixels, outPixels, width, height, alpha, edgeAction);
        } else {
            convolveHV(kernel, inPixels, outPixels, width, height, alpha, edgeAction);
        }

    }

    public static void convolveHV(Kernel kernel, int[] inPixels, int[] outPixels, int width, int height, boolean alpha, int edgeAction) {
        int index = 0;
        float[] matrix = kernel.getKernelData((float[]) null);
        int rows = kernel.getHeight();
        int cols = kernel.getWidth();
        int rows2 = rows / 2;
        int cols2 = cols / 2;

        for (int y = 0; y < height; ++y) {
            for (int x = 0; x < width; ++x) {
                float r = 0.0F;
                float g = 0.0F;
                float b = 0.0F;
                float a = 0.0F;

                int ia;
                int ir;
                int ig;
                int ib;

                for (ia = -rows2; ia <= rows2; ++ia) {
                    ir = y + ia;
                    if (0 <= ir && ir < height) {
                        ig = ir * width;
                    } else if (edgeAction == ConvolveFilter.CLAMP_EDGES) {
                        ig = y * width;
                    } else {
                        if (edgeAction != ConvolveFilter.WRAP_EDGES) {
                            continue;
                        }

                        ig = (ir + height) % height * width;
                    }

                    ib = cols * (ia + rows2) + cols2;

                    for (int col = -cols2; col <= cols2; ++col) {
                        float f = matrix[ib + col];

                        if (f != 0.0F) {
                            int ix = x + col;

                            if (0 > ix || ix >= width) {
                                if (edgeAction == ConvolveFilter.CLAMP_EDGES) {
                                    ix = x;
                                } else {
                                    if (edgeAction != ConvolveFilter.WRAP_EDGES) {
                                        continue;
                                    }

                                    ix = (x + width) % width;
                                }
                            }

                            int rgb = inPixels[ig + ix];

                            a += f * (float) (rgb >> 24 & 255);
                            r += f * (float) (rgb >> 16 & 255);
                            g += f * (float) (rgb >> 8 & 255);
                            b += f * (float) (rgb & 255);
                        }
                    }
                }

                ia = alpha ? PixelUtils.clamp((int) ((double) a + 0.5D)) : 255;
                ir = PixelUtils.clamp((int) ((double) r + 0.5D));
                ig = PixelUtils.clamp((int) ((double) g + 0.5D));
                ib = PixelUtils.clamp((int) ((double) b + 0.5D));
                outPixels[index++] = ia << 24 | ir << 16 | ig << 8 | ib;
            }
        }

    }

    public static void convolveH(Kernel kernel, int[] inPixels, int[] outPixels, int width, int height, boolean alpha, int edgeAction) {
        int index = 0;
        float[] matrix = kernel.getKernelData((float[]) null);
        int cols = kernel.getWidth();
        int cols2 = cols / 2;

        for (int y = 0; y < height; ++y) {
            int ioffset = y * width;

            for (int x = 0; x < width; ++x) {
                float r = 0.0F;
                float g = 0.0F;
                float b = 0.0F;
                float a = 0.0F;
                int moffset = cols2;

                int ia;
                int ig;
                int ib;

                for (ia = -cols2; ia <= cols2; ++ia) {
                    float ir = matrix[moffset + ia];

                    if (ir != 0.0F) {
                        ig = x + ia;
                        if (ig < 0) {
                            if (edgeAction == ConvolveFilter.CLAMP_EDGES) {
                                ig = 0;
                            } else if (edgeAction == ConvolveFilter.WRAP_EDGES) {
                                ig = (x + width) % width;
                            }
                        } else if (ig >= width) {
                            if (edgeAction == ConvolveFilter.CLAMP_EDGES) {
                                ig = width - 1;
                            } else if (edgeAction == ConvolveFilter.WRAP_EDGES) {
                                ig = (x + width) % width;
                            }
                        }

                        ib = inPixels[ioffset + ig];
                        a += ir * (float) (ib >> 24 & 255);
                        r += ir * (float) (ib >> 16 & 255);
                        g += ir * (float) (ib >> 8 & 255);
                        b += ir * (float) (ib & 255);
                    }
                }

                ia = alpha ? PixelUtils.clamp((int) ((double) a + 0.5D)) : 255;
                int i = PixelUtils.clamp((int) ((double) r + 0.5D));

                ig = PixelUtils.clamp((int) ((double) g + 0.5D));
                ib = PixelUtils.clamp((int) ((double) b + 0.5D));
                outPixels[index++] = ia << 24 | i << 16 | ig << 8 | ib;
            }
        }

    }

    public static void convolveV(Kernel kernel, int[] inPixels, int[] outPixels, int width, int height, boolean alpha, int edgeAction) {
        int index = 0;
        float[] matrix = kernel.getKernelData((float[]) null);
        int rows = kernel.getHeight();
        int rows2 = rows / 2;

        for (int y = 0; y < height; ++y) {
            for (int x = 0; x < width; ++x) {
                float r = 0.0F;
                float g = 0.0F;
                float b = 0.0F;
                float a = 0.0F;

                int ia;
                int ir;
                int ig;

                for (ia = -rows2; ia <= rows2; ++ia) {
                    ir = y + ia;
                    if (ir < 0) {
                        if (edgeAction == ConvolveFilter.CLAMP_EDGES) {
                            ig = 0;
                        } else if (edgeAction == ConvolveFilter.WRAP_EDGES) {
                            ig = (y + height) % height * width;
                        } else {
                            ig = ir * width;
                        }
                    } else if (ir >= height) {
                        if (edgeAction == ConvolveFilter.CLAMP_EDGES) {
                            ig = (height - 1) * width;
                        } else if (edgeAction == ConvolveFilter.WRAP_EDGES) {
                            ig = (y + height) % height * width;
                        } else {
                            ig = ir * width;
                        }
                    } else {
                        ig = ir * width;
                    }

                    float ib = matrix[ia + rows2];

                    if (ib != 0.0F) {
                        int rgb = inPixels[ig + x];

                        a += ib * (float) (rgb >> 24 & 255);
                        r += ib * (float) (rgb >> 16 & 255);
                        g += ib * (float) (rgb >> 8 & 255);
                        b += ib * (float) (rgb & 255);
                    }
                }

                ia = alpha ? PixelUtils.clamp((int) ((double) a + 0.5D)) : 255;
                ir = PixelUtils.clamp((int) ((double) r + 0.5D));
                ig = PixelUtils.clamp((int) ((double) g + 0.5D));
                int i = PixelUtils.clamp((int) ((double) b + 0.5D));

                outPixels[index++] = ia << 24 | ir << 16 | ig << 8 | i;
            }
        }

    }

    public String toString() {
        return "Blur/Convolve...";
    }
}
