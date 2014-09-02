package com.skyemarine.holsteraid;

import net.rim.device.api.notification.NotificationsConstants;
import net.rim.device.api.notification.NotificationsManager;
import net.rim.device.api.system.RuntimeStore;
import net.rim.device.api.system.Sensor;
import net.rim.device.api.ui.Color;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.MenuItem;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.ObjectChoiceField;
import net.rim.device.api.ui.container.MainScreen;
import net.rim.device.api.ui.container.VerticalFieldManager;
import net.rim.device.api.ui.decor.BackgroundFactory;

public class CopyOfHolsterAid extends UiApplication {
	//private static FilePicker _filePicker;
    //private static String _soundFilePath = "";

	public static final long ID = 0x7451402f595f81a1L;
	public static final long ID2 = 0x7451402f595f81a2L;
	private static final long ID3 = 0x749cb23a75c60e2dL;  //com.samples.simpleNotification

	private static HolsterAidListener myListener;

    public static void main(String[] args) {
        // The toString value of this object will be displayed in the user's Profile settings
    	Object myProfile = new Object() {
    		public String toString() {
    			return "HolsterAid";
    		}};   		
       	//NotificationsManager.deregisterSource(ID3);
       	NotificationsManager.registerSource(ID2, myProfile, NotificationsConstants.IMPORTANT);

    	CopyOfHolsterAid app = new CopyOfHolsterAid();
    	//RuntimeStore.getRuntimeStore().replace(ID, app);
    	app.enterEventDispatcher();
    }
        
    public CopyOfHolsterAid() {
        myListener = new HolsterAidListener();
        Sensor.addListener(this, myListener, Sensor.HOLSTER);

    	//A MenuItem used to fire the event.
        MenuItem fireEvent = new MenuItem("Trigger Event", 10, 10) {
            public void run() {
            	NotificationsManager.triggerImmediateEvent(ID2, 0, null, null);
            }
        };    	

        HolsterAidScreen screen = new HolsterAidScreen();
        screen.addMenuItem(fireEvent);
    	pushScreen(screen);
    }
    
	class HolsterAidScreen extends MainScreen {
	    private ObjectChoiceField _enabled;
	    //private NumericChoiceField _volume;
	    //private ButtonField _defaultSoundFile;
	    //private ButtonField _customSoundFile;	    
	
	    HolsterAidScreen() {
	    	setTitle("HolsterAid");
	        getMainManager().setBackground(BackgroundFactory.
	        	createLinearGradientBackground(Color.AZURE, Color.AZURE, Color.CADETBLUE, Color.CADETBLUE));        
	    	
	        Options.loadOptions();

	        VerticalFieldManager fields = new VerticalFieldManager(Field.USE_ALL_WIDTH);
	
	        _enabled = new ObjectChoiceField("Enabled:", Options.BOOLEAN_CHOICES);
	        boolean enabled = Options.getBooleanOption(Options.ENABLED);
	        if (enabled)
	        	_enabled.setSelectedIndex(0);
	        else
	        	_enabled.setSelectedIndex(1);
	        fields.add(_enabled);

	        /*
	        _volume = new NumericChoiceField("Volume:", 10, 100, 10);
	        _volume.setSelectedValue(Options.getIntOption(Options.VOLUME));
	        fields.add(_volume);

	        _soundFilePath = Options.getStringOption(Options.SOUND_FILE_PATH, Options.DEFAULT_SOUND_FILE_PATH);
	        HorizontalFieldManager defaultSoundFileManager = 
	        	new HorizontalFieldManager(Field.USE_ALL_WIDTH);
	        defaultSoundFileManager.add(new LabelField("Sound file:", Field.FIELD_LEFT | Field.FIELD_VCENTER));

	        _defaultSoundFile = new ButtonField("Default", Field.FIELD_RIGHT)
	        {
	     	    protected void fieldChangeNotify(int context) {
	     	    	super.fieldChangeNotify(context);
	     	    	_soundFilePath = Options.DEFAULT_SOUND_FILE_PATH;
	        	}
	        };
	        VerticalFieldManager dumbWorkaround1 = new VerticalFieldManager(Field.USE_ALL_WIDTH);
	        dumbWorkaround1.add(_defaultSoundFile);	        
	        defaultSoundFileManager.add(dumbWorkaround1);
	        fields.add(defaultSoundFileManager);
	        
	        HorizontalFieldManager customSoundFileManager =
	        	new HorizontalFieldManager(Field.USE_ALL_WIDTH);
	        _customSoundFile = new ButtonField("Custom", Field.FIELD_RIGHT) {
	     	    protected void fieldChangeNotify(int context) {
	     	    	super.fieldChangeNotify(context);
     	        	invokeLater(new Runnable() {
	     	   	    	public void run() {
	     	 	        	String filePath = null;
	     	 	        	String[] fileExtensions = { ".mp3" };
	     	   	    		if (_soundFilePath != null && _soundFilePath.length() > 0 &&
	     	 	        			!_soundFilePath.equals(Options.DEFAULT_SOUND_FILE_PATH)) {
	     	 	        		int slash = _soundFilePath.lastIndexOf('/');
    	 	        			filePath = _soundFilePath.substring(0, slash);  
    	 	        			//String filePath = _soundFilePath.substring(0, slash);  
	     	 	        		//_filePicker.setPath(filePath + "/");    	 	        			
	     	 	        	}
	     	   	    		FilePickerPopup _filePicker = new FilePickerPopup(filePath, fileExtensions);
	     	 	        	//String soundFilePath = _filePicker.show();
	     	 		        _filePicker.pickFile();
	     	 		        String soundFilePath = _filePicker.getFile();
	     			    	if (soundFilePath != null)
	     			    		_soundFilePath = soundFilePath;
	     	   	    	}
	     	    	});
	     	    }
	        };
	        VerticalFieldManager dumbWorkaround2 = new VerticalFieldManager(Field.USE_ALL_WIDTH);
	        dumbWorkaround2.add(_customSoundFile);	        
	        customSoundFileManager.add(dumbWorkaround2);
	        fields.add(customSoundFileManager);
	        
	        //_filePicker = FilePicker.getInstance();
	        //_filePicker.setFilter(".mp3");
	        */
	        add(fields);
	    }
	    
	    protected boolean onSave() {
	        if (_enabled.isDirty())
        		Options.setBooleanOption(Options.ENABLED, _enabled.getSelectedIndex() == 0);	        		

        	/*
        	if (_volume.isDirty())
        		Options.setIntOption(Options.VOLUME, _volume.getSelectedValue());

        	if (_soundFilePath == null)
        		Options.setStringOption(Options.SOUND_FILE_PATH, Options.DEFAULT_SOUND_FILE_PATH);
        	else
        		Options.setStringOption(Options.SOUND_FILE_PATH, _soundFilePath);
	        */
        	
	        Options.saveOptions();	        
	        return true;
	    }
	}
}
