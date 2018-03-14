package de.bergwerklabs.bewerbung.gadget;

import de.bergwerklabs.bewerbung.Gadgets;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GadgetManager {

    private List<IGadget> gadgets;
    private ExecutorService executorService;

    public GadgetManager() {
        this.gadgets = new ArrayList<>();
        this.executorService = Executors.newCachedThreadPool();
    }

    public void registerGadget(IGadget gadget) {
        gadgets.add(gadget);
    }

    public IGadget getByItem(ItemStack itemStack) {
        return gadgets.stream().filter(gadget -> gadget.getItem().getType() == itemStack.getType()).findFirst().orElse(null);
    }

    public boolean itemCanBeUsed(long nextUse) {
        if(nextUse > 0) {
            if(System.currentTimeMillis() >= nextUse) {
                return true;
            } else {
                return false;
            }
        }
        return true;
    }


    public void runGadgetClick(Player player, ItemStack itemStack) {
        long nextUse = 0;

        IGadget gadget = getByItem(itemStack);

        if(player.hasMetadata(gadget.getName())) {
            nextUse = player.getMetadata(gadget.getName()).get(0).asLong();
        }

        if(itemCanBeUsed(nextUse)) {
            player.setMetadata(gadget.getName(), new FixedMetadataValue(Gadgets.getInstance(), System.currentTimeMillis() + (gadget.getCooldown() * 1000)));
            if(gadget.runAsync())
                executorService.execute(() -> gadget.onClick().onEvent(player));
            else
                gadget.onClick().onEvent(player);
        } else {
            player.sendMessage(Gadgets.getInstance().getPrefix() + "§cDu musst noch " + (int) Math.ceil( (nextUse - System.currentTimeMillis()) / 1000) + " Sekunden warten");
        }
    }


}
