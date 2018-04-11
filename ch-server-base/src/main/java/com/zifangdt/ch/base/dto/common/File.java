package com.zifangdt.ch.base.dto.common;

import com.zifangdt.ch.base.dto.BaseEntity;
import org.apache.commons.io.FilenameUtils;
import org.springframework.http.MediaType;

import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 袁兵 on 2017/9/6.
 */
@Table(name = "com_file")
public class File extends BaseEntity<String> implements Serializable {

    private static final long serialVersionUID = 8907189570733683106L;

    private static final Map<String,String> EXT_TYPE_MAP=new HashMap<>();
    static{
        EXT_TYPE_MAP.put("txt", MediaType.TEXT_PLAIN_VALUE);
        EXT_TYPE_MAP.put("js", MediaType.TEXT_PLAIN_VALUE);
        EXT_TYPE_MAP.put("css", MediaType.TEXT_PLAIN_VALUE);
        EXT_TYPE_MAP.put("sql", MediaType.TEXT_PLAIN_VALUE);
        EXT_TYPE_MAP.put("jpg", MediaType.IMAGE_JPEG_VALUE);
        EXT_TYPE_MAP.put("jpeg", MediaType.IMAGE_JPEG_VALUE);
        EXT_TYPE_MAP.put("png", MediaType.IMAGE_PNG_VALUE);
        EXT_TYPE_MAP.put("gif", MediaType.IMAGE_GIF_VALUE);
    }

    private String name;
    private String fastPath;
    private Long size;
    private Date createTime;
    private Long createId;
    private String createName;

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFastPath() {
        return fastPath;
    }

    public void setFastPath(String fastPath) {
        this.fastPath = fastPath;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getCreateId() {
        return createId;
    }

    public void setCreateId(Long createId) {
        this.createId = createId;
    }

    public String getContentType(){
        String contentType = EXT_TYPE_MAP.get(FilenameUtils.getExtension(this.name));
        if(contentType==null){
            return MediaType.APPLICATION_OCTET_STREAM_VALUE;
        }
        return contentType;
    }

    public boolean getIsImage(){
        return getContentType().startsWith("image");
    }
}
