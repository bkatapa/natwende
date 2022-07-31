/**
 * Seat reservation updater websocket
 */

var uri = "ws://" + window.location.host + "/natwende/seatUpdater";
console.log(uri);
var websocket = null;
var message = "";

function openConnection() {
	websocket = new WebSocket(this.uri);
	websocket.onmessage = function(event) { // alert(event.data);
		var jsonObj = event.data;
		if (jsonObj.constructor !== {}.constructor) {
			jsonObj = JSON.parse(jsonObj);
		}			
		updateSeats(jsonObj);
	};
	websocket.onopen = function() {
		var townEndpoints = document.getElementById('trip-endpoints-id').value;		
		console.log(townEndpoints);		
		var townList = townEndpoints.split(/[.,\/ -]/);		
		var reservationMg = { tripSerialNo: document.getElementById('trip-id').value, fromTown: townList[0], toTown: townList[1] };		
		var json = JSON.stringify(reservationMg);
		console.log(json);		
		sendData(json);
	};
}

function closeConnection() {
	websocket.close();
}

function sendMessage() {
	var msg = document.getElementById('trip-id').value;
	websocket.send(msg);
}

function sendData(msg) {
	websocket.send(msg);
}