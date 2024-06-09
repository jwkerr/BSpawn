package net.earthmc.bspawn.listener;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.event.NewDayEvent;
import com.palmergames.bukkit.towny.object.Town;
import net.earthmc.bspawn.manager.TownMetadataManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.time.Instant;

public class NewDayListener implements Listener {
    private final FileConfiguration config;

    public NewDayListener(FileConfiguration config) {
        this.config = config;
    }

    @EventHandler
    public void onNewDay(NewDayEvent event) {
        for (Town town : TownyAPI.getInstance().getTowns()) {
            if (!TownMetadataManager.getCanOutsidersSpawn(town))
                continue;

            Long toggled = TownMetadataManager.getToggledPublicOnAt(town);
            if (toggled == null || Instant.now().getEpochSecond() - toggled < 86400)
                continue;

            if (town.getAccount().getHoldingBalance() >= config.getDouble("upkeepCost")) {
                town.getAccount().withdraw(config.getDouble("upkeepCost"), "Outsiders can spawn status upkeep.");
            } else {
                TownMetadataManager.setCanOutsidersSpawn(town, false);
                TownMetadataManager.setToggledPublicOnAt(town, null);
            }
        }
    }
}
