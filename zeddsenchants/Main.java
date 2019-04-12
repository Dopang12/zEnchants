package zeddsenchants;

import java.util.ArrayList;
import java.util.Iterator;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Main extends JavaPlugin implements Listener {
   private static Plugin plugin;
   static final Plugin main;

   static {
      main = plugin;
   }

   public void onEnable() {
      this.getServer().getPluginManager().registerEvents(this, this);
      FileConfiguration config = this.getConfig();
      config.addDefault("Pyroaxe", 3);
      config.options().copyDefaults(true);
      this.saveConfig();
      this.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
         public void run() {
            Iterator var2 = Main.this.getServer().getOnlinePlayers().iterator();

            while(var2.hasNext()) {
               Player p = (Player)var2.next();
               Main.this.itemPotion(p, p.getInventory().getHelmet());
               Main.this.itemPotion(p, p.getInventory().getChestplate());
               Main.this.itemPotion(p, p.getInventory().getLeggings());
               Main.this.itemPotion(p, p.getInventory().getBoots());
            }

         }
      }, 0L, 20L);
   }

   public void itemPotion(Player p, ItemStack item) {
      if (item != null) {
         if (item.hasItemMeta()) {
            if (item.getItemMeta().hasLore()) {
               String itemLore = ChatColor.stripColor((String)item.getItemMeta().getLore().get(0));
               if (itemLore.equals("Glowing")) {
                  p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 999999, 2));
                  int hunger = p.getPlayer().getFoodLevel();
                  int air = p.getPlayer().getRemainingAir();
                  if (hunger < 20) {
                     p.getPlayer().setFoodLevel(hunger + 1);
                  }

                  if (air < p.getPlayer().getMaximumAir()) {
                     p.getPlayer().setRemainingAir(air + 1);
                  }
               }

               if (itemLore.equals("Infused with Strength")) {
                  p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 999999, 1));
               }

               if (itemLore.equals("Infused with Speed")) {
                  p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 999999, 1));
               }

               if (itemLore.equals("Infused with Gapples")) {
                  p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 999999, 4));
                  p.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, 999999, 1));
                  p.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 999999, 0));
                  p.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 999999, 3));
                  p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 999999, 0));
               }

               if (itemLore.equals("Infused with Invisibility")) {
                  p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 999999, 2));
               }

               if (itemLore.equals("Destroy The Armor Of All Your Enemies")) {
                  p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 999999, 1));
                  p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 999999, 1));
               }

               if (itemLore.equals("Infused with Air")) {
                  p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 999999, 1));
               }

            }
         }
      }
   }

   @EventHandler
   public void playerHitPlayerEvent(EntityDamageByEntityEvent event) {
      Entity damager = event.getDamager();
      if (damager instanceof Player) {
         Player player = (Player)damager;
         if (player.getItemInHand() == null) {
            return;
         }

         if (!player.getItemInHand().hasItemMeta()) {
            return;
         }

         if (!player.getItemInHand().getItemMeta().hasLore()) {
            return;
         }

         LivingEntity target;
         if (((String)player.getItemInHand().getItemMeta().getLore().get(0)).equals(ChatColor.DARK_RED + "I come from the Iron Hills")) {
            target = (LivingEntity)event.getEntity();
            target.getWorld().playEffect(target.getLocation(), Effect.ZOMBIE_DESTROY_DOOR, 1);
            target.getWorld().playSound(target.getLocation(), Sound.ANVIL_LAND, 1.0F, 0.001F);
            player.playSound(target.getLocation(), Sound.ANVIL_LAND, 2.0F, 0.001F);
            if (!event.isCancelled()) {
               event.setDamage((double)this.getConfig().getInt("Pyroaxe") * event.getDamage());
            }
         }

         if (((String)player.getItemInHand().getItemMeta().getLore().get(0)).equals(ChatColor.GREEN + "I come from the Iron Hills")) {
            target = (LivingEntity)event.getEntity();
            target.getWorld().playEffect(target.getLocation(), Effect.ZOMBIE_DESTROY_DOOR, 1);
            target.getWorld().playSound(target.getLocation(), Sound.ANVIL_LAND, 1.0F, 0.001F);
            player.playSound(target.getLocation(), Sound.ANVIL_LAND, 2.0F, 0.001F);
            if (!event.isCancelled()) {
               event.setDamage(event.getDamage() * 3.0D);
            }
         }

         if (ChatColor.stripColor((String)player.getInventory().getItemInHand().getItemMeta().getLore().get(0)).equals("Blow Your Opponent Away")) {
            target = (LivingEntity)event.getEntity();
            target.getWorld().playSound(target.getLocation(), Sound.CHEST_OPEN, 1.0F, 0.001F);
            target.getWorld().playSound(target.getLocation(), Sound.EXPLODE, 1.0F, 0.001F);
            if (!event.isCancelled()) {
               target.addPotionEffect(new PotionEffect(PotionEffectType.HARM, 3, 0));
            }
         }

         if (((String)player.getItemInHand().getItemMeta().getLore().get(0)).equals(ChatColor.DARK_RED + "I come from a Crate")) {
            target = (LivingEntity)event.getEntity();
            target.getWorld().playEffect(target.getLocation(), Effect.ZOMBIE_DESTROY_DOOR, 1);
            target.getWorld().playSound(target.getLocation(), Sound.ANVIL_LAND, 1.0F, 0.001F);
            player.playSound(target.getLocation(), Sound.ANVIL_LAND, 1.0F, 0.001F);
            if (!event.isCancelled()) {
               event.setDamage(event.getDamage() * 3.0D);
            }
         }

         if (ChatColor.stripColor((String)player.getItemInHand().getItemMeta().getLore().get(0)).equals("Deathbringer")) {
            if (player.getFireTicks() < 0) {
               event.setDamage(event.getDamage() * 3.0D);
            }

            player.setHealth(player.getHealth() + 2.0D);
         }
      }

   }

   @EventHandler
   public void damage(PlayerItemBreakEvent event) {
      Player player = event.getPlayer();
      String lore = ChatColor.stripColor((String)event.getBrokenItem().getItemMeta().getLore().get(7));
      if (lore.equals("Infused with Strength")) {
         player.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
      } else if (lore.equals("Infused with Invisibility")) {
         player.removePotionEffect(PotionEffectType.INVISIBILITY);
      } else if (lore.equals("Infused with Gapples")) {
         player.removePotionEffect(PotionEffectType.REGENERATION);
         player.removePotionEffect(PotionEffectType.ABSORPTION);
         player.removePotionEffect(PotionEffectType.HEALTH_BOOST);
         player.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
         player.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
      } else if (lore.equals("Glowing")) {
         player.removePotionEffect(PotionEffectType.NIGHT_VISION);
      } else if (lore.equals("Infused with Speed")) {
         player.removePotionEffect(PotionEffectType.SPEED);
      } else if (lore.equals("Destroy The Armor Of All Your Enemies")) {
         player.removePotionEffect(PotionEffectType.JUMP);
         player.removePotionEffect(PotionEffectType.SPEED);
      } else if (lore.equals("Infused with Air")) {
         player.removePotionEffect(PotionEffectType.JUMP);
      }

   }

   @EventHandler
   public void click(InventoryClickEvent event) {
      Player player = (Player)event.getWhoClicked();
      int slot = event.getSlot();
      if (event.getCurrentItem() != null) {
         if (event.getCurrentItem().hasItemMeta()) {
            if (event.getCurrentItem().getItemMeta().hasLore()) {
               String lore = ChatColor.stripColor((String)event.getCurrentItem().getItemMeta().getLore().get(0));
               if (player.getGameMode() != GameMode.CREATIVE && slot == 39 || slot == 38 || slot == 37 || slot == 36 || player.getGameMode() == GameMode.CREATIVE && slot == 5 || slot == 6 || slot == 7 || slot == 8) {
                  if (lore.equals("Infused with Strength")) {
                     player.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
                  } else if (lore.equals("Infused with Invisibility")) {
                     player.removePotionEffect(PotionEffectType.INVISIBILITY);
                  } else if (lore.equals("Infused with Gapples")) {
                     player.removePotionEffect(PotionEffectType.REGENERATION);
                     player.removePotionEffect(PotionEffectType.ABSORPTION);
                     player.removePotionEffect(PotionEffectType.HEALTH_BOOST);
                     player.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
                     player.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
                  } else if (lore.equals("Glowing")) {
                     player.removePotionEffect(PotionEffectType.NIGHT_VISION);
                  } else if (lore.equals("Infused with Speed")) {
                     player.removePotionEffect(PotionEffectType.SPEED);
                  } else if (lore.equals("Destroy The Armor Of All Your Enemies")) {
                     player.removePotionEffect(PotionEffectType.JUMP);
                     player.removePotionEffect(PotionEffectType.SPEED);
                  } else if (lore.equals("Infused with Air")) {
                     player.removePotionEffect(PotionEffectType.JUMP);
                  }
               }

            }
         }
      }
   }

   public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
      Player p = (Player)sender;
      if (label.equalsIgnoreCase("zenchant")) {
         if (args.length == 0) {
            p.sendMessage(ChatColor.MAGIC + "I" + ChatColor.DARK_RED + ChatColor.BOLD + "Kappa" + ChatColor.MAGIC + "I");
            p.sendMessage(ChatColor.RED + "By ZeddCC");
            p.sendMessage(ChatColor.DARK_GREEN + "---------------------");
         }

         if (args.length == 1 && p.hasPermission("Pyro.admin") && args[0].equalsIgnoreCase("create")) {
            p.sendMessage(ChatColor.DARK_RED + "Incorrect Usage: " + ChatColor.RED + "/zenchant create <pyroaxe,apollos,aegis,ethereals,hermes>");
         }

         if (args.length == 2 && p.hasPermission("Pyro.admin")) {
            if (args[0].equals("Reload")) {
               plugin.reloadConfig();
               p.sendMessage(ChatColor.DARK_RED + "zEnchant" + ChatColor.DARK_GRAY + " > " + ChatColor.RED + "Plugin Reloaded");
               p.sendMessage(ChatColor.DARK_GREEN + "---------------------");
            }

            if (args[0].equalsIgnoreCase("create")) {
               ItemStack ethereals;
               ItemMeta meta;
               ArrayList lore;
               if (args[1].equalsIgnoreCase("pyroaxe")) {
                  ethereals = new ItemStack(Material.DIAMOND_AXE, 1);
                  meta = ethereals.getItemMeta();
                  ethereals.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 4);
                  ethereals.addUnsafeEnchantment(Enchantment.FIRE_ASPECT, 1);
                  lore = new ArrayList();
                  meta.setDisplayName(ChatColor.DARK_RED + "Pyro Axe");
                  lore.add(ChatColor.DARK_RED + "I come from the Iron Hills");
                  meta.setLore(lore);
                  ethereals.setItemMeta(meta);
                  p.getInventory().addItem(new ItemStack[]{ethereals});
               }

               if (args[1].equalsIgnoreCase("Apollos")) {
                  ethereals = new ItemStack(Material.DIAMOND_HELMET, 1);
                  meta = ethereals.getItemMeta();
                  ethereals.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
                  ethereals.addUnsafeEnchantment(Enchantment.PROTECTION_FIRE, 3);
                  ethereals.addUnsafeEnchantment(Enchantment.THORNS, 2);
                  ethereals.addUnsafeEnchantment(Enchantment.DURABILITY, 2);
                  lore = new ArrayList();
                  meta.setDisplayName(ChatColor.DARK_GRAY + "* " + ChatColor.DARK_RED + "Apollos" + ChatColor.DARK_GRAY + " *");
                  lore.add(ChatColor.DARK_RED + " Glowing");
                  lore.add(ChatColor.DARK_RED + " Implants");
                  meta.setLore(lore);
                  ethereals.setItemMeta(meta);
                  p.getInventory().addItem(new ItemStack[]{ethereals});
               }

               if (args[1].equalsIgnoreCase("Aegis")) {
                  ethereals = new ItemStack(Material.DIAMOND_CHESTPLATE, 1);
                  meta = ethereals.getItemMeta();
                  ethereals.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
                  ethereals.addUnsafeEnchantment(Enchantment.PROTECTION_FIRE, 3);
                  ethereals.addUnsafeEnchantment(Enchantment.THORNS, 2);
                  ethereals.addUnsafeEnchantment(Enchantment.DURABILITY, 3);
                  lore = new ArrayList();
                  meta.setDisplayName(ChatColor.DARK_RED + "* " + ChatColor.DARK_GRAY + "Aegis" + ChatColor.DARK_RED + " *");
                  lore.add(ChatColor.DARK_GREEN + "Infused with Strength");
                  meta.setLore(lore);
                  ethereals.setItemMeta(meta);
                  p.getInventory().addItem(new ItemStack[]{ethereals});
               }

               if (args[1].equalsIgnoreCase("Ethereals")) {
                  ethereals = new ItemStack(Material.DIAMOND_LEGGINGS, 1);
                  meta = ethereals.getItemMeta();
                  ethereals.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
                  ethereals.addUnsafeEnchantment(Enchantment.PROTECTION_FIRE, 3);
                  ethereals.addUnsafeEnchantment(Enchantment.THORNS, 2);
                  ethereals.addUnsafeEnchantment(Enchantment.DURABILITY, 3);
                  lore = new ArrayList();
                  meta.setDisplayName(ChatColor.DARK_GRAY + "Ethereals");
                  lore.add(ChatColor.DARK_GREEN + "Infused with Invisibility");
                  meta.setLore(lore);
                  ethereals.setItemMeta(meta);
                  p.getInventory().addItem(new ItemStack[]{ethereals});
               }

               if (args[1].equalsIgnoreCase("Hermes")) {
                  ethereals = new ItemStack(Material.DIAMOND_BOOTS, 1);
                  meta = ethereals.getItemMeta();
                  ethereals.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
                  ethereals.addUnsafeEnchantment(Enchantment.PROTECTION_FIRE, 3);
                  ethereals.addUnsafeEnchantment(Enchantment.PROTECTION_FALL, 3);
                  ethereals.addUnsafeEnchantment(Enchantment.DURABILITY, 3);
                  lore = new ArrayList();
                  meta.setDisplayName(ChatColor.GOLD + "Hermes Boots");
                  lore.add(ChatColor.DARK_RED + "Infused with Speed");
                  meta.setLore(lore);
                  ethereals.setItemMeta(meta);
                  p.getInventory().addItem(new ItemStack[]{ethereals});
               }
            }
         }

         return true;
      } else {
         return false;
      }
   }
}
