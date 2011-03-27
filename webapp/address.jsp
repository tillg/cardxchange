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
				<td colspan="2"> <span>${busAddress.addressInOneRow}</span> 
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
				<td class="col_addresscontent"> <span>${busAddress.tel}</span>
				</td>
				<td>
				</td>
			</tr>
			<tr>
				<td>
				</td>
				<td> <span>Fax</span>
				</td>
				<td><span>${busAddress.fax}</span>
				</td>
				<td>
				</td>
			</tr>
			<tr>
				<td>
				</td>
				<td> <span>Handy</span>
				</td>
				<td> <span>${busAddress.mobile}</span>
				</td>
				<td>
				</td>
			</tr>
		</table>
	</div>
	<div id="business_edit">
		<h3>Geschäftlich</h3>
		<form method="post" action="saveaddress" name="business_address">
		<input type="hidden" name="addressType" value="1"/>
		<table>
			<tr>
				<td class="add_streetcol"><span>Adresse (Strasse und Hausnummer)</span></td>
				<td class="add_zipcol"><span>Postleitzahl</span></td>
				<td class="add_citycol"><span>Ort</span></td>
			</tr>
			<tr>
				<td class="add_streetcol"><input type="text" name="street" value="${busAddress.street}"/></td>
				<td class="add_zipcol"><input type="text" name="zip" value="${busAddress.zip}"/></td>
				<td class="add_citycol"><input type="text" name="city" value="${busAddress.city}"/></td>
			</tr>
			<tr>
				<td><span>Land</span></td>
				<td colspan="2"><span>Bundesland</span></td>
			</tr>
			<tr>
				<td>
					<select id="bus_country" name="country">
						<option name="Deutschland" <c:if test="${busAddress.country=='Deutschland'}">selected="selected"</c:if>>Deutschland</option>
						<option name="Österreich" <c:if test="${busAddress.country=='Österreich'}">selected="selected"</c:if>>Österreich</option>
						<option name="Schweiz" <c:if test="${busAddress.country=='Schweiz'}">selected="selected"</c:if>>Schweiz</option>
					</select></td>
				<td colspan="2">
					<select id="bus_state" name="state">
						<option name="">--- Bitte auswählen ---</option>
						<option name="Baden-Württemberg" <c:if test="${busAddress.state=='Baden-Württemberg'}">selected="selected"</c:if>>Baden-Württemberg</option>
						<option name="Bayern" <c:if test="${busAddress.state=='Bayern'}">selected="selected"</c:if>>Bayern</option>
						<option name="Berlin" <c:if test="${busAddress.state=='Berlin'}">selected="selected"</c:if>>Berlin</option>
						<option name="Brandenburg" <c:if test="${busAddress.state=='Brandenburg'}">selected="selected"</c:if>>Brandenburg</option>
						<option name="Bremen" <c:if test="${busAddress.state=='Bremen'}">selected="selected"</c:if>>Bremen</option>
						<option name="Hamburg" <c:if test="${busAddress.state=='Hamburg'}">selected="selected"</c:if>>Hamburg</option>
						<option name="Hessen" <c:if test="${busAddress.state=='Hessen'}">selected="selected"</c:if>>Hessen</option>
						<option name="Mecklenburg-Vorpommern" <c:if test="${busAddress.state=='Mecklenburg-Vorpommern'}">selected="selected"</c:if>>Mecklenburg-Vorpommern</option>
						<option name="Niedersachsen" <c:if test="${busAddress.state=='Niedersachsen'}">selected="selected"</c:if>>Niedersachsen</option>
						<option name="Nordrhein-Westfalen" <c:if test="${busAddress.state=='Nordrhein-Westfalen'}">selected="selected"</c:if>>Nordrhein-Westfalen</option>
						<option name="Rheinland-Pfalz" <c:if test="${busAddress.state=='Rheinland-Pfalz'}">selected="selected"</c:if>>Rheinland-Pfalz</option>
						<option name="Saarland" <c:if test="${busAddress.state=='Saarland'}">selected="selected"</c:if>>Saarland</option>
						<option name="Sachsen" <c:if test="${busAddress.state=='Sachsen'}">selected="selected"</c:if>>Sachsen</option>
						<option name="Sachsen-Anhalt" <c:if test="${busAddress.state=='Sachsen-Anhalt'}">selected="selected"</c:if>>Sachsen-Anhalt</option>
						<option name="Schleswig-Holstein" <c:if test="${busAddress.state=='Schleswig-Holstein'}">selected="selected"</c:if>>Schleswig-Holstein</option>
						<option name="Thüringen" <c:if test="${busAddress.state=='Thüringen'}">selected="selected"</c:if>>Thüringen</option>
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
				<td class="tel_country"><input type="text" name="tel_country" value="${busAddress.telCountry}"/></td>
				<td class="tel_city"><input type="text" name="tel_city" value="${busAddress.telCity}"/></td>
				<td class="tel_number"><input type="text" name="tel_number" value="${busAddress.telNumber}"/></td>
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
				<td class="tel_country"><input type="text" name="fax_country" value="${busAddress.faxCountry}"/></td>
				<td class="tel_city"><input type="text" name="fax_city" value="${busAddress.faxCity}"/></td>
				<td class="tel_number"><input type="text" name="fax_number" value="${busAddress.faxNumber}"/></td>
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
				<td class="tel_country"><input type="text" name="mobile_country" value="${busAddress.mobileCountry}"/></td>
				<td class="tel_city"><input type="text" name="mobile_city" value="${busAddress.mobileCity}"/></td>
				<td class="tel_number"><input type="text" name="mobile_number" value="${busAddress.mobileNumber}"/></td>
				<td>+49-173-3570687</td>
			</tr>
		</table>
		<div class="button_line">
			<label class="cx_button">
				<input type="submit" value="Speichern"/>
			</label>
			<label class="cx_button">
				<input type="button" value="Abbrechen" onclick="switch_display('business_edit','business_view');"/>
			</label>
		</div>
		</form>
	</div>
		
		
		
	
	<div id="private_view">	
		<table cellspacing="0">
			<tr>
				<td colspan="4"> <br/><hr/><br/>
				</td>
			</tr>

			<tr>
				<td class="col_addresstype"> <span><b>Privat</b></span>
				</td>
				<td colspan="2"> <span>${privateAddress.addressInOneRow}</span> 
				</td>
				<td> 
					<label class="cx_button">
						<input type="submit" value="Bearbeiten" onclick="switch_display('private_view','private_edit');"/>
					</label>
				</td>
			</tr>
			<tr>
				<td>
				</td>
				<td class="col_addressdetail"> <span>Telefon</span> 
				</td>
				<td class="col_addresscontent"> <span>${privateAddress.tel}</span>
				</td>
				<td>
				</td>
			</tr>
			<tr>
				<td>
				</td>
				<td> <span>Fax</span>
				</td>
				<td> <span>${privateAddress.fax}</span>
				</td>
				<td>
				</td>
			</tr>
			<tr>
				<td>
				</td>
				<td> <span>Handy</span>
				</td>
				<td> <span>${privateAddress.mobile}</span>
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
	<div id="private_edit">
		<h3>Privat</h3>
		<form method="post" action="saveaddress" name="private_address">
		<input type="hidden" name="addressType" value="2"/>
		<table>
			<tr>
				<td class="add_streetcol"><span>Adresse (Strasse und Hausnummer)</span></td>
				<td class="add_zipcol"><span>Postleitzahl</span></td>
				<td class="add_citycol"><span>Ort</span></td>
			</tr>
			<tr>
				<td class="add_streetcol"><input type="text" name="street" value="${privateAddress.street}"/></td>
				<td class="add_zipcol"><input type="text" name="zip" value="${privateAddress.zip}"/></td>
				<td class="add_citycol"><input type="text" name="city" value="${privateAddress.city}"/></td>
			</tr>
			<tr>
				<td><span>Land</span></td>
				<td colspan="2"><span>Bundesland</span></td>
			</tr>
			<tr>
				<td>
					<select id="bus_country" name="country">
						<option name="Deutschland" <c:if test="${privateAddress.country=='Deutschland'}">selected="selected"</c:if>>Deutschland</option>
						<option name="Österreich" <c:if test="${privateAddress.country=='Österreich'}">selected="selected"</c:if>>Österreich</option>
						<option name="Schweiz" <c:if test="${privateAddress.country=='Schweiz'}">selected="selected"</c:if>>Schweiz</option>
					</select></td>
				<td colspan="2">
					<select id="bus_state" name="state">
						<option name="">--- Bitte auswählen ---</option>
						<option name="Baden-Württemberg" <c:if test="${privateAddress.state=='Baden-Württemberg'}">selected="selected"</c:if>>Baden-Württemberg</option>
						<option name="Bayern" <c:if test="${privateAddress.state=='Bayern'}">selected="selected"</c:if>>Bayern</option>
						<option name="Berlin" <c:if test="${privateAddress.state=='Berlin'}">selected="selected"</c:if>>Berlin</option>
						<option name="Brandenburg" <c:if test="${privateAddress.state=='Brandenburg'}">selected="selected"</c:if>>Brandenburg</option>
						<option name="Bremen" <c:if test="${privateAddress.state=='Bremen'}">selected="selected"</c:if>>Bremen</option>
						<option name="Hamburg" <c:if test="${privateAddress.state=='Hamburg'}">selected="selected"</c:if>>Hamburg</option>
						<option name="Hessen" <c:if test="${privateAddress.state=='Hessen'}">selected="selected"</c:if>>Hessen</option>
						<option name="Mecklenburg-Vorpommern" <c:if test="${privateAddress.state=='Mecklenburg-Vorpommern'}">selected="selected"</c:if>>Mecklenburg-Vorpommern</option>
						<option name="Niedersachsen" <c:if test="${privateAddress.state=='Niedersachsen'}">selected="selected"</c:if>>Niedersachsen</option>
						<option name="Nordrhein-Westfalen" <c:if test="${privateAddress.state=='Nordrhein-Westfalen'}">selected="selected"</c:if>>Nordrhein-Westfalen</option>
						<option name="Rheinland-Pfalz" <c:if test="${privateAddress.state=='Rheinland-Pfalz'}">selected="selected"</c:if>>Rheinland-Pfalz</option>
						<option name="Saarland" <c:if test="${privateAddress.state=='Saarland'}">selected="selected"</c:if>>Saarland</option>
						<option name="Sachsen" <c:if test="${privateAddress.state=='Sachsen'}">selected="selected"</c:if>>Sachsen</option>
						<option name="Sachsen-Anhalt" <c:if test="${privateAddress.state=='Sachsen-Anhalt'}">selected="selected"</c:if>>Sachsen-Anhalt</option>
						<option name="Schleswig-Holstein" <c:if test="${privateAddress.state=='Schleswig-Holstein'}">selected="selected"</c:if>>Schleswig-Holstein</option>
						<option name="Thüringen" <c:if test="${privateAddress.state=='Thüringen'}">selected="selected"</c:if>>Thüringen</option>
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
				<td class="tel_country"><input type="text" name="tel_country" value="${privateAddress.telCountry}"/></td>
				<td class="tel_city"><input type="text" name="tel_city" value="${privateAddress.telCity}"/></td>
				<td class="tel_number"><input type="text" name="tel_number" value="${privateAddress.telNumber}"/></td>
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
				<td class="tel_country"><input type="text" name="fax_country" value="${privateAddress.faxCountry}"/></td>
				<td class="tel_city"><input type="text" name="fax_city" value="${privateAddress.faxCity}"/></td>
				<td class="tel_number"><input type="text" name="fax_number" value="${privateAddress.faxNumber}"/></td>
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
				<td class="tel_country"><input type="text" name="mobile_country" value="${privateAddress.mobileCountry}"/></td>
				<td class="tel_city"><input type="text" name="mobile_city" value="${privateAddress.mobileCity}"/></td>
				<td class="tel_number"><input type="text" name="mobile_number" value="${privateAddress.mobileNumber}"/></td>
				<td>+49-173-3570687</td>
			</tr>
		</table>
		<div class="button_line">
			<label class="cx_button">
				<input type="submit" value="Speichern"/>
			</label>
			<label class="cx_button">
				<input type="button" value="Abbrechen" onclick="switch_display('private_edit','private_view');"/>
			</label>
		</div>
		</form>
	</div>

		
	</div>
</div>
</body>
</html>