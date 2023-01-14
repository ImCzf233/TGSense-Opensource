package me.Skid.ui.elements;

import com.mojang.realmsclient.gui.ChatFormatting;
import java.math.BigDecimal;
import me.Skid.novoline.impl.Fonts;
import me.Skid.novoline.util.MathematicHelper;
import net.ccbluex.liquidbounce.ui.client.hud.element.Border;
import net.ccbluex.liquidbounce.ui.client.hud.element.Element;
import net.ccbluex.liquidbounce.ui.client.hud.element.ElementInfo;

@ElementInfo(
    name = "WorldInfo"
)
public class WorldInfo extends Element {

    float x = 5.0F;
    float y = 300.0F;
    float Width = 88.0F;
    float Height = 20.0F;

    public Border drawElement() {
        double prevPosX = WorldInfo.mc2.player.posX - WorldInfo.mc2.player.prevPosX;
        double prevPosZ = WorldInfo.mc2.player.posZ - WorldInfo.mc2.player.prevPosZ;
        float distance = (float) Math.sqrt(prevPosX * prevPosX + prevPosZ * prevPosZ);
        BigDecimal speedValue = MathematicHelper.round(distance * 15.5F, 1);

        Fonts.SFBOLD.SFBOLD_20.SFBOLD_20.drawString("XYZ: " + ChatFormatting.WHITE + Math.round(WorldInfo.mc2.player.posX) + " " + Math.round(WorldInfo.mc2.player.posY) + " " + Math.round(WorldInfo.mc2.player.posZ), this.x, this.y + 18.0F, -1, true);
        return new Border(this.x, this.y, this.Width, this.Height);
    }
}
