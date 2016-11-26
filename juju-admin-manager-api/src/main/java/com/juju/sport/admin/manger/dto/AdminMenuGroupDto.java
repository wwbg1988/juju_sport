package com.juju.sport.admin.manger.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.ToString;

@ToString
public class AdminMenuGroupDto  implements Serializable{
   
	private static final long serialVersionUID = 7922926852940318288L;


	private String id;

    
    private String menuGroupName;

   
    private Integer menuGroupOrder;
    
    
    private List<AdminMenuFunctionDto> functions = new ArrayList<AdminMenuFunctionDto>();
    

    public String getId() {
        return id;
    }

  
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

  
    public String getMenuGroupName() {
        return menuGroupName;
    }

    
    public void setMenuGroupName(String menuGroupName) {
        this.menuGroupName = menuGroupName == null ? null : menuGroupName.trim();
    }

    public Integer getMenuGroupOrder() {
        return menuGroupOrder;
    }

    
    public void setMenuGroupOrder(Integer menuGroupOrder) {
        this.menuGroupOrder = menuGroupOrder;
    }


	public List<AdminMenuFunctionDto> getFunctions() {
		return functions;
	}


	public void setFunctions(List<AdminMenuFunctionDto> functions) {
		this.functions = functions;
	}
    
}