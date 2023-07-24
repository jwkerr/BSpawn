package net.xbyz.bspawn.events;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.event.NewDayEvent;
import com.palmergames.bukkit.towny.object.Town;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class NewDay implements Listener {
    private final FileConfiguration config;

    public NewDay(FileConfiguration config) {
        this.config = config;
    }

    @EventHandler
    public void onNewDay(NewDayEvent event) {
        for (Town town : TownyAPI.getInstance().getTowns()) {
            if (town.isPublic() && town.getAccount().getHoldingBalance() >= config.getDouble("cost")) {
                town.getAccount().withdraw(config.getDouble("cost"), "Public status upkeep.");
            } else {
                town.setPublic(false);
            }
        }
    }
}
