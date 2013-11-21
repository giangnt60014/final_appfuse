<%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="userProfile.title"/></title>
    <meta name="menu" content="UserMenu"/>
</head>

<c:set var="delObject" scope="request"><fmt:message key="userList.user"/></c:set>
<script type="text/javascript">var msgDelConfirm =
   "<fmt:message key="delete.confirm"><fmt:param value="${delObject}"/></fmt:message>";
</script>

<div class="span2">
    <h2><fmt:message key="userProfile.heading"/></h2>
    <c:choose>
        <c:when test="${param.from == 'list'}">
            <p><fmt:message key="userProfile.admin.message"/></p>
        </c:when>
        <c:otherwise>
            <p><fmt:message key="userProfile.message"/></p>
        </c:otherwise>
    </c:choose>
</div>
<div class="span10">
    <h2><fmt:message key='personList.heading'/></h2>
    <div id="actions" class="form-actions">
        <a class="btn btn-primary" href="<c:url value='/personform'/>">
            <i class="icon-plus icon-white"></i> <fmt:message key="button.add"/></a>
        <a class="btn" href="<c:url value='/mainMenu'/>">
            <i class="icon-ok"></i> <fmt:message key="button.done"/></a>
    </div>
    <display:table name="accountList" class="table table-condensed table-striped table-hover" requestURI="" id="accountList" export="true" pagesize="25">
        <display:column property="id" sortable="true" href="getterform" media="html"
            paramId="id" paramProperty="id" titleKey="person.id"/>
        <display:column property="id" media="csv excel xml pdf" titleKey="person.id"/>
        <display:column property="accName" sortable="true" titleKey="person.firstName"/>
        <display:column property="security" sortable="true" titleKey="person.lastName"/>
        <display:setProperty name="paging.banner.item_name"><fmt:message key="personList.person"/></display:setProperty>
        <display:setProperty name="paging.banner.items_name"><fmt:message key="personList.persons"/></display:setProperty>
        <display:setProperty name="export.excel.filename"><fmt:message key="personList.title"/>.xls</display:setProperty>
        <display:setProperty name="export.csv.filename"><fmt:message key="personList.title"/>.csv</display:setProperty>
        <display:setProperty name="export.pdf.filename"><fmt:message key="personList.title"/>.pdf</display:setProperty>
    </display:table>
</div>


<c:set var="scripts" scope="request">
<script type="text/javascript">
    function passwordChanged(passwordField) {
        if (passwordField.id == "password") {
            var origPassword = "${user.password}";
        } else if (passwordField.id == "confirmPassword") {
            var origPassword = "${user.confirmPassword}";
        }

        if (passwordField.value != origPassword) {
            createFormElement("input", "hidden",  "encryptPass", "encryptPass",
                              "true", passwordField.form);
        }
    }

/* This is here so we can exclude the selectAll call when roles is hidden */
function onFormSubmit(theForm) {
    return validateUser(theForm);
}
</script>
</c:set>

<v:javascript formName="user" staticJavascript="false"/>
<script type="text/javascript" src="<c:url value="/scripts/validator.jsp"/>"></script>

