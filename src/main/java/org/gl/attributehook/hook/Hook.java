package org.gl.attributehook.hook;

import java.lang.annotation.*;

@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Hook {
    String plugin();
}
