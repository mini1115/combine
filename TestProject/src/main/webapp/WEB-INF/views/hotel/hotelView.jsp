
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp"%>
<link href="/css/list.css" rel="stylesheet" />
<header class="normal">
	<div class="container position-relative">
		<div class="row justify-content-center">
			<div class="panel" align="center">
				<h4>숙소상세보기</h4>
				<table>
					<tr>
						<td><label for="id">관리 번호</label>
						<td><input type="text" class="form-control" id="id"
							name="id" value="${hotel.id }" readonly="readonly"></td>
					</tr>
					<tr>
						<td><label for="name">숙소 이름</label></td>
						<td><input type="text" class="form-control" id="name"
							name="name" value="${hotel.name }" readonly="readonly"></td>
					</tr>
					<tr>
						<td>숙소 주소</td>
						<td><input type="text" class="form-control" id="address1"
							name="address.address1" value="${hotel.address.address1 }" readonly="readonly" /></td>
					</tr>
					<tr>
						<td>상세 주소</td>
						<td><input type="text" class="form-control" id="address2"
							name="address2" value="${hotel.address.address2 }" readonly="readonly" /></td>
					</tr>
					<tr>
						<td>우편번호</td>
						<td><input type="text" class="form-control" id="zipcode"
							name="address.zipcode" value="${hotel.address.zipcode }" readonly="readonly" /></td>
					</tr>
					<%-- <tr>
							<td><label for="upload">숙소 사진</label></td>
							<td><input type="image" class="form-control" id="upload"
								placeholder="Enter File" name="upload" value="${hotel.upload }"></td>
						</tr> --%>
					<tr>
						<td><label for="content">숙소 등급</label></td>
						<td><input type="text" class="form-control"
							value="${hotel.grade }" readonly="readonly"></td>

					</tr>
					<tr>
						<td><label for="content">숙소 정보</label></td>
						<td><textarea class="form-control" id="content"
								name="content" readonly="readonly">${hotel.content }</textarea>
						</td>
					</tr>
					<tr>
						<td><label for="title">전화번호</label></td>
						<td><input type="text" class="form-control" id="h_tel"
							name="h_tel" value="${hotel.tel }" readonly="readonly"></td>
					</tr>
					<tr>
						<td><label for="price">금액</label></td>
						<td><input type="text" class="form-control" id="price"
							name="price" value="${hotel.price }" readonly="readonly"></td>
					</tr>

				</table>

				<a href="/hotel/update/${hotel.h_num }"><button type="button"
						class="btn btn-primary btn-sm">수정하기</button></a>
				<button type="button" class="btn btn-danger btn-sm" id="btnDelete">삭제</button>
			</div>
		</div>
</header>



<br />
<br />
<br />
<br />



<div id="replyResult"></div>

<div align="center">
	<textarea rows="3" cols="50" id="msg"></textarea>

	<button type="button" class="btn btn-secondary btn-sm" id="btnComment">리뷰쓰기</button>

</div>
<hr />
<form id="frm" action="post"></form>


<script>
var init = function(){
	$.ajax({
		type:"get",
		url : "/reply/list/${hotel.id }"
	}) //ajax
	.done(function(resp){
	
		   var str="<table class='table table-hover' >"
		   $.each(resp, function(key, val){
				
			   str+="<tr>"
			    str+="<td>"+val.user.id+"</td>"
			   str+="<td>"+val.content+"</td>"
			   str+="<td>"+val.regdate+"</td>"
			    if("${principal.user.id}"==val.user.id){
				   str+="<td><a href='javascript:fdel("+val.cnum+")'>삭제</a></td>"
			   } 
			   str+="</tr>" 
		   })
		   str +="</table>"
			  $("#replyResult").html(str);
	})  //done

} //init
//댓글삭제
function fdel(cnum){
	$.ajax({
		type:"delete",
		url : "/reply/delete/"+review_num
	})
	.done(function(resp){
		alert(resp+" 번 글 삭제 성공")
		init()
	})
	.fail(function(){
		alert("댓글 삭제 실패")
	})
}

$("#btnComment").click(function() {
	if(${empty principal.user}){
		alert("로그인하세요")
		location.href="/login"
		return
	}
	if ($("#msg").val() == "") {
		alert("댓글 입력하세요")
		return;
	}
	data = {
		"content" : $("#msg").val(),
		"id": ${hotel.id }
	}
	$.ajax({
		type : "post",
		url : "/reply/insert/${hotel.id }",
		contentType : "application/json;charset=utf-8",
		data : JSON.stringify(data)
	}).done(function() {
		alert("댓글추가");
		init();
	}).fail(function() {
		alert("댓글 추가 실패")
	})
})

$("#btnDelete").click(function(){
	if(!confirm("정말 삭제할까요?")) return
	$.ajax({
		type:"delete",
		url : "/hotel/delete/${hotel.id}",
		success: function(resp){
			if(resp=="success"){
				alert("삭제성공")
				location.href="/hotel/hotelList"
			}
		},//success
		error :function(e){
			alert("삭제실패 : " + e)
		}
	})
})
	init();
</script>



<%@ include file="../include/footer.jsp"%>
