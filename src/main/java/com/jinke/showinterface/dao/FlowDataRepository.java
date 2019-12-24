package com.jinke.showinterface.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.jinke.showinterface.entity.FlowDataEntity;

/**
 * @author luobo
 */
@Repository
public interface FlowDataRepository extends JpaRepository<FlowDataEntity, String>{
	/**
	 * @param oaAccount
	 * @return FlowDataEntity
	 */
	@Query(value = "select t.id,t.oa_account,t.avg_time,t.end_time,t.start_time,t.sp_sum,t.fq_sum,t.over_date,t.user_code,(round((select count(*) from JK_FLOW_DATA where avg_time > t.avg_time)/(select count(*) from JK_FLOW_DATA),2)*100) as ratio from JK_FLOW_DATA t where t.oa_account = ?1", nativeQuery=true)
	FlowDataEntity getFlowDataByOaAccount(String oaAccount);
}
