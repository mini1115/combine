package com.example0.service;

import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example0.model.Manager;
import com.example0.repository.EnterpriseRepository;
import com.example0.repository.ManagerRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ManagerService {
	
	private final ManagerRepository managerRepository;
	private final EnterpriseRepository enterpriseRepository;
	private final BCryptPasswordEncoder encoder;
	
	//회원가입
	public void managerjoin(Manager manager) {
		System.out.println(manager);
		System.out.println(enterpriseRepository.findById((long)1));
		String rawPassword = manager.getM_password();
		String encPassword = encoder.encode(rawPassword);
		manager.setM_password(encPassword);
		
		managerRepository.save(manager);
	}
	
	//리스트
	public List<Manager> list(){
		return managerRepository.findAll();
	}
}
