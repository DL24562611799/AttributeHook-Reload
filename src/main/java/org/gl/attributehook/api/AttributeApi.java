package org.gl.attributehook.api;

import org.bukkit.inventory.ItemStack;
import org.gl.attributehook.api.data.HAttributeData;
import org.gl.attributehook.api.data.Operation;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public interface AttributeApi {


    /**
     * 获取一个字符串集合中的属性值
     * @param lore 字符串集合
     * @return 属性值
     */
    HAttributeData getAttributeData(@NotNull List<String> lore);
    /**
     * 获取是某个物品的属性值
     * @param itemStack 物品
     * @return 属性值
     */
    HAttributeData getAttributeData(@NotNull ItemStack itemStack);

    /**
     * 移除某个实体的当前属性值
     * @param uuid 实体
     */
    void removeEntity(@NotNull UUID uuid);

    /**
     * 移除一个属性源的属性值
     * @param source 属性源
     */
    void removeSourceData(@NotNull String source);

    /**
     * 设置某个实体的某个源属性值
     * @param uuid 实体
     * @param source 属性源
     * @param data 属性值
     */
    void setEntityData(@NotNull UUID uuid, @NotNull String source, @NotNull HAttributeData data);

    /**
     * 设置某个实体的某个源属性值
     * @param uuid 实体
     * @param source 属性源
     * @param data 属性值
     */
    void operationAttribute(@NotNull Operation type, @NotNull UUID uuid, @NotNull String source, @NotNull HAttributeData data);

    /**
     * 移除某个实体的某个源属性
     * @param uuid 实体
     * @param source 属性源
     */
    void removeEntityData(@NotNull UUID uuid, @NotNull String source);

    /**
     * 获取某个实体的当前属性值
     * @param uuid 实体
     * @return HAttributeData
     */
    HAttributeData getEntityData(@NotNull UUID uuid);

    /**
     * 获取所有实体的属性数据ConcurrentHashMap
     * @return ConcurrentHashMap
     */
    ConcurrentHashMap<UUID, Map<String, HAttributeData>> getAttributes();
}
