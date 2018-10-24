package com.dianping.cat.annotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface CatAnnotation {
    String type();
    String value();
}
