package com.ubayKyu.accountingSystem.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ubayKyu.accountingSystem.dto.AccountingNoteInterFace;
import com.ubayKyu.accountingSystem.entity.AccountingNote;

@Repository
public interface AccountingNoteRepository extends JpaRepository<AccountingNote, Integer> {

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
	
	//取得登入者的流水帳資訊(Interface)
    @Query(value = "SELECT A.[accid]"
                + "        ,A.[act_type]"
                + "        ,A.[amount]"
                + "        ,A.[body]"
                + "        ,A.[caption]"
                + "        ,A.[categoryid]"
                + "        ,FORMAT(A.[create_date], 'yyyy/MM/dd') AS [create_date]"
                + "        ,A.[userid]"
                + "        ,C.[caption] AS [categoryCaption]"
                + "    FROM [accounting_note] AS A"
                + "    LEFT JOIN [category] AS C ON C.[categoryid] = A.[categoryid]"
                + " WHERE A.[userid] =:userid"
                + " GROUP BY A.[accid], A.[act_type], A.[amount], A.[body], A.[caption], A.[categoryid], A.[create_date], A.[userid], C.[caption]"
                + " ORDER BY [create_date] DESC, [amount] DESC"
                , nativeQuery = true)
    List<AccountingNoteInterFace> GetAccountingNoteInterfaceListByUserID(@Param("userid") String userid);
    
    //得到小計(全體)
    @Query(value = " SELECT SUM(amount) AS Amount"
            + "    FROM accounting_note"
            + "    WHERE userid=:userid AND act_type=:actType"
            , nativeQuery = true)
    Integer FindAccountingNoteAmount(@Param("userid") String userid, @Param("actType") int actType);
    
    //得到小計(單月)
    @Query(value = "DECLARE @firstdate  DATETIME"
    		+ " SET @firstdate  = dateadd(m, datediff(m,0,getdate()),0)"
    		+ " DECLARE @lastdate  DATETIME"
    		+ " SET @lastdate  =dateadd(day ,-1, dateadd(m, datediff(m,0,getdate())+1,0))"
    		+ " SELECT SUM(amount) AS Amount"
    		+ " FROM accounting_note"
    		+ " WHERE userid=:userid AND act_type=:actType AND create_date BETWEEN @firstdate AND @lastdate"
    		,nativeQuery = true)
	Integer FindAccountingNoteAmountOfMonth(@Param("userid") String userid, @Param("actType") int actType);

}
