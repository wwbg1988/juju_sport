package com.juju.sport.admin.manger.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by rkzhang on 14-9-25.
 */
@ToString
public class RoleDto implements Serializable {

    @Getter
    @Setter
    private String id;

    @Getter
    @Setter
    private String roleName;

    @Getter
    @Setter
    private String roleDescription;
}
