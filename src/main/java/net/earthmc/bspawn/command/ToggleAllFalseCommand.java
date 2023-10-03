package net.earthmc.bspawn.command;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.object.Town;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.jetbrains.annotations.NotNull;

public class ToggleAllFalseCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof ConsoleCommandSender)) {
            sender.sendMessage("Only console can use this command");
            return true;
        }

        try {
            for (Town town : TownyAPI.getInstance().getTowns()) {
                town.setPublic(false);
            }

            sender.sendMessage("Successfully set all towns to public=false");
        } catch (Exception e) {
            sender.sendMessage("Failed to set all towns to public=false");
        }

        return true;
    }
}
