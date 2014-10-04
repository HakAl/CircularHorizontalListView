package com.jacmobile.circularhorizontallistview.injection;

import java.lang.annotation.Retention;
import javax.inject.Qualifier;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by alex on 9/21/14.
 */
@Qualifier @Retention(RUNTIME)
public @interface ForApplication {
}
