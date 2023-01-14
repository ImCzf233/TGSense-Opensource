package net.ccbluex.liquidbounce.script.remapper.injection.transformers;

import java.util.function.Consumer;
import net.ccbluex.liquidbounce.script.remapper.injection.utils.ClassUtils;
import net.ccbluex.liquidbounce.script.remapper.injection.utils.NodeUtils;
import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

public class AbstractJavaLinkerTransformer implements IClassTransformer {

    public byte[] transform(String name, String transformedName, byte[] basicClass) {
        if (name.equals("jdk.internal.dynalink.beans.AbstractJavaLinker")) {
            try {
                ClassNode throwable = ClassUtils.INSTANCE.toClassNode(basicClass);

                throwable.methods.forEach((methodNode) -> {
                    String s = methodNode.name + methodNode.desc;
                    byte b0 = -1;

                    switch (s.hashCode()) {
                    case -2098129779:
                        if (s.equals("addMember(Ljava/lang/String;Ljava/lang/reflect/AccessibleObject;Ljava/util/Map;)V")) {
                            b0 = 0;
                        }
                        break;

                    case -218897209:
                        if (s.equals("setPropertyGetter(Ljava/lang/String;Ljdk/internal/dynalink/beans/SingleDynamicMethod;Ljdk/internal/dynalink/beans/GuardedInvocationComponent$ValidationType;)V")) {
                            b0 = 2;
                        }
                        break;

                    case 1744451451:
                        if (s.equals("addMember(Ljava/lang/String;Ljdk/internal/dynalink/beans/SingleDynamicMethod;Ljava/util/Map;)V")) {
                            b0 = 1;
                        }
                    }

                    switch (b0) {
                    case 0:
                        methodNode.instructions.insertBefore(methodNode.instructions.getFirst(), NodeUtils.INSTANCE.toNodes(new AbstractInsnNode[] { new VarInsnNode(25, 0), new FieldInsnNode(180, "jdk/internal/dynalink/beans/AbstractJavaLinker", "clazz", "Ljava/lang/Class;"), new VarInsnNode(25, 1), new VarInsnNode(25, 2), new MethodInsnNode(184, "net/ccbluex/liquidbounce/script/remapper/injection/transformers/handlers/AbstractJavaLinkerHandler", "addMember", "(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/reflect/AccessibleObject;)Ljava/lang/String;", false), new VarInsnNode(58, 1)}));
                        break;

                    case 1:
                        methodNode.instructions.insertBefore(methodNode.instructions.getFirst(), NodeUtils.INSTANCE.toNodes(new AbstractInsnNode[] { new VarInsnNode(25, 0), new FieldInsnNode(180, "jdk/internal/dynalink/beans/AbstractJavaLinker", "clazz", "Ljava/lang/Class;"), new VarInsnNode(25, 1), new MethodInsnNode(184, "net/ccbluex/liquidbounce/script/remapper/injection/transformers/handlers/AbstractJavaLinkerHandler", "addMember", "(Ljava/lang/Class;Ljava/lang/String;)Ljava/lang/String;", false), new VarInsnNode(58, 1)}));
                        break;

                    case 2:
                        methodNode.instructions.insertBefore(methodNode.instructions.getFirst(), NodeUtils.INSTANCE.toNodes(new AbstractInsnNode[] { new VarInsnNode(25, 0), new FieldInsnNode(180, "jdk/internal/dynalink/beans/AbstractJavaLinker", "clazz", "Ljava/lang/Class;"), new VarInsnNode(25, 1), new MethodInsnNode(184, "net/ccbluex/liquidbounce/script/remapper/injection/transformers/handlers/AbstractJavaLinkerHandler", "setPropertyGetter", "(Ljava/lang/Class;Ljava/lang/String;)Ljava/lang/String;", false), new VarInsnNode(58, 1)}));
                    }

                });
                return ClassUtils.INSTANCE.toBytes(throwable);
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }

        return basicClass;
    }
}
