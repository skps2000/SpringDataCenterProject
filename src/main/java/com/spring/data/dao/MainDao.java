package com.spring.data.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MainDao {

	@Autowired
	SqlSession sqlsession;
	
	public static final Logger log = LogManager.getLogger(MainDao.class);
	
	public List<Object> getNow(HashMap<String,Object> pMap){
		log.info("dao-getnow");
		return sqlsession.selectList("mainMapper.getNow", pMap);
	}
	
	
	
	
	
	
	
	
	

}
