package com.jinke.showinterface.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.jinke.showinterface.entity.MeetingEntity;

/**
 * @author luobo
 */
@Repository
public interface MeetingRepository extends JpaRepository<MeetingEntity, String>{
	/**
	 * @param oaAccount
	 * @return MeetingEntity
	 */
	@Query(value = "select * from jk_meeting where oa_account = ?1", nativeQuery=true)
	MeetingEntity getMeetingByOaAccount(String oaAccount);
}
