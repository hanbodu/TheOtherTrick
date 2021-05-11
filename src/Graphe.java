import java.io.*;
import java.util.*;

/**
 * Classe qui impl¨¦mente des graphes non orient¨¦s ¨¦ventuellement avec boucles et ar¨ºtes multiples <BR>
 * impl¨¦mentation sous forme de ArrayList avec dans chaque case la listes des voisins
 *
 * @author Nadia Brauner
 *
 */
public class Graphe {

    private final int n; // nombre de sommets. Ne peut ¨ºtre assign¨¦ qu'une fois

    private ArrayList<LinkedList<Integer>> adjacence; // les ar¨ºtes en listes de voisins

    /**
     * Initialise l'instance de graphe avec {@code n} sommets
     *
     * @param n le nombre de sommets du graphe. {@code n} doit ¨ºtre strictement positif.
     * @throws IllegalArgumentException si {@code n} est n¨¦gatif ou nul
     */
    public Graphe(int n) {
        if (n < 1)
            throw new IllegalArgumentException("le nombre de sommets doit etre strictement positif");
        this.n = n;
        this.adjacence = new ArrayList<LinkedList<Integer>>(n);
        for (int i = 0; i < n; i++)
            adjacence.add(new LinkedList<Integer>());
        // ajoute la LinkedList a la liste adjacence
    }

    /**
     * Construit un graphe ¨¤ partir d'un flux de donn¨¦es (InputStream)
     *
     * @param input donn¨¦es du graphe ¨¤ construire
     * @return un graphe
     * @throws GrapheReaderException en cas d'erreur de lecture de {@code input}
     */
    public static Graphe read(final InputStream input) throws GrapheReaderException {
        return new Reader().read(input);
    }

    /**
     * Ajoute l'arete {@code i}{@code j} au graphe.<BR>
     * On peut rajouter une ar¨ºte d¨¦j¨¤ existante ou faire des boucles !
     *
     * @param i sommet extremit¨¦ de l'ar¨ºte
     * @param j sommet extremit¨¦ de l'ar¨ºte
     * @throws IllegalArgumentException si {@code i} ou {@code j} n'est pas un sommet valide
     */
    public void ajouteArete(int i, int j) {
        // graphe non orient¨¦. Si on a l'ar¨ºte ij alors, on a aussi ji
        verifieSommet(i);
        verifieSommet(j);
        this.adjacence.get(i).add(j);
        this.adjacence.get(j).add(i);
    }

    /**
     * renvoie le nombre de sommets du graphe
     *
     * @return le nombre de sommets (toujours positif)
     */
    public int getN() {
        return this.n;
    }

    /**
     * v¨¦rifie que le sommet {@code v} est un sommet valide du graphe
     *
     * @param v sommet du graphe ¨¤ verifier
     * @throws IllegalArgumentException si le sommet n'est pas valide
     */
    private void verifieSommet(int v) {
        if (v >= getN() || v < 0)
            throw new IllegalArgumentException(String.format("Le sommet %d n'est pas valide", v));
    }


    /**
     * Renvoie la liste des voisins du sommet {@code v}.<BR>
     * Cette liste ne peut pas etre modifi¨¦e
     *
     * @param v sommet du graphe
     * @return la liste des voisins du sommet {@code v}
     * @throws IllegalArgumentException si le sommet n'est pas valide
     */
    public List<Integer> voisins(int v) {
        verifieSommet(v);

        return Collections.unmodifiableList(adjacence.get(v));
    }
    
     /**
     * Renvoie vrai si et seulement si {@code u} et {@code v} sont voisins
     *
     * @param u un sommet du graphe
     * @param v un sommet du graphe
     * @return true si {@code u} et {@code v} sont voisins, false sinon
     */
    public boolean sontVoisins(int u, int v) {
        return (adjacence.get(u).contains(v) || adjacence.get(v).contains(u));
    }
    
    /**
     * Calcule le degr¨¦ du sommet {@code v}
     *
     * @param v sommet du graphe
     * @return le degr¨¦ de {@code v}
     * @throws IllegalArgumentException si {@code v} n'est pas un sommet valide
     */
    public int degre(int v) throws IllegalArgumentException {
    	verifieSommet(v);
        return this.adjacence.get(v).size() ;
    }

    /**
     * Calcule le  degr¨¦ maximum des sommets du graphe
     *
     * @return le degr¨¦ maximum des sommets du graphe
     */
    public int maxDegre() {
        int maxDegree = 0;
        for(int i=0; i<this.n; i++) {
        	if(this.adjacence.get(i).size() > maxDegree) {
        		maxDegree = this.adjacence.get(i).size();
        	}
        }
        
        return maxDegree;
    }


