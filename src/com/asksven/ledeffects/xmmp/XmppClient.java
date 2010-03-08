package com.asksven.ledeffects.xmmp;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.MessageTypeFilter;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.util.StringUtils;

import com.asksven.ledeffects.data.Preferences;
import com.asksven.ledeffects.manager.EffectsFassade;

import android.content.Context;
import android.util.Log;

public class XmppClient
{
	/** the runtime Context */
	Context m_myCtx;
	
	/** XMPP connection of the client */
	private XMPPConnection m_myXmppClient;
	
	public XmppClient(Context ctx)
	{
		m_myCtx = ctx;
	}
	
    public void connect()
    {
    	if ((m_myXmppClient != null) && (m_myXmppClient.isConnected()))
    	{
    		return;
    	}
    	Preferences myPrefs = new Preferences(m_myCtx.getSharedPreferences(Preferences.PREFS_NAME, 0));
    	
        String host 	= myPrefs.getHost();
        int port 		= myPrefs.getPort();
        String service  = myPrefs.getService();
        String username = myPrefs.getUsername();
        String password = myPrefs.getPassword();
        
        // Create a connection
        ConnectionConfiguration connConfig = new ConnectionConfiguration(host, port, service);
        m_myXmppClient = new XMPPConnection(connConfig);

        try
        {
            m_myXmppClient.connect();
            Log.i(getClass().getSimpleName(), "XMPP: Connected to " + m_myXmppClient.getHost());
        }
        catch (XMPPException ex)
        {
            Log.e(getClass().getSimpleName(), "XMPP: Failed to connect to " + m_myXmppClient.getHost());
            Log.e(getClass().getSimpleName(), ex.toString());
        }
        if (m_myXmppClient.isConnected())
        {
	        try
	        {
	        	m_myXmppClient.login(username, password);
	            Log.i(getClass().getSimpleName(), "XMPP: Logged in as " + m_myXmppClient.getUser());
	
	            // Set the status to available
	            Presence presence = new Presence(Presence.Type.available);
	            this.createXMPPListener(m_myXmppClient);
	            m_myXmppClient.sendPacket(presence);
	        }
	        catch (XMPPException ex)
	        {
	            Log.e(getClass().getSimpleName(), "XMPP: Failed to log in as " + username);
	            Log.e(getClass().getSimpleName(), ex.toString());
	        }
        }
    }
    
    public void disconnect()
    {
    	if ((m_myXmppClient == null) || (!m_myXmppClient.isConnected()))
    	{
    		return;
    	}
    	m_myXmppClient.disconnect();
    	
    }

    /**
     * Called by Settings dialog when a connection is establised with the XMPP server
     *
     * @param connection
     */
    private void createXMPPListener(XMPPConnection connection)
    {
        if (connection != null)
        {
            // Add a packet listener to get messages sent to us
            PacketFilter filter = new MessageTypeFilter(Message.Type.chat);
            connection.addPacketListener(
            new PacketListener() {
                public void processPacket(Packet packet) {
                    Message message = (Message) packet;
                    if (message.getBody() != null) {
                        String fromName = StringUtils.parseBareAddress(message.getFrom());
                        Log.e(getClass().getSimpleName(), "Got text [" + message.getBody() + "] from [" + fromName + "]");
                        EffectsFassade myEffectsMgr = EffectsFassade.getInstance();
                        myEffectsMgr.setNotifyIM(true);
                        // Apply the effect for current state
                		myEffectsMgr.doEffect(m_myCtx);
                    }
                }
            }, filter);

            // Add a (re)connection listener to get updated connection status
            connection.addConnectionListener(
            new ConnectionListener() {
            	@Override
                public void connectionClosedOnError(Exception e) {
                	Log.i(getClass().getSimpleName(), "XMPP: Connection closed " + e.getMessage());
                }
                @Override
                public void reconnectingIn(int in) {
                	Log.i(getClass().getSimpleName(), "XMPP: Reconnecting in " + in);
                }
                @Override
				public void connectionClosed() {
					Log.i(getClass().getSimpleName(), "XMPP: Connection closed");
				}
				@Override
				public void reconnectionFailed(Exception e) {
					// TODO Auto-generated method stub
					Log.i(getClass().getSimpleName(), "XMPP: Connection closed: " + e.getMessage());
				}
				@Override
				public void reconnectionSuccessful() {
					Log.i(getClass().getSimpleName(), "XMPP: Reconnection successful");
				}
            });
            
        }
    }

}
