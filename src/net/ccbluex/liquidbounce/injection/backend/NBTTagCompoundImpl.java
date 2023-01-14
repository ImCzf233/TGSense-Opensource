package net.ccbluex.liquidbounce.injection.backend;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.api.minecraft.nbt.INBTBase;
import net.ccbluex.liquidbounce.api.minecraft.nbt.INBTTagCompound;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import org.jetbrains.annotations.NotNull;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\n\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u0003B\r\u0012\u0006\u0010\u0004\u001a\u00020\u0002¢\u0006\u0002\u0010\u0005J\u0010\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH\u0016J\u0010\u0010\n\u001a\u00020\u000b2\u0006\u0010\b\u001a\u00020\tH\u0016J\u0018\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\t2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016J\u0018\u0010\u0011\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\t2\u0006\u0010\u000f\u001a\u00020\tH\u0016J\u0018\u0010\u0012\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\t2\u0006\u0010\u0013\u001a\u00020\u0014H\u0016¨\u0006\u0015"},
    d2 = { "Lnet/ccbluex/liquidbounce/injection/backend/NBTTagCompoundImpl;", "Lnet/ccbluex/liquidbounce/injection/backend/NBTBaseImpl;", "Lnet/minecraft/nbt/NBTTagCompound;", "Lnet/ccbluex/liquidbounce/api/minecraft/nbt/INBTTagCompound;", "wrapped", "(Lnet/minecraft/nbt/NBTTagCompound;)V", "getShort", "", "name", "", "hasKey", "", "setInteger", "", "key", "value", "", "setString", "setTag", "tag", "Lnet/ccbluex/liquidbounce/api/minecraft/nbt/INBTBase;", "LiquidBounce"}
)
public final class NBTTagCompoundImpl extends NBTBaseImpl implements INBTTagCompound {

    public boolean hasKey(@NotNull String name) {
        Intrinsics.checkParameterIsNotNull(name, "name");
        return ((NBTTagCompound) this.getWrapped()).hasKey(name);
    }

    public short getShort(@NotNull String name) {
        Intrinsics.checkParameterIsNotNull(name, "name");
        return ((NBTTagCompound) this.getWrapped()).getShort(name);
    }

    public void setString(@NotNull String key, @NotNull String value) {
        Intrinsics.checkParameterIsNotNull(key, "key");
        Intrinsics.checkParameterIsNotNull(value, "value");
        ((NBTTagCompound) this.getWrapped()).setString(key, value);
    }

    public void setTag(@NotNull String key, @NotNull INBTBase tag) {
        Intrinsics.checkParameterIsNotNull(key, "key");
        Intrinsics.checkParameterIsNotNull(tag, "tag");
        NBTTagCompound nbttagcompound = (NBTTagCompound) this.getWrapped();
        boolean $i$f$unwrap = false;
        NBTBase nbtbase = ((NBTBaseImpl) tag).getWrapped();

        nbttagcompound.setTag(key, nbtbase);
    }

    public void setInteger(@NotNull String key, int value) {
        Intrinsics.checkParameterIsNotNull(key, "key");
        ((NBTTagCompound) this.getWrapped()).setInteger(key, value);
    }

    public NBTTagCompoundImpl(@NotNull NBTTagCompound wrapped) {
        Intrinsics.checkParameterIsNotNull(wrapped, "wrapped");
        super((NBTBase) wrapped);
    }
}
