package me.fuckpcl;

import java.io.File;
import kotlin.Metadata;
import kotlin.io.FilesKt;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import me.fuckpcl.utils.WindowUtils;
import org.jetbrains.annotations.NotNull;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0004J\u001a\u0010\b\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\u0004H\u0007J\u0006\u0010\t\u001a\u00020\u0004¨\u0006\n"},
    d2 = { "Lme/fuckpcl/PCLChecker;", "", "()V", "folderCheck", "", "mcDir", "Ljava/io/File;", "deleteFolder", "fullCheck", "titleCheck", "LiquidBounce"}
)
public final class PCLChecker {

    public static final PCLChecker INSTANCE;

    @JvmOverloads
    public final boolean fullCheck(@NotNull File mcDir, boolean deleteFolder) {
        Intrinsics.checkParameterIsNotNull(mcDir, "mcDir");
        return this.titleCheck() ? true : this.folderCheck(mcDir, deleteFolder);
    }

    public static boolean fullCheck$default(PCLChecker pclchecker, File file, boolean flag, int i, Object object) {
        if ((i & 2) != 0) {
            flag = true;
        }

        return pclchecker.fullCheck(file, flag);
    }

    @JvmOverloads
    public final boolean fullCheck(@NotNull File mcDir) {
        return fullCheck$default(this, mcDir, false, 2, (Object) null);
    }

    public final boolean titleCheck() {
        boolean flag;

        if (!WindowUtils.isWindows()) {
            flag = false;
        } else {
            String targetStr = "Plain Craft Launcher";
            String[] astring = WindowUtils.getWindowNames();

            Intrinsics.checkExpressionValueIsNotNull(astring, "WindowUtils.getWindowNames()");
            String[] astring1 = astring;
            boolean flag1 = false;
            boolean flag2 = false;
            String[] astring2 = astring1;
            int i = astring1.length;
            int j = 0;

            String s;

            while (true) {
                if (j >= i) {
                    s = null;
                    break;
                }

                String s1;
                label24: {
                    s1 = astring2[j];
                    boolean $i$a$-find-PCLChecker$titleCheck$1 = false;

                    if (s1.length() < targetStr.length() * 2) {
                        Intrinsics.checkExpressionValueIsNotNull(s1, "it");
                        if (StringsKt.contains$default((CharSequence) s1, (CharSequence) targetStr, false, 2, (Object) null)) {
                            flag = true;
                            break label24;
                        }
                    }

                    flag = false;
                }

                if (flag) {
                    s = s1;
                    break;
                }

                ++j;
            }

            flag = s != null;
        }

        return flag;
    }

    public final boolean folderCheck(@NotNull File mcDir, boolean deleteFolder) {
        Intrinsics.checkParameterIsNotNull(mcDir, "mcDir");
        boolean exists = mcDir.exists();
        boolean pclDataDir = false;
        boolean mcVersionDir = false;
        String s;
        boolean flag;

        if (!exists) {
            flag = false;
            s = "Argument \"mcDir\" is not exists";
            throw (Throwable) (new IllegalArgumentException(s.toString()));
        } else {
            exists = mcDir.isDirectory();
            pclDataDir = false;
            mcVersionDir = false;
            if (!exists) {
                flag = false;
                s = "Argument \"mcDir\" should be a folder";
                throw (Throwable) (new IllegalArgumentException(s.toString()));
            } else {
                exists = false;
                File file = new File(mcDir, "PCL");

                if (file.exists()) {
                    if (deleteFolder) {
                        FilesKt.deleteRecursively(file);
                    }

                    exists = true;
                }

                File file1 = new File(mcDir, "versions");

                if (file1.exists()) {
                    File[] afile = file1.listFiles();

                    Intrinsics.checkExpressionValueIsNotNull(afile, "mcVersionDir.listFiles()");
                    File[] $this$forEach$iv = afile;
                    boolean $i$f$forEach = false;
                    File[] afile1 = $this$forEach$iv;
                    int i = $this$forEach$iv.length;

                    for (int j = 0; j < i; ++j) {
                        File element$iv = afile1[j];
                        boolean $i$a$-forEach-PCLChecker$folderCheck$3 = false;
                        File pclVersionDataDir = new File(element$iv, "PCL");

                        if (pclVersionDataDir.exists()) {
                            if (deleteFolder) {
                                FilesKt.deleteRecursively(pclVersionDataDir);
                            }

                            exists = true;
                        }
                    }
                }

                return exists;
            }
        }
    }

    static {
        PCLChecker pclchecker = new PCLChecker();

        INSTANCE = pclchecker;
    }
}
