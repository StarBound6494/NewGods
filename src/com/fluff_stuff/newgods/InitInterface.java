package com.fluff_stuff.newgods;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.fluff_stuff.newgods.commands.God;

public class InitInterface {
	public JavaPlugin plugin;
	//Change white glass to black glass
	//Change link to

	public static IconMenu menu;
	public static IconMenu menuMarrige;
	public static IconMenu menuPropose;
	public static IconMenu menuMarryList;
	public static IconMenu menuBuy;
	public static IconMenu menuSacrifice;
	public static IconMenu menuManage;
	public static IconMenu menuRank;
	public static IconMenu menuDemote;
	public static IconMenu menuInformation;
	public static IconMenu menuGodType;
	public static IconMenu menuGodList;

	public InitInterface(JavaPlugin p) {
		plugin = p;
		initMenu();
		initInformationMenu();
		initManageMenu();
		initRankMenu();
		initDemoteMenu();
		initSacrificeMenu();
		initMarrigeMenu();
		initProposeMenu();
		initMarryMenu();
		initBuyMenu();
		initGodType();
		initGodList();
	}

	public void initGodList() {
		menuGodList = new IconMenu("List Menu", 45, new IconMenu.OptionClickEventHandler() {
			@Override
			public void onOptionClick(IconMenu.OptionClickEvent event) {
				String name = event.getName();
				Player p = event.getPlayer();
				if (name.equals("Back")) {
					menuInformation.open(p);
				} else {
					God.FollowGod(p, name);
				}
				event.setWillClose(true);
			}
		}, plugin);
		menuGodList.setOption(0, new ItemStack(Material.BARRIER, 1), "Back", "Go back");
	}

	public void initGodType() {
		menuGodType = new IconMenu("God Type Menu", 18, new IconMenu.OptionClickEventHandler() {
			@Override
			public void onOptionClick(IconMenu.OptionClickEvent event) {
				String name = event.getName();
				Player p = event.getPlayer();
				if (name.equals("Back")) {
					menuManage.open(p);
				} else {
					God.SetType(event.getPlayer(), name);
				}

				event.setWillClose(true);
			}
		}, plugin).setOption(0, new ItemStack(Material.BARRIER, 1), "Back", "Go back");
		setGlass(menuGodType, 1, 18);
		menuGodType.setOption(1, new ItemStack(Material.WHITE_WOOL, 1), "WHITE", "Change your god to be this hue");
		menuGodType.setOption(2, new ItemStack(Material.ORANGE_WOOL, 1), "GOLD", "Change your god to be this hue");
		menuGodType.setOption(3, new ItemStack(Material.PURPLE_WOOL, 1), "LIGHT_PURPLE",
				"Change your god to be this hue");
		menuGodType.setOption(4, new ItemStack(Material.LIGHT_BLUE_WOOL, 1), "AQUA", "Change your god to be this hue");
		menuGodType.setOption(5, new ItemStack(Material.YELLOW_WOOL, 1), "YELLOW", "Change your god to be this hue");
		menuGodType.setOption(6, new ItemStack(Material.GREEN_WOOL, 1), "GREEN", "Change your god to be this hue");
		menuGodType.setOption(7, new ItemStack(Material.PINK_WOOL, 1), "RED", "Change your god to be this hue");
		menuGodType.setOption(8, new ItemStack(Material.GRAY_WOOL, 1), "DARK_GRAY",
				"Change your god to be this hue");
		menuGodType.setOption(9, new ItemStack(Material.LIGHT_GRAY_WOOL, 1), "GRAY", "Change your god to be this hue");
		menuGodType.setOption(10, new ItemStack(Material.CYAN_WOOL, 1), "DARK_AQUA",
				"Change your god to be this hue");
		menuGodType.setOption(11, new ItemStack(Material.MAGENTA_WOOL, 1), "DARK_PURPLE",
				"Change your god to be this hue");
		menuGodType.setOption(12, new ItemStack(Material.BLUE_WOOL, 1), "DARK_BLUE",
				"Change your god to be this hue");
		menuGodType.setOption(13, new ItemStack(Material.GREEN_WOOL, 1), "DARK_GREEN",
				"Change your god to be this hue");
		menuGodType.setOption(14, new ItemStack(Material.RED_WOOL, 1), "DARK_RED",
				"Change your god to be this hue");
		menuGodType.setOption(15, new ItemStack(Material.BLACK_WOOL, 1), "BLACK",
				"Change your god to be this hue");
	}

