package ayamitsu.deathpost.server;

import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import cpw.mods.fml.common.network.Player;
import ayamitsu.deathpost.Proxy;

public class ServerProxy extends Proxy
{

	@Override
	public void load() {}

	@Override
	public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player) {}

}
