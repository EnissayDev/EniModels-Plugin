package fr.enissay.enimodels.plugin.utils.commands;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import net.md_5.bungee.api.chat.BaseComponent;

public abstract class EniCommand {

    protected static CommandMap cmap;

    protected Plugin plugin;

    protected List<String> alias;
    protected String command, description, permission;

    public Player player;
    public CommandSender sender;

    protected String usageString;
    public Integer minArg = 0;
    public boolean allowConsole = true;

    public boolean isAsynchronous = false;

    public EniCommand(Plugin plugin, String command, String... alias) {
        this.plugin = plugin;
        this.command = command;
        this.alias = Arrays.asList(alias);
    }

    public EniCommand(Plugin plugin, String permission) {
        this.permission = permission;
        this.plugin = plugin;
    }

    public EniCommand(Plugin plugin, String command, String permission, String description, String... alias) {
        this.plugin = plugin;
        this.command = command;
        this.description = description;
        this.alias = Arrays.asList(alias);
        this.permission = permission;
    }

    public void broadcast(String message) {
        Bukkit.broadcastMessage(message);
    }

    public String buildText(int min, String[] args) {
        return String.join(" ", Arrays.copyOfRange(args, min, args.length));
    }

    CommandMap getCommandMap() {
        if (cmap == null) {
            try {
                Field f = Bukkit.getServer().getClass().getDeclaredField("commandMap");
                f.setAccessible(true);
                cmap = (CommandMap) f.get(Bukkit.getServer());
                f.setAccessible(false);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return cmap;
    }

    public Player getPlayer() {
        return this.player;
    }

    public String getPermission() {
        return permission;
    }

    public abstract boolean onCommand(CommandSender sender, Command cmd, String label, String[] args);

    public abstract List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args);

    public void register() {
        ReflectCommand reflectCommand = new ReflectCommand(this.command);
        if (this.alias != null) reflectCommand.setAliases(this.alias);
        if (this.description != null) reflectCommand.setDescription(this.description);
        reflectCommand.setPermission(this.permission);
        reflectCommand.setExecutor(this);
        // TODO test null instanceof ""
        this.getCommandMap().register("", reflectCommand);
    }

    public boolean hasPermission(String permission) {
        return getPlayer().hasPermission(permission);
    }

    public void sendDoNotHavePermission() {
        this.sendError("You do not have the Permission to use this command.");
    }

    public void sendError(String message) {
        this.sendMessage(ChatColor.RED + "Error: "+message);
    }

    public void sendImpossibleWithConsole() {
        this.sendError("This Command is only executable in game.");
    }

    public void sendIncorrectSyntax() {
        this.sendError("Incorrect Syntax.");
    }

    public void sendIncorrectSyntax(String correctSyntax) {
        this.sendError("Syntax : §o" + correctSyntax);
    }

    public void sendMessage(BaseComponent... text) {
        this.player.spigot().sendMessage(text);
    }


    public void sendMessage(CommandSender sender, String text) {
        sender.sendMessage(text);
    }

    public void sendMessage(String text) {
        this.sendMessage(this.sender, text);
    }

    public void sendMessageToAll(String text) {
        Bukkit.getOnlinePlayers().forEach(player -> this.sendMessage(player, text));
        this.sendMessage(Bukkit.getConsoleSender(), text);
    }

    public void sendSuccess(String message) {
        this.sendMessage(ChatColor.GREEN + message);
    }

    public void sendUsage(String label) {
        this.sendMessage(ChatColor.RED + "Error: Please use: /" + label + " " + this.usageString + " instead.");
    }

    public void setAllowConsole(boolean allowConsole) {
        this.allowConsole = allowConsole;
    }

    /**
     * Don't foget to set {@link EniCommand#usageString}
     */
    public void setMinArg(Integer minArg) {
        this.minArg = minArg;
    }

    /**
     * Format: <%obligatory%|%obligatory%> [%optional%]
     * Other like 'Usage : /command' is autofill.
     */
    public void setUsageString(String usageString) {
        this.usageString = usageString;
    }
}