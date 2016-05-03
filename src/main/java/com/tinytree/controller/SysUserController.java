package com.tinytree.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tinytree.bean.LoginCommand;
import com.kungfu.dental.entity.SysUser;
import com.kungfu.dental.service.SysUserService;
import com.kungfu.dental.util.GlobalUtil;

@Controller
public class SysUserController extends BaseController{
	private static final Logger logger = LoggerFactory.getLogger(SysUserController.class);
	private static final String SUCCESS = "redirect:/sysUser/manageSysUsers";
	private static final String ADD = "addSysUser";
	@Autowired
	private SysUserService sysUserService;
	
	@RequestMapping(value="/sysUser/showLogin")
	public String showLogin(Map<String, Object> map){
		map.put("loginCommand", new LoginCommand());
		return "login";
		
	}
	/**
	 * 系统用户登录
	 * @param model
	 * @param command
	 * @param request
	 * @param response
	 * @return 
	 */
	@ResponseBody
	@RequestMapping(value="/sysUser/islogin",method= RequestMethod.POST)
    public String isLogin(Model model, @ModelAttribute LoginCommand command,
    	HttpServletRequest request,HttpServletResponse response) {
		String status= "";
		JSONObject jsonObject =new JSONObject();
		try{
			do{
				if(command==null){
					status = GlobalUtil.PARAM_ERROR ;
					jsonObject.put("status", status);
					logger.error("param error LoginCommand is null");
					break;
				}
				String password = command.getPassword();
				String username = command.getUsername();
				SysUser sysUser = sysUserService.findSysUserByNameAndPassword(username, password);
				if(sysUser==null){
					status = GlobalUtil.PARAM_ERROR;
					jsonObject.put("status", status);
					logger.error("param error LoginCommand is null");
					break;
				}
			}while(false);
		}catch(Exception e){
			status = GlobalUtil.SYS_ERROR;
			jsonObject.put("status", status);
		}
		return "redirect:/sysUser/home";
    }
	
	@RequestMapping(value="/sysUser/home")
	public String isLogin(){
		return "home";
	}
    @RequestMapping(value = "/sysUser/manageSysUsers" ,method=RequestMethod.GET)
    public String manageSysUsers(Map<String, Object> request) {
    	List<SysUser> sysUsers = sysUserService.getAll();
    	request.put("sysUsers", sysUsers);
    	System.out.println(sysUsers);
    	return "listSysUser";
    }

	
	@ResponseBody
	@RequestMapping(value="/sysUser/changePassword",method= RequestMethod.POST)
	public String changePassword(HttpServletRequest request,HttpServletResponse response){
		
		
		
		return null;
	}
	
	/**
	 * 添加系统用户 /sysUser/addSysUser
	 * @param map
	 * @return add.jsp页面
	 */
	@RequestMapping(value="/sysUser/addSysUser")
	public String addSysUser(Map<String, Object> map){
		map.put("sysUser", new SysUser());
		return "addSysUser";
	}
	/**
	 * 修改系统用户 /sysUser/addSysUser/{id} 传递修改数据的id
	 * @param id
	 * @param map
	 * @return add.jsp 同时在map里面存放了该id所对应的数据,用于回显
	 */
	@RequestMapping(value="/sysUser/addSysUser/{id}")
	public String addSysUser(@PathVariable("id") String id, Map<String, Object> map){
		map.put("sysUser", sysUserService.get(id));
		return ADD;
	}
	/**
	 * 保存和修改 根据请求的类型不同 执行不同的操作 
	 * @param sysUser
	 * @return 返回 listSysUser.jsp
	 */
	@RequestMapping(value="/sysUser/saveSysUser", method={RequestMethod.POST, RequestMethod.PUT})
	public String save(SysUser sysUser){
		if(sysUser.getId() == null)
			sysUserService.save(sysUser);
		else
			sysUserService.update(sysUser);
		return SUCCESS;
	}
	/**
	 * 删除系统用户 /sysUser/deleteSysUser/{id} 调用service 删除方法
	 * @param id
	 * @return 返回到 listSysUser.jsp
	 */
	@RequestMapping(value="/sysUser/deleteSysUser/{id}")
	public String deleteSysUser(@PathVariable("id") String id){
		sysUserService.delete(id);
		return SUCCESS;
	}
	
}
