package com.juju.sport.admin.controller.manage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.juju.sport.admin.manager.service.IMenuService;
import com.juju.sport.admin.manger.dto.AdminMenuFunctionDto;
import com.juju.sport.common.model.ListResult;
import com.juju.sport.common.model.RequestResult;

/**
 * 
 * @author rkzhang
 *
 */
@Controller
@RequestMapping(value="/menu")
public class MenuManagerController {

    protected static final Log logger = LogFactory.getLog(MenuManagerController.class);
    
    @Autowired
    private IMenuService menuService;
    
    @RequestMapping(value="/index.do")
    public String index(){
        logger.debug("visit role manager page");
        return "/admin/manager/menu";
    }
    
    @ResponseBody
	@RequestMapping(value="/findAll.do")
    public ListResult<AdminMenuFunctionDto> findAll(){
    	return menuService.findAll();
    }
    
	@ResponseBody
	@RequestMapping(value="/saveOrUpdate.do")
    public RequestResult saveOrUpdate(AdminMenuFunctionDto menu){
		logger.debug(menu);
		return menuService.saveOrUpdate(menu);
	}
	
	@ResponseBody
	@RequestMapping(value="/deleteMenu.do")
	public RequestResult deleteMenu(@RequestParam("menuId")String menuId){
		return menuService.deleteMenu(menuId);
	}
}
