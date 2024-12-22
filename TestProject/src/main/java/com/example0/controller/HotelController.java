package com.example0.controller;

import javax.servlet.http.HttpSession;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example0.config.auth.PrincipalDetails;
import com.example0.model.hotel.Hotel;
import com.example0.model.hotel.Reservation;
import com.example0.repository.HotelRepository;
import com.example0.repository.ReservationRepository;
import com.example0.service.HotelService;
import com.example0.service.ReservationService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/hotel/*") //hotel

@RequiredArgsConstructor
@Controller
public class HotelController {
    @Autowired
    private HotelService hotelService;
    @Autowired
    private HotelRepository hotelRepository;
    @Autowired
    private ReservationService reservationService;
    @Autowired
    private ReservationRepository reservationRepository;

    @Value("${kakao_api_key}")
    private String kakaoApiKey;

    @GetMapping("hotelInsert")
    public String insert() {
        return "/hotel/hotelInsert";
    }

    @PostMapping("hotelInsert")
    public String insert(@ModelAttribute Hotel hotel, HttpSession session,
                         @AuthenticationPrincipal PrincipalDetails principal) {
        String uploadFolder = session.getServletContext().getRealPath("/") + "\\resources\\img";
        hotel.setUser(principal.getUser());
        hotelService.hotelInsert(hotel, uploadFolder);
        return "redirect:hotelList";
    }

    @GetMapping("hotelList")
    public String list(Model model,
                       @PageableDefault(size = 6, direction = Direction.DESC) Pageable pageable,
                       @RequestParam(required = false, defaultValue = "") String field,
                       @RequestParam(required = false, defaultValue = "") String field2,
                       @RequestParam(required = false, defaultValue = "") String word) {
        Page<Hotel> lists = hotelService.findAll(field, field2, word, pageable);
        Long count = hotelService.count(field, word);
        model.addAttribute("count", count);
        model.addAttribute("hotels", lists);
        model.addAttribute("word", word);
        return "/hotel/hotelList";
    }

    @GetMapping("detail/{id}")
    public String detail(@PathVariable Long id, Model model) {
        model.addAttribute("hotel", hotelService.findById(id));
        model.addAttribute("kakaoAPI",kakaoApiKey);
        return "/hotel/hotelDetail";
    }

    @PutMapping("update")
    @ResponseBody
    public String update(@RequestBody Hotel hotel) {
        hotelService.update(hotel);
        return "success";
    }

    @GetMapping("update/{id}")
    public String update(@PathVariable Long id, Model model) {
        model.addAttribute("hotel", hotelService.findById(id));
        return "/hotel/hotelUpdate";
    }

    @GetMapping("view/{id}")
    public String view(@PathVariable Long id, Model model) {
        model.addAttribute("hotel", hotelService.findById(id));
        return "/hotel/hotelView";
    }

    //숙소삭제
    @DeleteMapping("delete/{id}")
    @ResponseBody
    public String delete(@PathVariable Long id) {
        hotelService.delete(id);
        return "success";
    }

    //숙소예약
    @PostMapping("reservation/{id}")
    @ResponseBody
    public String insert(@RequestBody Reservation reservation,
                         @AuthenticationPrincipal PrincipalDetails principal,
                         @PathVariable Long id) {
        Hotel hotel = hotelRepository.findById(id).get();

        reservation.setHotel(hotel);
        reservation.setUser(principal.getUser());
        if (reservationRepository.CheckDate
                (reservation.getCheck_in(),
                        reservation.getCheck_out(),
                        hotel.getId()).isEmpty()) {
            reservationService.reservationInsert(reservation);
            return "success";
        }
        return "fail";
    }

    //예약폼
    @GetMapping("reservationForm/{id}")
    public String test(Model model, @PathVariable Long id) {
        model.addAttribute("reservation", reservationService.reservationList(id));
        model.addAttribute("hotel", hotelService.findById(id));
        return "/hotel/hotelReservation";
    }
}