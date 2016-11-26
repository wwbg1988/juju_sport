package com.juju.sport.user.dto;
import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

public class UserAccountDto implements Serializable{
		@Getter
	    @Setter
	    private String id;

	    @Getter
	    @Setter
	    private String userAccount;

	    @Getter
	    @Setter
	    private String password;

	    @Getter
	    @Setter
	    private Integer type;
	    
	    @Getter
	    @Setter
	    private String cardNo;
	    
	    @Getter
	    @Setter
	    private String thirdLogin;

	    @Getter
	    @Setter
	    private int thirdType;
	    
	    @Getter
	    @Setter
	    private String validCode;
	    
	    @Getter
        @Setter
        private String cardName;
}
