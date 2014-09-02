package com.skyemarine.holsteraid;

import java.io.EOFException;
import javax.microedition.media.Player;
import javax.microedition.media.control.VolumeControl;

import net.rim.device.api.notification.Consequence;
import net.rim.device.api.synchronization.SyncConverter;
import net.rim.device.api.synchronization.SyncObject;
//import net.rim.device.api.system.Alert;
//import net.rim.device.api.system.LED;
import net.rim.device.api.ui.component.Dialog;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.util.DataBuffer;
import net.rim.device.api.util.Persistable;

import com.skyemarine.holsteraid.lib.*;

/**
 * Implementation of the Consequence interface. It must also implement SyncConverter. A Consequence
 * can be used to flash the LED, play a sound, and vibrate the device when it is triggered. 
 */
public class ConsequenceImpl implements Consequence, SyncConverter
{
    static final long ID = 0xbd2350c0dfda2a51L; 
    private static final int TYPE = 'n' << 24 | 'o' << 16 | 't' << 8 | 'd'; 
    
    // Notes for NotificationsDemo.
    private static final byte[] DATA = 
    	new byte[] { 'm', 'y', '-', 'c', 'o', 'n', 'f', 'i', 'g','-', 'o', 'b', 'j', 'e', 'c', 't' };
    private static final Configuration CONFIG = new Configuration(DATA);

    /**
     * A static inner class, describing the Configuration information for this consequence.
     * <p>
     * This implements the SyncObject interface, although returns a fixed value.
     */
    private static final class Configuration implements SyncObject, Persistable {
        public byte[] _data;

        private Configuration(byte[] data) {
            _data = data;
        }

        public int getUID() {
            // We're not actually doing any synchronization (versus backup/restore) so we don't care about this value
            return 0;
        }
    }

    public void startNotification(long consequenceID, long sourceID, long eventID, Object configuration, Object context) {
        if (sourceID == HolsterAid.NOTIFICATIONS_ID) {            
            // Start the LED blinking
            //LED.setConfiguration(500, 250, LED.BRIGHTNESS_50);
            //LED.setState(LED.STATE_BLINKING);

            //Alert.startAudio(TUNE, VOLUME);
            //Alert.startBuzzer(TUNE, VOLUME);

            try {
            	Options.loadOptions();
            	if (Options.getBooleanOption(Options.ENABLED)) {
	                VolumeControl volumeControl = (VolumeControl)HolsterAid.player.getControl("VolumeControl");
	                if (volumeControl != null)
	                	volumeControl.setLevel(Options.getIntOption(Options.VOLUME));
	                HolsterAid.player.prefetch();
	                HolsterAid.player.start();
            	}
            }
            catch (Exception ex) {
            	Dialog.alert(ex.toString());
            }
        }
    }

    public void stopNotification(long consequenceID, long sourceID, long eventID, Object configuration, Object context) {
        //  We only want to respond if we initiated the event
        if (sourceID == HolsterAid.NOTIFICATIONS_ID) {            
            // Cancel the LED.
            //LED.setState(LED.STATE_OFF);
            //Alert.stopAudio();
            //Alert.stopBuzzer();
            try {
            	HolsterAid.player.stop();
            	HolsterAid.player.close();
            }
            catch (Exception ex) {
            	Dialog.alert(ex.toString());
            }
        }
    }

    /**
     * It is likely that the following call will return a separate config object for each SourceID,
     * such as data that describes user set notification settings. However, for this example,
     * we return a trivial, arbitrary config object.
     */
    public Object newConfiguration(long consequenceID, long sourceID, byte profileIndex, int level, Object context) {
        return CONFIG;
    }

    /**
     * Called when there is inbound (from the desktop) data to be converted to object form.
     */
    public SyncObject convert(DataBuffer data, int version, int UID) {
        // It's up to us to write and read the data. We apply a four byte type
        // and a 4 byte length, and then the raw data.
        try {
            int type = data.readInt();
            int length = data.readCompressedInt();

            if (type == TYPE) {
                byte[] rawdata = new byte[length];
                data.readFully(rawdata);

                return new Configuration(rawdata);
            }
        }
        // We've prematurely reached the end of the DataBuffer.
        catch(final EOFException ex) {
             UiApplication.getUiApplication().invokeLater(new Runnable() {
                 public void run() {
                     Dialog.alert(ex.toString());
                 } 
             });
        }        
        return null;
    }

    public boolean convert(SyncObject object, DataBuffer buffer, int version) {
        boolean retval = false;

        if (object instanceof Configuration) {
            Configuration c = (Configuration) object;
            buffer.writeInt(TYPE);
            buffer.writeCompressedInt(c._data.length);
            buffer.write(c._data);
            retval = true;
        }
        return retval;
    }
}
