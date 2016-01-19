package sabria.no_inject_runtime.view;

import android.app.Activity;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by xiongwei,An Android project Engineer.
 * Date:2016-01-12  10:50
 * Base on Meilimei.com (PHP Service)
 * Describe:
 * Version:1.0
 * Open source
 */
public class ViewInject {

    private static final String FIND_VIEW_BY_ID = "findViewById";

    public static void inject(Activity activity){
        Class<? extends Activity> aClass = activity.getClass();
        Field[] declaredFields = aClass.getDeclaredFields();
        if(declaredFields !=null && declaredFields.length>0){
            for(Field field : declaredFields){
                InjectView annotation = field.getAnnotation(InjectView.class);
                if(annotation!=null){
                    int viewId = annotation.value();
                    if(viewId!= -1){//defalut 0
                        try {
                            //得到activity中的方法findViewById，参数二是参数的类型
                            Method method = aClass.getMethod(FIND_VIEW_BY_ID, int.class);
                            //通过method.ivoke执行该方法，activity.findViewById方法方法的就是view对象
                            Object view = method.invoke(activity, viewId);
                            if(view!=null){
                                //根据属性名得到其Set方法设置它的值，这里拿到的也就是tvRunId的set方法。
                                field.setAccessible(true);
                                //得到指定的属性，并给该属性Filed设置值。
                                field.set(activity,view);
                            }
                        } catch (NoSuchMethodException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }



}
