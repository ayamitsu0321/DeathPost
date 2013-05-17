package ayamitsu.deathpost;

import cpw.mods.fml.common.network.IPacketHandler;

public abstract class Proxy implements IPacketHandler
{
	public abstract void load();
}
