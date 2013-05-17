package ayamitsu.deathpost;

import java.io.IOException;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.packet.Packet250CustomPayload;

import ayamitsu.util.io.Configuration;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

@Mod(
	modid = "ayamitsu.deathpost",
	name = "DeathPost",
	version = "0.1.0"
)
@NetworkMod(
	clientSideRequired = true,
	serverSideRequired = true
)
public class DeathPost
{

	@Mod.Instance("ayamitsu.deathpost")
	public static DeathPost instance;

	@SidedProxy(clientSide = "ayamitsu.deathpost.client.ClientProxy", serverSide = "ayamitsu.deathpost.server.ServerProxy")
	public static Proxy proxy;

	public static String MSG_CHANNEL = "deathpost.msg";

	public static String head = "";
	public static String bottom = "";

	@Mod.PreInit
	public void preInit(FMLPreInitializationEvent event)
	{
		// TODO:

		if (event.getSide().isClient())
		{
			Configuration conf = new Configuration(event.getSuggestedConfigurationFile());

			try
			{
				conf.load();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}

			this.head = conf.getProperty("head", "").getValue();
			this.bottom = conf.getProperty("bottom", "").getValue();

			try
			{
				conf.save();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}

	@Mod.Init
	public void init(FMLInitializationEvent event)
	{
		this.proxy.load();
		NetworkRegistry.instance().registerChannel(this.proxy, MSG_CHANNEL);
		LanguageRegistry.instance().addStringLocalization("deathpost.time", "en_US", "%s times");
		LanguageRegistry.instance().addStringLocalization("deathpost.time", "ja_JP", "%s回目");
		LanguageRegistry.instance().addStringLocalization("deathpost.auth", "en_US", "Completed Authorization");
		LanguageRegistry.instance().addStringLocalization("deathpost.auth", "ja_JP", "認証完了");
	}

	/**
	 * call on EntityPlayerMP class onDeath method
	 * insert by coremods
	 */
	public static void sendDeathMsgToClient(EntityPlayerMP player, String msg)
	{
		player.playerNetServerHandler.sendPacketToPlayer(new Packet250CustomPayload("deathpost.msg", msg.getBytes()));
	}

}
