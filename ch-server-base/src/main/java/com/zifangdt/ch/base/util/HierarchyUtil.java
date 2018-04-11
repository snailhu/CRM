package com.zifangdt.ch.base.util;

import com.zifangdt.ch.base.dto.BaseEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 袁兵 on 2017/9/26.
 */
public class HierarchyUtil {

    public static <T extends BaseEntity<?> & Hierarchical> List<T> buildTree(List<T> list){
        List<T> result = new ArrayList<>();
        for(T t:list){
            if(t.getChildren()==null){
                throw new RuntimeException("children字段未初始化");
            }
            t.getChildren().clear();
        }

        for (T t1 : list) {
            boolean mark = false;
            for (T t2 : list) {
                if (t1.getParentId() != null && t1.getParentId().equals(t2.getId())) {
                    mark = true;
                    t2.getChildren().add(t1);
                    break;
                }
            }
            if (!mark) {
                result.add(t1);
            }
        }
        return result;
    }


    /**
     * O(n)
     * @param list
     * @param <T>
     * @return
     */
    public static <T extends BaseEntity<Long> & Hierarchical> void buildTree(List<T> list, T root){
        Map<Long, T> map = new HashMap<>();
        map.put(root.getId(), root);
        for (T t: list){
            map.put(t.getId(), t);
        }

        for (T t: list){
            if (t.getParentId() == null){
                root.getChildren().add(t);
            } else {
                T parent = map.get(t.getParentId());
                parent.getChildren().add(t);
            }
        }
    }
}
