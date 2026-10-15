package app.classmanagementappclient.Frontend.DashboardView.ChatTry.src.raven.chat.component;

import app.classmanagementappclient.Backend.Objects.Enum.ChatType;
import app.classmanagementappclient.Backend.Objects.WebObject.Chat.Chat;
import app.classmanagementappclient.Frontend.DashboardView.ChatTry.src.raven.chat.BackendConnector.BackendToMenuUtility;
import app.classmanagementappclient.Frontend.DashboardView.ChatTry.src.raven.chat.animation.AnimationFloatingButton;
import app.classmanagementappclient.Frontend.DashboardView.ChatTry.src.raven.chat.animation.AnimationScroll;
import app.classmanagementappclient.Frontend.DashboardView.ChatTry.src.raven.chat.model.ModelMessage;
import app.classmanagementappclient.Frontend.DashboardView.ChatTry.src.raven.chat.swing.Button;
import app.classmanagementappclient.Frontend.DashboardView.ChatTry.src.raven.chat.swing.RoundPanel;
import app.classmanagementappclient.Frontend.DashboardView.ChatTry.src.raven.chat.swing.RoundPanels.BodyRoundPanel;
import app.classmanagementappclient.Frontend.DashboardView.ChatTry.src.raven.chat.swing.RoundPanels.BottomRoundPanel;
import app.classmanagementappclient.Frontend.DashboardView.ChatTry.src.raven.chat.swing.RoundPanels.TitleRoundPanel;
import app.classmanagementappclient.Frontend.DashboardView.ChatTry.src.raven.chat.swing.TextField;
import app.classmanagementappclient.Frontend.DashboardView.ChatTry.src.raven.chat.swing.scroll.ScrollBar;
import app.classmanagementappclient.Frontend.javaswingdev.*;
import app.classmanagementappclient.Main;
import net.miginfocom.swing.MigLayout;

import javax.accessibility.AccessibleContext;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Timestamp;
import java.util.List;

public class ChatArea extends JPanel {

    
    private JLabel connectionLabel;
    private MigLayout layout;
    private MigLayout layoutLayered;
    private JLayeredPane layeredPane;
    private JPanel header;
    private JPanel body;
    private JPanel bottom;
    private TextField textMessage;
    private JScrollPane scrollBody;
    private Button floatingButton;
    private JLabel labelTitle;
    private final Icon userIcon;
    private final Icon botIcon;    
    private AnimationScroll animationScroll;
    private AnimationFloatingButton animationFloatingButton;    

    public ChatArea(List<List<Object>> chatList, JLabel connectionLabel) {
        
        userIcon = new ImageIcon(String.valueOf(Main.getApplicationFilesPaths().get("chatUserIcon")));
        botIcon = new ImageIcon(String.valueOf(Main.getApplicationFilesPaths().get("chatBotIcon")));

        init();
        initAnimator();
        initChat(chatList);
        this.connectionLabel = connectionLabel;
        // add to view the userMsgs && robotMsgs
    }

