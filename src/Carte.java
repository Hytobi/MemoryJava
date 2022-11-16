import javax.swing.JButton;
import javax.swing.ImageIcon;

public class Carte extends JButton{
    public static final ImageIcon[] TABIMAGES = new ImageIcon[]{
        new ImageIcon("bin/images/Bird.gif"),
        new ImageIcon("bin/images/Bird2.gif"),
        new ImageIcon("bin/images/Cat.gif"),
        new ImageIcon("bin/images/Cat2.gif"),
        new ImageIcon("bin/images/Dog.gif"),
        new ImageIcon("bin/images/Dog2.gif"),
        new ImageIcon("bin/images/Rabbit.gif"),
        new ImageIcon("bin/images/Pig.gif")
    };
    public static final ImageIcon CACHE = new ImageIcon("bin/images/help.gif");
    public static final ImageIcon TROUVE = new ImageIcon("bin/images/Rien.gif");

    private int coord;
    private int imageId;
    private boolean estTrouve;

    public Carte(int coord, int imageId){
        super(CACHE);
        this.coord = coord;
        this.imageId = imageId;
    }

    public int getCoord(){
        return coord;
    }
    public int getId(){
        return imageId;
    }
    public boolean getEstTrouve(){
        return estTrouve;
    }

    public void desacClick(boolean isClicked){
        if (isClicked) setDisabledIcon(TABIMAGES[imageId]);
        else setDisabledIcon(CACHE);
        setEnabled(false);
    }
    public void activeClick(){
        setEnabled(true);
    }
    public void bienTrouve(){
        setDisabledIcon(TROUVE);
        estTrouve = true;
    }
}