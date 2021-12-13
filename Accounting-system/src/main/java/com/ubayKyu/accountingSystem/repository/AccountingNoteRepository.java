package com.ubayKyu.accountingSystem.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ubayKyu.accountingSystem.dto.AccountingNoteInterFace;
import com.ubayKyu.accountingSystem.entity.AccountingNote;

public interface AccountingNoteRepository extends JpaRepository<AccountingNote, String> {

	// 按照時間排列查表(因效率不佳廢棄)
	@Query(value = "SELECT  [accid]" + "      ,[act_type]" + "      ,[amount]" + "      ,[body]" + "      ,[caption]"
			+ "      ,[categoryid]" + "      ,[create_date]" + "      ,[userid]"
			+ "  FROM [AccNoteJava].[dbo].[accounting_note]" + "  ORDER BY [create_date] ASC", nativeQuery = true)
	List<AccountingNote> GetAccOrderbyASC();

	// 查詢最早一筆時間
	@Query(value = "SELECT Min(accounting_note.create_date)" + "FROM accounting_note", nativeQuery = true)
	LocalDateTime GetFirstDate();

	// 查詢最晚一筆時間
	@Query(value = "SELECT Max(accounting_note.create_date)" + "FROM accounting_note", nativeQuery = true)
	LocalDateTime GetLastDate();

	//查詢資料數量
	@Query(value = "SELECT Count(*)" + "FROM accounting_note", nativeQuery = true)
	Integer GetCount();
	
	//按照ID查表
	// 流水帳分類管理的list資料查詢語法、配對到dto的ListedCategory
	@Query(value = "SELECT  [accid]"
			+ "      ,[act_type]"
			+ "      ,[amount]"
			+ "      ,accounting_note.[body]"
			+ "      ,accounting_note.[caption]"
			+ "      ,accounting_note.[categoryid]"
			+ "      ,accounting_note.[create_date]"
			+ "      ,accounting_note.[userid]"
			+ "	  ,category.caption AS categoryCaption"
			+ "  FROM [AccNoteJava].[dbo].[accounting_note]"
			+ "  LEFT JOIN [category] category   ON category.categoryid = accounting_note.categoryid"
			+ "  WHERE accounting_note.[userid] =:userid"
			, nativeQuery = true)
	List<AccountingNoteInterFace> GetAccNotebyUserId(@Param("userid") String userid);
	
	

}
