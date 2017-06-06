package be.feeps.epicpets.api;

import be.feeps.epicpets.EpicPetsPlayer;
import be.feeps.epicpets.pets.DefaultPet;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Created by feeps on 05/06/2017.
 */
public class EpicPlayer {

    private EpicPetsPlayer epicPetsPlayer;
    private DefaultPet pet;

    public EpicPlayer(Player player)
    {
        this.epicPetsPlayer = EpicPetsPlayer.instanceOf(player);
    }

    public EpicPetsPlayer getEpicPlayer()
    {
        /**
         * With that you can have a lot of features.
         *
         * @1 You can get the pet:
         *      this.getEpicPlayer().getPet();
         *      @Return null if the player don't have pet.
         *
         * @2 You can remove the pet:
         *      this.getEpicPlayer().removePet();
         *      @Do nothing if the player don't have pet.
         *
         * @3 You can set an animation:
         *      this.getEpicPlayer().setAnim(new EpicAnimations(this.getEpicPlayer().getPlayer(), "YourFile"));
         *
         * @4 You can get the current inventory of the player.
         *      this.getEpicPlayer().getInventory();
         *      @Return an object EpicInventory
         *      Example: this.getEpicPlayer().getInventory().getInv(); @For get the Bukkit inventory
         *
         * @5 You can get the Bukkit player:
         *      this.getEpicPlayer().getPlayer();
         *      @Return an object Player
         */

        return this.epicPetsPlayer;
    }

    public void createPet()
    {
        this.pet = new DefaultPet(this.epicPetsPlayer.getPlayer());
        /**
         * Or we can use:
         *      this.epicPetsPlayer.setPet(new DefaultPet(this.epicPetsPlayer.getPlayer()));
         */
    }

    public DefaultPet getPet(){
        /**
         * Or we can use:
         *      this.epicPetsPlayer.getPet();
         *
         * With that you have all features of a Pet
         *
         * @1 You can get the location of the pet:
         *      this.getPet().getPetLoc();
         */

        this.getPet().getPetLoc();
        return this.epicPetsPlayer.getPet();
    }

    /**
     * EXAMPLE
     */
    private class Example
    {
        private EpicPlayer epicPets;

        public Example()
        {
            //You can create a pet with that.
            this.epicPets.createPet();
            //Or
            this.epicPets.getEpicPlayer().setPet(new DefaultPet(this.epicPets.epicPetsPlayer.getPlayer()));

            //You can equip the pet with getting the armorstand.
            this.epicPets.getPet().getArmorStand().setChestplate(new ItemStack(Material.CHAINMAIL_CHESTPLATE));

        }
    }
}

