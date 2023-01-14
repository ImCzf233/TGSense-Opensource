package net.ccbluex.liquidbounce.ui.font;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.opengl.GL11;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0012\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\t\u0010\u0013\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0014\u001a\u00020\u0005HÆ\u0003J\t\u0010\u0015\u001a\u00020\u0007HÆ\u0003J\'\u0010\u0016\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u0007HÆ\u0001J\u0013\u0010\u0017\u001a\u00020\u00072\b\u0010\u0018\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\b\u0010\u0019\u001a\u00020\u001aH\u0004J\t\u0010\u001b\u001a\u00020\u0003HÖ\u0001J\t\u0010\u001c\u001a\u00020\u001dHÖ\u0001R\u001a\u0010\u0006\u001a\u00020\u0007X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012¨\u0006\u001e"},
    d2 = { "Lnet/ccbluex/liquidbounce/ui/font/CachedFont;", "", "displayList", "", "lastUsage", "", "deleted", "", "(IJZ)V", "getDeleted", "()Z", "setDeleted", "(Z)V", "getDisplayList", "()I", "getLastUsage", "()J", "setLastUsage", "(J)V", "component1", "component2", "component3", "copy", "equals", "other", "finalize", "", "hashCode", "toString", "", "LiquidBounce"}
)
public final class CachedFont {

    private final int displayList;
    private long lastUsage;
    private boolean deleted;

    protected final void finalize() {
        if (!this.deleted) {
            GL11.glDeleteLists(this.displayList, 1);
        }

    }

    public final int getDisplayList() {
        return this.displayList;
    }

    public final long getLastUsage() {
        return this.lastUsage;
    }

    public final void setLastUsage(long <set-?>) {
        this.lastUsage = <set-?>;
    }

    public final boolean getDeleted() {
        return this.deleted;
    }

    public final void setDeleted(boolean <set-?>) {
        this.deleted = <set-?>;
    }

    public CachedFont(int displayList, long lastUsage, boolean deleted) {
        this.displayList = displayList;
        this.lastUsage = lastUsage;
        this.deleted = deleted;
    }

    public CachedFont(int i, long j, boolean flag, int k, DefaultConstructorMarker defaultconstructormarker) {
        if ((k & 4) != 0) {
            flag = false;
        }

        this(i, j, flag);
    }

    public final int component1() {
        return this.displayList;
    }

    public final long component2() {
        return this.lastUsage;
    }

    public final boolean component3() {
        return this.deleted;
    }

    @NotNull
    public final CachedFont copy(int displayList, long lastUsage, boolean deleted) {
        return new CachedFont(displayList, lastUsage, deleted);
    }

    public static CachedFont copy$default(CachedFont cachedfont, int i, long j, boolean flag, int k, Object object) {
        if ((k & 1) != 0) {
            i = cachedfont.displayList;
        }

        if ((k & 2) != 0) {
            j = cachedfont.lastUsage;
        }

        if ((k & 4) != 0) {
            flag = cachedfont.deleted;
        }

        return cachedfont.copy(i, j, flag);
    }

    @NotNull
    public String toString() {
        return "CachedFont(displayList=" + this.displayList + ", lastUsage=" + this.lastUsage + ", deleted=" + this.deleted + ")";
    }

    public int hashCode() {
        int i = (Integer.hashCode(this.displayList) * 31 + Long.hashCode(this.lastUsage)) * 31;
        byte b0 = this.deleted;

        if (this.deleted) {
            b0 = 1;
        }

        return i + b0;
    }

    public boolean equals(@Nullable Object object) {
        if (this != object) {
            if (object instanceof CachedFont) {
                CachedFont cachedfont = (CachedFont) object;

                if (this.displayList == cachedfont.displayList && this.lastUsage == cachedfont.lastUsage && this.deleted == cachedfont.deleted) {
                    return true;
                }
            }

            return false;
        } else {
            return true;
        }
    }
}
