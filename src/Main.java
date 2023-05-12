import java.io.*;
import java.util.regex.Pattern;
import java.util.regex.MatchResult;
import javax.swing.UIManager;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import exceptions.*;
import payables.*;
import inventaire.GestionnaireInventaire;
import gui.GUIGestionnaireInventaire;

public class Main {
    public static void lireInventaire(String nomFichier, GestionnaireInventaire gestionnaireInventaire) throws Exception {
        FileReader reader = new FileReader(nomFichier);
        try {
            BufferedReader bufferedReader = new BufferedReader(reader);
            String ligne;
            while ((ligne = bufferedReader.readLine()) != null) {
                //System.out.println(line);
                String[] m = Pattern.compile("\\[(.*?)\\]")
                        .matcher(ligne)
                        .results()
                        .map(MatchResult::group)
                        .toArray(String[]::new);
                for (int i = 0; i < m.length; i++) {
                    m[i] = m[i].substring(1, m[i].length() - 1);
                    m[i] = m[i].trim();
                }

                String cat = m[m.length - 1].trim();
                Categorie c = Categorie.valueOf(cat);
                Payable p = null;
                switch(c) {
                    case Facture:
                        p = new Facture(Integer.parseInt(m[0]), m[1], m[2],Integer.parseInt(m[3]),
                                Double.parseDouble(m[4].replaceAll(",", ".")), m[5]);
                        break;
                    case EmployeHoraire:
                        p = new EmployeHoraire(Integer.parseInt(m[0]), m[1], m[2],
                                Double.parseDouble(m[3].replaceAll(",", ".")),
                                Double.parseDouble(m[4].replaceAll(",", ".")), m[5]);
                        break;
                    case EmployeSalarie:
                        p = new EmployeSalarie(Integer.parseInt(m[0]), m[1], m[2],
                                Double.parseDouble(m[3].replaceAll(",", ".")), m[4]);
                        break;
                    case EmployeHoraireAvecCommission:
                        p = new EmployeHoraireAvecCommission(Integer.parseInt(m[0]), m[1], m[2],
                                Double.parseDouble(m[3].replaceAll(",", ".")),
                                Double.parseDouble(m[4].replaceAll(",", ".")),
                                Double.parseDouble(m[5].replaceAll(",", ".")),
                                Double.parseDouble(m[6].replaceAll(",", ".")), m[7]);
                        break;
                    case EmployeSalarieAvecCommission:
                        p = new EmployeSalarieAvecCommission(Integer.parseInt(m[0]), m[1], m[2],
                                Double.parseDouble(m[3].replaceAll(",", ".")),
                                Double.parseDouble(m[4].replaceAll(",", ".")),
                                Double.parseDouble(m[5].replaceAll(",", ".")), m[6]);
                        break;
                }
                gestionnaireInventaire.ajouterPayable(p);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            reader.close();
        }
    }

    public static void ecrireInventaire(String nomFichier, Payable[] payables) {
        try {
            FileWriter writer = new FileWriter(nomFichier, false);
            BufferedWriter bufferedWriter = new BufferedWriter(writer);
            for (Payable p : payables) {
                bufferedWriter.write(p.toStringSauvegarde());
                bufferedWriter.newLine();
            }
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {

        /* --- DÉMARRAGE DES TESTS --------------------------------------------------------------------------*/
        GestionnaireInventaire gestionnaireInventaire = new GestionnaireInventaire();

        System.out.println("\n=> TEST Création ou lecture de nouveaux payables");
        final boolean LECTURE = false;
        if (LECTURE)
            lireInventaire("payables.in", gestionnaireInventaire);
        else {
            gestionnaireInventaire.ajouterPayable(new EmployeSalarie(10, "Marie Renaud", "246864246", 50000, "Bonne employée"));
            gestionnaireInventaire.ajouterPayable(new EmployeHoraire(11, "Kevin Bouchard", "123321123", 25.50, 35, "Assidu"));
            gestionnaireInventaire.ajouterPayable(new EmployeSalarieAvecCommission(12, "Aline Brullemans", "123327832", 40000, 0.1, 15000, "Peu motivée"));
            gestionnaireInventaire.ajouterPayable(new EmployeHoraireAvecCommission(13, "Alan Walsh", "973813265", 15, 32.5,0.15, 40000, "Du potentiel"));
            gestionnaireInventaire.ajouterPayable(new Facture(14, "34X53", "Tournevis", 34, 23, "Gros vendeur"));
        }
        System.out.println("\n=> TEST Trouver un payable et afficher l'information sur ce payable");
        Payable payable = gestionnaireInventaire.getPayable(10);
        System.out.println(payable);

        System.out.println("\n=> TEST Création d'un payable avec un ID existant");
        try {
            gestionnaireInventaire.ajouterPayable(new EmployeHoraire(10,"Mario Bouchard","129271123",55000,40,"Déjà vu?"));
        } catch (ExceptionPayableExisteDeja e) {
            System.out.println(e.getMessage());
        }

        System.out.println("\n=> TEST Enlever un payable");
        gestionnaireInventaire.retirerPayable(10);

        System.out.println("\n=> TEST Enlever un payable non existant (catch exception)");
        try {
            gestionnaireInventaire.retirerPayable(10);
        } catch (ExceptionPayableIntrouvable e) {
            System.out.println(e.getMessage());
        }

        System.out.println("\n=> TEST Remettre un payable");
        gestionnaireInventaire.ajouterPayable(new EmployeSalarie(10,"Marie Renaud","246864246",50000, "Bonne employée" ));

        System.out.println("\n=> TEST Allonger deux fois le délai de paiement d'un payable et afficher les nouvelles informations");
        gestionnaireInventaire.augmenterEcheancePayable(11, 25);
        gestionnaireInventaire.augmenterEcheancePayable(11, 25);
        payable = gestionnaireInventaire.getPayable(11);
        System.out.println(payable + "\nÉchéance en jours: " + payable.getEcheanceJours());

        System.out.println("\n=> TEST Raccourcir le délai de paiement d'un payable et afficher les nouvelles informations");
        gestionnaireInventaire.diminuerEcheancePayable(11, 25);
        payable = gestionnaireInventaire.getPayable(11);
        System.out.println(payable + "\nÉchéance en jours: " + payable.getEcheanceJours());

        System.out.println("\n=> Raccourcir le délai de paiement d'un payable non existant (catch exception)");
        try {
            gestionnaireInventaire.diminuerEcheancePayable(9, 1);
        } catch (ExceptionPayableIntrouvable e) {
            System.out.println(e.getMessage());
        }

        System.out.println("\n=> TEST Trop raccourcir le délai de paiement d'un payable (catch exception)");
        try {
            gestionnaireInventaire.diminuerEcheancePayable(11, 100);
        } catch (ExceptionEcheanceInsuffisante e) {
            System.out.println(e.getMessage());
        }

        System.out.println("\n=> TEST Récupérer le tableau de payables");
        Payable[] payables = gestionnaireInventaire.getTableauPayables();
        for (Payable p : payables) {
            System.out.println(p.toStringAffichage());
            System.out.printf("%s %n%s: %,.2f%n%n", p, "paiement dû", p.getMontantPaiement());
        }
        System.out.println("\n=> TEST Écrire le tableau de payables dans un fichier pour relecture");
        ecrireInventaire("payables.out", payables);

        System.out.println("\n=> TEST Ce qu'il faut payer à tous les payables, " +
                "incluant un bonus de 10% sur le salaire de base des employés à commission");
        for (Payable p : payables) {
            System.out.print("ID " + p.getID());
            if (p instanceof EmployeSalarieAvecCommission employe) {
                System.out.print(". Salaire de base = " + employe.getSalaireHebdomadaire());
                employe.setSalaireHebdomadaire(1.10 * employe.getSalaireHebdomadaire());
                System.out.print(", nouveau salaire de base avec une augmentation de 10%: " + employe.getSalaireHebdomadaire());
            }
            System.out.println(". À payer: " + p.getMontantPaiement());
        }

        /* --- DÉMARRAGE de l'interface graphique (GUI)-------------------------------------------------------------*/
        UIManager.setLookAndFeel(new NimbusLookAndFeel());
        GUIGestionnaireInventaire GUIGestionnaireInventaire = new GUIGestionnaireInventaire(gestionnaireInventaire);
    }
}
