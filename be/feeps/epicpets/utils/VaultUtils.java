package be.feeps.epicpets.utils;

import be.feeps.epicpets.Main;
import org.bukkit.entity.Player;

/**
 * Created by feeps on 21/06/2017.
 */
public final class VaultUtils {

    //---Economy---

    public static boolean isEconNull(){
        if(Main.getI().getEcon() == null){
            return true;
        }else{
            return false;
        }
    }

    public static boolean withdrawMoney(Player player, double amount){
        if(!isEconNull()){
            if(Main.getI().getEcon().has(player, amount)){
                Main.getI().getEcon().withdrawPlayer(player, amount);
                return true;
            }else{
                player.sendMessage(MessageUtil.translate(Main.getI().getMsgCfg().prefix + Main.getI().getMsgCfg().noMoney));
                return false;
            }
        }else {
            return true;
        }
    }

    public static void depositPlayer(Player player, double amount){
        if(!isEconNull()){
            Main.getI().getEcon().depositPlayer(player, amount);
        }
    }

    //---Permissions---

    public static boolean isPermsNull(){
        if(Main.getI().getPerms() == null){
            return true;
        }else{
            return false;
        }
    }

    public static void addPerms(Player player, String permission){
        if(!isPermsNull()){
            if(!hasPerms(player,permission)){
                Main.getI().getPerms().playerAdd(player, permission);
            }
        }
    }

    public static boolean hasPerms(Player player, String permission){
        if(!isPermsNull()){
            if(Main.getI().getPerms().has(player, permission)){
                return true;
            } else{
                return false;
            }
        }else{
            if(player.hasPermission(permission)){
                return true;
            }else{
                return false;
            }
        }
    }
}
