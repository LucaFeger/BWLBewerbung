package de.bergwerklabs.bewerbung.listeners;

import de.bergwerklabs.bewerbung.Gadgets;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.FallingBlock;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.util.Vector;

public class EntityListeners implements Listener {

    @EventHandler
    public void onBlockFall(EntityChangeBlockEvent event) {
        if(event.getEntity().hasMetadata("gadgetBlock")) {
            Block block = event.getBlock();
            block.getWorld().createExplosion(block.getLocation().getX(), block.getLocation().getY(), block.getLocation().getZ(), 5F, false, false);
            block.getWorld().strikeLightningEffect(block.getLocation());

            FallingBlock fallingBlock = block.getWorld().spawnFallingBlock(block.getLocation().clone().add(0, 1.5, 0), Material.DIAMOND_BLOCK, (byte) 0);
            fallingBlock.setVelocity(new Vector().setX(0.5).setY(1.5).multiply(0.8D));
            fallingBlock.setDropItem(false);
            fallingBlock.setMetadata("gadgetBlock2", new FixedMetadataValue(Gadgets.getInstance(), true));

            FallingBlock fallingBlock2 = block.getWorld().spawnFallingBlock(block.getLocation().clone().add(0, 1.5, 0), Material.DIAMOND_BLOCK, (byte) 0);
            fallingBlock2.setVelocity(new Vector().setX(-0.5).setY(1.5).multiply(0.8D));
            fallingBlock2.setDropItem(false);
            fallingBlock2.setMetadata("gadgetBlock2", new FixedMetadataValue(Gadgets.getInstance(), true));

            FallingBlock fallingBlock3 = block.getWorld().spawnFallingBlock(block.getLocation().clone().add(0, 1.5, 0), Material.DIAMOND_BLOCK, (byte) 0);
            fallingBlock3.setVelocity(new Vector().setZ(0.5).setY(1.5).multiply(0.8D));
            fallingBlock3.setDropItem(false);
            fallingBlock3.setMetadata("gadgetBlock2", new FixedMetadataValue(Gadgets.getInstance(), true));

            FallingBlock fallingBlock4 = block.getWorld().spawnFallingBlock(block.getLocation().clone().add(0, 1.5, 0), Material.DIAMOND_BLOCK, (byte) 0);
            fallingBlock4.setVelocity(new Vector().setZ(-0.5).setY(1.5).multiply(0.8D));
            fallingBlock4.setDropItem(false);
            fallingBlock4.setMetadata("gadgetBlock2", new FixedMetadataValue(Gadgets.getInstance(), true));

            Bukkit.getScheduler().runTaskLater(Gadgets.getInstance(), () -> block.setType(Material.AIR), 2);
        } else if (event.getEntity().hasMetadata("gadgetBlock2")) {
            Block block = event.getBlock();
            block.getWorld().strikeLightningEffect(block.getLocation());

            Bukkit.getScheduler().runTaskLater(Gadgets.getInstance(), () -> block.setType(Material.AIR), 2);
        }
    }

}
