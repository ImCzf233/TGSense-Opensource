package net.ccbluex.liquidbounce.api.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMutableList;
import kotlin.jvm.internal.markers.KMutableListIterator;
import org.jetbrains.annotations.NotNull;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000@\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u001e\n\u0002\b\u0006\n\u0002\u0010+\n\u0002\b\b\u0018\u0000*\u0004\b\u0000\u0010\u0001*\u0004\b\u0001\u0010\u0002*\u000e\b\u0002\u0010\u0003*\b\u0012\u0004\u0012\u0002H\u00010\u00042\u0014\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00052\b\u0012\u0004\u0012\u0002H\u00020\u0004:\u0001\"B5\u0012\u0006\u0010\u0006\u001a\u00028\u0002\u0012\u0012\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u00028\u00000\b\u0012\u0012\u0010\t\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\b¢\u0006\u0002\u0010\nJ\u001d\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010\u0010J\u001e\u0010\u0011\u001a\u00020\u00122\u0006\u0010\r\u001a\u00020\u000e2\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00028\u00010\u0014H\u0016J\u0016\u0010\u0015\u001a\u00028\u00012\u0006\u0010\r\u001a\u00020\u000eH\u0096\u0002¢\u0006\u0002\u0010\u0016J\u0015\u0010\u0017\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010\u0018J\u0015\u0010\u0019\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010\u0018J\u000e\u0010\u001a\u001a\b\u0012\u0004\u0012\u00028\u00010\u001bH\u0016J\u0016\u0010\u001a\u001a\b\u0012\u0004\u0012\u00028\u00010\u001b2\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0015\u0010\u001c\u001a\u00028\u00012\u0006\u0010\r\u001a\u00020\u000eH\u0016¢\u0006\u0002\u0010\u0016J\u001e\u0010\u001d\u001a\u00028\u00012\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00028\u0001H\u0096\u0002¢\u0006\u0002\u0010\u001eJ\u001e\u0010\u001f\u001a\b\u0012\u0004\u0012\u00028\u00010\u00042\u0006\u0010 \u001a\u00020\u000e2\u0006\u0010!\u001a\u00020\u000eH\u0016¨\u0006#"},
    d2 = { "Lnet/ccbluex/liquidbounce/api/util/WrappedMutableList;", "O", "T", "C", "", "Lnet/ccbluex/liquidbounce/api/util/WrappedMutableCollection;", "wrapped", "unwrapper", "Lkotlin/Function1;", "wrapper", "(Ljava/util/List;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;)V", "add", "", "index", "", "element", "(ILjava/lang/Object;)V", "addAll", "", "elements", "", "get", "(I)Ljava/lang/Object;", "indexOf", "(Ljava/lang/Object;)I", "lastIndexOf", "listIterator", "", "removeAt", "set", "(ILjava/lang/Object;)Ljava/lang/Object;", "subList", "fromIndex", "toIndex", "WrappedMutableCollectionIterator", "LiquidBounce"}
)
public final class WrappedMutableList extends WrappedMutableCollection implements List, KMutableList {

    public Object get(int index) {
        return this.getWrapper().invoke(((List) this.getWrapped()).get(index));
    }

    public int indexOf(Object element) {
        return ((List) this.getWrapped()).indexOf(this.getUnwrapper().invoke(element));
    }

    public int lastIndexOf(Object element) {
        return ((List) this.getWrapped()).lastIndexOf(this.getUnwrapper().invoke(element));
    }

    public void add(int index, Object element) {
        ((List) this.getWrapped()).add(index, this.getUnwrapper().invoke(element));
    }

