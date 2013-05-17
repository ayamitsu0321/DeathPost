package ayamitsu.deathpost.asm;

import java.util.Arrays;

import cpw.mods.fml.common.DummyModContainer;
import cpw.mods.fml.common.ModMetadata;

public class ModContainer extends DummyModContainer {

	public ModContainer()
	{
		super(new ModMetadata());
		ModMetadata meta = this.getMetadata();
		meta.modId       = "ayamitsu.deathpostplugin";
		meta.name        = "DeathPostPlugin";
		meta.version     = "0.1.0";
		meta.authorList  = Arrays.asList("ayamitsu");
		meta.description = "";
		meta.url         = "";
		meta.credits     = "";
	}

}
