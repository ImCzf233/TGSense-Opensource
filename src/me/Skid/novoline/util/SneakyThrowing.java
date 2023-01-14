package me.Skid.novoline.util;

public final class SneakyThrowing {

    public static RuntimeException sneakyThrow(Throwable throwable) {
        return (RuntimeException) sneakyThrow0(throwable);
    }

    private static Throwable sneakyThrow0(Throwable throwable) throws Throwable {
        throw throwable;
    }

    private SneakyThrowing() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}
