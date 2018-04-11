package com.zifangdt.ch.base.dto;

import com.zifangdt.ch.base.util.WebUtil;

/**
 * Created by 袁兵 on 2017/9/30.
 */
@Deprecated
public interface ClientAware {
    void setFromApp(Boolean fromApp);

    default void setFromApp() {
        setFromApp(WebUtil.isFromApp());
    }
}
