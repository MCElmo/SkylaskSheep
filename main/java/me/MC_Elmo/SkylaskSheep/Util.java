package me.MC_Elmo.SkylaskSheep;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Elom on 1/21/17.
 */
public class Util
{
    private SkylaskSheep plugin;
    private FileConfiguration config;
    public Util(SkylaskSheep plugin)
    {
        this.plugin = plugin;
        this.config = plugin.getPluginConifg();
    }

    public ItemStack getDye(DyeColor color)
    {
        config = plugin.getPluginConifg();
        int amount = 1;
        List<String> amounts = config.getStringList("SkylaskSheep.Shear drop amounts");
        HashMap<Integer, Double> amountHash= new HashMap<Integer, Double>();
        for(String a : amounts)
        {
            try
            {
                if(a.contains(":"))
                {
                    String[] arr = a.split(":");
                    if(arr.length == 2)
                    {
                        amountHash.put(Integer.valueOf(arr[0]), Double.valueOf(arr[1].replaceAll("%", "")));
                    }
                }
            }catch (NumberFormatException e)
            {

            }
        }
        int total = 0;
        for(Double d: amountHash.values())
        {
            total += d;
        }

        if(total != 100)
        {
            amount = 0;
        }else
        {
            Double random = Math.random() * 100;
            double prev = 0;
            for(int i : amountHash.keySet())
            {
                if(random < amountHash.get(i) + prev)
                {
                    amount = i;
                    break;
                }
                prev += amountHash.get(i);
            }
        }

        switch (color)
        {
            case RED:
                return new ItemStack(Material.RED_ROSE,amount,(short)1);
            case GREEN:
                return new ItemStack(Material.INK_SACK,amount,(short)2);
            case BROWN:
                return new ItemStack(Material.INK_SACK,amount,(short)3);
            case BLUE:
                return new ItemStack(Material.INK_SACK,amount,(short)4);
            case PURPLE:
                return new ItemStack(Material.INK_SACK,amount,(short)5);
            case CYAN:
                return new ItemStack(Material.INK_SACK,amount,(short)6);
            case SILVER:
                return new ItemStack(Material.INK_SACK,amount,(short)7);
            case GRAY:
                return new ItemStack(Material.INK_SACK,amount,(short)8);
            case PINK:
                return new ItemStack(Material.INK_SACK,amount,(short)9);
            case LIME:
                return new ItemStack(Material.INK_SACK,amount,(short)10);
            case YELLOW:
                return new ItemStack(Material.INK_SACK,amount,(short)11);
            case LIGHT_BLUE:
                return new ItemStack(Material.INK_SACK,amount,(short)12);
            case MAGENTA:
                return new ItemStack(Material.INK_SACK,amount,(short)13);
            case ORANGE:
                return new ItemStack(Material.INK_SACK,amount,(short)14);
            default:
                return null;

        }
    }

    public ItemStack getDyeDeath(DyeColor color)
    {
        config = plugin.getPluginConifg();
        int amount = 1;
        List<String> amounts = config.getStringList("SkylaskSheep.Death drop amounts");
        HashMap<Integer, Double> amountHash= new HashMap<Integer, Double>();
        for(String a : amounts)
        {
            try
            {
                if(a.contains(":"))
                {
                    String[] arr = a.split(":");
                    if(arr.length == 2)
                    {
                        amountHash.put(Integer.valueOf(arr[0]), Double.valueOf(arr[1].replaceAll("%", "")));
                    }
                }
            }catch (NumberFormatException  e)
            {

            }
        }
        int total = 0;
        for(Double d: amountHash.values())
        {
            total += d;
        }

        if(total != 100)
        {
            amount = 0;
        }else
        {
            Double random = Math.random() * 100;
            double prev = 0;
            for(int i : amountHash.keySet())
            {

                if(random < amountHash.get(i) + prev)
                {
                    amount = i;
                    break;
                }
                prev += amountHash.get(i);
            }
        }
        switch (color)
        {
            case RED:
                return new ItemStack(Material.RED_ROSE,amount,(short)1);
            case GREEN:
                return new ItemStack(Material.INK_SACK,amount,(short)2);
            case BROWN:
                return new ItemStack(Material.INK_SACK,amount,(short)3);
            case BLUE:
                return new ItemStack(Material.INK_SACK,amount,(short)4);
            case PURPLE:
                return new ItemStack(Material.INK_SACK,amount,(short)5);
            case CYAN:
                return new ItemStack(Material.INK_SACK,amount,(short)6);
            case SILVER:
                return new ItemStack(Material.INK_SACK,amount,(short)7);
            case GRAY:
                return new ItemStack(Material.INK_SACK,amount,(short)8);
            case PINK:
                return new ItemStack(Material.INK_SACK,amount,(short)9);
            case LIME:
                return new ItemStack(Material.INK_SACK,amount,(short)10);
            case YELLOW:
                return new ItemStack(Material.INK_SACK,amount,(short)11);
            case LIGHT_BLUE:
                return new ItemStack(Material.INK_SACK,amount,(short)12);
            case MAGENTA:
                return new ItemStack(Material.INK_SACK,amount,(short)13);
            case ORANGE:
                return new ItemStack(Material.INK_SACK,amount,(short)14);
            default:
                return null;

        }
    }
}
