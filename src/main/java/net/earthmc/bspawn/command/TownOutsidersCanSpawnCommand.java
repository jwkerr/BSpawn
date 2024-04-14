package net.earthmc.bspawn.command;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.TownyEconomyHandler;
import com.palmergames.bukkit.towny.TownyMessaging;
import com.palmergames.bukkit.towny.confirmations.Confirmation;
import com.palmergames.bukkit.towny.confirmations.ConfirmationTransaction;
import com.palmergames.bukkit.towny.object.Town;
import com.palmergames.bukkit.towny.object.Translatable;
import net.earthmc.bspawn.manager.TownMetadataManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.time.Instant;

public class TownOutsidersCanSpawnCommand implements CommandExecutor {

    private final FileConfiguration config;

    public TownOutsidersCanSpawnCommand(FileConfiguration config) {
        this.config = config;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            TownyMessaging.sendErrorMsg("Only players can use this command");
            return true;
        }

        Town town = TownyAPI.getInstance().getTown(player);
        if (town == null) {
            TownyMessaging.sendErrorMsg("You must be part of a town to use this command");
            return true;
        }

        if (!player.hasPermission("bspawn.command.town.toggle.outsiderscanspawn")) {
            TownyMessaging.sendErrorMsg("You do not have permission to use this command");
            return true;
        }

        if (TownMetadataManager.getCanOutsidersSpawn(town)) { // If its already enabled just disable it and return
            TownMetadataManager.setCanOutsidersSpawn(town, false);
            TownMetadataManager.setToggledPublicOnAt(town, null);
            return true;
        }

        Confirmation
                .runOnAccept(() -> {
                    TownMetadataManager.setCanOutsidersSpawn(town, true);
                    TownMetadataManager.setToggledPublicOnAt(town, Instant.now().getEpochSecond());

                    TownyMessaging.sendPrefixedTownMessage(town, "Outsiders can now teleport to " + town.getName());
                })
                .setTitle("You must pay to allow outsiders to spawn to your town. This will cost " + TownyEconomyHandler.getFormattedBalance(config.getInt("toggleCost")) + " and will require " + TownyEconomyHandler.getFormattedBalance(config.getInt("upkeepCost")) + " daily upkeep each new day to remain true.")
                .setCost(new ConfirmationTransaction(() -> config.getDouble("toggleCost"), town.getAccount(), "Cost of allowing outsiders to spawn to town."))
                .sendTo(sender);

        return true;
    }
}
