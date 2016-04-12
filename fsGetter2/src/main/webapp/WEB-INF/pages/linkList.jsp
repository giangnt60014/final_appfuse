<%@ include file="/common/taglibs.jsp" %>

<head>
    <title><fmt:message key="filmList.title"/></title>
    <meta name="menu" content="AdminMenu"/>
</head>

<c:if test="${not empty searchError}">
    <div class="alert alert-error fade in">
        <a href="#" data-dismiss="alert" class="close">&times;</a>
        <c:out value="${searchError}"/>
    </div>
</c:if>

<div class="span10">
    <h2><fmt:message key="filmList.heading"/></h2>

    <%-- <form method="get" action="${ctx}/admin/users" id="searchForm" class="form-search">
    <div id="search" class="input-append">
        <input type="text" size="20" name="q" id="query" value="${param.q}"
               placeholder="<fmt:message key="search.enterTerms"/>" class="input-medium search-query"/>
        <button id="button.search" class="btn" type="submit">
            <i class="icon-search"></i> <fmt:message key="button.search"/>
        </button>
    </div>
    </form>

    <div id="actions" class="form-actions">
        <a class="btn btn-primary" href="<c:url value='/userform?method=Add&from=list'/>">
            <i class="icon-plus icon-white"></i> <fmt:message key="button.add"/></a>

        <a class="btn" href="<c:url value='/mainMenu'/>">
            <i class="icon-ok"></i> <fmt:message key="button.done"/></a>
    </div> --%>

    <display:table name="filmList" cellspacing="0" cellpadding="0" requestURI=""
                   defaultsort="1" id="users" pagesize="25" class="table table-condensed table-striped table-hover" export="true">
        <display:column property="title" escapeXml="true" sortable="true" titleKey="filmList.filmTitle" style="width: 25%"
                   		/>
        <display:column property="description" escapeXml="true" sortable="true" titleKey="filmList.desc"
                        style="width: 34%"/>
        <display:column property="link" sortable="true" titleKey="filmList.link" style="width: 25%" autolink="true"
                        media="html"/>
    </display:table>
</div>
