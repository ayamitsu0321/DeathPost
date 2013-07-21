package ayamitsu.deathpost.client;

import java.awt.Desktop;
import java.net.URI;
import java.lang.IllegalStateException;
import java.io.*;

import cpw.mods.fml.common.Loader;

import net.minecraft.util.StatCollector;

import twitter4j.*;
import twitter4j.auth.*;
import twitter4j.conf.*;

public class Auth
{
	private Twitter twitter;
	private int deathCount = 0;

	public Auth()
	{
		this.init();
	}

	public Twitter getTwitter()
	{
		return this.twitter;
	}

	private void init()
	{
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setOAuthConsumerKey("CONSUMER_KEY");
		cb.setOAuthConsumerSecret("CONSUMER_SECRET");
		this.load(cb);
		Configuration cf = cb.build();
		OAuthAuthorization oaa = new OAuthAuthorization(cf);
		this.twitter = new TwitterFactory(cf).getInstance();
	}

	public void post(String msg, String head, String bottom)
	{
		if (this.isEnabled())
		{
			try
			{
				++this.deathCount;
				StringBuilder sb = (new StringBuilder()).append(head).append(StatCollector.translateToLocalFormatted("deathpost.time", Integer.valueOf(this.deathCount))).append(":").append(msg).append(bottom);
				this.getTwitter().updateStatus(sb.toString());
			}
			catch (TwitterException e)
			{
				e.printStackTrace();
			}
			catch (NullPointerException e)
			{
				e.printStackTrace();
			}
			catch (java.lang.IllegalStateException e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			System.out.println("Not authenticated yet.");
		}
	}

	public void setAccessToken(String token, String token_secret)
	{
		try
		{
			this.twitter.setOAuthAccessToken(new AccessToken(token, token_secret));
		}
		catch (IllegalStateException e)
		{
			e.printStackTrace();
		}
	}

	public void setAccessToken(String pin)
	{
		try
		{
			AccessToken accessToken = this.twitter.getOAuthAccessToken(pin);
			this.twitter.setOAuthAccessToken(accessToken);
			this.save();
		}
		catch (TwitterException e)
		{
			e.printStackTrace();
		}
		catch (IllegalStateException e)
		{
			e.printStackTrace();
		}
	}

	public void openAuthorizationURL()
	{
		try
		{
			RequestToken requestToken = this.twitter.getOAuthRequestToken();
			this.openURL(requestToken.getAuthorizationURL());
		}
		catch (TwitterException e)
		{
			e.printStackTrace();
		}
		catch (IllegalStateException e)
		{
			System.out.println("Access token already avaiable.");
			//e.printStackTrace();
		}
		catch (NullPointerException e)
		{
			e.printStackTrace();
		}
	}

	private void openURL(String url)
	{
		try
		{
			Desktop desktop = Desktop.getDesktop();
			desktop.browse(URI.create(url));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public boolean isEnabled()
	{
		return this.twitter.getAuthorization() != null && this.twitter.getAuthorization().isEnabled();
	}

	public void load(ConfigurationBuilder cb)
	{
		File dir = this.getDir();

		if (!dir.exists())
		{
			return;
		}

		File file = this.getFile();

		if (!file.exists())
		{
			return;
		}

		try
		{
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line = br.readLine();

			while (line != null)
			{
				if (line.startsWith("token:"))
				{
					StringBuffer sb = new StringBuffer(line.trim());
					sb.delete(0, 6);
					cb.setOAuthAccessToken(sb.toString().trim());
				}
				else if (line.startsWith("token_secret:"))
				{
					StringBuffer sb = new StringBuffer(line.trim());
					sb.delete(0, 13);
					cb.setOAuthAccessTokenSecret(sb.toString().trim());
				}

				line = br.readLine();
			}

			br.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		catch (ArrayIndexOutOfBoundsException e)
		{
			e.printStackTrace();
		}
	}

	public void save()
	{
		if (!this.isEnabled())
		{
			System.out.println("Not Enabled.");
			return;
		}

		File dir = this.getDir();
		File file = this.getFile();

		try
		{
			if (!dir.exists())
			{
				dir.mkdirs();
			}

			if (!file.exists())
			{
				if (!file.createNewFile())
				{
					return;
				}
			}

			PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file)));
			AccessToken accessToken = this.twitter.getOAuthAccessToken();
			String access_token = accessToken.getToken();
			String access_token_secret = accessToken.getTokenSecret();
			pw.println("token:" + access_token);
			pw.println("token_secret:" + access_token_secret);
			pw.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		catch (TwitterException e)
		{
			e.printStackTrace();
		}
		catch (NullPointerException e)
		{
			e.printStackTrace();
		}
	}

	public File getDir()
	{
		return new File(Loader.instance().getConfigDir(), "/deathpost");
	}

	public File getFile()
	{
		return new File(this.getDir(), "access_token.txt");
	}

}