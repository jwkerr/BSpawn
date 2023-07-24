package net.xbyz.bspawn.events;

import com.palmergames.bukkit.towny.confirmations.Confirmation;
import com.palmergames.bukkit.towny.confirmations.ConfirmationTransaction;
import com.palmergames.bukkit.towny.event.town.toggle.TownTogglePublicEvent;
import com.palmergames.bukkit.towny.object.Town;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class TownTogglePublic implements Listener {
    private final FileConfiguration config;

    public TownTogglePublic(FileConfiguration config) {
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
                    .setTitle("You must pay to toggle public to true. This will cost " + config.getInt("cost") + " gold.")
                    .setCost(new ConfirmationTransaction(() -> config.getDouble("cost"), town.getAccount(), "Cost of toggling public to true."))
                    .sendTo(event.getSender());
        }
    }
}
