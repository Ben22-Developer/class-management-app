package app.classmanagementappclient.Frontend.TreeWindow.TreeCustomRender;

import app.classmanagementappclient.Main;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;

/**
 *
 * @author BEN
 */
public class TreeCustomRender extends DefaultTreeCellRenderer
{   
    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row,boolean hasFocus) 
    {
        super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
        String iconPath = getIconPath(value);
        String path = Main.getApplicationFilesPaths().getProperty(iconPath);
        ImageIcon icon = new ImageIcon(path);
        setIcon(icon);
        return this;
    }

    private String getIconPath (Object value) 
    {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
        int lvl = node.getLevel();
        if (lvl == 4)
            return "FileIcon";
        return "FolderIcon";
    }
}
