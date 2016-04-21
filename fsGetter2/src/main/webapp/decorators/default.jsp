<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
	<meta http-equiv="Cache-Control" content="no-store"/>
    <meta http-equiv="Pragma" content="no-cache"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="icon" href="<c:url value="/images/favicon.ico"/>"/>
    <title><decorator:title/> | <fmt:message key="webapp.name"/></title>

    <link rel="stylesheet" type="text/css" media="all" href="<c:url value='/styles/lib/bootstrap-2.2.1.min.css'/>" />
    <link rel="stylesheet" type="text/css" media="all" href="<c:url value='/styles/lib/bootstrap-responsive-2.2.1.min.css'/>" />
    <script type="text/javascript" src="<c:url value='/scripts/lib/bootstrap-2.2.1.min.js'/>"></script>
    
    <link rel="stylesheet" type="text/css" media="all" href="<c:url value='/styles/style.css'/>" />
    <decorator:head/>
    
    <script type="text/javascript" src="<c:url value='/scripts/lib/jquery-1.8.2.min.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/scripts/lib/plugins/jquery.cookie.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/scripts/script.js'/>"></script>
</head>
<body<decorator:getProperty property="body.id" writeEntireProperty="true"/><decorator:getProperty property="body.class" writeEntireProperty="true"/>>
    <c:set var="currentMenu" scope="request"><decorator:getProperty property="meta.menu"/></c:set>

    <div class="navbar navbar-fixed-top">
        <div class="navbar-inner">
            <div class="container-fluid" style="background-color: #343A44;padding-left: 8%" >
                <%-- For smartphones and smaller screens --%>
                <button type="button" class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="brand" href="<c:url value='/'/>"><fmt:message key="webapp.name"/></a>
                <%@ include file="/common/menu.jsp" %>
                <span class="right">
                	<a href="<c:url value='?locale=en'/>" >
                	<img alt="" src="<c:url value='/images/gbFlag.gif'/>">
                	<a href="<c:url value='?locale=vi_VN'/>">
                	<img alt="" src="<c:url value='/images/vnFlag.png'/>">
                	<!-- hitwebcounter Code START -->
					<a href="http://www.hitwebcounter.com" target="_blank">
					<img src="http://hitwebcounter.com/counter/counter.php?page=6358802&style=0038&nbdigits=5&type=ip&initCount=0" title="Hit Web Stats" Alt="Hit Web Stats"   border="0" >
					</a> <br/>
		            <c:if test="${pageContext.request.remoteUser != null}">
		            | <fmt:message key="user.status"/> ${pageContext.request.remoteUser}
		            </c:if>
		        </span>
            </div>
        </div>
    </div>

    <div class="container-fluid" style="padding-left: 8%">
        <%@ include file="/common/messages.jsp" %>
        <div class="row-fluid" style="padding-top: 2%">
            <decorator:body/>

            <c:if test="${currentMenu == 'AdminMenu'}">
                <div class="span2">
                <menu:useMenuDisplayer name="Velocity" config="navlistMenu.vm" permissions="rolesAdapter">
                    <menu:displayMenu name="AdminMenu"/>
                </menu:useMenuDisplayer>
                </div>
            </c:if>
        </div>
    </div>

    <div id="footer">
    	<iframe id="iframediv" src="https://javavnsite.wordpress.com#content-wrapper"></iframe>
        <%-- <span class="left" id="rss">
        <%@ include file="/rss/rssVnExpress.jsp" %>
            <script async src="//pagead2.googlesyndication.com/pagead/js/adsbygoogle.js"></script>
			<!-- fsGetter-300x600 sidefooter -->
			<ins class="adsbygoogle"
			     style="display:inline-block;width:300px;height:600px"
			     data-ad-client="ca-pub-5775470735879068"
			     data-ad-slot="9291234230"></ins>
			<script>
			(adsbygoogle = window.adsbygoogle || []).push({});
			</script>
        </span>
        <span style="float:left;margin:5px">
        	<%@ include file="/rss/rssTinhte.jsp" %>
        </span>
        <span style="float:left;margin:5px">
        	<%@ include file="/rss/rssTuoitre.jsp" %>
        </span>
        <span style="float:left;margin:5px">
        	<%@ include file="/rss/rssVNN.jsp" %>
        	<script async src="//pagead2.googlesyndication.com/pagead/js/adsbygoogle.js"></script>
			<!-- fsGetter-300x600 sidefooter -->
			<ins class="adsbygoogle"
			     style="display:inline-block;width:300px;height:600px"
			     data-ad-client="ca-pub-5775470735879068"
			     data-ad-slot="9291234230"></ins>
			<script>
			(adsbygoogle = window.adsbygoogle || []).push({});
			</script>
        </span>
        <span class="right">
            &copy; <fmt:message key="copyright.year"/> <a href="<fmt:message key="company.url"/>"><fmt:message key="webapp.version"/></a>
        </span> --%>
        <!-- <script async src="//pagead2.googlesyndication.com/pagead/js/adsbygoogle.js"></script>
			Footer
			<ins class="adsbygoogle"
			     style="display:inline-block;width:336px;height:280px"
			     data-ad-client="ca-pub-5775470735879068"
			     data-ad-slot="8779007036"></ins>
			<script>
			(adsbygoogle = window.adsbygoogle || []).push({});
			</script> -->
        
    </div>
<%= (request.getAttribute("scripts") != null) ?  request.getAttribute("scripts") : "" %>
</body>
</html>
<script>
$( document ).ready(function() {
	var hours = 24; // Reset when storage is more than 24hours
	var now = new Date().getTime();
	var setupTime = localStorage.getItem('setupTime');
	if (setupTime == null) {
	    localStorage.setItem('setupTime', now)
	} else {
	    if(now-setupTime > hours*60*60*1000) {
	        localStorage.clear();
	        localStorage.setItem('setupTime', now);
	    }
	}
});
$('#iframediv').load('https://www.google.co.in #DIVNAME');
</script>
