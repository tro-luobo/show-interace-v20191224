package com.jinke.showinterface.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.jinke.showinterface.entity.CapitalAllocationEntity;

/**
 * @author luobo
 */
@Repository
public interface CapitalAllocationRepository extends JpaRepository<CapitalAllocationEntity, String>  {
	/**
	 * @param oaAccount
	 * @return CapitalAllocationEntity
	 */
	@Query(value = "select * from jk_capital_allocation where oa_account = ?1", nativeQuery=true)
	CapitalAllocationEntity getCapitalAllocationByOaAccount(String oaAccount);
}
