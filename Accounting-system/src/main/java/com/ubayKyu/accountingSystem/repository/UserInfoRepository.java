package com.ubayKyu.accountingSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import com.ubayKyu.accountingSystem.dto.UserInfoInterface;
import com.ubayKyu.accountingSystem.entity.UserInfo;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo,String>{
	
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
    		+ "      ,[edit_date]"
    		+ "  FROM [AccNoteJava].[dbo].[user_info]"
    		+"  WHERE [account] =:account AND [pwd]=:pwd"
    		, nativeQuery = true)
    UserInfo GetUserByLogin(@Param("account") String account , @Param("pwd") String pwd);
    
    //查詢資料數量
    @Query(value = "SELECT Count(*)" + "FROM user_info", nativeQuery = true)
	Integer GetCount();
    
  //取得會員資訊(Interface)
    @Query(value = "SELECT [userid]"
                + " ,[account]"
                 + " ,FORMAT([create_date], 'yyyy/MM/dd hh:mm') AS [create_date]"
                 + " ,[email]"
                 + " ,[name]"
                 + " ,[user_level]"
                 + " FROM [user_info]"
                 + " ORDER BY [create_date] DESC"
                 , nativeQuery = true)
     List<UserInfoInterface> GetUserInfoInterface();

    //刪除會員、流水帳及分類資訊
    @Query(value = "DELETE FROM [user_info]"
                + "    WHERE [user_info].[userid] =:userid"
                + " DELETE FROM [accounting_note]"
                + "    WHERE [accounting_note].[userid] =:userid"
                + "    DELETE FROM [category]"
                + "    WHERE [category].[userid] =:userid"
                + "    SELECT COUNT(*) FROM [user_info]"
                , nativeQuery = true)
     Integer DeleteUserInfoAccountingNoteAndCategoryByUserID(@Param("userid") String userid);
	
    @Query(value = "SELECT [userid]"
            + "     ,[account]"
             + "        ,FORMAT([create_date], 'yyyy/MM/dd hh:mm:ss') AS [create_date]"
             + "        ,[email]"
             + "        ,[name]"
             + "        ,[user_level]"
             + "        ,FORMAT([edit_date], 'yyyy/MM/dd hh:mm:ss') AS [edit_date]"
             + "    FROM [user_info]"
             + "    WHERE [user_info].[userid] =:userid"
             , nativeQuery = true)
    Optional<UserInfoInterface> FindUserInfoInterfaceByID(@Param("userid") String userid);
    
    //檢查帳號是否重複
    @Query(value = "  SELECT COUNT(*)"
            + "  FROM  user_info"
            + "  WHERE account =:account", nativeQuery = true)
    int FindUserAccountByAccountAndUserID(@Param("account") String account);
    
}



