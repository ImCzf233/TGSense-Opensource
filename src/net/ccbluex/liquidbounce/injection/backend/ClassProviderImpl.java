package net.ccbluex.liquidbounce.injection.backend;

import com.mojang.authlib.GameProfile;
import io.netty.buffer.ByteBuf;
import java.awt.image.BufferedImage;
import java.io.File;
import java.security.PublicKey;
import javax.crypto.SecretKey;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.NotImplementedError;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.api.IClassProvider;
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
import net.ccbluex.liquidbounce.api.minecraft.creativetabs.ICreativeTabs;
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
import net.ccbluex.liquidbounce.injection.backend.utils.BackendExtentionsKt$WhenMappings;
import net.ccbluex.liquidbounce.injection.backend.utils.CreativeTabsWrapper;
import net.ccbluex.liquidbounce.injection.backend.utils.FontRendererWrapper;
import net.ccbluex.liquidbounce.injection.backend.utils.GuiPasswordField;
import net.ccbluex.liquidbounce.injection.backend.utils.GuiScreenWrapper;
import net.ccbluex.liquidbounce.injection.backend.utils.GuiSlotWrapper;
import net.ccbluex.liquidbounce.injection.backend.utils.SafeVertexBuffer;
import net.ccbluex.liquidbounce.ui.client.clickgui.ClickGui;
import net.ccbluex.liquidbounce.ui.client.hud.designer.GuiHudDesigner;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockCactus;
import net.minecraft.block.BlockCarpet;
import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockDynamicLiquid;
import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockFire;
import net.minecraft.block.BlockLadder;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockPane;
import net.minecraft.block.BlockSand;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.BlockSlime;
import net.minecraft.block.BlockSnow;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.BlockStaticLiquid;
import net.minecraft.block.BlockVine;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiGameOver;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.GuiWorldSelection;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.IImageBuffer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.ThreadDownloadImageData;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexBuffer;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.item.EntityMinecartChest;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntityShulker;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAir;
import net.minecraft.item.ItemAppleGold;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemBed;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemBoat;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemBucketMilk;
import net.minecraft.item.ItemEgg;
import net.minecraft.item.ItemEnchantedBook;
import net.minecraft.item.ItemEnderPearl;
import net.minecraft.item.ItemFishingRod;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemMinecart;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemSnowball;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagDouble;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.handshake.client.C00Handshake;
import net.minecraft.network.login.client.CPacketEncryptionResponse;
import net.minecraft.network.play.client.CPacketAnimation;
import net.minecraft.network.play.client.CPacketChatMessage;
import net.minecraft.network.play.client.CPacketClientStatus;
import net.minecraft.network.play.client.CPacketCloseWindow;
import net.minecraft.network.play.client.CPacketCreativeInventoryAction;
import net.minecraft.network.play.client.CPacketCustomPayload;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.network.play.client.CPacketKeepAlive;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.network.play.client.CPacketResourcePackStatus;
import net.minecraft.network.play.client.CPacketTabComplete;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.network.play.client.CPacketClientStatus.State;
import net.minecraft.network.play.client.CPacketPlayer.Position;
import net.minecraft.network.play.client.CPacketPlayer.PositionRotation;
import net.minecraft.network.play.client.CPacketPlayer.Rotation;
import net.minecraft.network.play.server.SPacketAnimation;
import net.minecraft.network.play.server.SPacketChat;
import net.minecraft.network.play.server.SPacketCloseWindow;
import net.minecraft.network.play.server.SPacketEntity;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import net.minecraft.network.play.server.SPacketResourcePackSend;
import net.minecraft.network.play.server.SPacketTabComplete;
import net.minecraft.network.play.server.SPacketWindowItems;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.stats.StatBase;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityDispenser;
import net.minecraft.tileentity.TileEntityEnderChest;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.tileentity.TileEntityHopper;
import net.minecraft.tileentity.TileEntityShulkerBox;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Session;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.ClickEvent.Action;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.GuiModList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000Ä\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0002\b`\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J8\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\u0010\u001a\u00020\u000e2\u0006\u0010\u0011\u001a\u00020\u000e2\u0006\u0010\u0012\u001a\u00020\u000e2\u0006\u0010\u0013\u001a\u00020\u000eH\u0016J\b\u0010\u0014\u001a\u00020\u0015H\u0016J\u0010\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0019H\u0016J\b\u0010\u001a\u001a\u00020\u001bH\u0016J\u0010\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001dH\u0016J\u0018\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020\u001d2\u0006\u0010!\u001a\u00020\"H\u0016J\u0018\u0010#\u001a\u00020$2\u0006\u0010%\u001a\u00020&2\u0006\u0010\'\u001a\u00020(H\u0016J \u0010)\u001a\u00020\u001f2\u0006\u0010*\u001a\u00020+2\u0006\u0010,\u001a\u00020-2\u0006\u0010.\u001a\u00020/H\u0016J\u0018\u00100\u001a\u0002012\u0006\u00102\u001a\u0002032\u0006\u00104\u001a\u000205H\u0016J\u0010\u00106\u001a\u0002072\u0006\u0010 \u001a\u00020\u001dH\u0016J\b\u00108\u001a\u000209H\u0016J\u0010\u0010:\u001a\u00020;2\u0006\u0010<\u001a\u00020=H\u0016J\u0012\u0010>\u001a\u00020?2\b\u0010@\u001a\u0004\u0018\u00010\"H\u0016J:\u0010>\u001a\u00020?2\u0006\u0010A\u001a\u00020B2\u0006\u0010C\u001a\u00020\u001d2\b\u0010D\u001a\u0004\u0018\u00010\"2\u0006\u0010E\u001a\u00020F2\u0006\u0010G\u001a\u00020F2\u0006\u0010H\u001a\u00020FH\u0016J \u0010I\u001a\u00020\u001f2\u0006\u00104\u001a\u00020J2\u0006\u0010K\u001a\u00020B2\u0006\u0010L\u001a\u00020MH\u0016J \u0010N\u001a\u00020;2\u0006\u0010O\u001a\u00020F2\u0006\u0010P\u001a\u00020F2\u0006\u0010<\u001a\u00020=H\u0016J8\u0010Q\u001a\u00020R2\u0006\u0010S\u001a\u00020\u000e2\u0006\u0010T\u001a\u00020\u000e2\u0006\u0010U\u001a\u00020\u000e2\u0006\u0010O\u001a\u00020F2\u0006\u0010P\u001a\u00020F2\u0006\u0010<\u001a\u00020=H\u0016J(\u0010V\u001a\u00020;2\u0006\u0010S\u001a\u00020\u000e2\u0006\u0010T\u001a\u00020\u000e2\u0006\u0010U\u001a\u00020\u000e2\u0006\u0010<\u001a\u00020=H\u0016J\u0010\u0010W\u001a\u00020\u001f2\u0006\u0010X\u001a\u00020&H\u0016J\u0014\u0010Y\u001a\u0006\u0012\u0002\b\u00030Z2\u0006\u0010[\u001a\u00020\\H\u0016J\u0018\u0010]\u001a\u00020^2\u0006\u00102\u001a\u0002032\u0006\u00104\u001a\u00020_H\u0016J\u0018\u0010]\u001a\u00020^2\u0006\u0010`\u001a\u0002032\u0006\u0010a\u001a\u00020bH\u0016J\u0010\u0010c\u001a\u00020d2\u0006\u0010X\u001a\u00020&H\u0016J\u0018\u0010e\u001a\u00020f2\u0006\u0010g\u001a\u00020h2\u0006\u0010i\u001a\u00020&H\u0016J\u0010\u0010j\u001a\u00020k2\u0006\u0010l\u001a\u00020mH\u0016J\u0018\u0010n\u001a\u00020o2\u0006\u0010p\u001a\u00020q2\u0006\u0010r\u001a\u00020sH\u0016J8\u0010t\u001a\u00020u2\u0006\u0010v\u001a\u00020\u001d2\u0006\u0010S\u001a\u00020\u001d2\u0006\u0010T\u001a\u00020\u001d2\u0006\u0010w\u001a\u00020\u001d2\u0006\u0010x\u001a\u00020\u001d2\u0006\u0010X\u001a\u00020&H\u0016J(\u0010t\u001a\u00020u2\u0006\u0010v\u001a\u00020\u001d2\u0006\u0010S\u001a\u00020\u001d2\u0006\u0010T\u001a\u00020\u001d2\u0006\u0010X\u001a\u00020&H\u0016J \u0010y\u001a\u00020z2\u0006\u0010{\u001a\u00020z2\u0006\u0010|\u001a\u00020}2\u0006\u0010~\u001a\u00020\u007fH\u0016J\u0012\u0010\u0080\u0001\u001a\u00020z2\u0007\u0010\u0081\u0001\u001a\u00020zH\u0016J\u0012\u0010\u0082\u0001\u001a\u00020z2\u0007\u0010\u0081\u0001\u001a\u00020zH\u0016J\u001c\u0010\u0083\u0001\u001a\u00020z2\u0007\u0010\u0081\u0001\u001a\u00020z2\b\u0010\u0084\u0001\u001a\u00030\u0085\u0001H\u0016J<\u0010\u0086\u0001\u001a\u00030\u0087\u00012\u0006\u0010v\u001a\u00020\u001d2\b\u0010\u0088\u0001\u001a\u00030\u0089\u00012\u0006\u0010S\u001a\u00020\u001d2\u0006\u0010T\u001a\u00020\u001d2\u0006\u0010w\u001a\u00020\u001d2\u0006\u0010x\u001a\u00020\u001dH\u0016J\u0012\u0010\u008a\u0001\u001a\u00020z2\u0007\u0010\u0081\u0001\u001a\u00020zH\u0016J<\u0010\u008b\u0001\u001a\u00030\u0087\u00012\u0006\u0010v\u001a\u00020\u001d2\b\u0010\u0088\u0001\u001a\u00030\u0089\u00012\u0006\u0010S\u001a\u00020\u001d2\u0006\u0010T\u001a\u00020\u001d2\u0006\u0010w\u001a\u00020\u001d2\u0006\u0010x\u001a\u00020\u001dH\u0016J\u001c\u0010\u008c\u0001\u001a\u00020\u001f2\u0007\u0010\u008d\u0001\u001a\u00020&2\b\u0010\u008e\u0001\u001a\u00030\u008f\u0001H\u0016J\n\u0010\u0090\u0001\u001a\u00030\u0091\u0001H\u0016J\u0013\u0010\u0092\u0001\u001a\u00020\"2\b\u0010\u0093\u0001\u001a\u00030\u0094\u0001H\u0016J\u0013\u0010\u0092\u0001\u001a\u00020\"2\b\u0010\u0095\u0001\u001a\u00030\u0091\u0001H\u0016J%\u0010\u0092\u0001\u001a\u00020\"2\b\u0010\u0095\u0001\u001a\u00030\u0091\u00012\u0007\u0010\u0096\u0001\u001a\u00020\u001d2\u0007\u0010\u0097\u0001\u001a\u00020\u001dH\u0016J\n\u0010\u0098\u0001\u001a\u00030\u0099\u0001H\u0016J\u0012\u0010\u009a\u0001\u001a\u00030\u009b\u00012\u0006\u0010i\u001a\u00020\u000eH\u0016J\n\u0010\u009c\u0001\u001a\u00030\u009d\u0001H\u0016J\u0013\u0010\u009e\u0001\u001a\u00030\u009f\u00012\u0007\u0010 \u0001\u001a\u00020&H\u0016J\u0013\u0010¡\u0001\u001a\u00020(2\b\u0010¢\u0001\u001a\u00030£\u0001H\u0016J$\u0010¤\u0001\u001a\u00030¥\u00012\u0006\u0010v\u001a\u00020\u001d2\u0007\u0010¦\u0001\u001a\u00020\u001d2\u0007\u0010§\u0001\u001a\u00020\u001dH\u0016J\u0013\u0010¨\u0001\u001a\u00030©\u00012\u0007\u0010ª\u0001\u001a\u00020&H\u0016J\u0014\u0010«\u0001\u001a\u00030¬\u00012\b\u0010\u00ad\u0001\u001a\u00030®\u0001H\u0016J\u0012\u0010¯\u0001\u001a\u00030°\u00012\u0006\u0010|\u001a\u00020}H\u0016J.\u0010±\u0001\u001a\u00030²\u00012\u0007\u0010³\u0001\u001a\u00020&2\u0007\u0010´\u0001\u001a\u00020&2\u0007\u0010µ\u0001\u001a\u00020&2\u0007\u0010¶\u0001\u001a\u00020&H\u0016J5\u0010·\u0001\u001a\u00030¸\u00012\n\u0010¹\u0001\u001a\u0005\u0018\u00010º\u00012\u0007\u0010»\u0001\u001a\u00020&2\n\u0010¼\u0001\u001a\u0005\u0018\u00010©\u00012\b\u0010½\u0001\u001a\u00030¾\u0001H\u0016J\u0014\u0010¿\u0001\u001a\u00030\u0094\u00012\b\u0010À\u0001\u001a\u00030Á\u0001H\u0016J\u0014\u0010Â\u0001\u001a\u00030Ã\u00012\b\u0010À\u0001\u001a\u00030Ä\u0001H\u0016J\u0013\u0010Å\u0001\u001a\u00020M2\b\u0010À\u0001\u001a\u00030Æ\u0001H\u0016J\n\u0010Ç\u0001\u001a\u00030È\u0001H\u0016J\u0014\u0010É\u0001\u001a\u00030\u0091\u00012\b\u0010À\u0001\u001a\u00030Ê\u0001H\u0016J\u0014\u0010Ë\u0001\u001a\u00030Ì\u00012\b\u0010À\u0001\u001a\u00030Í\u0001H\u0016J\u0014\u0010Î\u0001\u001a\u00030Ï\u00012\b\u0010À\u0001\u001a\u00030Ð\u0001H\u0016J\u0014\u0010Ñ\u0001\u001a\u00030Ò\u00012\b\u0010À\u0001\u001a\u00030Ó\u0001H\u0016J\u0014\u0010Ô\u0001\u001a\u00030®\u00012\b\u0010À\u0001\u001a\u00030Õ\u0001H\u0016J\u0015\u0010Ö\u0001\u001a\u00020=2\n\u0010×\u0001\u001a\u0005\u0018\u00010Ø\u0001H\u0016J\u0015\u0010Ù\u0001\u001a\u00020=2\n\u0010×\u0001\u001a\u0005\u0018\u00010Ø\u0001H\u0016J\u0015\u0010Ú\u0001\u001a\u00020=2\n\u0010×\u0001\u001a\u0005\u0018\u00010Ø\u0001H\u0016J\u0015\u0010Û\u0001\u001a\u00020=2\n\u0010×\u0001\u001a\u0005\u0018\u00010Ø\u0001H\u0016J\u0015\u0010Ü\u0001\u001a\u00020=2\n\u0010×\u0001\u001a\u0005\u0018\u00010Ø\u0001H\u0016J\u0015\u0010Ý\u0001\u001a\u00020=2\n\u0010×\u0001\u001a\u0005\u0018\u00010Ø\u0001H\u0016J\u0015\u0010Þ\u0001\u001a\u00020=2\n\u0010×\u0001\u001a\u0005\u0018\u00010Ø\u0001H\u0016J\u0015\u0010ß\u0001\u001a\u00020=2\n\u0010×\u0001\u001a\u0005\u0018\u00010Ø\u0001H\u0016J\u0015\u0010à\u0001\u001a\u00020=2\n\u0010×\u0001\u001a\u0005\u0018\u00010Ø\u0001H\u0016J\u0015\u0010á\u0001\u001a\u00020=2\n\u0010×\u0001\u001a\u0005\u0018\u00010Ø\u0001H\u0016J\u0015\u0010â\u0001\u001a\u00020=2\n\u0010×\u0001\u001a\u0005\u0018\u00010Ø\u0001H\u0016J\u0015\u0010ã\u0001\u001a\u00020=2\n\u0010×\u0001\u001a\u0005\u0018\u00010Ø\u0001H\u0016J\u0015\u0010ä\u0001\u001a\u00020=2\n\u0010×\u0001\u001a\u0005\u0018\u00010Ø\u0001H\u0016J\u0015\u0010å\u0001\u001a\u00020=2\n\u0010×\u0001\u001a\u0005\u0018\u00010Ø\u0001H\u0016J\u0015\u0010æ\u0001\u001a\u00020=2\n\u0010×\u0001\u001a\u0005\u0018\u00010Ø\u0001H\u0016J\u0015\u0010ç\u0001\u001a\u00020=2\n\u0010×\u0001\u001a\u0005\u0018\u00010Ø\u0001H\u0016J\u0015\u0010è\u0001\u001a\u00020=2\n\u0010×\u0001\u001a\u0005\u0018\u00010Ø\u0001H\u0016J\u0015\u0010é\u0001\u001a\u00020=2\n\u0010×\u0001\u001a\u0005\u0018\u00010Ø\u0001H\u0016J\u0015\u0010ê\u0001\u001a\u00020=2\n\u0010×\u0001\u001a\u0005\u0018\u00010Ø\u0001H\u0016J\u0015\u0010ë\u0001\u001a\u00020=2\n\u0010×\u0001\u001a\u0005\u0018\u00010Ø\u0001H\u0016J\u0015\u0010ì\u0001\u001a\u00020=2\n\u0010×\u0001\u001a\u0005\u0018\u00010Ø\u0001H\u0016J\u0015\u0010í\u0001\u001a\u00020=2\n\u0010×\u0001\u001a\u0005\u0018\u00010Ø\u0001H\u0016J\u0015\u0010î\u0001\u001a\u00020=2\n\u0010×\u0001\u001a\u0005\u0018\u00010Ø\u0001H\u0016J\u0015\u0010ï\u0001\u001a\u00020=2\n\u0010×\u0001\u001a\u0005\u0018\u00010Ø\u0001H\u0016J\u0015\u0010ð\u0001\u001a\u00020=2\n\u0010×\u0001\u001a\u0005\u0018\u00010Ø\u0001H\u0016J\u0015\u0010ñ\u0001\u001a\u00020=2\n\u0010×\u0001\u001a\u0005\u0018\u00010Ø\u0001H\u0016J\u0015\u0010ò\u0001\u001a\u00020=2\n\u0010×\u0001\u001a\u0005\u0018\u00010Ø\u0001H\u0016J\u0015\u0010ó\u0001\u001a\u00020=2\n\u0010×\u0001\u001a\u0005\u0018\u00010Ø\u0001H\u0016J\u0015\u0010ô\u0001\u001a\u00020=2\n\u0010×\u0001\u001a\u0005\u0018\u00010Ø\u0001H\u0016J\u0015\u0010õ\u0001\u001a\u00020=2\n\u0010×\u0001\u001a\u0005\u0018\u00010Ø\u0001H\u0016J\u0015\u0010ö\u0001\u001a\u00020=2\n\u0010×\u0001\u001a\u0005\u0018\u00010Ø\u0001H\u0016J\u0015\u0010÷\u0001\u001a\u00020=2\n\u0010×\u0001\u001a\u0005\u0018\u00010Ø\u0001H\u0016J\u0015\u0010ø\u0001\u001a\u00020=2\n\u0010×\u0001\u001a\u0005\u0018\u00010Ø\u0001H\u0016J\u0015\u0010ù\u0001\u001a\u00020=2\n\u0010×\u0001\u001a\u0005\u0018\u00010Ø\u0001H\u0016J\u0015\u0010ú\u0001\u001a\u00020=2\n\u0010×\u0001\u001a\u0005\u0018\u00010Ø\u0001H\u0016J\u0015\u0010û\u0001\u001a\u00020=2\n\u0010×\u0001\u001a\u0005\u0018\u00010Ø\u0001H\u0016J\u0015\u0010ü\u0001\u001a\u00020=2\n\u0010×\u0001\u001a\u0005\u0018\u00010Ø\u0001H\u0016J\u0015\u0010ý\u0001\u001a\u00020=2\n\u0010×\u0001\u001a\u0005\u0018\u00010Ø\u0001H\u0016J\u0015\u0010þ\u0001\u001a\u00020=2\n\u0010×\u0001\u001a\u0005\u0018\u00010Ø\u0001H\u0016J\u0015\u0010ÿ\u0001\u001a\u00020=2\n\u0010×\u0001\u001a\u0005\u0018\u00010Ø\u0001H\u0016J\u0015\u0010\u0080\u0002\u001a\u00020=2\n\u0010×\u0001\u001a\u0005\u0018\u00010Ø\u0001H\u0016J\u0015\u0010\u0081\u0002\u001a\u00020=2\n\u0010×\u0001\u001a\u0005\u0018\u00010Ø\u0001H\u0016J\u0015\u0010\u0082\u0002\u001a\u00020=2\n\u0010×\u0001\u001a\u0005\u0018\u00010Ø\u0001H\u0016J\u0015\u0010\u0083\u0002\u001a\u00020=2\n\u0010×\u0001\u001a\u0005\u0018\u00010Ø\u0001H\u0016J\u0015\u0010\u0084\u0002\u001a\u00020=2\n\u0010×\u0001\u001a\u0005\u0018\u00010Ø\u0001H\u0016J\u0015\u0010\u0085\u0002\u001a\u00020=2\n\u0010×\u0001\u001a\u0005\u0018\u00010Ø\u0001H\u0016J\u0015\u0010\u0086\u0002\u001a\u00020=2\n\u0010×\u0001\u001a\u0005\u0018\u00010Ø\u0001H\u0016J\u0015\u0010\u0087\u0002\u001a\u00020=2\n\u0010×\u0001\u001a\u0005\u0018\u00010Ø\u0001H\u0016J\u0015\u0010\u0088\u0002\u001a\u00020=2\n\u0010×\u0001\u001a\u0005\u0018\u00010Ø\u0001H\u0016J\u0015\u0010\u0089\u0002\u001a\u00020=2\n\u0010×\u0001\u001a\u0005\u0018\u00010Ø\u0001H\u0016J\u0015\u0010\u008a\u0002\u001a\u00020=2\n\u0010×\u0001\u001a\u0005\u0018\u00010Ø\u0001H\u0016J\u0015\u0010\u008b\u0002\u001a\u00020=2\n\u0010×\u0001\u001a\u0005\u0018\u00010Ø\u0001H\u0016J\u0015\u0010\u008c\u0002\u001a\u00020=2\n\u0010×\u0001\u001a\u0005\u0018\u00010Ø\u0001H\u0016J\u0015\u0010\u008d\u0002\u001a\u00020=2\n\u0010×\u0001\u001a\u0005\u0018\u00010Ø\u0001H\u0016J\u0015\u0010\u008e\u0002\u001a\u00020=2\n\u0010×\u0001\u001a\u0005\u0018\u00010Ø\u0001H\u0016J\u0015\u0010\u008f\u0002\u001a\u00020=2\n\u0010×\u0001\u001a\u0005\u0018\u00010Ø\u0001H\u0016J\u0015\u0010\u0090\u0002\u001a\u00020=2\n\u0010×\u0001\u001a\u0005\u0018\u00010Ø\u0001H\u0016J\u0015\u0010\u0091\u0002\u001a\u00020=2\n\u0010×\u0001\u001a\u0005\u0018\u00010Ø\u0001H\u0016J\u0015\u0010\u0092\u0002\u001a\u00020=2\n\u0010×\u0001\u001a\u0005\u0018\u00010Ø\u0001H\u0016J\u0015\u0010\u0093\u0002\u001a\u00020=2\n\u0010×\u0001\u001a\u0005\u0018\u00010Ø\u0001H\u0016J\u0015\u0010\u0094\u0002\u001a\u00020=2\n\u0010×\u0001\u001a\u0005\u0018\u00010Ø\u0001H\u0016J\u0015\u0010\u0095\u0002\u001a\u00020=2\n\u0010×\u0001\u001a\u0005\u0018\u00010Ø\u0001H\u0016J\u0015\u0010\u0096\u0002\u001a\u00020=2\n\u0010×\u0001\u001a\u0005\u0018\u00010Ø\u0001H\u0016J\u0015\u0010\u0097\u0002\u001a\u00020=2\n\u0010×\u0001\u001a\u0005\u0018\u00010Ø\u0001H\u0016J\u0015\u0010\u0098\u0002\u001a\u00020=2\n\u0010×\u0001\u001a\u0005\u0018\u00010Ø\u0001H\u0016J\u0015\u0010\u0099\u0002\u001a\u00020=2\n\u0010×\u0001\u001a\u0005\u0018\u00010Ø\u0001H\u0016J\u0015\u0010\u009a\u0002\u001a\u00020=2\n\u0010×\u0001\u001a\u0005\u0018\u00010Ø\u0001H\u0016J\u0015\u0010\u009b\u0002\u001a\u00020=2\n\u0010×\u0001\u001a\u0005\u0018\u00010Ø\u0001H\u0016J\u0015\u0010\u009c\u0002\u001a\u00020=2\n\u0010×\u0001\u001a\u0005\u0018\u00010Ø\u0001H\u0016J\u0015\u0010\u009d\u0002\u001a\u00020=2\n\u0010×\u0001\u001a\u0005\u0018\u00010Ø\u0001H\u0016J\u0015\u0010\u009e\u0002\u001a\u00020=2\n\u0010×\u0001\u001a\u0005\u0018\u00010Ø\u0001H\u0016J\u0015\u0010\u009f\u0002\u001a\u00020=2\n\u0010×\u0001\u001a\u0005\u0018\u00010Ø\u0001H\u0016J\u0015\u0010 \u0002\u001a\u00020=2\n\u0010×\u0001\u001a\u0005\u0018\u00010Ø\u0001H\u0016J\u0015\u0010¡\u0002\u001a\u00020=2\n\u0010×\u0001\u001a\u0005\u0018\u00010Ø\u0001H\u0016J\u0015\u0010¢\u0002\u001a\u00020=2\n\u0010×\u0001\u001a\u0005\u0018\u00010Ø\u0001H\u0016J\u0015\u0010£\u0002\u001a\u00020=2\n\u0010×\u0001\u001a\u0005\u0018\u00010Ø\u0001H\u0016J\u0015\u0010¤\u0002\u001a\u00020=2\n\u0010×\u0001\u001a\u0005\u0018\u00010Ø\u0001H\u0016J\u0015\u0010¥\u0002\u001a\u00020=2\n\u0010×\u0001\u001a\u0005\u0018\u00010Ø\u0001H\u0016J\u0015\u0010¦\u0002\u001a\u00020=2\n\u0010×\u0001\u001a\u0005\u0018\u00010Ø\u0001H\u0016J\u0015\u0010§\u0002\u001a\u00020=2\n\u0010×\u0001\u001a\u0005\u0018\u00010Ø\u0001H\u0016J\u0015\u0010¨\u0002\u001a\u00020=2\n\u0010×\u0001\u001a\u0005\u0018\u00010Ø\u0001H\u0016J\u0015\u0010©\u0002\u001a\u00020=2\n\u0010×\u0001\u001a\u0005\u0018\u00010Ø\u0001H\u0016J\u0015\u0010ª\u0002\u001a\u00020=2\n\u0010×\u0001\u001a\u0005\u0018\u00010Ø\u0001H\u0016J\u0015\u0010«\u0002\u001a\u00020=2\n\u0010×\u0001\u001a\u0005\u0018\u00010Ø\u0001H\u0016J\u0015\u0010¬\u0002\u001a\u00020=2\n\u0010×\u0001\u001a\u0005\u0018\u00010Ø\u0001H\u0016J\u0015\u0010\u00ad\u0002\u001a\u00020=2\n\u0010×\u0001\u001a\u0005\u0018\u00010Ø\u0001H\u0016J\u0015\u0010®\u0002\u001a\u00020=2\n\u0010×\u0001\u001a\u0005\u0018\u00010Ø\u0001H\u0016J\u0015\u0010¯\u0002\u001a\u00020=2\n\u0010×\u0001\u001a\u0005\u0018\u00010Ø\u0001H\u0016J\u0015\u0010°\u0002\u001a\u00020=2\n\u0010×\u0001\u001a\u0005\u0018\u00010Ø\u0001H\u0016J\u0015\u0010±\u0002\u001a\u00020=2\n\u0010×\u0001\u001a\u0005\u0018\u00010Ø\u0001H\u0016J\u0015\u0010²\u0002\u001a\u00020=2\n\u0010×\u0001\u001a\u0005\u0018\u00010Ø\u0001H\u0016J\u0015\u0010³\u0002\u001a\u00020=2\n\u0010×\u0001\u001a\u0005\u0018\u00010Ø\u0001H\u0016J\u0015\u0010´\u0002\u001a\u00020=2\n\u0010×\u0001\u001a\u0005\u0018\u00010Ø\u0001H\u0016J\u0015\u0010µ\u0002\u001a\u00020=2\n\u0010×\u0001\u001a\u0005\u0018\u00010Ø\u0001H\u0016J\u0015\u0010¶\u0002\u001a\u00020=2\n\u0010×\u0001\u001a\u0005\u0018\u00010Ø\u0001H\u0016J\u0015\u0010·\u0002\u001a\u00020=2\n\u0010×\u0001\u001a\u0005\u0018\u00010Ø\u0001H\u0016J\u001d\u0010¸\u0002\u001a\u00030¹\u00022\u0007\u0010³\u0001\u001a\u00020&2\b\u0010º\u0002\u001a\u00030»\u0002H\u0016J\u0014\u0010¼\u0002\u001a\u00030\u0089\u00012\b\u0010½\u0002\u001a\u00030¾\u0002H\u0016J\u0013\u0010¿\u0002\u001a\u00020z2\b\u0010À\u0002\u001a\u00030Á\u0002H\u0016JG\u0010Â\u0002\u001a\u00030¹\u00022\b\u0010Ã\u0002\u001a\u00030Ä\u00022\u0006\u0010|\u001a\u00020}2\u0006\u0010w\u001a\u00020\u001d2\u0006\u0010x\u001a\u00020\u001d2\u0007\u0010Å\u0002\u001a\u00020\u001d2\u0007\u0010Æ\u0002\u001a\u00020\u001d2\u0007\u0010Ç\u0002\u001a\u00020\u001dH\u0016R\u0014\u0010\u0003\u001a\u00020\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u0014\u0010\u0007\u001a\u00020\b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\n¨\u0006È\u0002"},
    d2 = { "Lnet/ccbluex/liquidbounce/injection/backend/ClassProviderImpl;", "Lnet/ccbluex/liquidbounce/api/IClassProvider;", "()V", "jsonToNBTInstance", "Lnet/ccbluex/liquidbounce/api/minecraft/nbt/IJsonToNBT;", "getJsonToNBTInstance", "()Lnet/ccbluex/liquidbounce/api/minecraft/nbt/IJsonToNBT;", "tessellatorInstance", "Lnet/ccbluex/liquidbounce/api/minecraft/client/render/ITessellator;", "getTessellatorInstance", "()Lnet/ccbluex/liquidbounce/api/minecraft/client/render/ITessellator;", "createAxisAlignedBB", "Lnet/ccbluex/liquidbounce/api/minecraft/util/IAxisAlignedBB;", "minX", "", "minY", "minZ", "maxX", "maxY", "maxZ", "createCPacketAnimation", "Lnet/ccbluex/liquidbounce/api/minecraft/network/play/client/ICPacketAnimation;", "createCPacketClientStatus", "Lnet/ccbluex/liquidbounce/api/minecraft/network/play/client/ICPacketClientStatus;", "state", "Lnet/ccbluex/liquidbounce/api/minecraft/network/play/client/ICPacketClientStatus$WEnumState;", "createCPacketCloseWindow", "Lnet/ccbluex/liquidbounce/api/minecraft/network/play/client/ICPacketCloseWindow;", "windowId", "", "createCPacketCreativeInventoryAction", "Lnet/ccbluex/liquidbounce/api/minecraft/network/IPacket;", "slot", "itemStack", "Lnet/ccbluex/liquidbounce/api/minecraft/item/IItemStack;", "createCPacketCustomPayload", "Lnet/ccbluex/liquidbounce/api/minecraft/network/play/client/ICPacketCustomPayload;", "channel", "", "payload", "Lnet/ccbluex/liquidbounce/api/network/IPacketBuffer;", "createCPacketEncryptionResponse", "secretKey", "Ljavax/crypto/SecretKey;", "publicKey", "Ljava/security/PublicKey;", "verifyToken", "", "createCPacketEntityAction", "Lnet/ccbluex/liquidbounce/api/minecraft/network/play/client/ICPacketEntityAction;", "player", "Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/IEntity;", "wAction", "Lnet/ccbluex/liquidbounce/api/minecraft/network/play/client/ICPacketEntityAction$WAction;", "createCPacketHeldItemChange", "Lnet/ccbluex/liquidbounce/api/minecraft/network/play/client/ICPacketHeldItemChange;", "createCPacketKeepAlive", "Lnet/ccbluex/liquidbounce/api/minecraft/network/play/client/ICPacketKeepAlive;", "createCPacketPlayer", "Lnet/ccbluex/liquidbounce/api/minecraft/network/play/client/ICPacketPlayer;", "onGround", "", "createCPacketPlayerBlockPlacement", "Lnet/ccbluex/liquidbounce/api/minecraft/network/play/client/ICPacketPlayerBlockPlacement;", "stack", "positionIn", "Lnet/ccbluex/liquidbounce/api/minecraft/util/WBlockPos;", "placedBlockDirectionIn", "stackIn", "facingXIn", "", "facingYIn", "facingZIn", "createCPacketPlayerDigging", "Lnet/ccbluex/liquidbounce/api/minecraft/network/play/client/ICPacketPlayerDigging$WAction;", "pos", "facing", "Lnet/ccbluex/liquidbounce/api/minecraft/util/IEnumFacing;", "createCPacketPlayerLook", "yaw", "pitch", "createCPacketPlayerPosLook", "Lnet/ccbluex/liquidbounce/api/minecraft/network/play/client/ICPacketPlayerPosLook;", "x", "y", "z", "createCPacketPlayerPosition", "createCPacketTabComplete", "text", "createCPacketTryUseItem", "Lnet/ccbluex/liquidbounce/injection/backend/PacketImpl;", "hand", "Lnet/ccbluex/liquidbounce/api/enums/WEnumHand;", "createCPacketUseEntity", "Lnet/ccbluex/liquidbounce/api/minecraft/network/play/client/ICPacketUseEntity;", "Lnet/ccbluex/liquidbounce/api/minecraft/network/play/client/ICPacketUseEntity$WAction;", "entity", "positionVector", "Lnet/ccbluex/liquidbounce/api/minecraft/util/WVec3;", "createChatComponentText", "Lnet/ccbluex/liquidbounce/api/minecraft/util/IIChatComponent;", "createClickEvent", "Lnet/ccbluex/liquidbounce/api/minecraft/event/IClickEvent;", "action", "Lnet/ccbluex/liquidbounce/api/minecraft/event/IClickEvent$WAction;", "value", "createDynamicTexture", "Lnet/ccbluex/liquidbounce/api/minecraft/client/render/texture/IDynamicTexture;", "image", "Ljava/awt/image/BufferedImage;", "createEntityOtherPlayerMP", "Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/player/IEntityOtherPlayerMP;", "world", "Lnet/ccbluex/liquidbounce/api/minecraft/client/multiplayer/IWorldClient;", "gameProfile", "Lcom/mojang/authlib/GameProfile;", "createGuiButton", "Lnet/ccbluex/liquidbounce/api/minecraft/client/gui/IGuiButton;", "id", "width", "height", "createGuiConnecting", "Lnet/ccbluex/liquidbounce/api/minecraft/client/gui/IGuiScreen;", "parent", "mc", "Lnet/ccbluex/liquidbounce/api/minecraft/client/IMinecraft;", "serverData", "Lnet/ccbluex/liquidbounce/api/minecraft/client/multiplayer/IServerData;", "createGuiModList", "parentScreen", "createGuiMultiplayer", "createGuiOptions", "gameSettings", "Lnet/ccbluex/liquidbounce/api/minecraft/client/settings/IGameSettings;", "createGuiPasswordField", "Lnet/ccbluex/liquidbounce/api/minecraft/client/gui/IGuiTextField;", "iFontRenderer", "Lnet/ccbluex/liquidbounce/api/minecraft/client/gui/IFontRenderer;", "createGuiSelectWorld", "createGuiTextField", "createICPacketResourcePackStatus", "hash", "status", "Lnet/ccbluex/liquidbounce/api/minecraft/network/play/client/ICPacketResourcePackStatus$WAction;", "createItem", "Lnet/ccbluex/liquidbounce/api/minecraft/item/IItem;", "createItemStack", "blockEnum", "Lnet/ccbluex/liquidbounce/api/minecraft/client/block/IBlock;", "item", "amount", "meta", "createNBTTagCompound", "Lnet/ccbluex/liquidbounce/api/minecraft/nbt/INBTTagCompound;", "createNBTTagDouble", "Lnet/ccbluex/liquidbounce/api/minecraft/nbt/INBTTagDouble;", "createNBTTagList", "Lnet/ccbluex/liquidbounce/api/minecraft/nbt/INBTTagList;", "createNBTTagString", "Lnet/ccbluex/liquidbounce/api/minecraft/nbt/INBTTagString;", "string", "createPacketBuffer", "buffer", "Lio/netty/buffer/ByteBuf;", "createPotionEffect", "Lnet/ccbluex/liquidbounce/api/minecraft/potion/IPotionEffect;", "time", "strength", "createResourceLocation", "Lnet/ccbluex/liquidbounce/api/minecraft/util/IResourceLocation;", "resourceName", "createSafeVertexBuffer", "Lnet/ccbluex/liquidbounce/api/minecraft/client/renderer/vertex/IVertexBuffer;", "vertexFormat", "Lnet/ccbluex/liquidbounce/api/minecraft/client/render/vertex/IVertexFormat;", "createScaledResolution", "Lnet/ccbluex/liquidbounce/api/minecraft/util/IScaledResolution;", "createSession", "Lnet/ccbluex/liquidbounce/api/minecraft/util/ISession;", "name", "uuid", "accessToken", "accountType", "createThreadDownloadImageData", "Lnet/ccbluex/liquidbounce/api/minecraft/client/render/IThreadDownloadImageData;", "cacheFileIn", "Ljava/io/File;", "imageUrlIn", "textureResourceLocation", "imageBufferIn", "Lnet/ccbluex/liquidbounce/api/minecraft/client/render/WIImageBuffer;", "getBlockEnum", "type", "Lnet/ccbluex/liquidbounce/api/enums/BlockType;", "getEnchantmentEnum", "Lnet/ccbluex/liquidbounce/api/minecraft/enchantments/IEnchantment;", "Lnet/ccbluex/liquidbounce/api/enums/EnchantmentType;", "getEnumFacing", "Lnet/ccbluex/liquidbounce/api/enums/EnumFacingType;", "getGlStateManager", "Lnet/ccbluex/liquidbounce/api/minecraft/client/renderer/IGlStateManager;", "getItemEnum", "Lnet/ccbluex/liquidbounce/api/enums/ItemType;", "getMaterialEnum", "Lnet/ccbluex/liquidbounce/api/minecraft/block/material/IMaterial;", "Lnet/ccbluex/liquidbounce/api/enums/MaterialType;", "getPotionEnum", "Lnet/ccbluex/liquidbounce/api/minecraft/potion/IPotion;", "Lnet/ccbluex/liquidbounce/api/minecraft/potion/PotionType;", "getStatEnum", "Lnet/ccbluex/liquidbounce/api/minecraft/stats/IStatBase;", "Lnet/ccbluex/liquidbounce/api/enums/StatType;", "getVertexFormatEnum", "Lnet/ccbluex/liquidbounce/api/enums/WDefaultVertexFormats;", "isBlockAir", "obj", "", "isBlockBedrock", "isBlockBush", "isBlockCactus", "isBlockCarpet", "isBlockFence", "isBlockLadder", "isBlockLiquid", "isBlockPane", "isBlockSlab", "isBlockSlime", "isBlockSnow", "isBlockStairs", "isBlockVine", "isCPacketAnimation", "isCPacketChatMessage", "isCPacketClientStatus", "isCPacketCloseWindow", "isCPacketCustomPayload", "isCPacketEntityAction", "isCPacketHandshake", "isCPacketHeldItemChange", "isCPacketKeepAlive", "isCPacketPlayer", "isCPacketPlayerBlockPlacement", "isCPacketPlayerDigging", "isCPacketPlayerLook", "isCPacketPlayerPosLook", "isCPacketPlayerPosition", "isCPacketTryUseItem", "isCPacketUseEntity", "isClickGui", "isEntityAnimal", "isEntityArmorStand", "isEntityArrow", "isEntityBat", "isEntityBoat", "isEntityDragon", "isEntityFallingBlock", "isEntityGhast", "isEntityGolem", "isEntityItem", "isEntityLivingBase", "isEntityMinecart", "isEntityMinecartChest", "isEntityMob", "isEntityPlayer", "isEntityShulker", "isEntitySlime", "isEntitySquid", "isEntityTNTPrimed", "isEntityVillager", "isGuiChat", "isGuiChest", "isGuiContainer", "isGuiGameOver", "isGuiHudDesigner", "isGuiIngameMenu", "isGuiInventory", "isItemAir", "isItemAppleGold", "isItemArmor", "isItemAxe", "isItemBed", "isItemBlock", "isItemBoat", "isItemBow", "isItemBucket", "isItemBucketMilk", "isItemEgg", "isItemEnchantedBook", "isItemEnderPearl", "isItemFishingRod", "isItemFood", "isItemMinecart", "isItemPickaxe", "isItemPotion", "isItemSnowball", "isItemSword", "isItemTool", "isSPacketAnimation", "isSPacketChat", "isSPacketCloseWindow", "isSPacketEntity", "isSPacketEntityVelocity", "isSPacketExplosion", "isSPacketPlayerPosLook", "isSPacketResourcePackSend", "isSPacketTabComplete", "isSPacketWindowItems", "isTileEntityChest", "isTileEntityDispenser", "isTileEntityEnderChest", "isTileEntityFurnace", "isTileEntityHopper", "isTileEntityShulkerBox", "wrapCreativeTab", "", "wrappedCreativeTabs", "Lnet/ccbluex/liquidbounce/api/util/WrappedCreativeTabs;", "wrapFontRenderer", "fontRenderer", "Lnet/ccbluex/liquidbounce/api/util/IWrappedFontRenderer;", "wrapGuiScreen", "clickGui", "Lnet/ccbluex/liquidbounce/api/util/WrappedGuiScreen;", "wrapGuiSlot", "wrappedGuiSlot", "Lnet/ccbluex/liquidbounce/api/util/WrappedGuiSlot;", "top", "bottom", "slotHeight", "LiquidBounce"}
)
public final class ClassProviderImpl implements IClassProvider {

