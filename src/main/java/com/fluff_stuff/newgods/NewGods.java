package com.fluff_stuff.newgods;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;

import com.fluff_stuff.newgods.commands.Accept;
import com.fluff_stuff.newgods.commands.Buy;
import com.fluff_stuff.newgods.commands.Demote;
import com.fluff_stuff.newgods.commands.Deny;
import com.fluff_stuff.newgods.commands.Divorce;
import com.fluff_stuff.newgods.commands.God;
import com.fluff_stuff.newgods.commands.Help;
import com.fluff_stuff.newgods.commands.Home;
import com.fluff_stuff.newgods.commands.Info;
import com.fluff_stuff.newgods.commands.Invite;
import com.fluff_stuff.newgods.commands.Leave;
import com.fluff_stuff.newgods.commands.List;
import com.fluff_stuff.newgods.commands.Marry;
import com.fluff_stuff.newgods.commands.MarryGift;
import com.fluff_stuff.newgods.commands.MarryTP;
import com.fluff_stuff.newgods.commands.Plugin;
import com.fluff_stuff.newgods.commands.Rank;
import com.fluff_stuff.newgods.commands.Sacrifice;
import com.fluff_stuff.newgods.commands.SetHome;
import com.fluff_stuff.newgods.commands.Type;
import com.fluff_stuff.newgods.event.block.Interact;
import com.fluff_stuff.newgods.event.death.EntityDeath;
import com.fluff_stuff.newgods.event.player.PlayerChat;
import com.fluff_stuff.newgods.event.player.PlayerJoined;
import com.fluff_stuff.newgods.event.player.PlayerQuit;
import com.fluff_stuff.newgods.event.sign.SignChanged;

