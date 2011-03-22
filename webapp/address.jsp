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
	<script type="text/javascript" src="js/cx.js"></script>
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
	<div>
		<br/>
		<h2>Adressdaten</h2>
		<p>
		  Alle Ihre Daten sind privat. Sie entscheiden, wer diese Daten sehen darf, wenn Sie Ihre Adresse mit Ihren Kontakten tauschen.
		  <br/>
		  cardXchange wird diese Daten nie an Dritte weitergeben.
		</p>

		<br/>
		
	<div id="business_view">
		<table cellspacing="0">
			<tr>
				<td class="col_addresstype"> <span><b>Geschäftlich</b></span>
				</td>
				<td colspan="2"> <span>Frankfurter Ring 105a, 80807 München, Deutschland (Bayern)</span> 
				</td>
				<td> 
					<label class="cx_button">
						<input type="submit" value="Bearbeiten" onclick="switch_display('business_view','business_edit');"/>
					</label>
				</td>
			</tr>
			<tr>
				<td>
				</td>
				<td class="col_addressdetail"> <span>Telefon</span> 
				</td>
				<td class="col_addresscontent"> <span>+49-89-358680 113</span>
				</td>
				<td>
				</td>
			</tr>
			<tr>
				<td>
				</td>
				<td> <span>Fax</span>
				</td>
				<td>
				</td>
				<td>
				</td>
			</tr>
			<tr>
				<td>
				</td>
				<td> <span>Handy</span>
				</td>
				<td> <span>+49-173-3570687</span>
				</td>
				<td>
				</td>
			</tr>
		</table>
	</div>
	<div id="business_edit">
		<h3>Geschäftlich</h3>
		<form method="post" action="" name="business_address">
		<input type="hidden" name="addressType" value="1"/>
		<table>
			<tr>
				<td class="add_streetcol"><span>Adresse (Strasse und Hausnummer)</span></td>
				<td class="add_zipcol"><span>Postleitzahl</span></td>
				<td class="add_citycol"><span>Ort</span></td>
			</tr>
			<tr>
				<td class="add_streetcol"><input type="text" name="street"/></td>
				<td class="add_zipcol"><input type="text" name="zip"/></td>
				<td class="add_citycol"><input type="text" name="city"/></td>
			</tr>
			<tr>
				<td><span>Land</span></td>
				<td colspan="2"><span>Bundesland</span></td>
			</tr>
			<tr>
				<td>
					<select id="bus_country" name="country">
						<option name="Deutschland">Deutschland</option>
						<option name="Österreich">Österreich</option>
						<option name="Schweiz">Schweiz</option>
					</select></td>
				<td colspan="2">
					<select id="bus_state" name="state">
						<option name="">--- Bitte auswählen ---</option>
						<option name="Baden-Württemberg">Baden-Württemberg</option>
						<option name="Bayern">Bayern</option>
						<option name="Berlin">Berlin</option>
						<option name="Brandenburg">Brandenburg</option>
						<option name="Bremen">Bremen</option>
						<option name="Hamburg">Hamburg</option>
						<option name="Hessen">Hessen</option>
						<option name="Mecklenburg-Vorpommern">Mecklenburg-Vorpommern</option>
						<option name="Niedersachsen">Niedersachsen</option>
						<option name="Nordrhein-Westfalen">Nordrhein-Westfalen</option>
						<option name="Rheinland-Pfalz">Rheinland-Pfalz</option>
						<option name="Saarland">Saarland</option>
						<option name="Sachsen">Sachsen</option>
						<option name="Sachsen-Anhalt">Sachsen-Anhalt</option>
						<option name="Schleswig-Holstein">Schleswig-Holstein</option>
						<option name="Thüringen">Thüringen</option>
					</select>
				</td>
			</tr>
		</table>
		<table>
			<tr>
				<td colspan="4"><span><b>Telefon</b></span></td>
			</tr>
			<tr>
				<td><span>Landesvorwahl</span></td>
				<td><span>Ortsvorwahl ohne "0"</span></td>
				<td><span>Nummer plus Durchwahl</span></td>
				<td><span>Vorschau</span></td>
			</tr>
			<tr>
				<td class="tel_country"><input type="text" name="tel_country"/></td>
				<td class="tel_city"><input type="text" name="tel_city"/></td>
				<td class="tel_number"><input type="text" name="tel_number"/></td>
				<td>+49-89-358680 113</td>
			</tr>
			<tr>
				<td colspan="4"><span><b>Fax</b></span></td>
			</tr>
			<tr>
				<td><span>Landesvorwahl</span></td>
				<td><span>Ortsvorwahl ohne "0"</span></td>
				<td><span>Nummer plus Durchwahl</span></td>
				<td></td>
			</tr>
			<tr>
				<td class="tel_country"><input type="text" name="fax_country"/></td>
				<td class="tel_city"><input type="text" name="fax_city"/></td>
				<td class="tel_number"><input type="text" name="fax_number"/></td>
				<td></td>
			</tr>
			<tr>
				<td colspan="4"><span><b>Handy</b></span></td>
			</tr>
			<tr>
				<td><span>Landesvorwahl</span></td>
				<td><span>Ortsvorwahl ohne "0"</span></td>
				<td><span>Nummer plus Durchwahl</span></td>
				<td></td>
			</tr>
			<tr>
				<td class="tel_country"><input type="text" name="mobile_country"/></td>
				<td class="tel_city"><input type="text" name="mobile_city"/></td>
				<td class="tel_number"><input type="text" name="mobile_number"/></td>
				<td>+49-173-3570687</td>
			</tr>
		</table>
		<div class="button_line">
			<label class="cx_button">
				<input type="submit" value="Speichern"/>
			</label>
			<label class="cx_button">
				<input type="submit" value="Abbrechen" onclick="switch_display('business_edit','business_view');"/>
			</label>
		</div>
		</form>
	</div>
		
		
		
		
		
		<table cellspacing="0">
			<tr>
				<td colspan="4"> <br/><hr/><br/>
				</td>
			</tr>

			<tr>
				<td class="col_addresstype"> <span><b>Privat</b></span>
				</td>
				<td colspan="2"> <span>Seebauerstrasse 41, 81735 München, Deutschland (Bayern)</span> 
				</td>
				<td> 
					<label class="cx_button">
						<input type="submit" value="Bearbeiten"/>
					</label>
				</td>
			</tr>
			<tr>
				<td>
				</td>
				<td class="col_addressdetail"> <span>Telefon</span> 
				</td>
				<td class="col_addresscontent"> <span>+49-89-60062244</span>
				</td>
				<td>
				</td>
			</tr>
			<tr>
				<td>
				</td>
				<td> <span>Fax</span>
				</td>
				<td>
				</td>
				<td>
				</td>
			</tr>
			<tr>
				<td>
				</td>
				<td> <span>Handy</span>
				</td>
				<td> <span>+49-173-3570687</span>
				</td>
				<td>
				</td>
			</tr>

			<tr>
				<td colspan="4"> <br/><hr/><br/>
				</td>
			</tr>

			<tr>
				<td class="col_addresstype"> <span><b>E-Mail-Adressen</b></span>
				</td>
				<td class="col_addressdetail"> <span>Geschäftlich</span> 
				</td>
				<td class="col_addresscontent"> <span>christian.voss@mgm-tp.com</span>
				</td>
				<td>
					<label class="cx_button">
						<input type="submit" value="Bearbeiten"/>
					</label>
				</td>
			</tr>
			<tr>
				<td>
				</td>
				<td> <span>Privat</span>
				</td>
				<td> <span>vossi.voss@gmail.com</span>
				</td>
				<td>
				</td>
			</tr>

		</table>

		
	</div>
</div>
</body>
</html>