<%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="mainMenu.title"/></title>
    <meta name="menu" content="MainMenu"/>
</head>
<body class="home">

<h2><fmt:message key="mainMenu.heading"/></h2>
<p><fmt:message key="mainMenu.message"/></p>

<ul class="glassList">
    <li>
        <a href="<c:url value='/userform'/>"><fmt:message key="menu.user"/></a>
    </li>
    <li>
        <a href="<c:url value='/fsaccount'/>"><fmt:message key="getter.title"/></a>
    </li>
</ul>

<a href="http://www.hitwebcounter.com/" target="_blank">
<img src="http://hitwebcounter.com/counter/counter.php?page=5223348&style=0038&nbdigits=7&type=ip&initCount=0" title="secure stats" Alt="secure stats"   border="0" >
</a><br/>
<!-- hitwebcounter.com --><a href="http://www.hitwebcounter.com/" title="Hitsweb Counter" 
target="_blank" style="font-family: Arial, Helvetica, sans-serif; 
font-size: 10px; color: #6E6A68; text-decoration: none ;"><em>Hitsweb Counter</em>
</a>   
</body>

