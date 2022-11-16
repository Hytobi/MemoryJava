import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class VueMemory extends JFrame implements ActionListener{
    public static final ImageIcon QUITTER =
                new ImageIcon("bin/images/quitter.gif");
    public static final ImageIcon RECOMMENCER =
                new ImageIcon("bin/images/recommencer.gif");
    public static final ImageIcon OPTION =
                new ImageIcon("bin/images/options.gif");
    public static final int FLASH=5;
    private int time;

    private Carte[] mesCartes;
    private Carte carte1;
    private Carte carte2;
    private boolean lesMemes;
    private Timer timer;
    private JPanel p;
    private JLabel nbTentativeLabel;
    private JLabel nbTrouveLabel;

    private final ModeleMemory model;
    private Action quitterAction;
    private Action recommencerAction;
    //private Action  optionAction;

    public VueMemory(ModeleMemory model){
        super("Memory");
        this.model = model;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        initCarte();
		initTimer();
		initActions();
		initMenuBar();
		initToolBar();
		initGameInfos();

        pack();
        Dimension dimEcran = getToolkit().getScreenSize();
        setLocation((dimEcran.width-getWidth())/2, (dimEcran.height-getHeight())/2);
        setVisible(true);
    }
    public void initCarte(){
        JPanel p = new JPanel();
        p.setLayout(new GridLayout(ModeleMemory.LIGNE,ModeleMemory.COLONNE));
        //color ?
        getContentPane().add(p,BorderLayout.CENTER);

        mesCartes = new Carte[ModeleMemory.NB_CARTES];
        for (int i=0; i<ModeleMemory.NB_CARTES;i++){
            mesCartes[i]=new Carte(i,model.getVal(i));
            mesCartes[i].addActionListener(this);
            p.add(mesCartes[i]);
        }
        
    }

    private void initTimer() {
		timer = new Timer(FLASH, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionTimer(e);
			}
		});
		timer.setInitialDelay(1500);
		time = 0;
	}
	private void initActions() {
		quitterAction = new AbstractAction("Exit", QUITTER) {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		};
		recommencerAction = new AbstractAction("Restart", RECOMMENCER) {

			public void actionPerformed(ActionEvent e) {
				VueMemory.this.restart();
			}
		};
		/**
        aboutAction = new AbstractAction("About...", ABOUT_ICON) {
	
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, ABOUT_TEXT);
			}
		}; */
	}


    public void initMenuBar(){
        JMenuBar menuBar = new JMenuBar();
		JMenu menuGame = new JMenu("Game");
		JMenu menuHelp = new JMenu("Help");
		menuGame.setForeground(Color.WHITE);
		menuHelp.setForeground(Color.WHITE);
		menuGame.setBorderPainted(false);
		menuHelp.setBorderPainted(false);
		JMenuItem menuItemRestart = new JMenuItem(recommencerAction);
		JMenuItem menuItemExit = new JMenuItem(quitterAction);
		//JMenuItem menuItemAbout = new JMenuItem(aboutAction);
		menuItemRestart.setForeground(Color.WHITE);
		menuItemExit.setForeground(Color.WHITE);
		//menuItemAbout.setForeground(Color.WHITE);
		menuItemRestart.setBorderPainted(false);
		menuItemExit.setBorderPainted(false);
		//menuItemAbout.setBorderPainted(false);
		menuItemRestart.setBackground(Color.BLUE);
		menuItemExit.setBackground(Color.BLUE);
		//menuItemAbout.setBackground(COLOR2);
		menuGame.add(menuItemRestart);
		menuGame.add(menuItemExit);
		//menuHelp.add(menuItemAbout);
		menuBar.add(menuGame);
		menuBar.add(menuHelp);
		menuBar.setBackground(Color.BLUE);
		menuBar.setBorderPainted(false);
		setJMenuBar(menuBar);
	}

    public void initToolBar(){
        JToolBar toolBar = new JToolBar();
        JButton quitterBtn = new JButton(quitterAction);
        JButton recommencerBtn = new JButton(recommencerAction);

        quitterBtn.setBorderPainted(false);
        quitterBtn.setFocusPainted(false);
        quitterBtn.setContentAreaFilled(false);
        quitterBtn.setForeground(Color.WHITE);

        recommencerBtn.setBorderPainted(false);
        recommencerBtn.setFocusPainted(false);
        recommencerBtn.setContentAreaFilled(false);
        recommencerBtn.setForeground(Color.WHITE);

        toolBar.add(quitterBtn);
        toolBar.addSeparator();
        toolBar.add(recommencerBtn);

        toolBar.setBackground(new Color(38,34,98));
        toolBar.setFloatable(false);
        toolBar.setRollover(true);
        toolBar.setBorderPainted(false);
        getContentPane().add(toolBar, BorderLayout.NORTH);
    }
    /**Initialise les ingo de la parti */
    public void initGameInfos(){
        JPanel gameInfos = new JPanel();
        nbTentativeLabel = new JLabel();
        nbTrouveLabel = new JLabel();
        nbTentativeLabel.setForeground(Color.WHITE);
        nbTrouveLabel.setForeground(Color.WHITE);
        gameInfos.add(nbTentativeLabel);
        gameInfos.add(nbTrouveLabel);
        updateGameInfos();
        gameInfos.setBackground(Color.BLUE);
        getContentPane().add(gameInfos, BorderLayout.SOUTH);

    }
    /**Met a jour les info de la partie */
    public void updateGameInfos(){
        int tentative = model.getTentative();
        if (tentative<2) {
            nbTentativeLabel.setText(tentative + " tentative");
        } else {
            nbTentativeLabel.setText(tentative + " tentatives");
        }
        int carteTrouve = model.getTrouve()*2;
        if (carteTrouve<2) {
            nbTrouveLabel.setText(carteTrouve + " carte trouvée");
        } else {
            nbTrouveLabel.setText(carteTrouve + " cartes trouvées");
        }

    }
    /**desactive le click de toute les cartes */
    public void desactiverTouteLesCartes(){
        for (int i=0;i<ModeleMemory.NB_CARTES;i++){
            if ((!mesCartes[i].getEstTrouve()) && (mesCartes[i]!=carte1) && mesCartes[i]!=carte2){
                mesCartes[i].desacClick(false);
            }
        }
    }
    /**Active le click de toute les cartes */
    public void activerTouteLesCartes(){
        for (int i=0;i<ModeleMemory.NB_CARTES;i++){
            if (!mesCartes[i].getEstTrouve()){
                mesCartes[i].activeClick();
            }
        }
    }
    /**appelé quand carte est cliqué et lance le timer */
    public void actionPerformed(ActionEvent e){
        int coordClick = ((Carte) e.getSource()).getCoord();
        mesCartes[coordClick].desacClick(true);
        if (carte1 == null){
            carte1 = mesCartes[coordClick];
        } else {
            carte2 = mesCartes[coordClick];
            lesMemes = model.imgPareil(carte1.getCoord(), carte2.getCoord());
            desactiverTouteLesCartes();
            timer.start();
        }
    }
    /**gere les actions */
    public void actionTimer(ActionEvent e){
        if (lesMemes){
            if (time<FLASH){
                carte1.setVisible(time%2 !=0);
                carte2.setVisible(time%2 !=0);
                time++;
            } else {
                carte1.bienTrouve();
                carte1.removeActionListener(this);
                carte2.bienTrouve();
                carte2.removeActionListener(this);
                model.getTrouve();
                timerEnd();
                if (model.finie()){
                    JOptionPane.showMessageDialog(null, "Vous avez gagné");
                    restart();
                }
            }
        } else {
            carte1.activeClick();
            carte2.activeClick();
            timerEnd();
        }
    } 
    /** fin de la methode actionTimer pour eviter la duplication de code */
    public void timerEnd(){
        carte1.setVisible(true);
        carte2.setVisible(true);
        carte1=null;
        carte2=null;
        model.incTentative();
        updateGameInfos();
        activerTouteLesCartes();
        time=0;
        timer.stop();
    }

    /**Relance une partie */
    public void restart(){
        timer.stop();
        time=0;
        carte1=null;
        carte2=null;
        model.reinit();
        updateGameInfos();
        p.removeAll();;
        for (int i=0; i<ModeleMemory.NB_CARTES;i++){
            mesCartes[i] = new Carte(i, model.getVal(i));
            mesCartes[i].addActionListener(this);
            p.add(mesCartes[i]);
        }
        revalidate();
        repaint();
    }
}