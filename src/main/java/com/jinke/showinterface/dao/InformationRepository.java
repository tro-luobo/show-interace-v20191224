package com.jinke.showinterface.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.jinke.showinterface.entity.InformationEntity;

/**
 * @author luobo
 */
@Repository
public interface InformationRepository extends JpaRepository<InformationEntity, String> {
	/**
	 * @param oaAccount
	 * @return InformationEntity
	 */
	@Query(value = "select * from jk_information where oa_account = ?1", nativeQuery=true)
	InformationEntity getInformationByOaAccount(String oaAccount);
}
