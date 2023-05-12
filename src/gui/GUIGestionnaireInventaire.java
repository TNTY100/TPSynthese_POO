package gui;

import exceptions.ExceptionEcheanceInsuffisante;
import exceptions.ExceptionPayableExisteDeja;
import exceptions.ExceptionPayableIntrouvable;
import inventaire.GestionnaireInventaire;
import payables.*;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class GUIGestionnaireInventaire extends JFrame
{
    private GestionnaireInventaire gestionnaireInventaire;
    private DefaultListModel modeleListePayables;
    private JList listePayables;
    private int idSuivant;

    public GUIGestionnaireInventaire(GestionnaireInventaire gestionnaireInventaire) {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.gestionnaireInventaire = gestionnaireInventaire;
        idSuivant = 100;
        creerEtAfficherGUI();
    }

    private void creerEtAfficherGUI() {
        add(creerTitrePanneau(), BorderLayout.NORTH);
        add(creerPanneauContenu(), BorderLayout.CENTER);

        setSize(1200, 300);
        setVisible(true);
    }

    private JPanel creerPanneauContenu() {
        JPanel contentPane = new JPanel();
        contentPane.setBorder(bordure());
        contentPane.setLayout(new BorderLayout());

        contentPane.add(creerActionsPayables(), BorderLayout.NORTH);
        contentPane.add(creerListePayables(), BorderLayout.CENTER);

        return contentPane;
    }

    private JPanel creerTitrePanneau() {
        JPanel titlePane = new JPanel();
        titlePane.setLayout(new BoxLayout(titlePane, BoxLayout.Y_AXIS));
        titlePane.setBorder(bordure());

        JLabel title = new JLabel("Gestionnaire de l'inventaire", SwingConstants.CENTER);
        title.setFont(new Font("Verdana", Font.PLAIN, 20));
        title.setBorder(titreBordure());
        titlePane.add(title);
        titlePane.add(new JSeparator());

        return titlePane;
    }

    private JScrollPane creerListePayables() {
        modeleListePayables = new DefaultListModel<>();
        for (Payable payable : gestionnaireInventaire.getTableauPayables())
            modeleListePayables.addElement(payable.toStringAffichage());

        listePayables = new JList(modeleListePayables);
        listePayables.setFont(new Font("Courier New", Font.PLAIN, 12));
        listePayables.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane listScroller = new JScrollPane(listePayables);

        return listScroller;
    }

    private JPanel creerActionsPayables() {
        JPanel payableButtons = new JPanel();
        payableButtons.setLayout(new BoxLayout(payableButtons, BoxLayout.X_AXIS));
        payableButtons.add(creerBoutonAjout());
        payableButtons.add(creerBoutonRetrait());
        payableButtons.add(creerBoutonEdition());
        payableButtons.add(creerBoutonEffacage());
        payableButtons.add(creerFacture());
        payableButtons.add(creerEmployeSalarie());
        payableButtons.add(creerEmployeHoraire());
        payableButtons.add(creerEmployeSalarieAvecCommission());
        payableButtons.add(creerEmployeHoraireAvecCommission());
        return payableButtons;
    }

    private JButton creerBoutonAjout() {
        JButton button = creerBouton("icons/icons8-add-new-24.png");
        button.addActionListener(event -> {
            try {
                Payable payable = gestionnaireInventaire.getPayable(extraitID((String) listePayables.getSelectedValue()));
                payable.augmenterEcheance(1);
                majModeleListe();
            }
            catch (ExceptionPayableIntrouvable exception) {
                  afficherDialogueErreurSelection();
            }
            afficherTableau(); // Pas demendé
        });

        return button;
    }

    private JButton creerBoutonRetrait() {
        JButton button = creerBouton("icons/icons8-minus-sign-24.png");
        button.addActionListener(event -> {
            try {
                Payable payable = gestionnaireInventaire.getPayable(extraitID((String) listePayables.getSelectedValue()));
                payable.diminuerEcheance(1);
                majModeleListe();
            }
            catch (ExceptionPayableIntrouvable exception) {
                afficherDialogueErreurSelection();
            }
            catch (ExceptionEcheanceInsuffisante exception) {
                afficherDialogueErreur(exception.getMessage());
            }
            afficherTableau();
        });

        return button;
    }

    private JButton creerBoutonEdition() {
        JButton button = creerBouton("icons/icons8-edit-row-24.png");
        button.addActionListener(event -> {
            try {
                Payable payable = gestionnaireInventaire.getPayable(extraitID((String) listePayables.getSelectedValue()));
                GUIPayableDialogue dialog = new GUIPayableDialogue(this, payable);
                dialog.setVisible(true);
                majModeleListe();
            }
            catch (ExceptionPayableIntrouvable exception) {
                afficherDialogueErreurSelection();
            }
            afficherTableau();
        });

        return button;
    }

    private JButton creerBoutonEffacage() {
        JButton button = creerBouton("icons/icons8-erase-24.png");
        button.addActionListener(event -> {
            try {
                Payable payable = gestionnaireInventaire.getPayable(extraitID((String) listePayables.getSelectedValue()));
                gestionnaireInventaire.retirerPayable(payable.getID());
                majModeleListe();
            }
            catch (ExceptionPayableIntrouvable exception) {
                afficherDialogueErreurSelection();
            }
            afficherTableau();
        });

        return button;
    }

    private JButton creerFacture() {
        Facture nouvelle = new Facture(idSuivant, "PARTNUMBER", "PARTDESCRIPTION",
                0,0.0,"MÉMO");
        return creerBoutonPayable(nouvelle, "icons/FCT.png");
    }

    private JButton creerEmployeSalarie() {
        Employe nouveau = new EmployeSalarie(idSuivant++, "NOM","NAS",
                0.0, "MÉMO");
        return creerBoutonPayable(nouveau, "icons/EST.png");
    }

    private JButton creerEmployeHoraire() {
        Employe nouveau = new EmployeHoraire(idSuivant++, "NOM", "NAS",
                0.0, 0.0, "MÉMO");
        return creerBoutonPayable(nouveau, "icons/EHT.png");
    }

    private JButton creerEmployeSalarieAvecCommission() {
        Employe nouveau = new EmployeSalarieAvecCommission(idSuivant++, "NOM", "NAS",
                0.0,0.1,0.0,"MÉMO");
        return creerBoutonPayable(nouveau, "icons/EPT.png");
    }

    private JButton creerEmployeHoraireAvecCommission() {
        Employe nouveau = new EmployeHoraireAvecCommission(idSuivant++, "NOM", "NAS",
                0.0, 0.0, 0.1, 0.0, "MÉMO");
        return creerBoutonPayable(nouveau, "icons/ECT.png");
    }

    private JButton creerBoutonPayable(Payable nouveau, String icone) {
        JButton button = creerBouton(icone);
        button.addActionListener(event -> {
            try {
                gestionnaireInventaire.ajouterPayable(nouveau);
                modeleListePayables.insertElementAt(nouveau.toStringAffichage(),0);
            }
            catch (ExceptionPayableExisteDeja exception) {
                afficherDialogueErreur(exception.getMessage());
            }
            afficherTableau(); // Pas nécessaire pour les étudiants
        });
        return button;
    }

    private JButton creerBouton(String icone) {
        JButton bouton = new JButton(new ImageIcon(icone));
        bouton.setBorder(BorderFactory.createEmptyBorder(5, 5, 5 , 5));
        return bouton;
    }

    private void afficherDialogueErreurSelection() {
        afficherDialogueErreur("SVP choisir un payable");
    }

    private void afficherDialogueErreur(String message) {
        GUIErreurDialogue dialog = new GUIErreurDialogue(this, message);
        dialog.setVisible(true);
    }

    private Border bordure() {
        return BorderFactory.createEmptyBorder(5, 10, 10, 10);
    }

    private Border titreBordure() {
        return BorderFactory.createEmptyBorder(5, 0, 10, 10);
    }

    private int extraitID(String payableString) {
        if (payableString == null)
            return -1;
        payableString = payableString.replace("]", "");
        payableString = payableString.replace("[", "");
        payableString = payableString.trim().replaceAll("\\s+", " ");
        String[] parties = payableString.split(" ");
        return Integer.parseInt(parties[1]);
    }

    private void afficherTableau() {
        System.out.println("\n=> TEST Récupérer le tableau de payables après un événement");
        Payable[] payables = gestionnaireInventaire.getTableauPayables();
        for (Payable payable : payables)
                System.out.println(payable);
    }

    private void majModeleListe() {
        //listePayables.updateUI(); serait suffisant si on utilisait DefaultListModel<Payable>
        Payable[] payables = gestionnaireInventaire.getTableauPayables();
        modeleListePayables.clear();
        for (Payable payable : payables)
            modeleListePayables.addElement(payable.toStringAffichage());
    }
}