	public void initMenu() {
		menu = new IconMenu("Gods Menu", 9, new IconMenu.OptionClickEventHandler() {
			@Override
			public void onOptionClick(IconMenu.OptionClickEvent event) {
				String name = event.getName();
				Player p = event.getPlayer();
				int godID = NewGods.data.getGodID(NewGods.data.playerGod.get(NewGods.data.getPlayerID(p.getName())));
				if (name.equals("Home")) {
					if (godID == -1) {
						p.closeInventory();
						p.sendMessage(ChatColor.DARK_RED + "You need a god to do this.");
					} else {
						p.performCommand("ghome");
						p.closeInventory();
					}
				}
				if (name.equals("Marrige") && NewGods.allowMarriges) {
					menuMarrige.open(p);
				}
				if (name.equals("Sacrifice")) {
					p.performCommand("gsacrifice");
				}
				if (name.equals("Manage")) {
					if (godID == -1) {
						p.closeInventory();
						p.sendMessage(ChatColor.DARK_RED + "You need a god to do this.");
					} else {
						menuManage.open(p);
					}
				}
				if (name.equals("Buy")) {
					p.performCommand("gbuy");
				}
				if (name.equals("Information")) {
					menuInformation.open(p);
				}
				if (name.equals("Exit")) {
					p.closeInventory();
				}
				event.setWillClose(true);
			}
		}, plugin);
		setGlass(menu, 0, 9);
		menu.setOption(1, new ItemStack(Material.RED_BED, 1), "Home", "Teleport to your religion's home");
		if (NewGods.allowMarriges) {
			menu.setOption(2, new ItemStack(Material.ROSE_BUSH, 1), "Marrige", "Choose marrige options");
		}
		menu.setOption(3, new ItemStack(Material.EMERALD, 1), "Buy", "Spend your faith power");
		menu.setOption(4, new ItemStack(Material.LAVA_BUCKET, 1), "Sacrifice", "Choose an item to sacrifice");
		menu.setOption(5, new ItemStack(Material.REDSTONE_TORCH, 1), "Manage", "Do priestly business");
		menu.setOption(6, new ItemStack(Material.PAPER, 1), "Information", "Get help and info");
		menu.setOption(7, new ItemStack(Material.BARRIER, 1), "Exit", "Exit the interface");
	}

