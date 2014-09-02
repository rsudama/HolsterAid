package com.skyemarine.holsteraid;

import net.rim.blackberry.api.phone.Phone;
import net.rim.blackberry.api.phone.PhoneListener;
import net.rim.device.api.notification.NotificationsConstants;
import net.rim.device.api.notification.NotificationsManager;
import net.rim.device.api.system.Application;
import net.rim.device.api.system.RuntimeStore;
import net.rim.device.api.system.Sensor;
import net.rim.device.api.system.SensorListener;
import net.rim.device.api.ui.Color;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.ButtonField;
import net.rim.device.api.ui.component.SeparatorField;
import net.rim.device.api.ui.component.TextField;
import net.rim.device.api.ui.container.MainScreen;
import net.rim.device.api.ui.container.VerticalFieldManager;
import net.rim.device.api.ui.decor.BackgroundFactory;

public class HolsterAid extends UiApplication implements PhoneListener, SensorListener {
	public static final long PROFILE_ID = 0x7451402f595f81a9L;
	public static final long RTS_ID = 0x7451402f595f81b9L;

	public static void main(String[] args) {
        // The toString value of this object will be displayed in the user's Profile settings
    	Object holsterAidProfile = new Object() {
    		public String toString() {
    			return "HolsterAid";
    		}};   		
       	NotificationsManager.registerSource(PROFILE_ID, holsterAidProfile, NotificationsConstants.IMPORTANT);

    	HolsterAid app = new HolsterAid();
    	app.enterEventDispatcher();
    }
        
    public HolsterAid() {
		//holsterAidListener = new HolsterAidListener();
        Sensor.addListener(Application.getApplication(), this, Sensor.HOLSTER);
		Phone.addPhoneListener(this);

		RuntimeStore.getRuntimeStore().replace(RTS_ID, Application.getApplication());

    	HolsterAidScreen screen = new HolsterAidScreen();
    	pushScreen(screen);
    }
    
	/*
     * Event handler for holster sensor.
     */
	public void onSensorUpdate(int sensorId, int update) {
		if (sensorId == Sensor.HOLSTER && update == Sensor.STATE_OUT_OF_HOLSTER) {
			Phone.addPhoneListener(this);
		}
		else if (sensorId == Sensor.HOLSTER && update ==  Sensor.STATE_IN_HOLSTER) {
			Phone.removePhoneListener(this);
		}
	}
	
	/*
     * Event handler for incoming phone calls.
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

    class HolsterAidScreen extends MainScreen {
	    HolsterAidScreen() {
	    	setTitle("HolsterAid");
	        getMainManager().setBackground(BackgroundFactory.
	        	createLinearGradientBackground(Color.AZURE, Color.AZURE, Color.CADETBLUE, Color.CADETBLUE));        
	    	
	        VerticalFieldManager fields = new VerticalFieldManager(Field.USE_ALL_WIDTH);
	        
	        String doc1 = "How to use HolsterAid:";
	        TextField docs1 = new TextField(Field.READONLY | Field.NON_FOCUSABLE | Field.VISUAL_STATE_DISABLED);
	        docs1.setText(doc1);
	        fields.add(docs1);
	        fields.add(new SeparatorField());
	        
	        String doc2 = "- Open 'Sound and Alert Profiles'\n";
	        doc2 += "- Select 'Change Sounds and Alerts'\n";
	        doc2 += "- Select 'Profile Management'\n";
	        doc2 += "- Select Active Profile (check)\n";
	        doc2 += "- Select 'Phone'\n";
	        doc2 += "- Select desired in-holster Ringtone, Volume, etc.\n";
	        doc2 += "- Select 'Play sound: In holster'\n";
	        doc2 += "- Escape and Save";
	        TextField docs2 = new TextField(Field.READONLY | Field.NON_FOCUSABLE | Field.VISUAL_STATE_DISABLED);
	        docs2.setText(doc2);
	        fields.add(docs2);
	        fields.add(new SeparatorField());
	        
	        String doc3 = "- Select 'Other Applications - Notifiers'\n";
	        doc3 += "- Select 'HolsterAid'\n";
	        doc3 += "- Select desired out-of-holster Notifier Tone, Volume, etc.\n";
	        doc3 += "- Escape and Save";
	        TextField docs3 = new TextField(Field.READONLY | Field.NON_FOCUSABLE | Field.VISUAL_STATE_DISABLED);
	        docs3.setText(doc3);
	        fields.add(docs3);
	        fields.add(new SeparatorField());
	        
	        fields.add(new TextField(Field.READONLY | Field.NON_FOCUSABLE | Field.VISUAL_STATE_DISABLED));
	        
	        ButtonField testButton = new ButtonField("Test HolsterAid Profile", Field.FIELD_HCENTER);
	        FieldChangeListener listener = new FieldChangeListener() {
	            public void fieldChanged(Field field, int context) {
	                invokeLater(new Runnable() {
						public void run() {
	                		NotificationsManager.triggerImmediateEvent(HolsterAid.PROFILE_ID, 0, null, null);
	                		//System.out.println("Button pressed: " + buttonField.getLabel());
	                	}
	                });
	            }
	        };
	        testButton.setChangeListener(listener);
	        fields.add(testButton);
	        add(fields);
	    }

	    /**
	     * Prevent the save dialog from being displayed.
	     */
	    public boolean onSavePrompt() {
	        return true;
	    }
	}
}
