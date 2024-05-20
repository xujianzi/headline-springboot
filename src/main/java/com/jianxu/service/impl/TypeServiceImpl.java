package com.jianxu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jianxu.pojo.Type;
import com.jianxu.service.TypeService;
import com.jianxu.mapper.TypeMapper;
import com.jianxu.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author Now Now
* @description 针对表【news_type】的数据库操作Service实现
* @createDate 2024-03-06 13:04:06
*/
@Service
public class TypeServiceImpl extends ServiceImpl<TypeMapper, Type>
    implements TypeService{

    @Autowired
    private TypeMapper typeMapper;

    /**
     *
     * @return
     */
    @Override
    public Result listAll() {
        List<Type> types = typeMapper.selectList(null);

        return Result.ok(types);
    }
}




