package com.sabria.noinject.process;  // PackageElement

import java.io.Serializable;

/**
 * Created by xiongwei,An Android project Engineer.
 * Date:2016-01-13  14:44
 * Base on Meilimei.com (PHP Service)
 * Describe:
 * Version:1.0
 * Open source
 */
public class ElementDemo implements Serializable{ //TypeElement

    private int num;      // VariableElement

    public ElementDemo() {   //ExecuteableElement
    }

    public void setNum(int num) {// ExecuteableElement   其中的int num 参数是TypeElement
        this.num = num;
    }

    public int getNum() {// ExecuteableElement
        return num;
    }
}
