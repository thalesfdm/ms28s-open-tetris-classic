package Tetris;

import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class ControlsWindow extends JFrame {
    public static Image icon;

    /**
     * Create the application.
     */
    public ControlsWindow() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        icon = new ImageIcon("graphics/pieces/6.png").getImage();
        setIconImage(icon);
        setTitle("Controls");
        setResizable(false);
        setLocationRelativeTo(null);
        setSize(271, 306);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        getContentPane().setLayout(null);

        JLabel lblMoveLeft = new JLabel("Move Left");
        lblMoveLeft.setBounds(34, 11, 77, 14);
        getContentPane().add(lblMoveLeft);

        JButton button = new JButton("VK_LEFT");
        button.setBounds(10, 31, 104, 23);
        getContentPane().add(button);

        JButton button_1 = new JButton("VK_RIGHT");
        button_1.setBounds(142, 31, 104, 23);
        getContentPane().add(button_1);

        JLabel label = new JLabel("Move Right");
        label.setBounds(166, 11, 77, 14);
        getContentPane().add(label);

        JButton button_2 = new JButton("VK_A");
        button_2.setBounds(10, 101, 104, 23);
        getContentPane().add(button_2);

        JLabel label_1 = new JLabel("Rotate Left");
        label_1.setBounds(34, 81, 77, 14);
        getContentPane().add(label_1);

        JButton button_3 = new JButton("VK_D");
        button_3.setBounds(142, 101, 104, 23);
        getContentPane().add(button_3);

        JLabel label_2 = new JLabel("Rotate Right");
        label_2.setBounds(166, 81, 77, 14);
        getContentPane().add(label_2);

        JButton button_4 = new JButton("VK_UP");
        button_4.setBounds(10, 166, 104, 23);
        getContentPane().add(button_4);

        JLabel label_3 = new JLabel("Drop Piece");
        label_3.setBounds(34, 146, 77, 14);
        getContentPane().add(label_3);

        JButton button_5 = new JButton("VK_DOWN");
        button_5.setBounds(142, 166, 104, 23);
        getContentPane().add(button_5);

        JLabel label_4 = new JLabel("Move Down");
        label_4.setBounds(166, 146, 77, 14);
        getContentPane().add(label_4);

        JButton btnDefaults = new JButton("Defaults");
        btnDefaults.setBounds(10, 200, 235, 23);
        getContentPane().add(btnDefaults);

        JButton btnApply = new JButton("Apply");
        btnApply.setBounds(10, 234, 89, 23);
        getContentPane().add(btnApply);

        JButton btnClose = new JButton("Close");
        btnClose.setBounds(157, 234, 89, 23);
        getContentPane().add(btnClose);

        btnClose.addActionListener(e -> setVisible(false));

        btnApply.addActionListener(e -> setVisible(false));

        btnDefaults.addActionListener(e -> {
        });
    }
}
