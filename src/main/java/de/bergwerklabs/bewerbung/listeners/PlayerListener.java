package de.bergwerklabs.bewerbung.listeners;

import de.bergwerklabs.bewerbung.Gadgets;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerListener implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if(event.getItem() != null && Gadgets.getInstance().getGadgetManager().getByItem(event.getItem()) != null) {
                Gadgets.getInstance().getGadgetManager().runGadgetClick(event.getPlayer(), event.getItem());
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        if(event.getPlayer().hasMetadata("parachute")) {
            boolean active = event.getPlayer().getMetadata("parachute").get(0).asBoolean();
            if(active) {
                event.setTo(event.getFrom().clone().add(0, -0.3, 0));
            }
        }
    }

}
