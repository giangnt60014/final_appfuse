<%@ include file="/common/taglibs.jsp"%>
<head>
    <title><fmt:message key="getter.title"/></title>
    <meta name="menu" content="GetterMenu"/>
</head>
<div class="span10">
    <h2><fmt:message key='getter.heading'/></h2>
    <div id="actions" class="form-actions">
        <a class="btn btn-primary" href="<c:url value='/personform'/>">
            <i class="icon-plus icon-white"></i> <fmt:message key="button.add"/></a>
        <a class="btn" href="<c:url value='/mainMenu'/>">
            <i class="icon-ok"></i> <fmt:message key="button.done"/></a>
    </div>
    <display:table name="getterList" class="table table-condensed table-striped table-hover" requestURI="" id="getterList" export="true" pagesize="25">
        <display:column property="id" sortable="true" href="personform" media="html"
            paramId="id" paramProperty="id" titleKey="getter.id"/>
        <display:column property="id" media="csv excel xml pdf" titleKey="getter.id"/>
        <display:column property="account" sortable="true" titleKey="getter.account"/>
        <display:column property="security" sortable="true" titleKey="getter.security"/>
        <display:setProperty name="paging.banner.item_name"><fmt:message key="personList.person"/></display:setProperty>
        <display:setProperty name="paging.banner.items_name"><fmt:message key="personList.persons"/></display:setProperty>
        <display:setProperty name="export.excel.filename"><fmt:message key="personList.title"/>.xls</display:setProperty>
        <display:setProperty name="export.csv.filename"><fmt:message key="personList.title"/>.csv</display:setProperty>
        <display:setProperty name="export.pdf.filename"><fmt:message key="personList.title"/>.pdf</display:setProperty>
    </display:table>
</div>