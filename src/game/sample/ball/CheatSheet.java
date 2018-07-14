package game.sample.ball;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CheatSheet extends JFrame {
    JTextField jtf = new JTextField(30);
    JButton jb = new JButton("Cheat And Chill!");

    public CheatSheet(GameState state) {
        setSize(300, 300);
        setLayout(null);
        jb.setBounds(70,150,125,45);
        add(jb);
        jtf.setBounds(20,50,250,40);
        jtf.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equalsIgnoreCase("health")) {
                    state.mainTankHealth+= 300;
                }
                else if (e.getActionCommand().equalsIgnoreCase("cannon")) {
                    state.numOfHeavyBullets+= 100;
                }
                else if (e.getActionCommand().equalsIgnoreCase("machineGun")) {
                    state.numOfMachineGunBullets+= 1000;
                }
                else if (e.getActionCommand().equalsIgnoreCase("health")) {
                    state.mainTankHealth+= 300;
                }
                dispose();
            }
        });
        add(jtf);
        setVisible(true);
    }
}
