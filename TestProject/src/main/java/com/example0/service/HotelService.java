package com.example0.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example0.model.Hotel;
import com.example0.repository.HotelRepository;

@Service
public class HotelService {
	@Autowired
	private HotelRepository hotelRepository;

	// 숙소등록
	public void hotelInsert(Hotel hotel, String uploadFolder) {
		System.out.println("hotelInsert");
		UUID uuid = UUID.randomUUID();
		MultipartFile file = hotel.getUpload(); // 실제 업로드할 파일(숙소 이미지)
		String uploadFileName = "";
		if (!file.isEmpty()) { // 파일이 선택된 경우 파일이름 설정
			uploadFileName = uuid.toString() + "_" + file.getOriginalFilename();
			File saveFile = new File(uploadFolder, uploadFileName);
			try {
				file.transferTo(saveFile); // 파일 업로드
				hotel.setImage(uploadFileName); // 테이블에 저장될 파일이름

				hotelRepository.save(hotel);
			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
			}
		} // if
	}// hotelInsert

	@Transactional
	// 전체보기
	public List<Hotel> findAll() {
		return hotelRepository.findAll();
	}

	
	// 페이징 전체보기
	public Page<Hotel> findAll(String field,String field2,String word,Pageable pageable) {
		
		if(!field2.isEmpty())
			return hotelRepository.sortHotel(word, pageable);
		if(field.equals("location1"))
			return  hotelRepository.findByAddressContaining(word, pageable);
		
		return hotelRepository.findAll(pageable);
	}

	
	//개수 
	public Long count() {
		return hotelRepository.count();
		}
	 
	//정렬 개수
		public Long count(String field,String word) {
			if(field.equals("location1"))
				return hotelRepository.cntLocationSearch(word);
			
			return hotelRepository.count();

		}
		/*
	 * public int getCount(HashMap<String, Object> map) { return
	 * boardRepository.getCount(map); }
	 */

	// 수정하기
	@Transactional
	public void update(Hotel hotel) {
		Hotel b = hotelRepository.findById(hotel.getId()).get();
		b.setName(hotel.getName());
		b.setAddress(hotel.getAddress());
		b.setUpload(hotel.getUpload());
		b.setGrade(hotel.getGrade());
		b.setImage(hotel.getImage());
		b.setContent(hotel.getContent());
		b.setTel(hotel.getTel());
		b.setPrice(hotel.getPrice());
	}

	// 삭제하기
	@Transactional
	public void delete(Long num) {
		hotelRepository.deleteById(num);
	}

	// 상세보기
	@Transactional
	public Hotel findById(Long h_num) {
		Hotel hotel = hotelRepository.findById(h_num).get();
		return hotel;

	}

	//내호텔리스트
	public List<Hotel> myhotel(Long u_num) {
		return hotelRepository.myHotel(u_num);
	}

}
