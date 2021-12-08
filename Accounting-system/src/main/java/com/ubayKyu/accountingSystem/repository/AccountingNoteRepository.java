package com.ubayKyu.accountingSystem.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.ubayKyu.accountingSystem.entity.AccountingNote;

public interface AccountingNoteRepository extends JpaRepository<AccountingNote, Integer> {
	List<AccountingNote> findAll();

	// 按照時間排列查表(因效率不佳廢棄)
	@Query(value = "SELECT  [accid]" + "      ,[act_type]" + "      ,[amount]" + "      ,[body]" + "      ,[caption]"
			+ "      ,[categoryid]" + "      ,[create_date]" + "      ,[userid]"
			+ "  FROM [SampleDB].[dbo].[accounting_note]" + "  ORDER BY [create_date] ASC", nativeQuery = true)
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

}