	public void initMarrigeMenu() {
		menuMarrige = new IconMenu("Marrige Menu", 9, new IconMenu.OptionClickEventHandler() {
			@Override
			public void onOptionClick(IconMenu.OptionClickEvent event) {
				String name = event.getName();
				Player p = event.getPlayer();
				if (name.equals("Back")) {
					menu.open(p);
				}
				if (name.equals("Divorce Player")) {
					p.performCommand("gdivorce");
					p.closeInventory();
				}
				if (name.equals("Marry Player")) {
					proposeMenuUpdate();
					menuPropose.open(p);
				}
				if (name.equals("Married Players")) {
					marryListMenuUpdate();
					menuMarryList.open(p);
				}
				if (name.equals("Teleport")) {
					p.closeInventory();
					p.performCommand("gmarrytp");
				}
				if (name.equals("Gift")) {
					p.closeInventory();
					p.performCommand("gmarrygift");
				}
				if (name.equals("Exit")) {
					p.closeInventory();
				}
				event.setWillClose(true);
			}
		}, plugin);
		setGlass(menuMarrige, 1, 9);
		menuMarrige.setOption(0, new ItemStack(Material.BARRIER, 1), "Back", "Go back");
		menuMarrige.setOption(2, new ItemStack(Material.ELYTRA, 1), "Teleport", "Teleport to your husband/wife");
		menuMarrige.setOption(3, new ItemStack(Material.ROSE_BUSH, 1), "Marry Player", "Show your love and propose!");
		menuMarrige.setOption(4, new ItemStack(Material.PAPER, 1), "Married Players", "Online players who are married");
		menuMarrige.setOption(5, new ItemStack(Material.SKELETON_SKULL, 1), "Divorce Player",
				"Pack up your things and get out!");
		menuMarrige.setOption(6, new ItemStack(Material.DIAMOND, 1), "Gift",
				"Give your husband/wife the item you hold");
		menuMarrige.setOption(8, new ItemStack(Material.BARRIER, 1), "Exit", "Exit the interface");
	}

	public void initProposeMenu() {
		menuPropose = new IconMenu("Propose Menu", 45, new IconMenu.OptionClickEventHandler() {
			@Override
			public void onOptionClick(IconMenu.OptionClickEvent event) {
				String name = event.getName();
				Player p = event.getPlayer();
				if (name.equals("Back")) {
					menuMarrige.open(p);
				} else {
					if (name.equals(" ") == false) {
						p.performCommand("gmarry " + name);
						p.closeInventory();
					}
				}
				event.setWillClose(true);
			}
		}, plugin).setOption(0, new ItemStack(Material.BARRIER, 1), "Back", "Go back");
	}

	public void initMarryMenu() {
		menuMarryList = new IconMenu("Marrige List Menu", 45, new IconMenu.OptionClickEventHandler() {
			@Override
			public void onOptionClick(IconMenu.OptionClickEvent event) {
				String name = event.getName();
				Player p = event.getPlayer();
				if (name.equals("Back")) {
					menuMarrige.open(p);
				}
				event.setWillClose(true);
			}
		}, plugin).setOption(0, new ItemStack(Material.BARRIER, 1), "Back", "Go back");
	}

	public void initBuyMenu() {
		NewGods.buyItems = (ArrayList<String>) plugin.getConfig().getList("buy-items");
		NewGods.buyBlessing = (ArrayList<String>) plugin.getConfig().getList("buy-blessings");
		menuBuy = new IconMenu("Buy Menu", 45, new IconMenu.OptionClickEventHandler() {
			@Override
			public void onOptionClick(IconMenu.OptionClickEvent event) {
				String name = event.getName();
				Player p = event.getPlayer();
				if (name.equals("Back")) {
					menu.open(p);
				}
				int pID = NewGods.data.getPlayerID(p.getName());
				for (int i = 0; i < NewGods.buyItems.size(); i++) {
					String[] itm = NewGods.buyItems.get(i).split(",");
					if (name.equals(itm[0])) {
						if (NewGods.data.playerHolyness.get(pID) >= Integer.parseInt(itm[1])) {
							p.getInventory()
									.addItem(new ItemStack(Material.getMaterial(itm[0]), Integer.parseInt(itm[2])));
							NewGods.data.playerHolyness.set(pID,
									NewGods.data.playerHolyness.get(pID) - Integer.parseInt(itm[1]));
						} else {
							p.sendMessage(ChatColor.DARK_RED + "You need " + itm[1]
									+ " faith power to get this and you have " + NewGods.data.playerHolyness.get(pID)+".");
							p.closeInventory();
						}
					}
				}
				for (int i = 0; i < NewGods.buyBlessing.size(); i++) {
					String[] itm = NewGods.buyBlessing.get(i).split(",");
					if (name.equals(itm[0])) {
						if (NewGods.data.playerHolyness.get(pID) >= Integer.parseInt(itm[1])) {
							p.addPotionEffect(new PotionEffect(PotionEffectType.getByName(itm[0]),
									Integer.parseInt(itm[2]) * 20, 1));
							NewGods.data.playerHolyness.set(pID,
									NewGods.data.playerHolyness.get(pID) - Integer.parseInt(itm[1]));
						} else {
							p.sendMessage(ChatColor.DARK_RED + "You need " + itm[1]
									+ " faith power to get this and you have " + NewGods.data.playerHolyness.get(pID));
							p.closeInventory();
						}
					}
				}
				event.setWillClose(true);
			}
		}, plugin).setOption(0, new ItemStack(Material.BARRIER, 1), "Back", "Go back");
		setGlass(menuBuy, 1, 45);
	}

