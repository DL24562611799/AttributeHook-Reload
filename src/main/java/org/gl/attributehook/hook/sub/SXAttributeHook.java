package org.gl.attributehook.hook.sub;

import github.saukiya.sxattribute.SXAttribute;
import github.saukiya.sxattribute.data.attribute.SXAttributeData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.gl.attributehook.hook.Hook;
import org.gl.attributehook.hook.PluginHook;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.UUID;

@Hook(plugin = "SX-Attribute")
public class SXAttributeHook extends PluginHook {

    private Object api;
    private Method attributeUpdate;
    private Method setEntityAPIData;
    private Method removeEntityAPIData;
    private Method loadListData;

    public SXAttributeHook() {
    }

    @Override
    public void enabled() {
        this.api = SXAttribute.getApi();
        try {
            Class<?> apiClass;
            if (!isVersion("2.0.9")) {
                apiClass = Class.forName("github.saukiya.sxattribute.api.SXAttributeAPI");
                attributeUpdate = apiClass.getMethod("updateStats", LivingEntity.class);
                loadListData = apiClass.getMethod("getLoreData", LivingEntity.class, Class.forName("github.saukiya.sxattribute.data.condition.SXConditionType"), List.class);
            }else {
                apiClass = Class.forName("github.saukiya.sxattribute.api.SXAPI");
                attributeUpdate = apiClass.getMethod("attributeUpdate", LivingEntity.class);
                loadListData = apiClass.getMethod("loadListData", List.class);
            }
            setEntityAPIData = apiClass.getMethod("setEntityAPIData", Class.class, UUID.class, SXAttributeData.class);
            removeEntityAPIData = apiClass.getMethod("removeEntityAPIData", Class.class, UUID.class);
        } catch (ClassNotFoundException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public SXAttributeData loadListData(List<String> list) {
        SXAttributeData data = new SXAttributeData();
        try {
            if (this.isVersion("3.5")) {
                data.add((SXAttributeData) loadListData.invoke(api, list));
            } else {
                data.add((SXAttributeData) loadListData.invoke(api, null, null, list));
            }
        } catch (IllegalAccessException | InvocationTargetException ignored) {
        }
        return data;
    }

    public void attributeUpdate(LivingEntity entity) {
        try {
            attributeUpdate.invoke(api, entity);
        } catch (IllegalAccessException | InvocationTargetException ignored) {
        }
    }

    public void setEntityAPIData(UUID uuid, SXAttributeData data) {
        try {
            setEntityAPIData.invoke(api, SXAttributeHook.class, uuid, data);
        } catch (IllegalAccessException | InvocationTargetException ignored) {
        }
    }

    public void removeEntityAPIData(UUID uuid) {
        try {
            removeEntityAPIData.invoke(api, SXAttributeHook.class, uuid);
        } catch (IllegalAccessException | InvocationTargetException ignored) {
        }
    }

    @Override
    public void setAttribute(@NotNull UUID uuid, List<String> lore) {
        Entity entity = Bukkit.getEntity(uuid);
        if (entity instanceof LivingEntity) {
            this.setEntityAPIData(uuid, this.loadListData(lore));
            this.attributeUpdate((LivingEntity) entity);
        }
    }

    @Override
    public void removeAttribute(@NotNull UUID uuid) {
        Entity entity = Bukkit.getEntity(uuid);
        if (entity instanceof LivingEntity) {
            this.removeEntityAPIData(uuid);
            this.attributeUpdate((LivingEntity) entity);
        }
    }

}
