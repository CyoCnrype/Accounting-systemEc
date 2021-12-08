package com.ubayKyu.accountingSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import com.ubayKyu.accountingSystem.entity.UserInfo;


public interface UserInfoRepository extends JpaRepository<UserInfo,Integer>{
	
	//UserInfo findByAccountAndPwd(String name, String password);
	List<UserInfo> findAll();
	
	//JpaRepository內建的查詢
	//UserInfo findByAccountAndPwd(String account,String pwd);
	
	 // 自定義SQL查詢(登入)
    @Query(value = "SELECT [userid]"
    		+ "      ,[account]"
    		+ "      ,[create_date]"
    		+ "      ,[email]"
    		+ "      ,[name]"
    		+ "      ,[pwd]"
    		+ "      ,[user_level]"
    		+ "  FROM [SampleDB].[dbo].[user_info]"
    		+"  WHERE [account] =:account AND [pwd]=:pwd"
    		, nativeQuery = true)
    UserInfo GetUserByLogin(@Param("account") String account , @Param("pwd") String pwd);
    
    //查詢資料數量
    @Query(value = "SELECT Count(*)" + "FROM user_info", nativeQuery = true)
	Integer GetCount();
	
}



