package org.gl.attributehook.utils.number;

import org.gl.attributehook.utils.Strings;
import org.jetbrains.annotations.Nullable;

class Numbers {

    protected static boolean isEmpty(@Nullable Object object) {
        return object == null || Strings.isEmpty(object.toString());
    }

}
