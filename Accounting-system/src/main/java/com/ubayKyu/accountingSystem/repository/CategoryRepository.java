package com.ubayKyu.accountingSystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ubayKyu.accountingSystem.dto.CategoryInterFace;
import com.ubayKyu.accountingSystem.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, String> {
	List<Category> findAll();

	// 流水帳分類管理的list資料查詢語法、配對到dto的ListedCategory
	@Query(value = "  SELECT category.categoryid" + "      ,category.body" + "      ,category.caption"
			+ "      ,FORMAT(category.create_date, 'yyyy-MM-dd') create_date" + "      ,category.userid"
			+ "      ,COUNT(accounting_note.categoryid) count" + "  FROM category category"
			+ "  LEFT JOIN accounting_note accounting_note   ON accounting_note.categoryid = category.categoryid"
			+ "  WHERE category.userid =:userid"
			+ "  GROUP   BY category.categoryid ,category.body ,category.caption ,category.create_date,category.userid", nativeQuery = true)
	List<CategoryInterFace> FindCategoryModelListByUserid(@Param("userid") String userid);

	// 計算分類底下有無剩餘的流水帳數
	@Query(value = "  SELECT COUNT(accounting_note.categoryid) count" + "  FROM category category"
			+ "  LEFT JOIN accounting_note accounting_note   ON accounting_note.categoryid = category.categoryid"
			+ "  WHERE category.categoryid =:categoryid" + "  GROUP   BY category.categoryid", nativeQuery = true)
	int FindCountByCategoryid(@Param("categoryid") String categoryid);

	// 檢查是否有分類數存在
	@Query(value = "SELECT COUNT(category.categoryid) count\n"
			+ "             FROM category category           \n"
			+ "             WHERE category.categoryid =:categoryid", nativeQuery = true)
	int FindCategoryidNumber(@Param("categoryid") String categoryid);

}
