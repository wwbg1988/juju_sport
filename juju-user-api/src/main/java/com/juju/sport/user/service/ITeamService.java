package com.juju.sport.user.service;

import com.juju.sport.user.dto.TeamDto;

public interface ITeamService {

	TeamDto findByTeamId(String id);
}
