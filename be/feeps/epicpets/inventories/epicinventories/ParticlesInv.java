package be.feeps.epicpets.inventories.epicinventories;

import be.feeps.epicpets.EpicPermissions;
import be.feeps.epicpets.Main;
import be.feeps.epicpets.inventories.EpicInventories;
import be.feeps.epicpets.particles.ParticlesList.*;
import be.feeps.epicpets.utils.MessageUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Created by feeps on 18/06/2017.
 */
public class ParticlesInv extends EpicInventories {
    public ParticlesInv(Player player){
        super(player, MessageUtil.translate(Main.getI().getMsgCfg().invNameParticles), 27);
        this.create();
    }

    @Override
    public void create() {
        this.setItem(new ItemStack(Material.APPLE), 11, MessageUtil.translate(Main.getI().getMsgCfg().invParticles.get("Love")), null);
        this.setItem(new ItemStack(Material.ENDER_PEARL), 12 , MessageUtil.translate(Main.getI().getMsgCfg().invParticles.get("Ring")), null);
        this.setItem(new ItemStack(Material.EMERALD), 13, MessageUtil.translate(Main.getI().getMsgCfg().invParticles.get("Sparks")), null);
        this.setItem(new ItemStack(Material.REDSTONE), 14 , MessageUtil.translate(Main.getI().getMsgCfg().invParticles.get("BloodHelix")), null);
        this.setItem(new ItemStack(Material.SNOW_BALL), 15, MessageUtil.translate(Main.getI().getMsgCfg().invParticles.get("FrostLord")), null);

        this.setItem(new ItemStack(Material.ARROW), 22, MessageUtil.translate(Main.getI().getMsgCfg().invParticles.get("Back")), null);

        if(epicPetsPlayer.getEpicParticles() != null){
            this.setItem(new ItemStack(Material.BARRIER), 26 , MessageUtil.translate(Main.getI().getMsgCfg().invParticles.get("Reset")), null);
        }
    }

    @Override
    public void onClick(ItemStack current, int slot) {
        if(current.getType() == Material.ARROW)
            new MainInv(player).openInv();

        if(epicPetsPlayer.getPet() != null){
            switch(current.getType()){
                case APPLE:
                    if(EpicPermissions.PARTICLESLOVE.hasPerm(player)){
                        new ParticlesLove(player);
                        player.closeInventory();
                    }
                    break;
                case ENDER_PEARL:
                    if(EpicPermissions.PARTICLESRING.hasPerm(player)){
                        new ParticlesRing(player);
                        player.closeInventory();
                    }
                    break;
                case EMERALD:
                    if(EpicPermissions.PARTICLESSPARKS.hasPerm(player)){
                        new ParticlesSparks(player);
                        player.closeInventory();
                    }
                    break;
                case REDSTONE:
                    if(EpicPermissions.PARTICLESBLOODHELIX.hasPerm(player)){
                        new ParticlesHelix(player);
                        player.closeInventory();
                    }
                    break;
                case SNOW_BALL:
                    if(EpicPermissions.PARTICLESFROSTLORD.hasPerm(player)){
                        new ParticlesFrostLord(player);
                        player.closeInventory();
                    }
                    break;
                case BARRIER:
                    if(epicPetsPlayer.getEpicParticles() != null){
                        epicPetsPlayer.getEpicParticles().stop();
                        this.getInv().clear(26);
                    }
                    break;
            }
        }
    }
}
