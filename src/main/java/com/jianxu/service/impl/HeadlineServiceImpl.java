package com.jianxu.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jianxu.pojo.Headline;
import com.jianxu.pojo.vo.PortalVo;
import com.jianxu.service.HeadlineService;
import com.jianxu.mapper.HeadlineMapper;
import com.jianxu.utils.JwtHelper;
import com.jianxu.utils.Result;
import com.jianxu.utils.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
* @author Now Now
* @description 针对表【news_headline】的数据库操作Service实现
* @createDate 2024-03-06 13:04:06
*/
@Service
public class HeadlineServiceImpl extends ServiceImpl<HeadlineMapper, Headline>
    implements HeadlineService{

    @Autowired
    HeadlineMapper headlineMapper;

    @Autowired
    JwtHelper jwtHelper;

    /**
     * 根据查询条件分页数据
     *  1. 进行分页查询
     *  2. 分页的结果，拼接到result
     *
     *  注意1: 查询需要自定义语句, 自定义mapper方法，携带分页
     *  注意2： 返回结果是list<>map</>
     * @return
     */
    @Override
    public Result findNewsPage(PortalVo portalVo) {

        // 分页参数
        IPage<Map> page = new Page(portalVo.getPageNum(), portalVo.getPageSize());

        // 自定义查询方法
        headlineMapper.selectNewsPage(page, portalVo);
        // 结果封装
        Map<String, Object> pageInfo = new HashMap<>();
        pageInfo.put("pageData", page.getRecords());
        pageInfo.put("pageNum", page.getCurrent());
        pageInfo.put("pageSize", page.getSize());
        pageInfo.put("totalPage", page.getPages());
        pageInfo.put("totalSize", page.getTotal());
        Map<String, Object> pageInfoMap = new HashMap<>();
        pageInfoMap.put("pageInfo", pageInfo);

        return Result.ok(pageInfoMap);
    }

    /**
     * 根据id查询头条内容
     *     1.需要查询到完整新闻文章信息
     *          多表查询
     *     2.需要后端让新闻的浏览量+1
     * @param hid
     * @return
     */
    @Override
    public Result showHeadlineDetail(int hid) {
        // 根据id查询，多表，使用map接受
        Map<String,Object> headLineDetail = headlineMapper.selectDetailMap(hid);
        // 拼接头条对象(阅读量和version),然后进行数据更新
        Headline headline = new Headline();
        headline.setHid(hid);
        headline.setPageViews((Integer) headLineDetail.get("pageViews") + 1);
        headline.setVersion((Integer) headLineDetail.get("version"));
        headlineMapper.updateById(headline);

        Map<String, Object> newMap = new HashMap<>(headLineDetail);
        newMap.remove("version");

        Map<String, Object> pageInfoMap = new HashMap<>();
        pageInfoMap.put("headline", newMap);

        return Result.ok(pageInfoMap);
    }

    /**
     * 实现步骤
     * 1.token获取userId [无需校验，会直接拦截]
     * 2.封装headline数据
     * 3.插入
     *
     * @param headline
     * @param token
     * @return
     */
    @Override
    public Result<Object> publish(Headline headline, String token) {
        int userId = jwtHelper.getUserId(token).intValue();
        headline.setPublisher(userId);
        headline.setPageViews(0);
        headline.setCreateTime(new Date());
        headline.setUpdateTime(new Date());
        int count = headlineMapper.insert(headline);
        if (count > 0) {
            return Result.ok(null);
        }
        return  null;
    }

    /**
     * 根据id查询用户信息
     * @param hid
     * @return
     */
    @Override
    public Result findHeadlineByHid(Integer hid) {
        Headline headline = headlineMapper.selectById(hid);
        Map<String, Object> pageInfoMap = new HashMap<>();
        pageInfoMap.put("headline", headline);
        return Result.ok(pageInfoMap);
    }

    @Override
    public Result removeHeadline(Integer hid) {
        int i = headlineMapper.deleteById(hid);
        if (i > 0){
            return Result.ok(null);
        }
        return Result.build(null, ResultCodeEnum.NOTLOGIN);
    }

    /**
     * 注意事项，修改要获取version 【乐观锁】
     * 步骤：
     *    1. 根据hid 获取version
     *    2. 更新时间
     *    3. 更新headline
     * @param headline
     * @return
     */
    @Override
    public Result updateHeadLine(Headline headline) {
        Integer version = headlineMapper.selectById(headline.getHid()).getVersion();
        headline.setVersion(version);
        headline.setUpdateTime(new Date());
        headlineMapper.updateById(headline);

        return Result.ok(null);
    }

}




