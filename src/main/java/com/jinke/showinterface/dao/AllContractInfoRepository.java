package com.jinke.showinterface.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.jinke.showinterface.entity.ContractEntity;

/**
 * @author luobo
 */
@Repository
public interface AllContractInfoRepository extends JpaRepository<ContractEntity, String>{
	/**
	 *oa账号查询合同信息
	 * @param oaAccount Str
	 * @return ContractEntity
	 */
	@Query(value = "select * from jk_contract where oa_account = ?1", nativeQuery=true)
	ContractEntity getContractByOaAccount(String oaAccount);
}
