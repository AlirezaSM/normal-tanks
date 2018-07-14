package game.sample.ball;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class Menu extends JFrame {

    //Menu frame height and width.
    public static final int GAME_HEIGHT = 720;                  // 720p game resolution
    public static final int GAME_WIDTH = 16 * GAME_HEIGHT / 9;  // wide aspect ratio

    //Buttons.
    private JButton singlePlayerButton = new JButton(new ImageIcon(".\\menuIcons\\singlePlayerButtonIcon.png"));
    private JButton multiPlayerButtton = new JButton(new ImageIcon(".\\menuIcons\\multiplayerButtonIcon.png"));
    private JButton easyLevelButtton = new JButton(new ImageIcon(".\\menuIcons\\easyButtonIcon.png"));
    private JButton normalLevelButtton = new JButton(new ImageIcon(".\\menuIcons\\normalButtonIcon.png"));
    private JButton hardLevelButtton = new JButton(new ImageIcon(".\\menuIcons\\hardButtonIcon.png"));
    private JButton backButton = new JButton(new ImageIcon(".\\menuIcons\\backButtonIcon.png"));
    private JButton serverButton = new JButton(new ImageIcon(".\\menuIcons\\serverButtonIcon.png"));
    private JButton clientButton = new JButton(new ImageIcon(".\\menuIcons\\clientButtonIcon.png"));

    //Background label.
    private JLabel backgroundLabel = new JLabel(new ImageIcon(".\\menuIcons\\menuBackground.png"));

    //String of game mode and level.
    public static boolean multiplayer = false;
    public static String gameLevel;
    public static boolean server = false;

    //Soundplayer of menu.
    public SoundPlayer soundPlayer;

    /**
     * Create a new Menu with given title.
     * @param title title of menu frame
     */
    public Menu(String title){
        super(title);

        soundPlayer =  new SoundPlayer();

        setResizable(false);
        setSize(GAME_WIDTH, GAME_HEIGHT);
        setLayout(null);
        setLocationRelativeTo(null); // put frame at center of screen
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);


        singlePlayerButton.setBounds(440, 255, 300, 80);
        multiPlayerButtton.setBounds(440, 365, 300, 80);
        easyLevelButtton.setBounds(440, 215, 300, 80);
        normalLevelButtton.setBounds(440, 320, 300, 80);
        hardLevelButtton.setBounds(440, 430, 300, 80);
        backButton.setBounds(1210, 20, 40, 40);
        backgroundLabel.setBounds(0, 0, 1280, 720);
        serverButton.setBounds(440, 255, 300, 80);
        clientButton.setBounds(440, 365, 300, 80);

        singlePlayerButton.setContentAreaFilled(false);
        multiPlayerButtton.setContentAreaFilled(false);
        easyLevelButtton.setContentAreaFilled(false);
        normalLevelButtton.setContentAreaFilled(false);
        hardLevelButtton.setContentAreaFilled(false);
        backButton.setContentAreaFilled(false);
        serverButton.setContentAreaFilled(false);
        clientButton.setContentAreaFilled(false);

        singlePlayerButton.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        multiPlayerButtton.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        easyLevelButtton.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        normalLevelButtton.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        hardLevelButtton.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        backButton.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        serverButton.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        clientButton.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        easyLevelButtton.setVisible(false);
        normalLevelButtton.setVisible(false);
        hardLevelButtton.setVisible(false);
        backButton.setVisible(false);
        serverButton.setVisible(false);
        clientButton.setVisible(false);

        add(singlePlayerButton);
        add(multiPlayerButtton);
        add(easyLevelButtton);
        add(normalLevelButtton);
        add(hardLevelButtton);
        add(backButton);
        add(serverButton);
        add(clientButton);
        add(backgroundLabel);


        //ActionListener of buttons.
        singlePlayerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                singlePlayerButton.setVisible(false);
                multiPlayerButtton.setVisible(false);
                easyLevelButtton.setVisible(true);
                normalLevelButtton.setVisible(true);
                hardLevelButtton.setVisible(true);
                backButton.setVisible(true);

                multiplayer = false;
                soundPlayer.menuClick();
            }
        });

        multiPlayerButtton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                singlePlayerButton.setVisible(false);
                multiPlayerButtton.setVisible(false);
                easyLevelButtton.setVisible(true);
                normalLevelButtton.setVisible(true);
                hardLevelButtton.setVisible(true);
                backButton.setVisible(true);

                multiplayer = true;
                soundPlayer.menuClick();
            }
        });

        easyLevelButtton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(!multiplayer) {
                    playGame();
                }else{
                    easyLevelButtton.setVisible(false);
                    normalLevelButtton.setVisible(false);
                    hardLevelButtton.setVisible(false);
                    serverButton.setVisible(true);
                    clientButton.setVisible(true);
                }
                soundPlayer.menuClick();
                gameLevel = "Easy";
            }
        });

        normalLevelButtton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!multiplayer) {
                    playGame();
                }else{
                    easyLevelButtton.setVisible(false);
                    normalLevelButtton.setVisible(false);
                    hardLevelButtton.setVisible(false);
                    serverButton.setVisible(true);
                    clientButton.setVisible(true);
                }
                soundPlayer.menuClick();
                gameLevel = "Normal";
            }
        });

        hardLevelButtton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!multiplayer) {
                    playGame();
                }else{
                    easyLevelButtton.setVisible(false);
                    normalLevelButtton.setVisible(false);
                    hardLevelButtton.setVisible(false);
                    serverButton.setVisible(true);
                    clientButton.setVisible(true);
                }
                soundPlayer.menuClick();
                gameLevel = "Hard";
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(easyLevelButtton.isVisible()) {
                    singlePlayerButton.setVisible(true);
                    multiPlayerButtton.setVisible(true);
                    easyLevelButtton.setVisible(false);
                    normalLevelButtton.setVisible(false);
                    hardLevelButtton.setVisible(false);
                    backButton.setVisible(false);
                }else if(serverButton.isVisible()){
                    easyLevelButtton.setVisible(true);
                    normalLevelButtton.setVisible(true);
                    hardLevelButtton.setVisible(true);
                    serverButton.setVisible(false);
                    clientButton.setVisible(false);
                }
            }
        });

        serverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                server = true;
                playGame();
            }
        });

        clientButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                server = false;
                playGame();
            }
        });

        //MouseListener(Entered) of buttons.
        singlePlayerButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                singlePlayerButton.setIcon(new ImageIcon(".\\menuIcons\\singlePlayerButtonEnteredIcon.png"));
                soundPlayer.menuEntered();
            }
        });

        multiPlayerButtton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                multiPlayerButtton.setIcon(new ImageIcon(".\\menuIcons\\multiplayerButtonEnteredIcon.png"));
                soundPlayer.menuEntered();
            }
        });

        easyLevelButtton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                easyLevelButtton.setIcon(new ImageIcon(".\\menuIcons\\easyButtonEnteredIcon.png"));
                soundPlayer.menuEntered();
            }
        });

        normalLevelButtton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                normalLevelButtton.setIcon(new ImageIcon(".\\menuIcons\\normalButtonEnteredIcon.png"));
                soundPlayer.menuEntered();
            }
        });

        hardLevelButtton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                hardLevelButtton.setIcon(new ImageIcon(".\\menuIcons\\hardButtonEnteredIcon.png"));
                soundPlayer.menuEntered();
            }
        });

        backButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                backButton.setIcon(new ImageIcon(".\\menuIcons\\backButtonEnteredIcon.png"));
                soundPlayer.menuEntered();
            }
        });

        serverButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                serverButton.setIcon(new ImageIcon(".\\menuIcons\\serverButtonEnteredIcon.png"));
                soundPlayer.menuEntered();
            }
        });

        clientButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                clientButton.setIcon(new ImageIcon(".\\menuIcons\\clientButtonEnteredIcon.png"));
                soundPlayer.menuEntered();
            }
        });

        //MouseListener(Exited) of buttons.
        singlePlayerButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                singlePlayerButton.setIcon(new ImageIcon(".\\menuIcons\\singlePlayerButtonIcon.png"));
            }
        });

        multiPlayerButtton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                multiPlayerButtton.setIcon(new ImageIcon(".\\menuIcons\\multiplayerButtonIcon.png"));
            }
        });

        easyLevelButtton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                easyLevelButtton.setIcon(new ImageIcon(".\\menuIcons\\easyButtonIcon.png"));
            }
        });

        normalLevelButtton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                normalLevelButtton.setIcon(new ImageIcon(".\\menuIcons\\normalButtonIcon.png"));
            }
        });

        hardLevelButtton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                hardLevelButtton.setIcon(new ImageIcon(".\\menuIcons\\hardButtonIcon.png"));
            }
        });

        backButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                backButton.setIcon(new ImageIcon(".\\menuIcons\\backButtonIcon.png"));
            }
        });

        serverButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                serverButton.setIcon(new ImageIcon(".\\menuIcons\\serverButtonIcon.png"));
            }
        });

        clientButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                clientButton.setIcon(new ImageIcon(".\\menuIcons\\clientButtonIcon.png"));
            }
        });
    }

    public static void playGame(){
        // Initialize the global thread-pool
        ThreadPool.init();

        // Show the game menu ...

        // After the player clicks 'PLAY' ...
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {


			/*	try {
					System.out.println(InetAddress.getLocalHost());
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}  */
                GameState state = new GameState();
                GameFrame frame = new GameFrame("server",state,multiplayer,server);
                frame.setLocationRelativeTo(null); // put frame at center of screen
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
                frame.initBufferStrategy();
                // Create and execute the game-loop
                GameLoop game = new GameLoop(frame);
                game.init(state);
                ThreadPool.execute(game);
                // and the game starts ...
            }
        });
    }
}
