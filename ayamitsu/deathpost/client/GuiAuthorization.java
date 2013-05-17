package ayamitsu.deathpost.client;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.src.*;
import net.minecraft.util.StatCollector;

import org.lwjgl.input.Keyboard;

import ayamitsu.deathpost.DeathPost;

public class GuiAuthorization extends GuiScreen
{
	public GuiTextField textField;
	public String mainString = "";

	public GuiAuthorization() {}

	@Override
	public void initGui()
	{
		super.initGui();
		this.textField = new GuiTextField(this.fontRenderer, this.width/2 - 100, (this.height / 2) - 30, 200, 20);
		this.textField.setEnableBackgroundDrawing(true);
		this.textField.setText(this.mainString);
		this.buttonList.clear();
		this.buttonList.add(new GuiButton(0, width / 2 + 80, height / 4 + 100, 20, 20, "Browse"));
		this.buttonList.add(new GuiButton(1, width / 2 - 100, height / 4 + 120, "Done"));
	}

	@Override
	public void onGuiClosed()
	{
		Keyboard.enableRepeatEvents(false);
	}

	@Override
	protected void actionPerformed(GuiButton guibutton)
	{
		if(!guibutton.enabled)
		{
			return;
		}
		else
		{
			if (guibutton.id == 0)
			{
				((ClientProxy)DeathPost.proxy).getAuth().openAuthorizationURL();
			}
			else if (guibutton.id == 1)
			{
				((ClientProxy)DeathPost.proxy).getAuth().setAccessToken(this.mainString);
			}
		}
	}

	@Override
	protected void keyTyped(char par1, int par2)
	{
		super.keyTyped(par1, par2);

		if (this.textField.isFocused())
		{
			this.textField.setText(this.mainString);
			this.textField.textboxKeyTyped(par1, par2);
			this.mainString = this.textField.getText();
		}
	}

	@Override
	public void updateScreen()
	{
		this.textField.updateCursorCounter();
	}

	@Override
	public void drawScreen(int i, int j, float f)
	{
		this.drawDefaultBackground();
		this.textField.setText(this.mainString);
		this.textField.drawTextBox();
		super.drawScreen(i, j, f);

		if (((ClientProxy)DeathPost.proxy).getAuth().isEnabled())
		{
			this.fontRenderer.drawString(StatCollector.translateToLocal("deathpost.auth"), 60, 40, 0xffffff);
		}
	}

	@Override
	public boolean doesGuiPauseGame()
	{
		return true;
	}

	@Override
	protected void mouseClicked(int par1, int par2, int par3)
	{
		super.mouseClicked(par1, par2, par3);
		this.textField.mouseClicked(par1, par2, par3);
	}
}