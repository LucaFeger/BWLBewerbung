package de.bergwerklabs.bewerbung;

import de.bergwerklabs.bewerbung.gadgets.BlockExplosionGadget;
import de.bergwerklabs.bewerbung.gadgets.RocketLauncherGadget;
import de.bergwerklabs.bewerbung.listeners.EntityListeners;
import de.bergwerklabs.bewerbung.listeners.PlayerListener;
import de.bergwerklabs.bewerbung.gadget.GadgetManager;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Gadgets extends JavaPlugin {

    @Getter
    private static Gadgets instance;
    @Getter
    private String prefix = "§aGadgets §7»";

    @Getter
    private GadgetManager gadgetManager;

    @Override
    public void onEnable() {
        instance = this;

        this.gadgetManager = new GadgetManager();
        Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
        Bukkit.getPluginManager().registerEvents(new EntityListeners(), this);
        Bukkit.getPluginManager().registerEvents(new RocketLauncherGadget(), this);

        //INITIALIZE GADGETS
        new BlockExplosionGadget();
        new RocketLauncherGadget();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
