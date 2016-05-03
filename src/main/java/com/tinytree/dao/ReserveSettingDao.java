package com.tinytree.dao;

import com.tinytree.entity.ReserveSetting;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description:预约设置处理接口
 * @ClassName: ReserveSettingDao
 * @Author：tangyang
 * @Date 2016-1-13
 * (变更历史)
 * 如：eric	2015/01/08 修改了删除功
 */
@Repository
public interface ReserveSettingDao extends BaseDao<ReserveSetting>{
	/**
	 * 查询预约设置
	 * @param doctorId
	 * @param weekDay
	 * @param repeatStatus
	 * @return
	 */
	@Select("select * from reserve_setting where doctor_id = #{doctorId}")
	@ResultMap(value="reserveSettingMap")
	public List<ReserveSetting> get(String doctorId);
	
}
