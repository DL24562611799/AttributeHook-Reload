package org.gl.attributehook.hook.sub.attributeplus;

import org.gl.attributehook.hook.Hook;
import org.gl.attributehook.hook.PluginHook;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.UUID;

@Hook(plugin = "AttributePlus")
public class AttributePlusHook extends PluginHook {

    private IAttributePlus attributePlus;

    @Override
    public void enabled() {
        this.attributePlus = this.isVersion("3.0.0") ? new AttributePlus3() : new AttributePlus2();
    }

    @Override
    public void setAttribute(@NotNull UUID uuid, List<String> lore) {
        this.attributePlus.setAttribute(uuid, lore);
    }

    @Override
    public void removeAttribute(@NotNull UUID uuid) {
        this.attributePlus.removeAttribute(uuid);
    }
}
