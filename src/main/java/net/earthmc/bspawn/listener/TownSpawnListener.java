package net.earthmc.bspawn.listener;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.TownyUniverse;
import com.palmergames.bukkit.towny.event.TownSpawnEvent;
import com.palmergames.bukkit.towny.object.Nation;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.Town;
import com.palmergames.bukkit.towny.permissions.PermissionNodes;
import net.earthmc.bspawn.manager.TownMetadataManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class TownSpawnListener implements Listener {

    @EventHandler
    public void onTownSpawn(TownSpawnEvent event) {
        Resident resident = TownyAPI.getInstance().getResident(event.getPlayer());
        if (resident == null) return;

        if (TownyUniverse.getInstance().getPermissionSource().testPermission(event.getPlayer(), PermissionNodes.TOWNY_SPAWN_ADMIN.getNode()))
            return;

        Town toTown = event.getToTown();

        Nation toNation = toTown.getNationOrNull();

        // Return if toTown or toNation match the resident's town or nation
        if (toTown.equals(resident.getTownOrNull()) || (toNation != null && toNation.equals(resident.getNationOrNull()))) return;

        if (!TownMetadataManager.getCanOutsidersSpawn(toTown)) {
            event.setCancelMessage("You can not teleport to this town as they are not currently paying to let outsiders teleport");
            event.setCancelled(true);
        }
    }
}
