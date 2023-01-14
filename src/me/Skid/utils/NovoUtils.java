package me.Skid.utils;

import java.awt.Color;
import me.Skid.utils.Render.Translate;
import net.minecraft.item.ItemStack;

public class NovoUtils {

    public Translate Translate = new Translate(0.0F, 0.0F);
    public ItemStack stack;
    public float y;
    public Color color;
    public Color color2;
    public Color color3;
    public Color color4;

    public NovoUtils(ItemStack stack, Translate translate, float y, Color color, Color color2, Color color3, Color color4) {
        this.stack = stack;
        this.Translate = translate;
        this.y = y;
        this.color = color;
        this.color2 = color2;
        this.color3 = color3;
        this.color4 = color4;
    }
}
