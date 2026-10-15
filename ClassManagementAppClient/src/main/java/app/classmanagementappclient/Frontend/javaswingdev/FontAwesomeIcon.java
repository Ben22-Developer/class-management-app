/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.classmanagementappclient.Frontend.javaswingdev;

/**
 *
 * @author BEN
 */

import javax.swing.*;
import java.awt.*;

public class FontAwesomeIcon {
    private FontAwesome icon;
    private GradientType gradientType;
    private Color color1;
    private Color color2;
    private int size;

    public FontAwesome getIcon() {
        return this.icon;
    }

    public void setIcon(FontAwesome icon) {
        this.icon = icon;
    }

    public GradientType getGradientType() {
        return this.gradientType;
    }

    public void setGradientType(GradientType gradientType) {
        this.gradientType = gradientType;
    }

    public Color getColor1() {
        return this.color1;
    }

    public void setColor1(Color color1) {
        this.color1 = color1;
    }

    public Color getColor2() {
        return this.color2;
    }

    public void setColor2(Color color2) {
        this.color2 = color2;
    }

    public int getSize() {
        return this.size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public FontAwesomeIcon(FontAwesome icon, GradientType gradientType, Color color1, Color color2, int size) {
        this();
        this.icon = icon;
        this.gradientType = gradientType;
        this.color1 = color1;
        this.color2 = color2;
        this.size = size;
    }

    public FontAwesomeIcon() {
        this.icon = FontAwesome.COFFEE;
        this.gradientType = GradientType.HORIZONTAL;
        this.color1 = new Color(127, 0, 255);
        this.color2 = new Color(225, 0, 255);
        this.size = 15;
        IconFontSwing.register(FontAwesome.getIconFont());
    }

    public Icon toIcon() {
        return IconFontSwing.buildIcon(this.icon, (float)this.size, this.color1, this.color2, this.gradientType);
    }
}

