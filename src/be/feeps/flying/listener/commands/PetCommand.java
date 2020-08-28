package be.feeps.flying.listener.commands;

import be.feeps.flying.FlyingPlayer;
import be.feeps.flying.Main;
import be.feeps.flying.inventories.MainInventory;
import be.feeps.flying.utilities.FlyingUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PetCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)){
            sender.sendMessage("Only players can use this command !");
            return true;
        }
        Player player = (Player) sender;
        FlyingPlayer fPlayer = FlyingPlayer.instanceOf(player);

        if (args.length != 1){
            player.sendMessage(FlyingUtils.toColorable(new String[]{
                    "&m         &r &6&lFlyingPets &8&o" + Main.getInstance().getDescription().getVersion() + "&7 by Feeps &r&m         ",
                    "/fpets &ospawn",
                    "/fpets &oremove",
                    "/fpets &ogui",
            }));
            return true;
        }

        switch (args[0].toLowerCase()){
            case "spawn":
                fPlayer.getPetManager().spawn();
                break;
            case "remove":
                fPlayer.getPetManager().remove();
                break;
            case "gui":
                if (Main.getInstance().getConfig().getBoolean("this-server.allow-editing"))
                    new MainInventory(player).open();
                break;
        }

        return true;
    }


}