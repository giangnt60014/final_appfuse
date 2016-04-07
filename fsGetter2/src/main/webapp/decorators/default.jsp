<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<html lang="en">
<head>
    <meta http-equiv="Cache-Control" content="no-store"/>
    <meta http-equiv="Pragma" content="no-cache"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="icon" href="<c:url value="/images/favicon.ico"/>"/>
    <title><decorator:title/> | <fmt:message key="webapp.name"/></title>

    <link rel="stylesheet" type="text/css" media="all" href="<c:url value='/styles/lib/bootstrap-2.2.1.min.css'/>" />
    <link rel="stylesheet" type="text/css" media="all" href="<c:url value='/styles/lib/bootstrap-responsive-2.2.1.min.css'/>" />
    <link rel="stylesheet" type="text/css" media="all" href="<c:url value='/styles/style.css'/>" />
    <decorator:head/>

    <script type="text/javascript" src="<c:url value='/scripts/lib/jquery-1.8.2.min.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/scripts/lib/bootstrap-2.2.1.min.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/scripts/lib/plugins/jquery.cookie.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/scripts/script.js'/>"></script>
</head>
<body<decorator:getProperty property="body.id" writeEntireProperty="true"/><decorator:getProperty property="body.class" writeEntireProperty="true"/>>
    <c:set var="currentMenu" scope="request"><decorator:getProperty property="meta.menu"/></c:set>

    <div class="navbar navbar-fixed-top">
        <div class="navbar-inner">
            <div class="container-fluid">
                <%-- For smartphones and smaller screens --%>
                <button type="button" class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="brand" href="<c:url value='/'/>"><fmt:message key="webapp.name"/></a>
                <%@ include file="/common/menu.jsp" %>
                <c:if test="${pageContext.request.locale.language ne 'en'}">
                    <div id="switchLocale"><a href="<c:url value='/?locale=en'/>">
                        <fmt:message key="webapp.name"/> in English</a>
                    </div>
                </c:if>
                <span class="right">
                	<!-- hitwebcounter Code START -->
					<a href="http://www.hitwebcounter.com" target="_blank">
					<img src="http://hitwebcounter.com/counter/counter.php?page=6358802&style=0038&nbdigits=5&type=ip&initCount=0" title="Hit Web Stats" Alt="Hit Web Stats"   border="0" >
					</a> <br/>
					    <!-- hitwebcounter.com --><a href="http://www.hitwebcounter.com" title="Fast Counters" 
					    target="_blank" style="font-family: sans-serif, Arial, Helvetica; 
					    font-size: 9px; color: #9F9F97; text-decoration: none ;"><strong>Fast Counters</strong>
					    </a> 
		            <c:if test="${pageContext.request.remoteUser != null}">
		            | <fmt:message key="user.status"/> ${pageContext.request.remoteUser}
		            </c:if>
		        </span>
            </div>
        </div>
    </div>

    <div class="container-fluid">
        <%@ include file="/common/messages.jsp" %>
        <div class="row-fluid">
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
    	
        <span class="left" id="rss">
        <%@ include file="/rss/rssVnExpress.jsp" %>
            <!-- <script async src="//pagead2.googlesyndication.com/pagead/js/adsbygoogle.js"></script>
			Footer
			<ins class="adsbygoogle"
			     style="display:inline-block;width:336px;height:280px"
			     data-ad-client="ca-pub-5775470735879068"
			     data-ad-slot="8779007036"></ins>
			<script>
			(adsbygoogle = window.adsbygoogle || []).push({});
			</script> -->
        </span>
        <span style="float:left;margin:5px">
        	<%@ include file="/rss/rssTinhte.jsp" %>
        </span>
        <span style="float:left;margin:5px">
        	<%@ include file="/rss/rssTuoitre.jsp" %>
        </span>
        <span style="float:left;margin:5px">
        	<%@ include file="/rss/rssVNN.jsp" %>
        </span>
        <span class="right">
            &copy; <fmt:message key="copyright.year"/> <a href="<fmt:message key="company.url"/>"><fmt:message key="webapp.version"/></a>
        </span>
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
