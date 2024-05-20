package com.jianxu.controller;

import com.jianxu.pojo.Headline;
import com.jianxu.service.HeadlineService;
import com.jianxu.utils.Result;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @projectName: springboot-headline-part
 * @author: Jian Xu
 * @version: 1.0
 * @time 5/20/2024
 * @description: 实现头条的各项功能，增删改
 */

@RestController
@RequestMapping("/headline")
@CrossOrigin
@Slf4j
public class HeadLineController {
    @Autowired
    HeadlineService headlineService;

    @PostMapping("/publish")
    public Result publish(@RequestBody Headline headline, @RequestHeader String token){

        Result<Object> result = headlineService.publish(headline, token);
        return result;
    }

    @PostMapping("/findHeadlineByHid")
    public Result findHeadlineByHid(@RequestParam Integer  hid){
        Result result = headlineService.findHeadlineByHid(hid);
        return  result;
    }

    @PostMapping("/update")
    public Result update(@RequestBody Headline headline){
        return headlineService.updateHeadLine(headline);
    }


    @PostMapping("/removeByHid")
    public Result remove(@RequestParam Integer hid){
        Result result = headlineService.removeHeadline(hid);
        log.info("result is {}", result);
        return result;
    }
}
