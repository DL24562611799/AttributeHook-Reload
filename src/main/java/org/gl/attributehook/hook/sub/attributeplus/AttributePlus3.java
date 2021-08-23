package org.gl.attributehook.hook.sub.attributeplus;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;
import org.serverct.ersha.api.AttributeAPI;
import org.serverct.ersha.attribute.data.AttributeData;
import org.serverct.ersha.attribute.data.AttributeSource;

import java.util.List;
import java.util.UUID;

public class AttributePlus3 implements IAttributePlus {

    @Override
    public void setAttribute(@NotNull UUID uuid, List<String> lore) {
        Entity entity = Bukkit.getEntity(uuid);
        if (entity instanceof LivingEntity) {
            AttributeData attrData = AttributeAPI.getAttrData((LivingEntity) entity);
            AttributeAPI.addSourceAttribute(attrData, SOURCE, lore, false);
        }
    }

    @Override
    public void removeAttribute(@NotNull UUID uuid) {
        Entity entity = Bukkit.getEntity(uuid);
        if (entity instanceof LivingEntity) {
            AttributeData attrData = AttributeAPI.getAttrData((LivingEntity) entity);
            attrData.operationApiAttribute(SOURCE, AttributeSource.OperationType.TAKE);
        }
    }
}