    public static final ClassProviderImpl INSTANCE;

    @NotNull
    public ITessellator getTessellatorInstance() {
        Tessellator tessellator = Tessellator.getInstance();

        Intrinsics.checkExpressionValueIsNotNull(tessellator, "Tessellator.getInstance()");
        return (ITessellator) (new TessellatorImpl(tessellator));
    }

    @NotNull
    public IJsonToNBT getJsonToNBTInstance() {
        return (IJsonToNBT) JsonToNBTImpl.INSTANCE;
    }

    @NotNull
    public IResourceLocation createResourceLocation(@NotNull String resourceName) {
        Intrinsics.checkParameterIsNotNull(resourceName, "resourceName");
        return (IResourceLocation) (new ResourceLocationImpl(new ResourceLocation(resourceName)));
    }

    @NotNull
    public IThreadDownloadImageData createThreadDownloadImageData(@Nullable File cacheFileIn, @NotNull String imageUrlIn, @Nullable IResourceLocation textureResourceLocation, @NotNull final WIImageBuffer imageBufferIn) {
        Intrinsics.checkParameterIsNotNull(imageUrlIn, "imageUrlIn");
        Intrinsics.checkParameterIsNotNull(imageBufferIn, "imageBufferIn");
        File file = cacheFileIn;
        String s = imageUrlIn;
        ResourceLocation resourcelocation;

        if (textureResourceLocation != null) {
            boolean $i$f$unwrap = false;

            if (textureResourceLocation == null) {
                throw new TypeCastException("null cannot be cast to non-null type net.ccbluex.liquidbounce.injection.backend.ResourceLocationImpl");
            }

            ResourceLocation resourcelocation1 = ((ResourceLocationImpl) textureResourceLocation).getWrapped();

            file = cacheFileIn;
            s = imageUrlIn;
            resourcelocation = resourcelocation1;
        } else {
            resourcelocation = null;
        }

        IImageBuffer iimagebuffer = (IImageBuffer) (new IImageBuffer() {
            @Nullable
            public BufferedImage parseUserSkin(@Nullable BufferedImage image) {
                return imageBufferIn.parseUserSkin(image);
            }

            public void skinAvailable() {
                imageBufferIn.skinAvailable();
            }
        });
        ResourceLocation resourcelocation2 = resourcelocation;
        String s1 = s;
        File file1 = file;
        ThreadDownloadImageData threaddownloadimagedata = new ThreadDownloadImageData(file1, s1, resourcelocation2, iimagebuffer);

        return (IThreadDownloadImageData) (new ThreadDownloadImageDataImpl(threaddownloadimagedata));
    }

