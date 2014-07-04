<%@page import="com.cx.UserManager"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
	<title>CardXchange - Email Aktivierung</title>
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
					<h1>Email Bestätigung</h1>
					<br/>
					<p>Vielen Dank für die Bestätigung Ihrer Email. </p>

				
				</td> 
				<td>
					<%--- Register block on the right ---%>	
					
				</td>
			</tr>
		</table>
	</div>


</body>
</html>