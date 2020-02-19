package com.sailotech.mcap.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sailotech.mcap.entity.User;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

	@Query("from User as user WHERE lower(user.username)=:username")
	User findByUsername(@Param("username") String username);

	@Query("FROM User where company.companyId = :company")
	List<User> findByCompanyId(@Param("company") Integer companyId);

	@Query("from User as user WHERE lower(user.emailId)=:emailId")
	User findByUserEmailId(@Param("emailId") String emailId);

	@Query("from User as user WHERE user.userId not in(:userId)  and lower(user.username)=:username")
	User finduserLoginIdNotInId(@Param("username") String username, @Param("userId") Integer userId);

	@Query("from User as user WHERE user.userId not in(:userId)  and user.emailId=:emailId")
	User validateUserEmailUnique(@Param("emailId") String emailId, @Param("userId") Integer userId);

	@Query("from User as user WHERE user.userId not  in(:userId)")
	List<User> getAllUsersLoginIdNotIn(@Param("userId") Integer userId);

}
