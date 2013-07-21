package ayamitsu.deathpost.client;

import java.util.EnumSet;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.settings.KeyBinding;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.util.ChatMessageComponent;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.KeyBindingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.TickType;
import cpw.mods.fml.common.network.Player;
import ayamitsu.deathpost.DeathPost;
import ayamitsu.deathpost.Proxy;

public class ClientProxy extends Proxy
{

	private Auth auth;

	@Override
	public void load()
	{
		KeyBindingRegistry.registerKeyBinding(new KeyHandler());
		this.auth = new Auth();
	}

	@Override
	public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player)
	{
		if (DeathPost.MSG_CHANNEL == packet.channel)
		{
			this.auth.post(ChatMessageComponent.func_111078_c(new String(packet.data)).func_111068_a(true), DeathPost.head, DeathPost.bottom);
		}
	}

	public Auth getAuth()
	{
		return this.auth;
	}

	private static class KeyHandler extends KeyBindingRegistry.KeyHandler
	{

		public KeyHandler()
		{
			super(new KeyBinding[] { new KeyBinding("Auth", Keyboard.KEY_K) }, new boolean[] { false });
		}

		@Override
		public String getLabel()
		{
			return "DeathPost.Auth";
		}

		@Override
		public void keyDown(EnumSet<TickType> types, KeyBinding kb, boolean tickEnd, boolean isRepeat)
		{
			System.out.println("key:" + kb.keyDescription);

			if (tickEnd && this.canInputKey())
			{
				try
				{
					FMLClientHandler.instance().getClient().displayGuiScreen(new GuiAuthorization());
				}
				catch (NullPointerException e)
				{
					e.printStackTrace();
				}
			}
		}

		@Override
		public void keyUp(EnumSet<TickType> types, KeyBinding kb, boolean tickEnd)
		{
		}

		@Override
		public EnumSet<TickType> ticks()
		{
			return EnumSet.of(TickType.CLIENT);
		}

		private boolean canInputKey()
		{
			return FMLClientHandler.instance().getClient().thePlayer != null && FMLClientHandler.instance().getClient().theWorld != null && FMLClientHandler.instance().getClient().currentScreen == null;
		}
	}

}
