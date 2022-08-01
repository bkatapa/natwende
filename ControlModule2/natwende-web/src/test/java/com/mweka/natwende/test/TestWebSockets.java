package com.mweka.natwende.test;

import java.net.URI;
import java.net.URISyntaxException;

public class TestWebSockets {

	public static void main(String[] args) {
		try {
			// open websocket
			final String url = "ws://localhost:8080/natwende/seatUpdater";
			final WebsocketClientEndpoint clientEndPoint = new WebsocketClientEndpoint(
					new URI(url));

			// add listener
			clientEndPoint.addMessageHandler(new WebsocketClientEndpoint.MessageHandler() {
				public void handleMessage(String message) {
					System.out.println(message);
				}
			});

			// send message to websocket
			clientEndPoint.sendMessage("{'event':'addChannel','channel':'ok_btccny_ticker'}");

			// wait 5 seconds for messages from websocket
			Thread.sleep(5000);

		} catch (InterruptedException ex) {
			System.err.println("InterruptedException exception: " + ex.getMessage());
		} catch (URISyntaxException ex) {
			System.err.println("URISyntaxException exception: " + ex.getMessage());
		}
	}
}