    @NotNull
    public IPacketBuffer createPacketBuffer(@NotNull ByteBuf buffer) {
        Intrinsics.checkParameterIsNotNull(buffer, "buffer");
        return (IPacketBuffer) (new PacketBufferImpl(new PacketBuffer(buffer)));
    }

    @NotNull
    public IIChatComponent createChatComponentText(@NotNull String text) {
        Intrinsics.checkParameterIsNotNull(text, "text");
        return (IIChatComponent) (new IChatComponentImpl((ITextComponent) (new TextComponentString(text))));
    }

    @NotNull
    public IClickEvent createClickEvent(@NotNull IClickEvent.WAction action, @NotNull String value) {
        Intrinsics.checkParameterIsNotNull(action, "action");
        Intrinsics.checkParameterIsNotNull(value, "value");
        boolean $i$f$unwrap = false;

        switch (BackendExtentionsKt$WhenMappings.$EnumSwitchMapping$11[action.ordinal()]) {
        case 1:
            Action action = Action.OPEN_URL;
            ClickEvent clickevent = new ClickEvent(action, value);

            return (IClickEvent) (new ClickEventImpl(clickevent));

        default:
            throw new NoWhenBranchMatchedException();
        }
    }

    @NotNull
    public IGuiTextField createGuiTextField(int id, @NotNull IFontRenderer iFontRenderer, int x, int y, int width, int height) {
        Intrinsics.checkParameterIsNotNull(iFontRenderer, "iFontRenderer");
        boolean $i$f$unwrap = false;
        FontRenderer fontrenderer = ((FontRendererImpl) iFontRenderer).getWrapped();
        GuiTextField guitextfield = new GuiTextField(id, fontrenderer, x, y, width, height);

        return (IGuiTextField) (new GuiTextFieldImpl(guitextfield));
    }

