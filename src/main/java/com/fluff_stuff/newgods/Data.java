package com.fluff_stuff.newgods;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.plugin.java.JavaPlugin;

import com.fluff_stuff.newgods.event.player.PlayerJoined;

import net.md_5.bungee.api.ChatColor;

public class Data {

	private final JavaPlugin plugin;

	public static ArrayList<String> playerNames = new ArrayList<String>();
	public static ArrayList<String> playerGod = new ArrayList<String>();
	public static ArrayList<Integer> playerHolyness = new ArrayList<Integer>();
	public static ArrayList<String> playerPartner = new ArrayList<String>();
	public static ArrayList<Integer> playerGodHappines = new ArrayList<Integer>();
	
	public static ArrayList<String> godNames = new ArrayList<String>();
	public static ArrayList<String> godLeader = new ArrayList<String>();
	public static ArrayList<List<String>> godPriests = new ArrayList<List<String>>();
	public static ArrayList<Integer> godPower = new ArrayList<Integer>();
	public static ArrayList<Integer> godBelievers = new ArrayList<Integer>();
	public static ArrayList<String> godSpawnWorld = new ArrayList<String>();
	public static ArrayList<Integer> godSpawnX = new ArrayList<Integer>();
	public static ArrayList<Integer> godSpawnY = new ArrayList<Integer>();
	public static ArrayList<Integer> godSpawnZ = new ArrayList<Integer>();
	public static ArrayList<String> godType = new ArrayList<String>();
	
	public static ArrayList<AcceptInfo> pendingAccepts = new ArrayList<AcceptInfo>();

	FileConfiguration godData;
	File g;

	public Data(JavaPlugin plugin) {
		this.plugin = plugin;
		loadGods();
	}

	private void loadGods() {
		File gdata = new File(plugin.getDataFolder(), File.separator + "GodDatabase");
		g = new File(gdata, File.separator + "gods" + ".yml");
		godData = YamlConfiguration.loadConfiguration(g);
		if (!g.exists()) {
			try {
				godData.createSection("gods");
				godData.save(g);
			} catch (IOException exception) {
				exception.printStackTrace();
			}
		}

		List<String> rawGodNames = godData.getStringList("gods.names");
		for (String g : rawGodNames) {
			if(godData.getInt("gods.data." + g + ".believers")>0){
				godNames.add(g);
				godLeader.add(godData.getString("gods.data." + g + ".leader"));
				godPriests.add(godData.getStringList("gods.data." + g + ".priests"));
				godPower.add(godData.getInt("gods.data." + g + ".power"));
				godBelievers.add(godData.getInt("gods.data." + g + ".believers"));
				godSpawnWorld.add(godData.getString("gods.data." + g + ".world"));
				godSpawnX.add(godData.getInt("gods.data." + g + ".spawnx"));
				godSpawnY.add(godData.getInt("gods.data." + g + ".spawny"));
				godSpawnZ.add(godData.getInt("gods.data." + g + ".spawnz"));
				godType.add(godData.getString("gods.data." + g + ".type"));
				if(godType.get(godType.size()-1)==null){
					godType.set(godType.size()-1,"AQUA");
				}
			}
		}
	}

	public void loadPlayer(String playerName) {
		File userdata = new File(plugin.getDataFolder(), File.separator + "PlayerDatabase");
		File f = new File(userdata, File.separator + playerName + ".yml");
		FileConfiguration playerData = YamlConfiguration.loadConfiguration(f);

		// When the player file is created for the first time...
		if (!f.exists()) {
			try {
				playerData.createSection("god");				
				playerData.createSection("partner");
				playerData.createSection("holyness");
				playerData.createSection("godHappiness");
				playerData.set("god", "null");
				playerData.set("partner", "null");
				playerData.set("holyness", 0);
				playerData.set("godHappiness", 100);
				playerData.save(f);
				loadPlayer(playerName);
				return;
			} catch (IOException exception) {
				exception.printStackTrace();
			}
		}
		playerNames.add(playerName);
		playerGod.add(playerData.getString("god"));
		playerPartner.add(playerData.getString("partner"));
		playerHolyness.add(playerData.getInt("holyness"));
		playerGodHappines.add(playerData.getInt("godHappiness"));
	}


	
	public void savePlayerData(int playerID){
		File userdata = new File(plugin.getDataFolder(), File.separator + "PlayerDatabase");
		File f = new File(userdata, File.separator + playerNames.get(playerID) + ".yml");
		FileConfiguration playerData = YamlConfiguration.loadConfiguration(f);		
		playerData.set("god", playerGod.get(playerID));
		playerData.set("partner", playerPartner.get(playerID));
		playerData.set("holyness", playerHolyness.get(playerID));
		playerData.set("godHappiness", playerGodHappines.get(playerID));
		try {
			playerData.save(f);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public int getPlayerID(String plr){
		for(int i=0;i<playerNames.size();i++){
			if(playerNames.get(i).equals(plr)){
				return i;
			}
		}
		return -1;
	}
	
	public int getGodID(String god){
		if(god.equals("null")){
			return -1;
		}
		for(int i=0;i<godNames.size();i++){
			if(godNames.get(i).equals(god)){
				return i;
			}
		}
		return -1;
	}

	public void saveGodData() {
		try {
			godData.set("gods.names", godNames);
			for (int i = 0; i < godNames.size(); i++) {
				String name = godNames.get(i);
				godData.set(("gods.data." + name + ".leader"), godLeader.get(i));
				godData.set(("gods.data." + name + ".priests"), godPriests.get(i));
				godData.set(("gods.data." + name + ".power"), godPower.get(i));
				godData.set(("gods.data." + name + ".believers"), godBelievers.get(i));
				godData.set(("gods.data." + name + ".world"), godSpawnWorld.get(i));
				godData.set(("gods.data." + name + ".spawnx"), godSpawnX.get(i));
				godData.set(("gods.data." + name + ".spawny"), godSpawnY.get(i));
				godData.set(("gods.data." + name + ".spawnz"), godSpawnZ.get(i));
				godData.set(("gods.data." + name + ".type"), godType.get(i));
			}

			godData.save(g);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void unloadPlayer(int playerID) {
		savePlayerData(playerID);
		playerNames.remove(playerID);
		playerGod.remove(playerID);
		playerHolyness.remove(playerID);
		playerPartner.remove(playerID);
		playerGodHappines.remove(playerID);
	}

	public void reloadPlayers() {
		for(Player all : plugin.getServer().getOnlinePlayers()){
			int playerID = getPlayerID(all.getName());
			if(playerID!=-1){unloadPlayer(playerID);}
			PlayerJoined.LoadPlayer(all);
		}		
	}

	public void saveAndUnloadPlayerData() {
		if(playerNames.isEmpty()==false){
			unloadPlayer(0);
			saveAndUnloadPlayerData();
		}
	}

	public void saveAndUnloadGodData() {
		saveGodData();
		godNames.clear();
		godLeader.clear();
		godPriests.clear();
		godPower.clear();
		godBelievers.clear();
		godSpawnWorld.clear();
		godSpawnX.clear();
		godSpawnY.clear();
		godSpawnZ.clear();
		godType.clear();
	}

}
