<%@ include file="/common/taglibs.jsp"%>
<head>
<title><fmt:message key="getter.title" /></title>
<meta name="menu" content="GetterMenu" />
<script type="text/javascript" src="<c:url value='/scripts/fsaccount.js'/>"></script>       
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
				<input	type="text" name="link" id="link"/>
			</div>			
		</fieldset>
		<fieldset class="control-group${(not empty status.errorMessage) ? ' error' : ''}">
			<appfuse:label styleClass="control-label" key="getter.account" /> 
			<div class="controls">
				<form:select path="id" id="account" style="width: 150px">
				<form:options items="${fsaccountList}" itemLabel="account"
				itemValue="id" />
			</div>
		</form:select>		
		</fieldset>
		<fieldset class="control-group${(not empty status.errorMessage) ? ' error' : ''}">
			
				<appfuse:label styleClass="control-label" key="getter.freeLink" /> 
			<div class="controls">
			<label id="freeLink">${user.freeLink}</label>
			</div>			
		</fieldset>
		<fieldset class="form-actions">
			<input type="button" class="btn btn-primary" name="action"
				id="submit" onclick="bCancel=false; getLink()"
				value="<fmt:message key="button.getLink"/>" /> 
				
			<input type="submit"
				class="btn btn-primary" name="cancel" id="cancel"
				onclick="bCancel=true" value="<fmt:message key="button.cancel"/>" />
		</fieldset>
	</form:form>

	<textarea rows="10" cols="1" id="directLink" name="link" style="width: 200px;"></textarea>
	
</div>