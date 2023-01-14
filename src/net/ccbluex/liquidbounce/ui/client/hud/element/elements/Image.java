package net.ccbluex.liquidbounce.ui.client.hud.element.elements;

import com.google.gson.JsonElement;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Base64;
import javax.imageio.ImageIO;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.api.minecraft.client.render.texture.IAbstractTexture;
import net.ccbluex.liquidbounce.api.minecraft.util.IResourceLocation;
import net.ccbluex.liquidbounce.ui.client.hud.element.Border;
import net.ccbluex.liquidbounce.ui.client.hud.element.Element;
import net.ccbluex.liquidbounce.ui.client.hud.element.ElementInfo;
import net.ccbluex.liquidbounce.ui.client.hud.element.Side;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.misc.MiscUtils;
import net.ccbluex.liquidbounce.utils.misc.RandomUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.value.TextValue;
import org.jetbrains.annotations.NotNull;

@ElementInfo(
    name = "Image"
)
@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0007\u0018\u0000 \u00112\u00020\u0001:\u0001\u0011B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\n\u001a\u00020\u000bH\u0016J\b\u0010\f\u001a\u00020\rH\u0016J\u000e\u0010\u000e\u001a\u00020\u00002\u0006\u0010\u0005\u001a\u00020\u000fJ\u0010\u0010\u000e\u001a\u00020\u00002\u0006\u0010\u0005\u001a\u00020\u0010H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0012"},
    d2 = { "Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/Image;", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Element;", "()V", "height", "", "image", "Lnet/ccbluex/liquidbounce/value/TextValue;", "resourceLocation", "Lnet/ccbluex/liquidbounce/api/minecraft/util/IResourceLocation;", "width", "createElement", "", "drawElement", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Border;", "setImage", "Ljava/io/File;", "", "Companion", "LiquidBounce"}
)
public final class Image extends Element {

    private final TextValue image = (TextValue) (new TextValue("Image", "") {
        public void fromJson(@NotNull JsonElement element) {
            Intrinsics.checkParameterIsNotNull(element, "element");
            super.fromJson(element);
            CharSequence charsequence = (CharSequence) this.get();
            boolean flag = false;

            if (charsequence.length() != 0) {
                Image.this.setImage((String) this.get());
            }
        }

        protected void onChanged(@NotNull String oldValue, @NotNull String newValue) {
            Intrinsics.checkParameterIsNotNull(oldValue, "oldValue");
            Intrinsics.checkParameterIsNotNull(newValue, "newValue");
            CharSequence charsequence = (CharSequence) this.get();
            boolean flag = false;

            if (charsequence.length() != 0) {
                Image.this.setImage((String) this.get());
            }
        }
    });
    private final IResourceLocation resourceLocation;
    private int width;
    private int height;
    public static final Image.Companion Companion = new Image.Companion((DefaultConstructorMarker) null);

    @NotNull
    public Border drawElement() {
        RenderUtils.drawImage(this.resourceLocation, 0, 0, this.width / 2, this.height / 2);
        return new Border(0.0F, 0.0F, (float) this.width / 2.0F, (float) this.height / 2.0F);
    }

    public boolean createElement() {
        File file = MiscUtils.openFileChooser();

        if (file != null) {
            File file = file;

            if (!file.exists()) {
                MiscUtils.showErrorPopup("Error", "The file does not exist.");
                return false;
            } else if (file.isDirectory()) {
                MiscUtils.showErrorPopup("Error", "The file is a directory.");
                return false;
            } else {
                this.setImage(file);
                return true;
            }
        } else {
            return false;
        }
    }

    private final Image setImage(String image) {
        try {
            this.image.changeValue(image);
            ByteArrayInputStream e = new ByteArrayInputStream(Base64.getDecoder().decode(image));
            BufferedImage bufferedImage = ImageIO.read((InputStream) e);

            e.close();
            Intrinsics.checkExpressionValueIsNotNull(bufferedImage, "bufferedImage");
            this.width = bufferedImage.getWidth();
            this.height = bufferedImage.getHeight();
            MinecraftInstance.mc.getTextureManager().loadTexture(this.resourceLocation, (IAbstractTexture) MinecraftInstance.classProvider.createDynamicTexture(bufferedImage));
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return this;
    }

    @NotNull
    public final Image setImage(@NotNull File image) {
        Intrinsics.checkParameterIsNotNull(image, "image");

        try {
            String s = Base64.getEncoder().encodeToString(Files.readAllBytes(image.toPath()));

            Intrinsics.checkExpressionValueIsNotNull(s, "Base64.getEncoder().enco…AllBytes(image.toPath()))");
            this.setImage(s);
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return this;
    }

    public Image() {
        super(0.0D, 0.0D, 0.0F, (Side) null, 15, (DefaultConstructorMarker) null);
        this.resourceLocation = MinecraftInstance.classProvider.createResourceLocation(RandomUtils.INSTANCE.randomNumber(128));
        this.width = 64;
        this.height = 64;
    }

    @Metadata(
        mv = { 1, 1, 16},
        bv = { 1, 0, 3},
        k = 1,
        d1 = { "\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0006\u0010\u0003\u001a\u00020\u0004¨\u0006\u0005"},
        d2 = { "Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/Image$Companion;", "", "()V", "default", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/Image;", "LiquidBounce"}
    )
    public static final class Companion {

        @NotNull
        public final Image default() {
            Image image = new Image();

            image.setX(0.0D);
            image.setY(0.0D);
            return image;
        }

        private Companion() {}

        public Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}
