package be.feeps.epicpets.inventories.epicpetsinv;

import be.feeps.epicpets.EpicPermissions;
import be.feeps.epicpets.EpicPetsPlayer;
import be.feeps.epicpets.Main;
import be.feeps.epicpets.inventories.EpicInventory;
import be.feeps.epicpets.particles.ParticlesList.*;
import be.feeps.epicpets.utils.MessageUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * Created by feeps on 08/06/2017.
 */
public class ParticlesInv extends EpicInventory {

    public ParticlesInv(){
        super(null);
    }

    @Override
    public String name() {
        return MessageUtil.translate(Main.getI().getMsgCfg().invNameParticles);
    }

    @Override
    public int size() {
        return 27;
    }

    @Override
    public void contents(Player player, Inventory inv) {
        EpicPetsPlayer epicPetsPlayer = EpicPetsPlayer.instanceOf(player);
        this.setItem(new ItemStack(Material.APPLE), 11, MessageUtil.translate(Main.getI().getMsgCfg().invParticles.get("Love")), null);
        this.setItem(new ItemStack(Material.ENDER_PEARL), 12 , MessageUtil.translate(Main.getI().getMsgCfg().invParticles.get("Ring")), null);
        this.setItem(new ItemStack(Material.EMERALD), 13, MessageUtil.translate(Main.getI().getMsgCfg().invParticles.get("Sparks")), null);
        this.setItem(new ItemStack(Material.REDSTONE), 14 , MessageUtil.translate(Main.getI().getMsgCfg().invParticles.get("BloodHelix")), null);
        this.setItem(new ItemStack(Material.SNOW_BALL), 15, MessageUtil.translate(Main.getI().getMsgCfg().invParticles.get("FrostLord")), null);

        this.setItem(new ItemStack(Material.ARROW), 22, MessageUtil.translate(Main.getI().getMsgCfg().invParticles.get("Back")), null);
        this.setItem(new ItemStack(Material.BARRIER), 26 , MessageUtil.translate(Main.getI().getMsgCfg().invParticles.get("Reset")), null);

    }

    @Override
    public void onClick(Player player, Inventory inv, ItemStack current, int slot) {
        EpicPetsPlayer epicPetsPlayer = EpicPetsPlayer.instanceOf(player);
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
            case ARROW:
                new MainInventory().open(player);
                break;
            case BARRIER:
                if(epicPetsPlayer.getEpicParticles() != null){
                    epicPetsPlayer.getEpicParticles().stop();
                }
                break;
        }
    }
}
