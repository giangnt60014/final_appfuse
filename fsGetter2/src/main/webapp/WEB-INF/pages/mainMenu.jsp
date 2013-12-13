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
</body>
