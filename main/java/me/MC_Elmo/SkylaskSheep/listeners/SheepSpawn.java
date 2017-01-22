package me.MC_Elmo.SkylaskSheep.listeners;

import me.MC_Elmo.SkylaskSheep.SkylaskSheep;
import me.MC_Elmo.SkylaskSheep.Util;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Sheep;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerShearEntityEvent;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Elom on 1/20/17.
 */
public class SheepSpawn implements Listener
{
    private SkylaskSheep plugin;
    private FileConfiguration config;
    private Util util;
    public SheepSpawn(SkylaskSheep plugin)
    {
        this.plugin = plugin;
        this.config = plugin.getPluginConifg();
        util = plugin.getUtil();
    }
    @EventHandler
    public void onSheer(PlayerShearEntityEvent e)
    {
        config = plugin.getPluginConifg();
        if(!(e.getEntity() instanceof Sheep))
        {
            return;
        }
        final Sheep s = (Sheep) e.getEntity();
        if(config.getBoolean("SkylaskSheep.Drop dye on shear"))
        {


            String dyeChance = config.getString("SkylaskSheep.Shear drop chance");
            dyeChance = dyeChance.replaceAll("%", "");
            Double chance;
            try
            {
                chance = Double.valueOf(dyeChance);
            } catch (NumberFormatException ex)
            {
                chance = 0d;
            }


            if (Math.random() * 100 < chance)
            {
                s.getWorld().dropItemNaturally(s.getLocation(), util.getDye(s.getColor()));
                return;
            }
            return;
        }

        if(config.getBoolean("SkylaskSheep.Keep color on shear"))
        {
            final DyeColor c = s.getColor();
            Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable(){
                public void run() {
                    s.setColor(c);
                }
            }, 1L);
        }


    }

    @EventHandler
    public void onDeath(EntityDeathEvent e)
    {
        config = plugin.getPluginConifg();
        if(!(e.getEntity() instanceof Sheep))
        {
            return;
        }
        final Sheep s = (Sheep) e.getEntity();
        if(config.getBoolean("SkylaskSheep.Drop dye on death"))
        {


            String deathChance = config.getString("SkylaskSheep.Death drop chance");
            deathChance =deathChance.replaceAll("%", "");
            Double chance;
            try
            {
                chance = Double.valueOf(deathChance);
            } catch (NumberFormatException ex)
            {
                chance = 0d;
            }


            if (Math.random() * 100 < chance)
            {
               e.getDrops().add(util.getDyeDeath(s.getColor()));
                return;
            }
            return;
        }



    }
    @EventHandler
    public void onSpawn(CreatureSpawnEvent e)
    {
        config = plugin.getPluginConifg();
        if(!(config.getBoolean("SkylaskSheep.enabled")))
            return;

        if(!(e.getEntityType() == EntityType.SHEEP))
            return;

        Sheep s = (Sheep) e.getEntity();

        if(!s.isAdult())
            return;

        List<String> colors =  config.getStringList("SkylaskSheep.Spawn Chance");

        //Custom Sheep Color getting
        if(colors != null)
        {
            HashMap<DyeColor, Double> colorHash = new HashMap<DyeColor, Double>();
            for(String color : colors)
            {
                if (color.contains(":"))
                {
                    String[] split = color.split(":");
                    if (split.length == 2)
                    {
                        try
                        {

                        if(DyeColor.valueOf(split[0].toUpperCase()) == null)
                            return;

                        split[1] = split[1].replaceAll("%", "");

                            colorHash.put(DyeColor.valueOf(split[0].toUpperCase()), Double.valueOf(split[1]));
                        }catch (NumberFormatException ex)
                        {
                            return;
                        }catch (IllegalArgumentException ex)
                        {
                            return;
                        }
                    }
                    else
                    {
                        return;
                    }
                }
            }
            double total = 0;

            for(double i :colorHash.values())
                total += i;

            if(total == 100.0)
            {
                double d = Math.random() * 100;
                int prev = 0;
                for(DyeColor dyeColor : colorHash.keySet())
                {
                    if(d <= colorHash.get(dyeColor) + prev)
                    {
                        s.setColor(dyeColor);
                        return;
                    }
                    prev += colorHash.get(dyeColor);
                }
            }

            return;
        }else{return;}


    }
}
