package net.ccbluex.liquidbounce.api;

import com.mojang.authlib.GameProfile;
import io.netty.buffer.ByteBuf;
import java.awt.image.BufferedImage;
import java.io.File;
import java.security.PublicKey;
import javax.crypto.SecretKey;
import kotlin.Metadata;
import net.ccbluex.liquidbounce.api.enums.BlockType;
import net.ccbluex.liquidbounce.api.enums.EnchantmentType;
import net.ccbluex.liquidbounce.api.enums.EnumFacingType;
import net.ccbluex.liquidbounce.api.enums.ItemType;
import net.ccbluex.liquidbounce.api.enums.MaterialType;
import net.ccbluex.liquidbounce.api.enums.StatType;
import net.ccbluex.liquidbounce.api.enums.WDefaultVertexFormats;
import net.ccbluex.liquidbounce.api.enums.WEnumHand;
import net.ccbluex.liquidbounce.api.minecraft.block.material.IMaterial;
import net.ccbluex.liquidbounce.api.minecraft.client.IMinecraft;
import net.ccbluex.liquidbounce.api.minecraft.client.block.IBlock;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.player.IEntityOtherPlayerMP;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IFontRenderer;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IGuiButton;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IGuiScreen;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IGuiTextField;
import net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IServerData;
import net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IWorldClient;
import net.ccbluex.liquidbounce.api.minecraft.client.render.ITessellator;
import net.ccbluex.liquidbounce.api.minecraft.client.render.IThreadDownloadImageData;
import net.ccbluex.liquidbounce.api.minecraft.client.render.WIImageBuffer;
import net.ccbluex.liquidbounce.api.minecraft.client.render.texture.IDynamicTexture;
import net.ccbluex.liquidbounce.api.minecraft.client.render.vertex.IVertexFormat;
import net.ccbluex.liquidbounce.api.minecraft.client.renderer.IGlStateManager;
import net.ccbluex.liquidbounce.api.minecraft.client.renderer.vertex.IVertexBuffer;
import net.ccbluex.liquidbounce.api.minecraft.client.settings.IGameSettings;
import net.ccbluex.liquidbounce.api.minecraft.enchantments.IEnchantment;
import net.ccbluex.liquidbounce.api.minecraft.event.IClickEvent;
import net.ccbluex.liquidbounce.api.minecraft.item.IItem;
import net.ccbluex.liquidbounce.api.minecraft.item.IItemStack;
import net.ccbluex.liquidbounce.api.minecraft.nbt.IJsonToNBT;
import net.ccbluex.liquidbounce.api.minecraft.nbt.INBTTagCompound;
import net.ccbluex.liquidbounce.api.minecraft.nbt.INBTTagDouble;
import net.ccbluex.liquidbounce.api.minecraft.nbt.INBTTagList;
import net.ccbluex.liquidbounce.api.minecraft.nbt.INBTTagString;
import net.ccbluex.liquidbounce.api.minecraft.network.IPacket;
import net.ccbluex.liquidbounce.api.minecraft.network.play.client.ICPacketAnimation;
import net.ccbluex.liquidbounce.api.minecraft.network.play.client.ICPacketClientStatus;
import net.ccbluex.liquidbounce.api.minecraft.network.play.client.ICPacketCloseWindow;
import net.ccbluex.liquidbounce.api.minecraft.network.play.client.ICPacketCustomPayload;
import net.ccbluex.liquidbounce.api.minecraft.network.play.client.ICPacketEntityAction;
import net.ccbluex.liquidbounce.api.minecraft.network.play.client.ICPacketHeldItemChange;
import net.ccbluex.liquidbounce.api.minecraft.network.play.client.ICPacketKeepAlive;
import net.ccbluex.liquidbounce.api.minecraft.network.play.client.ICPacketPlayer;
import net.ccbluex.liquidbounce.api.minecraft.network.play.client.ICPacketPlayerBlockPlacement;
import net.ccbluex.liquidbounce.api.minecraft.network.play.client.ICPacketPlayerDigging;
import net.ccbluex.liquidbounce.api.minecraft.network.play.client.ICPacketPlayerPosLook;
import net.ccbluex.liquidbounce.api.minecraft.network.play.client.ICPacketResourcePackStatus;
import net.ccbluex.liquidbounce.api.minecraft.network.play.client.ICPacketUseEntity;
import net.ccbluex.liquidbounce.api.minecraft.potion.IPotion;
import net.ccbluex.liquidbounce.api.minecraft.potion.IPotionEffect;
import net.ccbluex.liquidbounce.api.minecraft.potion.PotionType;
import net.ccbluex.liquidbounce.api.minecraft.stats.IStatBase;
import net.ccbluex.liquidbounce.api.minecraft.util.IAxisAlignedBB;
import net.ccbluex.liquidbounce.api.minecraft.util.IEnumFacing;
import net.ccbluex.liquidbounce.api.minecraft.util.IIChatComponent;
import net.ccbluex.liquidbounce.api.minecraft.util.IResourceLocation;
import net.ccbluex.liquidbounce.api.minecraft.util.IScaledResolution;
import net.ccbluex.liquidbounce.api.minecraft.util.ISession;
import net.ccbluex.liquidbounce.api.minecraft.util.WBlockPos;
import net.ccbluex.liquidbounce.api.minecraft.util.WVec3;
import net.ccbluex.liquidbounce.api.network.IPacketBuffer;
import net.ccbluex.liquidbounce.api.util.IWrappedFontRenderer;
import net.ccbluex.liquidbounce.api.util.WrappedCreativeTabs;
import net.ccbluex.liquidbounce.api.util.WrappedGuiScreen;
import net.ccbluex.liquidbounce.api.util.WrappedGuiSlot;
import net.ccbluex.liquidbounce.injection.backend.PacketImpl;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000¸\u0004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\bb\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\bf\u0018\u00002\u00020\u0001J8\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\r2\u0006\u0010\u000f\u001a\u00020\r2\u0006\u0010\u0010\u001a\u00020\r2\u0006\u0010\u0011\u001a\u00020\r2\u0006\u0010\u0012\u001a\u00020\rH&J\b\u0010\u0013\u001a\u00020\u0014H&J\u0010\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018H&J\b\u0010\u0019\u001a\u00020\u001aH&J\u0010\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001cH&J\u0018\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020\u001c2\u0006\u0010 \u001a\u00020!H&J\u0018\u0010\"\u001a\u00020#2\u0006\u0010$\u001a\u00020%2\u0006\u0010&\u001a\u00020\'H&J \u0010(\u001a\u00020\u001e2\u0006\u0010)\u001a\u00020*2\u0006\u0010+\u001a\u00020,2\u0006\u0010-\u001a\u00020.H&J\u0018\u0010/\u001a\u0002002\u0006\u00101\u001a\u0002022\u0006\u00103\u001a\u000204H&J\u0010\u00105\u001a\u0002062\u0006\u0010\u001f\u001a\u00020\u001cH&J\b\u00107\u001a\u000208H&J\u0010\u00109\u001a\u00020:2\u0006\u0010;\u001a\u00020<H&J\u0012\u0010=\u001a\u00020>2\b\u0010?\u001a\u0004\u0018\u00010!H\'J:\u0010=\u001a\u00020>2\u0006\u0010@\u001a\u00020A2\u0006\u0010B\u001a\u00020\u001c2\b\u0010C\u001a\u0004\u0018\u00010!2\u0006\u0010D\u001a\u00020E2\u0006\u0010F\u001a\u00020E2\u0006\u0010G\u001a\u00020EH&J \u0010H\u001a\u00020\u001e2\u0006\u00103\u001a\u00020I2\u0006\u0010J\u001a\u00020A2\u0006\u0010K\u001a\u00020LH&J \u0010M\u001a\u00020:2\u0006\u0010N\u001a\u00020E2\u0006\u0010O\u001a\u00020E2\u0006\u0010;\u001a\u00020<H&J8\u0010P\u001a\u00020Q2\u0006\u0010R\u001a\u00020\r2\u0006\u0010S\u001a\u00020\r2\u0006\u0010T\u001a\u00020\r2\u0006\u0010N\u001a\u00020E2\u0006\u0010O\u001a\u00020E2\u0006\u0010;\u001a\u00020<H&J(\u0010U\u001a\u00020:2\u0006\u0010R\u001a\u00020\r2\u0006\u0010V\u001a\u00020\r2\u0006\u0010T\u001a\u00020\r2\u0006\u0010;\u001a\u00020<H&J\u0010\u0010W\u001a\u00020\u001e2\u0006\u0010X\u001a\u00020%H&J\u0014\u0010Y\u001a\u0006\u0012\u0002\b\u00030Z2\u0006\u0010?\u001a\u00020[H\'J\u0018\u0010\\\u001a\u00020]2\u0006\u00101\u001a\u0002022\u0006\u00103\u001a\u00020^H&J\u0018\u0010\\\u001a\u00020]2\u0006\u0010_\u001a\u0002022\u0006\u0010`\u001a\u00020aH&J\u0010\u0010b\u001a\u00020c2\u0006\u0010X\u001a\u00020%H&J\u0018\u0010d\u001a\u00020e2\u0006\u0010f\u001a\u00020g2\u0006\u0010h\u001a\u00020%H&J\u0010\u0010i\u001a\u00020j2\u0006\u0010k\u001a\u00020lH&J\u0018\u0010m\u001a\u00020n2\u0006\u0010o\u001a\u00020p2\u0006\u0010q\u001a\u00020rH&J8\u0010s\u001a\u00020t2\u0006\u0010u\u001a\u00020\u001c2\u0006\u0010R\u001a\u00020\u001c2\u0006\u0010S\u001a\u00020\u001c2\u0006\u0010v\u001a\u00020\u001c2\u0006\u0010w\u001a\u00020\u001c2\u0006\u0010X\u001a\u00020%H&J(\u0010s\u001a\u00020t2\u0006\u0010u\u001a\u00020\u001c2\u0006\u0010R\u001a\u00020\u001c2\u0006\u0010S\u001a\u00020\u001c2\u0006\u0010X\u001a\u00020%H&J \u0010x\u001a\u00020y2\u0006\u0010z\u001a\u00020y2\u0006\u0010{\u001a\u00020|2\u0006\u0010}\u001a\u00020~H&J\u0011\u0010\u007f\u001a\u00020y2\u0007\u0010\u0080\u0001\u001a\u00020yH&J\u0012\u0010\u0081\u0001\u001a\u00020y2\u0007\u0010\u0080\u0001\u001a\u00020yH&J\u001c\u0010\u0082\u0001\u001a\u00020y2\u0007\u0010\u0080\u0001\u001a\u00020y2\b\u0010\u0083\u0001\u001a\u00030\u0084\u0001H&J<\u0010\u0085\u0001\u001a\u00030\u0086\u00012\u0006\u0010u\u001a\u00020\u001c2\b\u0010\u0087\u0001\u001a\u00030\u0088\u00012\u0006\u0010R\u001a\u00020\u001c2\u0006\u0010S\u001a\u00020\u001c2\u0006\u0010v\u001a\u00020\u001c2\u0006\u0010w\u001a\u00020\u001cH&J\u0012\u0010\u0089\u0001\u001a\u00020y2\u0007\u0010\u0080\u0001\u001a\u00020yH&J<\u0010\u008a\u0001\u001a\u00030\u0086\u00012\u0006\u0010u\u001a\u00020\u001c2\b\u0010\u0087\u0001\u001a\u00030\u0088\u00012\u0006\u0010R\u001a\u00020\u001c2\u0006\u0010S\u001a\u00020\u001c2\u0006\u0010v\u001a\u00020\u001c2\u0006\u0010w\u001a\u00020\u001cH&J\u001c\u0010\u008b\u0001\u001a\u00020\u001e2\u0007\u0010\u008c\u0001\u001a\u00020%2\b\u0010\u008d\u0001\u001a\u00030\u008e\u0001H&J\n\u0010\u008f\u0001\u001a\u00030\u0090\u0001H&J\u0013\u0010\u0091\u0001\u001a\u00020!2\b\u0010\u0092\u0001\u001a\u00030\u0093\u0001H&J\u0013\u0010\u0091\u0001\u001a\u00020!2\b\u0010\u0094\u0001\u001a\u00030\u0090\u0001H&J%\u0010\u0091\u0001\u001a\u00020!2\b\u0010\u0094\u0001\u001a\u00030\u0090\u00012\u0007\u0010\u0095\u0001\u001a\u00020\u001c2\u0007\u0010\u0096\u0001\u001a\u00020\u001cH&J\n\u0010\u0097\u0001\u001a\u00030\u0098\u0001H&J\u0012\u0010\u0099\u0001\u001a\u00030\u009a\u00012\u0006\u0010h\u001a\u00020\rH&J\n\u0010\u009b\u0001\u001a\u00030\u009c\u0001H&J\u0013\u0010\u009d\u0001\u001a\u00030\u009e\u00012\u0007\u0010\u009f\u0001\u001a\u00020%H&J\u0013\u0010 \u0001\u001a\u00020\'2\b\u0010¡\u0001\u001a\u00030¢\u0001H&J$\u0010£\u0001\u001a\u00030¤\u00012\u0006\u0010u\u001a\u00020\u001c2\u0007\u0010¥\u0001\u001a\u00020\u001c2\u0007\u0010¦\u0001\u001a\u00020\u001cH&J\u0013\u0010§\u0001\u001a\u00030¨\u00012\u0007\u0010©\u0001\u001a\u00020%H&J\u0014\u0010ª\u0001\u001a\u00030«\u00012\b\u0010¬\u0001\u001a\u00030\u00ad\u0001H&J\u0012\u0010®\u0001\u001a\u00030¯\u00012\u0006\u0010{\u001a\u00020|H&J.\u0010°\u0001\u001a\u00030±\u00012\u0007\u0010²\u0001\u001a\u00020%2\u0007\u0010³\u0001\u001a\u00020%2\u0007\u0010´\u0001\u001a\u00020%2\u0007\u0010µ\u0001\u001a\u00020%H&J5\u0010¶\u0001\u001a\u00030·\u00012\n\u0010¸\u0001\u001a\u0005\u0018\u00010¹\u00012\u0007\u0010º\u0001\u001a\u00020%2\n\u0010»\u0001\u001a\u0005\u0018\u00010¨\u00012\b\u0010¼\u0001\u001a\u00030½\u0001H&J\u0014\u0010¾\u0001\u001a\u00030\u0093\u00012\b\u0010¿\u0001\u001a\u00030À\u0001H&J\u0014\u0010Á\u0001\u001a\u00030Â\u00012\b\u0010¿\u0001\u001a\u00030Ã\u0001H&J\u0013\u0010Ä\u0001\u001a\u00020L2\b\u0010¿\u0001\u001a\u00030Å\u0001H&J\n\u0010Æ\u0001\u001a\u00030Ç\u0001H&J\u0014\u0010È\u0001\u001a\u00030\u0090\u00012\b\u0010¿\u0001\u001a\u00030É\u0001H&J\u0014\u0010Ê\u0001\u001a\u00030Ë\u00012\b\u0010¿\u0001\u001a\u00030Ì\u0001H&J\u0014\u0010Í\u0001\u001a\u00030Î\u00012\b\u0010¿\u0001\u001a\u00030Ï\u0001H&J\u0014\u0010Ð\u0001\u001a\u00030Ñ\u00012\b\u0010¿\u0001\u001a\u00030Ò\u0001H&J\u0014\u0010Ó\u0001\u001a\u00030\u00ad\u00012\b\u0010¿\u0001\u001a\u00030Ô\u0001H&J\u0014\u0010Õ\u0001\u001a\u00020<2\t\u0010Ö\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010×\u0001\u001a\u00020<2\t\u0010Ö\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010Ø\u0001\u001a\u00020<2\t\u0010Ö\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010Ù\u0001\u001a\u00020<2\t\u0010Ö\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010Ú\u0001\u001a\u00020<2\t\u0010Ö\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010Û\u0001\u001a\u00020<2\t\u0010Ö\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010Ü\u0001\u001a\u00020<2\t\u0010Ö\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010Ý\u0001\u001a\u00020<2\t\u0010Ö\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010Þ\u0001\u001a\u00020<2\t\u0010Ö\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010ß\u0001\u001a\u00020<2\t\u0010Ö\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010à\u0001\u001a\u00020<2\t\u0010Ö\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010á\u0001\u001a\u00020<2\t\u0010Ö\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010â\u0001\u001a\u00020<2\t\u0010Ö\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010ã\u0001\u001a\u00020<2\t\u0010Ö\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010ä\u0001\u001a\u00020<2\t\u0010Ö\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010å\u0001\u001a\u00020<2\t\u0010Ö\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010æ\u0001\u001a\u00020<2\t\u0010Ö\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010ç\u0001\u001a\u00020<2\t\u0010Ö\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010è\u0001\u001a\u00020<2\t\u0010Ö\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010é\u0001\u001a\u00020<2\t\u0010Ö\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010ê\u0001\u001a\u00020<2\t\u0010Ö\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010ë\u0001\u001a\u00020<2\t\u0010Ö\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010ì\u0001\u001a\u00020<2\t\u0010Ö\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010í\u0001\u001a\u00020<2\t\u0010Ö\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010î\u0001\u001a\u00020<2\t\u0010Ö\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010ï\u0001\u001a\u00020<2\t\u0010Ö\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010ð\u0001\u001a\u00020<2\t\u0010Ö\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010ñ\u0001\u001a\u00020<2\t\u0010Ö\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010ò\u0001\u001a\u00020<2\t\u0010Ö\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010ó\u0001\u001a\u00020<2\t\u0010Ö\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010ô\u0001\u001a\u00020<2\t\u0010Ö\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010õ\u0001\u001a\u00020<2\t\u0010Ö\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010ö\u0001\u001a\u00020<2\t\u0010Ö\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010÷\u0001\u001a\u00020<2\t\u0010Ö\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010ø\u0001\u001a\u00020<2\t\u0010Ö\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010ù\u0001\u001a\u00020<2\t\u0010Ö\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010ú\u0001\u001a\u00020<2\t\u0010Ö\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010û\u0001\u001a\u00020<2\t\u0010Ö\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010ü\u0001\u001a\u00020<2\t\u0010Ö\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010ý\u0001\u001a\u00020<2\t\u0010Ö\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010þ\u0001\u001a\u00020<2\t\u0010Ö\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010ÿ\u0001\u001a\u00020<2\t\u0010Ö\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010\u0080\u0002\u001a\u00020<2\t\u0010Ö\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010\u0081\u0002\u001a\u00020<2\t\u0010Ö\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010\u0082\u0002\u001a\u00020<2\t\u0010Ö\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010\u0083\u0002\u001a\u00020<2\t\u0010Ö\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010\u0084\u0002\u001a\u00020<2\t\u0010Ö\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010\u0085\u0002\u001a\u00020<2\t\u0010Ö\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010\u0086\u0002\u001a\u00020<2\t\u0010Ö\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010\u0087\u0002\u001a\u00020<2\t\u0010Ö\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010\u0088\u0002\u001a\u00020<2\t\u0010Ö\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010\u0089\u0002\u001a\u00020<2\t\u0010Ö\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010\u008a\u0002\u001a\u00020<2\t\u0010Ö\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010\u008b\u0002\u001a\u00020<2\t\u0010Ö\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010\u008c\u0002\u001a\u00020<2\t\u0010Ö\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010\u008d\u0002\u001a\u00020<2\t\u0010Ö\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010\u008e\u0002\u001a\u00020<2\t\u0010Ö\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010\u008f\u0002\u001a\u00020<2\t\u0010Ö\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010\u0090\u0002\u001a\u00020<2\t\u0010Ö\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010\u0091\u0002\u001a\u00020<2\t\u0010Ö\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010\u0092\u0002\u001a\u00020<2\t\u0010Ö\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010\u0093\u0002\u001a\u00020<2\t\u0010Ö\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010\u0094\u0002\u001a\u00020<2\t\u0010Ö\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010\u0095\u0002\u001a\u00020<2\t\u0010Ö\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010\u0096\u0002\u001a\u00020<2\t\u0010Ö\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010\u0097\u0002\u001a\u00020<2\t\u0010Ö\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010\u0098\u0002\u001a\u00020<2\t\u0010Ö\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010\u0099\u0002\u001a\u00020<2\t\u0010Ö\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010\u009a\u0002\u001a\u00020<2\t\u0010Ö\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010\u009b\u0002\u001a\u00020<2\t\u0010Ö\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010\u009c\u0002\u001a\u00020<2\t\u0010Ö\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010\u009d\u0002\u001a\u00020<2\t\u0010Ö\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010\u009e\u0002\u001a\u00020<2\t\u0010Ö\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010\u009f\u0002\u001a\u00020<2\t\u0010Ö\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010 \u0002\u001a\u00020<2\t\u0010Ö\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010¡\u0002\u001a\u00020<2\t\u0010Ö\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010¢\u0002\u001a\u00020<2\t\u0010Ö\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010£\u0002\u001a\u00020<2\t\u0010Ö\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010¤\u0002\u001a\u00020<2\t\u0010Ö\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010¥\u0002\u001a\u00020<2\t\u0010Ö\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010¦\u0002\u001a\u00020<2\t\u0010Ö\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010§\u0002\u001a\u00020<2\t\u0010Ö\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010¨\u0002\u001a\u00020<2\t\u0010Ö\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010©\u0002\u001a\u00020<2\t\u0010Ö\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010ª\u0002\u001a\u00020<2\t\u0010Ö\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010«\u0002\u001a\u00020<2\t\u0010Ö\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010¬\u0002\u001a\u00020<2\t\u0010Ö\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010\u00ad\u0002\u001a\u00020<2\t\u0010Ö\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010®\u0002\u001a\u00020<2\t\u0010Ö\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010¯\u0002\u001a\u00020<2\t\u0010Ö\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010°\u0002\u001a\u00020<2\t\u0010Ö\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010±\u0002\u001a\u00020<2\t\u0010Ö\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010²\u0002\u001a\u00020<2\t\u0010Ö\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010³\u0002\u001a\u00020<2\t\u0010Ö\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010´\u0002\u001a\u00020<2\t\u0010Ö\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010µ\u0002\u001a\u00020<2\t\u0010Ö\u0001\u001a\u0004\u0018\u00010\u0001H&J\u001d\u0010¶\u0002\u001a\u00030·\u00022\u0007\u0010²\u0001\u001a\u00020%2\b\u0010¸\u0002\u001a\u00030¹\u0002H&J\u0014\u0010º\u0002\u001a\u00030\u0088\u00012\b\u0010»\u0002\u001a\u00030¼\u0002H&J\u0013\u0010½\u0002\u001a\u00020y2\b\u0010¾\u0002\u001a\u00030¿\u0002H&JG\u0010À\u0002\u001a\u00030·\u00022\b\u0010Á\u0002\u001a\u00030Â\u00022\u0006\u0010{\u001a\u00020|2\u0006\u0010v\u001a\u00020\u001c2\u0006\u0010w\u001a\u00020\u001c2\u0007\u0010Ã\u0002\u001a\u00020\u001c2\u0007\u0010Ä\u0002\u001a\u00020\u001c2\u0007\u0010Å\u0002\u001a\u00020\u001cH&R\u0012\u0010\u0002\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005R\u0012\u0010\u0006\u001a\u00020\u0007X¦\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\t¨\u0006Æ\u0002"},
    d2 = { "Lnet/ccbluex/liquidbounce/api/IClassProvider;", "", "jsonToNBTInstance", "Lnet/ccbluex/liquidbounce/api/minecraft/nbt/IJsonToNBT;", "getJsonToNBTInstance", "()Lnet/ccbluex/liquidbounce/api/minecraft/nbt/IJsonToNBT;", "tessellatorInstance", "Lnet/ccbluex/liquidbounce/api/minecraft/client/render/ITessellator;", "getTessellatorInstance", "()Lnet/ccbluex/liquidbounce/api/minecraft/client/render/ITessellator;", "createAxisAlignedBB", "Lnet/ccbluex/liquidbounce/api/minecraft/util/IAxisAlignedBB;", "minX", "", "minY", "minZ", "maxX", "maxY", "maxZ", "createCPacketAnimation", "Lnet/ccbluex/liquidbounce/api/minecraft/network/play/client/ICPacketAnimation;", "createCPacketClientStatus", "Lnet/ccbluex/liquidbounce/api/minecraft/network/play/client/ICPacketClientStatus;", "state", "Lnet/ccbluex/liquidbounce/api/minecraft/network/play/client/ICPacketClientStatus$WEnumState;", "createCPacketCloseWindow", "Lnet/ccbluex/liquidbounce/api/minecraft/network/play/client/ICPacketCloseWindow;", "windowId", "", "createCPacketCreativeInventoryAction", "Lnet/ccbluex/liquidbounce/api/minecraft/network/IPacket;", "slot", "itemStack", "Lnet/ccbluex/liquidbounce/api/minecraft/item/IItemStack;", "createCPacketCustomPayload", "Lnet/ccbluex/liquidbounce/api/minecraft/network/play/client/ICPacketCustomPayload;", "channel", "", "payload", "Lnet/ccbluex/liquidbounce/api/network/IPacketBuffer;", "createCPacketEncryptionResponse", "secretKey", "Ljavax/crypto/SecretKey;", "publicKey", "Ljava/security/PublicKey;", "VerifyToken", "", "createCPacketEntityAction", "Lnet/ccbluex/liquidbounce/api/minecraft/network/play/client/ICPacketEntityAction;", "player", "Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/IEntity;", "wAction", "Lnet/ccbluex/liquidbounce/api/minecraft/network/play/client/ICPacketEntityAction$WAction;", "createCPacketHeldItemChange", "Lnet/ccbluex/liquidbounce/api/minecraft/network/play/client/ICPacketHeldItemChange;", "createCPacketKeepAlive", "Lnet/ccbluex/liquidbounce/api/minecraft/network/play/client/ICPacketKeepAlive;", "createCPacketPlayer", "Lnet/ccbluex/liquidbounce/api/minecraft/network/play/client/ICPacketPlayer;", "onGround", "", "createCPacketPlayerBlockPlacement", "Lnet/ccbluex/liquidbounce/api/minecraft/network/play/client/ICPacketPlayerBlockPlacement;", "stack", "positionIn", "Lnet/ccbluex/liquidbounce/api/minecraft/util/WBlockPos;", "placedBlockDirectionIn", "stackIn", "facingXIn", "", "facingYIn", "facingZIn", "createCPacketPlayerDigging", "Lnet/ccbluex/liquidbounce/api/minecraft/network/play/client/ICPacketPlayerDigging$WAction;", "pos", "facing", "Lnet/ccbluex/liquidbounce/api/minecraft/util/IEnumFacing;", "createCPacketPlayerLook", "yaw", "pitch", "createCPacketPlayerPosLook", "Lnet/ccbluex/liquidbounce/api/minecraft/network/play/client/ICPacketPlayerPosLook;", "x", "y", "z", "createCPacketPlayerPosition", "negativeInfinity", "createCPacketTabComplete", "text", "createCPacketTryUseItem", "Lnet/ccbluex/liquidbounce/injection/backend/PacketImpl;", "Lnet/ccbluex/liquidbounce/api/enums/WEnumHand;", "createCPacketUseEntity", "Lnet/ccbluex/liquidbounce/api/minecraft/network/play/client/ICPacketUseEntity;", "Lnet/ccbluex/liquidbounce/api/minecraft/network/play/client/ICPacketUseEntity$WAction;", "entity", "positionVector", "Lnet/ccbluex/liquidbounce/api/minecraft/util/WVec3;", "createChatComponentText", "Lnet/ccbluex/liquidbounce/api/minecraft/util/IIChatComponent;", "createClickEvent", "Lnet/ccbluex/liquidbounce/api/minecraft/event/IClickEvent;", "action", "Lnet/ccbluex/liquidbounce/api/minecraft/event/IClickEvent$WAction;", "value", "createDynamicTexture", "Lnet/ccbluex/liquidbounce/api/minecraft/client/render/texture/IDynamicTexture;", "image", "Ljava/awt/image/BufferedImage;", "createEntityOtherPlayerMP", "Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/player/IEntityOtherPlayerMP;", "world", "Lnet/ccbluex/liquidbounce/api/minecraft/client/multiplayer/IWorldClient;", "GameProfile", "Lcom/mojang/authlib/GameProfile;", "createGuiButton", "Lnet/ccbluex/liquidbounce/api/minecraft/client/gui/IGuiButton;", "id", "width", "height", "createGuiConnecting", "Lnet/ccbluex/liquidbounce/api/minecraft/client/gui/IGuiScreen;", "parent", "mc", "Lnet/ccbluex/liquidbounce/api/minecraft/client/IMinecraft;", "serverData", "Lnet/ccbluex/liquidbounce/api/minecraft/client/multiplayer/IServerData;", "createGuiModList", "parentScreen", "createGuiMultiplayer", "createGuiOptions", "gameSettings", "Lnet/ccbluex/liquidbounce/api/minecraft/client/settings/IGameSettings;", "createGuiPasswordField", "Lnet/ccbluex/liquidbounce/api/minecraft/client/gui/IGuiTextField;", "iFontRenderer", "Lnet/ccbluex/liquidbounce/api/minecraft/client/gui/IFontRenderer;", "createGuiSelectWorld", "createGuiTextField", "createICPacketResourcePackStatus", "hash", "status", "Lnet/ccbluex/liquidbounce/api/minecraft/network/play/client/ICPacketResourcePackStatus$WAction;", "createItem", "Lnet/ccbluex/liquidbounce/api/minecraft/item/IItem;", "createItemStack", "blockEnum", "Lnet/ccbluex/liquidbounce/api/minecraft/client/block/IBlock;", "item", "amount", "meta", "createNBTTagCompound", "Lnet/ccbluex/liquidbounce/api/minecraft/nbt/INBTTagCompound;", "createNBTTagDouble", "Lnet/ccbluex/liquidbounce/api/minecraft/nbt/INBTTagDouble;", "createNBTTagList", "Lnet/ccbluex/liquidbounce/api/minecraft/nbt/INBTTagList;", "createNBTTagString", "Lnet/ccbluex/liquidbounce/api/minecraft/nbt/INBTTagString;", "string", "createPacketBuffer", "buffer", "Lio/netty/buffer/ByteBuf;", "createPotionEffect", "Lnet/ccbluex/liquidbounce/api/minecraft/potion/IPotionEffect;", "time", "strength", "createResourceLocation", "Lnet/ccbluex/liquidbounce/api/minecraft/util/IResourceLocation;", "resourceName", "createSafeVertexBuffer", "Lnet/ccbluex/liquidbounce/api/minecraft/client/renderer/vertex/IVertexBuffer;", "vertexFormat", "Lnet/ccbluex/liquidbounce/api/minecraft/client/render/vertex/IVertexFormat;", "createScaledResolution", "Lnet/ccbluex/liquidbounce/api/minecraft/util/IScaledResolution;", "createSession", "Lnet/ccbluex/liquidbounce/api/minecraft/util/ISession;", "name", "uuid", "accessToken", "accountType", "createThreadDownloadImageData", "Lnet/ccbluex/liquidbounce/api/minecraft/client/render/IThreadDownloadImageData;", "cacheFileIn", "Ljava/io/File;", "imageUrlIn", "textureResourceLocation", "imageBufferIn", "Lnet/ccbluex/liquidbounce/api/minecraft/client/render/WIImageBuffer;", "getBlockEnum", "type", "Lnet/ccbluex/liquidbounce/api/enums/BlockType;", "getEnchantmentEnum", "Lnet/ccbluex/liquidbounce/api/minecraft/enchantments/IEnchantment;", "Lnet/ccbluex/liquidbounce/api/enums/EnchantmentType;", "getEnumFacing", "Lnet/ccbluex/liquidbounce/api/enums/EnumFacingType;", "getGlStateManager", "Lnet/ccbluex/liquidbounce/api/minecraft/client/renderer/IGlStateManager;", "getItemEnum", "Lnet/ccbluex/liquidbounce/api/enums/ItemType;", "getMaterialEnum", "Lnet/ccbluex/liquidbounce/api/minecraft/block/material/IMaterial;", "Lnet/ccbluex/liquidbounce/api/enums/MaterialType;", "getPotionEnum", "Lnet/ccbluex/liquidbounce/api/minecraft/potion/IPotion;", "Lnet/ccbluex/liquidbounce/api/minecraft/potion/PotionType;", "getStatEnum", "Lnet/ccbluex/liquidbounce/api/minecraft/stats/IStatBase;", "Lnet/ccbluex/liquidbounce/api/enums/StatType;", "getVertexFormatEnum", "Lnet/ccbluex/liquidbounce/api/enums/WDefaultVertexFormats;", "isBlockAir", "obj", "isBlockBedrock", "isBlockBush", "isBlockCactus", "isBlockCarpet", "isBlockFence", "isBlockLadder", "isBlockLiquid", "isBlockPane", "isBlockSlab", "isBlockSlime", "isBlockSnow", "isBlockStairs", "isBlockVine", "isCPacketAnimation", "isCPacketChatMessage", "isCPacketClientStatus", "isCPacketCloseWindow", "isCPacketCustomPayload", "isCPacketEntityAction", "isCPacketHandshake", "isCPacketHeldItemChange", "isCPacketKeepAlive", "isCPacketPlayer", "isCPacketPlayerBlockPlacement", "isCPacketPlayerDigging", "isCPacketPlayerLook", "isCPacketPlayerPosLook", "isCPacketPlayerPosition", "isCPacketTryUseItem", "isCPacketUseEntity", "isClickGui", "isEntityAnimal", "isEntityArmorStand", "isEntityArrow", "isEntityBat", "isEntityBoat", "isEntityDragon", "isEntityFallingBlock", "isEntityGhast", "isEntityGolem", "isEntityItem", "isEntityLivingBase", "isEntityMinecart", "isEntityMinecartChest", "isEntityMob", "isEntityPlayer", "isEntityShulker", "isEntitySlime", "isEntitySquid", "isEntityTNTPrimed", "isEntityVillager", "isGuiChat", "isGuiChest", "isGuiContainer", "isGuiGameOver", "isGuiHudDesigner", "isGuiIngameMenu", "isGuiInventory", "isItemAir", "isItemAppleGold", "isItemArmor", "isItemAxe", "isItemBed", "isItemBlock", "isItemBoat", "isItemBow", "isItemBucket", "isItemBucketMilk", "isItemEgg", "isItemEnchantedBook", "isItemEnderPearl", "isItemFishingRod", "isItemFood", "isItemMinecart", "isItemPickaxe", "isItemPotion", "isItemSnowball", "isItemSword", "isItemTool", "isSPacketAnimation", "isSPacketChat", "isSPacketCloseWindow", "isSPacketEntity", "isSPacketEntityVelocity", "isSPacketExplosion", "isSPacketPlayerPosLook", "isSPacketResourcePackSend", "isSPacketTabComplete", "isSPacketWindowItems", "isTileEntityChest", "isTileEntityDispenser", "isTileEntityEnderChest", "isTileEntityFurnace", "isTileEntityHopper", "isTileEntityShulkerBox", "wrapCreativeTab", "", "wrappedCreativeTabs", "Lnet/ccbluex/liquidbounce/api/util/WrappedCreativeTabs;", "wrapFontRenderer", "fontRenderer", "Lnet/ccbluex/liquidbounce/api/util/IWrappedFontRenderer;", "wrapGuiScreen", "clickGui", "Lnet/ccbluex/liquidbounce/api/util/WrappedGuiScreen;", "wrapGuiSlot", "wrappedGuiSlot", "Lnet/ccbluex/liquidbounce/api/util/WrappedGuiSlot;", "top", "bottom", "slotHeight", "LiquidBounce"}
)
public interface IClassProvider {

