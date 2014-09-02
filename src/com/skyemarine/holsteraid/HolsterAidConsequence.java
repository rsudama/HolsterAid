package com.skyemarine.holsteraid;

import net.rim.device.api.notification.Consequence;
import net.rim.device.api.notification.NotificationsManager;
import net.rim.device.api.synchronization.SyncObject;
import net.rim.device.api.util.Persistable;

public class HolsterAidConsequence implements Consequence {
    static final long ID = 0xbd2350c0dfda2a51L; 
    private static final byte[] DATA = new byte[] { 'm', 'y', '-', 'c', 'o', 'n', 'f', 'i', 'g','-', 'o', 'b', 'j', 'e', 'c', 't' };
    private static final Configuration CONFIG = new Configuration( DATA );

    public Object newConfiguration(long consequenceID, long sourceID,
			byte profileIndex, int level, Object context) {
		return CONFIG;
	}

	public void startNotification(long consequenceID, long sourceID,
			long eventID, Object configuration, Object context) {
		//try {
		//NotificationsManager.triggerImmediateEvent(HolsterAid.ID2, 0, null, null); }
		//catch (Exception ex) {
		//	System.out.println(ex.getMessage());
		//}
	}

	public void stopNotification(long consequenceID, long sourceID,
			long eventID, Object configuration, Object context) {
		//NotificationsManager.cancelImmediateEvent(HolsterAid.ID2, 0, null, null);
	}
	
    /**
     * A static inner class, describing the Configuration information for this consequence.
     * This implements the SyncObject interface, although returns a fixed value.
     */
    private static final class Configuration implements SyncObject, Persistable
    {
        public byte[] _data;

        private Configuration(byte[] data) {
            _data = data;
        }

        public int getUID() {
            // We're not actually doing any synchronization (vs. backup/restore) so we don't care about this value
            return 0;
        }
    }
}
