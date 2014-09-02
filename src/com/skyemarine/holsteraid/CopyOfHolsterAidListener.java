package com.skyemarine.holsteraid;

import net.rim.blackberry.api.phone.Phone;
import net.rim.blackberry.api.phone.PhoneListener;
import net.rim.device.api.notification.NotificationsManager;
import net.rim.device.api.system.Sensor;
import net.rim.device.api.system.SensorListener;

public class CopyOfHolsterAidListener implements PhoneListener, SensorListener {
    //private static Player _player = null;

	/*
	public HolsterAidListener() {
		try	{
			Phone.removePhoneListener(this);
			Phone.addPhoneListener(this);
		}
		catch (Exception ex) {}
	}
	*/

	/*
     * Event handler for incoming phone calls.
     * @see net.rim.blackberry.api.phone.PhoneListener#callIncoming(int)
     */
	public void callIncoming(int callId) {
		if (Sensor.getState(Sensor.HOLSTER) == Sensor.STATE_OUT_OF_HOLSTER) {
			NotificationsManager.triggerImmediateEvent(HolsterAid.ID2, 0, null, null); }

		/*			
			Options.loadOptions();
			if (Options.getBooleanOption(Options.ENABLED) && _player == null) {
	            try {
	            	String soundFilePath = Options.getStringOption
	            		(Options.SOUND_FILE_PATH, Options.DEFAULT_SOUND_FILE_PATH);
	            	InputStream inputStream = null;
	            	if (soundFilePath.equals(Options.DEFAULT_SOUND_FILE_PATH))
	            		inputStream = this.getClass().getResourceAsStream(soundFilePath);
	            	else
	                	inputStream = Connector.openInputStream(soundFilePath);
	                _player = Manager.createPlayer(inputStream, "audio/mpeg");
	                _player.setLoopCount(NotificationsConstants.CONTINUOUS_REPEAT);
	                _player.realize();
	                VolumeControl volumeControl = (VolumeControl)_player.getControl("VolumeControl");
	                if (volumeControl != null)
	                	volumeControl.setLevel(Options.getIntOption(Options.VOLUME));
	                _player.prefetch();
	                _player.start();
	            }
	            catch (Exception ex) {
	            	Dialog.alert(ex.toString());
	            }
			}
		}
		*/
	}

	public void callAnswered(int callId) {
		NotificationsManager.cancelImmediateEvent(HolsterAid.ID2, 0, null, null);
		/*
		if (_player != null && _player.getState() == Player.STARTED) {
			try {
				_player.stop();
				_player.close();
				_player = null;
			}
			catch (Exception ex) {}
		}
		*/
	}

	public void callDisconnected(int callId) {
		NotificationsManager.cancelImmediateEvent(HolsterAid.ID2, 0, null, null);
		/*
		if (_player != null && _player.getState() == Player.STARTED) {
			try {
				_player.stop();
				_player.close();
				_player = null;
			}
			catch (Exception ex) {}
		}
		*/
	}

	// Required methods for implementing PhoneListener interface
	public void callAdded(int callId) {}
	public void callConferenceCallEstablished(int callId) {}
	public void callConnected(int callId) {}
	public void callDirectConnectConnected(int callId) {}
	public void callDirectConnectDisconnected(int callId) {}
	public void callEndedByUser(int callId) {}
	public void callFailed(int callId, int reason) {}
	public void callHeld(int callId) {}
	public void callInitiated(int callid) {}
	public void callRemoved(int callId) {}
	public void callResumed(int callId) {}
	public void callWaiting(int callid) {}
	public void conferenceCallDisconnected(int callId) {}

	public void onSensorUpdate(int sensorId, int update) {
		if (sensorId == Sensor.HOLSTER && update == Sensor.STATE_OUT_OF_HOLSTER) {
			//NotificationsManager.registerConsequence(HolsterAidConsequence.ID, new HolsterAidConsequence());
			Phone.addPhoneListener(this);
		}
		else if (sensorId == Sensor.HOLSTER && update ==  Sensor.STATE_IN_HOLSTER) {
			//NotificationsManager.deregisterConsequence(HolsterAidConsequence.ID);
			Phone.removePhoneListener(this);
		}
	}
}
