package com.tencent.wxcloudrun.dao;

import com.tencent.wxcloudrun.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

/**
 * @author : chenzg
 * @date : 2023-04-23 17:20:22
 */
public interface UserRepository extends JpaRepository<User, String> {
	@Query(value = "from User where userId in (:userId) ")
	List<User> findByUserId(@Param("userId") Collection<String> userId);

	@Modifying
	@Query(value = "delete from User where userId in (:userId)")
	int deleteByUserId(@Param("userId") Collection<String> userId);

	@Query(value = "from User where unionId in (:unionId) ")
	List<User> findByUnionId(@Param("unionId") Collection<String> unionId);

	@Modifying
	@Query(value = "delete from User where unionId in (:unionId)")
	int deleteByUnionId(@Param("unionId") Collection<String> unionId);

	@Query(value = "from User where openId in (:openId) ")
	List<User> findByOpenId(@Param("openId") Collection<String> openId);

	@Modifying
	@Query(value = "delete from User where openId in (:openId)")
	int deleteByOpenId(@Param("openId") Collection<String> openId);

}