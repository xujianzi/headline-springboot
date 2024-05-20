package com.jianxu.service;

import com.jianxu.pojo.Type;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jianxu.utils.Result;

/**
* @author Now Now
* @description 针对表【news_type】的数据库操作Service
* @createDate 2024-03-06 13:04:06
*/
public interface TypeService extends IService<Type> {

    /**
     * 显示所有类别
     * @return Result json 对象
     */
    Result listAll();
}
