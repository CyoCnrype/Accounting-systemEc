package com.ubayKyu.accountingSystem.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ubayKyu.accountingSystem.entity.Category;
import com.ubayKyu.accountingSystem.repository.CategoryRepository;

@Service
public class CategoryService {

	@Autowired
	CategoryRepository CategoryRepository;

	// 建立有ID與時間的空分類
	public Category NewCleanCategory(String userId) {
		Category category = new Category();
		UUID uuid = UUID.randomUUID();
		String guid = uuid.toString();
		category.setCategoryID(guid);
		category.setCreateDate(LocalDateTime.now());
		category.setUserID(userId);
		return category;
	}

	// 是否有重複的標題
	public boolean IsUsedCaption(String userid, String caption) {
		int searchTarget = CategoryRepository.FindSameCategoryNumber(userid, caption);
		if (searchTarget > 0)
			return true;
		else
			return false;
	}

	// 修改category
	public void Update(String categoryId, String txtCaption, String txtBody) {
		Optional<Category> category = CategoryRepository.findById(categoryId);
		category.get().setCaption(txtCaption);
		category.get().setBody(txtBody);
		CategoryRepository.save(category.get());
	}

	// 新增category
	public String Add(String caption, String Body, String userId) {
		Category category = new Category();
		String guid = UUID.randomUUID().toString();
		category.setCategoryID(guid); // categoryId
		category.setCreateDate(LocalDateTime.now()); // time
		category.setUserID(userId); // userId
		category.setCaption(caption); // Caption
		category.setBody(Body); // Body
		CategoryRepository.save(category); // 儲存進DB
		return category.getCategoryID();
	}

	// 判定是否為修改模式
	public boolean IsUpdating(String caption, String userid, String categoryid) {
		if (categoryid == null || categoryid.equals("")) {
			return false;
		}
		String target = CategoryRepository.FindCaptionByUseridAndCategoryid(userid, categoryid); // 找到要比對的標題
		if (target.equals(caption)) {
			return true;
		}
		return false;
	}

	//由ID取的Category
	public List<Category> getCategoryByUserID(String userId) {
		List<Category> categorys = CategoryRepository.FindCategoryByUserID(userId);
		return categorys;
	}

}
