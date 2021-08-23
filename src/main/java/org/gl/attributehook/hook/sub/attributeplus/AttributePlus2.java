package org.gl.attributehook.hook.sub.attributeplus;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.serverct.ersha.jd.AttributeAPI;
import org.serverct.ersha.jd.attribute.AttributeData;

import java.util.List;
import java.util.UUID;

public class AttributePlus2 implements IAttributePlus {

    @Override
    public void setAttribute(@NotNull UUID uuid, List<String> lore) {
        Entity entity = Bukkit.getEntity(uuid);
        if (entity != null) {
            AttributeData attrData = AttributeAPI.getAttrData(entity);
            attrData.addApiAttribute(SOURCE, lore, false);
            attrData.updateAttribute(false);
        }
    }

    @Override
    public void removeAttribute(@NotNull UUID uuid) {
        Entity entity = Bukkit.getEntity(uuid);
        if (entity != null) {
            AttributeData attrData = AttributeAPI.getAttrData(entity);
            attrData.deleteApiAttribute(SOURCE);
            attrData.updateAttribute(false);
        }
    }
}
