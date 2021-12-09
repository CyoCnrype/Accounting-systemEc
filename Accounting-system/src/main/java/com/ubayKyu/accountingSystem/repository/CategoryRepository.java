package com.ubayKyu.accountingSystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ubayKyu.accountingSystem.dto.ListedCategory;
import com.ubayKyu.accountingSystem.entity.Category;
import com.ubayKyu.accountingSystem.entity.UserInfo;

public interface CategoryRepository extends JpaRepository<Category,Integer>{ 
	List<Category> findAll();
	
	//驗證
	// 流水帳分類管理的list資料查詢語法、配對到dto的ListedCategory
	@Query(value = "SELECT C.[create_date],C.[caption],COUNT(A.[categoryid]) [Count] FROM [category] AS C LEFT JOIN [accounting_note] AS A ON A.[categoryid] = C.[categoryid] WHERE C.[userid] = '94037537-7245-4530-BB73-01FE015973E7' GROUP BY C.[categoryid], C.[body], C.[caption], C.[create_date], C.[userid]")
    List<ListedCategory> GetListedCategory(String userid );
}
