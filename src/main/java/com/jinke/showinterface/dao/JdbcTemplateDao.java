package com.jinke.showinterface.dao;

import javax.annotation.Resource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.jinke.showinterface.entity.OrderDataEntity;

@Repository
public class JdbcTemplateDao {
	
	final static String SQL = "select c.process_num as processNum,u.oa_account as oaAccount," + 
			"co.contracts_num as contractsNum,f.invoice_sum as invoiceSum," + 
			"f.ratio,fo.usage_count as usageCount," + 
			"i.handle_sum as handleSum,i.partake_sum as partakeSum,s.set_cm as setCm,s.set_num as setNum from user_info_entity u " + 
			" left join capital_allocation_entity c " + 
			" on u.oa_account = c.oa_account " + 
			"left join contract_entity co " + 
			" on c.oa_account = co.oa_account " + 
			"left join finance_invoice_data_entity f" + 
			" on c.oa_account = f.oa_account " + 
			"left join forensic_processing_entity fo " + 
			" on c.oa_account = fo.oa_account " + 
			"left join information_entity i " + 
			" on c.oa_account = i.oa_account " + 
			"left join sale_amount_entity s " + 
			" on c.oa_account = s.oa_account " + 
			" where u.oa_account = ?";
	
	@Resource
	JdbcTemplate jdbcTemplate;
	
	public OrderDataEntity getAllOrderData(String oaAccount) {
		OrderDataEntity order = new OrderDataEntity();
		try {
			order = (OrderDataEntity) jdbcTemplate.queryForObject(SQL, new BeanPropertyRowMapper(OrderDataEntity.class), oaAccount);
		} catch (EmptyResultDataAccessException ee) {
			order = null;
		}
		return order;
	}
}
