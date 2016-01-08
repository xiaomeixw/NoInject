package com.sabria.noinject;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by xiongwei,An Android project Engineer.
 * Date:2016-01-08  17:11
 * Base on Meilimei.com (PHP Service)
 * Describe:
 * Version:1.0
 * Open source
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.FIELD)
public @interface InjectView {

    int value();
}