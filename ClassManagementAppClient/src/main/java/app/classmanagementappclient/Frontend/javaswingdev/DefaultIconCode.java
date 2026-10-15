/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.classmanagementappclient.Frontend.javaswingdev;

/**
 *
 * @author BEN
 */

class DefaultIconCode implements IconCode {
    private final char unicode;
    private final String fontFamily;

    public DefaultIconCode(String fontFamily, char unicode) {
        this.fontFamily = fontFamily;
        this.unicode = unicode;
    }

    public String name() {
        return "[" + this.getUnicode() + "]";
    }

    public char getUnicode() {
        return this.unicode;
    }

    public String getFontFamily() {
        return this.fontFamily;
    }
}
