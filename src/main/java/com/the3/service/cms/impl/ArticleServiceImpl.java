package com.the3.service.cms.impl;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.the3.base.repository.DynamicSpecifications;
import com.the3.base.repository.SearchFilter;
import com.the3.dto.common.ReturnDto;
import com.the3.entity.cms.Article;
import com.the3.entity.cms.Channel;
import com.the3.repository.cms.ArticleRepository;
import com.the3.repository.cms.ChannelRepository;
import com.the3.service.cms.ArticleService;
import com.the3.service.cms.ChannelService;

/**
 * ArticleServiceImpl.java
 *
 * @author Ethan Wong
 * @time 2014年3月2日下午4:45:49
 */
@Service
@Transactional(readOnly = true)
public class ArticleServiceImpl implements ArticleService {
	
	private Logger logger = Logger.getLogger(ArticleServiceImpl.class);  
	
	@Autowired
	private ArticleRepository articleRepository;
	@Autowired
	private ChannelRepository channelRepository;
	@Autowired
	private ChannelService channelService;
	
	@Override
	@Transactional(readOnly = false)
	public ReturnDto saveOrModify(Article entity) {
		boolean isSuccess = true;
		String message = "保存成功";
		try {
			Channel channel = null;
			Long id = entity.getId();
			
			if(id !=null){
				//修改文章
				Article articleDb = this.getById(entity.getId());
				Channel channelDb = articleDb.getChannel();
				
				Channel channelNow = channelRepository.getOne(entity.getChannel().getId());
				if(channelDb.getId() != channelNow.getId()){
					//更新文章数量
					channelService.updateArticleAmount(channelDb.getId(),-1);
					channelService.updateArticleAmount(channelNow.getId(),+1);
					channel = channelNow;
				}else{
					channel = channelDb;
				}
				
				entity.setModifyTime(new Date());
				entity.setChannel(channel);
				articleRepository.save(entity);
				
			}else{
				//新增加文章
				
				//获取栏目信息
				channel = channelRepository.getOne(entity.getChannel().getId());
				
				//更新文章数量
				channelService.updateArticleAmount(channel.getId(),1);
				
				entity.setChannel(channel);
				articleRepository.save(entity);
			}

		} catch (Exception e) {
			e.printStackTrace();
			isSuccess = false;
			message = "保存失败";
			logger.error(e.getMessage());
		}
		return new ReturnDto(isSuccess , message , entity);
	}

	@Override
	public Article getById(Long id) {
		Article article = new Article();
		try {
			article = articleRepository.findOne(id);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return article;
	}

	@Override
	@Transactional(readOnly = false)
	public ReturnDto deleteById(Long id) {
	
		
		boolean isSuccess = true;
		String message = "删除成功";
		try {
			//更新文章数
			Article article = articleRepository.getOne(id);
			Channel channel = article.getChannel();
			
			//更新文章数量
			channelService.updateArticleAmount(channel.getId(),-1);
			
			articleRepository.delete(id);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return new ReturnDto(isSuccess , message);
	}


	@Override
	public Page<Article> findPage(List<SearchFilter> filters, PageRequest pageable) {
		Page<Article> result = null;
		try {
			//如果没有登录不展现未发布文章
			if(!SecurityUtils.getSubject().isAuthenticated()){
		    	SearchFilter articleFilter2 = new SearchFilter("channel.isPublish",SearchFilter.Operator.EQ,true);
		    	filters.add(articleFilter2);
		    	
		    	SearchFilter articleFilter3 = new SearchFilter("isPublish",SearchFilter.Operator.EQ,true);
		    	filters.add(articleFilter3);
			}
	    	
			Specification<Article> spec = DynamicSpecifications.bySearchFilter(filters, Article.class);
			result = articleRepository.findAll(spec, pageable);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	@Transactional(readOnly = false)
	public ReturnDto updatePublish(Long id) {
		boolean flag = true;
		String message = "更新成功";
		boolean publish = true;
		if(this.getById(id).isPublish()){
			publish = false;
		};
		
		try {
			articleRepository.updatePublish(id,publish);
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
			message = "更新失败";
		}
		
		return new ReturnDto(flag,message);
	}
}