    @NotNull
    public IGuiTextField createGuiPasswordField(int id, @NotNull IFontRenderer iFontRenderer, int x, int y, int width, int height) {
        Intrinsics.checkParameterIsNotNull(iFontRenderer, "iFontRenderer");
        boolean $i$f$unwrap = false;
        FontRenderer fontrenderer = ((FontRendererImpl) iFontRenderer).getWrapped();
        GuiTextField guitextfield = (GuiTextField) (new GuiPasswordField(id, fontrenderer, x, y, width, height));

        return (IGuiTextField) (new GuiTextFieldImpl(guitextfield));
    }

    @NotNull
    public IGuiButton createGuiButton(int id, int x, int y, int width, int height, @NotNull String text) {
        Intrinsics.checkParameterIsNotNull(text, "text");
        return (IGuiButton) (new GuiButtonImpl(new GuiButton(id, x, y, width, height, text)));
    }

    @NotNull
    public IGuiButton createGuiButton(int id, int x, int y, @NotNull String text) {
        Intrinsics.checkParameterIsNotNull(text, "text");
        return (IGuiButton) (new GuiButtonImpl(new GuiButton(id, x, y, text)));
    }

    @NotNull
    public ISession createSession(@NotNull String name, @NotNull String uuid, @NotNull String accessToken, @NotNull String accountType) {
        Intrinsics.checkParameterIsNotNull(name, "name");
        Intrinsics.checkParameterIsNotNull(uuid, "uuid");
        Intrinsics.checkParameterIsNotNull(accessToken, "accessToken");
        Intrinsics.checkParameterIsNotNull(accountType, "accountType");
        return (ISession) (new SessionImpl(new Session(name, uuid, accessToken, accountType)));
    }

    @NotNull
    public IDynamicTexture createDynamicTexture(@NotNull BufferedImage image) {
        Intrinsics.checkParameterIsNotNull(image, "image");
        return (IDynamicTexture) (new DynamicTextureImpl(new DynamicTexture(image)));
    }

    @NotNull
    public IItem createItem() {
        return (IItem) (new ItemImpl(new Item()));
    }

    @NotNull
    public IItemStack createItemStack(@NotNull IItem item, int amount, int meta) {
        Intrinsics.checkParameterIsNotNull(item, "item");
        boolean $i$f$unwrap = false;
        Item item = ((ItemImpl) item).getWrapped();
        ItemStack itemstack = new ItemStack(item, amount, meta);

        return (IItemStack) (new ItemStackImpl(itemstack));
    }

    @NotNull
    public IItemStack createItemStack(@NotNull IItem item) {
        Intrinsics.checkParameterIsNotNull(item, "item");
        boolean $i$f$unwrap = false;
        Item item = ((ItemImpl) item).getWrapped();
        ItemStack itemstack = new ItemStack(item);

        return (IItemStack) (new ItemStackImpl(itemstack));
    }

    @NotNull
    public IItemStack createItemStack(@NotNull IBlock blockEnum) {
        Intrinsics.checkParameterIsNotNull(blockEnum, "blockEnum");
        boolean $i$f$unwrap = false;
        Block block = ((BlockImpl) blockEnum).getWrapped();
        ItemStack itemstack = new ItemStack(block);

        return (IItemStack) (new ItemStackImpl(itemstack));
    }

    @NotNull
    public IAxisAlignedBB createAxisAlignedBB(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
        return (IAxisAlignedBB) (new AxisAlignedBBImpl(new AxisAlignedBB(minX, minY, minZ, maxX, maxY, maxZ)));
    }

    @NotNull
    public IScaledResolution createScaledResolution(@NotNull IMinecraft mc) {
        Intrinsics.checkParameterIsNotNull(mc, "mc");
        boolean $i$f$unwrap = false;
        Minecraft minecraft = ((MinecraftImpl) mc).getWrapped();
        ScaledResolution scaledresolution = new ScaledResolution(minecraft);

        return (IScaledResolution) (new ScaledResolutionImpl(scaledresolution));
    }

    @NotNull
    public INBTTagCompound createNBTTagCompound() {
        return (INBTTagCompound) (new NBTTagCompoundImpl(new NBTTagCompound()));
    }

    @NotNull
    public INBTTagList createNBTTagList() {
        return (INBTTagList) (new NBTTagListImpl(new NBTTagList()));
    }

    @NotNull
    public INBTTagString createNBTTagString(@NotNull String string) {
        Intrinsics.checkParameterIsNotNull(string, "string");
        return (INBTTagString) (new NBTTagStringImpl(new NBTTagString(string)));
    }

    @NotNull
    public INBTTagDouble createNBTTagDouble(double value) {
        return (INBTTagDouble) (new NBTTagDoubleImpl(new NBTTagDouble(value)));
    }

    @NotNull
    public IEntityOtherPlayerMP createEntityOtherPlayerMP(@NotNull IWorldClient world, @NotNull GameProfile gameProfile) {
        Intrinsics.checkParameterIsNotNull(world, "world");
        Intrinsics.checkParameterIsNotNull(gameProfile, "gameProfile");
        boolean $i$f$unwrap = false;
        WorldClient worldclient = (WorldClient) ((WorldClientImpl) world).getWrapped();
        World world = (World) worldclient;
        EntityOtherPlayerMP entityotherplayermp = new EntityOtherPlayerMP(world, gameProfile);

        return (IEntityOtherPlayerMP) (new EntityOtherPlayerMPImpl(entityotherplayermp));
    }

    @NotNull
    public IPotionEffect createPotionEffect(int id, int time, int strength) {
        return (IPotionEffect) (new PotionEffectImpl(new PotionEffect(Potion.getPotionById(id), time, strength)));
    }

    @NotNull
    public IGuiScreen createGuiOptions(@NotNull IGuiScreen parentScreen, @NotNull IGameSettings gameSettings) {
        Intrinsics.checkParameterIsNotNull(parentScreen, "parentScreen");
        Intrinsics.checkParameterIsNotNull(gameSettings, "gameSettings");
        boolean $i$f$unwrap = false;
        GuiScreen guiscreen = (GuiScreen) ((GuiScreenImpl) parentScreen).getWrapped();

        $i$f$unwrap = false;
        GameSettings gamesettings = ((GameSettingsImpl) gameSettings).getWrapped();
        GuiScreen guiscreen1 = (GuiScreen) (new GuiOptions(guiscreen, gamesettings));

        return (IGuiScreen) (new GuiScreenImpl(guiscreen1));
    }

    @NotNull
    public IGuiScreen createGuiSelectWorld(@NotNull IGuiScreen parentScreen) {
        Intrinsics.checkParameterIsNotNull(parentScreen, "parentScreen");
        boolean $i$f$unwrap = false;
        GuiScreen guiscreen = (GuiScreen) ((GuiScreenImpl) parentScreen).getWrapped();
        GuiScreen guiscreen1 = (GuiScreen) (new GuiWorldSelection(guiscreen));

        return (IGuiScreen) (new GuiScreenImpl(guiscreen1));
    }

    @NotNull
    public IGuiScreen createGuiMultiplayer(@NotNull IGuiScreen parentScreen) {
        Intrinsics.checkParameterIsNotNull(parentScreen, "parentScreen");
        boolean $i$f$unwrap = false;
        GuiScreen guiscreen = (GuiScreen) ((GuiScreenImpl) parentScreen).getWrapped();
        GuiScreen guiscreen1 = (GuiScreen) (new GuiMultiplayer(guiscreen));

        return (IGuiScreen) (new GuiScreenImpl(guiscreen1));
    }

    @NotNull
    public IGuiScreen createGuiModList(@NotNull IGuiScreen parentScreen) {
        Intrinsics.checkParameterIsNotNull(parentScreen, "parentScreen");
        boolean $i$f$unwrap = false;
        GuiScreen guiscreen = (GuiScreen) ((GuiScreenImpl) parentScreen).getWrapped();
        GuiScreen guiscreen1 = (GuiScreen) (new GuiModList(guiscreen));

        return (IGuiScreen) (new GuiScreenImpl(guiscreen1));
    }

    @NotNull
    public IGuiScreen createGuiConnecting(@NotNull IGuiScreen parent, @NotNull IMinecraft mc, @NotNull IServerData serverData) {
        Intrinsics.checkParameterIsNotNull(parent, "parent");
        Intrinsics.checkParameterIsNotNull(mc, "mc");
        Intrinsics.checkParameterIsNotNull(serverData, "serverData");
        boolean $i$f$unwrap = false;
        GuiScreen guiscreen = (GuiScreen) ((GuiScreenImpl) parent).getWrapped();

        $i$f$unwrap = false;
        Minecraft minecraft = ((MinecraftImpl) mc).getWrapped();

        $i$f$unwrap = false;
        ServerData serverdata = ((ServerDataImpl) serverData).getWrapped();
        GuiScreen guiscreen1 = (GuiScreen) (new GuiConnecting(guiscreen, minecraft, serverdata));

        return (IGuiScreen) (new GuiScreenImpl(guiscreen1));
    }

    @NotNull
    public ICPacketHeldItemChange createCPacketHeldItemChange(int slot) {
        return (ICPacketHeldItemChange) (new CPacketHeldItemChangeImpl(new CPacketHeldItemChange(slot)));
    }

    @NotNull
    public ICPacketPlayerBlockPlacement createCPacketPlayerBlockPlacement(@Nullable IItemStack stack) {
        Backend this_$iv = Backend.INSTANCE;
        boolean $i$f$BACKEND_UNSUPPORTED = false;

        throw (Throwable) (new NotImplementedError("1.12.2 doesn\'t support this feature\'"));
    }

    @NotNull
    public ICPacketPlayerBlockPlacement createCPacketPlayerBlockPlacement(@NotNull WBlockPos positionIn, int placedBlockDirectionIn, @Nullable IItemStack stackIn, float facingXIn, float facingYIn, float facingZIn) {
        Intrinsics.checkParameterIsNotNull(positionIn, "positionIn");
        boolean $i$f$unwrap = false;
        BlockPos blockpos = new BlockPos(positionIn.getX(), positionIn.getY(), positionIn.getZ());
        EnumFacing enumfacing = EnumFacing.values()[placedBlockDirectionIn];
        EnumHand enumhand = EnumHand.MAIN_HAND;
        EnumFacing enumfacing1 = enumfacing;
        CPacketPlayerTryUseItemOnBlock cpacketplayertryuseitemonblock = new CPacketPlayerTryUseItemOnBlock(blockpos, enumfacing1, enumhand, facingXIn, facingYIn, facingZIn);

        return (ICPacketPlayerBlockPlacement) (new CPacketPlayerBlockPlacementImpl(cpacketplayertryuseitemonblock));
    }

    @NotNull
    public ICPacketPlayerPosLook createCPacketPlayerPosLook(double x, double y, double z, float yaw, float pitch, boolean onGround) {
        return (ICPacketPlayerPosLook) (new CPacketPlayerPosLookImpl(new PositionRotation(x, y, z, yaw, pitch, onGround)));
    }

    @NotNull
    public ICPacketClientStatus createCPacketClientStatus(@NotNull ICPacketClientStatus.WEnumState state) {
        Intrinsics.checkParameterIsNotNull(state, "state");
        boolean $i$f$unwrap = false;
        State state;

        switch (BackendExtentionsKt$WhenMappings.$EnumSwitchMapping$12[state.ordinal()]) {
        case 1:
            state = State.PERFORM_RESPAWN;
            break;

        case 2:
            state = State.REQUEST_STATS;
            break;

        case 3:
            Backend backend = Backend.INSTANCE;
            boolean $i$f$BACKEND_UNSUPPORTED = false;

            throw (Throwable) (new NotImplementedError("1.12.2 doesn\'t support this feature\'"));

        default:
            throw new NoWhenBranchMatchedException();
        }

        State state1 = state;
        CPacketClientStatus cpacketclientstatus = new CPacketClientStatus(state1);

        return (ICPacketClientStatus) (new CPacketClientStatusImpl(cpacketclientstatus));
    }

    @NotNull
    public IPacket createCPacketPlayerDigging(@NotNull ICPacketPlayerDigging.WAction wAction, @NotNull WBlockPos pos, @NotNull IEnumFacing facing) {
        Intrinsics.checkParameterIsNotNull(wAction, "wAction");
        Intrinsics.checkParameterIsNotNull(pos, "pos");
        Intrinsics.checkParameterIsNotNull(facing, "facing");
        boolean $i$f$unwrap = false;
        net.minecraft.network.play.client.CPacketPlayerDigging.Action net_minecraft_network_play_client_cpacketplayerdigging_action;

        switch (BackendExtentionsKt$WhenMappings.$EnumSwitchMapping$10[wAction.ordinal()]) {
        case 1:
            net_minecraft_network_play_client_cpacketplayerdigging_action = net.minecraft.network.play.client.CPacketPlayerDigging.Action.START_DESTROY_BLOCK;
            break;

        case 2:
            net_minecraft_network_play_client_cpacketplayerdigging_action = net.minecraft.network.play.client.CPacketPlayerDigging.Action.ABORT_DESTROY_BLOCK;
            break;

        case 3:
            net_minecraft_network_play_client_cpacketplayerdigging_action = net.minecraft.network.play.client.CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK;
            break;

        case 4:
            net_minecraft_network_play_client_cpacketplayerdigging_action = net.minecraft.network.play.client.CPacketPlayerDigging.Action.DROP_ALL_ITEMS;
            break;

        case 5:
            net_minecraft_network_play_client_cpacketplayerdigging_action = net.minecraft.network.play.client.CPacketPlayerDigging.Action.DROP_ITEM;
            break;

        case 6:
            net_minecraft_network_play_client_cpacketplayerdigging_action = net.minecraft.network.play.client.CPacketPlayerDigging.Action.RELEASE_USE_ITEM;
            break;

        case 7:
            net_minecraft_network_play_client_cpacketplayerdigging_action = net.minecraft.network.play.client.CPacketPlayerDigging.Action.SWAP_HELD_ITEMS;
            break;

        default:
            throw new NoWhenBranchMatchedException();
        }

        net.minecraft.network.play.client.CPacketPlayerDigging.Action net_minecraft_network_play_client_cpacketplayerdigging_action1 = net_minecraft_network_play_client_cpacketplayerdigging_action;

        $i$f$unwrap = false;
        BlockPos blockpos = new BlockPos(pos.getX(), pos.getY(), pos.getZ());

        $i$f$unwrap = false;
        EnumFacing enumfacing = ((EnumFacingImpl) facing).getWrapped();
        Packet packet = (Packet) (new CPacketPlayerDigging(net_minecraft_network_play_client_cpacketplayerdigging_action1, blockpos, enumfacing));

        return (IPacket) (new PacketImpl(packet));
    }

