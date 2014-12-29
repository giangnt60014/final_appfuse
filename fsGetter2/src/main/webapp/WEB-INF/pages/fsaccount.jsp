<%@ include file="/common/taglibs.jsp"%>
<link href="//vjs.zencdn.net/4.11/video-js.css" rel="stylesheet">
<script src="//vjs.zencdn.net/4.11/video.js"></script>
<script type="text/javascript">
  document.createElement('video');document.createElement('audio');document.createElement('track');
</script>
<head>
<title><fmt:message key="getter.title" /></title>
<meta name="menu" content="GetterMenu" charset="UTF-8"/>
<script type="text/javascript" src="<c:url value='/scripts/fsaccount.js'/>"></script>       
</head>

<div class="span3">
    <h2><fmt:message key="getter.heading"/></h2>
    <p><fmt:message key="getter.message"/></p>
</div>

<div class="span7">
	<form:form commandName="fsaccount" method="post" action="fsaccountform"
		id="fsaccountform" cssClass="well form-horizontal">
		
		<fieldset class="control-group${(not empty status.errorMessage) ? ' error' : ''}">
			
				<appfuse:label styleClass="control-label" key="getter.link" /> 
			<div class="controls">
				<input	type="text" name="link" id="link"/>
			</div>			
		</fieldset>
		<fieldset class="control-group${(not empty status.errorMessage) ? ' error' : ''}">
			<appfuse:label styleClass="control-label" key="getter.account" /> 
			<div class="controls">
				<form:select path="id" id="account" style="width: 150px">
				<form:options items="${fsaccountList}" itemLabel="id"
				itemValue="id" />
			</div>
		</form:select>		
		</fieldset>
		<fieldset style="display:none" class="control-group${(not empty status.errorMessage) ? ' error' : ''}">
			
				<appfuse:label styleClass="control-label" key="getter.freeLink" /> 
			<div class="controls">
			<label id="freeLink">${user.freeLink}</label>
			</div>			
		</fieldset>
		<fieldset class="form-actions">
			<input type="button" class="btn btn-primary" name="action"
				id="submit" onclick="bCancel=false; getLink()"
				value="<fmt:message key="button.getLink"/>" /> 
			<input type="button" class="btn btn-primary" name="action"
				id="submit" onclick="bCancel=false; getLink1()"
				value="<fmt:message key="button.getLink"/>" /> 	
			<input type="submit"
				class="btn btn-primary" name="cancel" id="cancel"
				onclick="bCancel=true" value="<fmt:message key="button.cancel"/>" />
		</fieldset>
	</form:form>

	<appfuse:label styleClass="control-label" key="getter.directLink" />
	<textarea rows="10" cols="1" id="directLink" name="link" style="width:1027px;height:150px"></textarea>
	<% response.setHeader("Cache-Control","no-cache"); //HTTP 1.1 
	   response.setHeader("Pragma","no-cache"); //HTTP 1.0 
	   response.setDateHeader ("Expires", 0); //prevents caching at the proxy server 
	   response.setDateHeader("Expires", 0);
	%>
	<video id="example_video_1" class="video-js vjs-default-skin"
	  controls preload="auto" width="640" height="264"
	  poster="http://video-js.zencoder.com/oceans-clip.png"
	  data-setup='{"example_option":true}'>
	 <source src="http://video-js.zencoder.com/oceans-clip.mp4" type='video/mp4' />
	 <p class="vjs-no-js">To view this video please enable JavaScript, and consider upgrading to a web browser that <a href="http://videojs.com/html5-video-support/" target="_blank">supports HTML5 video</a></p>
	</video>
<!-- 	<form action="https://www.baokim.vn/payment/product/version11" method="get" accept-charset="UTF-8">
<input type="hidden" name="business" value="giangnt60014@gmail.com">
<input type="hidden" name="product_name" value="VIP 1 tháng">
<input type="hidden" name="product_price" value="10000">
<input type="hidden" name="product_quantity" value="1">
<input type="hidden" name="total_amount" value="10000">
<input type="hidden" name="url_detail" value="">
<input type="hidden" name="url_success" value="">
<input type="hidden" name="url_cancel" value="">
<input type="hidden" name="order_description" value="">
<input type="hidden" name="id" value="">
<input type="image" src="http://developer.baokim.vn/uploads/baokim_btn/thanhtoanantoan-s.png" border="0" name="submit" alt="Thanh toán an toàn với Bảo Kim !" title="Thanh toán trực tuyến an toàn dùng tài khoản Ngân hàng (VietcomBank, TechcomBank, Đông Á, VietinBank, Quân Đội, VIB, SHB,... và thẻ Quốc tế (Visa, Master Card...) qua Cổng thanh toán trực tuyến BảoKim.vn"></form>
	 -->
</div>
	
