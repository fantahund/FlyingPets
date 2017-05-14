package be.feeps.epicpets.inventories.epicpetsinv;

import be.feeps.epicpets.EpicPetsPlayer;
import be.feeps.epicpets.Main;
import be.feeps.epicpets.animations.EpicAnimations;
import be.feeps.epicpets.inventories.EpicInventory;
import be.feeps.epicpets.utils.MessageUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * Created by feeps on 16/04/2017.
 */
public class AnimationsInv extends EpicInventory {

    public AnimationsInv(){
        super(null);
    }

    @Override
    public String name() {
        return MessageUtil.translate(Main.getI().getMsgCfg().invNameAnimations);
    }

    @Override
    public int size() {
        return 27;
    }

    @Override
    public void contents(Player player,Inventory inv) {
        addAnimItems();
        this.setItem(new ItemStack(Material.ARROW), 22, MessageUtil.translate(Main.getI().getMsgCfg().invAnimations.get("Back")), null);
        this.setItem(new ItemStack(Material.BARRIER), 26 , MessageUtil.translate(Main.getI().getMsgCfg().invAnimations.get("Reset")), null);
    }

    @Override
    public void onClick(Player player, Inventory inv, ItemStack current, int slot) {
        EpicPetsPlayer epicPetsPlayer = EpicPetsPlayer.instanceOf(player);
        if(current.getType() == Material.ARROW){
            new MainInventory().open(player);
        }

        if(current.getType() == Material.BARRIER){
            if(epicPetsPlayer.getAnim() != null){
                epicPetsPlayer.getAnim().stop();
                this.cache.getData().set(player.getUniqueId().toString() + ".pet.currentanimation", "NULL");
            }
        }
        if(current.getType() != Material.ARROW && current.getType() != Material.BARRIER){
            if(epicPetsPlayer.getAnim() != null){
                epicPetsPlayer.getAnim().stop();
            }
            if(epicPetsPlayer.getPet() != null) {

                File folder = new File(Main.getI().getDataFolder().getAbsolutePath()+"/Animations/");
                File[] listOfFiles = folder.listFiles();

                for (int i = 0; i < listOfFiles.length; i++) {
                    if (listOfFiles[i].isFile()) {
                        BufferedReader br = null;
                        try {
                            br = new BufferedReader(new FileReader(listOfFiles[i]));
                            String line = "";
                            String permission = "";
                            String name = "";

                            while ((line = br.readLine()) != null) {
                                if(line.startsWith("Permission")){
                                    permission = (line.split(" ")[1]);
                                }
                                if(line.startsWith("NameItem")){
                                    name = (line.split(" ")[1]);
                                    if(current.getItemMeta().getDisplayName().equalsIgnoreCase(MessageUtil.translate(name))){
                                        if(player.hasPermission(permission)){
                                            epicPetsPlayer.setAnim(new EpicAnimations(player, listOfFiles[i].getName()));
                                        }else{
                                            player.sendMessage(MessageUtil.translate(Main.getI().getMsgCfg().noPerm));
                                        }
                                    }
                                }
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        } finally {
                            // Toujours être sûr que le stream est bien fermé
                            if (br != null) {
                                try {
                                    br.close();
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                            }
                        }
                    } else if (listOfFiles[i].isDirectory()) {}
                }
            }
        }
    }



    public void addAnimItems() {
        File folder = new File(Main.getI().getDataFolder().getAbsolutePath()+"/Animations/");
        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                BufferedReader br = null;
                try {
                    br = new BufferedReader(new FileReader(listOfFiles[i]));
                    String line = "";

                    String itemType = null;
                    int itemSlot = 0;
                    String itemName = "";

                    while ((line = br.readLine()) != null) {
                        //System.out.print(line);
                        if(line.startsWith("TypeItem")){
                            itemType = (line.split(" ")[1]);
                        }
                        if (line.startsWith("NameItem")) {
                            itemName = (line.split(" ")[1]);
                        }
                        if (line.startsWith("SlotItem")) {
                            itemSlot =  Integer.parseInt((line.split(" ")[1]));
                            this.setItem(new ItemStack(Material.valueOf(itemType)), itemSlot, MessageUtil.translate(itemName), null);
                        }

                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                } finally {
                    // Toujours être sûr que le stream est bien fermé
                    if (br != null) {
                        try {
                            br.close();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }
                //System.out.print(listOfFiles[i].getName());  Nom du fichier
            } else if (listOfFiles[i].isDirectory()) {}
        }
    }
}
