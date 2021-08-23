package org.gl.attributehook.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.gl.attributehook.AttributeHook;
import org.gl.attributehook.module.IModule;
import org.gl.attributehook.module.log.HookLogger;

public class ReloadCommand implements IModule, CommandExecutor {

    private final PluginCommand command;

    private ReloadCommand() {
        this.command = Bukkit.getPluginCommand("attributeHook");
    }

    @Override
    public void starting() {
        command.setExecutor(this);
    }

    @Override
    public void stopping() {
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        boolean check = !(sender instanceof Player) || sender.isOp() || sender.hasPermission("AttributeHook.Reload");
        if (check) {
            AttributeHook.getPlugin().onDisable();
            AttributeHook.getPlugin().onEnable();
            HookLogger.log(sender, "{title} &c插件已重载");
        }
        return true;
    }

}
