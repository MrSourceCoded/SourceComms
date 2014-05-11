package sourcecoded.comms.network;

import java.util.HashMap;

import sourcecoded.comms.eventsystem.EventBus;
import sourcecoded.comms.eventsystem.SourceCommsEvent;
import sourcecoded.comms.eventsystem.event.EventPacketHandled;
import sourcecoded.comms.network.packets.Pkt0x00Ping;
import sourcecoded.comms.network.packets.Pkt1x02NBTMap;
import sourcecoded.comms.socket.SourceCommsClient;
import sourcecoded.comms.socket.SourceCommsServer;

public class Demo {
	@SuppressWarnings({ "rawtypes" })
	public static void main(String[] args) throws InterruptedException {
		EventBus.Registry.register(Demo.class);
		
		SourceCommsServer.instance().setData(1338);
		SourceCommsServer.instance().open();
		SourceCommsServer.instance().setListeningState(true);
		SourceCommsServer.instance().listen();
		
		SourceCommsClient.instance().setData("localhost", 1338);
		SourceCommsClient.instance().connect();
		SourceCommsClient.instance().setListeningState(true);
		SourceCommsClient.instance().listen();
		
		Pkt0x00Ping packetC = new Pkt0x00Ping();
		SourceCommsServer.instance().sendToClient(packetC);
		Thread.sleep(1000);
		Pkt0x00Ping packetS = new Pkt0x00Ping();
		SourceCommsClient.instance().sendToServer(packetS);
		
		Thread.sleep(1000);
		HashMap tags = new HashMap();
		//NBT Pkt here
		Pkt1x02NBTMap packetNBT = new Pkt1x02NBTMap(tags);
		SourceCommsServer.instance().sendToClient(packetNBT);
	}
	
	@SourceCommsEvent
	public void onPacketHandled(EventPacketHandled e) {
		System.err.println("Pkt Received");
		if (e.getPacket() instanceof Pkt0x00Ping) {
			Pkt0x00Ping pi = (Pkt0x00Ping) e.getPacket();
			System.err.println(pi.diff);
		}
	}
}
