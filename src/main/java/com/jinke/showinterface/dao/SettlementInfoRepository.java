package com.jinke.showinterface.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.jinke.showinterface.entity.SettlementInfoEntity;

/**
 * @author luobo
 */
@Repository
public interface SettlementInfoRepository extends JpaRepository<SettlementInfoEntity, String>{
	/**
	 * 查询
	 * @param oaAccount
	 * @return SettlementInfoEntity
	 */
	@Query(value = "select * from jk_settlement_info where oa_account = ?1", nativeQuery=true)
	SettlementInfoEntity getSettlementInfoByOaAccount(String oaAccount);

	/**
	 * 查询总数
	 * @param oaAccount
	 * @param type
	 * @return Integer
	 */
	@Query(value = "select nvl(sum(num),0) from JK_SETTLEMENT_INFO where oa_account = ?1 and type = ?2", nativeQuery=true)
	Integer getSum(String oaAccount,String type);
}
