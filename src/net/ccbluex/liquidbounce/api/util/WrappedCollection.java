package net.ccbluex.liquidbounce.api.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.function.Predicate;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.CollectionToArray;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMappedMarker;
import org.jetbrains.annotations.NotNull;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u00000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u001e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0010(\n\u0002\b\u0002\b\u0016\u0018\u0000*\u0004\b\u0000\u0010\u0001*\u0004\b\u0001\u0010\u0002*\u000e\b\u0002\u0010\u0003*\b\u0012\u0004\u0012\u0002H\u00010\u00042\b\u0012\u0004\u0012\u0002H\u00020\u0004:\u0001\u001dB5\u0012\u0006\u0010\u0005\u001a\u00028\u0002\u0012\u0012\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u00028\u00000\u0007\u0012\u0012\u0010\b\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u0007¢\u0006\u0002\u0010\tJ\u0016\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00028\u0001H\u0096\u0002¢\u0006\u0002\u0010\u0017J\u0016\u0010\u0018\u001a\u00020\u00152\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00028\u00010\u0004H\u0016J\b\u0010\u001a\u001a\u00020\u0015H\u0016J\u000f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00028\u00010\u001cH\u0096\u0002R\u0014\u0010\n\u001a\u00020\u000b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\rR\u001d\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u00028\u00000\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0013\u0010\u0005\u001a\u00028\u0002¢\u0006\n\n\u0002\u0010\u0012\u001a\u0004\b\u0010\u0010\u0011R\u001d\u0010\b\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u000f¨\u0006\u001e"},
    d2 = { "Lnet/ccbluex/liquidbounce/api/util/WrappedCollection;", "O", "T", "C", "", "wrapped", "unwrapper", "Lkotlin/Function1;", "wrapper", "(Ljava/util/Collection;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;)V", "size", "", "getSize", "()I", "getUnwrapper", "()Lkotlin/jvm/functions/Function1;", "getWrapped", "()Ljava/util/Collection;", "Ljava/util/Collection;", "getWrapper", "contains", "", "element", "(Ljava/lang/Object;)Z", "containsAll", "elements", "isEmpty", "iterator", "", "WrappedCollectionIterator", "LiquidBounce"}
)
public class WrappedCollection implements Collection, KMappedMarker {

    @NotNull
    private final Collection wrapped;
    @NotNull
    private final Function1 unwrapper;
    @NotNull
    private final Function1 wrapper;

    public int getSize() {
        return this.wrapped.size();
    }

    public boolean contains(Object element) {
        return this.wrapped.contains(this.unwrapper.invoke(element));
    }

    public boolean containsAll(@NotNull Collection elements) {
        Intrinsics.checkParameterIsNotNull(elements, "elements");
        Iterable $this$forEach$iv = (Iterable) elements;
        boolean $i$f$forEach = false;
        Iterator iterator = $this$forEach$iv.iterator();

        Object element$iv;

        do {
            if (!iterator.hasNext()) {
                return false;
            }

            element$iv = iterator.next();
            boolean $i$a$-forEach-WrappedCollection$containsAll$1 = false;
        } while (!this.wrapped.contains(this.unwrapper.invoke(element$iv)));

        return true;
    }

    public boolean isEmpty() {
        return this.wrapped.isEmpty();
    }

    @NotNull
    public Iterator iterator() {
        return (Iterator) (new WrappedCollection.WrappedCollectionIterator(this.wrapped.iterator(), this.wrapper));
    }

    @NotNull
    public final Collection getWrapped() {
        return this.wrapped;
    }

    @NotNull
    public final Function1 getUnwrapper() {
        return this.unwrapper;
    }

    @NotNull
    public final Function1 getWrapper() {
        return this.wrapper;
    }

    public WrappedCollection(@NotNull Collection wrapped, @NotNull Function1 unwrapper, @NotNull Function1 wrapper) {
        Intrinsics.checkParameterIsNotNull(wrapped, "wrapped");
        Intrinsics.checkParameterIsNotNull(unwrapper, "unwrapper");
        Intrinsics.checkParameterIsNotNull(wrapper, "wrapper");
        super();
        this.wrapped = wrapped;
        this.unwrapper = unwrapper;
        this.wrapper = wrapper;
    }

    public boolean add(Object object) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    public boolean addAll(Collection collection) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    public void clear() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    public boolean remove(Object object) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    public boolean removeAll(Collection collection) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    public boolean removeIf(Predicate predicate) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    public boolean retainAll(Collection collection) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    public Object[] toArray() {
        return CollectionToArray.toArray(this);
    }

    public Object[] toArray(Object[] aobject) {
        return CollectionToArray.toArray(this, aobject);
    }

    @Metadata(
        mv = { 1, 1, 16},
        bv = { 1, 0, 3},
        k = 1,
        d1 = { "\u0000 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010(\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0003\u0018\u0000*\u0004\b\u0003\u0010\u0001*\u0004\b\u0004\u0010\u00022\b\u0012\u0004\u0012\u0002H\u00020\u0003B\'\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00030\u0003\u0012\u0012\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00028\u0003\u0012\u0004\u0012\u00028\u00040\u0006¢\u0006\u0002\u0010\u0007J\t\u0010\f\u001a\u00020\rH\u0096\u0002J\u000e\u0010\u000e\u001a\u00028\u0004H\u0096\u0002¢\u0006\u0002\u0010\u000fR\u001d\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00028\u0003\u0012\u0004\u0012\u00028\u00040\u0006¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00030\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\u0010"},
        d2 = { "Lnet/ccbluex/liquidbounce/api/util/WrappedCollection$WrappedCollectionIterator;", "O", "T", "", "wrapped", "unwrapper", "Lkotlin/Function1;", "(Ljava/util/Iterator;Lkotlin/jvm/functions/Function1;)V", "getUnwrapper", "()Lkotlin/jvm/functions/Function1;", "getWrapped", "()Ljava/util/Iterator;", "hasNext", "", "next", "()Ljava/lang/Object;", "LiquidBounce"}
    )
    public static final class WrappedCollectionIterator implements Iterator, KMappedMarker {

        @NotNull
        private final Iterator wrapped;
        @NotNull
        private final Function1 unwrapper;

        public boolean hasNext() {
            return this.wrapped.hasNext();
        }

        public Object next() {
            return this.unwrapper.invoke(this.wrapped.next());
        }

        @NotNull
        public final Iterator getWrapped() {
            return this.wrapped;
        }

        @NotNull
        public final Function1 getUnwrapper() {
            return this.unwrapper;
        }

        public WrappedCollectionIterator(@NotNull Iterator wrapped, @NotNull Function1 unwrapper) {
            Intrinsics.checkParameterIsNotNull(wrapped, "wrapped");
            Intrinsics.checkParameterIsNotNull(unwrapper, "unwrapper");
            super();
            this.wrapped = wrapped;
            this.unwrapper = unwrapper;
        }

        public void remove() {
            throw new UnsupportedOperationException("Operation is not supported for read-only collection");
        }
    }
}