    @NotNull
    ITessellator getTessellatorInstance();

    @NotNull
    IJsonToNBT getJsonToNBTInstance();

    @NotNull
    IResourceLocation createResourceLocation(@NotNull String s);

    @NotNull
    IThreadDownloadImageData createThreadDownloadImageData(@Nullable File file, @NotNull String s, @Nullable IResourceLocation iresourcelocation, @NotNull WIImageBuffer wiimagebuffer);

    @NotNull
    IPacketBuffer createPacketBuffer(@NotNull ByteBuf bytebuf);

    @NotNull
    IIChatComponent createChatComponentText(@NotNull String s);

    @NotNull
    IClickEvent createClickEvent(@NotNull IClickEvent.WAction iclickevent_waction, @NotNull String s);

    @NotNull
    IGuiTextField createGuiTextField(int i, @NotNull IFontRenderer ifontrenderer, int j, int k, int l, int i1);

    @NotNull
    IGuiTextField createGuiPasswordField(int i, @NotNull IFontRenderer ifontrenderer, int j, int k, int l, int i1);

    @NotNull
    IGuiButton createGuiButton(int i, int j, int k, int l, int i1, @NotNull String s);

    @NotNull
    IGuiButton createGuiButton(int i, int j, int k, @NotNull String s);

    @NotNull
    ISession createSession(@NotNull String s, @NotNull String s1, @NotNull String s2, @NotNull String s3);

