<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp"%>
<link href="/css/detail.css" rel="stylesheet" />
<div id="wapper">
	<header>
		<h3>${hotel.name }</h3>

		<button type="button" id="mapBtn">
			<span id="span_detail">지도에서 보기</span>

		</button>
		<span>${hotel.address.address1 } </span><span>${hotel.address.address2 } </span>
	</header>
	<!--네비게이션-->

	<div class="total">
		<nav id="nav_detail">
			<div id="mapView"></div>
			<div id="map" style="width:100%;height:350px;">위치정보</div>
			<div id="clickLatlng"></div>
		</nav>

		<!--콘텐츠부분-->
		<section id="section_detail">

			<img class="center" src="/resources/img/${hotel.image}">
 
		</section>
		<!--사이드바-->
		<aside id="aside_detail">

			<p>리뷰</p>
			<ul>
				<li><a href="">${reply.count}개의 리뷰후기</a></li>
				
			</ul>
			<div id="replyResult"></div>
	<td><label for="content">리뷰 평점</label></td>
			<td><label id="star1"> <input type="radio"
				class="form-check-input" name="point" value="1" id="point"
				>1개
			</label> <label id="star2"> <input type="radio"
				class="form-check-input" name="point" value="2" id="point"
				>2개
			</label> <label id="star3"> <input type="radio"
				class="form-check-input" name="point" value="3" id="point"
				>3개
			</label> <label id="star3"> <input type="radio"
				class="form-check-input" name="point" value="4" id="point"
				>4개
			</label> <label id="star3"> <input type="radio"
				class="form-check-input" name="point" value="5" id="point"
				checked="checked">5개
			</label></td>
			<div id="replyResult"></div>

			<div align="center">
				<textarea rows="3" cols="25" id="msg"></textarea>

				<button type="button" class="btn btn-secondary btn-sm"
					id="btnComment">리뷰쓰기</button>

			</div>
			<hr />
			<form id="frm" action="post"></form>
			<div class="d-flex justify-content-between mt-5 mr-auto">
				<ul class="pagination">
					<c:if test="${hotels.first==false }">
						<li class="page-item"><a class="page-link"
							href="?page=${reviews.number-1 }">이전</a></li>
					</c:if>
					<c:if test="${hotels.last ==false }">
						<li class="page-item"><a class="page-link"
							href="?page=${reviews.number+1 }">다음</a></li>
					</c:if>
				</ul>
				<div></div>
			</div>
		</aside>
	</div>


</div>
<div class="parent">
	<div class="child">금액:${hotel.price }</div>
	<div class="child2">설명:${hotel.content }</div>
	<div class="child">전화번호:${hotel.tel }</div>
	<div class="child">
		<a href="/hotel/reservationForm/${hotel.id }">
		<button	class="button-19">예약하기</button></a>
	</div>
</div>

<script type="text/javascript"
    src="//dapi.kakao.com/v2/maps/sdk.js?appkey=${kakaoAPI}&libraries=services,clusterer,drawing">
</script>
<script>
    var hotelAddress = '${hotel.address.address1}';
	var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
        mapOption = {
            center: new kakao.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표
            level: 3 // 지도의 확대 레벨
        };
	var map = new kakao.maps.Map(mapContainer, mapOption);

    var geocoder = new kakao.maps.services.Geocoder();

	geocoder.addressSearch(hotelAddress, function(result, status) {

        // 정상적으로 검색이 완료됐으면
         if (status === kakao.maps.services.Status.OK) {

            var coords = new kakao.maps.LatLng(result[0].y, result[0].x);

            // 결과값으로 받은 위치를 마커로 표시합니다
            var marker = new kakao.maps.Marker({
                map: map,
                position: coords
            });

            // 인포윈도우로 장소에 대한 설명을 표시합니다
            var infowindow = new kakao.maps.InfoWindow({
                content: '<div style="width:150px;text-align:center;padding:6px 0;">우리회사</div>'
            });
            infowindow.open(map, marker);

            // 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
            map.setCenter(coords);
        }
    });
	//지도에서 보기 클릭
	$(document).ready(function(){
	$("#mapBtn").click(function(){
		var offset = $('#mapView').offset();
		 $('html').animate({scrollTop : offset.top}, 400);
		 
	});
	});

</script>
<%@ include file="../include/footer.jsp"%>