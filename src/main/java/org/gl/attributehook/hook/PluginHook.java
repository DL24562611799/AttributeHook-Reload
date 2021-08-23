package org.gl.attributehook.hook;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.gl.attributehook.utils.VersionUtil;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.UUID;

public abstract class PluginHook {

    public void enabled() {

    }

    public abstract void setAttribute(@NotNull UUID uuid, List<String> lore);

    public abstract void removeAttribute(@NotNull UUID uuid);

    public final String getName() {
        return this.getClass().getAnnotation(Hook.class).plugin();
    }

    public final boolean isVersion(String version) {
        Plugin plugin = Bukkit.getPluginManager().getPlugin(getName());
        if (plugin == null) return false;
        PluginDescriptionFile description = plugin.getDescription();
        return VersionUtil.isCompatible(description.getVersion(), version);
    }
}
