package com.ubayKyu.accountingSystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ubayKyu.accountingSystem.dto.ListedCategory;
import com.ubayKyu.accountingSystem.entity.Category;

public interface testRepo extends JpaRepository<ListedCategory, Integer> {

	// 流水帳分類管理的list資料查詢語法、配對到dto的ListedCategory
	@Query(value = " SELECT       " + "      C.[create_date]" + "	  	,C.[caption]"
			+ "      ,COUNT(A.[categoryid]) [Count]" + "  FROM [category] AS C"
			+ "  LEFT JOIN [accounting_note] AS A ON A.[categoryid] = C.[categoryid]" + "  WHERE C.[userid] = :userid "
			+ "  GROUP BY C.[categoryid], C.[body], C.[caption], C.[create_date], C.[userid]", nativeQuery = true)
	List<ListedCategory> GetListedCategory(@Param("userid") String userid);
}