	public void initSacrificeMenu() {
		menuSacrifice = new IconMenu("Sacrifice Menu", 9, new IconMenu.OptionClickEventHandler() {
			@Override
			public void onOptionClick(IconMenu.OptionClickEvent event) {
				String name = event.getName();
				Player p = event.getPlayer();
				if (name.equals(" ") == false) {
					if (name.equals("Back")) {
						menu.open(p);
					} else {
						God.SacrificeItem(p);
					}
				}
				event.setWillClose(true);
			}
		}, plugin).setOption(0, new ItemStack(Material.BARRIER, 1), "Back", "Go back");
	}

	public void initManageMenu() {
		menuManage = new IconMenu("Manage Menu", 9, new IconMenu.OptionClickEventHandler() {
			@Override
			public void onOptionClick(IconMenu.OptionClickEvent event) {
				String name = event.getName();
				Player p = event.getPlayer();
				if (name.equals("Back")) {
					menu.open(p);
				}
				if (name.equals("Leave Religion")) {
					p.performCommand("gleave");
					p.closeInventory();
				}
				if (name.equals("Rank Player")) {
					String god = NewGods.data.playerGod.get(NewGods.data.getPlayerID(p.getName()));
					rankMenuUpdate(god);
					menuRank.open(p);
				}
				if (name.equals("Set Home")) {
					p.closeInventory();
					p.performCommand("gsethome");
				}
				if (name.equals("Demote Player")) {
					String god = NewGods.data.playerGod.get(NewGods.data.getPlayerID(p.getName()));
					demoteMenuUpdate(god);
					menuDemote.open(p);
				}
				if (name.equals("Set God Type")) {
					menuGodType.open(p);
				}
				event.setWillClose(true);
			}
		}, plugin);
		setGlass(menuManage, 1, 9);
		menuManage.setOption(0, new ItemStack(Material.BARRIER, 1), "Back", "Go back");
		menuManage.setOption(3, new ItemStack(Material.GREEN_BANNER, 1), "Rank Player",
				"Pick a player in your faith to rank");
		menuManage.setOption(4, new ItemStack(Material.RED_BED, 1), "Set Home", "Set home");
		menuManage.setOption(5, new ItemStack(Material.RED_BANNER, 1), "Demote Player",
				"Pick a player in your faith to demote");
		menuManage.setOption(8, new ItemStack(Material.BARRIER, 1), "Leave Religion", "Abandon your religion");
		menuManage.setOption(6, new ItemStack(Material.GLOBE_BANNER_PATTERN, 1), "Set God Type",
				"Change what type of god you follow");
	}

	public void initRankMenu() {
		menuRank = new IconMenu("Rank Menu", 45, new IconMenu.OptionClickEventHandler() {
			@Override
			public void onOptionClick(IconMenu.OptionClickEvent event) {
				String name = event.getName();
				Player p = event.getPlayer();
				if (name.equals("Back")) {
					menuManage.open(p);
				} else {
					if (name.equals(" ") == false) {
						p.performCommand("grank " + name);
						p.closeInventory();
					}
				}
				event.setWillClose(true);
			}
		}, plugin).setOption(0, new ItemStack(Material.BARRIER, 1), "Back", "Go back");
	}

