package net.ccbluex.liquidbounce.utils.render;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.api.minecraft.block.state.IIBlockState;
import net.ccbluex.liquidbounce.api.minecraft.client.block.IBlock;
import net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IWorldClient;
import net.ccbluex.liquidbounce.api.minecraft.util.WBlockPos;
import net.ccbluex.liquidbounce.api.minecraft.world.IChunk;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.minecraft.client.renderer.texture.DynamicTexture;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0007\bÆ\u0002\u0018\u00002\u00020\u0001:\u0002\u001a\u001bB\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0018\u0010\u000f\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0011J\u0006\u0010\u0013\u001a\u00020\u0011J\u0006\u0010\u0014\u001a\u00020\u0015J\u0016\u0010\u0016\u001a\u00020\u00152\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0011J\u000e\u0010\u0017\u001a\u00020\u00152\u0006\u0010\u0018\u001a\u00020\u000eJ\u0006\u0010\u0019\u001a\u00020\u0015R*\u0010\u0003\u001a\u001e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004j\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0006`\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u0010\n\u001a\u0012\u0012\u0004\u0012\u00020\u00050\u000bj\b\u0012\u0004\u0012\u00020\u0005`\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u0010\r\u001a\u0012\u0012\u0004\u0012\u00020\u000e0\u000bj\b\u0012\u0004\u0012\u00020\u000e`\fX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001c"},
    d2 = { "Lnet/ccbluex/liquidbounce/utils/render/MiniMapRegister;", "Lnet/ccbluex/liquidbounce/utils/MinecraftInstance;", "()V", "chunkTextureMap", "Ljava/util/HashMap;", "Lnet/ccbluex/liquidbounce/utils/render/MiniMapRegister$ChunkLocation;", "Lnet/ccbluex/liquidbounce/utils/render/MiniMapRegister$MiniMapTexture;", "Lkotlin/collections/HashMap;", "deleteAllChunks", "Ljava/util/concurrent/atomic/AtomicBoolean;", "queuedChunkDeletions", "Ljava/util/HashSet;", "Lkotlin/collections/HashSet;", "queuedChunkUpdates", "Lnet/ccbluex/liquidbounce/api/minecraft/world/IChunk;", "getChunkTextureAt", "x", "", "z", "getLoadedChunkCount", "unloadAllChunks", "", "unloadChunk", "updateChunk", "chunk", "updateChunks", "ChunkLocation", "MiniMapTexture", "LiquidBounce"}
)
public final class MiniMapRegister extends MinecraftInstance {

    private static final HashMap chunkTextureMap;
    private static final HashSet queuedChunkUpdates;
    private static final HashSet queuedChunkDeletions;
    private static final AtomicBoolean deleteAllChunks;
    public static final MiniMapRegister INSTANCE;

    public final void updateChunk(@NotNull IChunk chunk) {
        Intrinsics.checkParameterIsNotNull(chunk, "chunk");
        HashSet hashset = MiniMapRegister.queuedChunkUpdates;
        boolean flag = false;
        boolean flag1 = false;

        synchronized (hashset){}

        try {
            boolean $i$a$-synchronized-MiniMapRegister$updateChunk$1 = false;

            flag1 = MiniMapRegister.queuedChunkUpdates.add(chunk);
        } finally {
            ;
        }

    }

    @Nullable
    public final MiniMapRegister.MiniMapTexture getChunkTextureAt(int x, int z) {
        return (MiniMapRegister.MiniMapTexture) MiniMapRegister.chunkTextureMap.get(new MiniMapRegister.ChunkLocation(x, z));
    }

    public final void updateChunks() {
        HashSet hashset = MiniMapRegister.queuedChunkUpdates;
        boolean flag = false;
        boolean flag1 = false;

        synchronized (hashset) {
            boolean $i$a$-synchronized-MiniMapRegister$updateChunks$1 = false;
            HashSet $this$forEach$iv;
            boolean $i$f$forEach;
            boolean flag2;
            boolean element$iv;
            Unit unit;
            boolean $i$a$-forEach-MiniMapRegister$updateChunks$1$41;

            if (MiniMapRegister.deleteAllChunks.get()) {
                $this$forEach$iv = MiniMapRegister.queuedChunkDeletions;
                $i$f$forEach = false;
                flag2 = false;

                synchronized ($this$forEach$iv){}

                try {
                    element$iv = false;
                    MiniMapRegister.queuedChunkDeletions.clear();
                    unit = Unit.INSTANCE;
                } finally {
                    ;
                }

                MiniMapRegister.queuedChunkUpdates.clear();
                Map $this$forEach$iv1 = (Map) MiniMapRegister.chunkTextureMap;

                $i$f$forEach = false;
                element$iv = false;
                Iterator it = $this$forEach$iv1.entrySet().iterator();

                while (it.hasNext()) {
                    Entry $i$a$-forEach-MiniMapRegister$updateChunks$1$4 = (Entry) it.next();
                    boolean element$iv1 = false;

                    ((MiniMapRegister.MiniMapTexture) $i$a$-forEach-MiniMapRegister$updateChunks$1$4.getValue()).delete$LiquidBounce();
                }

                MiniMapRegister.chunkTextureMap.clear();
                MiniMapRegister.deleteAllChunks.set(false);
            } else {
                $this$forEach$iv = MiniMapRegister.queuedChunkDeletions;
                $i$f$forEach = false;
                flag2 = false;

                synchronized ($this$forEach$iv){}

                try {
                    element$iv = false;
                    Iterable it3 = (Iterable) MiniMapRegister.queuedChunkDeletions;

                    $i$a$-forEach-MiniMapRegister$updateChunks$1$41 = false;
                    Iterator it1 = it3.iterator();

                    while (it1.hasNext()) {
                        Object element$iv3 = it1.next();
                        MiniMapRegister.ChunkLocation it2 = (MiniMapRegister.ChunkLocation) element$iv3;
                        boolean $i$a$-forEach-MiniMapRegister$updateChunks$1$3$1 = false;
                        MiniMapRegister.MiniMapTexture minimapregister_minimaptexture = (MiniMapRegister.MiniMapTexture) MiniMapRegister.chunkTextureMap.remove(it2);

                        if (minimapregister_minimaptexture != null) {
                            minimapregister_minimaptexture.delete$LiquidBounce();
                        }
                    }

                    MiniMapRegister.queuedChunkDeletions.clear();
                    unit = Unit.INSTANCE;
                } finally {
                    ;
                }
            }

            Iterable $this$forEach$iv2 = (Iterable) MiniMapRegister.queuedChunkUpdates;

            $i$f$forEach = false;
            Iterator iterator = $this$forEach$iv2.iterator();

            while (iterator.hasNext()) {
                Object element$iv2 = iterator.next();
                IChunk it4 = (IChunk) element$iv2;

                $i$a$-forEach-MiniMapRegister$updateChunks$1$41 = false;
                ((MiniMapRegister.MiniMapTexture) MiniMapRegister.chunkTextureMap.computeIfAbsent(new MiniMapRegister.ChunkLocation(it4.getX(), it4.getZ()), (Function) MiniMapRegister$updateChunks$1$4$1.INSTANCE)).updateChunkData(it4);
            }

            MiniMapRegister.queuedChunkUpdates.clear();
            Unit unit1 = Unit.INSTANCE;
        }
    }

    public final int getLoadedChunkCount() {
        return MiniMapRegister.chunkTextureMap.size();
    }

    public final void unloadChunk(int x, int z) {
        HashSet hashset = MiniMapRegister.queuedChunkDeletions;
        boolean flag = false;
        boolean flag1 = false;

        synchronized (hashset){}

        try {
            boolean $i$a$-synchronized-MiniMapRegister$unloadChunk$1 = false;

            flag1 = MiniMapRegister.queuedChunkDeletions.add(new MiniMapRegister.ChunkLocation(x, z));
        } finally {
            ;
        }

    }

    public final void unloadAllChunks() {
        MiniMapRegister.deleteAllChunks.set(true);
    }

    static {
        MiniMapRegister minimapregister = new MiniMapRegister();

        INSTANCE = minimapregister;
        chunkTextureMap = new HashMap();
        queuedChunkUpdates = new HashSet(256);
        queuedChunkDeletions = new HashSet(256);
        deleteAllChunks = new AtomicBoolean(false);
    }

    @Metadata(
        mv = { 1, 1, 16},
        bv = { 1, 0, 3},
        k = 1,
        d1 = { "\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\r\u0010\r\u001a\u00020\u000eH\u0000¢\u0006\u0002\b\u000fJ\b\u0010\u0010\u001a\u00020\u000eH\u0004J\u000e\u0010\u0011\u001a\u00020\u000e2\u0006\u0010\u0012\u001a\u00020\u0013R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u0011\u0010\t\u001a\u00020\n¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f¨\u0006\u0014"},
        d2 = { "Lnet/ccbluex/liquidbounce/utils/render/MiniMapRegister$MiniMapTexture;", "", "()V", "deleted", "", "getDeleted", "()Z", "setDeleted", "(Z)V", "texture", "Lnet/minecraft/client/renderer/texture/DynamicTexture;", "getTexture", "()Lnet/minecraft/client/renderer/texture/DynamicTexture;", "delete", "", "delete$LiquidBounce", "finalize", "updateChunkData", "chunk", "Lnet/ccbluex/liquidbounce/api/minecraft/world/IChunk;", "LiquidBounce"}
    )
    public static final class MiniMapTexture {

        @NotNull
        private final DynamicTexture texture = new DynamicTexture(16, 16);
        private boolean deleted;

        @NotNull
        public final DynamicTexture getTexture() {
            return this.texture;
        }

        public final boolean getDeleted() {
            return this.deleted;
        }

        public final void setDeleted(boolean <set-?>) {
            this.deleted = <set-?>;
        }

        public final void updateChunkData(@NotNull IChunk chunk) {
            Intrinsics.checkParameterIsNotNull(chunk, "chunk");
            int[] rgbValues = this.texture.getTextureData();
            int x = 0;

            for (byte b0 = 15; x <= b0; ++x) {
                int z = 0;

                for (byte b1 = 15; z <= b1; ++z) {
                    WBlockPos bp = new WBlockPos(x, chunk.getHeightValue(x, z) - 1, z);
                    IIBlockState blockState = chunk.getBlockState(bp);
                    int i = rgbValues.length - (z * 16 + x + 1);
                    IBlock iblock = blockState.getBlock();
                    IWorldClient iworldclient = MinecraftInstance.mc.getTheWorld();

                    if (iworldclient == null) {
                        Intrinsics.throwNpe();
                    }

                    rgbValues[i] = iblock.getMapColor(blockState, iworldclient, bp) | -16777216;
                }
            }

            this.texture.updateDynamicTexture();
        }

        public final void delete$LiquidBounce() {
            if (!this.deleted) {
                this.texture.deleteGlTexture();
                this.deleted = true;
            }

        }

        protected final void finalize() {
            if (!this.deleted) {
                this.texture.deleteGlTexture();
            }

        }
    }

    @Metadata(
        mv = { 1, 1, 16},
        bv = { 1, 0, 3},
        k = 1,
        d1 = { "\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005J\t\u0010\t\u001a\u00020\u0003HÆ\u0003J\t\u0010\n\u001a\u00020\u0003HÆ\u0003J\u001d\u0010\u000b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u000f\u001a\u00020\u0003HÖ\u0001J\t\u0010\u0010\u001a\u00020\u0011HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0007¨\u0006\u0012"},
        d2 = { "Lnet/ccbluex/liquidbounce/utils/render/MiniMapRegister$ChunkLocation;", "", "x", "", "z", "(II)V", "getX", "()I", "getZ", "component1", "component2", "copy", "equals", "", "other", "hashCode", "toString", "", "LiquidBounce"}
    )
    public static final class ChunkLocation {

        private final int x;
        private final int z;

        public final int getX() {
            return this.x;
        }

        public final int getZ() {
            return this.z;
        }

        public ChunkLocation(int x, int z) {
            this.x = x;
            this.z = z;
        }

        public final int component1() {
            return this.x;
        }

        public final int component2() {
            return this.z;
        }

        @NotNull
        public final MiniMapRegister.ChunkLocation copy(int x, int z) {
            return new MiniMapRegister.ChunkLocation(x, z);
        }

        public static MiniMapRegister.ChunkLocation copy$default(MiniMapRegister.ChunkLocation minimapregister_chunklocation, int i, int j, int k, Object object) {
            if ((k & 1) != 0) {
                i = minimapregister_chunklocation.x;
            }

            if ((k & 2) != 0) {
                j = minimapregister_chunklocation.z;
            }

            return minimapregister_chunklocation.copy(i, j);
        }

        @NotNull
        public String toString() {
            return "ChunkLocation(x=" + this.x + ", z=" + this.z + ")";
        }

        public int hashCode() {
            return Integer.hashCode(this.x) * 31 + Integer.hashCode(this.z);
        }

        public boolean equals(@Nullable Object object) {
            if (this != object) {
                if (object instanceof MiniMapRegister.ChunkLocation) {
                    MiniMapRegister.ChunkLocation minimapregister_chunklocation = (MiniMapRegister.ChunkLocation) object;

                    if (this.x == minimapregister_chunklocation.x && this.z == minimapregister_chunklocation.z) {
                        return true;
                    }
                }

                return false;
            } else {
                return true;
            }
        }
    }
}
