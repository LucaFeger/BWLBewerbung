package de.bergwerklabs.bewerbung.gadgets;

import de.bergwerklabs.bewerbung.Gadgets;
import de.bergwerklabs.bewerbung.gadget.IGadget;
import de.bergwerklabs.bewerbung.gadget.IGadgetListener;
import de.bergwerklabs.bewerbung.utils.ItemBuilder;
import org.bukkit.*;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityUnleashEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RocketLauncherGadget implements IGadget, Listener {

    private ItemStack item;
    private Random random;
    private List<Chicken> chickens;

    public RocketLauncherGadget() {
        Gadgets.getInstance().getGadgetManager().registerGadget(this);
        this.random = new Random();
        this.chickens = new ArrayList<>();
    }

    @Override
    public String getName() {
        return "§aRocket Launcher";
    }

    @Override
    public ItemStack getItem() {
        if(item == null)
            item = new ItemBuilder(Material.FIREWORK).setDisplayName(getName()).build();

        return item;
    }

    @Override
    public int getCooldown() {
        return 20;
    }

    @Override
    public IGadgetListener onClick() {
        return player -> {
            Firework firework = (Firework) player.getWorld().spawnEntity(player.getLocation(), EntityType.FIREWORK);
            FireworkMeta fireworkMeta = firework.getFireworkMeta();

            FireworkEffect fireworkEffect = FireworkEffect.builder().flicker(true).trail(true).with(FireworkEffect.Type.BURST).withColor(Color.AQUA, Color.RED, Color.BLUE).build();
            fireworkMeta.addEffect(fireworkEffect);
            fireworkMeta.setPower(2);
            firework.setFireworkMeta(fireworkMeta);

            firework.setPassenger(player);

            Bukkit.getScheduler().runTaskLater(Gadgets.getInstance(), () -> {
                player.setMetadata("parachute", new FixedMetadataValue(Gadgets.getInstance(), true));
                for (int i = 0; i < 10; i++) {
                    Chicken chicken = (Chicken) player.getWorld().spawnEntity(player.getLocation().add(randomDouble(0, 1), 3, randomDouble(0, 1)), EntityType.CHICKEN);
                    chickens.add(chicken);
                    chicken.setLeashHolder(player);
                }
            }, 20*2);
        };
    }

    @EventHandler
    public void onLeashBreak(EntityUnleashEvent event) {
        if (chickens.contains(event.getEntity())) {
            event.getEntity().getNearbyEntities(1, 1, 1).stream().filter(entity -> entity instanceof Item
                    && ((Item) entity).getItemStack().getType() == Material.LEASH).forEach(Entity::remove);
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        if(event.getPlayer().hasMetadata("parachute")) {
            boolean active = event.getPlayer().getMetadata("parachute").get(0).asBoolean();
            if(active) {
                Location to = event.getFrom().clone().add(0, -0.3, 0);
                to.setYaw(event.getTo().getYaw());
                to.setPitch(event.getTo().getPitch());

                event.setTo(to);
            }
            if(event.getPlayer().getLocation().getBlock().getRelative(BlockFace.DOWN).getType() != Material.AIR) {
                for (Chicken chicken : chickens) {
                    chicken.setLeashHolder(null);
                    chicken.remove();
                }
                event.getPlayer().setMetadata("parachute", new FixedMetadataValue(Gadgets.getInstance(), false));
            }
        }
    }

    @Override
    public boolean runAsync() {
        return false;
    }

    private double randomDouble(double first, double second) {
        return first + (second - second) * random.nextDouble();
    }

}
