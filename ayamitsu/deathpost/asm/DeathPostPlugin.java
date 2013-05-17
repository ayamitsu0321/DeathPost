package ayamitsu.deathpost.asm;

import java.util.Map;

import cpw.mods.fml.relauncher.IFMLLoadingPlugin;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin.TransformerExclusions;

@TransformerExclusions("ayamitsu.deathpost.asm")
public class DeathPostPlugin implements IFMLLoadingPlugin
{

	@Override
	public String[] getLibraryRequestClass()
	{
		return null;
	}

	@Override
	public String[] getASMTransformerClass()
	{
		return new String[]
		{
			"ayamitsu.deathpost.asm.TransformerEntityPlayerMP"
		};
	}

	@Override
	public String getModContainerClass()
	{
		return "ayamitsu.deathpost.asm.ModContainer";
	}

	@Override
	public String getSetupClass()
	{
		return null;
	}

	@Override
	public void injectData(Map<String, Object> data) {}

}
