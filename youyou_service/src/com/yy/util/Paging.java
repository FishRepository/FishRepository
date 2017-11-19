package com.yy.util;

import java.util.List;
import java.util.Map;


public class Paging<T> {
	/**
	 * 开始记录行*
	 */
	private int startRecord;//
	/**
	 * 结束记录行*
	 */
	private int endRecord;//
	/**
	 * 当前页码
	 */
	private int nowPage;
	/**
	 * 下一页页码*
	 */
	private int nextPage;//
	/**
	 * 上一页页码*
	 */
	private int previousPage;//
	/**
	 * 总页数*
	 */
	private int totalCountPage;//
	/**
	 * 总记录数
	 */
	private int countRecord;
	/**
	 * 每页记录数
	 */
	private int countOfPerPage;
	/**
	 * 条件字符
	 */
	private String whereStr;
	/**
	 * 排序字符
	 */
	private String orderStr;
	/**
	 * 记录集
	 */
	private List<T> objects;
	
	private Map<String,Object> map;

	public static final int SQL_START_RECORD_0 = 0;
	public static final int SQL_START_RECORD_1 = 1;

	/**
	 * 初始化页码
	 * 0为首行指针为0的数据库 1为首行指针为1的数据库
	 */
	public void init(int add) {
		/** 计算总页数 */
		this.getTotalCountPage();
		/** 计算开始记录行 */
		this.getStartRecord(add);
		/** 计算结束记录行 */
		this.getEndRecord();
		/** 计算上一页 */
		this.getPreviousPage();
		/** 计算下一页 */
		this.getNextPage();
	}

	/**
	 * @return
	 * 
	 */
	public int getStartRecord() {
		return startRecord;
	}

	/**
	 * @return
	 * 
	 */
	public String getOrderStr() {
		return orderStr;
	}

	/**
	 * @param orderStr
	 * 
	 */
	public void setOrderStr(String orderStr) {
		this.orderStr = orderStr;
	}

	/**
	 * @return
	 * 
	 */
	public String getWhereStr() {
		return whereStr;
	}

	/**
	 * @param whereStr
	 * 
	 */
	public void setWhereStr(String whereStr) {
		this.whereStr = whereStr;
	}

	/**
	 * @return
	 * 
	 */
	public List<T> getObjects() {
		return objects;
	}

	/**
	 * @param objects
	 * 
	 */
	public void setObjects(List<T> objects) {
		this.objects = objects;
	}

	public int getCountOfPerPage() {
		return countOfPerPage;
	}

	public void setCountOfPerPage(int countOfPerPage) {
		this.countOfPerPage = countOfPerPage;
	}

	public int getCountRecord() {
		return countRecord;
	}

	public void setCountRecord(int countRecord) {
		this.countRecord = countRecord;
	}

	/** 计算开始记录行 */
	public int getStartRecord(int add) {
		startRecord = ((nowPage - 1) * countOfPerPage) + add;
		return startRecord;
	}

	/**
	 * 计算结束记录行
	 */
	public int getEndRecord() {
		endRecord = nowPage * countOfPerPage;
		return endRecord;
	}

	/**
	 * 计算上一页
	 */
	public int getPreviousPage() {
		if (nowPage <= 1) {
			nowPage = 1;
			previousPage = 1;
		} else {
			previousPage = nowPage - 1;
		}
		return previousPage;
	}

	/**
	 * 计算下一页
	 */
	public int getNextPage() {
		if (nowPage >= totalCountPage) {
			nowPage = totalCountPage;
			nextPage = totalCountPage;
		} else {
			nextPage = nowPage + 1;
		}
		return nextPage;
	}

	public int getNowPage() {
		return nowPage;
	}

	public void setNowPage(int nowPage) {
		this.nowPage = nowPage;
	}

	/**
	 * 计算总页数
	 */
	public int getTotalCountPage() {
		if (countRecord % countOfPerPage == 0) {
			totalCountPage = countRecord / countOfPerPage;
		} else {
			totalCountPage = (countRecord / countOfPerPage) + 1;
		}
		return totalCountPage;
	}

	public void setStartRecord(int startRecord) {
		this.startRecord = startRecord;
	}

	public Map<String, Object> getMap() {
		return map;
	}

	public void setMap(Map<String, Object> map) {
		this.map = map;
	}
}
