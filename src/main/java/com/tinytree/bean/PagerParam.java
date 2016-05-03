package com.tinytree.bean;

import java.util.List;

/**
 * @Description：分页参数
 * @ClassName: PagerParam
 * @Author：tangyang
 * @Date：2016-1-24 (变更历史)
 * 
 * 如：eric 2015/01/08 修改了删除功能
 * 
 */
public class PagerParam {
	public enum ConditionType{
		equal,isNotEqual, between,isNotBetween,greaterThan,greaterThanOrEqual,lessThen,LessThenOrEqual,
		in,isNotIn,isNotNull,isNull
	}
	
	private String propertyName;
	
	private Object propertyValue;
	
	private ConditionType conditionType;
	
	public String getPropertyName() {
		return propertyName;
	}

	public Object getPropertyValue() {
		return propertyValue;
	}

	public ConditionType getConditionType() {
		return conditionType;
	}

	/**
	 * 设置参数信息，
	 * in,isNotIn类型，参数必须是List
	 * between,isNotBetween类型，参数必须是数组，且长度必须为2
	 * 其余类型参数统一为Object对象
	 * @param conditionType 条件枚举
	 * @param propertyName 参数名称
	 * @param propertyValue 参数值
	 * 
	 * @throws Exception
	 */
	public void setParam(ConditionType conditionType,String propertyName,Object propertyValue) throws Exception{
		checkParam(conditionType,propertyValue);
		this.propertyName = propertyName;
		this.propertyValue = propertyValue;
		this.conditionType = conditionType;
	}
	
	private void checkParam(ConditionType conditionType,Object propertyValue) throws Exception{
		boolean flag = false;
		String message = "";
		switch(conditionType){
			case greaterThan:
			case greaterThanOrEqual:
			case isNotEqual:
			case isNotNull:
			case isNull:
			case lessThen:
			case equal:
			case LessThenOrEqual:{
				if(!(propertyValue instanceof Object)){
					flag = true;
					message = "param must be object";
				}
			}
				break;
			case isNotBetween:
			case between:{
				if(propertyValue instanceof Object[]){
					Object[] params = (Object[]) propertyValue;
					if(params.length !=2){
						flag = true;
						message = "between param length must 2";
					}
				}else{
					flag = true;
					message = "between param must by array";
				}
			}
				break;
			case isNotIn:
			case in:{
				if(!(propertyValue instanceof List)){
					flag = true;
					message = "in param must be list";
				}
			}
				break;
			default:
				break;
		}
		if(flag){
			throw new Exception(message);
		}
	}
	
}
