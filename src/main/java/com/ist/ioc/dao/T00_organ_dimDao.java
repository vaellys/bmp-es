package com.ist.ioc.dao;

import java.util.List;
import java.util.Map;

public interface T00_organ_dimDao {
	/**
	 * 根据机构key查询机构层次
	 * @param T00_organ_dimDTO
	 * @return
	 */
	public List<Map<String, Object>> getOrganDimByOrgankeys(List<String> organkeys);
	
}
