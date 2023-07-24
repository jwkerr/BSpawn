package net.xbyz.bspawn;

import net.xbyz.bspawn.events.NewDay;
import net.xbyz.bspawn.events.TownTogglePublic;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {
    private final FileConfiguration config = getConfig();

    @Override
    public void onEnable() {
        config.addDefault("cost", 5.0);
        config.options().copyDefaults(true);
        saveConfig();

        getServer().getPluginManager().registerEvents(new TownTogglePublic(config), this);
        getServer().getPluginManager().registerEvents(new NewDay(config), this);

        getLogger().info("BSpawn enabled");
    }

    @Override
    public void onDisable() {
        getLogger().info("BSpawn disabled");
    }
}