    @NotNull
    public PacketImpl createCPacketTryUseItem(@NotNull WEnumHand hand) {
        Intrinsics.checkParameterIsNotNull(hand, "hand");
        boolean $i$f$unwrap = false;
        EnumHand enumhand;

        switch (BackendExtentionsKt$WhenMappings.$EnumSwitchMapping$16[hand.ordinal()]) {
        case 1:
            enumhand = EnumHand.MAIN_HAND;
            break;

        case 2:
            enumhand = EnumHand.OFF_HAND;
            break;

        default:
            throw new NoWhenBranchMatchedException();
        }

        EnumHand enumhand1 = enumhand;
        Packet packet = (Packet) (new CPacketPlayerTryUseItem(enumhand1));

        return new PacketImpl(packet);
    }

    @NotNull
    public ICPacketPlayer createCPacketPlayerPosition(double x, double y, double z, boolean onGround) {
        return (ICPacketPlayer) (new CPacketPlayerImpl((CPacketPlayer) (new Position(x, y, z, onGround))));
    }

    @NotNull
    public IPacket createICPacketResourcePackStatus(@NotNull String hash, @NotNull ICPacketResourcePackStatus.WAction status) {
        Intrinsics.checkParameterIsNotNull(hash, "hash");
        Intrinsics.checkParameterIsNotNull(status, "status");
        boolean $i$f$unwrap = false;
        net.minecraft.network.play.client.CPacketResourcePackStatus.Action net_minecraft_network_play_client_cpacketresourcepackstatus_action;

        switch (BackendExtentionsKt$WhenMappings.$EnumSwitchMapping$13[status.ordinal()]) {
        case 1:
            net_minecraft_network_play_client_cpacketresourcepackstatus_action = net.minecraft.network.play.client.CPacketResourcePackStatus.Action.SUCCESSFULLY_LOADED;
            break;

        case 2:
            net_minecraft_network_play_client_cpacketresourcepackstatus_action = net.minecraft.network.play.client.CPacketResourcePackStatus.Action.DECLINED;
            break;

        case 3:
            net_minecraft_network_play_client_cpacketresourcepackstatus_action = net.minecraft.network.play.client.CPacketResourcePackStatus.Action.FAILED_DOWNLOAD;
            break;

        case 4:
            net_minecraft_network_play_client_cpacketresourcepackstatus_action = net.minecraft.network.play.client.CPacketResourcePackStatus.Action.ACCEPTED;
            break;

        default:
            throw new NoWhenBranchMatchedException();
        }

        net.minecraft.network.play.client.CPacketResourcePackStatus.Action net_minecraft_network_play_client_cpacketresourcepackstatus_action1 = net_minecraft_network_play_client_cpacketresourcepackstatus_action;
        Packet packet = (Packet) (new CPacketResourcePackStatus(net_minecraft_network_play_client_cpacketresourcepackstatus_action1));

        return (IPacket) (new PacketImpl(packet));
    }

    @NotNull
    public ICPacketPlayer createCPacketPlayerLook(float yaw, float pitch, boolean onGround) {
        return (ICPacketPlayer) (new CPacketPlayerImpl((CPacketPlayer) (new Rotation(yaw, pitch, onGround))));
    }

    @NotNull
    public ICPacketUseEntity createCPacketUseEntity(@NotNull IEntity player, @NotNull ICPacketUseEntity.WAction wAction) {
        Intrinsics.checkParameterIsNotNull(player, "player");
        Intrinsics.checkParameterIsNotNull(wAction, "wAction");
        boolean $i$f$BACKEND_UNSUPPORTED;
        Entity entity;
        ICPacketUseEntity icpacketuseentity;

        switch (ClassProviderImpl$WhenMappings.$EnumSwitchMapping$0[wAction.ordinal()]) {
        case 1:
            $i$f$BACKEND_UNSUPPORTED = false;
            entity = ((EntityImpl) player).getWrapped();
            EnumHand enumhand = EnumHand.MAIN_HAND;
            CPacketUseEntity cpacketuseentity = new CPacketUseEntity(entity, enumhand);

            icpacketuseentity = (ICPacketUseEntity) (new CPacketUseEntityImpl(cpacketuseentity));
            break;

        case 2:
            $i$f$BACKEND_UNSUPPORTED = false;
            entity = ((EntityImpl) player).getWrapped();
            CPacketUseEntity cpacketuseentity1 = new CPacketUseEntity(entity);

            icpacketuseentity = (ICPacketUseEntity) (new CPacketUseEntityImpl(cpacketuseentity1));
            break;

        case 3:
            Backend this_$iv = Backend.INSTANCE;

            $i$f$BACKEND_UNSUPPORTED = false;
            throw (Throwable) (new NotImplementedError("1.12.2 doesn\'t support this feature\'"));

        default:
            throw new NoWhenBranchMatchedException();
        }

        return icpacketuseentity;
    }

    @NotNull
    public ICPacketUseEntity createCPacketUseEntity(@NotNull IEntity entity, @NotNull WVec3 positionVector) {
        Intrinsics.checkParameterIsNotNull(entity, "entity");
        Intrinsics.checkParameterIsNotNull(positionVector, "positionVector");
        boolean $i$f$unwrap = false;
        Entity entity = ((EntityImpl) entity).getWrapped();
        EnumHand enumhand = EnumHand.MAIN_HAND;

        $i$f$unwrap = false;
        Vec3d vec3d = new Vec3d(positionVector.getXCoord(), positionVector.getYCoord(), positionVector.getZCoord());
        CPacketUseEntity cpacketuseentity = new CPacketUseEntity(entity, enumhand, vec3d);

        return (ICPacketUseEntity) (new CPacketUseEntityImpl(cpacketuseentity));
    }

    @NotNull
    public IPacket createCPacketCreativeInventoryAction(int slot, @NotNull IItemStack itemStack) {
        Intrinsics.checkParameterIsNotNull(itemStack, "itemStack");
        boolean $i$f$unwrap = false;
        ItemStack itemstack = ((ItemStackImpl) itemStack).getWrapped();
        Packet packet = (Packet) (new CPacketCreativeInventoryAction(slot, itemstack));

        return (IPacket) (new PacketImpl(packet));
    }

    @NotNull
    public ICPacketEntityAction createCPacketEntityAction(@NotNull IEntity player, @NotNull ICPacketEntityAction.WAction wAction) {
        Intrinsics.checkParameterIsNotNull(player, "player");
        Intrinsics.checkParameterIsNotNull(wAction, "wAction");
        boolean $i$f$unwrap = false;
        Entity entity = ((EntityImpl) player).getWrapped();

        $i$f$unwrap = false;
        net.minecraft.network.play.client.CPacketEntityAction.Action net_minecraft_network_play_client_cpacketentityaction_action;

        switch (BackendExtentionsKt$WhenMappings.$EnumSwitchMapping$14[wAction.ordinal()]) {
        case 1:
            net_minecraft_network_play_client_cpacketentityaction_action = net.minecraft.network.play.client.CPacketEntityAction.Action.START_SNEAKING;
            break;

        case 2:
            net_minecraft_network_play_client_cpacketentityaction_action = net.minecraft.network.play.client.CPacketEntityAction.Action.STOP_SNEAKING;
            break;

        case 3:
            net_minecraft_network_play_client_cpacketentityaction_action = net.minecraft.network.play.client.CPacketEntityAction.Action.STOP_SLEEPING;
            break;

        case 4:
            net_minecraft_network_play_client_cpacketentityaction_action = net.minecraft.network.play.client.CPacketEntityAction.Action.START_SPRINTING;
            break;

        case 5:
            net_minecraft_network_play_client_cpacketentityaction_action = net.minecraft.network.play.client.CPacketEntityAction.Action.STOP_SPRINTING;
            break;

        case 6:
            net_minecraft_network_play_client_cpacketentityaction_action = net.minecraft.network.play.client.CPacketEntityAction.Action.OPEN_INVENTORY;
            break;

        default:
            throw new NoWhenBranchMatchedException();
        }

        net.minecraft.network.play.client.CPacketEntityAction.Action net_minecraft_network_play_client_cpacketentityaction_action1 = net_minecraft_network_play_client_cpacketentityaction_action;
        CPacketEntityAction cpacketentityaction = new CPacketEntityAction(entity, net_minecraft_network_play_client_cpacketentityaction_action1);

        return (ICPacketEntityAction) (new CPacketEntityActionImpl(cpacketentityaction));
    }

    @NotNull
    public ICPacketCustomPayload createCPacketCustomPayload(@NotNull String channel, @NotNull IPacketBuffer payload) {
        Intrinsics.checkParameterIsNotNull(channel, "channel");
        Intrinsics.checkParameterIsNotNull(payload, "payload");
        boolean $i$f$unwrap = false;
        PacketBuffer packetbuffer = ((PacketBufferImpl) payload).getWrapped();
        CPacketCustomPayload cpacketcustompayload = new CPacketCustomPayload(channel, packetbuffer);

        return (ICPacketCustomPayload) (new CPacketCustomPayloadImpl(cpacketcustompayload));
    }

    @NotNull
    public ICPacketCloseWindow createCPacketCloseWindow(int windowId) {
        return (ICPacketCloseWindow) (new CPacketCloseWindowImpl(new CPacketCloseWindow(windowId)));
    }

    @NotNull
    public ICPacketCloseWindow createCPacketCloseWindow() {
        return (ICPacketCloseWindow) (new CPacketCloseWindowImpl(new CPacketCloseWindow()));
    }

    @NotNull
    public ICPacketPlayer createCPacketPlayer(boolean onGround) {
        return (ICPacketPlayer) (new CPacketPlayerImpl(new CPacketPlayer(onGround)));
    }

    @NotNull
    public IPacket createCPacketTabComplete(@NotNull String text) {
        Intrinsics.checkParameterIsNotNull(text, "text");
        return (IPacket) (new PacketImpl((Packet) (new CPacketTabComplete(text, (BlockPos) null, false))));
    }

    @NotNull
    public ICPacketAnimation createCPacketAnimation() {
        return (ICPacketAnimation) (new CPacketAnimationImpl(new CPacketAnimation(EnumHand.MAIN_HAND)));
    }

    @NotNull
    public ICPacketKeepAlive createCPacketKeepAlive() {
        return (ICPacketKeepAlive) (new CPacketKeepAliveImpl(new CPacketKeepAlive()));
    }

    public boolean isEntityAnimal(@Nullable Object obj) {
        return obj instanceof EntityImpl && ((EntityImpl) obj).getWrapped() instanceof EntityAnimal;
    }

    public boolean isEntitySquid(@Nullable Object obj) {
        return obj instanceof EntityImpl && ((EntityImpl) obj).getWrapped() instanceof EntitySquid;
    }

    public boolean isEntityBat(@Nullable Object obj) {
        return obj instanceof EntityImpl && ((EntityImpl) obj).getWrapped() instanceof EntityBat;
    }

    public boolean isEntityGolem(@Nullable Object obj) {
        return obj instanceof EntityImpl && ((EntityImpl) obj).getWrapped() instanceof EntityGolem;
    }

    public boolean isEntityMob(@Nullable Object obj) {
        return obj instanceof EntityImpl && ((EntityImpl) obj).getWrapped() instanceof EntityMob;
    }

    public boolean isEntityVillager(@Nullable Object obj) {
        return obj instanceof EntityImpl && ((EntityImpl) obj).getWrapped() instanceof EntityVillager;
    }

    public boolean isEntitySlime(@Nullable Object obj) {
        return obj instanceof EntityImpl && ((EntityImpl) obj).getWrapped() instanceof EntitySlime;
    }

    public boolean isEntityGhast(@Nullable Object obj) {
        return obj instanceof EntityImpl && ((EntityImpl) obj).getWrapped() instanceof EntityGhast;
    }

    public boolean isEntityDragon(@Nullable Object obj) {
        return obj instanceof EntityImpl && ((EntityImpl) obj).getWrapped() instanceof EntityDragon;
    }

    public boolean isEntityLivingBase(@Nullable Object obj) {
        return obj instanceof EntityImpl && ((EntityImpl) obj).getWrapped() instanceof EntityLivingBase;
    }

    public boolean isEntityPlayer(@Nullable Object obj) {
        return obj instanceof EntityImpl && ((EntityImpl) obj).getWrapped() instanceof EntityPlayer;
    }

    public boolean isEntityArmorStand(@Nullable Object obj) {
        return obj instanceof EntityImpl && ((EntityImpl) obj).getWrapped() instanceof EntityArmorStand;
    }

    public boolean isEntityTNTPrimed(@Nullable Object obj) {
        return obj instanceof EntityImpl && ((EntityImpl) obj).getWrapped() instanceof EntityTNTPrimed;
    }

    public boolean isEntityBoat(@Nullable Object obj) {
        return obj instanceof EntityImpl && ((EntityImpl) obj).getWrapped() instanceof EntityBoat;
    }

    public boolean isEntityMinecart(@Nullable Object obj) {
        return obj instanceof EntityImpl && ((EntityImpl) obj).getWrapped() instanceof EntityMinecart;
    }

    public boolean isEntityItem(@Nullable Object obj) {
        return obj instanceof EntityImpl && ((EntityImpl) obj).getWrapped() instanceof EntityItem;
    }

    public boolean isEntityArrow(@Nullable Object obj) {
        return obj instanceof EntityImpl && ((EntityImpl) obj).getWrapped() instanceof EntityArrow;
    }

    public boolean isEntityFallingBlock(@Nullable Object obj) {
        return obj instanceof EntityImpl && ((EntityImpl) obj).getWrapped() instanceof EntityFallingBlock;
    }

    public boolean isEntityMinecartChest(@Nullable Object obj) {
        return obj instanceof EntityImpl && ((EntityImpl) obj).getWrapped() instanceof EntityMinecartChest;
    }

    public boolean isEntityShulker(@Nullable Object obj) {
        return obj instanceof EntityImpl && ((EntityImpl) obj).getWrapped() instanceof EntityShulker;
    }

    public boolean isTileEntityChest(@Nullable Object obj) {
        return obj instanceof TileEntityImpl && ((TileEntityImpl) obj).getWrapped() instanceof TileEntityChest;
    }

    public boolean isTileEntityEnderChest(@Nullable Object obj) {
        return obj instanceof TileEntityImpl && ((TileEntityImpl) obj).getWrapped() instanceof TileEntityEnderChest;
    }

