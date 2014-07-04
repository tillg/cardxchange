/**
 * Toggle display of two div by ids.
 */
function switch_display(hideId, showId) {
	var hide_div = document.getElementById(hideId);
	var show_div = document.getElementById(showId);

	hide_div.style.display = "none";
	show_div.style.display = "block";
}