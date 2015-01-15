package com.the3.service.todo.impl;


import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.the3.base.repository.DynamicSpecifications;
import com.the3.base.repository.SearchFilter;
import com.the3.dto.common.ReturnDto;
import com.the3.entity.todo.Todo;
import com.the3.repository.todo.TodoRepository;
import com.the3.service.todo.TodoService;


@Service
@Transactional(readOnly = true)
public class TodoServiceImpl implements TodoService {
	
	private Logger logger = Logger.getLogger(TodoServiceImpl.class);  
	
	@Autowired
	private TodoRepository todoRepository;
	

	@Override
	@Transactional(readOnly = false)
	public ReturnDto save(Todo entity) {
		boolean isSuccess = true;
		String message = "保存成功";
		try {
			entity = todoRepository.save(entity);
		} catch (Exception e) {
			e.printStackTrace();
			isSuccess = false;
			message = "保存失败";
			logger.error(e.getMessage());
		}
		return new ReturnDto(isSuccess,message,entity);
	}


	@Override
	public Page<Todo> findPage(List<SearchFilter> filters,PageRequest pageable) {
		Page<Todo> result = null;
		
		try {
			Specification<Todo> spec = DynamicSpecifications.bySearchFilter(filters, Todo.class);
			
			result = todoRepository.findAll(spec, pageable);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

}


