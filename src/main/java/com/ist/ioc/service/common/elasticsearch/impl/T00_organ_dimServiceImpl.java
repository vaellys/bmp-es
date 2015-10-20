package com.ist.ioc.service.common.elasticsearch.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.ist.ioc.dao.T00_organ_dimDao;
import com.ist.ioc.service.common.elasticsearch.T00_organ_dimService;


public class T00_organ_dimServiceImpl implements T00_organ_dimService {
	
	@Resource
	private T00_organ_dimDao  t00_organ_dimDAO;
	
	/**
	 * 根据机构key查询机构层次
	 * @param organkeys
	 * @return List<T00_organ_dimDTO>
	 */
	public List<Map<String, Object>> getOrganDimByOrgankeys(List<String> organkeys){
		return t00_organ_dimDAO.getOrganDimByOrgankeys(organkeys);
	}
}