    private void init() {
        setOpaque(false);
        layout = new MigLayout("fill, wrap, inset 0", "[fill]", "[fill,40!][fill, 100%][shrink 0,::30%]");
        header = createHeader();
        body = createBody();
        bottom = createBottom();
        layeredPane = createLayeredPane();
        scrollBody = createScroll();
        scrollBody.setViewportView(body);
        scrollBody.setVerticalScrollBar(new ScrollBar());
        scrollBody.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollBody.getViewport().setOpaque(false);
        scrollBody.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
            private int oldValues;

            @Override
            public void adjustmentValueChanged(AdjustmentEvent e) {
                int value = scrollBody.getVerticalScrollBar().getValue();
                int extent = scrollBody.getVerticalScrollBar().getModel().getExtent();
                if ((value + extent) >= scrollBody.getVerticalScrollBar().getMaximum() - 300) {
                    animationFloatingButton.hide();
                } else if (oldValues <= e.getValue()) {
                    if (!animationScroll.isRunning()) {
                        animationFloatingButton.show();
                    }
                }

            }
        });
        floatingButton = createFloatingButton();
        layeredPane.setLayer(floatingButton, JLayeredPane.POPUP_LAYER);
        layeredPane.add(floatingButton, "pos 100%-50 100%,h 40,w 40");
        layeredPane.add(scrollBody);
        setLayout(layout);
        add(header);
        add(layeredPane);
        add(bottom);
    }

    private void initAnimator() {
        animationScroll = new AnimationScroll(body);
        animationFloatingButton = new AnimationFloatingButton(layoutLayered, floatingButton);
    }
    
    private void initChat (List<List<Object>> chatList) {
        
        List<Chat> chat = BackendToMenuUtility.formatTeacher_BotChats(chatList);
        
        chat.forEach(e -> 
        {
            Icon icon = e.getChatType() == ChatType.BOT ? botIcon : userIcon;
            String message = e.getContent();
            Timestamp timestamp = e.getTimestamp();
            ChatBox.BoxType boxType = e.getChatType() == ChatType.BOT ? ChatBox.BoxType.LEFT : ChatBox.BoxType.RIGHT;
            
            var modelMessage = new ModelMessage(icon, message, timestamp);
            addChatBox(modelMessage, boxType);
        });
    }

    private JPanel createHeader() {
        TitleRoundPanel panel = new TitleRoundPanel();
        panel.setLayout(new MigLayout("fill, inset 2"));
        panel.setBackground(new Color(255, 255, 255, 20));
        labelTitle = new JLabel();
        labelTitle.setFont(new Font("Arial", Font.BOLD, 16));
//        labelTitle.setForeground(Color.WHITE);
        panel.add(labelTitle);
        return panel;
    }

    private JPanel createBody() {
        RoundPanel panel = new BodyRoundPanel();
        panel.setLayout(new MigLayout("wrap,fillx"));
        return panel;
    }

    private JPanel createBottom() {
        RoundPanel panel = new BottomRoundPanel();
        panel.setBackground(new Color(255, 255, 255, 20)); // String layoutConstraints, String colConstraints, String rowConstraints
        panel.setLayout(new MigLayout("fill, inset 2", "[fill]2[fill,34!]", "[bottom]"));
        GoogleMaterialIcon iconSend = new GoogleMaterialIcon(GoogleMaterialDesignIcon.SEND, GradientType.VERTICAL, new Color(0, 133, 237), new Color(90, 182, 255), 20);
        Button cmdSend = new Button();
        cmdSend.setFocusable(false);
        cmdSend.setIcon(iconSend.toIcon());
        textMessage = new TextField();
        textMessage.setHint("Write a message here ...");
        textMessage.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                
                revalidate();
                
                if (e.getKeyCode() == KeyEvent.VK_ENTER)
                    chat(cmdSend);
            }
        });
        cmdSend.addActionListener((ActionEvent e) -> 
        {
            chat(cmdSend);
        });
        JScrollPane scroll = createScroll();
        scroll.setViewportView(textMessage);
        scroll.getViewport().setOpaque(false);
        scroll.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
        scroll.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 0));
        panel.add(scroll);
        panel.add(cmdSend, "height 34!");
        return panel;
    }

    private JLayeredPane createLayeredPane() {
        JLayeredPane layer = new JLayeredPane();
        layoutLayered = new MigLayout("fill,inset 0", "[fill]", "[fill]");
        layer.setLayout(layoutLayered);
        return layer;
    }

    private Button createFloatingButton() {
        
        Button button = new Button();
        button.setBorder(null);
        FontAwesomeIcon icon = new FontAwesomeIcon(FontAwesome.ANGLE_DOWN, GradientType.VERTICAL, new Color(79, 79, 79, 240), new Color(248, 248, 248, 240), 35);
        button.setIcon(icon.toIcon());
        button.setRound(40);
        button.setBackground(new Color(100, 100, 100, 100));
        button.setPaintBackground(true);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                animationScroll.scrollVertical(scrollBody, scrollBody.getVerticalScrollBar().getMaximum());
            }
        });
        return button;
    }

    private JScrollPane createScroll() {
        JScrollPane scroll = new JScrollPane();
        scroll.setBorder(null);
        scroll.setViewportBorder(null);
        return scroll;
    }
    
    private void chat (JButton cmdSend) {
        new ChatSendingUtility(this, cmdSend).chat();
    }
    

    public void addChatBox(ModelMessage message, ChatBox.BoxType type) {
        int values = scrollBody.getVerticalScrollBar().getValue();
        
        var s = new ChatBox(type, message);
        
        if (type == ChatBox.BoxType.LEFT) {
            
            
            body.add(s, "width ::50%");
            
        } else {
            body.add(s, "al right,width ::50%");
        }
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                body.revalidate();
                scrollBody.getVerticalScrollBar().setValue(values);
                bottom.revalidate();
            }
        });
        body.repaint();
        body.revalidate();
        scrollBody.revalidate();
        scrollToBottom();
    }

    public void clearChatBox() {
        body.removeAll();
        body.repaint();
        body.revalidate();
    }
 

    private void scrollToBottom() {
        animationScroll.scrollVertical(scrollBody, scrollBody.getVerticalScrollBar().getMaximum());
    }

    public String getText() {
        return textMessage.getText();
    }

    public void setTitle(String title) {
        SwingUtilities.invokeLater(() -> {
            labelTitle.setText(title);
        });
    }

    public String getTitle() {
        return labelTitle.getText();
    }

    public void setText(String text) {
        textMessage.setText(text);
    }

    public void textGrabFocus() {
        textMessage.grabFocus();
    }

    public void clearTextAndGrabFocus() {
        textMessage.setText("");
        textMessage.grabFocus();
    }

    public AnimationScroll getAnimationScroll() {
        return animationScroll;
    }

    public AnimationFloatingButton getAnimationFloatingButton() {
        return animationFloatingButton;
    }

    public JLabel getConnectionLabel() {
        return connectionLabel;
    }

    public MigLayout getLayout() {
        return layout;
    }

    public MigLayout getLayoutLayered() {
        return layoutLayered;
    }

    public JLayeredPane getLayeredPane() {
        return layeredPane;
    }

    public JPanel getHeader() {
        return header;
    }

    public JPanel getBody() {
        return body;
    }

    public JPanel getBottom() {
        return bottom;
    }

    public TextField getTextMessage() {
        return textMessage;
    }

    public JScrollPane getScrollBody() {
        return scrollBody;
    }

    public Button getFloatingButton() {
        return floatingButton;
    }

    public JLabel getLabelTitle() {
        return labelTitle;
    }

    public Icon getUserIcon() {
        return userIcon;
    }

    public Icon getBotIcon() {
        return botIcon;
    }

    public AccessibleContext getAccessibleContext() {
        return accessibleContext;
    }    
}