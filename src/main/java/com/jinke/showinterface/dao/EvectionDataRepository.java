package com.jinke.showinterface.dao;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.jinke.showinterface.entity.EvectionDataEntity;

/**
 * @author luobo
 */
@Repository
public interface EvectionDataRepository extends JpaRepository<EvectionDataEntity, String>{

	/**
	 * @param pageable
	 * @return Page<EvectionDataEntity>
	 */
	@Query(value = "select * from jk_evection_data where distance is null", nativeQuery=true)
	Page<EvectionDataEntity> findAllPageTest(Pageable pageable);

	/**
	 * @param min
	 * @param max
	 * @param pageable
	 * @return EvectionDataEntity
	 */
	@Query(value = "select * from jk_evection_data where distance is null and id >= ?1 and id < ?2", nativeQuery=true)
	Page<EvectionDataEntity> findAllPageAndMinMaxTest(int min,int max,Pageable pageable);

	/**
	 * @returnInteger
	 */
	@Query(value = "select count(*) from jk_evection_data where distance is null", nativeQuery=true)
	Integer findAllCountTest();

	/**
	 * @param oaAccount
	 * @return EvectionDataEntity
	 */
	@Query(value = "select * from jk_evection_data where oa_account = ?1", nativeQuery=true)
	EvectionDataEntity getEvectionDataByOaAccount(String oaAccount);

	/**
	 * 出差次数
	 * @param oaAccount
	 * @return Integer
	 */
	@Query(value = "select count(*) from jk_evection_data where oa_account = ?1", nativeQuery=true)
	Integer getEvectionCount(String oaAccount);

	/**
	 * 总共里程
	 * @param oaAccount
	 * @return Double
	 */
	@Query(value = "select nvl(sum(distance),0) as distance from jk_evection_data where oa_account = ?1", nativeQuery=true)
	Double getDistanceSum(String oaAccount);

	/**
	 * 出差去过的城市
	 * @param oaAccount
	 * @return Integer
	 */
	@Query(value = "select count(*) from (select end_add from jk_evection_data e where oa_account = ?1 group by end_add)", nativeQuery=true)
	Integer getCityCount(String oaAccount);

	/**
	 * 出差待过最久的城市，天数
	 * @param oaAccount
	 * @return Map<String, Object>
	 */
	@Query(value = "select a.day,a.end_add,a.start_add,to_char(a.start_time,'YYYY\"年\"MM\"月\"DD\"日\"') as start_time from (select round((e.end_time - e.start_time)) as day,e.oa_account,e.id,e.end_add,e.start_add,e.end_time,e.start_time,rownum as rnum from jk_evection_data e where e.oa_account = ?1 order by day desc)a where rownum = 1", nativeQuery=true)
	Map<String, Object> getInfoMap(String oaAccount);
}
