import java.util.Collections;
import java.util.ArrayList;
import java.util.List;

public class ModeleMemory{
    public static final int LIGNE = 4;
    public static final int COLONNE = 4;
    public static final int NB_CARTES = LIGNE*COLONNE;
    public static final int NB_IMAGES = NB_CARTES/2;

    private int[] tab;
    private int manche;
    private int trouve;
    private int tentative;

    /**Constructeur */
    public ModeleMemory(){
        tab = new int[NB_CARTES];
        manche = 0;
        trouve = 0;
        tentative = 0;
        for (int i=0;i<NB_IMAGES;i++){
            tab[i*2] = i;
            tab[i*2+1] = i;
        } 
        melanger();
    }

    /**Metthode qui donne la valeur dans la liste
     * @param coord: la coordonné ou on veut connaire la val
     * @return : la valeur a la position coord
     */
    public int getVal(int coord){
        return tab[coord];
    }
    /**Methode qui return le nombre de pair trouvé */
    public int getTrouve(){
        return trouve;
    }
    /**Methode qui return le numero de la manche */
    public int getManche(){
        return manche;
    }
    /**Methode qui return le nombre de pair pas encore trouvé */
    public int getTentative(){
        return tentative;
    }
    /**Methode qui met a jour le nb de tentative */
    public void incTentative(){
        tentative++;
    }
    /**Methode qui met a jour le nb de manche */
    public void incManche(){
        manche++;
    }
    /**Methode qui met a jour le nb de pair trouvé */
    public void incTrouve(){
        trouve++;
    }

    /**Methode qui melange le tableau */
    public void melanger(){
        int i;
        List<Integer> inter = new ArrayList<Integer>();
        for(i=0;i<tab.length;i++){
            inter.add(tab[i]);
        }
        Collections.shuffle(inter);
        for (i=0;i<tab.length;i++){
            tab[i] = inter.get(i);
        }
    }

    /**Methode qui regard si deux carte son pareil
     * @param coord1 : la coord de la premiere image
     * @param coord2 : La coord de la deuxieme image
     * @return : true si elles sont egales
     */
    public boolean imgPareil(int coord1, int coord2){
        if (tab[coord1]==tab[coord2]){
            trouve++;
            return true;
        }
        return false;
    }

    /**Methode qui regard si la partie est finie
     * @return : true si il n'y a pu de pair a trouver
     */
    public boolean finie(){
        return trouve == NB_IMAGES;
    }

    /**Methode qui reinitialise le modele de jeu */
    public void reinit(){
        trouve=0;
        manche=0;
        tentative=0;
        melanger();
    }
}