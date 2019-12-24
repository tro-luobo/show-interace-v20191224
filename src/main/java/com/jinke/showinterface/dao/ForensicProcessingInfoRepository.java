package com.jinke.showinterface.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.jinke.showinterface.entity.ForensicProcessingEntity;

/**
 * @author luobo
 */
@Repository
public interface ForensicProcessingInfoRepository extends JpaRepository<ForensicProcessingEntity, String>{
	/**
	 * @param oaAccount
	 * @return
	 */
	@Query(value = "select * from jk_forensic_processing where oa_account = ?1", nativeQuery=true)
	ForensicProcessingEntity getForensicProcessingByOaAccount(String oaAccount);
}
