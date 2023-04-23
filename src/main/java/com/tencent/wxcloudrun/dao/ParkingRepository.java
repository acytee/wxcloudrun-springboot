package com.tencent.wxcloudrun.dao;

import com.tencent.wxcloudrun.model.Parking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

/**
 * @author : chenzg
 * @date : 2023-04-23 17:20:21
 */
public interface ParkingRepository extends JpaRepository<Parking, String> {
	@Query(value = "from Parking where parkingId in (:parkingId) ")
	List<Parking> findByParkingId(@Param("parkingId") Collection<String> parkingId);

	@Modifying
	@Query(value = "delete from Parking where parkingId in (:parkingId)")
	int deleteByParkingId(@Param("parkingId") Collection<String> parkingId);

	@Modifying
	@Query(value = "update Parking set status=:status  where parkingId in (:parkingId)")
	int updateStatusByParkingId(@Param("status") Integer status, @Param("parkingId") Collection<String> parkingId);

}