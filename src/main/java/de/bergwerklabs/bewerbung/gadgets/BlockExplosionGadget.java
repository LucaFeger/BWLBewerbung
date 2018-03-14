package de.bergwerklabs.bewerbung.gadgets;

import de.bergwerklabs.bewerbung.Gadgets;
import de.bergwerklabs.bewerbung.gadget.IGadget;
import de.bergwerklabs.bewerbung.gadget.IGadgetListener;
import de.bergwerklabs.bewerbung.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.FallingBlock;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.util.Vector;

public class BlockExplosionGadget implements IGadget {

    ItemStack item;

    public BlockExplosionGadget() {
        Gadgets.getInstance().getGadgetManager().registerGadget(this);
    }

    @Override
    public String getName() {
        return "§cBlock Explosion";
    }

    @Override
    public ItemStack getItem() {
        if(item == null)
            item = new ItemBuilder(Material.DIAMOND_BLOCK).setDisplayName(getName()).build();

        return item;
    }

    @Override
    public int getCooldown() {
        return 10;
    }

    @Override
    public IGadgetListener onClick() {
        return (player -> {
            FallingBlock fallingBlock = player.getWorld().spawnFallingBlock(player.getLocation(), getItem().getType(), (byte) 0);
            fallingBlock.setVelocity(player.getLocation().getDirection().add(new Vector().setY(0.1)).multiply(1.3D));
            fallingBlock.setDropItem(false);
            fallingBlock.setMetadata("gadgetBlock", new FixedMetadataValue(Gadgets.getInstance(), true));
        });
    }

    @Override
    public boolean runAsync() {
        return false;
    }
}
