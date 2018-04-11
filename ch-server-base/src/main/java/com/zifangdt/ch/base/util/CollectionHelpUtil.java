package com.zifangdt.ch.base.util;

import java.util.Collection;

public class CollectionHelpUtil {


    public  static <T> boolean notEmpty(Collection<T> object){
        if(object!=null && object.size()>0){
            return true;
        }else {return  false;}
    }
}