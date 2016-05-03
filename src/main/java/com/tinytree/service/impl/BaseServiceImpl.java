package com.tinytree.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.tinytree.bean.Pager;
import com.tinytree.bean.PagerEx;
import com.tinytree.bean.PagerParam;
import com.tinytree.bean.PagerParam.ConditionType;
import com.tinytree.dao.BaseDao;
import com.tinytree.service.BaseService;
import com.tinytree.util.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Transactional(readOnly = true)
public class BaseServiceImpl<T, PK extends Serializable> implements BaseService<T, PK> {

	
	private BaseDao<T> baseDao;
	
	public BaseServiceImpl(){
	}
	
	public BaseDao<T> getBaseDao() {
		return baseDao;
	}

	public void setBaseDao(BaseDao<T> baseDao) {
		this.baseDao = baseDao;
	}

	@Override
	public T get(PK id) {
		return baseDao.selectByPrimaryKey(id);
	}

	@Override
	public List<T> getAll() {
		return baseDao.selectAll();
	}

	@Override
	public int getTotalCount() {
		return baseDao.count();
	}

	@Override
	@Transactional
	public void save(T entity) {
		baseDao.insert(entity);
	}
	
	@Override
	@Transactional
	public void save(List<T> entitys){
		for(T entity:entitys)
		{
			baseDao.insert(entity);
		}
	}

	@Override
	@Transactional
	public void update(T entity) {
		baseDao.updateByPrimaryKey(entity);
	}
	
	@Override
	@Transactional
	public void update(List<T> entitys){
		for(T entity : entitys){
			baseDao.updateByPrimaryKey(entity);
		}
	}

	@Override
	@Transactional
	public void delete(T entity) {
		baseDao.deleteByEntity(entity);
	}

	@Override
	@Transactional
	public void delete(PK id) {
		baseDao.deleteByPrimaryKey(id);
	}

	@Override
	@Transactional
	public void delete(List<PK> ids) {
		for(PK id:ids){
			baseDao.deleteByPrimaryKey(id);
		}
	}

	@Override
	public Pager findByPager(Pager pager) throws Exception {
		if(pager == null){
			pager = new Pager();
		}
		
		List<T> list = null;
		if(StringUtils.isNotEmpty(pager.getKeyword())&&StringUtils.isNotEmpty(pager.getProperty())){
			
			PageHelper.startPage(pager.getPageNumber(),pager.getPageSize());
			PageHelper.orderBy(pager.getOrderBy() + (pager.getOrderType() == Pager.OrderType.desc ? " desc":""));
			PagerParam param = new PagerParam();
			param.setParam(ConditionType.equal, pager.getProperty(), pager.getKeyword());
			String string = createCriteria(param);
			Map<String, String> map = new HashMap<String, String>();
			map.put("queryCondition", string);
			list = baseDao.selectByMap(map);
		}
		else{
			PageHelper.startPage(pager.getPageNumber(),pager.getPageSize());
			PageHelper.orderBy(pager.getOrderBy() + (pager.getOrderType() == Pager.OrderType.desc ? " desc":""));
			list = baseDao.selectAll();
		}
		
		if(list != null && !list.isEmpty()){
			pager.setTotalCount(((Page)list).getTotal());
			pager.setList(list);
		}
			
		return pager;
	}

	@Override
	public PagerEx findByPager(PagerEx pagerex) {
		if(pagerex == null){
			return null;
		}
		
		List<T> list = null;
		List<PagerParam> params = pagerex.getParams();
		if(params == null || params.isEmpty()){
			return null;
		}
		
		PageHelper.startPage(pagerex.getPageNumber(),pagerex.getPageSize());
		PageHelper.orderBy(pagerex.getOrderBy() + (pagerex.getOrderType() == Pager.OrderType.desc ? " desc":""));
		
		StringBuffer sb = new StringBuffer();
		
		for(PagerParam param:params){
			sb.append(createCriteria(param));
		}
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("queryCondition", sb.toString());
		
		list = baseDao.selectByMap(map);
		if(list != null && !list.isEmpty()){
			pagerex.setTotalCount(((Page)list).getTotal());
			pagerex.setList(list);
		}
			
		return pagerex;
	}
	
	private String convert(Object obj){
		String value = null;
		if(obj == null){
			return null;
		}
		
		if(obj instanceof Date){
			value = DateUtils.format2String((Date) obj, "yyyy-MM-dd");
		}
		else
		{
			value = obj.toString();
		}
		
		return value;
	}
	
