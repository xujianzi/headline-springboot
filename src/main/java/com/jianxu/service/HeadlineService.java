package com.jianxu.service;

import com.jianxu.pojo.Headline;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jianxu.pojo.vo.PortalVo;
import com.jianxu.utils.Result;

/**
* @author Now Now
* @description 针对表【news_headline】的数据库操作Service
* @createDate 2024-03-06 13:04:06
*/
public interface HeadlineService extends IService<Headline> {

    /**
     * 根据查询条件分页数据
     * @return
     */
    Result findNewsPage(PortalVo portalVo);

    /**
     * 根据id查询头条内容
     * @param hid
     * @return
     */
    Result showHeadlineDetail(int hid);

    Result<Object> publish(Headline headline, String token);

    Result findHeadlineByHid(Integer hid);

    Result updateHeadLine(Headline headline);

    Result removeHeadline(Integer hid);
}
