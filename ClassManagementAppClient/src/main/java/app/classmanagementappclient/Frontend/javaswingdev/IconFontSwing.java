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
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

final class IconFontSwing {
    private static List<IconFont> fonts = new ArrayList();

    public static synchronized void register(IconFont iconFont) {
        if (iconFont != null && !fonts.contains(iconFont)) {
            fonts.add(iconFont);
        }

    }

    public static final synchronized Font buildFont(String fontFamily) {
        try {
            for(IconFont iconFont : fonts) {
                if (iconFont.getFontFamily().equals(fontFamily)) {
                    return Font.createFont(0, iconFont.getFontInputStream());
                }
            }
        } catch (IOException | FontFormatException ex) {
            Logger.getLogger(IconFontSwing.class.getName()).log(Level.SEVERE, "Font load failure", ex);
        }

        Logger.getLogger(IconFontSwing.class.getName()).log(Level.SEVERE, "Font not found: " + fontFamily);
        throw new IllegalArgumentException("Font not found: " + fontFamily);
    }

    private IconFontSwing() {
    }

    public static Image buildImage(IconCode iconCode, float size) {
        return buildImage(iconCode, size, Color.BLACK);
    }

    public static Image buildImage(IconCode iconCode, float size, Color color) {
        Font font = buildFont(iconCode, size);
        String text = Character.toString(iconCode.getUnicode());
        return buildImage(text, font, color);
    }

    public static Icon buildIcon(IconCode iconCode, float size) {
        return buildIcon(iconCode, size, Color.BLACK);
    }

    public static Icon buildIcon(IconCode iconCode, float size, Color color) {
        return new ImageIcon(buildImage(iconCode, size, color));
    }

    public static Image buildImage(IconCode iconCode, float size, Color color1, Color color2, GradientType type) {
        Font font = buildFont(iconCode, size);
        String text = Character.toString(iconCode.getUnicode());
        return buildImage(text, font, color1, color2, type);
    }

    public static Icon buildIcon(IconCode iconCode, float size, Color color1, Color color2, GradientType type) {
        return new ImageIcon(buildImage(iconCode, size, color1, color2, type));
    }

    private static BufferedImage buildImage(String text, Font font, Color color) {
        JLabel label = new JLabel(text);
        label.setForeground(color);
        label.setFont(font);
        Dimension dim = label.getPreferredSize();
        int width = dim.width + 1;
        int height = dim.height + 1;
        label.setSize(width, height);
        BufferedImage bufImage = new BufferedImage(width, height, 2);
        Graphics2D g2d = bufImage.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        label.print(g2d);
        g2d.dispose();
        return bufImage;
    }

    private static BufferedImage buildImage(String text, Font font, Color color1, Color color2, GradientType type) {
        JLabel label = new JLabel(text);
        label.setForeground(color1);
        label.setFont(font);
        Dimension dim = label.getPreferredSize();
        int width = dim.width + 1;
        int height = dim.height + 1;
        label.setSize(width, height);
        BufferedImage bufImage = new BufferedImage(width, height, 2);
        Graphics2D g2d = bufImage.createGraphics();
        g2d.setFont(font);
        FontMetrics ft = g2d.getFontMetrics();
        Rectangle2D r2 = ft.getStringBounds(text, g2d);
        double x = ((double)width - r2.getWidth()) / (double)2.0F;
        double y = ((double)height - r2.getHeight()) / (double)2.0F;
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        int x1;
        int x2;
        int y1;
        int y2;
        if (type != GradientType.HORIZONTAL && type != null) {
            if (type == GradientType.VERTICAL) {
                x1 = 0;
                y1 = 0;
                x2 = 0;
                y2 = height;
            } else if (type == GradientType.DIAGONAL_1) {
                x1 = 0;
                y1 = height;
                x2 = width;
                y2 = 0;
            } else {
                x1 = 0;
                y1 = 0;
                x2 = width;
                y2 = height;
            }
        } else {
            x1 = 0;
            y1 = 0;
            x2 = width;
            y2 = 0;
        }

        GradientPaint gra = new GradientPaint((float)x1, (float)y1, color1, (float)x2, (float)y2, color2);
        g2d.setPaint(gra);
        g2d.drawString(text, (int)x, (int)(y + (double)ft.getAscent()));
        g2d.dispose();
        return bufImage;
    }

    private static Font buildFont(IconCode iconCode, float size) {
        Font font = buildFont(iconCode.getFontFamily());
        return font.deriveFont(size);
    }
}

