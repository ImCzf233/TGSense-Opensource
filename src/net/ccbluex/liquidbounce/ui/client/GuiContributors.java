package net.ccbluex.liquidbounce.ui.client;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.annotations.SerializedName;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.Closeable;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import javax.imageio.ImageIO;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.concurrent.ThreadsKt;
import kotlin.io.CloseableKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.api.minecraft.client.IMinecraft;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IFontRenderer;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IGuiButton;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IGuiScreen;
import net.ccbluex.liquidbounce.api.util.WrappedGuiScreen;
import net.ccbluex.liquidbounce.api.util.WrappedGuiSlot;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.misc.HttpUtils;
import net.ccbluex.liquidbounce.utils.render.CustomTexture;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.opengl.GL11;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000T\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0004\n\u0002\u0010\f\n\u0002\b\t\u0018\u00002\u00020\u0001:\u0006\u001f !\"#$B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0016J \u0010\u0012\u001a\u00020\u000f2\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00142\u0006\u0010\u0016\u001a\u00020\u0017H\u0016J\b\u0010\u0018\u001a\u00020\u000fH\u0016J\b\u0010\u0019\u001a\u00020\u000fH\u0016J\u0018\u0010\u001a\u001a\u00020\u000f2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u0014H\u0016J\b\u0010\u001e\u001a\u00020\u000fH\u0002R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0018\u0010\u0007\u001a\f\u0012\b\u0012\u00060\tR\u00020\u00000\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0012\u0010\f\u001a\u00060\rR\u00020\u0000X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006%"},
    d2 = { "Lnet/ccbluex/liquidbounce/ui/client/GuiContributors;", "Lnet/ccbluex/liquidbounce/api/util/WrappedGuiScreen;", "prevGui", "Lnet/ccbluex/liquidbounce/api/minecraft/client/gui/IGuiScreen;", "(Lnet/ccbluex/liquidbounce/api/minecraft/client/gui/IGuiScreen;)V", "DECIMAL_FORMAT", "Ljava/text/DecimalFormat;", "credits", "", "Lnet/ccbluex/liquidbounce/ui/client/GuiContributors$Credit;", "failed", "", "list", "Lnet/ccbluex/liquidbounce/ui/client/GuiContributors$GuiList;", "actionPerformed", "", "button", "Lnet/ccbluex/liquidbounce/api/minecraft/client/gui/IGuiButton;", "drawScreen", "mouseX", "", "mouseY", "partialTicks", "", "handleMouseInput", "initGui", "keyTyped", "typedChar", "", "keyCode", "loadCredits", "ContributorInformation", "Credit", "GitHubAuthor", "GitHubContributor", "GitHubWeek", "GuiList", "LiquidBounce"}
)
public final class GuiContributors extends WrappedGuiScreen {

    private final DecimalFormat DECIMAL_FORMAT;
    private GuiContributors.GuiList list;
    private List credits;
    private boolean failed;
    private final IGuiScreen prevGui;

