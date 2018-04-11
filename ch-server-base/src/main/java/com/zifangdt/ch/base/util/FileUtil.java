package com.zifangdt.ch.base.util;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.zifangdt.ch.base.dto.common.File;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by 袁兵 on 2017/9/29.
 */
public class FileUtil {
    public static ResponseEntity<byte[]> download(byte[] bytes, String filename) {
        HttpHeaders headers = new HttpHeaders();
        try {
            headers.setContentDispositionFormData("attachment", URLEncoder.encode(filename, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
        }
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        return new ResponseEntity<>(bytes, headers, HttpStatus.OK);
    }

    public static ResponseEntity<byte[]> view(byte[] bytes, String contentType) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf(contentType));
        return new ResponseEntity<>(bytes, headers, HttpStatus.OK);
    }

    public static ResponseEntity<byte[]> download(String classpath, String filename) throws IOException {
        try (InputStream is = new ClassPathResource(classpath).getInputStream()) {
            return download(
                    IOUtils.toByteArray(is),
                    filename);
        }
    }

    public static void download(HttpServletResponse response, Workbook workbook, String filename) throws IOException {
        try {
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
        }
        response.setContentType(MediaType.APPLICATION_OCTET_STREAM.toString());
        workbook.write(response.getOutputStream());
    }

    public static File uploadLocalFile(FastFileStorageClient client, String path, String filename) {
        java.io.File localFile = new java.io.File(path);
        if (StringUtils.isEmpty(filename)) {
            filename = localFile.getName();
        }
        long size = localFile.length();
        File insert = new File();
        try (InputStream in = new FileInputStream(localFile)) {
            StorePath storePath = client.uploadFile(in, size, FilenameUtils.getExtension(filename), null);
            insert.setFastPath(storePath.getFullPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        insert.setName(filename);
        insert.setSize(size);
        return insert;
    }
}
