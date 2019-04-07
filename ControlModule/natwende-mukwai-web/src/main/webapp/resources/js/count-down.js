$(document).ready(function () {
	var delay = 10;
	var url = "http://" + window.location.host + "/natwende/home.xhtml";
	
	function countdown() {
		setTimeout(countdown, 1000);
		$('#countmsg').html("Redirecting in " + delay + " seconds.");
		delay--;
		
		if (delay < 0) {
			window.location = url;
			delay = 0;
		}
	}
	countdown();
});