package net.ccbluex.liquidbounce.injection.backend;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.api.minecraft.nbt.IJsonToNBT;
import net.ccbluex.liquidbounce.api.minecraft.nbt.INBTTagCompound;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTTagCompound;
import org.jetbrains.annotations.NotNull;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016¨\u0006\u0007"},
    d2 = { "Lnet/ccbluex/liquidbounce/injection/backend/JsonToNBTImpl;", "Lnet/ccbluex/liquidbounce/api/minecraft/nbt/IJsonToNBT;", "()V", "getTagFromJson", "Lnet/ccbluex/liquidbounce/api/minecraft/nbt/INBTTagCompound;", "s", "", "LiquidBounce"}
)
public final class JsonToNBTImpl implements IJsonToNBT {

    public static final JsonToNBTImpl INSTANCE;

    @NotNull
    public INBTTagCompound getTagFromJson(@NotNull String s) {
        Intrinsics.checkParameterIsNotNull(s, "s");
        NBTTagCompound nbttagcompound = JsonToNBT.getTagFromJson(s);

        Intrinsics.checkExpressionValueIsNotNull(nbttagcompound, "JsonToNBT.getTagFromJson(s)");
        NBTTagCompound $this$wrap$iv = nbttagcompound;
        boolean $i$f$wrap = false;

        return (INBTTagCompound) (new NBTTagCompoundImpl($this$wrap$iv));
    }

    static {
        JsonToNBTImpl jsontonbtimpl = new JsonToNBTImpl();

        INSTANCE = jsontonbtimpl;
    }
}
