package org.gl.attributehook.module.handler;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.gl.attributehook.AttributeHook;
import org.gl.attributehook.api.AttributeApi;
import org.gl.attributehook.api.data.HAttributeData;
import org.gl.attributehook.api.data.Operation;
import org.gl.attributehook.module.IModule;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public final class AttributeHandler implements IModule, AttributeApi {

    private final PluginHandler handler;
    private final ConcurrentHashMap<UUID, Map<String, HAttributeData>> entityAttributes = new ConcurrentHashMap<>();

    private AttributeHandler() {
        this.handler = AttributeHook.getPlugin().getModel(PluginHandler.class);
    }

    @Override
    public void starting() {
    }

    @Override
    public void stopping() {
        this.entityAttributes.clear();
    }

    /**
     * 获取一个字符串集合中的属性值
     * @param lore 字符串集合
     * @return 属性值
     */
    public HAttributeData getAttributeData(@NotNull List<String> lore) {
        return new HAttributeData().operation(Operation.ADD, lore);
    }

    /**
     * 获取是某个物品的属性值
     * @param itemStack 物品
     * @return 属性值
     */
    public HAttributeData getAttributeData(@NotNull ItemStack itemStack) {
        HAttributeData data = new HAttributeData();
        if (itemStack.getType() != Material.AIR && itemStack.hasItemMeta() && itemStack.getItemMeta().hasLore()) {
            data.operation(Operation.ADD, getAttributeData(itemStack.getItemMeta().getLore()));
        }
        return data;
    }

    /**
     * 移除某个实体的当前属性值
     * @param uuid 实体
     */
    public synchronized void removeEntity(@NotNull UUID uuid) {
        this.entityAttributes.remove(uuid);
        this.handler.removeAttributes(uuid);
    }

    /**
     * 移除一个属性源的属性值
     * @param source 属性源
     */
    public synchronized void removeSourceData(@NotNull String source) {
        for (UUID uuid : entityAttributes.keySet()) {
            Map<String, HAttributeData> map = entityAttributes.get(uuid);
            map.remove(source);
            this.entityAttributes.put(uuid, map);
            this.handler.setAttributes(uuid, this.getEntityData(uuid).getAttributes());
        }
    }

    /**
     * 设置某个实体的某个源属性值
     * @param uuid 实体
     * @param source 属性源
     * @param data 属性值
     */
    public synchronized void setEntityData(@NotNull UUID uuid, @NotNull String source, @NotNull HAttributeData data) {
        HAttributeData attributeData = this.getEntityData(uuid, source);
        this.entityAttributes.computeIfAbsent(uuid, t -> new ConcurrentHashMap<>()).put(source, attributeData.operation(Operation.ADD, data));
        this.handler.setAttributes(uuid, this.getEntityData(uuid).getAttributes());
    }

    @Override
    public synchronized void operationAttribute(@NotNull Operation type, @NotNull UUID uuid, @NotNull String source, @NotNull HAttributeData data) {
        if (type == Operation.SET) {
            this.setEntityData(uuid, source, data);
        }else {
            this.setEntityData(uuid, source, getEntityData(uuid).operation(type, data));
        }
    }

    /**
     * 移除某个实体的某个源属性
     * @param uuid 实体
     * @param source 属性源
     */
    public synchronized void removeEntityData(@NotNull UUID uuid, @NotNull String source) {
        Map<String, HAttributeData> map = this.entityAttributes.getOrDefault(uuid, new ConcurrentHashMap<>());
        map.remove(source);
        this.entityAttributes.put(uuid, map);
        this.handler.setAttributes(uuid, this.getEntityData(uuid).getAttributes());
    }

    /**
     * 获取某个实体的当前属性值
     * @param uuid 实体
     * @return HAttributeData
     */
    public synchronized HAttributeData getEntityData(@NotNull UUID uuid) {
        Map<String, HAttributeData> map = this.entityAttributes.get(uuid);
        HAttributeData data = new HAttributeData();
        if (map != null) {
            map.values().forEach(d -> data.operation(Operation.ADD, d));
        }
        return data;
    }

    @Override
    public ConcurrentHashMap<UUID, Map<String, HAttributeData>> getAttributes() {
        return this.entityAttributes;
    }

    private HAttributeData getEntityData(@NotNull UUID uuid, @NotNull String source) {
        Map<String, HAttributeData> map = entityAttributes.getOrDefault(uuid, new ConcurrentHashMap<>());
        return map.getOrDefault(source, new HAttributeData());
    }
}
