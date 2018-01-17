package com.spring.data.service;

import java.util.HashMap;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.data.dao.MainDao;
import com.spring.data.serviceimpl.MainServiceImpl;

@Service
public class MainService implements MainServiceImpl{
	
	@Autowired
	MainDao dao;
	
	public static final Logger log = LogManager.getLogger(MainService.class);
	
	public List<Object> getNow(HashMap<String,Object> pMap){
		log.info("service-getnow");
		return dao.getNow(pMap);
	}
	
	
	

}
