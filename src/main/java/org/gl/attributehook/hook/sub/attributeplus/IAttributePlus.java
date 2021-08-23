package org.gl.attributehook.hook.sub.attributeplus;

import org.gl.attributehook.AttributeHook;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.UUID;

public interface IAttributePlus {

    String SOURCE = AttributeHook.getPlugin().getName();

    void setAttribute(@NotNull UUID uuid, List<String> lore);

    void removeAttribute(@NotNull UUID uuid);

}
