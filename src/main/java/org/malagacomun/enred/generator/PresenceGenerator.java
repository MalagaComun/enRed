package org.malagacomun.enred.generator;

import org.malagacomun.enred.entities.Account;
import org.malagacomun.enred.entities.Contact;
import org.malagacomun.enred.entities.Presence;
import org.malagacomun.enred.services.XmppConnectionService;
import org.malagacomun.enred.xml.Element;
import org.malagacomun.enred.xmpp.stanzas.PresencePacket;

public class PresenceGenerator extends AbstractGenerator {

	public PresenceGenerator(XmppConnectionService service) {
		super(service);
	}

	private PresencePacket subscription(String type, Contact contact) {
		PresencePacket packet = new PresencePacket();
		packet.setAttribute("type", type);
		packet.setTo(contact.getJid());
		packet.setFrom(contact.getAccount().getJid().toBareJid());
		return packet;
	}

	public PresencePacket requestPresenceUpdatesFrom(Contact contact) {
		return subscription("subscribe", contact);
	}

	public PresencePacket stopPresenceUpdatesFrom(Contact contact) {
		return subscription("unsubscribe", contact);
	}

	public PresencePacket stopPresenceUpdatesTo(Contact contact) {
		return subscription("unsubscribed", contact);
	}

	public PresencePacket sendPresenceUpdatesTo(Contact contact) {
		return subscription("subscribed", contact);
	}

	public PresencePacket selfPresence(Account account, Presence.Status status) {
		PresencePacket packet = new PresencePacket();
		if(status.toShowString() != null) {
			packet.addChild("show").setContent(status.toShowString());
		}
		packet.setFrom(account.getJid());
		String sig = account.getPgpSignature();
		if (sig != null && mXmppConnectionService.getPgpEngine() != null) {
			packet.addChild("x", "jabber:x:signed").setContent(sig);
		}
		String capHash = getCapHash();
		if (capHash != null) {
			Element cap = packet.addChild("c",
					"http://jabber.org/protocol/caps");
			cap.setAttribute("hash", "sha-1");
			cap.setAttribute("node", "http://conversations.im");
			cap.setAttribute("ver", capHash);
		}
		return packet;
	}

	public PresencePacket sendOfflinePresence(Account account) {
		PresencePacket packet = new PresencePacket();
		packet.setFrom(account.getJid());
		packet.setAttribute("type","unavailable");
		return packet;
	}
}
