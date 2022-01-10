package com.ubayKyu.accountingSystem.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ubayKyu.accountingSystem.dto.UserInfoInterface;
import com.ubayKyu.accountingSystem.entity.UserInfo;
import com.ubayKyu.accountingSystem.repository.UserInfoRepository;

@Service
public class UserInfoService {

	@Autowired
	private UserInfoRepository repository;

	// 內建查詢語法
	public Optional<UserInfo> findByUserID(String userID) {
		return repository.findById(userID);
	}

	// 編輯個人資訊
	public void UpdateUserProfile(String userID, String txtName, String txtEmail) {
		Optional<UserInfo> userInfoForEdit = repository.findById(userID);
		userInfoForEdit.get().setName(txtName); // 更新Name
		userInfoForEdit.get().setEmail(txtEmail); // 更新Email
		repository.save(userInfoForEdit.get()); // 內建儲存語法
	}

	// 將查出的List於html使用th:each印出會員列表
	public List<UserInfoInterface> getUserInfoInterface() {
		return repository.GetUserInfoInterface();
	}

	// 刪除會員、流水帳及分類資訊
	public Integer deleteUserInfoAccountingNoteAndCategoryByUserID(String userID) {
		return repository.DeleteUserInfoAccountingNoteAndCategoryByUserID(userID);
	}

	// 由ID取得UserInfoInterface
	public Optional<UserInfoInterface> GetUserInfoInterfaceByUserID(String userID) {
		return repository.FindUserInfoInterfaceByID(userID);
	}

	// 新增、編輯使用者
	public void SaveUserInfo(UserInfo userInfo, String userID, String txtAccount, String txtName, String txtEmail,
			Integer ddlUserLevel) {
		userInfo.setUserID(userID);
		userInfo.setPWD("12345678");
		userInfo.setAccount(txtAccount);
		userInfo.setName(txtName);
		userInfo.setEmail(txtEmail);
		userInfo.setUserLevel(ddlUserLevel);
		repository.save(userInfo);
	}

	// 檢查是否為管理者、且管理員人數是否低於1人
	public boolean IsAdminUserLevelChange(String userID) {
		Optional<UserInfo> user = repository.findById(userID);
		if (user.get().getUserLevel() == 0 && repository.FindAdminUserCount() <= 1)
			return true;
		else
			return false;
	}

	// 檢查管理者是否卸任
	public boolean IsAdminChangeToNormalUser(String userID, Integer userLevel) {
		Optional<UserInfo> user = repository.findById(userID);
		if (user.get().getUserLevel() == 0 && userLevel != 0)
			return true;
		else
			return false;

	}

}
