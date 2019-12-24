package com.jinke.showinterface.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.jinke.showinterface.entity.FinanceInvoiceDataEntity;

/**
 * @author luobo
 */
@Repository
public interface FinanceInvoiceDataRepository extends JpaRepository<FinanceInvoiceDataEntity, String>{
	/**
	 * @param oaAccount
	 * @return FinanceInvoiceDataEntity
	 */
	@Query(value = "select * from jk_finance_invoice_data where oa_account = ?1", nativeQuery=true)
	FinanceInvoiceDataEntity getFinanceInvoiceDataByOaAccount(String oaAccount);

	/**
	 * @param oaAccount
	 * @return Double
	 */
	@Query(value = "select ROUND((select count(*) as lcount from jk_finance_invoice_data t where t.invoice_sum < (select j.invoice_sum from jk_finance_invoice_data j where j.oa_account = ?1))/(select count(*) from jk_finance_invoice_data),2)*100 from dual", nativeQuery=true)
	Double getRatio(String oaAccount);
}
