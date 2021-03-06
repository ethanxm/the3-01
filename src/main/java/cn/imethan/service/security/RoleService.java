package cn.imethan.service.security;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import cn.imethan.dto.common.ReturnDto;
import cn.imethan.entity.security.Role;


/**
 * RoleService.java
 *
 * @author Ethan Wong
 * @time 2014年3月16日下午5:00:13
 */
public interface RoleService{
	
	/**
	 * 保存或更新
	 * @param entity
	 * @param resourcePermission
	 * @return
	 */
	public ReturnDto saveOrModify(Role entity,String resourcePermission);
	
	/**
	 * 获取分页列表
	 * @param parameters
	 * @param pageable
	 * @return
	 */
	public Page<Role> getPage(Map<String, Object> parameters,PageRequest pageable);
	
	/**
	 * 获取所有列表
	 * @return
	 */
	public List<Role> getAllList();
	
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	public ReturnDto deleteById(Long id);
	
	/**
	 * 根据ID获取
	 * @param id
	 * @return
	 */
	public Role getById(Long id);
	

}


