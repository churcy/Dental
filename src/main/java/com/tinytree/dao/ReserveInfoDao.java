package com.tinytree.dao;

import com.tinytree.entity.ReserveInfo;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Description:预约信息处理接口
 * @ClassName: ReserveInfoDao
 * @Author：tangyang
 * @Date 2016-1-13
 * (变更历史)
 * 如：eric	2015/01/08 修改了删除功
 */
@Repository
public interface ReserveInfoDao extends BaseDao<ReserveInfo>{

	/**
	 * 统计数量
	 * @param doctorId
	 * @param date
	 * @return
	 */
	
	public Integer countByDoctorId(String doctorId,String date);
	
	/**
	 * 查询医生的预约列表
	 * @param map
	 * @return
	 */
	public List<Map<String, String>> findReserveList(Map<String, String> map);
	
	/**
	 * 查询患者的预约列表
	 * @param map
	 * @return
	 */
	public List<Map<String, String>> findReserveListUser(Map<String, String> map);

	/**
	 * 查询预约详情
	 * @param reserveId
	 * @return
	 */
	public Map<String, Object> findReserveDetail(String reserveId);
	
	/**
	 * 根据时间查询预约
	 * @param map
	 * @return
	 */
	public List<Map<String, String>> findReserveListByDate(Map<String, Object> map);
	
	/**
	 * 查询预约信息
	 * @param doctorId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	@Select("select * from reserve_info where doctor_id = #{0} and start_time > #{1} and start_time < #{2}")
	@ResultMap(value="reserveInfoMap")
	public List<ReserveInfo> findReserveListByDoctor(String doctorId,String startDate,String endDate);

}