    public boolean isTileEntityFurnace(@Nullable Object obj) {
        return obj instanceof TileEntityImpl && ((TileEntityImpl) obj).getWrapped() instanceof TileEntityFurnace;
    }

    public boolean isTileEntityDispenser(@Nullable Object obj) {
        return obj instanceof TileEntityImpl && ((TileEntityImpl) obj).getWrapped() instanceof TileEntityDispenser;
    }

    public boolean isTileEntityHopper(@Nullable Object obj) {
        return obj instanceof TileEntityImpl && ((TileEntityImpl) obj).getWrapped() instanceof TileEntityHopper;
    }

    public boolean isTileEntityShulkerBox(@Nullable Object obj) {
        return obj instanceof TileEntityImpl && ((TileEntityImpl) obj).getWrapped() instanceof TileEntityShulkerBox;
    }

    public boolean isSPacketChat(@Nullable Object obj) {
        return obj instanceof PacketImpl && ((PacketImpl) obj).getWrapped() instanceof SPacketChat;
    }

    public boolean isSPacketEntity(@Nullable Object obj) {
        return obj instanceof PacketImpl && ((PacketImpl) obj).getWrapped() instanceof SPacketEntity;
    }

    public boolean isSPacketResourcePackSend(@Nullable Object obj) {
        return obj instanceof PacketImpl && ((PacketImpl) obj).getWrapped() instanceof SPacketResourcePackSend;
    }

    public boolean isSPacketPlayerPosLook(@Nullable Object obj) {
        return obj instanceof PacketImpl && ((PacketImpl) obj).getWrapped() instanceof SPacketPlayerPosLook;
    }

    public boolean isSPacketAnimation(@Nullable Object obj) {
        return obj instanceof PacketImpl && ((PacketImpl) obj).getWrapped() instanceof SPacketAnimation;
    }

    public boolean isSPacketEntityVelocity(@Nullable Object obj) {
        return obj instanceof PacketImpl && ((PacketImpl) obj).getWrapped() instanceof SPacketEntityVelocity;
    }

    public boolean isSPacketExplosion(@Nullable Object obj) {
        return obj instanceof PacketImpl && ((PacketImpl) obj).getWrapped() instanceof SPacketEntityVelocity;
    }

    public boolean isSPacketCloseWindow(@Nullable Object obj) {
        return obj instanceof PacketImpl && ((PacketImpl) obj).getWrapped() instanceof SPacketCloseWindow;
    }

    public boolean isSPacketTabComplete(@Nullable Object obj) {
        return obj instanceof PacketImpl && ((PacketImpl) obj).getWrapped() instanceof SPacketTabComplete;
    }

    public boolean isCPacketPlayer(@Nullable Object obj) {
        return obj instanceof PacketImpl && ((PacketImpl) obj).getWrapped() instanceof CPacketPlayer;
    }

    public boolean isCPacketPlayerBlockPlacement(@Nullable Object obj) {
        return obj instanceof PacketImpl && ((PacketImpl) obj).getWrapped() instanceof CPacketPlayerTryUseItemOnBlock;
    }

    public boolean isCPacketUseEntity(@Nullable Object obj) {
        return obj instanceof PacketImpl && ((PacketImpl) obj).getWrapped() instanceof CPacketUseEntity;
    }

    public boolean isCPacketCloseWindow(@Nullable Object obj) {
        return obj instanceof PacketImpl && ((PacketImpl) obj).getWrapped() instanceof CPacketCloseWindow;
    }

    public boolean isCPacketChatMessage(@Nullable Object obj) {
        return obj instanceof PacketImpl && ((PacketImpl) obj).getWrapped() instanceof CPacketChatMessage;
    }

    public boolean isCPacketKeepAlive(@Nullable Object obj) {
        return obj instanceof PacketImpl && ((PacketImpl) obj).getWrapped() instanceof CPacketKeepAlive;
    }

    public boolean isCPacketPlayerPosition(@Nullable Object obj) {
        return obj instanceof PacketImpl && ((PacketImpl) obj).getWrapped() instanceof Position;
    }

    public boolean isCPacketPlayerPosLook(@Nullable Object obj) {
        return obj instanceof PacketImpl && ((PacketImpl) obj).getWrapped() instanceof PositionRotation;
    }

    public boolean isCPacketClientStatus(@Nullable Object obj) {
        return obj instanceof PacketImpl && ((PacketImpl) obj).getWrapped() instanceof CPacketClientStatus;
    }

    public boolean isCPacketAnimation(@Nullable Object obj) {
        return obj instanceof PacketImpl && ((PacketImpl) obj).getWrapped() instanceof CPacketAnimation;
    }

    public boolean isCPacketEntityAction(@Nullable Object obj) {
        return obj instanceof PacketImpl && ((PacketImpl) obj).getWrapped() instanceof CPacketEntityAction;
    }

    public boolean isSPacketWindowItems(@Nullable Object obj) {
        return obj instanceof PacketImpl && ((PacketImpl) obj).getWrapped() instanceof SPacketWindowItems;
    }

    public boolean isCPacketHeldItemChange(@Nullable Object obj) {
        return obj instanceof PacketImpl && ((PacketImpl) obj).getWrapped() instanceof CPacketHeldItemChange;
    }

    public boolean isCPacketPlayerLook(@Nullable Object obj) {
        return obj instanceof PacketImpl && ((PacketImpl) obj).getWrapped() instanceof Rotation;
    }

    public boolean isCPacketCustomPayload(@Nullable Object obj) {
        return obj instanceof PacketImpl && ((PacketImpl) obj).getWrapped() instanceof CPacketCustomPayload;
    }

    public boolean isCPacketHandshake(@Nullable Object obj) {
        return obj instanceof PacketImpl && ((PacketImpl) obj).getWrapped() instanceof C00Handshake;
    }

    public boolean isCPacketPlayerDigging(@Nullable Object obj) {
        return obj instanceof PacketImpl && ((PacketImpl) obj).getWrapped() instanceof CPacketPlayerDigging;
    }

    public boolean isCPacketTryUseItem(@Nullable Object obj) {
        return obj instanceof PacketImpl && ((PacketImpl) obj).getWrapped() instanceof CPacketPlayerTryUseItem;
    }

    public boolean isItemSword(@Nullable Object obj) {
        return obj instanceof ItemImpl && ((ItemImpl) obj).getWrapped() instanceof ItemSword;
    }

    public boolean isItemTool(@Nullable Object obj) {
        return obj instanceof ItemImpl && ((ItemImpl) obj).getWrapped() instanceof ItemTool;
    }

    public boolean isItemArmor(@Nullable Object obj) {
        return obj instanceof ItemImpl && ((ItemImpl) obj).getWrapped() instanceof ItemArmor;
    }

    public boolean isItemPotion(@Nullable Object obj) {
        return obj instanceof ItemImpl && ((ItemImpl) obj).getWrapped() instanceof ItemPotion;
    }

    public boolean isItemBlock(@Nullable Object obj) {
        return obj instanceof ItemImpl && ((ItemImpl) obj).getWrapped() instanceof ItemBlock;
    }

    public boolean isItemBow(@Nullable Object obj) {
        return obj instanceof ItemImpl && ((ItemImpl) obj).getWrapped() instanceof ItemBow;
    }

    public boolean isItemBucket(@Nullable Object obj) {
        return obj instanceof ItemImpl && ((ItemImpl) obj).getWrapped() instanceof ItemBucket;
    }

    public boolean isItemFood(@Nullable Object obj) {
        return obj instanceof ItemImpl && ((ItemImpl) obj).getWrapped() instanceof ItemFood;
    }

    public boolean isItemBucketMilk(@Nullable Object obj) {
        return obj instanceof ItemImpl && ((ItemImpl) obj).getWrapped() instanceof ItemBucketMilk;
    }

    public boolean isItemPickaxe(@Nullable Object obj) {
        return obj instanceof ItemImpl && ((ItemImpl) obj).getWrapped() instanceof ItemPickaxe;
    }

    public boolean isItemAxe(@Nullable Object obj) {
        return obj instanceof ItemImpl && ((ItemImpl) obj).getWrapped() instanceof ItemAxe;
    }

    public boolean isItemBed(@Nullable Object obj) {
        return obj instanceof ItemImpl && ((ItemImpl) obj).getWrapped() instanceof ItemBed;
    }

    public boolean isItemEnderPearl(@Nullable Object obj) {
        return obj instanceof ItemImpl && ((ItemImpl) obj).getWrapped() instanceof ItemEnderPearl;
    }

    public boolean isItemEnchantedBook(@Nullable Object obj) {
        return obj instanceof ItemImpl && ((ItemImpl) obj).getWrapped() instanceof ItemEnchantedBook;
    }

    public boolean isItemBoat(@Nullable Object obj) {
        return obj instanceof ItemImpl && ((ItemImpl) obj).getWrapped() instanceof ItemBoat;
    }

    public boolean isItemMinecart(@Nullable Object obj) {
        return obj instanceof ItemImpl && ((ItemImpl) obj).getWrapped() instanceof ItemMinecart;
    }

    public boolean isItemAppleGold(@Nullable Object obj) {
        return obj instanceof ItemImpl && ((ItemImpl) obj).getWrapped() instanceof ItemAppleGold;
    }

    public boolean isItemSnowball(@Nullable Object obj) {
        return obj instanceof ItemImpl && ((ItemImpl) obj).getWrapped() instanceof ItemSnowball;
    }

    public boolean isItemEgg(@Nullable Object obj) {
        return obj instanceof ItemImpl && ((ItemImpl) obj).getWrapped() instanceof ItemEgg;
    }

    public boolean isItemFishingRod(@Nullable Object obj) {
        return obj instanceof ItemImpl && ((ItemImpl) obj).getWrapped() instanceof ItemFishingRod;
    }

    public boolean isItemAir(@Nullable Object obj) {
        return obj instanceof ItemImpl && ((ItemImpl) obj).getWrapped() instanceof ItemAir;
    }

    public boolean isBlockAir(@Nullable Object obj) {
        return obj instanceof BlockImpl && ((BlockImpl) obj).getWrapped() instanceof BlockAir;
    }

    public boolean isBlockFence(@Nullable Object obj) {
        return obj instanceof BlockImpl && ((BlockImpl) obj).getWrapped() instanceof BlockFence;
    }

    public boolean isBlockSnow(@Nullable Object obj) {
        return obj instanceof BlockImpl && ((BlockImpl) obj).getWrapped() instanceof BlockSnow;
    }

    public boolean isBlockLadder(@Nullable Object obj) {
        return obj instanceof BlockImpl && ((BlockImpl) obj).getWrapped() instanceof BlockLadder;
    }

    public boolean isBlockVine(@Nullable Object obj) {
        return obj instanceof BlockImpl && ((BlockImpl) obj).getWrapped() instanceof BlockVine;
    }

    public boolean isBlockSlime(@Nullable Object obj) {
        return obj instanceof BlockImpl && ((BlockImpl) obj).getWrapped() instanceof BlockSlime;
    }

    public boolean isBlockSlab(@Nullable Object obj) {
        return obj instanceof BlockImpl && ((BlockImpl) obj).getWrapped() instanceof BlockSlab;
    }

    public boolean isBlockStairs(@Nullable Object obj) {
        return obj instanceof BlockImpl && ((BlockImpl) obj).getWrapped() instanceof BlockStairs;
    }

    public boolean isBlockCarpet(@Nullable Object obj) {
        return obj instanceof BlockImpl && ((BlockImpl) obj).getWrapped() instanceof BlockCarpet;
    }

    public boolean isBlockPane(@Nullable Object obj) {
        return obj instanceof BlockImpl && ((BlockImpl) obj).getWrapped() instanceof BlockPane;
    }

    public boolean isBlockLiquid(@Nullable Object obj) {
        return obj instanceof BlockImpl && ((BlockImpl) obj).getWrapped() instanceof BlockLiquid;
    }

    public boolean isBlockCactus(@Nullable Object obj) {
        return obj instanceof BlockImpl && ((BlockImpl) obj).getWrapped() instanceof BlockCactus;
    }

    public boolean isBlockBedrock(@Nullable Object obj) {
        return obj instanceof BlockImpl && Intrinsics.areEqual(((BlockImpl) obj).getWrapped(), Blocks.BEDROCK);
    }

    public boolean isBlockBush(@Nullable Object obj) {
        return obj instanceof BlockImpl && ((BlockImpl) obj).getWrapped() instanceof BlockBush;
    }

    public boolean isGuiInventory(@Nullable Object obj) {
        return obj instanceof GuiImpl && ((GuiImpl) obj).getWrapped() instanceof GuiInventory;
    }

    public boolean isGuiContainer(@Nullable Object obj) {
        return obj instanceof GuiImpl && ((GuiImpl) obj).getWrapped() instanceof GuiContainer;
    }

    public boolean isGuiGameOver(@Nullable Object obj) {
        return obj instanceof GuiImpl && ((GuiImpl) obj).getWrapped() instanceof GuiGameOver;
    }

    public boolean isGuiChat(@Nullable Object obj) {
        return obj instanceof GuiImpl && ((GuiImpl) obj).getWrapped() instanceof GuiChat;
    }

    public boolean isGuiIngameMenu(@Nullable Object obj) {
        return obj instanceof GuiImpl && ((GuiImpl) obj).getWrapped() instanceof GuiIngameMenu;
    }

    public boolean isGuiChest(@Nullable Object obj) {
        return obj instanceof GuiImpl && ((GuiImpl) obj).getWrapped() instanceof GuiChest;
    }

    public boolean isGuiHudDesigner(@Nullable Object obj) {
        return obj instanceof GuiScreenImpl && ((GuiScreenImpl) obj).getWrapped() instanceof GuiScreenWrapper && ((GuiScreenWrapper) ((GuiScreenImpl) obj).getWrapped()).getWrapped() instanceof GuiHudDesigner;
    }

    public boolean isClickGui(@Nullable Object obj) {
        return obj instanceof GuiScreenImpl && ((GuiScreenImpl) obj).getWrapped() instanceof GuiScreenWrapper && ((GuiScreenWrapper) ((GuiScreenImpl) obj).getWrapped()).getWrapped() instanceof ClickGui;
    }

    @NotNull
    public IPotion getPotionEnum(@NotNull PotionType type) {
        Intrinsics.checkParameterIsNotNull(type, "type");
        PotionImpl potionimpl = new PotionImpl;
        Potion potion;

        switch (ClassProviderImpl$WhenMappings.$EnumSwitchMapping$1[type.ordinal()]) {
        case 1:
            potion = MobEffects.JUMP_BOOST;
            break;

        case 2:
            potion = MobEffects.INSTANT_HEALTH;
            break;

        case 3:
            potion = MobEffects.REGENERATION;
            break;

        case 4:
            potion = MobEffects.BLINDNESS;
            break;

        case 5:
            potion = MobEffects.SPEED;
            break;

        case 6:
            potion = MobEffects.HUNGER;
            break;

        case 7:
            potion = MobEffects.MINING_FATIGUE;
            break;

        case 8:
            potion = MobEffects.NAUSEA;
            break;

        case 9:
            potion = MobEffects.WEAKNESS;
            break;

        case 10:
            potion = MobEffects.SLOWNESS;
            break;

        case 11:
            potion = MobEffects.INSTANT_DAMAGE;
            break;

        case 12:
            potion = MobEffects.WITHER;
            break;

        case 13:
            potion = MobEffects.POISON;
            break;

        case 14:
            potion = MobEffects.NIGHT_VISION;
            break;

        default:
            throw new NoWhenBranchMatchedException();
        }

        Intrinsics.checkExpressionValueIsNotNull(potion, "when (type) {\n          …s.NIGHT_VISION\n\n        }");
        potionimpl.<init>(potion);
        return (IPotion) potionimpl;
    }

