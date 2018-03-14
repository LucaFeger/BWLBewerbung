package de.bergwerklabs.bewerbung.gadget;

import org.bukkit.inventory.ItemStack;

public interface IGadget {

    String getName();

    ItemStack getItem();

    int getCooldown();

    IGadgetListener onClick();

    boolean runAsync();

}
