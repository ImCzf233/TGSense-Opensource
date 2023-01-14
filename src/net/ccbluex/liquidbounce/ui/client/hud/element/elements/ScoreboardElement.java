package net.ccbluex.liquidbounce.ui.client.hud.element.elements;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.ThreadLocalRandom;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref.IntRef;
import kotlin.jvm.internal.Ref.ObjectRef;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import me.Skid.utils.ShadowUtils;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IFontRenderer;
import net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IWorldClient;
import net.ccbluex.liquidbounce.api.minecraft.scoreboard.IScore;
import net.ccbluex.liquidbounce.api.minecraft.scoreboard.IScoreObjective;
import net.ccbluex.liquidbounce.api.minecraft.scoreboard.IScoreboard;
import net.ccbluex.liquidbounce.api.minecraft.scoreboard.ITeam;
import net.ccbluex.liquidbounce.api.minecraft.util.WEnumChatFormatting;
import net.ccbluex.liquidbounce.ui.client.hud.element.Border;
import net.ccbluex.liquidbounce.ui.client.hud.element.Element;
import net.ccbluex.liquidbounce.ui.client.hud.element.ElementInfo;
import net.ccbluex.liquidbounce.ui.client.hud.element.Side;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.render.ColorUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.FontValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@ElementInfo(
    name = "Scoreboard"
)
@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000`\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001B-\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0006\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\b\u0010*\u001a\u00020+H\u0002J\n\u0010,\u001a\u0004\u0018\u00010-H\u0016J\b\u0010.\u001a\u00020+H\u0002R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\rX\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u000fR\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0017X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u000eX\u0082D¢\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\u001eX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001f\u001a\u00020\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010 \u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010!\u001a\u00020\u001eX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\"\u001a\u00020#¢\u0006\b\n\u0000\u001a\u0004\b$\u0010%R\u000e\u0010&\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\'\u001a\u00020\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010(\u001a\u00020\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010)\u001a\u00020\u0011X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006/"},
    d2 = { "Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/ScoreboardElement;", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Element;", "x", "", "y", "scale", "", "side", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Side;", "(DDFLnet/ccbluex/liquidbounce/ui/client/hud/element/Side;)V", "ShadowValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "allowedDomains", "", "", "[Ljava/lang/String;", "backgroundColorAlphaValue", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "backgroundColorBlueValue", "backgroundColorGreenValue", "backgroundColorRedValue", "fakeKill", "fontValue", "Lnet/ccbluex/liquidbounce/value/FontValue;", "hytRegex", "noPointValue", "rectColorBlueAlpha", "rectColorBlueValue", "rectColorGreenValue", "rectColorModeValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "rectColorRedValue", "rectValue", "serverValue", "shadowStrength", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "getShadowStrength", "()Lnet/ccbluex/liquidbounce/value/FloatValue;", "shadowValue", "textBlueValue", "textGreenValue", "textRedValue", "backgroundColor", "Ljava/awt/Color;", "drawElement", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Border;", "textColor", "LiquidBounce"}
)
public final class ScoreboardElement extends Element {

    private final IntegerValue textRedValue;
    private final IntegerValue textGreenValue;
    private final IntegerValue textBlueValue;
    private final IntegerValue backgroundColorRedValue;
    private final IntegerValue backgroundColorGreenValue;
    private final IntegerValue backgroundColorBlueValue;
    private final IntegerValue backgroundColorAlphaValue;
    private final BoolValue ShadowValue;
    @NotNull
    private final FloatValue shadowStrength;
    private final BoolValue rectValue;
    private final ListValue rectColorModeValue;
    private final IntegerValue rectColorRedValue;
    private final IntegerValue rectColorGreenValue;
    private final IntegerValue rectColorBlueValue;
    private final IntegerValue rectColorBlueAlpha;
    private final BoolValue shadowValue;
    private final ListValue serverValue;
    private final BoolValue noPointValue;
    private final BoolValue fakeKill;
    private final FontValue fontValue;
    private final String[] allowedDomains;
    private final String hytRegex;

    @NotNull
    public final FloatValue getShadowStrength() {
        return this.shadowStrength;
    }

    @Nullable
    public Border drawElement() {
        IFontRenderer fontRenderer = (IFontRenderer) this.fontValue.get();
        int textColor = this.textColor().getRGB();
        int backColor = this.backgroundColor().getRGB();
        String rectColorMode = (String) this.rectColorModeValue.get();
        int rectCustomColor = (new Color(((Number) this.rectColorRedValue.get()).intValue(), ((Number) this.rectColorGreenValue.get()).intValue(), ((Number) this.rectColorBlueValue.get()).intValue(), ((Number) this.rectColorBlueAlpha.get()).intValue())).getRGB();
        IWorldClient iworldclient = MinecraftInstance.mc.getTheWorld();

        if (iworldclient == null) {
            Intrinsics.throwNpe();
        }

        IScoreboard worldScoreboard = iworldclient.getScoreboard();
        IScoreObjective currObjective = (IScoreObjective) null;
        IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

        if (ientityplayersp == null) {
            Intrinsics.throwNpe();
        }

        ITeam playerTeam = worldScoreboard.getPlayersTeam(ientityplayersp.getName());

        if (playerTeam != null) {
            int objective = playerTeam.getChatFormat().getColorIndex();

            if (objective >= 0) {
                currObjective = worldScoreboard.getObjectiveInDisplaySlot(3 + objective);
            }
        }

        IScoreObjective iscoreobjective = currObjective;

        if (currObjective == null) {
            iscoreobjective = worldScoreboard.getObjectiveInDisplaySlot(1);
        }

        if (iscoreobjective == null) {
            return null;
        } else {
            IScoreObjective iscoreobjective1 = iscoreobjective;
            IScoreboard scoreboard = iscoreobjective1.getScoreboard();
            ObjectRef scoreCollection = new ObjectRef();

            scoreCollection.element = scoreboard.getSortedScores(iscoreobjective1);
            ArrayList scores = Lists.newArrayList(Iterables.filter((Iterable) ((Collection) scoreCollection.element), (Predicate) null.INSTANCE));
            Collection collection;

            if (scores.size() > 15) {
                ArrayList arraylist = Lists.newArrayList(Iterables.skip((Iterable) scores, ((Collection) scoreCollection.element).size() - 15));

                Intrinsics.checkExpressionValueIsNotNull(arraylist, "Lists.newArrayList(Itera…oreCollection.size - 15))");
                collection = (Collection) arraylist;
            } else {
                Intrinsics.checkExpressionValueIsNotNull(scores, "scores");
                collection = (Collection) scores;
            }

            scoreCollection.element = collection;
            IntRef maxWidth = new IntRef();

            maxWidth.element = fontRenderer.getStringWidth(iscoreobjective1.getDisplayName());

            String $i$f$forEachIndexed;

            for (Iterator l1 = ((ArrayList) ((Collection) scoreCollection.element)).iterator(); l1.hasNext(); maxWidth.element = RangesKt.coerceAtLeast(maxWidth.element, fontRenderer.getStringWidth($i$f$forEachIndexed))) {
                IScore maxHeight = (IScore) l1.next();
                ITeam $this$forEachIndexed$iv = scoreboard.getPlayersTeam(maxHeight.getPlayerName());

                $i$f$forEachIndexed = MinecraftInstance.functions.scoreboardFormatPlayerName($this$forEachIndexed$iv, maxHeight.getPlayerName()) + ": " + WEnumChatFormatting.RED + maxHeight.getScorePoints();
            }

            int i = ((Collection) scoreCollection.element).size() * fontRenderer.getFontHeight();
            int j = -maxWidth.element - 3 - (((Boolean) this.rectValue.get()).booleanValue() ? 3 : 0);

            Gui.drawRect(j - 7, -5, 9, i + fontRenderer.getFontHeight() + 5, backColor);
            RenderUtils.drawShadowWithCustomAlpha((float) j - 7.0F, -5.0F, (float) (-j) + 16.0F, (float) (i + fontRenderer.getFontHeight()) + 10.0F, 255.0F);
            Iterable iterable = (Iterable) ((Collection) scoreCollection.element);
            boolean flag = false;
            int index$iv = 0;
            Iterator iterator = iterable.iterator();

            while (iterator.hasNext()) {
                Object item$iv = iterator.next();
                int k = index$iv++;
                boolean flag1 = false;

                if (k < 0) {
                    CollectionsKt.throwIndexOverflow();
                }

                IScore score = (IScore) item$iv;
                boolean $i$a$-forEachIndexed-ScoreboardElement$drawElement$1 = false;
                ITeam team = scoreboard.getPlayersTeam(score.getPlayerName());
                String name = MinecraftInstance.functions.scoreboardFormatPlayerName(team, score.getPlayerName());
                String scorePoints = "" + WEnumChatFormatting.RED + score.getScorePoints();
                int width = 5 - (((Boolean) this.rectValue.get()).booleanValue() ? 4 : 0);
                int height = i - k * fontRenderer.getFontHeight();

                GlStateManager.resetColor();
                int listColor = textColor;
                String rectColor;

                if (!this.serverValue.equals("none")) {
                    String[] astring = this.allowedDomains;
                    int l = astring.length;

                    for (int i1 = 0; i1 < l; ++i1) {
                        rectColor = astring[i1];
                        if (StringsKt.contains((CharSequence) name, (CharSequence) rectColor, true)) {
                            String s = (String) this.serverValue.get();
                            boolean flag2 = false;

                            if (s == null) {
                                throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                            }

                            String s1 = s.toLowerCase();

                            Intrinsics.checkExpressionValueIsNotNull(s1, "(this as java.lang.String).toLowerCase()");
                            s = s1;
                            switch (s.hashCode()) {
                            case 1103204566:
                                if (s.equals("clientname")) {
                                    s1 = "TGSense";
                                    break;
                                }

                            default:
                                s1 = "null";
                            }

                            name = s1;
                            listColor = ColorUtils.rainbow().getRGB();
                            break;
                        }

                        if (((Boolean) this.fakeKill.get()).booleanValue() && StringsKt.contains$default((CharSequence) name, (CharSequence) this.hytRegex, false, 2, (Object) null)) {
                            name = "�?�?:" + ThreadLocalRandom.current().nextInt(90, 1000) + " / 死亡:0";
                        }
                    }
                }

                fontRenderer.drawString(name, (float) j, (float) height, listColor, ((Boolean) this.shadowValue.get()).booleanValue());
                if (!((Boolean) this.noPointValue.get()).booleanValue()) {
                    fontRenderer.drawString(scorePoints, (float) (width - fontRenderer.getStringWidth(scorePoints)), (float) height, textColor, ((Boolean) this.shadowValue.get()).booleanValue());
                }

                if (((Boolean) this.ShadowValue.get()).booleanValue()) {
                    ShadowUtils.INSTANCE.shadow(((Number) this.shadowStrength.get()).floatValue(), (Function0) (new ScoreboardElement$drawElement$$inlined$forEachIndexed$lambda$1(this, scoreboard, i, fontRenderer, textColor, j, scoreCollection, iscoreobjective1, maxWidth, rectColorMode, rectCustomColor)), (Function0) (new ScoreboardElement$drawElement$$inlined$forEachIndexed$lambda$2(this, scoreboard, i, fontRenderer, textColor, j, scoreCollection, iscoreobjective1, maxWidth, rectColorMode, rectCustomColor)));
                }

                if (k == ((Collection) scoreCollection.element).size() - 1) {
                    rectColor = iscoreobjective1.getDisplayName();
                    GlStateManager.resetColor();
                    fontRenderer.drawString(rectColor, (float) (j + maxWidth.element / 2 - fontRenderer.getStringWidth(rectColor) / 2), (float) (height - fontRenderer.getFontHeight()), textColor, ((Boolean) this.shadowValue.get()).booleanValue());
                }

                if (((Boolean) this.rectValue.get()).booleanValue()) {
                    int j1 = StringsKt.equals(rectColorMode, "Rainbow", true) ? ColorUtils.rainbow(k).getRGB() : rectCustomColor;

                    RenderUtils.drawRect(2.0F, k == ((Collection) scoreCollection.element).size() - 1 ? -2.0F : (float) height, 5.0F, k == 0 ? (float) fontRenderer.getFontHeight() : (float) height + (float) fontRenderer.getFontHeight() * 2.0F, j1);
                }
            }

            return new Border(-((float) maxWidth.element) - 10.0F - (float) (((Boolean) this.rectValue.get()).booleanValue() ? 3 : 0), -5.0F, 9.0F, (float) i + (float) fontRenderer.getFontHeight() + (float) 5);
        }
    }

    private final Color backgroundColor() {
        return new Color(((Number) this.backgroundColorRedValue.get()).intValue(), ((Number) this.backgroundColorGreenValue.get()).intValue(), ((Number) this.backgroundColorBlueValue.get()).intValue(), ((Number) this.backgroundColorAlphaValue.get()).intValue());
    }

    private final Color textColor() {
        return new Color(((Number) this.textRedValue.get()).intValue(), ((Number) this.textGreenValue.get()).intValue(), ((Number) this.textBlueValue.get()).intValue());
    }

    public ScoreboardElement(double x, double y, float scale, @NotNull Side side) {
        Intrinsics.checkParameterIsNotNull(side, "side");
        super(x, y, scale, side);
        this.textRedValue = new IntegerValue("Text-R", 255, 0, 255);
        this.textGreenValue = new IntegerValue("Text-G", 255, 0, 255);
        this.textBlueValue = new IntegerValue("Text-B", 255, 0, 255);
        this.backgroundColorRedValue = new IntegerValue("Background-R", 0, 0, 255);
        this.backgroundColorGreenValue = new IntegerValue("Background-G", 0, 0, 255);
        this.backgroundColorBlueValue = new IntegerValue("Background-B", 0, 0, 255);
        this.backgroundColorAlphaValue = new IntegerValue("Background-Alpha", 95, 0, 255);
        this.ShadowValue = new BoolValue("Shadow", false);
        this.shadowStrength = new FloatValue("Shadow-Strength", 1.0F, 0.01F, 40.0F);
        this.rectValue = new BoolValue("Rect", false);
        this.rectColorModeValue = new ListValue("Rect-Color", new String[] { "Custom", "Rainbow"}, "Custom");
        this.rectColorRedValue = new IntegerValue("Rect-R", 0, 0, 255);
        this.rectColorGreenValue = new IntegerValue("Rect-G", 111, 0, 255);
        this.rectColorBlueValue = new IntegerValue("Rect-B", 255, 0, 255);
        this.rectColorBlueAlpha = new IntegerValue("Rect-Alpha", 255, 0, 255);
        this.shadowValue = new BoolValue("FontShadow", false);
        this.serverValue = new ListValue("ServerIp", new String[] { "None", "ClientName"}, "ClientName");
        this.noPointValue = new BoolValue("NoPoints", false);
        this.fakeKill = new BoolValue("FakeKill", false);
        IFontRenderer ifontrenderer = Fonts.minecraftFont;

        Intrinsics.checkExpressionValueIsNotNull(Fonts.minecraftFont, "Fonts.minecraftFont");
        this.fontValue = new FontValue("Font", ifontrenderer);
        this.allowedDomains = new String[] { ".ac", ".academy", ".accountant", ".accountants", ".actor", ".adult", ".ag", ".agency", ".ai", ".airforce", ".am", ".amsterdam", ".apartments", ".app", ".archi", ".army", ".art", ".asia", ".associates", ".at", ".attorney", ".au", ".auction", ".auto", ".autos", ".baby", ".band", ".bar", ".barcelona", ".bargains", ".bayern", ".be", ".beauty", ".beer", ".berlin", ".best", ".bet", ".bid", ".bike", ".bingo", ".bio", ".biz", ".biz.pl", ".black", ".blog", ".blue", ".boats", ".boston", ".boutique", ".build", ".builders", ".business", ".buzz", ".bz", ".ca", ".cab", ".cafe", ".camera", ".camp", ".capital", ".car", ".cards", ".care", ".careers", ".cars", ".casa", ".cash", ".casino", ".catering", ".cc", ".center", ".ceo", ".ch", ".charity", ".chat", ".cheap", ".church", ".city", ".cl", ".claims", ".cleaning", ".clinic", ".clothing", ".cloud", ".club", ".cn", ".co", ".co.in", ".co.jp", ".co.kr", ".co.nz", ".co.uk", ".co.za", ".coach", ".codes", ".coffee", ".college", ".com", ".com.ag", ".com.au", ".com.br", ".com.bz", ".com.cn", ".com.co", ".com.es", ".com.mx", ".com.pe", ".com.ph", ".com.pl", ".com.ru", ".com.tw", ".community", ".company", ".computer", ".condos", ".construction", ".consulting", ".contact", ".contractors", ".cooking", ".cool", ".country", ".coupons", ".courses", ".credit", ".creditcard", ".cricket", ".cruises", ".cymru", ".cz", ".dance", ".date", ".dating", ".de", ".deals", ".degree", ".delivery", ".democrat", ".dental", ".dentist", ".design", ".dev", ".diamonds", ".digital", ".direct", ".directory", ".discount", ".dk", ".doctor", ".dog", ".domains", ".download", ".earth", ".education", ".email", ".energy", ".engineer", ".engineering", ".enterprises", ".equipment", ".es", ".estate", ".eu", ".events", ".exchange", ".expert", ".exposed", ".express", ".fail", ".faith", ".family", ".fan", ".fans", ".farm", ".fashion", ".film", ".finance", ".financial", ".firm.in", ".fish", ".fishing", ".fit", ".fitness", ".flights", ".florist", ".fm", ".football", ".forsale", ".foundation", ".fr", ".fun", ".fund", ".furniture", ".futbol", ".fyi", ".gallery", ".games", ".garden", ".gay", ".gen.in", ".gg", ".gifts", ".gives", ".glass", ".global", ".gmbh", ".gold", ".golf", ".graphics", ".gratis", ".green", ".gripe", ".group", ".gs", ".guide", ".guru", ".hair", ".haus", ".health", ".healthcare", ".hockey", ".holdings", ".holiday", ".homes", ".horse", ".hospital", ".host", ".house", ".idv.tw", ".immo", ".immobilien", ".in", ".inc", ".ind.in", ".industries", ".info", ".info.pl", ".ink", ".institute", ".insure", ".international", ".investments", ".io", ".irish", ".ist", ".istanbul", ".it", ".jetzt", ".jewelry", ".jobs", ".jp", ".kaufen", ".kim", ".kitchen", ".kiwi", ".kr", ".la", ".land", ".law", ".lawyer", ".lease", ".legal", ".lgbt", ".life", ".lighting", ".limited", ".limo", ".live", ".llc", ".loan", ".loans", ".london", ".love", ".ltd", ".ltda", ".luxury", ".maison", ".makeup", ".management", ".market", ".marketing", ".mba", ".me", ".me.uk", ".media", ".melbourne", ".memorial", ".men", ".menu", ".miami", ".mobi", ".moda", ".moe", ".money", ".monster", ".mortgage", ".motorcycles", ".movie", ".ms", ".mx", ".nagoya", ".name", ".navy", ".ne.kr", ".net", ".net.ag", ".net.au", ".net.br", ".net.bz", ".net.cn", ".net.co", ".net.in", ".net.nz", ".net.pe", ".net.ph", ".net.pl", ".net.ru", ".network", ".news", ".ninja", ".nl", ".no", ".nom.co", ".nom.es", ".nom.pe", ".nrw", ".nyc", ".okinawa", ".one", ".onl", ".online", ".org", ".org.ag", ".org.au", ".org.cn", ".org.es", ".org.in", ".org.nz", ".org.pe", ".org.ph", ".org.pl", ".org.ru", ".org.uk", ".page", ".paris", ".partners", ".parts", ".party", ".pe", ".pet", ".ph", ".photography", ".photos", ".pictures", ".pink", ".pizza", ".pl", ".place", ".plumbing", ".plus", ".poker", ".porn", ".press", ".pro", ".productions", ".promo", ".properties", ".protection", ".pub", ".pw", ".quebec", ".quest", ".racing", ".re.kr", ".realestate", ".recipes", ".red", ".rehab", ".reise", ".reisen", ".rent", ".rentals", ".repair", ".report", ".republican", ".rest", ".restaurant", ".review", ".reviews", ".rich", ".rip", ".rocks", ".rodeo", ".ru", ".run", ".ryukyu", ".sale", ".salon", ".sarl", ".school", ".schule", ".science", ".se", ".security", ".services", ".sex", ".sg", ".sh", ".shiksha", ".shoes", ".shop", ".shopping", ".show", ".singles", ".site", ".ski", ".skin", ".soccer", ".social", ".software", ".solar", ".solutions", ".space", ".storage", ".store", ".stream", ".studio", ".study", ".style", ".supplies", ".supply", ".support", ".surf", ".surgery", ".sydney", ".systems", ".tax", ".taxi", ".team", ".tech", ".technology", ".tel", ".tennis", ".theater", ".theatre", ".tienda", ".tips", ".tires", ".today", ".tokyo", ".tools", ".tours", ".town", ".toys", ".top", ".trade", ".training", ".travel", ".tube", ".tv", ".tw", ".uk", ".university", ".uno", ".us", ".vacations", ".vegas", ".ventures", ".vet", ".viajes", ".video", ".villas", ".vin", ".vip", ".vision", ".vodka", ".vote", ".voto", ".voyage", ".wales", ".watch", ".webcam", ".website", ".wedding", ".wiki", ".win", ".wine", ".work", ".works", ".world", ".ws", ".wtf", ".xxx", ".xyz", ".yachts", ".yoga", ".yokohama", ".zone", "花雨�?", "QQ"};
        this.hytRegex = "�?�?";
    }

    public ScoreboardElement(double d0, double d1, float f, Side side, int i, DefaultConstructorMarker defaultconstructormarker) {
        if ((i & 1) != 0) {
            d0 = 5.0D;
        }

        if ((i & 2) != 0) {
            d1 = 0.0D;
        }

        if ((i & 4) != 0) {
            f = 1.0F;
        }

        if ((i & 8) != 0) {
            side = new Side(Side.Horizontal.RIGHT, Side.Vertical.MIDDLE);
        }

        this(d0, d1, f, side);
    }

    public ScoreboardElement() {
        this(0.0D, 0.0D, 0.0F, (Side) null, 15, (DefaultConstructorMarker) null);
    }
}