    @NotNull
    IDynamicTexture createDynamicTexture(@NotNull BufferedImage bufferedimage);

    @NotNull
    IItem createItem();

    @NotNull
    IItemStack createItemStack(@NotNull IItem iitem, int i, int j);

    @NotNull
    IItemStack createItemStack(@NotNull IItem iitem);

    @NotNull
    IItemStack createItemStack(@NotNull IBlock iblock);

    @NotNull
    IAxisAlignedBB createAxisAlignedBB(double d0, double d1, double d2, double d3, double d4, double d5);

    @NotNull
    IScaledResolution createScaledResolution(@NotNull IMinecraft iminecraft);

    @NotNull
    INBTTagCompound createNBTTagCompound();

    @NotNull
    INBTTagList createNBTTagList();

    @NotNull
    INBTTagString createNBTTagString(@NotNull String s);

    @NotNull
    INBTTagDouble createNBTTagDouble(double d0);

    @NotNull
    IEntityOtherPlayerMP createEntityOtherPlayerMP(@NotNull IWorldClient iworldclient, @NotNull GameProfile gameprofile);

    @NotNull
    IPotionEffect createPotionEffect(int i, int j, int k);

    @NotNull
    IGuiScreen createGuiOptions(@NotNull IGuiScreen iguiscreen, @NotNull IGameSettings igamesettings);

