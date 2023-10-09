package net.earthmc.bspawn.config;

import org.bukkit.configuration.file.FileConfiguration;

public class Config {
    public static void init(FileConfiguration config) {
        config.addDefault("toggleCost", 5.0);
        config.addDefault("upkeepCost", 5.0);

        config.options().copyDefaults(true);

    }
}
