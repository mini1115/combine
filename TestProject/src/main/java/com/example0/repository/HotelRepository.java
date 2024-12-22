package com.example0.repository;


import java.util.HashMap;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example0.model.hotel.Hotel;


public interface HotelRepository extends JpaRepository<Hotel, Long> {

	Page<Hotel> findByAddressContaining(String address1,Pageable pageable);

	@Query(value = "select * from hotel where address.address1 like CONCAT('%',:word,'%') order by price",
			countQuery = "select count(*) from hotel sc where address.address1 like CONCAT('%',:word,'%')",
			nativeQuery = true)
	Page<Hotel> sortHotel(@Param("word") String word, Pageable pageable);
	
	@Query(value = "select count(*) from hotel sc where location1 like CONCAT('%',:word,'%')", 
			countQuery = "select count(*) from hotel sc where location1 like CONCAT('%',:word,'%')",
			nativeQuery=true)
	public Long cntLocationSearch(@Param("word") String  word);
	
	@Query("select count(*) from Hotel")
	public int getCount(HashMap<String, Object> map);

	@Query(value = "select * from hotel where user_id=:id", nativeQuery = true)
	public List<Hotel> myHotel(@Param("id") Long id);

}

 