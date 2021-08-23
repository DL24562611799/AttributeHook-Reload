package org.gl.attributehook.module.log;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.gl.attributehook.utils.Strings;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.gl.attributehook.AttributeHook;

public class HookLogger {

    public static void log(@NotNull CommandSender sender, @Nullable String msg, @Nullable Object... params) {
        if (Strings.nonEmpty(msg)) {
            String message = msg.replace("{title}", "[" + AttributeHook.getPlugin().getName() + "]");
            if (params != null) {
                message = Strings.replace(message, params);
            }
            sender.sendMessage(message);
        }
    }

    public static void console(@Nullable String msg, @Nullable Object... params) {
        log(Bukkit.getConsoleSender(), "{title} " + msg, params);
    }
}
