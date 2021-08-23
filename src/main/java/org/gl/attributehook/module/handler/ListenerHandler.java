package org.gl.attributehook.module.handler;

import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.gl.attributehook.AttributeHook;
import org.gl.attributehook.module.ExpansionModule;
import org.gl.attributehook.module.IModule;
import org.gl.attributehook.utils.ReflexUtil;
import org.gl.attributehook.utils.jar.ClassLoaderUtil;

public class ListenerHandler implements IModule, ExpansionModule {

    private ListenerHandler() {
    }

    @Override
    public void starting() {
        ClassLoaderUtil.forName(AttributeHook.class).forEach(this::registerModel);
    }

    @Override
    public void stopping() {
        HandlerList.unregisterAll(AttributeHook.getPlugin());
    }

    @Override
    public void registerModel(Class<?> model) {
        if (Listener.class.isAssignableFrom(model)) {
            Object instance = ReflexUtil.newInstance(model);
            if (instance != null) {
                Listener listener = (Listener) instance;
                Bukkit.getPluginManager().registerEvents(listener, AttributeHook.getPlugin());
            }
        }
    }
}
