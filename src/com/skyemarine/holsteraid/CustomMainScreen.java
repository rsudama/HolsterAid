package com.skyemarine.holsteraid;

import net.rim.device.api.ui.*;
import net.rim.device.api.ui.component.*;
import net.rim.device.api.ui.container.*;
import net.rim.device.api.ui.decor.*;

public class CustomMainScreen extends MainScreen {
    public static final int BackgroundColor = Color.LIGHTSKYBLUE;
    public static final int ContentColor = Color.POWDERBLUE;

    private FormattedField _titleField = new FormattedField
		(LabelField.USE_ALL_WIDTH | LabelField.READONLY | LabelField.NON_FOCUSABLE);    
    private VerticalFieldManager _container;
    private FormattedField _statusField = new FormattedField(false);

    public CustomMainScreen(String title, Menu menu) {
        this(title);
    }
    
    public CustomMainScreen(String title) {
        super(Manager.FOCUSABLE | Manager.VERTICAL_SCROLL | Manager.VERTICAL_SCROLLBAR | Manager.USE_ALL_WIDTH);
        
        _titleField.setText(title);
        setTitle(_titleField);

        getMainManager().setBackground(BackgroundFactory.
            createLinearGradientBackground(Color.AZURE, Color.AZURE, Color.CADETBLUE, Color.CADETBLUE));        
        
        _statusField.setBackground(BackgroundFactory.createSolidBackground(Color.LIGHTSTEELBLUE));
        setStatus(_statusField);

        _container = new VerticalFieldManager
        	(Manager.FOCUSABLE | Manager.VERTICAL_SCROLL | Manager.VERTICAL_SCROLLBAR | Manager.USE_ALL_WIDTH);
        super.add(_container);
    }
   
    public void add(Field field) {
        _container.add(field);
    }
    
    public void deleteAll() {
        _container.deleteAll();
    }
    
    public void delete(Field field) {
        _container.delete(field);
    }
    
    public void setStatusText(String msg) {
        RichTextFormatter.TextFormat format = RichTextFormatter.format(msg);
        _statusField.setText(format.text, format.offsets, format.attributes, format.fonts);
    }
}
