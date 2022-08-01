/**
 * Payment updater websocket
 */

var uri = "ws://" + window.location.host + "/natwende/paymentUpdater";
console.log(uri);
var websocket = null;
var message = "";

function listenForPayments() {
	websocket = new WebSocket(this.uri);
	websocket.onmessage = function(event) { // alert(event.data);
		var jsonObj = event.data;
		if (jsonObj.constructor !== {}.constructor) {
			jsonObj = JSON.parse(jsonObj);
		}
		console.log(jsonObj.message);
		if (jsonObj.message !== 'undefined' && jsonObj.message !== null) {
			handleMessage(jsonObj.message);
		}
	};
	websocket.onopen = function() {
		console.log("websocket session opened. listening for payment events.");
	};
}

function closeConnection() {
	websocket.close();
}

function sendMessage() {
	var msg = document.getElementById('element-id').value;
	websocket.send(msg);
}

function sendData(msg) {
	websocket.send(msg);
}