    @NotNull
    IGuiScreen createGuiSelectWorld(@NotNull IGuiScreen iguiscreen);

    @NotNull
    IGuiScreen createGuiMultiplayer(@NotNull IGuiScreen iguiscreen);

    @NotNull
    IGuiScreen createGuiModList(@NotNull IGuiScreen iguiscreen);

    @NotNull
    IGuiScreen createGuiConnecting(@NotNull IGuiScreen iguiscreen, @NotNull IMinecraft iminecraft, @NotNull IServerData iserverdata);

    @NotNull
    ICPacketPlayerBlockPlacement createCPacketPlayerBlockPlacement(@NotNull WBlockPos wblockpos, int i, @Nullable IItemStack iitemstack, float f, float f1, float f2);

    @NotNull
    ICPacketHeldItemChange createCPacketHeldItemChange(int i);

    @SupportsMinecraftVersions({ MinecraftVersion.MC_1_12})
    @NotNull
    ICPacketPlayerBlockPlacement createCPacketPlayerBlockPlacement(@Nullable IItemStack iitemstack);

    @NotNull
    ICPacketPlayerPosLook createCPacketPlayerPosLook(double d0, double d1, double d2, float f, float f1, boolean flag);

    @NotNull
    ICPacketClientStatus createCPacketClientStatus(@NotNull ICPacketClientStatus.WEnumState icpacketclientstatus_wenumstate);