    /**
     * Calcule le nombre d'ar¨ºtes ¨¤ partir de la somme des degr¨¦s
     *
     * @return la somme des degr¨¦s divis¨¦e par 2
     */
    public int nbAretes() {
        int nbAretes = 0;
        for(int i=0; i<this.n; i++) {
        	nbAretes += this.adjacence.get(i).size();
        }
        nbAretes /= 2;
        return nbAretes;
    }
    
    /**
     * calcule la matrice d'adjacence du graphe
     *
     * @return la matrice d'adjacence du graphe
     */
    public int[][] matriceAdj() {
    	int n = this.getN();
        int[][] matriceAdj = new int[n][n];
        for(int i=0; i<n; i++) {
        	for(int j=0; j<n; j++) {
        		if(sontVoisins(i, j)) {
        			matriceAdj[i][j] = 1;
        		}else {
        			matriceAdj[i][j] = 0;
        		}
        	}
        }
        return matriceAdj;
    }

    @Override
    public String toString() {
        // TODO
        return "un graphe";
    }

    /**
     * Calcule le graphe compl¨¦mentaire
     *
     * @return le graphe compl¨¦mentaire
     */
    public Graphe complementaire(){
    	Graphe C = new Graphe(this.n);
    	for(int i=0; i<C.n; i++) {
    		for(int j=0; j<C.n; j++) {
    			if(!sontVoisins(i,j) && i!=j) {
    				C.adjacence.get(i).add(j);
    			}
    		}
    	}
        return C;
    }
    
    /**
     * Construit le graphe complet ¨¤ {@code k} sommets
     *
     * @param k le nombre de sommets du graphe complet
     */
    public static Graphe graphe_complet(int k){
        Graphe complet = new Graphe(k);
        for(int i=0; i<k; i++) {
        	for(int j=0; j<k; j++) {
        		if(i != j) {
        			complet.adjacence.get(i).add(j);
        		}
        	}
        }
        return complet ;
    }
    
    /**
     * calcule la matrice d'incidence du graphe
     *
     * @return la matrice d'incidence du graphe
     */
    public int[][] matriceInc() {
        int nbAretes = this.nbAretes();
        ArrayList<LinkedList<Integer>> aretes = new ArrayList<LinkedList<Integer>>(nbAretes); // Arraylist qui stocke les aretes
        for (int i = 0; i < nbAretes; i++)
            aretes.add(new LinkedList<Integer>());
        // numeroter les aretes
        int numAretes = 0;
        for(int i=0; i<this.n; i++) {
        	for(int j=i+1; j<this.n; j++) {
        		if(this.sontVoisins(i, j)) {
        			aretes.get(numAretes).add(i);
        			aretes.get(numAretes).add(j);
        			numAretes ++;
        		}
        	}
        }
        
        // creer la matrice Inc
        int[][] matriceInc = new int[this.n][nbAretes];
        for(int i=0; i<this.n; i++) {
        	for(int j=0; j<nbAretes; j++) {
        		if(aretes.get(j).contains(i)) {
        			matriceInc[i][j] = 1;
        		}else {
        			matriceInc[i][j] = 0;
        		}
        	}
        }
        return matriceInc;
    }

     public static final class Reader {

        public Graphe read(final InputStream input) throws GrapheReaderException {
            try{
                final BufferedReader reader = new BufferedReader(new InputStreamReader(input)) ;

                final Graphe g;
                String line;

                String[] items = readNextLine(reader);

                if ((items == null) || (items.length < 2) || !"p".equals(items[0]))
                    throw new GrapheReaderException("il manque la ligne p",null);

                g = new Graphe(toInt(items[2]));

                while (items != null) {
                    items = readNextLine(reader);
                    if ((items != null) && (items.length > 0) && "e".equals(items[0])) {
                        g.ajouteArete(toInt(items[1]), toInt(items[2]));
                    }
                }
                return g;
            }
            catch (IOException e) {
               throw new GrapheReaderException(e.getMessage(),e) ;
            }
        }

        private String[] readNextLine(BufferedReader reader) throws IOException {
            String line = reader.readLine();
            while ((line != null) && line.startsWith("c")) {
                line = reader.readLine();
            }
            return (line == null) ? null : line.split("\\s+");
        }


        private static int toInt(String s) throws GrapheReaderException {
            try {
                return Integer.parseInt(s);
            } catch (NumberFormatException e) {
                throw new GrapheReaderException(String.format("'%1$s' n'est pas un entier", s),e) ;
            }
        }
    }

    public static class GrapheReaderException extends Exception {

        public GrapheReaderException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
