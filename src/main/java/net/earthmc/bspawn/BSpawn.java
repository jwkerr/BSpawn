package net.earthmc.bspawn;

import com.palmergames.bukkit.towny.TownyCommandAddonAPI;
import net.earthmc.bspawn.command.TownOutsidersCanSpawnCommand;
import net.earthmc.bspawn.config.Config;
import net.earthmc.bspawn.listener.NewDayListener;
import net.earthmc.bspawn.listener.TownSpawnListener;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class BSpawn extends JavaPlugin {
    private final FileConfiguration config = getConfig();

    @Override
    public void onEnable() {
        Config.init(config);
        saveConfig();

        registerCommands();
        registerListeners();
    }

    private void registerCommands() {
        TownyCommandAddonAPI.addSubCommand(TownyCommandAddonAPI.CommandType.TOWN_TOGGLE, "outsiderscanspawn", new TownOutsidersCanSpawnCommand(config));
    }

    private void registerListeners() {
        getServer().getPluginManager().registerEvents(new TownSpawnListener(), this);
        getServer().getPluginManager().registerEvents(new NewDayListener(config), this);
    }
}