	public void initDemoteMenu() {
		menuDemote = new IconMenu("Demote Menu", 45, new IconMenu.OptionClickEventHandler() {
			@Override
			public void onOptionClick(IconMenu.OptionClickEvent event) {
				String name = event.getName();
				Player p = event.getPlayer();
				if (name.equals("Back")) {
					menuManage.open(p);
				} else {
					if (name.equals(" ") == false) {
						p.performCommand("gdemote " + name);
						p.closeInventory();
					}
				}
				event.setWillClose(true);
			}
		}, plugin).setOption(0, new ItemStack(Material.BARRIER, 1), "Back", "Go back");
	}

	public void initInformationMenu() {
		menuInformation = new IconMenu("Information Menu", 9, new IconMenu.OptionClickEventHandler() {
			@Override
			public void onOptionClick(IconMenu.OptionClickEvent event) {
				String name = event.getName();
				Player p = event.getPlayer();
				if (name.equals("Back")) {
					menu.open(p);
				}
				if (name.equals("Info")) {
					p.performCommand("ginfo");
					p.closeInventory();
				}
				if (name.equals("Help")) {
					p.performCommand("ghelp");
					p.closeInventory();
				}
				if (name.equals("List Gods")) {
					p.performCommand("glist");
				}
				if (name.equals("Plugin")) {
					p.performCommand("gplugin");
					p.closeInventory();
				}
				event.setWillClose(true);
			}
		}, plugin);
		setGlass(menuInformation, 0, 9);
		menuInformation.setOption(0, new ItemStack(Material.BARRIER, 1), "Back", "Go back");
		menuInformation.setOption(3, new ItemStack(Material.BOOK, 1), "List Gods", "Get a list of gods");
		menuInformation.setOption(4, new ItemStack(Material.BIRCH_BOAT, 1), "Help", "Learn commands and how to make gods");
		menuInformation.setOption(5, new ItemStack(Material.PAPER, 1), "Info", "Get your gods information");
		menuInformation.setOption(6, new ItemStack(Material.NETHER_STAR, 1), "Plugin",
				"Get the most recent updates here");
	}

	public void updateSacrificeMenu(int godID) {
		setGlass(menuSacrifice, 1, 9);
		if (godID < NewGods.itemAmountLeft.size()) {
			if (NewGods.itemAmountLeft.get(godID) > 0) {
				ItemStack itms = new ItemStack(Material.getMaterial(NewGods.godItems.get(godID)),
						NewGods.itemAmountLeft.get(godID));
				menuSacrifice.setOption(3, itms, NewGods.godItems.get(godID),
						"Sacrifice for " + NewGods.itemPrayerPoints.get(godID) + " faith");
			}
		}
		if (godID < NewGods.mobAmountLeft.size()) {
			if (NewGods.mobAmountLeft.get(godID) > 0) {
				ItemStack itms = new ItemStack(Material.SKELETON_SKULL, NewGods.mobAmountLeft.get(godID));
				menuSacrifice.setOption(5, itms, NewGods.godMobs.get(godID),
						"Sacrifice with a sword for " + NewGods.mobPrayerPoints.get(godID) + " faith");
			}
		}
	}

	public void setGlass(IconMenu a, int start, int end) {
		for (int i = start; i < end; i++) {
			a.setOption(i, new ItemStack(Material.BLACK_STAINED_GLASS_PANE, 1), " ", "");
		}
	}

	public void proposeMenuUpdate() {
		menuPropose.setOption(0, new ItemStack(Material.BARRIER, 1), "Back", "Go back");
		setGlass(menuPropose, 1, 45);
		for (int i = 1; i < 45; i++) {
			if (i <= NewGods.data.playerNames.size()) {
				menuPropose.setOption(i, new ItemStack(Material.SKELETON_SKULL, 1), NewGods.data.playerNames.get(i - 1),
						"Propose to " + NewGods.data.playerNames.get(i - 1));
			}
		}
	}

