package DigitalClock;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Clock");
        JPanel panel = new JPanel();
        JTextField[] display = {new JTextField(), new JTextField(), new JTextField()};
        JTextField hour = display[0];
        JTextField min = display[1];
        JTextField sec = display[2];

        panel.setLayout(new GridLayout(1, 3));

        try {
            for (int i = 0; i < 3; i++) {
                display[i].setBackground(Color.BLACK);
                display[i].setForeground(Color.RED);
                display[i].setEditable(false);
                display[i].setFocusable(false);
                display[i].setHorizontalAlignment(JTextField.CENTER);
                display[i].setFont(loadCustomFont("DSEG7Classic-Regular.ttf", 50));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (JTextField field : display) {
            panel.add(field);
        }

        panel.setBounds(0, 0, 400, 100);
        frame.add(panel);

        JButton closeButton = new JButton("X");
        closeButton.addActionListener(e -> System.exit(0));
        closeButton.setBackground(Color.BLACK);
        closeButton.setForeground(Color.RED);
        closeButton.setFocusable(false);
        frame.add(closeButton, BorderLayout.NORTH);

        frame.setUndecorated(true); //set frame borderless
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(Color.BLACK);
        frame.setBounds(100, 100, 400, 120);
        frame.setAlwaysOnTop(true);
        frame.setResizable(false);

        enableWindowDrag(frame);

        frame.setVisible(true);

        // update clock every second
        while (true) {
            Clock clock = new Clock();
            hour.setText(clock.getHour());
            min.setText(clock.getMin());
            sec.setText(clock.getSec());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static Font loadCustomFont(String fontPath, float size) throws Exception {
        Font font = Font.createFont(Font.TRUETYPE_FONT, Main.class.getResourceAsStream(fontPath));
        return font.deriveFont(size);
    }

    private static void enableWindowDrag(JFrame frame) {
        MouseAdapter mouseAdapter = new MouseAdapter() {
            private int pX, pY;
    
            @Override
            public void mousePressed(MouseEvent e) {
                pX = e.getXOnScreen() - frame.getX();
                pY = e.getYOnScreen() - frame.getY();
            }
    
            @Override
            public void mouseDragged(MouseEvent e) {
                int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
                int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
    
                int newX = e.getXOnScreen() - pX;
                int newY = e.getYOnScreen() - pY;
    
                int frameWidth = frame.getWidth();
                int frameHeight = frame.getHeight();
    
                // prevent window from being dragged outside of monitor
                if (newX < 0) newX = 0; 
                if (newY < 0) newY = 0; 
                if (newX + frameWidth > screenWidth) newX = screenWidth - frameWidth;
                if (newY + frameHeight > screenHeight) newY = screenHeight - frameHeight; 
    
                frame.setLocation(newX, newY);
            }
        };
    
        addMouseEvents(frame.getContentPane(), mouseAdapter);
    }
    
    private static void addMouseEvents(Component component, MouseAdapter adapter) {
        component.addMouseListener(adapter);
        component.addMouseMotionListener(adapter);
        if (component instanceof Container) {
            for (Component child : ((Container) component).getComponents()) {
                addMouseEvents(child, adapter);
            }
        }
    }
}
