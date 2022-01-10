package com.ubayKyu.accountingSystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ubayKyu.accountingSystem.dto.CategoryInterFace;
import com.ubayKyu.accountingSystem.entity.Category;

@Repository
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
	@Query(value = "SELECT COUNT(category.categoryid) count" + " FROM category category"
			+ " WHERE category.categoryid =:categoryid", nativeQuery = true)
	int FindCategoryidNumber(@Param("categoryid") String categoryid);

	@Query(value = "SELECT COUNT(*) count" + " FROM  category"
			+ " WHERE userid =:userid AND caption =:caption", nativeQuery = true)
	int FindSameCategoryNumber(@Param("userid") String userid, @Param("caption") String caption);

	// 檢查是否有重複
	@Query(value = "SELECT caption " + "FROM category "
			+ "WHERE userid =:userid AND categoryid =:categoryid", nativeQuery = true)
	String FindCaptionByUseridAndCategoryid(@Param("userid") String userid, @Param("categoryid") String categoryid);

	@Query(value = "  SELECT *" + "  FROM  category" + "  WHERE userid =:userid", nativeQuery = true)
	List<Category> FindCategoryByUserID(@Param("userid") String userid);

}
