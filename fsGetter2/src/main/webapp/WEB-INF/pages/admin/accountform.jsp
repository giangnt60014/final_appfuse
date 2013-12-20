<%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="accountProfile.title"/></title>
    <meta name="menu" content="AdminMenu"/>
</head>

<div class="span2">
    <h2><fmt:message key="accountProfile.heading"/></h2>
    <c:choose>
        <c:when test="${param.from == 'list'}">
            <p><fmt:message key="userProfile.admin.message"/></p>
        </c:when>
        <c:otherwise>
            <p><fmt:message key="userProfile.message"/></p>
        </c:otherwise>
    </c:choose>
</div>
<div class="span7">


    <form:form commandName="fsaccount" method="post" action="accountform" id="accountform" autocomplete="off"
               cssClass="well form-horizontal" onsubmit="return validateUser(this)">
        <form:hidden path="id"/>
        <input type="hidden" name="from" value="<c:out value="${param.from}"/>"/>

        <spring:bind path="account">
        <fieldset class="control-group${(not empty status.errorMessage) ? ' error' : ''}">
        </spring:bind>
            <appfuse:label styleClass="control-label" key="getter.account"/>
            <div class="controls">
                <form:input path="account" id="account"/>
                <form:errors path="account" cssClass="help-inline"/>
            </div>
        </fieldset>
        <spring:bind path="security">
        <fieldset class="control-group${(not empty status.errorMessage) ? ' error' : ''}">
        </spring:bind>
            <appfuse:label styleClass="control-label" key="getter.security"/>
            <div class="controls">
                <form:input path="security" id="security" type="password"/>
                <form:errors path="security" cssClass="help-inline"/>
            </div>
        </fieldset>
        <fieldset class="form-actions">
            <button type="submit" class="btn btn-primary" name="save" onclick="bCancel=false">
                <i class="icon-ok icon-white"></i> <fmt:message key="button.save"/>
            </button>

            <button type="submit" class="btn" name="cancel" onclick="bCancel=true">
                <i class="icon-remove"></i> <fmt:message key="button.cancel"/>
            </button>
        </fieldset>
    </form:form>
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


function onFormSubmit(theForm) {
    return validateUser(theForm);
}
</script>
</c:set>

<script type="text/javascript" src="<c:url value="/scripts/validator.jsp"/>"></script>

