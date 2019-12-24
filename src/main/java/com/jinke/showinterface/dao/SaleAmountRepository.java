package com.jinke.showinterface.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.jinke.showinterface.entity.SaleAmountEntity;

/**
 * @author luobo
 */
@Repository
public interface SaleAmountRepository extends JpaRepository<SaleAmountEntity, String>{
	/**
	 * @param oaAccount
	 * @return SaleAmountEntity
	 */
	@Query(value = "select * from jk_sale_amount where oa_account = ?1", nativeQuery=true)
	SaleAmountEntity getSaleAmountByOaAccount(String oaAccount);
}
