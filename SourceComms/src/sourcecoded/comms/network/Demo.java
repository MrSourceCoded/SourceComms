package sourcecoded.comms.network;

import sourcecoded.comms.eventsystem.EventBus;
import sourcecoded.comms.eventsystem.SourceCommsEvent;
import sourcecoded.comms.eventsystem.event.EventPacketHandled;
import sourcecoded.comms.exception.ErrorHandler;
import sourcecoded.comms.network.packets.Pkt0x00Ping;
import sourcecoded.comms.socket.SourceCommsClient;
import sourcecoded.comms.socket.SourceCommsServer;

public class Demo {
	public static void main(String[] args) throws InterruptedException {
		EventBus.Registry.register(Demo.class);
		
		SourceCommsServer.instance().setData(1337);
		SourceCommsServer.instance().open();
		SourceCommsServer.instance().setListeningState(true);
		SourceCommsServer.instance().listen();
		
		SourceCommsClient.instance().setData("localhost", 1337);
		SourceCommsClient.instance().connect();
		SourceCommsClient.instance().setListeningState(true);
		SourceCommsClient.instance().listen();
		
		
		Pkt0x00Ping packetC = new Pkt0x00Ping();
		SourceCommsServer.instance().sendToClient(packetC);
		Thread.sleep(1000);
		Pkt0x00Ping packetS = new Pkt0x00Ping();
		SourceCommsClient.instance().sendToServer(packetS);
		
	}
	
	@SourceCommsEvent
	public void onPacketHandled(EventPacketHandled e) {
		System.err.println("Pkt Received");
	}
}
