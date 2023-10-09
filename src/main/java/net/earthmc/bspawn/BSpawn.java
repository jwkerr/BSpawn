package net.earthmc.bspawn;

import net.earthmc.bspawn.command.ToggleAllFalseCommand;
import net.earthmc.bspawn.config.Config;
import net.earthmc.bspawn.listener.TownTogglePublicListener;
import net.earthmc.bspawn.listener.NewDayListener;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class BSpawn extends JavaPlugin {
    private final FileConfiguration config = getConfig();

    @Override
    public void onEnable() {
        Config.init(config);
        saveConfig();

        getServer().getPluginManager().registerEvents(new TownTogglePublicListener(config), this);
        getServer().getPluginManager().registerEvents(new NewDayListener(config), this);

        getCommand("toggleallfalse").setExecutor(new ToggleAllFalseCommand());

        getLogger().info("BSpawn enabled");
    }

    @Override
    public void onDisable() {
        getLogger().info("BSpawn disabled");
    }
}
