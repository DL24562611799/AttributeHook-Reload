package org.gl.attributehook.api.event;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * 属性更新事件
 */
public class AttributeUpdateEvent extends Event {

    private static final HandlerList handlerList = new HandlerList();

    @Getter
    private final LivingEntity entity;

    private AttributeUpdateEvent(LivingEntity entity) {
        this.entity = entity;
    }

    public static void callEvent(LivingEntity entity) {
        Bukkit.getPluginManager().callEvent(new AttributeUpdateEvent(entity));
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }
}
