package org.malagacomun.enred.xmpp;

import org.malagacomun.enred.entities.Contact;

public interface OnContactStatusChanged {
	public void onContactStatusChanged(final Contact contact, final boolean online);
}
