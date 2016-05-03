package com.tinytree.bean;

import java.util.List;

/**
 * @Description:分页参数bean,提供更强大分页参数设置，支持单表多个条件的查询
 * @ClassName: PagerEx
 * @Author：tangyang
 * @Date 2016-1-13
 * (变更历史)
 * 如：eric	2015/01/08 修改了删除功
 */
public class PagerEx extends Pager{
	
	private List<PagerParam> params;

	public List<PagerParam> getParams() {
		return params;
	}

	public void setParams(List<PagerParam> params) {
		this.params = params;
	}
	
}