    @NotNull
    IPacket createCPacketPlayerDigging(@NotNull ICPacketPlayerDigging.WAction icpacketplayerdigging_waction, @NotNull WBlockPos wblockpos, @NotNull IEnumFacing ienumfacing);

    @NotNull
    ICPacketPlayer createCPacketPlayerPosition(double d0, double d1, double d2, boolean flag);

    @NotNull
    IPacket createICPacketResourcePackStatus(@NotNull String s, @NotNull ICPacketResourcePackStatus.WAction icpacketresourcepackstatus_waction);

    @NotNull
    ICPacketPlayer createCPacketPlayerLook(float f, float f1, boolean flag);

    @NotNull
    ICPacketUseEntity createCPacketUseEntity(@NotNull IEntity ientity, @NotNull ICPacketUseEntity.WAction icpacketuseentity_waction);

    @NotNull
    ICPacketUseEntity createCPacketUseEntity(@NotNull IEntity ientity, @NotNull WVec3 wvec3);

    @NotNull
    IPacket createCPacketCreativeInventoryAction(int i, @NotNull IItemStack iitemstack);

    @NotNull
    ICPacketEntityAction createCPacketEntityAction(@NotNull IEntity ientity, @NotNull ICPacketEntityAction.WAction icpacketentityaction_waction);

