package com.smart.compact.qualifier;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * Created by lshabory on 3/14/2018.
 */

@Qualifier
@Retention(RetentionPolicy.RUNTIME)
public @interface Cached {
}