    public boolean addAll(int index, @NotNull Collection elements) {
        Intrinsics.checkParameterIsNotNull(elements, "elements");
        List list = (List) this.getWrapped();
        Iterable $this$map$iv = (Iterable) elements;
        Function1 transform$iv = this.getUnwrapper();
        List list1 = list;
        boolean $i$f$map = false;
        Collection destination$iv$iv = (Collection) (new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10)));
        boolean $i$f$mapTo = false;
        Iterator iterator = $this$map$iv.iterator();

        while (iterator.hasNext()) {
            Object item$iv$iv = iterator.next();

            destination$iv$iv.add(transform$iv.invoke(item$iv$iv));
        }

        List list2 = (List) destination$iv$iv;

        return list1.addAll(index, (Collection) list2);
    }

    @NotNull
    public ListIterator listIterator() {
        return (ListIterator) (new WrappedMutableList.WrappedMutableCollectionIterator(((List) this.getWrapped()).listIterator(), this.getWrapper(), this.getUnwrapper()));
    }

    @NotNull
    public ListIterator listIterator(int index) {
        return (ListIterator) (new WrappedMutableList.WrappedMutableCollectionIterator(((List) this.getWrapped()).listIterator(index), this.getWrapper(), this.getUnwrapper()));
    }

    public Object removeAt(int index) {
        return this.getWrapper().invoke(((List) this.getWrapped()).remove(index));
    }

    public Object set(int index, Object element) {
        return this.getWrapper().invoke(((List) this.getWrapped()).set(index, this.getUnwrapper().invoke(element)));
    }

    @NotNull
    public List subList(int fromIndex, int toIndex) {
        return (List) (new WrappedMutableList(((List) this.getWrapped()).subList(fromIndex, toIndex), this.getUnwrapper(), this.getWrapper()));
    }

    public WrappedMutableList(@NotNull List wrapped, @NotNull Function1 unwrapper, @NotNull Function1 wrapper) {
        Intrinsics.checkParameterIsNotNull(wrapped, "wrapped");
        Intrinsics.checkParameterIsNotNull(unwrapper, "unwrapper");
        Intrinsics.checkParameterIsNotNull(wrapper, "wrapper");
        super((Collection) wrapped, unwrapper, wrapper);
    }

    @Metadata(
        mv = { 1, 1, 16},
        bv = { 1, 0, 3},
        k = 1,
        d1 = { "\u00000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010+\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0005\u0018\u0000*\u0004\b\u0003\u0010\u0001*\u0004\b\u0004\u0010\u00022\b\u0012\u0004\u0012\u0002H\u00020\u0003B;\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00030\u0003\u0012\u0012\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00028\u0003\u0012\u0004\u0012\u00028\u00040\u0006\u0012\u0012\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00028\u0004\u0012\u0004\u0012\u00028\u00030\u0006¢\u0006\u0002\u0010\bJ\u0015\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00028\u0004H\u0016¢\u0006\u0002\u0010\u0011J\t\u0010\u0012\u001a\u00020\u0013H\u0096\u0002J\b\u0010\u0014\u001a\u00020\u0013H\u0016J\u000e\u0010\u0015\u001a\u00028\u0004H\u0096\u0002¢\u0006\u0002\u0010\u0016J\b\u0010\u0017\u001a\u00020\u0018H\u0016J\r\u0010\u0019\u001a\u00028\u0004H\u0016¢\u0006\u0002\u0010\u0016J\b\u0010\u001a\u001a\u00020\u0018H\u0016J\b\u0010\u001b\u001a\u00020\u000fH\u0016J\u0015\u0010\u001c\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00028\u0004H\u0016¢\u0006\u0002\u0010\u0011R\u001d\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00028\u0004\u0012\u0004\u0012\u00028\u00030\u0006¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00030\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u001d\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00028\u0003\u0012\u0004\u0012\u00028\u00040\u0006¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\n¨\u0006\u001d"},
        d2 = { "Lnet/ccbluex/liquidbounce/api/util/WrappedMutableList$WrappedMutableCollectionIterator;", "O", "T", "", "wrapped", "wrapper", "Lkotlin/Function1;", "unwrapper", "(Ljava/util/ListIterator;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;)V", "getUnwrapper", "()Lkotlin/jvm/functions/Function1;", "getWrapped", "()Ljava/util/ListIterator;", "getWrapper", "add", "", "element", "(Ljava/lang/Object;)V", "hasNext", "", "hasPrevious", "next", "()Ljava/lang/Object;", "nextIndex", "", "previous", "previousIndex", "remove", "set", "LiquidBounce"}
    )
    public static final class WrappedMutableCollectionIterator implements ListIterator, KMutableListIterator {

        @NotNull
        private final ListIterator wrapped;
        @NotNull
        private final Function1 wrapper;
        @NotNull
        private final Function1 unwrapper;

        public boolean hasNext() {
            return this.wrapped.hasNext();
        }

        public boolean hasPrevious() {
            return this.wrapped.hasPrevious();
        }

        public Object next() {
            return this.wrapper.invoke(this.wrapped.next());
        }

        public int nextIndex() {
            return this.wrapped.nextIndex();
        }

        public Object previous() {
            return this.wrapper.invoke(this.wrapped.previous());
        }

        public int previousIndex() {
            return this.wrapped.previousIndex();
        }

        public void add(Object element) {
            this.wrapped.add(this.unwrapper.invoke(element));
        }

        public void remove() {
            this.wrapped.remove();
        }

        public void set(Object element) {
            this.wrapped.set(this.unwrapper.invoke(element));
        }

        @NotNull
        public final ListIterator getWrapped() {
            return this.wrapped;
        }

        @NotNull
        public final Function1 getWrapper() {
            return this.wrapper;
        }

        @NotNull
        public final Function1 getUnwrapper() {
            return this.unwrapper;
        }

        public WrappedMutableCollectionIterator(@NotNull ListIterator wrapped, @NotNull Function1 wrapper, @NotNull Function1 unwrapper) {
            Intrinsics.checkParameterIsNotNull(wrapped, "wrapped");
            Intrinsics.checkParameterIsNotNull(wrapper, "wrapper");
            Intrinsics.checkParameterIsNotNull(unwrapper, "unwrapper");
            super();
            this.wrapped = wrapped;
            this.wrapper = wrapper;
            this.unwrapper = unwrapper;
        }
    }
}
