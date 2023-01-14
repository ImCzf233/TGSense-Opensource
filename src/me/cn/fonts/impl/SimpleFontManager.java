package me.cn.fonts.impl;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;
import java.util.EnumMap;
import java.util.function.Function;
import me.cn.fonts.api.FontFamily;
import me.cn.fonts.api.FontManager;
import me.cn.fonts.api.FontType;
import me.cn.fonts.util.SneakyThrowing;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;

public final class SimpleFontManager implements FontManager {

    private static final String FONT_DIRECTORY = "liquidbounce+/fonts/";
    private final SimpleFontManager.FontRegistry fonts = new SimpleFontManager.FontRegistry(null);

    public static FontManager create() {
        return new SimpleFontManager();
    }

    public FontFamily fontFamily(FontType fontType) {
        return this.fonts.fontFamily(fontType);
    }

    private static final class FontRegistry extends EnumMap {

        private FontRegistry() {
            super(FontType.class);
        }

        private FontFamily fontFamily(FontType fontType) {
            return (FontFamily) this.computeIfAbsent(fontType, (ignored) -> {
                try {
                    return SimpleFontFamily.create(fontType, readFontFromResources(fontType));
                } catch (IOException ioexception) {
                    throw SneakyThrowing.sneakyThrow(ioexception);
                }
            });
        }

        private static Font readFontFromResources(FontType fontType) throws IOException {
            IResourceManager resourceManager = Minecraft.getMinecraft().getResourceManager();
            ResourceLocation location = new ResourceLocation("liquidbounce+/fonts/" + fontType.fileName());

            IResource resource;

            try {
                resource = resourceManager.getResource(location);
            } catch (IOException ioexception) {
                throw new IOException("Couldn\'t find resource: " + location, ioexception);
            }

            InputStream resourceInputStream = resource.getInputStream();
            Throwable throwable = null;

            Font font;

            try {
                font = readFont(resourceInputStream);
            } catch (Throwable throwable1) {
                throwable = throwable1;
                throw throwable1;
            } finally {
                if (resourceInputStream != null) {
                    if (throwable != null) {
                        try {
                            resourceInputStream.close();
                        } catch (Throwable throwable2) {
                            throwable.addSuppressed(throwable2);
                        }
                    } else {
                        resourceInputStream.close();
                    }
                }

            }

            return font;
        }

        private static Font readFont(InputStream resource) {
            try {
                Font font = Font.createFont(0, resource);

                return font;
            } catch (FontFormatException fontformatexception) {
                throw new RuntimeException("Resource does not contain the required font tables for the specified format", fontformatexception);
            } catch (IOException ioexception) {
                throw new RuntimeException("Couldn\'t completely read font resource", ioexception);
            }
        }

        FontRegistry(Object x0) {
            this();
        }
    }
}
