<%@ include file="/common/taglibs.jsp"%>
<head>
<title><fmt:message key="getter.title" /></title>
<meta name="menu" content="GetterMenu" />
</head>

<div class="span3">
    <h2><fmt:message key="getter.heading"/></h2>
    <p><fmt:message key="getter.message"/></p>
</div>

<div class="span7">
	<form:form commandName="fsaccount" method="post" action="fsaccountform"
		id="fsaccountform" cssClass="well form-horizontal">
		
		<fieldset class="control-group${(not empty status.errorMessage) ? ' error' : ''}">
			
				<appfuse:label styleClass="control-label" key="getter.link" /> 
			<div class="controls">
				<input	type="text" name="link" />
			</div>			
		</fieldset>
		<fieldset class="control-group${(not empty status.errorMessage) ? ' error' : ''}">
			<appfuse:label styleClass="control-label" key="getter.account" /> 
			<div class="controls">
				<form:select path="id" id="customer" style="width: 150px">
				<form:options items="${fsaccountList}" itemLabel="account"
				itemValue="id" />
			</div>
		</form:select>		
		</fieldset>
		<fieldset class="control-group${(not empty status.errorMessage) ? ' error' : ''}">
			
				<appfuse:label styleClass="control-label" key="getter.freeLink" /> 
			<div class="controls">
				${user.freeLink}
			</div>			
		</fieldset>
		<fieldset class="form-actions">
			<input type="submit" class="btn btn-primary" name="action"
				id="submit" onclick="bCancel=false"
				value="<fmt:message key="button.add"/>" /> 
				
			<input type="submit"
				class="btn btn-primary" name="cancel" id="cancel"
				onclick="bCancel=true" value="<fmt:message key="button.cancel"/>" />
		</fieldset>
			

		
	</form:form>
</div>