package com.jinke.showinterface.dao;

import java.util.List;
import java.util.Map;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.jinke.showinterface.entity.UserInfoEntity;

/**
 * @author luobo
 */
@Repository
public interface UserInfoRepository extends JpaRepository<UserInfoEntity, String>{

	/**
	 * 查询用户信息
	 * @param oaAccount
	 * @return Map<String, Object>
	 */
	@Query(value = "select t.School_Recruitment_Title as schoolRecruitmentTitle,to_char(t.add_date,'yyyy-MM-dd') as addTime, t.oa_account as oaAccount,to_char(t.add_date,'YYYY\"年\"MM\"月\"DD\"日\"') as addDate,"
			+ "(select count(*) from jk_user_info where to_char(add_date,'yyyy-MM') = to_char(t.add_date,'yyyy-MM') and oa_account != ?1) as inJobUserCount,"
			+ "(select count(*) from jk_user_info where to_char(birth_date,'MM-dd') = to_char(t.birth_date,'MM-dd') and oa_account != ?1) as sameBirthdayCount,"
			+ "(select count(*) from jk_user_info where graduate_school = t.graduate_school and oa_account != ?1) as classmateCount"
			+ " from jk_user_info t where t.oa_account = ?1", nativeQuery=true)
	Map<String, Object> getUserInfo(String oaAccount);

	/**
	 * 查询用户列表
	 * @return List<String>
	 */
	@Query(value = "select oa_account from jk_user_info where oa_account is not null", nativeQuery=true)
	List<String> findByOaAccountList();
}
