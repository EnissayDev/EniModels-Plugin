package fr.enissay.enimodels.plugin.utils.commands;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ReflectCommand extends Command {

    private EniCommand exe = null;

    public ReflectCommand(String name) {
        super(name);
    }

    public ReflectCommand(String name,String description, String usageMessage, List<String> aliases) {
        super(name, description, usageMessage, aliases);
    }

    @Override
    public boolean execute(final CommandSender sender, final String label, final String[] args) {
        this.exe.sender = sender;

        if (sender instanceof Player) {
            this.exe.player = (Player) sender;

            if (!this.exe.hasPermission(exe.getPermission()) && !this.exe.hasPermission("*")) {
                this.exe.sendDoNotHavePermission();
                return false;

            }
        } else if (!this.exe.allowConsole) {
            this.exe.sendImpossibleWithConsole();
            return false;
        }

        if (args.length < this.exe.minArg) {
            this.exe.sendUsage(label);
            return false;
        }
        if (!this.exe.isAsynchronous) {
            return this.exe.onCommand(sender, this, label, args);
        }/* else {
			/*Main.get().getTask().runTaskAsynchronously(() -> this.exe.onCommand(sender, this, label, args));
			return true;
		}*/
        return false;
    }

    public void setExecutor(final EniCommand exe) {
        this.exe = exe;
    }

    @Override
    public List<String> tabComplete(final CommandSender sender, final String alias, final String[] args) {
        return this.exe.onTabComplete(sender, this, alias, args);
    }

}