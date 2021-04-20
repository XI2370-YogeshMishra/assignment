package com.xebia.tennistest.repository;

import org.springframework.data.repository.CrudRepository;

import com.xebia.tennistest.entity.LeagueGroup;

public interface GroupRepository extends CrudRepository<LeagueGroup, Long> {
	

	}
