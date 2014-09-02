package com.skyemarine.holsteraid;

import java.util.*;
import net.rim.device.api.system.*;

/**
 * This class allows application options (user preferences) to be saved in persistent storage.
 */
public class Options {
    public static final String ENABLED = "Enabled";
    public static final boolean DEFAULT_ENABLED = true;
    
    public static final String SOUND_FILE_PATH = "SoundFilePath";
    public static final String DEFAULT_SOUND_FILE_PATH = "/TelephoneRing.mp3";
    
    public static final String VOLUME = "Volume";
    public static final int DEFAULT_VOLUME = 100;

    public static final String[] BOOLEAN_CHOICES = { "True", "False" };

	// Vector of application options
    private static final long _optionsPersistKey = 0xa4b4159478f59a27L;
    private static final PersistentObject _optionsPersistData = 
    	PersistentStore.getPersistentObject(_optionsPersistKey);

    private static Hashtable _options = new Hashtable();
    private static Hashtable _defaultValues = new Hashtable();
    
    public static boolean loadOptions() {
        _defaultValues.put(ENABLED, new Boolean(DEFAULT_ENABLED));
        _defaultValues.put(SOUND_FILE_PATH, DEFAULT_SOUND_FILE_PATH);
        _defaultValues.put(VOLUME, new Integer(DEFAULT_VOLUME));
    	
        _options = (Hashtable)_optionsPersistData.getContents();
        
        if (_options == null) {
        	_options = new Hashtable();
            setBooleanOption(ENABLED, DEFAULT_ENABLED);
            setStringOption(SOUND_FILE_PATH, DEFAULT_SOUND_FILE_PATH);
            setIntOption(VOLUME, DEFAULT_VOLUME);
            saveOptions();
        }
        return true;
    }

    public static void saveOptions() {
        _optionsPersistData.setContents(_options);
        _optionsPersistData.commit();
    }
    
    public static String getStringOption(String key) {
    	return getStringOption(key, (String)_defaultValues.get(key));
    }
    
    public static String getStringOption(String key, String defaultValue) {
    	System.out.println(key + "=" + defaultValue);
    	String value = defaultValue;
    	if (_options.containsKey(key))
    		value = (String)_options.get(key);
        return value;
    }
    
    public static void setStringOption(String key, String value) {
        _options.put(key, value);
    }
    
    public static int getIntOption(String key) {
    	return getIntOption(key, ((Integer)_defaultValues.get(key)).intValue());
    }
    
    public static int getIntOption(String key, int defaultValue) {
        String value = getStringOption(key, Integer.toString(defaultValue));        
        return Integer.parseInt(value);
    }

    public static void setIntOption(String key, int value)
    {
    	_options.put(key, Integer.toString(value));
    }
    
    public static boolean getBooleanOption(String key) {
    	return getBooleanOption(key, ((Boolean)_defaultValues.get(key)).booleanValue());
    }
    
    public static boolean getBooleanOption(String key, boolean defaultValue) {
        String defaultString = "FALSE";
    	if (defaultValue)
    		defaultString = "TRUE";
        return getStringOption(key, defaultString).equalsIgnoreCase("TRUE");
    }
    
    public static void setBooleanOption(String key, boolean value) {
    	if (value)
    		_options.put(key, "TRUE");
    	else
    		_options.put(key, "FALSE");
    }
} 
