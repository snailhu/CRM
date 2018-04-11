package com.zifangdt.ch.base.api;

import com.zifangdt.ch.base.dto.common.*;
import com.zifangdt.ch.base.enums.DataEnum;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Created by 袁兵 on 2017/10/27.
 */
@FeignClient(value = "COMMON-SERVER", qualifier = "commonServerOfBase")
public interface CommonServerApi {
    @GetMapping("/data?for=internal")
    Map<Integer, List<DataDictionary>> getAll();

    @GetMapping("/data/type/{dataEnum}")
    List<DataDictionary> getTypesFor(@PathVariable("dataEnum") DataEnum dataEnum);

    @PostMapping("/notices")
    void saveNotice(@RequestBody Notice notice);

    @PutMapping("/enum/dataEnum?for=internal")
    void modifyDataEnum(
            DataDictionary dataDictionary,
            @RequestParam(value = "delete", required = false) String delete);

    @GetMapping("/files/info")
    List<File> fileInfo(@RequestParam("ids") List<String> ids);

    @PostMapping("/notices/batch")
    void saveNoticeBatch(@RequestBody List<Notice> list);

    @PostMapping("/files/internalSave")
    String saveFile(@RequestBody File file);

    @GetMapping("/divisions/byIds?for=internal")
    List<Division> findDivisionsByIds(@RequestParam("ids") List<String> ids);

    @GetMapping("/notices/byTrackId?for=internal")
    Notice currentNoticeTrackId(@RequestParam("trackId") Long trackId);

    @PostMapping("/logs")
    void saveLog(@RequestBody SystemLog log);
}
