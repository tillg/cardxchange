<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:if test="${empty sessionScope['cx.loggedin']}">
	<% request.getRequestDispatcher("/index.jsp").forward(request, response); %>
</c:if>	

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
	<title>cardxchange - Homepage </title>
	<meta http-equiv="Content-type" content="text/html; charset=utf-8" />
	<meta http-equiv="expires" content="0" />

	<link href="css/cx.css" rel="stylesheet" type="text/css"/>
</head>

<body>
<div id="main-upper-bg" class="mainbgcolor">
	<div id="header">
		<table width="100%">
			<tr>
				<td width="100%">
					<h1>cardXchange</h1>
				</td>
				<td>
					<span>${sessionScope['cx.user'].firstName}&nbsp;${sessionScope['cx.user'].lastName}</span> <br/>
					<span>Letztes&nbsp;Login:&nbsp;<fmt:formatDate value="${sessionScope['cx.user'].lastLogin}" pattern="dd.MM.yyyy"/></span>
				</td>
			</tr>
		</table>
	</div>
</div>

<div id="main" class="maincolor">
	<div class="cx-home-block">
		<br/>
		<h2>Qualität Ihrer Adresse</h2>
		<div id="statusbar"></div>
		<table width="968px"><tr>
			<td><span>sehr lange nicht bestätigt</span></td>
			<td style="text-align: right;"><span>aktuell bestätigt</span></td>
		</tr></table>

		<br/>
		<form method="get" action="showaddress">
			<label class="cx_button" id="change-address">
				<input type="submit" value="Adresse pflegen"/>
			</label>
		</form>
	</div>
</div>

</body>