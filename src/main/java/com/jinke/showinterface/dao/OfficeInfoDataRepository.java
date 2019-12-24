package com.jinke.showinterface.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.jinke.showinterface.entity.OfficeInfoDataEntity;

/**
 * @author luobo
 */
@Repository
public interface OfficeInfoDataRepository extends JpaRepository<OfficeInfoDataEntity, String>{

	/**
	 * @param oaAccount
	 * @return OfficeInfoDataEntity
	 */
	@Query(value = "select * from jk_office_info_data where oa_account = ?1", nativeQuery=true)
	OfficeInfoDataEntity getOffceInfoByOaAccount(String oaAccount);
}