    @NotNull
    public IEnumFacing getEnumFacing(@NotNull EnumFacingType type) {
        Intrinsics.checkParameterIsNotNull(type, "type");
        EnumFacingImpl enumfacingimpl = new EnumFacingImpl;
        EnumFacing enumfacing;

        switch (ClassProviderImpl$WhenMappings.$EnumSwitchMapping$2[type.ordinal()]) {
        case 1:
            enumfacing = EnumFacing.DOWN;
            break;

        case 2:
            enumfacing = EnumFacing.UP;
            break;

        case 3:
            enumfacing = EnumFacing.NORTH;
            break;

        case 4:
            enumfacing = EnumFacing.SOUTH;
            break;

        case 5:
            enumfacing = EnumFacing.WEST;
            break;

        case 6:
            enumfacing = EnumFacing.EAST;
            break;

        default:
            throw new NoWhenBranchMatchedException();
        }

        enumfacingimpl.<init>(enumfacing);
        return (IEnumFacing) enumfacingimpl;
    }

    @NotNull
    public IBlock getBlockEnum(@NotNull BlockType type) {
        Intrinsics.checkParameterIsNotNull(type, "type");
        Block $this$wrap$iv;
        boolean $i$f$wrap;
        IBlock iblock;
        BlockDynamicLiquid blockdynamicliquid;
        BlockStaticLiquid blockstaticliquid;
        Block block;

        switch (ClassProviderImpl$WhenMappings.$EnumSwitchMapping$3[type.ordinal()]) {
        case 1:
            block = Blocks.ENCHANTING_TABLE;
            Intrinsics.checkExpressionValueIsNotNull(Blocks.ENCHANTING_TABLE, "Blocks.ENCHANTING_TABLE");
            $this$wrap$iv = block;
            $i$f$wrap = false;
            iblock = (IBlock) (new BlockImpl($this$wrap$iv));
            break;

        case 2:
            BlockChest blockchest = Blocks.CHEST;

            Intrinsics.checkExpressionValueIsNotNull(Blocks.CHEST, "Blocks.CHEST");
            $this$wrap$iv = (Block) blockchest;
            $i$f$wrap = false;
            iblock = (IBlock) (new BlockImpl($this$wrap$iv));
            break;

        case 3:
            block = Blocks.ENDER_CHEST;
            Intrinsics.checkExpressionValueIsNotNull(Blocks.ENDER_CHEST, "Blocks.ENDER_CHEST");
            $this$wrap$iv = block;
            $i$f$wrap = false;
            iblock = (IBlock) (new BlockImpl($this$wrap$iv));
            break;

        case 4:
            block = Blocks.TRAPPED_CHEST;
            Intrinsics.checkExpressionValueIsNotNull(Blocks.TRAPPED_CHEST, "Blocks.TRAPPED_CHEST");
            $this$wrap$iv = block;
            $i$f$wrap = false;
            iblock = (IBlock) (new BlockImpl($this$wrap$iv));
            break;

        case 5:
            block = Blocks.ANVIL;
            Intrinsics.checkExpressionValueIsNotNull(Blocks.ANVIL, "Blocks.ANVIL");
            $this$wrap$iv = block;
            $i$f$wrap = false;
            iblock = (IBlock) (new BlockImpl($this$wrap$iv));
            break;

        case 6:
            BlockSand blocksand = Blocks.SAND;

            Intrinsics.checkExpressionValueIsNotNull(Blocks.SAND, "Blocks.SAND");
            $this$wrap$iv = (Block) blocksand;
            $i$f$wrap = false;
            iblock = (IBlock) (new BlockImpl($this$wrap$iv));
            break;

        case 7:
            block = Blocks.WEB;
            Intrinsics.checkExpressionValueIsNotNull(Blocks.WEB, "Blocks.WEB");
            $this$wrap$iv = block;
            $i$f$wrap = false;
            iblock = (IBlock) (new BlockImpl($this$wrap$iv));
            break;

        case 8:
            block = Blocks.TORCH;
            Intrinsics.checkExpressionValueIsNotNull(Blocks.TORCH, "Blocks.TORCH");
            $this$wrap$iv = block;
            $i$f$wrap = false;
            iblock = (IBlock) (new BlockImpl($this$wrap$iv));
            break;

        case 9:
            block = Blocks.CRAFTING_TABLE;
            Intrinsics.checkExpressionValueIsNotNull(Blocks.CRAFTING_TABLE, "Blocks.CRAFTING_TABLE");
            $this$wrap$iv = block;
            $i$f$wrap = false;
            iblock = (IBlock) (new BlockImpl($this$wrap$iv));
            break;

        case 10:
            block = Blocks.FURNACE;
            Intrinsics.checkExpressionValueIsNotNull(Blocks.FURNACE, "Blocks.FURNACE");
            $this$wrap$iv = block;
            $i$f$wrap = false;
            iblock = (IBlock) (new BlockImpl($this$wrap$iv));
            break;

        case 11:
            block = Blocks.WATERLILY;
            Intrinsics.checkExpressionValueIsNotNull(Blocks.WATERLILY, "Blocks.WATERLILY");
            $this$wrap$iv = block;
            $i$f$wrap = false;
            iblock = (IBlock) (new BlockImpl($this$wrap$iv));
            break;

        case 12:
            block = Blocks.DISPENSER;
            Intrinsics.checkExpressionValueIsNotNull(Blocks.DISPENSER, "Blocks.DISPENSER");
            $this$wrap$iv = block;
            $i$f$wrap = false;
            iblock = (IBlock) (new BlockImpl($this$wrap$iv));
            break;

        case 13:
            block = Blocks.STONE_PRESSURE_PLATE;
            Intrinsics.checkExpressionValueIsNotNull(Blocks.STONE_PRESSURE_PLATE, "Blocks.STONE_PRESSURE_PLATE");
            $this$wrap$iv = block;
            $i$f$wrap = false;
            iblock = (IBlock) (new BlockImpl($this$wrap$iv));
            break;

        case 14:
            block = Blocks.WOODEN_PRESSURE_PLATE;
            Intrinsics.checkExpressionValueIsNotNull(Blocks.WOODEN_PRESSURE_PLATE, "Blocks.WOODEN_PRESSURE_PLATE");
            $this$wrap$iv = block;
            $i$f$wrap = false;
            iblock = (IBlock) (new BlockImpl($this$wrap$iv));
            break;

        case 15:
            block = Blocks.TNT;
            Intrinsics.checkExpressionValueIsNotNull(Blocks.TNT, "Blocks.TNT");
            $this$wrap$iv = block;
            $i$f$wrap = false;
            iblock = (IBlock) (new BlockImpl($this$wrap$iv));
            break;

        case 16:
            block = Blocks.STANDING_BANNER;
            Intrinsics.checkExpressionValueIsNotNull(Blocks.STANDING_BANNER, "Blocks.STANDING_BANNER");
            $this$wrap$iv = block;
            $i$f$wrap = false;
            iblock = (IBlock) (new BlockImpl($this$wrap$iv));
            break;

        case 17:
            block = Blocks.WALL_BANNER;
            Intrinsics.checkExpressionValueIsNotNull(Blocks.WALL_BANNER, "Blocks.WALL_BANNER");
            $this$wrap$iv = block;
            $i$f$wrap = false;
            iblock = (IBlock) (new BlockImpl($this$wrap$iv));
            break;

        case 18:
            block = Blocks.REDSTONE_TORCH;
            Intrinsics.checkExpressionValueIsNotNull(Blocks.REDSTONE_TORCH, "Blocks.REDSTONE_TORCH");
            $this$wrap$iv = block;
            $i$f$wrap = false;
            iblock = (IBlock) (new BlockImpl($this$wrap$iv));
            break;

        case 19:
            block = Blocks.NOTEBLOCK;
            Intrinsics.checkExpressionValueIsNotNull(Blocks.NOTEBLOCK, "Blocks.NOTEBLOCK");
            $this$wrap$iv = block;
            $i$f$wrap = false;
            iblock = (IBlock) (new BlockImpl($this$wrap$iv));
            break;

        case 20:
            block = Blocks.DROPPER;
            Intrinsics.checkExpressionValueIsNotNull(Blocks.DROPPER, "Blocks.DROPPER");
            $this$wrap$iv = block;
            $i$f$wrap = false;
            iblock = (IBlock) (new BlockImpl($this$wrap$iv));
            break;

        case 21:
            block = Blocks.SNOW_LAYER;
            Intrinsics.checkExpressionValueIsNotNull(Blocks.SNOW_LAYER, "Blocks.SNOW_LAYER");
            $this$wrap$iv = block;
            $i$f$wrap = false;
            iblock = (IBlock) (new BlockImpl($this$wrap$iv));
            break;

        case 22:
            block = Blocks.AIR;
            Intrinsics.checkExpressionValueIsNotNull(Blocks.AIR, "Blocks.AIR");
            $this$wrap$iv = block;
            $i$f$wrap = false;
            iblock = (IBlock) (new BlockImpl($this$wrap$iv));
            break;

        case 23:
            block = Blocks.PACKED_ICE;
            Intrinsics.checkExpressionValueIsNotNull(Blocks.PACKED_ICE, "Blocks.PACKED_ICE");
            $this$wrap$iv = block;
            $i$f$wrap = false;
            iblock = (IBlock) (new BlockImpl($this$wrap$iv));
            break;

        case 24:
            block = Blocks.ICE;
            Intrinsics.checkExpressionValueIsNotNull(Blocks.ICE, "Blocks.ICE");
            $this$wrap$iv = block;
            $i$f$wrap = false;
            iblock = (IBlock) (new BlockImpl($this$wrap$iv));
            break;

        case 25:
            blockstaticliquid = Blocks.WATER;
            Intrinsics.checkExpressionValueIsNotNull(Blocks.WATER, "Blocks.WATER");
            $this$wrap$iv = (Block) blockstaticliquid;
            $i$f$wrap = false;
            iblock = (IBlock) (new BlockImpl($this$wrap$iv));
            break;

        case 26:
            block = Blocks.BARRIER;
            Intrinsics.checkExpressionValueIsNotNull(Blocks.BARRIER, "Blocks.BARRIER");
            $this$wrap$iv = block;
            $i$f$wrap = false;
            iblock = (IBlock) (new BlockImpl($this$wrap$iv));
            break;

        case 27:
            blockdynamicliquid = Blocks.FLOWING_WATER;
            Intrinsics.checkExpressionValueIsNotNull(Blocks.FLOWING_WATER, "Blocks.FLOWING_WATER");
            $this$wrap$iv = (Block) blockdynamicliquid;
            $i$f$wrap = false;
            iblock = (IBlock) (new BlockImpl($this$wrap$iv));
            break;

        case 28:
            block = Blocks.COAL_ORE;
            Intrinsics.checkExpressionValueIsNotNull(Blocks.COAL_ORE, "Blocks.COAL_ORE");
            $this$wrap$iv = block;
            $i$f$wrap = false;
            iblock = (IBlock) (new BlockImpl($this$wrap$iv));
            break;

        case 29:
            block = Blocks.IRON_ORE;
            Intrinsics.checkExpressionValueIsNotNull(Blocks.IRON_ORE, "Blocks.IRON_ORE");
            $this$wrap$iv = block;
            $i$f$wrap = false;
            iblock = (IBlock) (new BlockImpl($this$wrap$iv));
            break;

        case 30:
            block = Blocks.GOLD_ORE;
            Intrinsics.checkExpressionValueIsNotNull(Blocks.GOLD_ORE, "Blocks.GOLD_ORE");
            $this$wrap$iv = block;
            $i$f$wrap = false;
            iblock = (IBlock) (new BlockImpl($this$wrap$iv));
            break;

        case 31:
            block = Blocks.REDSTONE_ORE;
            Intrinsics.checkExpressionValueIsNotNull(Blocks.REDSTONE_ORE, "Blocks.REDSTONE_ORE");
            $this$wrap$iv = block;
            $i$f$wrap = false;
            iblock = (IBlock) (new BlockImpl($this$wrap$iv));
            break;

        case 32:
            block = Blocks.LAPIS_ORE;
            Intrinsics.checkExpressionValueIsNotNull(Blocks.LAPIS_ORE, "Blocks.LAPIS_ORE");
            $this$wrap$iv = block;
            $i$f$wrap = false;
            iblock = (IBlock) (new BlockImpl($this$wrap$iv));
            break;

        case 33:
            block = Blocks.DIAMOND_ORE;
            Intrinsics.checkExpressionValueIsNotNull(Blocks.DIAMOND_ORE, "Blocks.DIAMOND_ORE");
            $this$wrap$iv = block;
            $i$f$wrap = false;
            iblock = (IBlock) (new BlockImpl($this$wrap$iv));
            break;

        case 34:
            block = Blocks.EMERALD_ORE;
            Intrinsics.checkExpressionValueIsNotNull(Blocks.EMERALD_ORE, "Blocks.EMERALD_ORE");
            $this$wrap$iv = block;
            $i$f$wrap = false;
            iblock = (IBlock) (new BlockImpl($this$wrap$iv));
            break;

        case 35:
            block = Blocks.QUARTZ_ORE;
            Intrinsics.checkExpressionValueIsNotNull(Blocks.QUARTZ_ORE, "Blocks.QUARTZ_ORE");
            $this$wrap$iv = block;
            $i$f$wrap = false;
            iblock = (IBlock) (new BlockImpl($this$wrap$iv));
            break;

        case 36:
            block = Blocks.CLAY;
            Intrinsics.checkExpressionValueIsNotNull(Blocks.CLAY, "Blocks.CLAY");
            $this$wrap$iv = block;
            $i$f$wrap = false;
            iblock = (IBlock) (new BlockImpl($this$wrap$iv));
            break;

        case 37:
            block = Blocks.GLOWSTONE;
            Intrinsics.checkExpressionValueIsNotNull(Blocks.GLOWSTONE, "Blocks.GLOWSTONE");
            $this$wrap$iv = block;
            $i$f$wrap = false;
            iblock = (IBlock) (new BlockImpl($this$wrap$iv));
            break;

        case 38:
            block = Blocks.LADDER;
            Intrinsics.checkExpressionValueIsNotNull(Blocks.LADDER, "Blocks.LADDER");
            $this$wrap$iv = block;
            $i$f$wrap = false;
            iblock = (IBlock) (new BlockImpl($this$wrap$iv));
            break;

        case 39:
            block = Blocks.COAL_BLOCK;
            Intrinsics.checkExpressionValueIsNotNull(Blocks.COAL_BLOCK, "Blocks.COAL_BLOCK");
            $this$wrap$iv = block;
            $i$f$wrap = false;
            iblock = (IBlock) (new BlockImpl($this$wrap$iv));
            break;

        case 40:
            block = Blocks.IRON_BLOCK;
            Intrinsics.checkExpressionValueIsNotNull(Blocks.IRON_BLOCK, "Blocks.IRON_BLOCK");
            $this$wrap$iv = block;
            $i$f$wrap = false;
            iblock = (IBlock) (new BlockImpl($this$wrap$iv));
            break;

        case 41:
            block = Blocks.GOLD_BLOCK;
            Intrinsics.checkExpressionValueIsNotNull(Blocks.GOLD_BLOCK, "Blocks.GOLD_BLOCK");
            $this$wrap$iv = block;
            $i$f$wrap = false;
            iblock = (IBlock) (new BlockImpl($this$wrap$iv));
            break;

        case 42:
            block = Blocks.DIAMOND_BLOCK;
            Intrinsics.checkExpressionValueIsNotNull(Blocks.DIAMOND_BLOCK, "Blocks.DIAMOND_BLOCK");
            $this$wrap$iv = block;
            $i$f$wrap = false;
            iblock = (IBlock) (new BlockImpl($this$wrap$iv));
            break;

        case 43:
            block = Blocks.EMERALD_BLOCK;
            Intrinsics.checkExpressionValueIsNotNull(Blocks.EMERALD_BLOCK, "Blocks.EMERALD_BLOCK");
            $this$wrap$iv = block;
            $i$f$wrap = false;
            iblock = (IBlock) (new BlockImpl($this$wrap$iv));
            break;

        case 44:
            block = Blocks.REDSTONE_BLOCK;
            Intrinsics.checkExpressionValueIsNotNull(Blocks.REDSTONE_BLOCK, "Blocks.REDSTONE_BLOCK");
            $this$wrap$iv = block;
            $i$f$wrap = false;
            iblock = (IBlock) (new BlockImpl($this$wrap$iv));
            break;

        case 45:
            block = Blocks.LAPIS_BLOCK;
            Intrinsics.checkExpressionValueIsNotNull(Blocks.LAPIS_BLOCK, "Blocks.LAPIS_BLOCK");
            $this$wrap$iv = block;
            $i$f$wrap = false;
            iblock = (IBlock) (new BlockImpl($this$wrap$iv));
            break;

        case 46:
            BlockFire blockfire = Blocks.FIRE;

            Intrinsics.checkExpressionValueIsNotNull(Blocks.FIRE, "Blocks.FIRE");
            $this$wrap$iv = (Block) blockfire;
            $i$f$wrap = false;
            iblock = (IBlock) (new BlockImpl($this$wrap$iv));
            break;

        case 47:
            block = Blocks.MOSSY_COBBLESTONE;
            Intrinsics.checkExpressionValueIsNotNull(Blocks.MOSSY_COBBLESTONE, "Blocks.MOSSY_COBBLESTONE");
            $this$wrap$iv = block;
            $i$f$wrap = false;
            iblock = (IBlock) (new BlockImpl($this$wrap$iv));
            break;

        case 48:
            block = Blocks.MOB_SPAWNER;
            Intrinsics.checkExpressionValueIsNotNull(Blocks.MOB_SPAWNER, "Blocks.MOB_SPAWNER");
            $this$wrap$iv = block;
            $i$f$wrap = false;
            iblock = (IBlock) (new BlockImpl($this$wrap$iv));
            break;

        case 49:
            block = Blocks.END_PORTAL_FRAME;
            Intrinsics.checkExpressionValueIsNotNull(Blocks.END_PORTAL_FRAME, "Blocks.END_PORTAL_FRAME");
            $this$wrap$iv = block;
            $i$f$wrap = false;
            iblock = (IBlock) (new BlockImpl($this$wrap$iv));
            break;

        case 50:
            block = Blocks.BOOKSHELF;
            Intrinsics.checkExpressionValueIsNotNull(Blocks.BOOKSHELF, "Blocks.BOOKSHELF");
            $this$wrap$iv = block;
            $i$f$wrap = false;
            iblock = (IBlock) (new BlockImpl($this$wrap$iv));
            break;

        case 51:
            block = Blocks.COMMAND_BLOCK;
            Intrinsics.checkExpressionValueIsNotNull(Blocks.COMMAND_BLOCK, "Blocks.COMMAND_BLOCK");
            $this$wrap$iv = block;
            $i$f$wrap = false;
            iblock = (IBlock) (new BlockImpl($this$wrap$iv));
            break;

        case 52:
            blockstaticliquid = Blocks.LAVA;
            Intrinsics.checkExpressionValueIsNotNull(Blocks.LAVA, "Blocks.LAVA");
            $this$wrap$iv = (Block) blockstaticliquid;
            $i$f$wrap = false;
            iblock = (IBlock) (new BlockImpl($this$wrap$iv));
            break;

        case 53:
            blockdynamicliquid = Blocks.FLOWING_LAVA;
            Intrinsics.checkExpressionValueIsNotNull(Blocks.FLOWING_LAVA, "Blocks.FLOWING_LAVA");
            $this$wrap$iv = (Block) blockdynamicliquid;
            $i$f$wrap = false;
            iblock = (IBlock) (new BlockImpl($this$wrap$iv));
            break;

        case 54:
            block = Blocks.LIT_FURNACE;
            Intrinsics.checkExpressionValueIsNotNull(Blocks.LIT_FURNACE, "Blocks.LIT_FURNACE");
            $this$wrap$iv = block;
            $i$f$wrap = false;
            iblock = (IBlock) (new BlockImpl($this$wrap$iv));
            break;

        case 55:
            block = Blocks.DRAGON_EGG;
            Intrinsics.checkExpressionValueIsNotNull(Blocks.DRAGON_EGG, "Blocks.DRAGON_EGG");
            $this$wrap$iv = block;
            $i$f$wrap = false;
            iblock = (IBlock) (new BlockImpl($this$wrap$iv));
            break;

        case 56:
            block = Blocks.BROWN_MUSHROOM_BLOCK;
            Intrinsics.checkExpressionValueIsNotNull(Blocks.BROWN_MUSHROOM_BLOCK, "Blocks.BROWN_MUSHROOM_BLOCK");
            $this$wrap$iv = block;
            $i$f$wrap = false;
            iblock = (IBlock) (new BlockImpl($this$wrap$iv));
            break;

        case 57:
            block = Blocks.RED_MUSHROOM_BLOCK;
            Intrinsics.checkExpressionValueIsNotNull(Blocks.RED_MUSHROOM_BLOCK, "Blocks.RED_MUSHROOM_BLOCK");
            $this$wrap$iv = block;
            $i$f$wrap = false;
            iblock = (IBlock) (new BlockImpl($this$wrap$iv));
            break;

        case 58:
            block = Blocks.FARMLAND;
            Intrinsics.checkExpressionValueIsNotNull(Blocks.FARMLAND, "Blocks.FARMLAND");
            $this$wrap$iv = block;
            $i$f$wrap = false;
            iblock = (IBlock) (new BlockImpl($this$wrap$iv));
            break;

        default:
            throw new NoWhenBranchMatchedException();
        }

        return iblock;
    }

