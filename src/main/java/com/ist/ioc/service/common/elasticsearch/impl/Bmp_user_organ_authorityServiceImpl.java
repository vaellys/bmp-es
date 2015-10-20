package com.ist.ioc.service.common.elasticsearch.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ist.ioc.dao.Bmp_user_organ_authorityDao;
import com.ist.ioc.service.common.elasticsearch.Bmp_user_organ_authorityService;

@Service("bmp_user_organ_authorityServiceI")
public class Bmp_user_organ_authorityServiceImpl implements
		Bmp_user_organ_authorityService {

	@Resource
	private Bmp_user_organ_authorityDao  bmp_user_organ_authorityDao;
	/**
	 * <p>根据用户名查询对应的机构key列表</p>
	 * @param username 用户名
	 * @return List<String> 机构key列表
	 */
	@Override
	public List<String> getBmpOrganKeysByUsername(String username) {
		return bmp_user_organ_authorityDao.getBmpOrganKeysByUsername(username);
	}

}
