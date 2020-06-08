package com.se.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.se.model.Merchant;

@Repository
public interface MerchantRepository extends JpaRepository<Merchant, Long> {
	
	@Query("SELECT u FROM Merchant u WHERE u.company_name = :company_name")
	Optional<Merchant> findByCompanyName(@Param("company_name")String company_name);
	
	@Query("SELECT u FROM Merchant u WHERE u.gst_no = :gst_no")
	Optional<Merchant> findByGstNo(@Param("gst_no") String gst_no);
	
	
	@Query("SELECT v FROM Merchant v WHERE v.user.id = :userId")
	Optional<Merchant> findByUserIdAndIsActive(@Param("userId") Long userId);

}