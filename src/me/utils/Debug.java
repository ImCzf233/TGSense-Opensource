package me.utils;

import net.ccbluex.liquidbounce.features.module.modules.render.Animations;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.math.MathHelper;

public class Debug extends MinecraftInstance {

    public static Boolean thePlayerisBlocking = Boolean.valueOf(false);

    private static void func_178103_d2() {
        GlStateManager.translate(((Float) Animations.itemPosX.get()).floatValue(), ((Float) Animations.itemPosY.get()).floatValue(), ((Float) Animations.itemPosZ.get()).floatValue());
        GlStateManager.translate(-0.5F, 0.2F, 0.0F);
        GlStateManager.rotate(30.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(-80.0F, 1.0F, 0.0F, 0.0F);
        GlStateManager.rotate(60.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.scale(((Float) Animations.Scale.get()).floatValue(), ((Float) Animations.Scale.get()).floatValue(), ((Float) Animations.Scale.get()).floatValue());
    }

    private static void genCustom(float p_178096_1_, float p_178096_2_) {
        GlStateManager.translate(((Float) Animations.itemPosX.get()).floatValue(), ((Float) Animations.itemPosY.get()).floatValue(), ((Float) Animations.itemPosZ.get()).floatValue());
        GlStateManager.translate(0.56F, -0.52F, -0.71999997F);
        GlStateManager.translate(0.0F, p_178096_1_ * -0.6F, 0.0F);
        GlStateManager.rotate(25.0F, 0.0F, 1.0F, 0.0F);
        float f = MathHelper.sin(p_178096_2_ * p_178096_2_ * 3.1415927F);
        float f1 = MathHelper.sin(MathHelper.sqrt(p_178096_2_) * 3.1415927F);

        GlStateManager.rotate(f * -15.0F, 0.0F, 1.0F, 0.2F);
        GlStateManager.rotate(f1 * -10.0F, 0.2F, 0.1F, 1.0F);
        GlStateManager.rotate(f1 * -30.0F, 1.3F, 0.1F, 0.2F);
        GlStateManager.scale(0.4F, 0.4F, 0.4F);
        new Animations();
        GlStateManager.scale(((Float) Animations.Scale.get()).floatValue(), ((Float) Animations.Scale.get()).floatValue(), ((Float) Animations.Scale.get()).floatValue());
    }

    public static void transformSideFirstPersonBlock(EnumHandSide hand, float equippedProg, float swingProgress) {
        int side = hand == EnumHandSide.RIGHT ? 1 : -1;

        GlStateManager.translate((double) side * 0.56D, -0.52D + (double) equippedProg * -0.6D, -0.72D);
        GlStateManager.translate((double) side * -0.1414214D, 0.08D, 0.1414214D);
        GlStateManager.rotate(-102.25F, 1.0F, 0.0F, 0.0F);
        GlStateManager.rotate((float) side * 13.365F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate((float) side * 78.05F, 0.0F, 0.0F, 1.0F);
        double f = Math.sin((double) (swingProgress * swingProgress) * 3.141592653589793D);
        double f1 = Math.sin(Math.sqrt((double) swingProgress) * 3.141592653589793D);

        GlStateManager.rotate((float) (f * -20.0D), 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate((float) (f1 * -20.0D), 0.0F, 0.0F, 1.0F);
        GlStateManager.rotate((float) (f1 * -80.0D), 1.0F, 0.0F, 0.0F);
    }

    public static void Remix(EnumHandSide hand, float equippedProg, float swingProgress) {
        double f = 0.0D;

        genCustom((float) f, 0.83F);
        func_178103_d2();
        f = Math.sin((double) (swingProgress * swingProgress) * 3.141592653589793D);
        double f1 = Math.sin(Math.sqrt((double) swingProgress) * 3.141592653589793D);
        float f4 = MathHelper.sin(MathHelper.sqrt(f1) * 3.83F);

        GlStateManager.translate(-0.5F, 0.2F, 0.2F);
        GlStateManager.rotate(-f4 * 0.0F, 0.0F, 0.0F, 0.0F);
        GlStateManager.rotate(-f4 * 43.0F, 58.0F, 23.0F, 45.0F);
    }

    public static void slide2(EnumHandSide hand, float equippedProg, float swingProgress) {
        int side = hand == EnumHandSide.RIGHT ? 1 : -1;

        GlStateManager.translate((double) side * 0.25D, -0.55D + (double) equippedProg * -0.6D, -0.72D);
        GlStateManager.translate((double) side * -0.1414214D, 0.2D, 0.1414214D);
        GlStateManager.rotate(-100.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate((float) side * 13.365F, 0.0F, 0.0F, 0.0F);
        GlStateManager.rotate((float) side * 78.05F, 0.0F, 0.0F, 1.0F);
        double f = Math.sin((double) (swingProgress * swingProgress) * 3.141592653589793D);
        double f1 = Math.sin(Math.sqrt((double) swingProgress) * 3.141592653589793D);

        GlStateManager.rotate((float) (f * -20.0D), 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate((float) (f1 * -20.0D), 0.0F, 0.0F, 1.0F);
        GlStateManager.rotate((float) (f1 * -80.0D), 1.0F, 0.0F, 0.0F);
    }

    public static void slide2(float f, float f1) {
        GlStateManager.translate(0.56F, -0.52F, -0.71999997F);
        GlStateManager.translate(0.0F, 0.0F, 0.0F);
        GlStateManager.rotate(45.0F, 0.0F, 1.0F, 0.0F);
        float f2 = MathHelper.sin(f1 * f1 * 3.1415927F);
        float f3 = MathHelper.sin(MathHelper.sqrt(f1) * 3.1415927F);

        GlStateManager.rotate(f2 * 0.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(f3 * 0.0F, 0.0F, 0.0F, 1.0F);
        GlStateManager.rotate(f3 * -80.0F, 1.0F, 0.0F, 0.0F);
        GlStateManager.scale(((Float) Animations.Scale.get()).floatValue(), ((Float) Animations.Scale.get()).floatValue(), ((Float) Animations.Scale.get()).floatValue());
    }

    public static void func_178103_d() {
        GlStateManager.translate(-0.5F, 0.2F, 0.0F);
        GlStateManager.rotate(30.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(-80.0F, 1.0F, 0.0F, 0.0F);
        GlStateManager.rotate(60.0F, 0.0F, 1.0F, 0.0F);
    }

    public static void WindMill(EnumHandSide hand, float equippedProg, float swingProgress) {
        int side = hand == EnumHandSide.RIGHT ? 1 : -1;

        GlStateManager.translate((double) side * 0.56D, -0.52D + (double) equippedProg * -0.6D, -0.72D);
        GlStateManager.translate((double) side * -0.1414214D, 0.08D, 0.1414214D);
        GlStateManager.rotate(-102.25F, 1.0F, 0.0F, 0.0F);
        GlStateManager.rotate((float) side * 13.365F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate((float) side * 78.05F, 0.0F, 0.0F, 1.0F);
        double f = Math.sin((double) (swingProgress * swingProgress) * 3.141592653589793D);
        double f1 = Math.sin(Math.sqrt((double) swingProgress) * 3.141592653589793D);

        GlStateManager.rotate((float) (f * -20.0D), 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate((float) (f1 * -20.0D), 0.0F, 0.0F, 1.0F);
        GlStateManager.rotate((float) (f1 * -80.0D), 1.0F, 0.0F, 0.0F);
    }

    public static void Push(EnumHandSide hand, float equippedProg, float swingProgress) {
        int side = hand == EnumHandSide.RIGHT ? 1 : -1;

        GlStateManager.translate((double) side * 0.56D, -0.52D + (double) equippedProg * -0.6D, -0.72D);
        GlStateManager.translate((double) side * -0.1414214D, 0.08D, 0.1414214D);
        GlStateManager.rotate(-102.25F, 1.0F, 0.0F, 0.0F);
        GlStateManager.rotate((float) side * 13.365F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate((float) side * 78.05F, 0.0F, 0.0F, 1.0F);
        double f = Math.sin((double) (swingProgress * swingProgress) * 3.141592653589793D);
        double f1 = Math.sin(Math.sqrt((double) swingProgress) * 3.141592653589793D);

        GlStateManager.rotate((float) (f * -10.0D), 1.0F, 1.0F, 1.0F);
        GlStateManager.rotate((float) (f1 * -10.0D), 1.0F, 1.0F, 1.0F);
        GlStateManager.rotate((float) (f1 * -10.0D), 1.0F, 1.0F, 1.0F);
    }

    public static void Pride(EnumHandSide hand, float equippedProg, float swingProgress) {
        double f = 0.0D;

        genCustom((float) f, 0.9F);
        func_178103_d2();
        f = Math.sin((double) (swingProgress * swingProgress) * 3.141592653589793D);
        double f1 = Math.sin(Math.sqrt((double) swingProgress) * 3.141592653589793D);
        float f4 = MathHelper.sin(MathHelper.sqrt(f1) * 3.83F);
        int side = hand == EnumHandSide.RIGHT ? 1 : -1;

        GlStateManager.translate(-0.0F, 0.4F, 0.0F);
        GlStateManager.translate(-0.5F, 0.2F, 0.0F);
        GlStateManager.rotate(-102.25F, 1.0F, 0.0F, 0.0F);
        GlStateManager.rotate((float) side * 13.365F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate((float) side * 78.05F, 0.0F, 0.0F, 1.0F);
        GlStateManager.rotate((float) (f1 * 100.0D), 3.0F, 3.0F, 1.0F);
    }

    public static void Vanilla(EnumHandSide hand, float equippedProg, float swingProgress) {
        double f = 0.0D;

        genCustom((float) f, 0.83F);
        func_178103_d2();
        f = Math.sin((double) (swingProgress * swingProgress) * 3.141592653589793D);
        double f1 = Math.sin(Math.sqrt((double) swingProgress) * 3.141592653589793D);
        float f4 = MathHelper.sin(MathHelper.sqrt(f1) * 3.83F);
        int side = hand == EnumHandSide.RIGHT ? 1 : -1;

        GlStateManager.translate(-0.0F, 0.4F, 0.0F);
        GlStateManager.translate(-0.5F, 0.2F, 0.0F);
        GlStateManager.rotate(-102.25F, 1.0F, 0.0F, 0.0F);
        GlStateManager.rotate((float) side * 13.365F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate((float) side * 78.05F, 0.0F, 0.0F, 1.0F);
        GlStateManager.rotate((float) (f1 * 100.0D), 3.0F, 3.0F, 1.0F);
    }
}
