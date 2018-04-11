package com.zifangdt.ch.base.web;

import com.zifangdt.ch.base.api.CommonServerApi;
import com.zifangdt.ch.base.dto.common.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by 袁兵 on 2018/1/15.
 */
@Component
public class FileVerboseFetcher extends AbstractVerboseFetcher<String, File> {

    @Autowired
    private CommonServerApi commonServerApi;

    @Override
    protected List<File> doFetch(Collection<String> ids) {
        return commonServerApi.fileInfo(new ArrayList<>(ids));
    }

    @Override
    public String nameOf(File verbose) {
        return verbose.getName();
    }
}
