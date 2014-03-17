package com.the3.web.console.user;

import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;
import org.springframework.web.util.WebUtils;

import com.the3.base.web.BaseController;
import com.the3.base.web.SearchFilter;
import com.the3.base.web.SuperController;
import com.the3.dto.web.WebReturnDto;
import com.the3.entity.cms.Article;
import com.the3.entity.cms.Channel;
import com.the3.entity.user.Permission;
import com.the3.entity.user.Resource;
import com.the3.service.cms.ArticleService;
import com.the3.service.cms.ChannelService;
import com.the3.service.user.PermissionService;
import com.the3.service.user.ResourceService;
import com.the3.utils.Debug;

/**
 * ResourceController.java
 *
 * @author Ethan Wong
 * @time 2014年3月17日下午10:12:07
 */
@Controller
@RequestMapping("/console/user/resource")
public class ResourceController extends SuperController implements BaseController{
	
	@Autowired
	private ResourceService resourceService;
	

	@Override
	@RequestMapping(value="", method = {RequestMethod.GET,RequestMethod.POST})
	public String index(RedirectAttributesModelMap redirectAttributesModelMap) {
		return "redirect:/console/user/resource/"+defaultPage+"/"+defaultSize;
	}


	@Override
	@RequestMapping(value="/{page}/{size}", method = {RequestMethod.GET,RequestMethod.POST})
	public String list(Model model, ServletRequest request, @PathVariable int page, @PathVariable int size) {
		Map<String,Object> parameters = WebUtils.getParametersStartingWith(request, SearchFilter.prefix);
		
		page = page >=0 ? page : defaultPage;
		size = size >0 ? size : defaultSize;
		
		Page<Resource> result = resourceService.getPage(parameters,new PageRequest(page,size,Direction.DESC,"createTime"));
		model.addAttribute("result", result);
		
		return "console/user/resource";
	}


	@Override
	public String input(Model model) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String detail(Model model, String id, ServletResponse response) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String forModify(Model model, String id, int page, int size,
			ServletResponse response) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String delete(Model model, String id, int page, int size,
			RedirectAttributesModelMap redirectAttributesModelMap,
			ServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}
	

}