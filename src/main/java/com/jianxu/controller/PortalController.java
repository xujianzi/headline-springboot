package com.jianxu.controller;

import com.jianxu.pojo.Headline;
import com.jianxu.pojo.vo.PortalVo;
import com.jianxu.service.HeadlineService;
import com.jianxu.service.TypeService;
import com.jianxu.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @projectName: springboot-headline-part
 * @author: Jian Xu
 * @version: 1.0
 * @time 3/26/2024
 * @description: TODO
 */

@RestController
@RequestMapping("portal")
@CrossOrigin
@Slf4j
public class PortalController {

    @Autowired
    private TypeService typeService;
    @Autowired
    private HeadlineService headlineService;

    @GetMapping("findAllTypes")
    public Result findAllTypes(){
        Result result = typeService.listAll();
        log.info("result: {}", result);

        return result;
    }

    @PostMapping("findNewsPage")
    public Result findNewsPage(@RequestBody PortalVo portalVo){
        Result result = headlineService.findNewsPage(portalVo);

        log.info("result: {}", result);
        return  result;
    }

    @PostMapping("showHeadlineDetail")
    public Result showHeadlineDetail(@RequestParam(name = "hid") int hid){
        Result result = headlineService.showHeadlineDetail(hid);

        log.info("result: {}", result);
        return  result;
    }
}
