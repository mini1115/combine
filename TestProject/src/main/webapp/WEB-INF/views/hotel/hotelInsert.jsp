<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp"%>

<header class="normal">
    <div class="container position-relative">
        <div class="row justify-content-center">

            <div class="panel" align="center">

                <h4>숙소등록</h4>

                <form action="hotelInsert" method="post" enctype="multipart/form-data">
                    <table>
                        <tr>
                            <td><label for="user_id">유저 번호</label>
                            <td><input type="text" class="form-control" id="user_id" name="user_id" readonly="readonly" value="${principal.user.id}"></td>
                        </tr>
                        <tr>
                            <td><label for="title">숙소 이름</label></td>
                            <td><input type="text" class="form-control" id="name" placeholder="Enter title" name="name"></td>
                        </tr>
                        <tr>
                            <td>숙소 주소</td>
                            <td><input type="text" class="form-control" id="address1" name="address.address1" readonly="readonly" /></td>
                        </tr>
                        <tr>
                            <td>상세 주소</td>
                            <td><input type="text" class="form-control" id="address2" name="address.address2" /></td>
                        </tr>
                        <tr>
                            <td>우편번호</td>
                            <td><input type="text" class="form-control" id="zipcode" name="address.zipcode" readonly="readonly" /></td>
                        </tr>
                        <tr>
                            <td><label for="upload">숙소 사진</label></td>
                            <td><input type="file" class="form-control" id="upload" placeholder="Enter File" name="upload"></td>
                        </tr>
                        <tr>
                            <td><label for="content">숙소 등급</label></td>
                            <td><label id="star1"> <input type="radio" class="form-check-input" name="grade" value="STAR1" id="grade" checked="checked">1성급
                                </label> <label id="star2"> <input type="radio" class="form-check-input" name="grade" value="STAR2" id="grade" checked="checked">2성급
                                </label> <label id="star3"> <input type="radio" class="form-check-input" name="grade" value="STAR3" id="grade" checked="checked">3성급
                                </label></td>

                        </tr>
                        <tr>
                            <td><label for="content">숙소 정보</label></td>
                            <td><textarea class="form-control" rows="5" id="content" name="content"></textarea></td>

                        </tr>
                        <tr>
                            <td><label for="title">전화번호</label></td>
                            <td><input type="text" class="form-control" id="tel" placeholder="Enter tel" name="tel"></td>
                        </tr>
                        <tr>
                            <td><label for="price">금액</label></td>
                            <td><input type="text" class="form-control" id="price" placeholder="Enter price" name="price"></td>
                        </tr>


                    </table>
                    <button type="submit" class="btn btn-primary btn-sm">등록하기</button>
                </form>
            </div>
        </div>
    </div>
</header>

</script>
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script>
	window.onload = function () {
		// 주소 입력 필드 클릭 이벤트
		document.getElementById("address1").addEventListener("click", function () {
			// 카카오 주소 검색 팝업창 호출
			new daum.Postcode({
				// 검색 결과를 선택했을 때 실행되는 함수
				oncomplete: function (data) {
					// 선택한 주소와 우편번호를 입력 필드에 설정
					document.getElementById("address1").value = data.address; // 주소 넣기
					document.getElementById("zipcode").value = data.zonecode; // 우편번호 넣기

					// 상세 주소 입력 필드로 포커스 이동
					document.querySelector("input[name='address\\.address2']").focus();
				},
				// 팝업창이 닫힐 때 실행되는 함수

			}).open();
		});
	};
</script>

<%@ include file="../include/footer.jsp"%>
