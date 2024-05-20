package com.jianxu.pojo.vo;

import lombok.Data;

/**
 * @projectName: springboot-headline-part
 * @author: Jian Xu
 * @version: 1.0
 * @time 3/26/2024
 * @description: TODO
 */
@Data
public class PortalVo {
    private String keyWords;
    private Integer type = 0;
    private Integer pageNum = 1;
    private Integer pageSize = 10;
}
