package com.skyemarine.holsteraid;

import net.rim.blackberry.api.phone.Phone;
import net.rim.blackberry.api.phone.PhoneListener;
import net.rim.device.api.notification.NotificationsManager;
import net.rim.device.api.system.Sensor;
import net.rim.device.api.system.SensorListener;

public class HolsterAidListener implements PhoneListener, SensorListener {
	/*
     * Event handler for incoming phone calls.
     * @see net.rim.blackberry.api.phone.PhoneListener#callIncoming(int)
     */
	public void callIncoming(int callId) {
		if (Sensor.getState(Sensor.HOLSTER) == Sensor.STATE_OUT_OF_HOLSTER) {
			NotificationsManager.triggerImmediateEvent(HolsterAid.PROFILE_ID, 0, null, null); }
	}

	public void callAnswered(int callId) {
		NotificationsManager.cancelImmediateEvent(HolsterAid.PROFILE_ID, 0, null, null);
	}

	public void callDisconnected(int callId) {
		NotificationsManager.cancelImmediateEvent(HolsterAid.PROFILE_ID, 0, null, null);
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
			Phone.addPhoneListener(this);
		}
		else if (sensorId == Sensor.HOLSTER && update ==  Sensor.STATE_IN_HOLSTER) {
			Phone.removePhoneListener(this);
		}
	}
}
