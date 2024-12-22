package com.example0.model.hotel;

import com.example0.model.user.User;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.springframework.web.multipart.MultipartFile;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="HOTEL_ID")
    private Long id;

    private String name;

    private String tel;

    @Enumerated(EnumType.STRING)
    private Grade grade;

    private int price;

    @OneToMany(mappedBy = "hotel", fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<Review> reviews;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Transient //객체에서 빼기
    private MultipartFile upload;

    private String image;

    private String content;

    @Embedded
    private Address address;


}