    @NotNull
    ICPacketCustomPayload createCPacketCustomPayload(@NotNull String s, @NotNull IPacketBuffer ipacketbuffer);

    @NotNull
    ICPacketCloseWindow createCPacketCloseWindow(int i);

    @NotNull
    ICPacketCloseWindow createCPacketCloseWindow();

    @NotNull
    ICPacketPlayer createCPacketPlayer(boolean flag);

    @NotNull
    IPacket createCPacketTabComplete(@NotNull String s);

    @NotNull
    ICPacketAnimation createCPacketAnimation();

    @NotNull
    ICPacketKeepAlive createCPacketKeepAlive();

    boolean isEntityAnimal(@Nullable Object object);

    boolean isEntitySquid(@Nullable Object object);

    boolean isEntityBat(@Nullable Object object);

    boolean isEntityGolem(@Nullable Object object);

    boolean isEntityMob(@Nullable Object object);

    boolean isEntityVillager(@Nullable Object object);

    boolean isEntitySlime(@Nullable Object object);

    boolean isEntityGhast(@Nullable Object object);

    boolean isEntityDragon(@Nullable Object object);

    boolean isEntityLivingBase(@Nullable Object object);

    boolean isEntityPlayer(@Nullable Object object);

    boolean isEntityArmorStand(@Nullable Object object);

