package com.skyemarine.holsteraid;

import net.rim.device.api.system.*;
import net.rim.device.api.ui.*;
import net.rim.device.api.ui.component.*;

class FormattedField extends RichTextField {
    private static final long DEFAULT_STYLE = 
    	Field.READONLY | Field.NON_FOCUSABLE | Field.USE_ALL_WIDTH | Field.FIELD_VCENTER;
    
    private int _width = 0;  
    private int _height = 0;
    private boolean _truncate = true;
  
    /*
     * 
     */
    public FormattedField() {
        this("");
    }
    
    /*
     * 
     */
    public FormattedField(String text) {
        this(text, Display.getWidth());
    }
    
    /*
     * 
     */
    public FormattedField(boolean truncate) {
        this("");
        _truncate = truncate;
    }

    /*
     * 
     */
    // Width of the field as a percentage of the display width
    public FormattedField(String text, int width) {
        this(text, width, DEFAULT_STYLE);
    }

    /*
     * 
     */
    public FormattedField(int width) {
        this("", width);
    }
       
    /*
     * 
     */
    public FormattedField(long style) {
        this("", style);
    }
    
    /*
     * 
     */
    public FormattedField(long style, int height) {
        this("", style, height);
    }
    
    /*
     * 
     */
    public FormattedField(int width, long style) {
        this("", width, style);
    }
    
    /*
     * 
     */
    public FormattedField(String text, long style) {
        this(text, Display.getWidth(), style);
    }

    /*
     * 
     */
    public FormattedField(String text, int width, long style) {
        super(style);
        super.setFont(Font.getDefault());
        _width = width;
        setText(text);
    }

    /*
     * 
     */
    public FormattedField(String text, long style, int height) {
        super(style);
        super.setFont(Font.getDefault());
        _height = height;
        setText(text);
    }

    /*
     * 
     */
    public FormattedField(String text, int width, int height) {
        super(DEFAULT_STYLE);
        super.setFont(Font.getDefault());
        _width = width;
        _height = height;
        setText(text);
    }

    /*
     * (non-Javadoc)
     * @see net.rim.device.api.ui.component.TextField#getPreferredWidth()
     */
    public int getPreferredWidth(){
        if (_width == 0) 
            return this.getFont().getAdvance(this.getLabel() + this.getText());
            //return this.getFont().getAdvance(this.getText());
        else return _width;
    }
    
    /*
     * (non-Javadoc)
     * @see net.rim.device.api.ui.component.RichTextField#getPreferredHeight()
     */
    public int getPreferredHeight(){
        if (_height == 0) 
            return this.getFont().getHeight();
        else return _height;
    }
    
    protected void layout(int width, int height) {
        width = getPreferredWidth();
        height = getPreferredHeight();
        super.layout(width, height);
        if (_truncate)
            super.setExtent(width, height);
    }
    
    /*
     * (non-Javadoc)
     * @see net.rim.device.api.ui.component.RichTextField#setText(java.lang.String)
     */
    public void setText(String text) {
        RichTextFormatter.TextFormat format = RichTextFormatter.format(text);
        super.setText(format.text, format.offsets, format.attributes, format.fonts);
    }        
}
