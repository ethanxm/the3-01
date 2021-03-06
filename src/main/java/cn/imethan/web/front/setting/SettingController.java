package cn.imethan.web.front.setting;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.imethan.common.encode.EncryptUtils;
import cn.imethan.dto.common.ReturnDto;
import cn.imethan.entity.security.User;
import cn.imethan.entity.system.Setting;
import cn.imethan.entity.system.SettingCode;
import cn.imethan.service.security.UserService;
import cn.imethan.service.system.SettingService;

/**
 * SettingController.java
 *
 * @author Ethan Wong
 * @time 2014年12月20日下午5:50:25
 */
@Controller
@RequestMapping("/setting")
public class SettingController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private SettingService settingService;
	
	/**
	 * 系统设置通用首页
	 * @param model
	 * @param type
	 * @return
	 */
	@RequiresUser//当前用户需为已认证用户或已记住用户 
	@RequestMapping("/{type}")
	public String setting(Model model,@PathVariable String type){
		
		model.addAttribute("type", type);
		
		if(!StringUtils.isEmpty(type)){
			
			switch(type){
				case "about":
					Setting setting = settingService.getByCode(SettingCode.RESUME.name());
					if(setting != null){
						model.addAttribute("isPublishResume", setting.getContent());
						model.addAttribute("description", setting.getDescription());
					} 
					break;
					
				case "profile":
					Subject subject =SecurityUtils.getSubject();
					if(subject.getPrincipal() != null){
						String username = subject.getPrincipal().toString();
						User user = userService.getByUsername(username);
						model.addAttribute("user", user);
					}
					break;
					
				case "main":
					Setting sitename = settingService.getByCode(SettingCode.SITENAME.name());
					Setting copyright = settingService.getByCode(SettingCode.COPYRIGHT.name());
					if(sitename != null){
						model.addAttribute("sitename", sitename);
					} 
					if(copyright != null){
						model.addAttribute("copyright", copyright);
					} 
					break;	
					
				default:break;
			}

			

			return "front/setting/setting-"+type;
		}
		
		return "front/setting/setting";
	}
	
	/**
	 * 更新基本信息
	 * @param user
	 * @param result
	 * @param request
	 * @return
	 */
	@RequiresUser//当前用户需为已认证用户或已记住用户 
	@ResponseBody
	@RequestMapping(value = "updateProfile" , method = {RequestMethod.POST})
	public ReturnDto updateProfile(@Valid @ModelAttribute("user") User user, BindingResult result,ServletRequest request){
		ReturnDto returnDto = new ReturnDto();
		if(result.hasFieldErrors()){
			returnDto.setMessage("参数验证出现异常:"+result.getFieldError().getDefaultMessage());
			returnDto.setSuccess(false);
		}else{
			returnDto = userService.updateProfile(user);
			
		}
		return returnDto;
	}
	
	/**
	 * 更新头像
	 * @param file
	 * @param model
	 * @param request
	 * @param userId
	 * @return
	 * @throws IOException
	 */
	@RequiresUser//当前用户需为已认证用户或已记住用户 
	@ResponseBody
	@RequestMapping("/updateAvatar")
	public String updateAvatar(@RequestParam MultipartFile file, Model model,HttpServletRequest request,@RequestParam Long userId) throws IOException{
		
        if(file.isEmpty()){  
            System.out.println("文件未上传");  
        }else{  
            System.out.println("文件长度: " + file.getSize());  
            System.out.println("文件类型: " + file.getContentType());  
            System.out.println("文件名称: " + file.getName());  
            System.out.println("文件原名: " + file.getOriginalFilename());  
            String realPath = request.getSession().getServletContext().getRealPath("/upload/avatar"); 
            String currentTimeMillis = String.valueOf(System.currentTimeMillis());
            String saveFileName = currentTimeMillis+"_"+file.getOriginalFilename();
            FileUtils.copyInputStreamToFile(file.getInputStream(), new File(realPath,saveFileName));
            
            userService.updateAvatar(userId,saveFileName);
        }  
       
		
		return "success";
	}
	
	/**
	 * 密码校验
	 * @param password
	 * @return
	 */
	@RequiresUser//当前用户需为已认证用户或已记住用户 
	@ResponseBody
	@RequestMapping("/validatePassword")
	public boolean validatePassword(@RequestParam String password){
		boolean flag = false;
		Subject subject =SecurityUtils.getSubject();
		if(subject.getPrincipal() != null){
			String username = subject.getPrincipal().toString();
			User user = userService.getByUsername(username);
			if(EncryptUtils.Encrypt(password, "SHA-1").equals(user.getPassword())){
				flag = true;
			}
		}
		return flag;
	}
	
	/**
	 * 更新密码
	 * @param password
	 * @return
	 */
	@RequiresUser//当前用户需为已认证用户或已记住用户 
	@ResponseBody
	@RequestMapping("/updatePassword/{password}")
	public ReturnDto updatePassword(@PathVariable String password){
		ReturnDto returnDto = new ReturnDto();
		Subject subject =SecurityUtils.getSubject();
		if(subject.getPrincipal() != null){
			String username = subject.getPrincipal().toString();
			returnDto = userService.updatePassword(username,EncryptUtils.Encrypt(password, "SHA-1"));
		}
		return returnDto;
	}
	
	@RequiresUser//当前用户需为已认证用户或已记住用户 
	@ResponseBody
	@RequestMapping(value="/updateAboutSet", method = RequestMethod.POST)
	public ReturnDto updateAboutSet(@RequestParam ("isPublish") boolean isPublish,@RequestParam("content") String content){
		
		System.out.println("isPublish:"+isPublish);
		System.out.println("content:"+content);
		
		
		return settingService.updateAboutSet(isPublish,content);
	}
	
	@RequiresUser//当前用户需为已认证用户或已记住用户 
	@ResponseBody
	@RequestMapping(value="/updateSiteInfo", method = RequestMethod.POST)
	public ReturnDto updateSiteInfo(@RequestParam ("sitename") String sitename,
			@RequestParam("copyright") String copyright,HttpServletRequest request){
		
		ReturnDto result = settingService.updateSiteInfo(sitename,copyright);
		if(result.isSuccess()){
			ServletContext servletContext = request.getSession().getServletContext();
			servletContext.setAttribute("SITENAME", sitename);
			servletContext.setAttribute("COPYRIGHT", copyright);
		}
		return result;
	}

}