    @NotNull
    public IMaterial getMaterialEnum(@NotNull MaterialType type) {
        Intrinsics.checkParameterIsNotNull(type, "type");
        MaterialImpl materialimpl = new MaterialImpl;
        Material material;

        switch (ClassProviderImpl$WhenMappings.$EnumSwitchMapping$4[type.ordinal()]) {
        case 1:
            material = Material.AIR;
            break;

        case 2:
            material = Material.WATER;
            break;

        case 3:
            material = Material.LAVA;
            break;

        default:
            throw new NoWhenBranchMatchedException();
        }

        Intrinsics.checkExpressionValueIsNotNull(material, "when (type) {\n          …terial.LAVA\n            }");
        materialimpl.<init>(material);
        return (IMaterial) materialimpl;
    }

    @NotNull
    public IStatBase getStatEnum(@NotNull StatType type) {
        Intrinsics.checkParameterIsNotNull(type, "type");
        StatBaseImpl statbaseimpl = new StatBaseImpl;

        switch (ClassProviderImpl$WhenMappings.$EnumSwitchMapping$5[type.ordinal()]) {
        case 1:
            StatBase statbase = StatList.JUMP;

            Intrinsics.checkExpressionValueIsNotNull(StatList.JUMP, "when (type) {\n          …atList.JUMP\n            }");
            statbaseimpl.<init>(statbase);
            return (IStatBase) statbaseimpl;

        default:
            throw new NoWhenBranchMatchedException();
        }
    }

    @NotNull
    public IItem getItemEnum(@NotNull ItemType type) {
        Intrinsics.checkParameterIsNotNull(type, "type");
        ItemImpl itemimpl = new ItemImpl;
        Item item;

        switch (ClassProviderImpl$WhenMappings.$EnumSwitchMapping$6[type.ordinal()]) {
        case 1:
            item = Items.MUSHROOM_STEW;
            break;

        case 2:
            item = Items.BOWL;
            break;

        case 3:
            item = Items.FLINT_AND_STEEL;
            break;

        case 4:
            item = Items.LAVA_BUCKET;
            break;

        case 5:
            item = Items.WRITABLE_BOOK;
            break;

        case 6:
            item = Items.WATER_BUCKET;
            break;

        case 7:
            item = Items.COMMAND_BLOCK_MINECART;
            break;

        case 8:
            item = (Item) Items.POTIONITEM;
            break;

        case 9:
            item = Items.SKULL;
            break;

        case 10:
            item = (Item) Items.ARMOR_STAND;
            break;

        default:
            throw new NoWhenBranchMatchedException();
        }

        itemimpl.<init>(item);
        return (IItem) itemimpl;
    }

    @NotNull
    public IEnchantment getEnchantmentEnum(@NotNull EnchantmentType type) {
        Intrinsics.checkParameterIsNotNull(type, "type");
        EnchantmentImpl enchantmentimpl = new EnchantmentImpl;
        Enchantment enchantment;

        switch (ClassProviderImpl$WhenMappings.$EnumSwitchMapping$7[type.ordinal()]) {
        case 1:
            enchantment = Enchantments.SHARPNESS;
            break;

        case 2:
            enchantment = Enchantments.POWER;
            break;

        case 3:
            enchantment = Enchantments.PROTECTION;
            break;

        case 4:
            enchantment = Enchantments.FEATHER_FALLING;
            break;

        case 5:
            enchantment = Enchantments.PROJECTILE_PROTECTION;
            break;

        case 6:
            enchantment = Enchantments.THORNS;
            break;

        case 7:
            enchantment = Enchantments.FIRE_PROTECTION;
            break;

        case 8:
            enchantment = Enchantments.RESPIRATION;
            break;

        case 9:
            enchantment = Enchantments.AQUA_AFFINITY;
            break;

        case 10:
            enchantment = Enchantments.BLAST_PROTECTION;
            break;

        case 11:
            enchantment = Enchantments.UNBREAKING;
            break;

        default:
            throw new NoWhenBranchMatchedException();
        }

        Intrinsics.checkExpressionValueIsNotNull(enchantment, "when (type) {\n          �?.UNBREAKING\n            }");
        enchantmentimpl.<init>(enchantment);
        return (IEnchantment) enchantmentimpl;
    }

    @NotNull
    public IVertexFormat getVertexFormatEnum(@NotNull WDefaultVertexFormats type) {
        Intrinsics.checkParameterIsNotNull(type, "type");
        VertexFormatImpl vertexformatimpl = new VertexFormatImpl;
        VertexFormat vertexformat;

        switch (ClassProviderImpl$WhenMappings.$EnumSwitchMapping$8[type.ordinal()]) {
        case 1:
            vertexformat = DefaultVertexFormats.POSITION;
            break;

        case 2:
            vertexformat = DefaultVertexFormats.POSITION_TEX;
            break;

        case 3:
            vertexformat = DefaultVertexFormats.POSITION_COLOR;
            break;

        default:
            throw new NoWhenBranchMatchedException();
        }

        Intrinsics.checkExpressionValueIsNotNull(vertexformat, "when (type) {\n          …ITION_COLOR\n            }");
        vertexformatimpl.<init>(vertexformat);
        return (IVertexFormat) vertexformatimpl;
    }

    @NotNull
    public IFontRenderer wrapFontRenderer(@NotNull IWrappedFontRenderer fontRenderer) {
        Intrinsics.checkParameterIsNotNull(fontRenderer, "fontRenderer");
        return (IFontRenderer) (new FontRendererImpl((FontRenderer) (new FontRendererWrapper(fontRenderer))));
    }

    @NotNull
    public IGuiScreen wrapGuiScreen(@NotNull WrappedGuiScreen clickGui) {
        Intrinsics.checkParameterIsNotNull(clickGui, "clickGui");
        GuiScreenImpl instance = new GuiScreenImpl((GuiScreen) (new GuiScreenWrapper(clickGui)));

        clickGui.setRepresentedScreen((IGuiScreen) instance);
        return (IGuiScreen) instance;
    }

    @NotNull
    public IVertexBuffer createSafeVertexBuffer(@NotNull IVertexFormat vertexFormat) {
        Intrinsics.checkParameterIsNotNull(vertexFormat, "vertexFormat");
        boolean $i$f$wrap = false;
        VertexFormat vertexformat = ((VertexFormatImpl) vertexFormat).getWrapped();
        VertexBuffer $this$wrap$iv = (VertexBuffer) (new SafeVertexBuffer(vertexformat));

        $i$f$wrap = false;
        return (IVertexBuffer) (new VertexBufferImpl($this$wrap$iv));
    }

    public void wrapCreativeTab(@NotNull String name, @NotNull WrappedCreativeTabs wrappedCreativeTabs) {
        Intrinsics.checkParameterIsNotNull(name, "name");
        Intrinsics.checkParameterIsNotNull(wrappedCreativeTabs, "wrappedCreativeTabs");
        wrappedCreativeTabs.setRepresentedType((ICreativeTabs) (new CreativeTabsImpl((CreativeTabs) (new CreativeTabsWrapper(wrappedCreativeTabs, name)))));
    }

    public void wrapGuiSlot(@NotNull WrappedGuiSlot wrappedGuiSlot, @NotNull IMinecraft mc, int width, int height, int top, int bottom, int slotHeight) {
        Intrinsics.checkParameterIsNotNull(wrappedGuiSlot, "wrappedGuiSlot");
        Intrinsics.checkParameterIsNotNull(mc, "mc");
        new GuiSlotWrapper(wrappedGuiSlot, mc, width, height, top, bottom, slotHeight);
    }

    @NotNull
    public IGlStateManager getGlStateManager() {
        return (IGlStateManager) GlStateManagerImpl.INSTANCE;
    }

    @NotNull
    public IPacket createCPacketEncryptionResponse(@NotNull SecretKey secretKey, @NotNull PublicKey publicKey, @NotNull byte[] verifyToken) {
        Intrinsics.checkParameterIsNotNull(secretKey, "secretKey");
        Intrinsics.checkParameterIsNotNull(publicKey, "publicKey");
        Intrinsics.checkParameterIsNotNull(verifyToken, "verifyToken");
        return (IPacket) (new PacketImpl((Packet) (new CPacketEncryptionResponse(secretKey, publicKey, verifyToken))));
    }

    static {
        ClassProviderImpl classproviderimpl = new ClassProviderImpl();

        INSTANCE = classproviderimpl;
    }
}
