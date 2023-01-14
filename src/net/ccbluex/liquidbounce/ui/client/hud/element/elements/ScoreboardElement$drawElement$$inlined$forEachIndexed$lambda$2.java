package net.ccbluex.liquidbounce.ui.client.hud.element.elements;

import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.Ref.IntRef;
import kotlin.jvm.internal.Ref.ObjectRef;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IFontRenderer;
import net.ccbluex.liquidbounce.api.minecraft.scoreboard.IScoreObjective;
import net.ccbluex.liquidbounce.api.minecraft.scoreboard.IScoreboard;
import org.lwjgl.opengl.GL11;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 3,
    d1 = { "\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u0001H\n¢\u0006\u0002\b\u0002¨\u0006\u0003"},
    d2 = { "<anonymous>", "", "invoke", "net/ccbluex/liquidbounce/ui/client/hud/element/elements/ScoreboardElement$drawElement$1$2"}
)
final class ScoreboardElement$drawElement$$inlined$forEachIndexed$lambda$2 extends Lambda implements Function0 {

    final ScoreboardElement this$0;
    final IScoreboard $scoreboard$inlined;
    final int $maxHeight$inlined;
    final IFontRenderer $fontRenderer$inlined;
    final int $textColor$inlined;
    final int $l1$inlined;
    final ObjectRef $scoreCollection$inlined;
    final IScoreObjective $objective$inlined;
    final IntRef $maxWidth$inlined;
    final String $rectColorMode$inlined;
    final int $rectCustomColor$inlined;

    ScoreboardElement$drawElement$$inlined$forEachIndexed$lambda$2(ScoreboardElement scoreboardelement, IScoreboard iscoreboard, int i, IFontRenderer ifontrenderer, int j, int k, ObjectRef objectref, IScoreObjective iscoreobjective, IntRef intref, String s, int l) {
        super(0);
        this.this$0 = scoreboardelement;
        this.$scoreboard$inlined = iscoreboard;
        this.$maxHeight$inlined = i;
        this.$fontRenderer$inlined = ifontrenderer;
        this.$textColor$inlined = j;
        this.$l1$inlined = k;
        this.$scoreCollection$inlined = objectref;
        this.$objective$inlined = iscoreobjective;
        this.$maxWidth$inlined = intref;
        this.$rectColorMode$inlined = s;
        this.$rectCustomColor$inlined = l;
    }

    public final void invoke() {
        GL11.glPushMatrix();
        GL11.glTranslated(this.this$0.getRenderX(), this.this$0.getRenderY(), 0.0D);
        GL11.glPopMatrix();
    }
}
