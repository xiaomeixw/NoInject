package com.sabria.noinject;

import android.app.Activity;
import android.util.Log;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by xiongwei,An Android project Engineer.
 * Date:2016-01-08  17:12
 * Base on Meilimei.com (PHP Service)
 * Describe:
 * Version:1.0
 * Open source
 */
public class LittleKnife {

    public static void inject(Activity target) {
        Log.e(LittleKnife.class.getCanonicalName(), "I AM ON INJECT");
        try {
            final Class clazz = Class
                    .forName(target.getClass().getCanonicalName() + "$$LittleKnife");
            final Method inject = clazz.getMethod("inject", target.getClass());
            inject.invoke(clazz.newInstance(), target);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }
}