import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class NewGods extends JavaPlugin {
	public final JavaPlugin plugin = this;

	public static Data data;
	
	public static ArrayList<String> godItems;
	public static ArrayList<Integer> itemAmountLeft;
	public static ArrayList<Integer> itemPrayerPoints;
	
	public static ArrayList<String> godMobs;
	public static ArrayList<Integer> mobAmountLeft;
	public static ArrayList<Integer> mobPrayerPoints;

	public static ArrayList<String> buyItems;
	public static ArrayList<String> buyBlessing;
	
	public static ArrayList<String> bannedGodNames;
	
	public static boolean allowMarriges;
	public static boolean allowPrefixs;
	public static InitInterface interfaces;
	
	public static int expAmount=200;
	public static boolean allowEXP=true;
	public static int sacraficeUpdateSpeed=12000;
	public static boolean godPunnishments = true;

	public void onEnable() {
		PluginDescriptionFile pdfFile = getDescription();
		Logger logger = getLogger();
		loadConfig();
		data = new Data(plugin);
		data.reloadPlayers();
		registerCommands();
		registerEvents();
		registerConfig();
		registerInterfaces();
		logger.info(pdfFile.getName() + " has been enabled! Version:" + pdfFile.getVersion());
		regularUpdate();
	}

	public void onDisable() {
		data.saveAndUnloadPlayerData();
		data.saveAndUnloadGodData();
		PluginDescriptionFile pdfFile = getDescription();
		getServer().getScheduler().cancelTasks(plugin);
		Logger logger = getLogger();
		org.bukkit.event.HandlerList.unregisterAll(plugin);
		logger.info(pdfFile.getName() + " has been disabled! Version:" + pdfFile.getVersion());
	}
	
	public void loadConfig(){
		allowMarriges = plugin.getConfig().getBoolean("allow-marriges");
		allowPrefixs = plugin.getConfig().getBoolean("allow-prefixs");	
		expAmount = plugin.getConfig().getInt("exp-bless-amount");	
		allowEXP = plugin.getConfig().getBoolean("allow-exp-blessing");	
		sacraficeUpdateSpeed = plugin.getConfig().getInt("sacrafice-update-speed"); 
		godPunnishments = plugin.getConfig().getBoolean("god-punishments");
		bannedGodNames = (ArrayList<String>) plugin.getConfig().getList("banned-god-names");
		Interact.timeBetweenPrays = plugin.getConfig().getInt("pray-delay");
	}

	public void registerEvents() {
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new SignChanged(), this);
		pm.registerEvents(new PlayerJoined(), this);
		if(allowPrefixs){pm.registerEvents(new PlayerChat(), this);}
		pm.registerEvents(new PlayerQuit(), this);
		pm.registerEvents(new Interact(), this);
		pm.registerEvents(new EntityDeath(), this);
	}

	public void registerCommands() {
		getCommand("gods").setExecutor(new God());
		getCommand("ghelp").setExecutor(new Help());
		getCommand("glist").setExecutor(new List());
		if(allowMarriges){getCommand("gmarry").setExecutor(new Marry());}
		if(allowMarriges){getCommand("gdivorce").setExecutor(new Divorce());}
		getCommand("gleave").setExecutor(new Leave());
		getCommand("ghome").setExecutor(new Home());
		getCommand("gaccept").setExecutor(new Accept());
		getCommand("gdeny").setExecutor(new Deny());
		getCommand("gsethome").setExecutor(new SetHome());
		getCommand("grank").setExecutor(new Rank());
		getCommand("gdemote").setExecutor(new Demote());
		getCommand("ginvite").setExecutor(new Invite());
		getCommand("ginfo").setExecutor(new Info());	
		getCommand("gbuy").setExecutor(new Buy());	
		getCommand("gsacrifice").setExecutor(new Sacrifice());	
		getCommand("gplugin").setExecutor(new Plugin());
		if(allowMarriges){getCommand("gmarrytp").setExecutor(new MarryTP());}
		if(allowMarriges){getCommand("gmarrygift").setExecutor(new MarryGift());}
		getCommand("gtype").setExecutor(new Type());
	}

	public void registerConfig() {
		getConfig().options().copyDefaults(true);
		saveConfig();
	}

	public void regularUpdate() {
		getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			public void run() {
				Random r = new Random();
				godMobs = new ArrayList<String>();
				mobAmountLeft = new ArrayList<Integer>();
				mobPrayerPoints = new ArrayList<Integer>();
				ArrayList<String> sacraficeItems = (ArrayList<String>) plugin.getConfig().getList("sacrafice-mobs");
				for (int i = 0; i < data.godNames.size(); i++) {
					String line = sacraficeItems.get((int) (r.nextFloat() * sacraficeItems.size()));
					String[] values = line.split(",");
					godMobs.add(values[0]);
					mobAmountLeft.add(Integer.parseInt(values[1]));
					mobPrayerPoints.add(Integer.parseInt(values[2]));
				}
				for (int i = 0; i < data.playerNames.size(); i++) {
					String playerGod = data.playerGod.get(i);
					int godID = data.getGodID(playerGod);
					Player p = getServer().getPlayer(data.playerNames.get(i));
					if (p != null && godID != -1) {					
						p.sendMessage(ChatColor.valueOf(data.godType.get(godID)) + playerGod + " wishes you to sacrifice "
								+ mobAmountLeft.get(godID) + " " + godMobs.get(godID) + " with a sword in their name.");
					}
				}
			}
		}, 0, sacraficeUpdateSpeed);		
		
		getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			public void run() {
				Random r = new Random();
				godItems = new ArrayList<String>();
				itemAmountLeft = new ArrayList<Integer>();
				itemPrayerPoints = new ArrayList<Integer>();
				ArrayList<String> sacraficeItems = (ArrayList<String>) plugin.getConfig().getList("sacrafice-items");
				for (int i = 0; i < data.godNames.size(); i++) {
					String line = sacraficeItems.get((int) (r.nextFloat() * sacraficeItems.size()));
					String[] values = line.split(",");
					godItems.add(values[0]);
					itemAmountLeft.add(Integer.parseInt(values[1]));
					itemPrayerPoints.add(Integer.parseInt(values[2]));
				}
				for (int i = 0; i < data.playerNames.size(); i++) {
					String playerGod = data.playerGod.get(i);
					int godID = data.getGodID(playerGod);
					Player p = getServer().getPlayer(data.playerNames.get(i));
					if (p != null && godID != -1) {
						p.sendMessage(ChatColor.valueOf(data.godType.get(godID)) + playerGod + " wishes you to sacrifice "
								+ itemAmountLeft.get(godID) + " " + godItems.get(godID) + " in their name.");
					}
				}
			}
		}, 0, sacraficeUpdateSpeed);			
		
		
		if(allowEXP){
			getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
				public void run() {
					Random r = new Random();
					for (int i = 0; i < data.playerNames.size(); i++) {
						String playerGod = data.playerGod.get(i);
						int godID = data.getGodID(playerGod);
						Player p = getServer().getPlayer(data.playerNames.get(i));

						if (p != null && godID != -1) {
							if (r.nextFloat() > 0.9 && data.playerGodHappines.get(i)>20) {
								p.giveExp(expAmount);
								p.sendMessage(ChatColor.valueOf(data.godType.get(godID)) + playerGod + " has blessed you with "+expAmount+" enchanting xp.");							
							}
																
							if(data.playerGodHappines.get(i)<=0){
								data.playerGodHappines.set(i,5);
								if(godPunnishments){									
									//curse player
									World world = p.getWorld();
								    Location location = p.getLocation();
								    world.strikeLightning(location);
								    p.sendMessage(ChatColor.valueOf(data.godType.get(godID)) + playerGod + " is unhappy with your lack of praying and sacrifices.");
								    world.strikeLightning(location.add(1, 0, 1));
								    world.strikeLightning(location.add(-3, 0, 2));
								    //curse player
								}
							}
							
							if (r.nextFloat() > 0.75) {
								data.playerGodHappines.set(i,data.playerGodHappines.get(i)-5);
								//data.playerGodHappines.set(i,(int) (data.playerGodHappines.get(i)*0.96f));
							}
						}
					}
				}
			}, 0, sacraficeUpdateSpeed/10);
		}
		
	}

	private void registerInterfaces() {
		interfaces= new InitInterface(plugin);
	}

	

}
