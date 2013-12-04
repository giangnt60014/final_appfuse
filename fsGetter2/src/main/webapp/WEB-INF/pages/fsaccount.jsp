<%@ include file="/common/taglibs.jsp"%>
<head>
    <title><fmt:message key="getter.title"/></title>
    <meta name="menu" content="GetterMenu"/>
</head>
<div class="span10">
    <h2><fmt:message key='getter.heading'/></h2>
    <display:table name="fsaccountList" class="table table-condensed table-striped table-hover" requestURI="" id="getterList" export="true" pagesize="25">
        <display:column property="id" sortable="true" href="personform" media="html"
            paramId="id" paramProperty="id" titleKey="getter.id"/>
        <display:column property="account" sortable="true" titleKey="getter.account"/>
        <display:column property="security" sortable="true" titleKey="getter.security"/>
    </display:table>
    
    
    <form:form commandName="fsaccount" method="post" action="fsaccountform" id="fsaccountform">
    <input type="text" name="age"/>
    <form:select path="id" id="customer" style="width: 150px">
    	<form:options items="${fsaccountList}" itemLabel="account" itemValue="id" />
    </form:select>
    <div id="actions" class="form-actions">
        <input type="submit" class="btn btn-primary" name="action" id="submit"
			onclick="bCancel=false" value="<fmt:message key="button.add"/>" />
			
        <input type="submit" class="btn btn-primary" name="cancel" id="cancel"
			onclick="bCancel=true" value="<fmt:message key="button.cancel"/>" />
    </div>
    	
    </form:form>
</div>