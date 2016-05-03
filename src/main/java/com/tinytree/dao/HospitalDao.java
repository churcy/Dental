package com.tinytree.dao;

import com.tinytree.entity.Hospital;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Map;
/**
 * @Description:医院相关数据库操作
 * @ClassName: DoctorDao
 * @Author：zhengzhong
 * @Date 2016-1-19
 * (变更历史)
 * 如：eric	2015/01/08 修改了删除功
 */
@Repository
public interface HospitalDao extends BaseDao<Hospital>{

	public List<Map<String ,Object>> getAll();

}
