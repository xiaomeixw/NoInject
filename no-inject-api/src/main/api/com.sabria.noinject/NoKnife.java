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
 * public的static的方法：没有任何权限问题，getMethod()就可以满足，根本不用getDeclaredMethod出马，更不用setAccessiable(true)
   public的非静态的方法：没有任何权限问题，getMethod()就可以满足，根本不用getDeclaredMethod出马，更不用setAccessiable(true)，
           但是，在invoke时，第一个参数必须是具体的某一个对象，static的可要可不要
   protected的非静态方法：必须使用getDeclaredMethod，不能使用getMethod，不用设置setAccessiable(true)
       friendly的非静态方法：必须使用getDeclaredMethod，不能使用getMethod，不用设置setAccessiable(true)
   private的非静态方法：必须使用getDeclaredMethod，不能使用getMethod，必须设置setAccessiable(true)


 包结果名问题导致IllegalAccessException ，所以我让最终胜出的    class MainActivity$$NoKnife 带public 标识

 * Version:1.0
 * Open source
 */
public class NoKnife {

    public static void inject(Activity target) {
        Log.e(NoKnife.class.getCanonicalName(), "I AM ON INJECT");
        try {
            final Class clazz = Class.forName(target.getClass().getCanonicalName() + "$$NoKnife");
            final Method method = clazz.getDeclaredMethod("inject", target.getClass());
            method.setAccessible(true);
            method.invoke(clazz.newInstance(), target);
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
