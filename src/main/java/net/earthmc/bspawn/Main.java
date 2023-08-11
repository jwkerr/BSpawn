package net.earthmc.bspawn;

import net.earthmc.bspawn.listeners.TownTogglePublicListener;
import net.earthmc.bspawn.listeners.NewDayListener;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {
    private final FileConfiguration config = getConfig();

    @Override
    public void onEnable() {
        config.addDefault("toggleCost", 5.0);
        config.addDefault("upkeepCost", 5.0);
        config.options().copyDefaults(true);
        saveConfig();

        getServer().getPluginManager().registerEvents(new TownTogglePublicListener(config), this);
        getServer().getPluginManager().registerEvents(new NewDayListener(config), this);

        getLogger().info("BSpawn enabled");
    }

    @Override
    public void onDisable() {
        getLogger().info("BSpawn disabled");
    }
}