    boolean isEntityTNTPrimed(@Nullable Object object);

    boolean isEntityBoat(@Nullable Object object);

    boolean isEntityMinecart(@Nullable Object object);

    boolean isEntityItem(@Nullable Object object);

    boolean isEntityArrow(@Nullable Object object);

    boolean isEntityFallingBlock(@Nullable Object object);

    boolean isEntityMinecartChest(@Nullable Object object);

    boolean isEntityShulker(@Nullable Object object);

    boolean isTileEntityChest(@Nullable Object object);

    boolean isTileEntityEnderChest(@Nullable Object object);

    boolean isTileEntityFurnace(@Nullable Object object);

    boolean isTileEntityDispenser(@Nullable Object object);

    boolean isTileEntityHopper(@Nullable Object object);

    boolean isSPacketChat(@Nullable Object object);

    boolean isSPacketEntity(@Nullable Object object);

    boolean isSPacketResourcePackSend(@Nullable Object object);

    boolean isSPacketPlayerPosLook(@Nullable Object object);

    boolean isSPacketAnimation(@Nullable Object object);

    boolean isSPacketEntityVelocity(@Nullable Object object);

    boolean isSPacketExplosion(@Nullable Object object);

    boolean isSPacketCloseWindow(@Nullable Object object);

    boolean isSPacketTabComplete(@Nullable Object object);