	public void marryListMenuUpdate() {
		menuMarryList.setOption(0, new ItemStack(Material.BARRIER, 1), "Back", "Go back");
		setGlass(menuMarryList, 1, 45);
		int playerNum = 0;
		for (int i = 0; i < 45; i++) {
			if (i < NewGods.data.playerNames.size()) {
				if (NewGods.data.playerPartner.get(i).equals("null") == false) {
					playerNum++;
					menuMarryList.setOption(playerNum, new ItemStack(Material.SKELETON_SKULL, 1),
							NewGods.data.playerNames.get(i), "Married to " + NewGods.data.playerPartner.get(i));
				}
			}
		}
	}

	public void rankMenuUpdate(String god) {
		menuRank.setOption(0, new ItemStack(Material.BARRIER, 1), "Back", "Go back");
		setGlass(menuRank, 1, 45);
		int playerNum = 0;
		for (int i = 0; i < 45; i++) {
			if (i < NewGods.data.playerNames.size()) {
				if (NewGods.data.playerGod.get(i).equals(god)) {
					playerNum++;
					menuRank.setOption(playerNum, new ItemStack(Material.SKELETON_SKULL, 1),
							NewGods.data.playerNames.get(i), "Rank this player");
				}
			}
		}
	}

	public void demoteMenuUpdate(String god) {
		menuDemote.setOption(0, new ItemStack(Material.BARRIER, 1), "Back", "Go back");
		setGlass(menuDemote, 1, 45);
		int playerNum = 0;
		for (int i = 0; i < 45; i++) {
			if (i < NewGods.data.playerNames.size()) {
				if (NewGods.data.playerGod.get(i).equals(god)) {
					int godID = NewGods.data.getGodID(god);
					if (NewGods.data.godPriests.get(godID).contains(NewGods.data.playerNames.get(i))) {
						playerNum++;
						menuDemote.setOption(playerNum, new ItemStack(Material.SKELETON_SKULL, 1),
								NewGods.data.playerNames.get(i), "Demote this player");
					}
				}
			}
		}
	}

	public static void updateBuyMenu(int pID) {
		int a = 1;
		int gID = NewGods.data.getGodID(NewGods.data.playerGod.get(pID));
		int gPower = NewGods.data.godPower.get(gID);
		for (int i = 0; i < NewGods.buyItems.size(); i++) {
			String[] itm = NewGods.buyItems.get(i).split(",");
			if (Integer.parseInt(itm[3]) <= gPower) {
				menuBuy.setOption(a, new ItemStack(Material.getMaterial(itm[0]), Integer.parseInt(itm[2])), "" + itm[0],
						"Cost : " + itm[1]);
				a++;
			}
		}
		for (int i = 0; i < NewGods.buyBlessing.size(); i++) {
			String[] itm = NewGods.buyBlessing.get(i).split(",");
			if (Integer.parseInt(itm[3]) <= gPower) {
				menuBuy.setOption(a, new ItemStack(Material.NETHER_STAR, 1), "" + itm[0],
						"Cost : " + itm[1] + ". Lasts " + itm[2] + " seconds.");
				a++;
			}
		}
	}

	public static void updateMenuGodList() {
		ArrayList<String> godNames = NewGods.data.godNames;
		ArrayList<String> godPriests = NewGods.data.godLeader;
		ArrayList<Integer> godPower = NewGods.data.godPower;
		ArrayList<Integer> godBelievers = NewGods.data.godBelievers;
		for (int i = 0; i < godNames.size(); i++) {
			menuGodList.setOption(i + 1, new ItemStack(Material.SNOWBALL, 1), godNames.get(i),
					"Priest:" + godPriests.get(i) + " Faith Power:" + godPower.get(i) + " Believers:" + godBelievers.get(i));
		}
	}

}
