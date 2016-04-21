<%@ include file="/common/taglibs.jsp" %>
<script type="text/javascript" src="<c:url value='/scripts/fsaccount.js'/>"></script>
<head>
    <title><fmt:message key="filmList.title"/></title>
    <meta name="menu" content="FilmMenu" charset="UTF-8"/>
</head>

<c:if test="${not empty searchError}">
    <div class="alert alert-error fade in">
        <a href="#" data-dismiss="alert" class="close">&times;</a>
        <c:out value="${searchError}"/>
    </div>
</c:if>

<div class="span10" style="width:80%">
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
                   defaultsort="1" id="films" pagesize="25" class="table table-condensed table-striped table-hover" style="width:95%">
        <display:column property="title" escapeXml="true" sortable="true" titleKey="filmList.filmTitle" style="width: 25%"
                   		/>
        <display:column property="description" escapeXml="true" sortable="true" titleKey="filmList.desc"
                        style="width: 34%" class="one-long-line"/>
	    <display:column title="Link" sortable="true" >
					    <a id="link" class='css_class_selector' href='${films.link}'>${films.link}</a>
					  </display:column>                        
        <display:column titleKey="filmList.link" style="width: 20%">
		 <input type="button" class="btn btn-primary" name="action"
				id="submit${films.id}" onclick="bCancel=false; getLinkList(${films.id})"
				value="<fmt:message key="button.getLink"/>" /> 
		<%-- <c:if test="${pageContext.request.remoteUser != null}">
 			<input type="button" class="btn btn-primary" name="action"
				id="submit" onclick="bCancel=false; getLinkReg()"
				value="<fmt:message key="button.getLink"/>" /> 
		</c:if> --%>
		<a href="" id="drLink${films.id}" ><p id="drLinkTxt${films.id}"></a>
		</display:column>
    </display:table>
</div>
<div class="span2"><script async src="//pagead2.googlesyndication.com/pagead/js/adsbygoogle.js"></script>
<!-- fsGetter-responsive -->
<ins class="adsbygoogle"
     style="display:block"
     data-ad-client="ca-pub-5775470735879068"
     data-ad-slot="4383356637"
     data-ad-format="auto"></ins>
<script>
(adsbygoogle = window.adsbygoogle || []).push({});
</script></div>
<style type="text/css">
.one-long-line {
   max-width:400px;
   white-space:nowrap; 
   overflow:hidden;
   text-overflow:ellipsis;
   }
.one-long-line:hover {
   overflow:visible;
   white-space:normal; 
   }
</style>

