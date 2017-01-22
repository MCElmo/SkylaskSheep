package me.MC_Elmo.SkylaskSheep;

import me.MC_Elmo.SkylaskSheep.listeners.SheepSpawn;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

/**
 * Created by Elom on 1/20/17.
 */
public class SkylaskSheep extends JavaPlugin implements CommandExecutor
{
    private FileConfiguration config;
    private String prefix;
    private String title;
    private PluginDescriptionFile pdfFile;
    private PluginManager pm;
    private Util util;

    @Override
    public void onEnable()
    {
        super.onEnable();
        util = new Util(this);
        loadConfig();
        if(config.getBoolean("SkylaskSheep.enabled"))
        {
            this.pdfFile = super.getDescription();
            this.pm = this.getServer().getPluginManager();
            registerCommands();
            registerEvents();
        }
        else
        {
            getLogger().log(Level.INFO,"Plugin not enabled in config.yml");
            getServer().getPluginManager().disablePlugin(this);
        }
        prefix = "SkylaskSheep";
        prefix = ChatColor.RED + "["+ ChatColor.GREEN + prefix + ChatColor.RED + "]" + ChatColor.RESET;
        this.title =  ChatColor.STRIKETHROUGH + "-----" + ChatColor.RESET + prefix + ChatColor.RESET + ChatColor.STRIKETHROUGH + "-----";
    }

    public Util getUtil(){
        return util;
    }
    public void registerEvents() {
        pm.registerEvents(new SheepSpawn(this), this);
    }
    private void registerCommands() {
        this.getCommand("skylasksheep").setExecutor(this);
    }
    private void loadConfig()
    {
        config = getConfig();
        config.options().copyDefaults(true);
        saveConfig();
    }
    public FileConfiguration getPluginConifg()
    {
        if(this.config == null)
            loadConfig();
        return this.config;
    }

    public PluginDescriptionFile getPdfFile() {
        return this.pdfFile;
    }
    public void reload() {
        saveConfig();
        pm.disablePlugin(this);
        pm.enablePlugin(this);
        loadConfig();
    }
    public String getPrefix()
    {
        prefix = "&aSkylask&2Sheep";
        prefix = ChatColor.WHITE + "[" + prefix + ChatColor.WHITE + "]";
        prefix = ChatColor.translateAlternateColorCodes('&', prefix);
        return this.prefix;
    }
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
    {
        if(!sender.hasPermission("skylasksheep.reload"))
        {
            sender.sendMessage(title);
            sender.sendMessage(ChatColor.GREEN + "Author: " + ChatColor.GOLD +  "MC_Elmo");
            sender.sendMessage(ChatColor.GREEN + "Alias: " + ChatColor.GREEN +  " [/ss] ");
            sender.sendMessage(ChatColor.GREEN + "Version: " + ChatColor.GREEN +  pdfFile.getVersion());
            sender.sendMessage(ChatColor.GREEN + "Description: " + ChatColor.DARK_GREEN + "Spawn configurable colored sheep");
            sender.sendMessage(ChatColor.STRIKETHROUGH + "--------------------");
            return false;
        }

        if(args.length != 1)
        {
            sender.sendMessage(prefix + ChatColor.RED + "Invalid arguments! Try /ss help");
            return false;
        }




        if(args[0].equalsIgnoreCase("help"))
        {
                sender.sendMessage(title);
                sender.sendMessage(ChatColor.GREEN + "/ss reload - " + ChatColor.DARK_GREEN + " Reload config.");
            return true;
        }

        if(args[0].equalsIgnoreCase("reload"))
        {
            reloadConfig();
            config = getConfig();
            saveConfig();
            sender.sendMessage(prefix + ChatColor.GREEN + "Successfully reloaded config.");
            return true;
        }
        sender.sendMessage(prefix + ChatColor.RED + "Invalid argument! Try /ss help");
        return false;
    }
}
