package net.xbyz.bspawn.listeners;

import com.palmergames.bukkit.towny.TownyEconomyHandler;
import com.palmergames.bukkit.towny.confirmations.Confirmation;
import com.palmergames.bukkit.towny.confirmations.ConfirmationTransaction;
import com.palmergames.bukkit.towny.event.town.toggle.TownTogglePublicEvent;
import com.palmergames.bukkit.towny.object.Town;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class TownTogglePublicListener implements Listener {
    private final FileConfiguration config;

    public TownTogglePublicListener(FileConfiguration config) {
        this.config = config;
    }

    @EventHandler
    public void onTownTogglePublic(TownTogglePublicEvent event) {
        if (!event.isAdminAction() && event.getFutureState()) {
            event.setCancelMessage("");
            event.setCancelled(true);
            Town town = event.getTown();

            Confirmation
                    .runOnAccept(() -> town.setPublic(true))
                    .setTitle("You must pay to toggle public to true. This will cost " + TownyEconomyHandler.getFormattedBalance(config.getInt("toggleCost")) + " and will require " + TownyEconomyHandler.getFormattedBalance(config.getInt("upkeepCost")) + " daily upkeep to remain true.")
                    .setCost(new ConfirmationTransaction(() -> config.getDouble("toggleCost"), town.getAccount(), "Cost of toggling public to true."))
                    .sendTo(event.getSender());
        }
    }
}
