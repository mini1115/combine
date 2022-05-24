package com.example0.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example0.config.auth.PrincipalDetails;
import com.example0.model.Hotel;
import com.example0.model.Review;
import com.example0.service.CommentService;

@RestController
@RequestMapping("/reply/*")
public class CommentController {
	@Autowired
	private CommentService commentService;
	
	@GetMapping("list/{num}")
	public List<Review> list(@PathVariable Long review_num){
		System.out.println("review_num"+review_num );
		List<Review> rlist = commentService.list(review_num);
		return rlist;
	}
	//댓글삭제
		@DeleteMapping("delete/{num}")
		public Long delete(@PathVariable Long review_num) {
			commentService.delete(review_num);
			return review_num;
		}
	//댓글추가
	@PostMapping("insert/{num}")
	public ResponseEntity<String> commentInsert(@PathVariable Long review_num,
				@RequestBody Review review,
				@AuthenticationPrincipal PrincipalDetails principal) {
			System.out.println("principal : " + principal);
			
			Hotel h = new Hotel();
			h.setH_num(review_num);
			review.setHotel(h);
			
			/*
			 * User user = new User(); user.setId(1L); user.setUsername("11");
			 */
			//System.out.println("princopal.getUser():"+principal.getUser());
			review.setUser(principal.getUser());//user
			//comment.setUser(p.getUser());
			commentService.insert(review);
			return new ResponseEntity<String>("success",HttpStatus.OK);
		}
}
