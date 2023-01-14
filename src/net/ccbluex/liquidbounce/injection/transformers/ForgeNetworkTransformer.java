package net.ccbluex.liquidbounce.injection.transformers;

import java.util.function.Consumer;
import java.util.function.Predicate;
import net.ccbluex.liquidbounce.features.special.AntiForge;
import net.ccbluex.liquidbounce.script.remapper.injection.utils.ClassUtils;
import net.ccbluex.liquidbounce.script.remapper.injection.utils.NodeUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

public class ForgeNetworkTransformer implements IClassTransformer {

    public static boolean returnMethod() {
        return AntiForge.enabled && AntiForge.blockFML && !Minecraft.getMinecraft().isIntegratedServerRunning();
    }

    public byte[] transform(String name, String transformedName, byte[] basicClass) {
        ClassNode throwable;

        if (name.equals("net.minecraftforge.fml.common.network.handshake.NetworkDispatcher")) {
            try {
                throwable = ClassUtils.INSTANCE.toClassNode(basicClass);
                throwable.methods.stream().filter((methodNode) -> {
                    return methodNode.name.equals("handleVanilla");
                }).forEach((methodNode) -> {
                    LabelNode labelNode = new LabelNode();

                    methodNode.instructions.insertBefore(methodNode.instructions.getFirst(), NodeUtils.INSTANCE.toNodes(new AbstractInsnNode[] { new MethodInsnNode(184, "net/ccbluex/liquidbounce/injection/transformers/ForgeNetworkTransformer", "returnMethod", "()Z", false), new JumpInsnNode(153, labelNode), new InsnNode(3), new InsnNode(172), labelNode}));
                });
                return ClassUtils.INSTANCE.toBytes(throwable);
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }

        if (name.equals("net.minecraftforge.fml.common.network.handshake.HandshakeMessageHandler")) {
            try {
                throwable = ClassUtils.INSTANCE.toClassNode(basicClass);
                throwable.methods.stream().filter((method) -> {
                    return method.name.equals("channelRead0");
                }).forEach((methodNode) -> {
                    LabelNode labelNode = new LabelNode();

                    methodNode.instructions.insertBefore(methodNode.instructions.getFirst(), NodeUtils.INSTANCE.toNodes(new AbstractInsnNode[] { new MethodInsnNode(184, "net/ccbluex/liquidbounce/injection/transformers/ForgeNetworkTransformer", "returnMethod", "()Z", false), new JumpInsnNode(153, labelNode), new InsnNode(177), labelNode}));
                });
                return ClassUtils.INSTANCE.toBytes(throwable);
            } catch (Throwable throwable1) {
                throwable1.printStackTrace();
            }
        }

        return basicClass;
    }
}
