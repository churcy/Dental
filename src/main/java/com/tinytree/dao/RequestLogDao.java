package com.tinytree.dao;

import com.tinytree.entity.RequestLog;
import org.springframework.stereotype.Repository;

/**
 * @Description:request请求数据 接口
 * @ClassName: RequestLogDao
 * @Author：zhengzhong
 * @Date 2016-1-22
 * (变更历史)
 * 如：eric	2015/01/08 修改了删除功
 */
@Repository
public interface RequestLogDao extends BaseDao<RequestLog>{

}
