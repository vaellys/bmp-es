package com.ist.common.es.util.page;

import java.util.ArrayList;
import java.util.List;

/**
 * 列表分页。包含list属性。
 * 
 * @author qianguobing
 * 
 */
@SuppressWarnings("serial")
public class Pagination extends SimplePage implements java.io.Serializable,
		Paginable {

	public Pagination() {
	}

	/**
	 * 构造器
	 * 
	 * @param pageNo
	 *            页码
	 * @param pageSize
	 *            每页几条数据
	 * @param totalCount
	 *            总共几条数据
	 */
	public Pagination(int pageNo, int pageSize, int totalCount) {
		super(pageNo, pageSize, totalCount);
	}

	/**
	 * 构造器
	 * 
	 * @param pageNo
	 *            页码
	 * @param pageSize
	 *            每页几条数据
	 * @param totalCount
	 *            总共几条数据
	 * @param list
	 *            分页内容
	 */
	public Pagination(int pageNo, int pageSize, int totalCount, List<?> list) {
		super(pageNo, pageSize, totalCount);
		this.list = list;
	}
	
	/**
     * 构造器
     * 
     * @param pageNo
     *            页码
     * @param pageSize
     *            每页几条数据
     * @param totalList
     *            总共数据
     */
	public Pagination(int pageNo, int pageSize, List<?> totalList, int totalCount) {
	    super(pageNo, pageSize, totalCount);
        this.list = totalList.subList(getCurrentNum(pageNo, pageSize), isLastPage() ? totalCount : cpn(pageNo)*cps(pageSize));
	}
	
	/**
	 * 第一条数据位置
	 * 
	 * @return
	 */
	public int getFirstResult() {
		return (pageNo - 1) * pageSize;
	}

	/**
	 * 当前页的数据
	 */
	private List<?> list;

	/**
	 * 获得分页内容
	 * 
	 * @return
	 */
	public List<?> getList() {
		return list;
	}

	/**
	 * 设置分页内容
	 * 
	 * @param list
	 */
	@SuppressWarnings("rawtypes")
    public void setList(List list) {
		this.list = list;
	}
	
	public static void main(String[] args) {
        List<String> s = new ArrayList<String>();
        s.add("111");
        s.add("222");
        s.add("333");
        s.add("444");
        s.add("555");
        s.add("666");
        s.add("777");
        s.add("888");
        s.add("999");
        Pagination p = new Pagination(5, 2, s, 9);
        System.out.println(p.getList());
    }
}
