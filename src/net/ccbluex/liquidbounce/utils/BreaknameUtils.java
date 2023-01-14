package net.ccbluex.liquidbounce.utils;

import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.features.module.Module;
import org.jetbrains.annotations.NotNull;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006¨\u0006\u0007"},
    d2 = { "Lnet/ccbluex/liquidbounce/utils/BreaknameUtils;", "", "()V", "breakname", "", "module", "Lnet/ccbluex/liquidbounce/features/module/Module;", "LiquidBounce"}
)
public final class BreaknameUtils {

    public static final BreaknameUtils INSTANCE;

    @NotNull
    public final String breakname(@NotNull Module module) {
        Intrinsics.checkParameterIsNotNull(module, "module");
        String detName = module.getName();
        boolean flag = false;

        if (detName == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        } else {
            String s = detName.toLowerCase();

            Intrinsics.checkExpressionValueIsNotNull(s, "(this as java.lang.String).toLowerCase()");
            String s1 = s;

            switch (s1.hashCode()) {
            case -2117682893:
                if (s1.equals("antiblind")) {
                    return "Anti Blind";
                }
                break;

            case -2073748310:
                if (s1.equals("longjump")) {
                    return "Long Jump";
                }
                break;

            case -2011673611:
                if (s1.equals("cameraclip")) {
                    return "Camere Clip";
                }
                break;

            case -1958347623:
                if (s1.equals("autorespawn")) {
                    return "Auto Respawn";
                }
                break;

            case -1940969246:
                if (s1.equals("potionsaver")) {
                    return "Potion Saver";
                }
                break;

            case -1800963612:
                if (s1.equals("nameprotect")) {
                    return "Name Protect";
                }
                break;

            case -1784198274:
                if (s1.equals("ghosthand")) {
                    return "Ghost Hand";
                }
                break;

            case -1755593056:
                if (s1.equals("superknockback")) {
                    return "Super Knockback";
                }
                break;

            case -1745954712:
                if (s1.equals("keepalive")) {
                    return "Keep Alive";
                }
                break;

            case -1736460397:
                if (s1.equals("tntblock")) {
                    return "TNT Block";
                }
                break;

            case -1684590384:
                if (s1.equals("highjump")) {
                    return "High Jump";
                }
                break;

            case -1683752826:
                if (s1.equals("norotateset")) {
                    return "No Rotate Set";
                }
                break;

            case -1673170764:
                if (s1.equals("nofriends")) {
                    return "No Friends";
                }
                break;

            case -1650930624:
                if (s1.equals("midclick")) {
                    return "Mid Click";
                }
                break;

            case -1641764582:
                if (s1.equals("speedmine")) {
                    return "Speed Mine";
                }
                break;

            case -1618493804:
                if (s1.equals("liquidchat")) {
                    return "Liquid Chat";
                }
                break;

            case -1617904379:
                if (s1.equals("liquidwalk")) {
                    return "Liquid Walk";
                }
                break;

            case -1563243394:
                if (s1.equals("bedgodmode")) {
                    return "Bed God Mode";
                }
                break;

            case -1479887745:
                if (s1.equals("atallprovider")) {
                    return "At All Provider";
                }
                break;

            case -1411744210:
                if (s1.equals("itemrotate")) {
                    return "Item Rotate";
                }
                break;

            case -1384588753:
                if (s1.equals("civbreak")) {
                    return "Civ Break";
                }
                break;

            case -1373800418:
                if (s1.equals("perfecthorsejump")) {
                    return "Perfect Horse Jump";
                }
                break;

            case -1250517206:
                if (s1.equals("enchanteffect")) {
                    return "Enchant Effect";
                }
                break;

            case -1217012392:
                if (s1.equals("hitbox")) {
                    return "Hit Box";
                }
                break;

            case -1205362161:
                if (s1.equals("anticactus")) {
                    return "Anti Cactus";
                }
                break;

            case -1202220504:
                if (s1.equals("hytrun")) {
                    return "HYT Run";
                }
                break;

            case -1077118994:
                if (s1.equals("fastbow")) {
                    return "Fast Bow";
                }
                break;

            case -1077100629:
                if (s1.equals("fastuse")) {
                    return "Fast Use";
                }
                break;

            case -1056278929:
                if (s1.equals("killeffect")) {
                    return "Kill Effect";
                }
                break;

            case -1043431275:
                if (s1.equals("antihunger")) {
                    return "Anti Hunger";
                }
                break;

            case -1040193391:
                if (s1.equals("noclip")) {
                    return "No Clip";
                }
                break;

            case -1040114500:
                if (s1.equals("nofall")) {
                    return "No Fall";
                }
                break;

            case -1039716542:
                if (s1.equals("noslow")) {
                    return "No Slow";
                }
                break;

            case -1018771300:
                if (s1.equals("icespeed")) {
                    return "Ice Speed";
                }
                break;

            case -1006627833:
                if (s1.equals("bufferspeed")) {
                    return "Buffer Speed";
                }
                break;

            case -991840136:
                if (s1.equals("airjump")) {
                    return "Air Jump";
                }
                break;

            case -868841976:
                if (s1.equals("tntesp")) {
                    return "TNT ESP";
                }
                break;

            case -867545633:
                if (s1.equals("tpaura")) {
                    return "Tp Aura";
                }
                break;

            case -866246404:
                if (s1.equals("nopitchlimit")) {
                    return "No Pitch Limit";
                }
                break;

            case -846896572:
                if (s1.equals("antiafk")) {
                    return "Anti AFK";
                }
                break;

            case -846895323:
                if (s1.equals("antibot")) {
                    return "Anti Bot";
                }
                break;

            case -815567418:
                if (s1.equals("targethud")) {
                    return "Target HUD";
                }
                break;

            case -796414146:
                if (s1.equals("itemteleport")) {
                    return "Item Teleport";
                }
                break;

            case -664576555:
                if (s1.equals("blockesp")) {
                    return "Block ESP";
                }
                break;

            case -664575802:
                if (s1.equals("blockfly")) {
                    return "Block Fly";
                }
                break;

            case -646312517:
                if (s1.equals("autobow")) {
                    return "Auto Bow";
                }
                break;

            case -646299066:
                if (s1.equals("autopot")) {
                    return "Auto Pot";
                }
                break;

            case -642273901:
                if (s1.equals("blurmodule")) {
                    return "Blur Module";
                }
                break;

            case -604554815:
                if (s1.equals("killaura")) {
                    return "Kill Aura";
                }
                break;

            case -603799069:
                if (s1.equals("freecam")) {
                    return "Free Cam";
                }
                break;

            case -584901197:
                if (s1.equals("abortbreaking")) {
                    return "Abort Breaking";
                }
                break;

            case -538145034:
                if (s1.equals("slimejump")) {
                    return "Slime Jump";
                }
                break;

            case -528921824:
                if (s1.equals("componentonhover")) {
                    return "Component On Hover";
                }
                break;

            case -483845667:
                if (s1.equals("antifall")) {
                    return "Anti Fall";
                }
                break;

            case -415051356:
                if (s1.equals("multiactions")) {
                    return "Multi Actions";
                }
                break;

            case -360334121:
                if (s1.equals("pingspoof")) {
                    return "Ping Spoof";
                }
                break;

            case -260519514:
                if (s1.equals("faststairs")) {
                    return "Fast Stairs";
                }
                break;

            case -246859590:
                if (s1.equals("consolespammer")) {
                    return "Console Spammer";
                }
                break;

            case -194656180:
                if (s1.equals("prophuntesp")) {
                    return "Prophunt ESP";
                }
                break;

            case -60613723:
                if (s1.equals("invcleaner")) {
                    return "Inv Cleaner";
                }
                break;

            case -58684039:
                if (s1.equals("lagback")) {
                    return "Lag Back";
                }
                break;

            case -24159709:
                if (s1.equals("fastbreak")) {
                    return "Fast Break";
                }
                break;

            case -23410727:
                if (s1.equals("fastclimb")) {
                    return "Fast Climb";
                }
                break;

            case -11412949:
                if (s1.equals("fastplace")) {
                    return "Fast Place";
                }
                break;

            case 94093327:
                if (s1.equals("bugup")) {
                    return "Bug Up";
                }
                break;

            case 104991828:
                if (s1.equals("nobob")) {
                    return "No Bob";
                }
                break;

            case 104995692:
                if (s1.equals("nofov")) {
                    return "No Fov";
                }
                break;

            case 105011699:
                if (s1.equals("noweb")) {
                    return "No Web";
                }
                break;

            case 130029634:
                if (s1.equals("resourcepackspoof")) {
                    return "Resourcepack Spoof";
                }
                break;

            case 196471935:
                if (s1.equals("nohurtcam")) {
                    return "No HurtCam";
                }
                break;

            case 255004020:
                if (s1.equals("nojumpdelay")) {
                    return "No Jump Delay";
                }
                break;

            case 257638572:
                if (s1.equals("bowaimbot")) {
                    return "Bow Aimbot";
                }
                break;

            case 269584331:
                if (s1.equals("portalmenu")) {
                    return "Portal Menu";
                }
                break;

            case 326577179:
                if (s1.equals("cheststealer")) {
                    return "Chest Stealer";
                }
                break;

            case 336056817:
                if (s1.equals("servercrasher")) {
                    return "Server Crasher";
                }
                break;

            case 341336829:
                if (s1.equals("noslowbreak")) {
                    return "No Slow Break";
                }
                break;

            case 362880492:
                if (s1.equals("airladder")) {
                    return "Air Ladder";
                }
                break;

            case 375554528:
                if (s1.equals("targetstrafe")) {
                    return "Target Strafe";
                }
                break;

            case 452990340:
                if (s1.equals("dmgparticle")) {
                    return "DMG Particle";
                }
                break;

            case 487322031:
                if (s1.equals("targetlist")) {
                    return "Target List";
                }
                break;

            case 633626763:
                if (s1.equals("autoweapon")) {
                    return "Auto Weapon";
                }
                break;

            case 688296023:
                if (s1.equals("fpshurtcam")) {
                    return "FPS Hurt Cam";
                }
                break;

            case 765482832:
                if (s1.equals("ladderjump")) {
                    return "Ladder Jump";
                }
                break;

            case 786282275:
                if (s1.equals("blockoverlay")) {
                    return "Block Overlay";
                }
                break;

            case 865360868:
                if (s1.equals("chestaura")) {
                    return "Chest Aura";
                }
                break;

            case 873482198:
                if (s1.equals("blockwalk")) {
                    return "Block Walk";
                }
                break;

            case 1007586325:
                if (s1.equals("noscoreboard")) {
                    return "No Scoreboard";
                }
                break;

            case 1068975772:
                if (s1.equals("keepcontainer")) {
                    return "Keep Container";
                }
                break;

            case 1140477178:
                if (s1.equals("oldhitting")) {
                    return "Old Hitting";
                }
                break;

            case 1240516744:
                if (s1.equals("teleporthit")) {
                    return "Teleport Hit";
                }
                break;

            case 1271744037:
                if (s1.equals("pointeresp")) {
                    return "Pointer ESP";
                }
                break;

            case 1362582506:
                if (s1.equals("forceunicodechat")) {
                    return "Force Unicode Chat";
                }
                break;

            case 1365290191:
                if (s1.equals("truesight")) {
                    return "True Sight";
                }
                break;

            case 1424958241:
                if (s1.equals("memoryfixer")) {
                    return "Memory Fixer";
                }
                break;

            case 1439261831:
                if (s1.equals("autofish")) {
                    return "Auto Fish";
                }
                break;

            case 1439317007:
                if (s1.equals("autohead")) {
                    return "Auto Head";
                }
                break;

            case 1439392349:
                if (s1.equals("autojump")) {
                    return "Auto Jump";
                }
                break;

            case 1439654950:
                if (s1.equals("autosoup")) {
                    return "Auto Soup";
                }
                break;

            case 1439684551:
                if (s1.equals("autotool")) {
                    return "Auto Tool";
                }
                break;

            case 1439760376:
                if (s1.equals("autowalk")) {
                    return "Auto Walk";
                }
                break;

            case 1463010091:
                if (s1.equals("wallclimb")) {
                    return "Wall Climb";
                }
                break;

            case 1522276526:
                if (s1.equals("reversestep")) {
                    return "Reverse Step";
                }
                break;

            case 1551939297:
                if (s1.equals("chestesp")) {
                    return "Chest ESP";
                }
                break;

            case 1663088880:
                if (s1.equals("autoarmor")) {
                    return "Auto Armor";
                }
                break;

            case 1664004272:
                if (s1.equals("autobreak")) {
                    return "Auto Break";
                }
                break;

            case 1672849000:
                if (s1.equals("autoleave")) {
                    return "Auto Leave";
                }
                break;

            case 1679863214:
                if (s1.equals("autosword")) {
                    return "Auto Sword";
                }
                break;

            case 1765327574:
                if (s1.equals("safewalk")) {
                    return "Safe Walk";
                }
                break;

            case 1816558310:
                if (s1.equals("morecarry")) {
                    return "More Carry";
                }
                break;

            case 1841525028:
                if (s1.equals("nametags")) {
                    return "Name Tags";
                }
                break;

            case 1937261038:
                if (s1.equals("tickbase")) {
                    return "Tick Base";
                }
                break;

            case 1960145730:
                if (s1.equals("invmove")) {
                    return "Inv Move";
                }
                break;

            case 1979765136:
                if (s1.equals("waterspeed")) {
                    return "Water Speed";
                }
                break;

            case 2096371902:
                if (s1.equals("playerface")) {
                    return "Player Face";
                }
                break;

            case 2099756966:
                if (s1.equals("autoclicker")) {
                    return "Auto Clicker";
                }
                break;

            case 2116211087:
                if (s1.equals("itemesp")) {
                    return "Item ESP";
                }
                break;

            case 2128847325:
                if (s1.equals("noswing")) {
                    return "No Swing";
                }
                break;

            case 2144066940:
                if (s1.equals("skinderp")) {
                    return "Skin Derp";
                }
            }

            return detName;
        }
    }

    static {
        BreaknameUtils breaknameutils = new BreaknameUtils();

        INSTANCE = breaknameutils;
    }
}
