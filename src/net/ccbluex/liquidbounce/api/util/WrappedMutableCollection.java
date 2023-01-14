package net.ccbluex.liquidbounce.api.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMutableCollection;
import kotlin.jvm.internal.markers.KMutableIterator;
import org.jetbrains.annotations.NotNull;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u00008\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u001f\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u001e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010)\n\u0002\b\u0005\b\u0016\u0018\u0000*\u0004\b\u0000\u0010\u0001*\u0004\b\u0001\u0010\u0002*\u000e\b\u0002\u0010\u0003*\b\u0012\u0004\u0012\u0002H\u00010\u00042\u0014\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00052\b\u0012\u0004\u0012\u0002H\u00020\u0004:\u0001\u0019B5\u0012\u0006\u0010\u0006\u001a\u00028\u0002\u0012\u0012\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u00028\u00000\b\u0012\u0012\u0010\t\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\b¢\u0006\u0002\u0010\nJ\u0015\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010\u000eJ\u0016\u0010\u000f\u001a\u00020\f2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00028\u00010\u0011H\u0016J\b\u0010\u0012\u001a\u00020\u0013H\u0016J\u000f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00028\u00010\u0015H\u0096\u0002J\u0015\u0010\u0016\u001a\u00020\f2\u0006\u0010\r\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010\u000eJ\u0016\u0010\u0017\u001a\u00020\f2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00028\u00010\u0011H\u0016J\u0016\u0010\u0018\u001a\u00020\f2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00028\u00010\u0011H\u0016¨\u0006\u001a"},
    d2 = { "Lnet/ccbluex/liquidbounce/api/util/WrappedMutableCollection;", "O", "T", "C", "", "Lnet/ccbluex/liquidbounce/api/util/WrappedCollection;", "wrapped", "unwrapper", "Lkotlin/Function1;", "wrapper", "(Ljava/util/Collection;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;)V", "add", "", "element", "(Ljava/lang/Object;)Z", "addAll", "elements", "", "clear", "", "iterator", "", "remove", "removeAll", "retainAll", "WrappedCollectionIterator", "LiquidBounce"}
)
public class WrappedMutableCollection extends WrappedCollection implements Collection, KMutableCollection {

    public boolean add(Object element) {
        return this.getWrapped().add(this.getUnwrapper().invoke(element));
    }

    public boolean addAll(@NotNull Collection elements) {
        Intrinsics.checkParameterIsNotNull(elements, "elements");
        Collection collection = this.getWrapped();
        Iterable $this$map$iv = (Iterable) elements;
        Function1 transform$iv = this.getUnwrapper();
        Collection collection1 = collection;
        boolean $i$f$map = false;
        Collection destination$iv$iv = (Collection) (new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10)));
        boolean $i$f$mapTo = false;
        Iterator iterator = $this$map$iv.iterator();

        while (iterator.hasNext()) {
            Object item$iv$iv = iterator.next();

            destination$iv$iv.add(transform$iv.invoke(item$iv$iv));
        }

        List list = (List) destination$iv$iv;

        return collection1.addAll((Collection) list);
    }

    public void clear() {
        this.getWrapped().clear();
    }

    @NotNull
    public Iterator iterator() {
        return (Iterator) (new WrappedMutableCollection.WrappedCollectionIterator(this.getWrapped().iterator(), this.getWrapper()));
    }

    public boolean remove(Object element) {
        return this.getWrapped().remove(this.getUnwrapper().invoke(element));
    }

    public boolean removeAll(@NotNull Collection elements) {
        Intrinsics.checkParameterIsNotNull(elements, "elements");
        Collection collection = this.getWrapped();
        Iterable $this$map$iv = (Iterable) elements;
        Function1 transform$iv = this.getUnwrapper();
        Collection collection1 = collection;
        boolean $i$f$map = false;
        Collection destination$iv$iv = (Collection) (new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10)));
        boolean $i$f$mapTo = false;
        Iterator iterator = $this$map$iv.iterator();

        while (iterator.hasNext()) {
            Object item$iv$iv = iterator.next();

            destination$iv$iv.add(transform$iv.invoke(item$iv$iv));
        }

        List list = (List) destination$iv$iv;

        return collection1.addAll((Collection) list);
    }

    public boolean retainAll(@NotNull Collection elements) {
        Intrinsics.checkParameterIsNotNull(elements, "elements");
        Collection collection = this.getWrapped();
        Iterable $this$map$iv = (Iterable) elements;
        Function1 transform$iv = this.getUnwrapper();
        Collection collection1 = collection;
        boolean $i$f$map = false;
        Collection destination$iv$iv = (Collection) (new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10)));
        boolean $i$f$mapTo = false;
        Iterator iterator = $this$map$iv.iterator();

        while (iterator.hasNext()) {
            Object item$iv$iv = iterator.next();

            destination$iv$iv.add(transform$iv.invoke(item$iv$iv));
        }

        List list = (List) destination$iv$iv;

        return collection1.addAll((Collection) list);
    }

    public WrappedMutableCollection(@NotNull Collection wrapped, @NotNull Function1 unwrapper, @NotNull Function1 wrapper) {
        Intrinsics.checkParameterIsNotNull(wrapped, "wrapped");
        Intrinsics.checkParameterIsNotNull(unwrapper, "unwrapper");
        Intrinsics.checkParameterIsNotNull(wrapper, "wrapper");
        super(wrapped, unwrapper, wrapper);
    }

    @Metadata(
        mv = { 1, 1, 16},
        bv = { 1, 0, 3},
        k = 1,
        d1 = { "\u0000&\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010)\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\u0018\u0000*\u0004\b\u0003\u0010\u0001*\u0004\b\u0004\u0010\u00022\b\u0012\u0004\u0012\u0002H\u00020\u0003B\'\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00030\u0003\u0012\u0012\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00028\u0003\u0012\u0004\u0012\u00028\u00040\u0006¢\u0006\u0002\u0010\u0007J\t\u0010\f\u001a\u00020\rH\u0096\u0002J\u000e\u0010\u000e\u001a\u00028\u0004H\u0096\u0002¢\u0006\u0002\u0010\u000fJ\b\u0010\u0010\u001a\u00020\u0011H\u0016R\u001d\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00028\u0003\u0012\u0004\u0012\u00028\u00040\u0006¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00030\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\u0012"},
        d2 = { "Lnet/ccbluex/liquidbounce/api/util/WrappedMutableCollection$WrappedCollectionIterator;", "O", "T", "", "wrapped", "unwrapper", "Lkotlin/Function1;", "(Ljava/util/Iterator;Lkotlin/jvm/functions/Function1;)V", "getUnwrapper", "()Lkotlin/jvm/functions/Function1;", "getWrapped", "()Ljava/util/Iterator;", "hasNext", "", "next", "()Ljava/lang/Object;", "remove", "", "LiquidBounce"}
    )
    public static final class WrappedCollectionIterator implements Iterator, KMutableIterator {

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

        public void remove() {
            this.wrapped.remove();
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
    }
}