    public void initGui() {
        this.list = new GuiContributors.GuiList(this.getRepresentedScreen());
        GuiContributors.GuiList guicontributors_guilist = this.list;

        if (this.list == null) {
            Intrinsics.throwUninitializedPropertyAccessException("list");
        }

        guicontributors_guilist.getRepresented().registerScrollButtons(7, 8);
        guicontributors_guilist = this.list;
        if (this.list == null) {
            Intrinsics.throwUninitializedPropertyAccessException("list");
        }

        guicontributors_guilist.getRepresented().elementClicked(-1, false, 0, 0);
        this.getRepresentedScreen().getButtonList().add(MinecraftInstance.classProvider.createGuiButton(1, this.getRepresentedScreen().getWidth() / 2 - 100, this.getRepresentedScreen().getHeight() - 30, "Back"));
        this.failed = false;
        ThreadsKt.thread$default(false, false, (ClassLoader) null, (String) null, 0, (Function0) (new Function0(0) {
            public final void invoke() {
                GuiContributors.this.loadCredits();
            }
        }), 31, (Object) null);
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.getRepresentedScreen().drawBackground(0);
        GuiContributors.GuiList guicontributors_guilist = this.list;

        if (this.list == null) {
            Intrinsics.throwUninitializedPropertyAccessException("list");
        }

        guicontributors_guilist.getRepresented().drawScreen(mouseX, mouseY, partialTicks);
        RenderUtils.drawRect((float) this.getRepresentedScreen().getWidth() / 4.0F, 40.0F, (float) this.getRepresentedScreen().getWidth(), (float) this.getRepresentedScreen().getHeight() - 40.0F, Integer.MIN_VALUE);
        guicontributors_guilist = this.list;
        if (this.list == null) {
            Intrinsics.throwUninitializedPropertyAccessException("list");
        }

        IFontRenderer ifontrenderer;
        float f;
        float f1;
        Color color;

        if (guicontributors_guilist.getSelectedSlot$LiquidBounce() != -1) {
            List list = this.credits;
            GuiContributors.GuiList guicontributors_guilist1 = this.list;

            if (this.list == null) {
                Intrinsics.throwUninitializedPropertyAccessException("list");
            }

            GuiContributors.Credit gb = (GuiContributors.Credit) list.get(guicontributors_guilist1.getSelectedSlot$LiquidBounce());
            byte y = 45;
            int x = this.getRepresentedScreen().getWidth() / 4 + 5;
            int infoOffset = 0;
            CustomTexture avatar = gb.getAvatar();
            int imageSize = this.getRepresentedScreen().getFontRendererObj().getFontHeight() * 4;

            if (avatar != null) {
                GL11.glPushAttrib(1048575);
                MinecraftInstance.classProvider.getGlStateManager().enableAlpha();
                MinecraftInstance.classProvider.getGlStateManager().enableBlend();
                MinecraftInstance.classProvider.getGlStateManager().tryBlendFuncSeparate(770, 771, 1, 0);
                MinecraftInstance.classProvider.getGlStateManager().enableTexture2D();
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                MinecraftInstance.classProvider.getGlStateManager().bindTexture(avatar.getTextureId());
                GL11.glBegin(7);
                GL11.glTexCoord2f(0.0F, 0.0F);
                GL11.glVertex2i(x, y);
                GL11.glTexCoord2f(0.0F, 1.0F);
                GL11.glVertex2i(x, y + imageSize);
                GL11.glTexCoord2f(1.0F, 1.0F);
                GL11.glVertex2i(x + imageSize, y + imageSize);
                GL11.glTexCoord2f(1.0F, 0.0F);
                GL11.glVertex2i(x + imageSize, y);
                GL11.glEnd();
                MinecraftInstance.classProvider.getGlStateManager().bindTexture(0);
                MinecraftInstance.classProvider.getGlStateManager().disableBlend();
                infoOffset = imageSize;
                GL11.glPopAttrib();
            }

            int y1 = y + imageSize;

            ifontrenderer = Fonts.font40;
            String s = "@" + gb.getName();

            f = (float) (x + infoOffset + 5);
            color = Color.WHITE;
            Intrinsics.checkExpressionValueIsNotNull(Color.WHITE, "Color.WHITE");
            ifontrenderer.drawString(s, f, 48.0F, color.getRGB(), true);
            ifontrenderer = Fonts.font40;
            s = gb.getCommits() + " commits §a" + this.DECIMAL_FORMAT.format(Integer.valueOf(gb.getAdditions())) + "++ §4" + this.DECIMAL_FORMAT.format(Integer.valueOf(gb.getDeletions())) + "--";
            f = (float) (x + infoOffset + 5);
            f1 = (float) (y1 - Fonts.font40.getFontHeight());
            color = Color.WHITE;
            Intrinsics.checkExpressionValueIsNotNull(Color.WHITE, "Color.WHITE");
            ifontrenderer.drawString(s, f, f1, color.getRGB(), true);
            Iterator iterator = gb.getContributions().iterator();

            while (iterator.hasNext()) {
                String s = (String) iterator.next();

                y1 += Fonts.font40.getFontHeight() + 2;
                MinecraftInstance.classProvider.getGlStateManager().disableTexture2D();
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                GL11.glBegin(1);
                GL11.glVertex2f((float) x, (float) y1 + (float) Fonts.font40.getFontHeight() / 2.0F - (float) 1);
                GL11.glVertex2f((float) x + 3.0F, (float) y1 + (float) Fonts.font40.getFontHeight() / 2.0F - (float) 1);
                GL11.glEnd();
                ifontrenderer = Fonts.font40;
                f = (float) x + 5.0F;
                f1 = (float) y1;
                color = Color.WHITE;
                Intrinsics.checkExpressionValueIsNotNull(Color.WHITE, "Color.WHITE");
                ifontrenderer.drawString(s, f, f1, color.getRGB(), true);
            }
        }

        Fonts.font40.drawCenteredString("Contributors", (float) this.getRepresentedScreen().getWidth() / 2.0F, 6.0F, 16777215);
        if (this.credits.isEmpty()) {
            if (this.failed) {
                double y2 = (double) System.currentTimeMillis() * 0.003003003003003003D;
                boolean infoOffset1 = false;
                int gb1 = (int) ((Math.sin(y2) + (double) 1) * 127.5D);

                Fonts.font40.drawCenteredString("Failed to load", (float) this.getRepresentedScreen().getWidth() / 8.0F, (float) this.getRepresentedScreen().getHeight() / 2.0F, (new Color(255, gb1, gb1)).getRGB());
            } else {
                ifontrenderer = Fonts.font40;
                f = (float) this.getRepresentedScreen().getWidth() / 8.0F;
                f1 = (float) this.getRepresentedScreen().getHeight() / 2.0F;
                color = Color.WHITE;
                Intrinsics.checkExpressionValueIsNotNull(Color.WHITE, "Color.WHITE");
                ifontrenderer.drawCenteredString("Loading...", f, f1, color.getRGB());
                RenderUtils.drawLoadingCircle((float) (this.getRepresentedScreen().getWidth() / 8), (float) (this.getRepresentedScreen().getHeight() / 2 - 40));
            }
        }

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    public void actionPerformed(@NotNull IGuiButton button) {
        Intrinsics.checkParameterIsNotNull(button, "button");
        if (button.getId() == 1) {
            MinecraftInstance.mc.displayGuiScreen(this.prevGui);
        }

    }

    public void keyTyped(char typedChar, int keyCode) {
        if (1 == keyCode) {
            MinecraftInstance.mc.displayGuiScreen(this.prevGui);
        } else {
            super.keyTyped(typedChar, keyCode);
        }
    }

    public void handleMouseInput() {
        super.handleMouseInput();
        GuiContributors.GuiList guicontributors_guilist = this.list;

        if (this.list == null) {
            Intrinsics.throwUninitializedPropertyAccessException("list");
        }

        guicontributors_guilist.getRepresented().handleMouseInput();
    }

    private final void loadCredits() {
        try {
            Gson e = new Gson();
            JsonParser jsonParser = new JsonParser();
            GuiContributors.GitHubContributor[] gitHubContributors = (GuiContributors.GitHubContributor[]) e.fromJson(HttpUtils.get("https://api.github.com/repos/CCBlueX/LiquidBounce/stats/contributors"), GuiContributors.GitHubContributor[].class);
            JsonElement jsonelement = jsonParser.parse(HttpUtils.get("https://raw.githubusercontent.com/CCBlueX/LiquidCloud/master/LiquidBounce/contributors.json"));

            Intrinsics.checkExpressionValueIsNotNull(jsonelement, "jsonParser.parse(HttpUti…unce/contributors.json\"))");
            JsonObject additionalInformation = jsonelement.getAsJsonObject();
            ArrayList credits = new ArrayList(gitHubContributors.length);
            GuiContributors.GitHubContributor[] aguicontributors_githubcontributor = gitHubContributors;
            int i = gitHubContributors.length;

            for (int j = 0; j < i; ++j) {
                GuiContributors.GitHubContributor credit = aguicontributors_githubcontributor[j];
                GuiContributors.ContributorInformation contributorInformation = (GuiContributors.ContributorInformation) null;
                JsonElement it = additionalInformation.get(String.valueOf(credit.getAuthor().getId()));

                if (it != null) {
                    contributorInformation = (GuiContributors.ContributorInformation) e.fromJson(it, GuiContributors.ContributorInformation.class);
                }

                int $i$a$-use-GuiContributors$loadCredits$2 = 0;
                int deletions = 0;
                int commits = 0;

                GuiContributors.GitHubWeek week;

                for (Iterator iterator = credit.getWeeks().iterator(); iterator.hasNext(); commits += week.getCommits()) {
                    week = (GuiContributors.GitHubWeek) iterator.next();
                    $i$a$-use-GuiContributors$loadCredits$2 += week.getAdditions();
                    deletions += week.getDeletions();
                }

                GuiContributors.Credit guicontributors_credit;
                String s;
                String s1;
                boolean flag;
                List list;
                label140: {
                    guicontributors_credit = new GuiContributors.Credit;
                    s = credit.getAuthor().getName();
                    s1 = credit.getAuthor().getAvatarUrl();
                    flag = contributorInformation != null ? contributorInformation.getTeamMember() : false;
                    if (contributorInformation != null) {
                        list = contributorInformation.getContributions();
                        if (list != null) {
                            break label140;
                        }
                    }

                    list = Collections.emptyList();
                    Intrinsics.checkExpressionValueIsNotNull(list, "Collections.emptyList()");
                }

                guicontributors_credit.<init>(s, s1, (CustomTexture) null, $i$a$-use-GuiContributors$loadCredits$2, deletions, commits, flag, list);
                credits.add(guicontributors_credit);
            }

            CollectionsKt.sortWith((List) credits, (Comparator) (new Comparator() {
                public int compare(@NotNull GuiContributors.Credit o1, @NotNull GuiContributors.Credit o2) {
                    Intrinsics.checkParameterIsNotNull(o1, "o1");
                    Intrinsics.checkParameterIsNotNull(o2, "o2");
                    return o1.isTeamMember() && o2.isTeamMember() ? -Intrinsics.compare(o1.getCommits(), o2.getCommits()) : (o1.isTeamMember() ? -1 : (o2.isTeamMember() ? 1 : -Intrinsics.compare(o1.getAdditions(), o2.getAdditions())));
                }
            }));
            this.credits = (List) credits;
            Iterator iterator1 = credits.iterator();

            while (iterator1.hasNext()) {
                GuiContributors.Credit guicontributors_credit1 = (GuiContributors.Credit) iterator1.next();

                try {
                    InputStream inputstream = HttpUtils.requestStream$default(HttpUtils.INSTANCE, guicontributors_credit1.getAvatarUrl() + "?s=" + this.getRepresentedScreen().getFontRendererObj().getFontHeight() * 4, "GET", (String) null, 4, (Object) null);

                    if (inputstream != null) {
                        Closeable closeable = (Closeable) inputstream;
                        boolean flag1 = false;
                        Throwable throwable = (Throwable) null;

                        try {
                            InputStream inputstream1 = (InputStream) closeable;
                            boolean flag2 = false;
                            CustomTexture customtexture = new CustomTexture;
                            BufferedImage bufferedimage = ImageIO.read(inputstream1);

                            if (bufferedimage == null) {
                                Intrinsics.throwNpe();
                            }

                            customtexture.<init>(bufferedimage);
                            guicontributors_credit1.setAvatar(customtexture);
                            Unit unit = Unit.INSTANCE;
                        } catch (Throwable throwable1) {
                            throwable = throwable1;
                            throw throwable1;
                        } finally {
                            CloseableKt.closeFinally(closeable, throwable);
                        }
                    }
                } catch (Exception exception) {
                    ;
                }
            }
        } catch (Exception exception1) {
            ClientUtils.getLogger().error("Failed to load credits.", (Throwable) exception1);
            this.failed = true;
        }

    }

    public GuiContributors(@NotNull IGuiScreen prevGui) {
        Intrinsics.checkParameterIsNotNull(prevGui, "prevGui");
        super();
        this.prevGui = prevGui;
        NumberFormat numberformat = NumberFormat.getInstance(Locale.US);

        if (numberformat == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.text.DecimalFormat");
        } else {
            this.DECIMAL_FORMAT = (DecimalFormat) numberformat;
            List list = Collections.emptyList();

            Intrinsics.checkExpressionValueIsNotNull(list, "Collections.emptyList()");
            this.credits = list;
        }
    }

    public static final void access$setCredits$p(GuiContributors $this, List <set-?>) {
        $this.credits = <set-?>;
    }

    @Metadata(
        mv = { 1, 1, 16},
        bv = { 1, 0, 3},
        k = 1,
        d1 = { "\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010 \n\u0002\b\b\b\u0080\u0004\u0018\u00002\u00020\u0001B#\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00030\u0007¢\u0006\u0002\u0010\bR\u0017\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00030\u0007¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u000f"},
        d2 = { "Lnet/ccbluex/liquidbounce/ui/client/GuiContributors$ContributorInformation;", "", "name", "", "teamMember", "", "contributions", "", "(Lnet/ccbluex/liquidbounce/ui/client/GuiContributors;Ljava/lang/String;ZLjava/util/List;)V", "getContributions", "()Ljava/util/List;", "getName", "()Ljava/lang/String;", "getTeamMember", "()Z", "LiquidBounce"}
    )
    public final class ContributorInformation {

        @NotNull
        private final String name;
        private final boolean teamMember;
        @NotNull
        private final List contributions;

        @NotNull
        public final String getName() {
            return this.name;
        }

        public final boolean getTeamMember() {
            return this.teamMember;
        }

        @NotNull
        public final List getContributions() {
            return this.contributions;
        }

        public ContributorInformation(@NotNull String name, boolean teamMember, @NotNull List contributions) {
            Intrinsics.checkParameterIsNotNull(name, "name");
            Intrinsics.checkParameterIsNotNull(contributions, "contributions");
            super();
            this.name = name;
            this.teamMember = teamMember;
            this.contributions = contributions;
        }
    }

    @Metadata(
        mv = { 1, 1, 16},
        bv = { 1, 0, 3},
        k = 1,
        d1 = { "\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\b\u0080\u0004\u0018\u00002\u00020\u0001B+\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0010\u0010\u0004\u001a\f\u0012\b\u0012\u00060\u0006R\u00020\u00070\u0005\u0012\n\u0010\b\u001a\u00060\tR\u00020\u0007¢\u0006\u0002\u0010\nR\u0015\u0010\b\u001a\u00060\tR\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u001b\u0010\u0004\u001a\f\u0012\b\u0012\u00060\u0006R\u00020\u00070\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010¨\u0006\u0011"},
        d2 = { "Lnet/ccbluex/liquidbounce/ui/client/GuiContributors$GitHubContributor;", "", "totalContributions", "", "weeks", "", "Lnet/ccbluex/liquidbounce/ui/client/GuiContributors$GitHubWeek;", "Lnet/ccbluex/liquidbounce/ui/client/GuiContributors;", "author", "Lnet/ccbluex/liquidbounce/ui/client/GuiContributors$GitHubAuthor;", "(Lnet/ccbluex/liquidbounce/ui/client/GuiContributors;ILjava/util/List;Lnet/ccbluex/liquidbounce/ui/client/GuiContributors$GitHubAuthor;)V", "getAuthor", "()Lnet/ccbluex/liquidbounce/ui/client/GuiContributors$GitHubAuthor;", "getTotalContributions", "()I", "getWeeks", "()Ljava/util/List;", "LiquidBounce"}
    )
    public final class GitHubContributor {

        @SerializedName("total")
        private final int totalContributions;
        @NotNull
        private final List weeks;
        @NotNull
        private final GuiContributors.GitHubAuthor author;

        public final int getTotalContributions() {
            return this.totalContributions;
        }

        @NotNull
        public final List getWeeks() {
            return this.weeks;
        }

        @NotNull
        public final GuiContributors.GitHubAuthor getAuthor() {
            return this.author;
        }

        public GitHubContributor(int totalContributions, @NotNull List weeks, @NotNull GuiContributors.GitHubAuthor author) {
            Intrinsics.checkParameterIsNotNull(weeks, "weeks");
            Intrinsics.checkParameterIsNotNull(author, "author");
            super();
            this.totalContributions = totalContributions;
            this.weeks = weeks;
            this.author = author;
        }
    }

    @Metadata(
        mv = { 1, 1, 16},
        bv = { 1, 0, 3},
        k = 1,
        d1 = { "\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\b\n\u0002\b\n\b\u0080\u0004\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\u0006\u0010\u0007\u001a\u00020\u0005¢\u0006\u0002\u0010\bR\u0016\u0010\u0004\u001a\u00020\u00058\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0016\u0010\u0007\u001a\u00020\u00058\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\nR\u0016\u0010\u0006\u001a\u00020\u00058\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\nR\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u000f"},
        d2 = { "Lnet/ccbluex/liquidbounce/ui/client/GuiContributors$GitHubWeek;", "", "timestamp", "", "additions", "", "deletions", "commits", "(Lnet/ccbluex/liquidbounce/ui/client/GuiContributors;JIII)V", "getAdditions", "()I", "getCommits", "getDeletions", "getTimestamp", "()J", "LiquidBounce"}
    )
    public final class GitHubWeek {

        @SerializedName("w")
        private final long timestamp;
        @SerializedName("a")
        private final int additions;
        @SerializedName("d")
        private final int deletions;
        @SerializedName("c")
        private final int commits;

        public final long getTimestamp() {
            return this.timestamp;
        }

        public final int getAdditions() {
            return this.additions;
        }

        public final int getDeletions() {
            return this.deletions;
        }

        public final int getCommits() {
            return this.commits;
        }

        public GitHubWeek(long timestamp, int additions, int deletions, int commits) {
            this.timestamp = timestamp;
            this.additions = additions;
            this.deletions = deletions;
            this.commits = commits;
        }
    }

    @Metadata(
        mv = { 1, 1, 16},
        bv = { 1, 0, 3},
        k = 1,
        d1 = { "\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\b\b\u0080\u0004\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0003¢\u0006\u0002\u0010\u0007R\u0016\u0010\u0006\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\t¨\u0006\r"},
        d2 = { "Lnet/ccbluex/liquidbounce/ui/client/GuiContributors$GitHubAuthor;", "", "name", "", "id", "", "avatarUrl", "(Lnet/ccbluex/liquidbounce/ui/client/GuiContributors;Ljava/lang/String;ILjava/lang/String;)V", "getAvatarUrl", "()Ljava/lang/String;", "getId", "()I", "getName", "LiquidBounce"}
    )
    public final class GitHubAuthor {

        @SerializedName("login")
        @NotNull
        private final String name;
        private final int id;
        @SerializedName("avatar_url")
        @NotNull
        private final String avatarUrl;

        @NotNull
        public final String getName() {
            return this.name;
        }

        public final int getId() {
            return this.id;
        }

        @NotNull
        public final String getAvatarUrl() {
            return this.avatarUrl;
        }

        public GitHubAuthor(@NotNull String name, int id, @NotNull String avatarUrl) {
            Intrinsics.checkParameterIsNotNull(name, "name");
            Intrinsics.checkParameterIsNotNull(avatarUrl, "avatarUrl");
            super();
            this.name = name;
            this.id = id;
            this.avatarUrl = avatarUrl;
        }
    }

    @Metadata(
        mv = { 1, 1, 16},
        bv = { 1, 0, 3},
        k = 1,
        d1 = { "\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010 \n\u0002\b\u0010\b\u0080\u0004\u0018\u00002\u00020\u0001BM\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\b\u0012\u0006\u0010\n\u001a\u00020\b\u0012\u0006\u0010\u000b\u001a\u00020\f\u0012\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00030\u000e¢\u0006\u0002\u0010\u000fR\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u001c\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0011\u0010\n\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0011R\u0017\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00030\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u0011\u0010\t\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0011R\u0011\u0010\u000b\u001a\u00020\f¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\u001cR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u0017¨\u0006\u001e"},
        d2 = { "Lnet/ccbluex/liquidbounce/ui/client/GuiContributors$Credit;", "", "name", "", "avatarUrl", "avatar", "Lnet/ccbluex/liquidbounce/utils/render/CustomTexture;", "additions", "", "deletions", "commits", "isTeamMember", "", "contributions", "", "(Lnet/ccbluex/liquidbounce/ui/client/GuiContributors;Ljava/lang/String;Ljava/lang/String;Lnet/ccbluex/liquidbounce/utils/render/CustomTexture;IIIZLjava/util/List;)V", "getAdditions", "()I", "getAvatar", "()Lnet/ccbluex/liquidbounce/utils/render/CustomTexture;", "setAvatar", "(Lnet/ccbluex/liquidbounce/utils/render/CustomTexture;)V", "getAvatarUrl", "()Ljava/lang/String;", "getCommits", "getContributions", "()Ljava/util/List;", "getDeletions", "()Z", "getName", "LiquidBounce"}
    )
    public final class Credit {

        @NotNull
        private final String name;
        @NotNull
        private final String avatarUrl;
        @Nullable
        private CustomTexture avatar;
        private final int additions;
        private final int deletions;
        private final int commits;
        private final boolean isTeamMember;
        @NotNull
        private final List contributions;

        @NotNull
        public final String getName() {
            return this.name;
        }

        @NotNull
        public final String getAvatarUrl() {
            return this.avatarUrl;
        }

        @Nullable
        public final CustomTexture getAvatar() {
            return this.avatar;
        }

        public final void setAvatar(@Nullable CustomTexture <set-?>) {
            this.avatar = <set-?>;
        }

        public final int getAdditions() {
            return this.additions;
        }

        public final int getDeletions() {
            return this.deletions;
        }

        public final int getCommits() {
            return this.commits;
        }

        public final boolean isTeamMember() {
            return this.isTeamMember;
        }

        @NotNull
        public final List getContributions() {
            return this.contributions;
        }

        public Credit(@NotNull String name, @NotNull String avatarUrl, @Nullable CustomTexture avatar, int additions, int deletions, int commits, boolean isTeamMember, @NotNull List contributions) {
            Intrinsics.checkParameterIsNotNull(name, "name");
            Intrinsics.checkParameterIsNotNull(avatarUrl, "avatarUrl");
            Intrinsics.checkParameterIsNotNull(contributions, "contributions");
            super();
            this.name = name;
            this.avatarUrl = avatarUrl;
            this.avatar = avatar;
            this.additions = additions;
            this.deletions = deletions;
            this.commits = commits;
            this.isTeamMember = isTeamMember;
            this.contributions = contributions;
        }
    }

    @Metadata(
        mv = { 1, 1, 16},
        bv = { 1, 0, 3},
        k = 1,
        d1 = { "\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\n\n\u0002\u0010\u000b\n\u0002\b\b\b\u0082\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u0007\u001a\u00020\bH\u0016J8\u0010\t\u001a\u00020\b2\u0006\u0010\n\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\u00062\u0006\u0010\f\u001a\u00020\u00062\u0006\u0010\r\u001a\u00020\u00062\u0006\u0010\u000e\u001a\u00020\u00062\u0006\u0010\u000f\u001a\u00020\u0006H\u0016J(\u0010\u0010\u001a\u00020\b2\u0006\u0010\u0011\u001a\u00020\u00062\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00062\u0006\u0010\u0015\u001a\u00020\u0006H\u0016J\r\u0010\u0016\u001a\u00020\u0006H\u0000¢\u0006\u0002\b\u0017J\b\u0010\u0018\u001a\u00020\u0006H\u0016J\u0010\u0010\u0019\u001a\u00020\u00132\u0006\u0010\u001a\u001a\u00020\u0006H\u0016R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u001b"},
        d2 = { "Lnet/ccbluex/liquidbounce/ui/client/GuiContributors$GuiList;", "Lnet/ccbluex/liquidbounce/api/util/WrappedGuiSlot;", "gui", "Lnet/ccbluex/liquidbounce/api/minecraft/client/gui/IGuiScreen;", "(Lnet/ccbluex/liquidbounce/ui/client/GuiContributors;Lnet/ccbluex/liquidbounce/api/minecraft/client/gui/IGuiScreen;)V", "selectedSlot", "", "drawBackground", "", "drawSlot", "entryID", "p_180791_2_", "p_180791_3_", "p_180791_4_", "mouseXIn", "mouseYIn", "elementClicked", "index", "doubleClick", "", "var3", "var4", "getSelectedSlot", "getSelectedSlot$LiquidBounce", "getSize", "isSelected", "id", "LiquidBounce"}
    )
    private final class GuiList extends WrappedGuiSlot {

        private int selectedSlot;

        public boolean isSelected(int id) {
            return this.selectedSlot == id;
        }

        public int getSize() {
            return GuiContributors.this.credits.size();
        }

        public final int getSelectedSlot$LiquidBounce() {
            return this.selectedSlot > GuiContributors.this.credits.size() ? -1 : this.selectedSlot;
        }

        public void elementClicked(int index, boolean doubleClick, int i, int j) {
            this.selectedSlot = index;
        }

        public void drawSlot(int entryID, int p_180791_2_, int p_180791_3_, int p_180791_4_, int mouseXIn, int mouseYIn) {
            GuiContributors.Credit credit = (GuiContributors.Credit) GuiContributors.this.credits.get(entryID);
            IFontRenderer ifontrenderer = Fonts.font40;
            String s = credit.getName();
            float f = (float) this.getRepresented().getWidth() / 2.0F;
            float f1 = (float) p_180791_3_ + 2.0F;
            Color color = Color.WHITE;

            Intrinsics.checkExpressionValueIsNotNull(Color.WHITE, "Color.WHITE");
            ifontrenderer.drawCenteredString(s, f, f1, color.getRGB(), true);
        }

        public void drawBackground() {}

        public GuiList(@NotNull IGuiScreen gui) {
            Intrinsics.checkParameterIsNotNull(gui, "gui");
            IMinecraft iminecraft = MinecraftInstance.mc;

            Intrinsics.checkExpressionValueIsNotNull(MinecraftInstance.mc, "mc");
            super(iminecraft, gui.getWidth() / 4, gui.getHeight(), 40, gui.getHeight() - 40, 15);
            this.getRepresented().setListWidth(gui.getWidth() * 3 / 13);
            this.getRepresented().setEnableScissor(true);
        }
    }
}
