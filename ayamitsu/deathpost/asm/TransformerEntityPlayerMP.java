package ayamitsu.deathpost.asm;

import java.io.IOException;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

import cpw.mods.fml.common.asm.transformers.deobf.FMLDeobfuscatingRemapper;
import cpw.mods.fml.relauncher.IClassTransformer;

public class TransformerEntityPlayerMP implements IClassTransformer, Opcodes
{
	private static final String ENTITYPLAYERMP_CLASS_NAME = "net.minecraft.entity.player.EntityPlayerMP";

	@Override
	public byte[] transform(String name, String transformedName, byte[] bytes)
	{
		if (transformedName.equals(ENTITYPLAYERMP_CLASS_NAME))
		{
			return this.transformEntityPlayerMP(bytes);
		}

		return bytes;
	}

	private byte[] transformEntityPlayerMP(byte[] arrayOfByte)
	{
		ClassNode cNode = new ClassNode();
		ClassReader cReader = new ClassReader(arrayOfByte);
		cReader.accept(cNode, 0);

		for (MethodNode mNode : cNode.methods)
		{
			// void onDeath(DamageSource) | void func_70645_a(DamageSource)
			if ("onDeath".equals(FMLDeobfuscatingRemapper.INSTANCE.mapMethodName(cNode.name, mNode.name, mNode.desc)) && "(Lnet/minecraft/util/DamageSource;)V".equals(FMLDeobfuscatingRemapper.INSTANCE.mapMethodDesc(mNode.desc)))
			{
				InsnList insnList = new InsnList();
				// ayamitsu.deathpost.DeathPost.sendDeathMsgToClient(this, this.field_94063_bt.func_94546_b());
				insnList.add(new VarInsnNode(ALOAD, 0));// this
				insnList.add(new VarInsnNode(ALOAD, 0));// this
				insnList.add(new FieldInsnNode(GETFIELD, "net/minecraft/entity/player/EntityPlayerMP", "field_94063_bt", "Lnet/minecraft/util/CombatTracker;"));
				insnList.add(new MethodInsnNode(INVOKEVIRTUAL, "net/minecraft/util/CombatTracker", "func_94546_b", "()Ljava/lang/String;"));
				insnList.add(new MethodInsnNode(INVOKESTATIC, "ayamitsu/deathpost/DeathPost", "sendDeathMsgToClient", "(Lnet/minecraft/entity/player/EntityPlayerMP;Ljava/lang/String;)V"));
				mNode.instructions.insertBefore(mNode.instructions.getLast().getPrevious(), insnList);
			}
		}

		ClassWriter cWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS)
		{
			@Override
			public String getCommonSuperClass(String type1, String type2)
			{
				return FMLDeobfuscatingRemapper.INSTANCE.map(type1);
			}
		};
		cNode.accept(cWriter);
		arrayOfByte = cWriter.toByteArray();
		return arrayOfByte;
	}

}