	private String createCriteria(PagerParam param){
		String criteria = null;
		switch(param.getConditionType()){
		case greaterThan:
			criteria = " and "+param.getPropertyName()+">'"+convert(param.getPropertyValue())+"'";
			break;
		case greaterThanOrEqual:
			criteria = " and "+param.getPropertyName()+">='"+convert(param.getPropertyValue())+"'";
			break;
		case isNotEqual:
			criteria = " and "+param.getPropertyName()+"<>'"+convert(param.getPropertyValue())+"'";
			break;
		case isNotNull:
			criteria = " and "+param.getPropertyName()+" is not null";
			break;
		case isNull:
			criteria = " and "+param.getPropertyName()+" is null";
			break;
		case lessThen:
			criteria = " and "+param.getPropertyName()+" < '"+convert(param.getPropertyValue())+"'";
			break;
		case equal:
			criteria = " and "+param.getPropertyName()+" = '"+convert(param.getPropertyValue())+"'";
			break;
		case LessThenOrEqual:
			criteria = " and "+param.getPropertyName()+" <= '"+convert(param.getPropertyValue())+"'";
			break;
		case isNotBetween:
			Object[] objs = (Object[]) param.getPropertyValue();
			criteria = " and "+param.getPropertyName()+" not between '"+convert(objs[0])+"' and '"+convert(objs[1])+"'";
			break;
		case between:
			Object[] params = (Object[]) param.getPropertyValue();
			criteria = " and "+param.getPropertyName()+" between '"+convert(params[0])+"' and "+convert(params[1])+"'";
			break;
		case isNotIn:
			String inNotParam = "(";
			List<?> notInlist = (List<?>) param.getPropertyValue();
			for(int i=0;i<notInlist.size();i++){
				Object obj = notInlist.get(i);
				if(i==notInlist.size()-1){
					inNotParam+=("'"+obj.toString()+"')");
				}else{
					inNotParam+=("'"+obj.toString()+"',");
				}
				
			}
			criteria = " and "+param.getPropertyName()+" not in "+inNotParam;
			break;
		case in:
			String inParam = "(";
			List<?> list = (List<?>) param.getPropertyValue();
			for(int i=0;i<list.size();i++){
				Object obj = list.get(i);
				if(i==list.size()-1){
					inParam+=("'"+obj.toString()+"')");
				}else{
					inParam+=("'"+obj.toString()+"',");
				}
				
			}
			criteria = " and "+param.getPropertyName()+" not in "+inParam;
			break;
		default:
			break;
		}
		
		return criteria;
	}
	
	/*private void createCriteria(PagerParam param,Example example){
		switch(param.getConditionType()){
		case greaterThan:
			example.createCriteria().andGreaterThan(param.getPropertyName(), param.getPropertyValue());
			break;
		case greaterThanOrEqual:
			example.createCriteria().andGreaterThanOrEqualTo(param.getPropertyName(), param.getPropertyValue());
			break;
		case isNotEqual:
			example.createCriteria().andNotEqualTo(param.getPropertyName(), param.getPropertyValue());
			break;
		case isNotNull:
			example.createCriteria().andIsNotNull(param.getPropertyName());
			break;
		case isNull:
			example.createCriteria().andIsNull(param.getPropertyName());
			break;
		case lessThen:
			example.createCriteria().andLessThan(param.getPropertyName(), param.getPropertyValue());
			break;
		case equal:
			example.createCriteria().andEqualTo(param.getPropertyName(), param.getPropertyValue());
			break;
		case LessThenOrEqual:
			example.createCriteria().andLessThanOrEqualTo(param.getPropertyName(), param.getPropertyValue());
			break;
		case isNotBetween:
			Object[] objs = (Object[]) param.getPropertyValue();
			example.createCriteria().andNotBetween(param.getPropertyName(), objs[0], objs[1]);
			break;
		case between:
			Object[] params = (Object[]) param.getPropertyValue();
			example.createCriteria().andBetween(param.getPropertyName(), params[0], params[1]);
			break;
		case isNotIn:
			example.createCriteria().andNotIn(param.getPropertyName(), (List<?>) param.getPropertyValue());
			break;
		case in:
			example.createCriteria().andIn(param.getPropertyName(), (List<?>) param.getPropertyValue());
			break;
		default:
			break;
		}
	}
*/
}