    boolean isCPacketPlayer(@Nullable Object object);

    boolean isCPacketPlayerBlockPlacement(@Nullable Object object);

    boolean isCPacketUseEntity(@Nullable Object object);

    boolean isCPacketCloseWindow(@Nullable Object object);

    boolean isCPacketChatMessage(@Nullable Object object);

    boolean isCPacketKeepAlive(@Nullable Object object);

    boolean isCPacketPlayerPosition(@Nullable Object object);

    boolean isCPacketPlayerPosLook(@Nullable Object object);

    boolean isCPacketClientStatus(@Nullable Object object);

    boolean isCPacketAnimation(@Nullable Object object);

    boolean isCPacketEntityAction(@Nullable Object object);

    boolean isSPacketWindowItems(@Nullable Object object);

    boolean isCPacketHeldItemChange(@Nullable Object object);

    boolean isCPacketPlayerLook(@Nullable Object object);

    boolean isCPacketCustomPayload(@Nullable Object object);

    boolean isCPacketHandshake(@Nullable Object object);

    boolean isCPacketPlayerDigging(@Nullable Object object);

    boolean isCPacketTryUseItem(@Nullable Object object);

    boolean isItemSword(@Nullable Object object);

    boolean isItemTool(@Nullable Object object);

    boolean isItemArmor(@Nullable Object object);

    boolean isItemPotion(@Nullable Object object);

    boolean isItemBlock(@Nullable Object object);

    boolean isItemBow(@Nullable Object object);

    boolean isItemBucket(@Nullable Object object);

    boolean isItemFood(@Nullable Object object);

    boolean isItemBucketMilk(@Nullable Object object);

    boolean isItemPickaxe(@Nullable Object object);

    boolean isItemAxe(@Nullable Object object);

    boolean isItemBed(@Nullable Object object);

    boolean isItemEnderPearl(@Nullable Object object);

    boolean isItemEnchantedBook(@Nullable Object object);

    boolean isItemBoat(@Nullable Object object);

    boolean isItemMinecart(@Nullable Object object);

    boolean isItemAppleGold(@Nullable Object object);

    boolean isItemSnowball(@Nullable Object object);

    boolean isItemEgg(@Nullable Object object);

    boolean isItemFishingRod(@Nullable Object object);

    boolean isItemAir(@Nullable Object object);

    boolean isBlockAir(@Nullable Object object);

    boolean isBlockFence(@Nullable Object object);

    boolean isBlockSnow(@Nullable Object object);

    boolean isBlockLadder(@Nullable Object object);

    boolean isBlockVine(@Nullable Object object);

    boolean isBlockSlime(@Nullable Object object);

    boolean isBlockSlab(@Nullable Object object);

    boolean isBlockStairs(@Nullable Object object);

    boolean isBlockCarpet(@Nullable Object object);

    boolean isBlockPane(@Nullable Object object);

    boolean isBlockLiquid(@Nullable Object object);

    boolean isBlockCactus(@Nullable Object object);

    boolean isBlockBedrock(@Nullable Object object);

    boolean isBlockBush(@Nullable Object object);

    boolean isGuiInventory(@Nullable Object object);

    boolean isGuiContainer(@Nullable Object object);

    boolean isGuiGameOver(@Nullable Object object);

    boolean isGuiChat(@Nullable Object object);

    boolean isGuiIngameMenu(@Nullable Object object);

    boolean isGuiChest(@Nullable Object object);

    boolean isGuiHudDesigner(@Nullable Object object);

    boolean isClickGui(@Nullable Object object);

    @NotNull
    IPotion getPotionEnum(@NotNull PotionType potiontype);

    @NotNull
    IEnumFacing getEnumFacing(@NotNull EnumFacingType enumfacingtype);

    @NotNull
    IBlock getBlockEnum(@NotNull BlockType blocktype);

    @NotNull
    IMaterial getMaterialEnum(@NotNull MaterialType materialtype);

    @NotNull
    IStatBase getStatEnum(@NotNull StatType stattype);

    @NotNull
    IItem getItemEnum(@NotNull ItemType itemtype);

    @NotNull
    IEnchantment getEnchantmentEnum(@NotNull EnchantmentType enchantmenttype);

    @NotNull
    IVertexFormat getVertexFormatEnum(@NotNull WDefaultVertexFormats wdefaultvertexformats);

    @NotNull
    IFontRenderer wrapFontRenderer(@NotNull IWrappedFontRenderer iwrappedfontrenderer);

    @NotNull
    IGuiScreen wrapGuiScreen(@NotNull WrappedGuiScreen wrappedguiscreen);

    @NotNull
    IVertexBuffer createSafeVertexBuffer(@NotNull IVertexFormat ivertexformat);

    void wrapCreativeTab(@NotNull String s, @NotNull WrappedCreativeTabs wrappedcreativetabs);

    void wrapGuiSlot(@NotNull WrappedGuiSlot wrappedguislot, @NotNull IMinecraft iminecraft, int i, int j, int k, int l, int i1);

    @NotNull
    IGlStateManager getGlStateManager();

    @NotNull
    IPacket createCPacketEncryptionResponse(@NotNull SecretKey secretkey, @NotNull PublicKey publickey, @NotNull byte[] abyte);

    @SupportsMinecraftVersions({ MinecraftVersion.MC_1_12})
    @NotNull
    PacketImpl createCPacketTryUseItem(@NotNull WEnumHand wenumhand);

    boolean isTileEntityShulkerBox(@Nullable Object object);
}
