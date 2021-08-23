package org.gl.attributehook.module.handler;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.gl.attributehook.hook.Hook;
import org.gl.attributehook.hook.PluginHook;
import org.gl.attributehook.module.ExpansionModule;
import org.gl.attributehook.module.IModule;
import org.gl.attributehook.utils.ReflexUtil;
import org.gl.attributehook.utils.jar.ClassLoaderUtil;
import org.jetbrains.annotations.NotNull;
import org.gl.attributehook.module.log.HookLogger;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PluginHandler implements IModule, ExpansionModule {

    private final List<PluginHook> plugins = new ArrayList<>();

    private PluginHandler() {
    }

    public void setAttributes(@NotNull UUID uuid, @NotNull List<String> lore) {
        plugins.forEach(attributePlugin -> attributePlugin.setAttribute(uuid, lore));
    }

    public void removeAttributes(@NotNull UUID uuid) {
        plugins.forEach(attributePlugin -> attributePlugin.removeAttribute(uuid));
    }

    public void register(@NotNull Class<?> cls) {
        Hook annotation = cls.getAnnotation(Hook.class);
        if (annotation == null) {
            return;
        }
        String name = annotation.plugin();
        Plugin p = Bukkit.getPluginManager().getPlugin(name);
        if (p != null) {
            Object instance = ReflexUtil.newInstance(cls);
            if (instance instanceof PluginHook) {
                PluginHook plugin = (PluginHook) instance;
                plugin.enabled();
                plugins.add(plugin);
                HookLogger.console("{0} hooked", name);
            }
        }else {
            HookLogger.console("{0} not find", name);
        }
    }

    @Override
    public void starting() {
        ClassLoaderUtil.forName(org.gl.attributehook.AttributeHook.class, Hook.class).forEach(this::register);
    }

    @Override
    public void stopping() {
        this.plugins.clear();
    }

    @Override
    public void registerModel(Class<?> model) {
        this.register(model);
    }
}
