package com.example0.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example0.model.user.User;
import com.example0.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    //가입
    public void register(User user) {

        String rawPassword = user.getPassword();
        String encPassword = encoder.encode(rawPassword);
        user.setPassword(encPassword);

        userRepository.save(user);
    }

    //유저리스트
    public List<User> ulist() {
        return userRepository.findAllUser();
    }

    @Transactional
    //유저삭제
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    //유저상세보기
    public User detail(Long id) {
        return userRepository.findById(id).get();
    }

    //수정(더디체킹)
    @Transactional
    public void update(User user) {
        //유저정보 담기
        User updateUser = userRepository.findById(user.getId()).get();
        //비밀번호암호화
        String rawPassword = user.getPassword();
        String encPassword = encoder.encode(rawPassword);
        //업데이트
        updateUser.setName(user.getName());
        updateUser.setEmail(user.getEmail());
        updateUser.setPassword(encPassword);
        updateUser.setTel(user.getTel());
        updateUser.setBusinessNum(user.getBusinessNum());
    }


}
