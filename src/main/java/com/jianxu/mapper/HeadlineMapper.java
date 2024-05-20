package com.jianxu.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jianxu.pojo.Headline;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jianxu.pojo.vo.PortalVo;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
* @author Now Now
* @description 针对表【news_headline】的数据库操作Mapper
* @createDate 2024-03-06 13:04:06
* @Entity com.jianxu.pojo.Headline
*/
public interface HeadlineMapper extends BaseMapper<Headline> {
    //自定义查询方法
    IPage<Map> selectNewsPage(IPage page,
                              @Param(value = "portalVo") PortalVo portalVo);

    Map<String, Object> selectDetailMap(int hid);
}




