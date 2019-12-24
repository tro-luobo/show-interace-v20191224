package com.jinke.showinterface.dao;

import java.util.Map;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.jinke.showinterface.entity.CheckWorkEntity;

/**
 * @author luobo
 */
@Repository
public interface CheckWorkRepository extends JpaRepository<CheckWorkEntity, String> {
	/**
	 * @param oaAccount
	 * @return CheckWorkEntity
	 */
	@Query(value = "select * from jk_check_work where oa_account = ?1", nativeQuery=true)
	CheckWorkEntity getCheckWorkByOaAccount(String oaAccount);

	/**
	 * @param oaAccount
	 * @return Map<String, Object>
	 */
	@Query(value = "select k.start_date,to_char(k.start_date,'YYYY\"年\"MM\"月\"DD\"日\"') as minDate,to_char(k.start_date,'hh24:mi') as minTime,round(（select count(*) from JK_CHECK_WORK cr where to_char(cr.start_date,'yyyy-MM-dd') = to_char(k.start_date,'yyyy-MM-dd') and to_number(to_char(cr.start_date,'hh24mi')) > to_number(to_char(k.start_date,'hh24mi')))/ "
			+ " (select (count(*)-1) from JK_CHECK_WORK cw where to_char(cw.start_date,'yyyy-MM-dd') = to_char(k.start_date,'yyyy-MM-dd'))*100,2) as gl"
			+ " from (select b.* from (select t.timenum,t.start_date,t.end_date,t.oa_account,t.user_code,t.user_name from (select to_number(to_char(c.start_date,'hh24mi')) as timenum,c.id,c.start_date,c.end_date,c.oa_account,c.user_code,c.user_name,c.id from JK_CHECK_WORK c where c.oa_account = 'sunff') t order by t.timenum asc) b where rownum = 1) k", nativeQuery=true)
	Map<String, Object> getStartData(String oaAccount);

	/**
	 * @param oaAccount
	 * @return Map<String, String>
	 */
	@Query(value = "select to_char(k.end_date,'YYYY\"年\"MM\"月\"DD\"日\"') as maxDate,to_char(k.start_date,'YYYY\"年\"MM\"月\"DD\"日\"')  as sbDate,to_char(k.end_date,'hh24:mi') as maxTime,to_char(k.end_date,'hh24') as maxHHTime,to_char(k.type) as type from (select s.* from (select case when to_char(t.start_date,'yyyy-MM-dd') = to_char(t.end_date,'yyyy-MM-dd') then 0 else 1 end as type,to_number(to_char(t.end_date,'hh24mi')) as times,t.* from JK_CHECK_WORK t where t.oa_account = ?1) s order by s.type desc,s.times desc) k where rownum = 1", nativeQuery=true)
	Map<String, String> getMaxDate(String oaAccount);

	/**
	 *
	 */
	@Query(value = "delete from JK_CHECK_WORK where id in (select id from JK_CHECK_WORK a where a.oa_account in "
			+ "(select oa_account from JK_CHECK_WORK group by start_date,end_date,oa_account,user_name,user_code having count(*) > 1) "
			+ "and rowid not in (select min(rowid) from JK_CHECK_WORK group by start_date,end_date,oa_account,user_name,user_code having count(*)>1))", nativeQuery=true)
	void deleteCfData();
}
