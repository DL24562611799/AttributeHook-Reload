package org.gl.attributehook.module.handler;

import org.gl.attributehook.AttributeHook;
import org.gl.attributehook.module.ExpansionModule;
import org.gl.attributehook.module.IModule;
import org.gl.attributehook.utils.jar.ClassLoaderUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ExpansionHandler implements IModule {

    private final File folder;
    private final List<ExpansionModule> models = new ArrayList<>();

    private ExpansionHandler() {
        this.folder = new File(AttributeHook.getPlugin().getDataFolder(), "expansions");
    }

    @Override
    public void starting() {
        if (!this.folder.exists()) {
            this.folder.mkdirs();
        }
        for (IModule model : AttributeHook.getPlugin().getModules().values()) {
            if (model instanceof ExpansionModule) {
                this.models.add((ExpansionModule) model);
            }
        }
        ClassLoaderUtil.forFolder(this.getClass().getClassLoader(), this.folder, null).forEach(model -> this.models.forEach(expansionModule -> expansionModule.registerModel(model)));
    }

    @Override
    public void stopping() {
        this.models.clear();
    }
}
