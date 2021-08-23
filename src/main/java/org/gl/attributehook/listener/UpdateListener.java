package org.gl.attributehook.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.gl.attributehook.module.handler.AttributeHandler;
import org.gl.attributehook.api.event.AttributeUpdateEvent;
import org.gl.attributehook.AttributeHook;

public class UpdateListener implements Listener {

    @EventHandler(priority = EventPriority.LOW)
    private void on(PlayerItemHeldEvent event) {
        AttributeUpdateEvent.callEvent(event.getPlayer());
    }

    @EventHandler(priority = EventPriority.LOW)
    private void on(InventoryCloseEvent event) {
        Inventory inv = event.getInventory();
        String name = inv.getName();
        if (name.contains("container") || name.contains("Repair") || name.contains("Crafting")) {
            AttributeUpdateEvent.callEvent(event.getPlayer());
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    private void on(PlayerInteractEvent event) {
        if ((event.getAction().toString().contains("RIGHT"))) {
            if (event.getItem() != null) {
                String name = event.getItem().getType().toString();
                if (name.contains("HELMET") || name.contains("CHESTPLATE") || name.contains("LEGGINGS") || name.contains("BOOTS")) {
                    AttributeUpdateEvent.callEvent(event.getPlayer());
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    void on(PlayerJoinEvent event) {
        AttributeUpdateEvent.callEvent(event.getPlayer());
    }

    @EventHandler(priority = EventPriority.LOW)
    void on(PlayerQuitEvent event) {
        AttributeHook.getPlugin().getModel(AttributeHandler.class).removeEntity(event.getPlayer().getUniqueId());
    }
}
