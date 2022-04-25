package fr.enissay.enimodels.plugin.listeners;

import fr.enissay.enimodels.plugin.management.ModelManager;
import fr.enissay.enimodels.plugin.management.ProjectManager;
import fr.enissay.enimodels.plugin.management.component.exceptions.ParsingErrorException;
import fr.enissay.enimodels.plugin.management.component.exceptions.ProjectNotFoundException;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {

    @EventHandler
    public void onJoin(final PlayerJoinEvent event) {
    }
}
