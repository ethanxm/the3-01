package com.the3.service.impl;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import com.the3.base.web.SearchFilter;
import com.the3.dto.service.ServiceReturnDto;
import com.the3.entity.cms.Channel;
import com.the3.repository.cms.ChannelRepository;
import com.the3.service.ChannelService;

/**
 * ChannelServiceImpl.java
 *
 * @author ETHAN
 * @time 2014年3月2日下午4:45:52
 */
@Service
public class ChannelServiceImpl implements ChannelService {
	
	private Logger logger = Logger.getLogger(ChannelServiceImpl.class);  
	
	@Autowired
	private MongoTemplate  mongoTemplate;
	
	@Autowired
	private ChannelRepository channelRepository;

	@Override
	public ServiceReturnDto<Channel> save(Channel entity) {
		boolean isSuccess = true;
		try {
			entity = channelRepository.save(entity);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			isSuccess = false;
			logger.error(e.getMessage());
		}
		return new ServiceReturnDto<Channel>(isSuccess,entity);
	}

	@Override
	public Page<Channel> getPage(Map<String,Object> parameters,PageRequest pageable) {
		
//		Page<Channel> page = channelRepository.findAll(pageable);
		
//		Map<String, SearchFilter> filters = SearchFilter.parse(parameters);
//		Specification<Channel> spec = DynamicSpecifications.bySearchFilter(filters.values(), Channel.class);
		return channelRepository.findAll(pageable);
	}
	
	@Override
	public Page<Channel> getPage(Pageable pageable) {
		
		Page<Channel> page = channelRepository.findAll(pageable);
		
		return page;
	}

	@Override
	public Channel getById(String id) {
		Channel entity = null;
		try {
			entity = channelRepository.findOne(id);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return entity;
	}

	@Override
	public boolean deleteById(String id) {
		boolean isSuccess = true;
		try {
			channelRepository.delete(id);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			isSuccess = false;
		}
		return isSuccess;
	}

	@Override
	public void testMongoTemplate() {
		Channel channel = new Channel();
		channel.setTitle("我是黄应锋");
		channel.setDescribe("asdfasdf");
		mongoTemplate.insert(channel);
		channel = mongoTemplate.findById("531dcc820e520e4b51fbecfe", Channel.class);
		System.out.println("channel:"+channel);
	}
	
	


}
