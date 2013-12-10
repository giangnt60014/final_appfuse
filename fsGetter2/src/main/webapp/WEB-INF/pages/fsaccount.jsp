<%@ include file="/common/taglibs.jsp"%>
<head>
<title><fmt:message key="getter.title" /></title>
<meta name="menu" content="GetterMenu" />
</head>
<div class="span10">
	<h2>
		<fmt:message key='getter.heading' />
	</h2>
	<form:form commandName="fsaccount" method="post" action="fsaccountform"
		id="fsaccountform">
		
		<ul>
		<li class="buttonBar right">
			<input type="submit" class="btn btn-primary" name="action"
				id="submit" onclick="bCancel=false"
				value="<fmt:message key="button.add"/>" /> <input type="submit"
				class="btn btn-primary" name="cancel" id="cancel"
				onclick="bCancel=true" value="<fmt:message key="button.cancel"/>" />
		</li>
		<li>
			<appfuse:label styleClass="desc" key="getter.link" /> 
			<input
				type="text" name="age" />
		</li>
		<form:select path="id" id="customer" style="width: 150px">
			<form:options items="${fsaccountList}" itemLabel="account"
				itemValue="id" />
		</form:select>
		</ul>

	</form:form>
</div>