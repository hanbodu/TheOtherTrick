import java.io.*;

import org.junit.Test;

import static org.junit.Assert.*;


/**
 * Created by Nadia Brauner on 10/12/2015.
 * Updated on 12/01/2020
 */

public class Graphe_tests {

    private final static String CHEMIN = "./src" ;

    @Test
    public void degre_maison() throws Exception {
        final InputStream input = new FileInputStream(CHEMIN + "/maison.txt");
        final Graphe g = Graphe.read(input);

        assertEquals("Test de degre(1) sur le fichier maison.txt.\n ", 3, g.degre(1));
    }

    @Test
    public void degre() throws Exception {
        final Graphe g = new Graphe(3);
        String s = "la fonction degre() ne fonctionne pas correctement";
        g.ajouteArete(0, 1);
        assertEquals(s, 1, g.degre(0));
        assertEquals(s, 1, g.degre(1));
        assertEquals(s, 0, g.degre(2));
        g.ajouteArete(0, 2);
        assertEquals(s, 2, g.degre(0));
        assertEquals(s, 1, g.degre(1));
        assertEquals(s, 1, g.degre(2));
        g.ajouteArete(1, 2);
        assertEquals(s, 2, g.degre(0));
        assertEquals(s, 2, g.degre(1));
        assertEquals(s, 2, g.degre(2));
    }

    @Test
    public void maxDegre_1_sommet() throws Exception {
        final Graphe g = new Graphe(1);
        assertEquals("Test de la fonction maxDegre() sur un sommet isol谷.\n ", 0, g.maxDegre());
    }


    @Test
    public void maxDegre_maison() throws Exception {
        final InputStream input = new FileInputStream(CHEMIN + "/maison.txt");
        final Graphe g = Graphe.read(input);

        assertEquals("Test de la fonction maxDegre() sur le fichier maison.txt.\n ", 4, g.maxDegre());
    }

    @Test
    public void maxDegre_3_isoles() throws Exception {
        final Graphe g = new Graphe(3);

        assertEquals("Test de la fonction maxDegre() sur trois sommets isol谷s.\n ", 0, g.maxDegre());
    }

    @Test
    public void maxDegre_connexe() throws Exception {
        final Graphe g = new Graphe(3);
        g.ajouteArete(1, 2);
        g.ajouteArete(2, 0);

        assertEquals("Test de la fonction maxDegre() sur un graphe connexe.\n ", 2, g.maxDegre());
    }

    @Test
    public void maxDegre_non_connexe() throws Exception {
        final Graphe g = new Graphe(3);
        g.ajouteArete(1, 2);

        assertEquals("Test de la fonction maxDegre() sur un graphe non connexe.\n ", 1, g.maxDegre());
    }

    @Test
    public void nbAretes_maison() throws Exception {
        final InputStream input = new FileInputStream(CHEMIN + "/maison.txt");
        final Graphe g = Graphe.read(input);

        assertEquals("Test de la fonction nbAretes() sur le fichier maison.txt.\n ", 7, g.nbAretes());
    }

    @Test
    public void nbAretes_1_arete() throws Exception {
        final Graphe g = new Graphe(3);
        g.ajouteArete(1, 2);

        assertEquals(1, g.nbAretes());
    }

    @Test
    public void complementaire_maison() throws Exception {
        final InputStream input = new FileInputStream(CHEMIN + "/maison.txt");
        final Graphe g = Graphe.read(input);

        Graphe gbar = g.complementaire();
        assertEquals("le nombre d'ar那tes du compl谷mentaire est faux", compterAretes(gbar), 3);

        String s = "une ar那te est fausse";
        assertTrue(s, gbar.sontVoisins(0, 3));
        assertTrue(s, gbar.sontVoisins(0, 4));
        assertTrue(s, gbar.sontVoisins(1, 4));
        assertTrue(s, !gbar.sontVoisins(0, 1));
        assertTrue(s, !gbar.sontVoisins(0, 2));
        assertTrue(s, !gbar.sontVoisins(1, 2));
        assertTrue(s, !gbar.sontVoisins(1, 3));
        assertTrue(s, !gbar.sontVoisins(2, 3));
        assertTrue(s, !gbar.sontVoisins(2, 4));
        assertTrue(s, !gbar.sontVoisins(3, 4));
    }

    @Test
    public void complementaire_3_sommets() throws Exception {
        Graphe g = new Graphe(3);
        Graphe gbar = g.complementaire();
        assertEquals("le nombre d'ar那tes du compl谷mentaire est faux", compterAretes(gbar), 3);
        String s = "une ar那te est fausse";
        assertTrue(s, gbar.sontVoisins(0, 1));
        assertTrue(s, gbar.sontVoisins(0, 2));
        assertTrue(s, gbar.sontVoisins(2, 1));
    }

    @Test
    public void complementaire_triangle() throws Exception {
        Graphe g = new Graphe(3);
        g.ajouteArete(0, 1);
        g.ajouteArete(2, 1);
        g.ajouteArete(0, 2);

        Graphe gbar = g.complementaire();
        assertEquals("le nombre d'ar那tes du compl谷mentaire est faux", compterAretes(gbar), 0);
        String s = "une ar那te est fausse";
        assertTrue(s, !gbar.sontVoisins(0, 1));
        assertTrue(s, !gbar.sontVoisins(0, 2));
        assertTrue(s, !gbar.sontVoisins(2, 1));
    }

    @Test
    public void complet_3() throws Exception {
        Graphe g = Graphe.graphe_complet(3);
        assertEquals("le nombre d'ar那tes du compl谷mentaire est faux", compterAretes(g), 3);
        String s = "une ar那te est fausse";
        assertTrue(s, g.sontVoisins(0, 1));
        assertTrue(s, g.sontVoisins(0, 2));
        assertTrue(s, g.sontVoisins(2, 1));
    }

    @Test
    public void complet_8() throws Exception {
        Graphe g = Graphe.graphe_complet(8);

        assertEquals("le nombre d'ar那tes du scomplet 角 8 sommets est faux", compterAretes(g), 28);
        String s = "une ar那te est fausse";

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (i != j) assertTrue(s, g.sontVoisins(i, j));
                else assertTrue(s, !g.sontVoisins(i, j));
            }
        }
    }


    public static int compterAretes(Graphe g) {
        int s = 0;
        for (int i = 0; i < g.getN(); i++) {
            s += g.voisins(i).size();
        }
        return (s / 2);
    }

}
