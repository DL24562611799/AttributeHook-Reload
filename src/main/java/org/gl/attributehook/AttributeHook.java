package org.gl.attributehook;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.gl.attributehook.api.AttributeApi;
import org.gl.attributehook.command.ReloadCommand;
import org.gl.attributehook.module.handler.*;
import org.gl.attributehook.module.IModule;
import org.gl.attributehook.module.log.HookLogger;
import org.gl.attributehook.utils.Strings;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;
import java.util.Map;

public final class AttributeHook extends JavaPlugin {

    @Getter
    private static AttributeHook plugin;
    @Getter
    private final Map<Class<? extends IModule>, IModule> modules = new LinkedHashMap<>();


    @Override
    public void onLoad() {
        if (plugin == null) {
            plugin = this;
        }
        this.registerModel(ReloadCommand.class);
        this.registerModel(PluginHandler.class);
        this.registerModel(AttributeHandler.class);
        this.registerModel(ListenerHandler.class);
        this.registerModel(ExpansionHandler.class);
    }

    @Override
    public void onDisable() {
        Bukkit.getScheduler().runTask(this, () -> modules.values().forEach(IModule::stopping));
    }

    @Override
    public void onEnable() {
        Bukkit.getScheduler().runTask(this, () -> {
            this.saveDefaultConfig();
            modules.values().forEach(IModule::starting);
            HookLogger.console("{0} v{1} 启动成功", this.getName(), this.getDescription().getVersion());
        });
    }

    @SuppressWarnings("unchecked")
    public <E extends IModule> E getModel(Class<E> model) {
        IModule instance = this.modules.get(model);
        if (instance == null) {
            throw new NullPointerException(Strings.replace("{0} model class not find", model.getSimpleName()));
        }
        return (E) instance;
    }

    private void registerModel(Class<? extends IModule> model) {
        if (this.modules.containsKey(model)) {
            return;
        }
        try {
            Constructor<? extends IModule> constructor = model.getDeclaredConstructor();
            constructor.setAccessible(true);
            this.modules.put(model, constructor.newInstance());
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException ignored) {
        }
    }

    public AttributeApi getAttributeApi() {
        return getModel(AttributeHandler.class);
    }
}
