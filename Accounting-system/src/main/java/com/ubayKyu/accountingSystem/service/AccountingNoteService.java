package com.ubayKyu.accountingSystem.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ubayKyu.accountingSystem.dto.AccountingNoteInterFace;
import com.ubayKyu.accountingSystem.entity.AccountingNote;
import com.ubayKyu.accountingSystem.repository.AccountingNoteRepository;

@Service
public class AccountingNoteService {
	@Autowired
	AccountingNoteRepository AccountingNoteRepository;
	// 將查出的List於html使用th:each印出流水帳列表
	public List<AccountingNoteInterFace> getAccountingNoteInterfaceListByUserID(String userid) {
		return AccountingNoteRepository.GetAccountingNoteInterfaceListByUserID(userid);
	}

	// 內建刪除語法
	public void deleteById(Integer accid) {
		AccountingNoteRepository.deleteById(accid);
	}

	// 計算小計(總和)
	public int getAccountingNoteAmountSum(String userid, int actType) {
		Integer answer = AccountingNoteRepository.FindAccountingNoteAmount(userid, actType);
		if (answer == null) {
			answer = 0;
		}
		return answer;
	}

	// 計算小計(單月)
	public int getAccountingNoteAmountOfMonth(String userid, int actType) {
		Integer answer = AccountingNoteRepository.FindAccountingNoteAmountOfMonth(userid, actType);
		if (answer == null) {
			answer = 0;
		}
		return answer;
	}

	//執行流水帳變更
	public Integer saveAccountingNote(AccountingNote accountingNote,String UserID) {
		AccountingNoteRepository.save(accountingNote);
		if (accountingNote.getAccID() == null) {
			List<AccountingNote> accountingNotes = AccountingNoteRepository.FindAccountingNoteByUserID(UserID);
			return accountingNotes.get(accountingNotes.size()).getAccID();
		}
		return accountingNote.getAccID();
	}

	//由ID取得流水帳列表
	public Optional<AccountingNote> getAccountingNoteByID(Integer accid) {
		Optional<AccountingNote> accountingNotes = AccountingNoteRepository.findById(accid);
		return accountingNotes;
	}

}
