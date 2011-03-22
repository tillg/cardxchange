<%@page import="com.cx.UserManager"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
	<title>CardXchange - Homepage</title>
	<meta http-equiv="Content-type" content="text/html; charset=UTF-8" />
	<meta http-equiv="expires" content="0" />
    <meta name="description" content="cardxchange Registrierung Login" />

	<link href="css/cx.css" rel="stylesheet" type="text/css"/>
</head>
<body>

<div id="main-upper-bg" class="mainbgcolor">
	<div id="header">
		<form action="login" method="post" name="login_form">
		<table width="100%">
			<tr>
				<td rowspan="3" width="100%">
					<h1>cardXchange</h1>
				</td>
				<td><span>Login</span></td>
				<td><span>Passwort</span></td>
				<td></td>
			</tr>
			<tr>
				<td><input type="text" maxlength="23" name="cx_login" /></td>
				<td><input type="password" maxlength="23" name ="cx_pwd" /></td>
				<td>
					<label class="cx_button">
						<input type="submit" value="Anmelden"/>
					</label>
				</td>
			</tr>
			<tr>
				<td></td>
				<td><a class="mainlightcolor" href="#">Passwort vergessen?</a></td>
				<td></td>
			</tr>
		</table>
		</form>
	</div>
</div>

	<div id="main" class="maincolor">
		<table width="100%">
			<tr>
				<td width="100%" valign="top">
					<%--- Main content on the left ---%>	
					<h1>Anzahl der registrierten Benutzer</h1>
					<br/>

<%
  synchronized (pageContext) {
    char[] cxCounts = com.cx.UserManager.getActiveUserCountAsStringArray();
    pageContext.setAttribute("cxcounts", cxCounts, PageContext.PAGE_SCOPE);
  }
%>
<c:forEach var="countChar" items="${pageScope.cxcounts}" varStatus="status">
	<c:choose>
		<c:when test="${countChar == ','}">
					<span class="comma"></span>
		</c:when>
		<c:otherwise>		
					<div class="cx_counter">
						<span class="digit">
							<span class="number"><c:out value="${countChar}" /></span>
							<span class="line"></span>
						</span>
					</div>
		</c:otherwise>
	</c:choose>
</c:forEach>				
				
				</td> 
				<td>
					<%--- Register block on the right ---%>	
					<form method="post" action="register">
					<div>
						<h1>Registrieren</h1>
						<span>cardXchange ist und bleibt kostenlos.</span>
					</div>
					
					<br/><hr/><br/>
					
					<c:if test="${not empty errText}">
						<div>
							<span class="cx_label error"> <c:out value="${errText}" /> </span>
						</div>
					</c:if>
					
					<table>
						<tr>
							<td class="cx_label">Vorname:</td>
							<td><input type="text" maxlength="40" name="firstname" value="${firstname}"/></td>
						</tr>
						<tr>
							<td class="cx_label">Nachname:</td>
							<td><input type="text" maxlength="40" name="lastname" value="${lastname}"/></td>
						</tr>
						<tr>
							<td class="cx_label">E-Mail:</td>
							<td><input type="text" maxlength="40" name="email" value="${email}"/></td>
						</tr>
						<tr>
							<td class="cx_label">Login:</td>
							<td><input type="text" maxlength="40" name="login" value="${login}"/></td>
						</tr>
						<tr>
							<td class="cx_label">Neues&nbsp;Passwort:</td>
							<td><input type="password" maxlength="40" name="pwd"/></td>
						</tr>
						
						<tr>
							<td class="cx_label">&nbsp;</td>
							<td>
								<label class="cx_button special_button">
								<input type="submit" value="Registrieren"/>
								</label>
							</td>
						</tr>

					</table>
					</form>

					<br/><hr/><br/>
					
				</td>
			</tr>
		</table>
	</div>


</body>
</html>