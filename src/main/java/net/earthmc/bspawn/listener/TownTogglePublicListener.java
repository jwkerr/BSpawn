package net.earthmc.bspawn.listener;

import com.palmergames.bukkit.towny.TownyEconomyHandler;
import com.palmergames.bukkit.towny.TownyMessaging;
import com.palmergames.bukkit.towny.confirmations.Confirmation;
import com.palmergames.bukkit.towny.confirmations.ConfirmationTransaction;
import com.palmergames.bukkit.towny.event.town.toggle.TownTogglePublicEvent;
import com.palmergames.bukkit.towny.object.Town;
import com.palmergames.bukkit.towny.object.Translatable;
import net.earthmc.bspawn.manager.TownMetadataManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.time.Instant;

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
                    .runOnAccept(() -> {
                        town.setPublic(true);
                        TownMetadataManager.setToggledPublicOnAt(town, Instant.now().getEpochSecond());
                        town.save();

                        TownyMessaging.sendPrefixedTownMessage(town, Translatable.of("msg_changed_public", "enabled"));
                    })
                    .setTitle("You must pay to toggle public to true. This will cost " + TownyEconomyHandler.getFormattedBalance(config.getInt("toggleCost")) + " and will require " + TownyEconomyHandler.getFormattedBalance(config.getInt("upkeepCost")) + " daily upkeep each new day to remain true.")
                    .setCost(new ConfirmationTransaction(() -> config.getDouble("toggleCost"), town.getAccount(), "Cost of toggling public to true."))
                    .sendTo(event.getSender());
        }
    }
}
