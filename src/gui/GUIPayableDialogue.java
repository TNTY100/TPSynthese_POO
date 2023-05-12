package gui;

import payables.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUIPayableDialogue extends JDialog {
    private Payable payable;
    private JFrame frame;
    private JTextField champPrixParItem, champQuantite,  champDescriptionPiece, champNumeroPiece,
            champNomComplet, champNumeroAssuranceSociale, champTauxCommission, champVentesBrutes,
            champSalaireHebdomadaire, champHeures, champTauxHoraire;

    public GUIPayableDialogue(JFrame frame, Payable payable) {
        super(frame, payable.getCategorieString(), ModalityType.DOCUMENT_MODAL);
        this.frame = frame;
        this.payable = payable;
        creerEtAfficherGUI(true);
    }

    private void creerEtAfficherGUI(boolean editable) {
        setLayout(new BorderLayout());
        JPanel contentPane = new JPanel();
        contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        contentPane.setLayout(new GridLayout(12, 2));

        contentPane.add(new JLabel("ID"));
        JTextField fieldID = new JTextField("" + payable.getID());
        fieldID.setEditable(false);
        fieldID.setBackground(contentPane.getBackground());
        contentPane.add(fieldID);

        contentPane.add(new JLabel("Mémo"));
        JTextField fieldMemo = new JTextField(payable.getMemo());
        fieldMemo.setEditable(editable);
        contentPane.add(fieldMemo);

        contentPane.add(new JLabel("Catégorie"));
        JTextField fieldCategorie = new JTextField(payable.getCategorieString());
        fieldCategorie.setEditable(false);
        contentPane.add(fieldCategorie);

        JTextField fieldEcheance = new JTextField("" + payable.getEcheanceJours());
        fieldEcheance.setEditable(editable);
        contentPane.add(new JLabel("Échéance"));
        contentPane.add(fieldEcheance);

        if (payable instanceof Facture) {
            champNumeroPiece = new JTextField("" + ((Facture) payable).getNumeroPiece());
            champNumeroPiece.setEditable(editable);
            contentPane.add(new JLabel("Numéro"));
            contentPane.add(champNumeroPiece);

            champDescriptionPiece = new JTextField("" + ((Facture) payable).getDescriptionPiece());
            champDescriptionPiece.setEditable(editable);
            contentPane.add(new JLabel("Description"));
            contentPane.add(champDescriptionPiece);

            champQuantite = new JTextField("" + ((Facture) payable).getQuantite());
            champQuantite .setEditable(editable);
            contentPane.add(new JLabel("Quantité"));
            contentPane.add(champQuantite );

            champPrixParItem = new JTextField("" + ((Facture) payable).getPrixParItem());
            champPrixParItem.setEditable(editable);
            contentPane.add(new JLabel("Prix unitaire"));
            contentPane.add(champPrixParItem);
        }
        else if (payable instanceof Employe employe) {
            champNomComplet = new JTextField("" + employe.getNomComplet());
            champNomComplet.setEditable(false);
            contentPane.add(new JLabel("Nom complet"));
            contentPane.add(champNomComplet);

            champNumeroAssuranceSociale = new JTextField("" + employe.getNumeroAssuranceSociale());
            champNumeroAssuranceSociale.setEditable(false);
            contentPane.add(new JLabel("NAS"));
            contentPane.add(champNumeroAssuranceSociale);
        }

        if (payable instanceof EmployeHoraire) {
            champTauxHoraire = new JTextField("" + ((EmployeHoraire) payable).getTauxHoraire());
            champTauxHoraire.setEditable(editable);
            contentPane.add(new JLabel("Taux horaire"));
            contentPane.add(champTauxHoraire);

            champHeures = new JTextField("" + ((EmployeHoraire) payable).getHeuresTravaillees());
            champHeures.setEditable(editable);
            contentPane.add(new JLabel("Heures travaillées"));
            contentPane.add(champHeures);
        }
        else if (payable instanceof EmployeSalarie) {
            champSalaireHebdomadaire = new JTextField("" + ((EmployeSalarie) payable).getSalaireHebdomadaire());
            champSalaireHebdomadaire.setEditable(true);
            contentPane.add(new JLabel("Salaire hebdomadaire"));
            contentPane.add(champSalaireHebdomadaire);
        }

        if (payable instanceof Commission employe) {
            champTauxCommission = new JTextField("" + employe.getTauxCommission());
            champTauxCommission.setEditable(editable);
            contentPane.add(new JLabel("Taux de commission"));
            contentPane.add(champTauxCommission);

            champVentesBrutes = new JTextField("" + employe.getVentesBrutes());
            champVentesBrutes.setEditable(editable);
            contentPane.add(new JLabel("Ventes brutes"));
            contentPane.add(champVentesBrutes);
        }

        JPanel buttons = new JPanel(new FlowLayout());
        JButton cancelButton = new JButton("Annuler");
        cancelButton.addActionListener(e -> setVisible(false));
        buttons.add(cancelButton);

        if (editable) {
            JButton okButton = new JButton("Enregistrer");
            okButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        payable.setMemo(fieldMemo.getText());
                        payable.setEcheanceJours(Integer.parseInt(fieldEcheance.getText()));
                        if (payable instanceof Facture facture) {
                            facture.setDescriptionPiece(champDescriptionPiece.getText());
                            facture.setQuantite(Integer.parseInt(champQuantite.getText()));
                            facture.setPrixParItem(Double.parseDouble(champPrixParItem.getText()));
                        }
                        else if (payable instanceof EmployeHoraireAvecCommission employe) {
                            employe.setTauxCommission(Double.parseDouble(champTauxCommission.getText()));
                            employe.setVentesBrutes(Double.parseDouble(champVentesBrutes.getText()));
                        }
                        else if (payable instanceof EmployeSalarieAvecCommission employe) {
                            employe.setSalaireHebdomadaire(Double.parseDouble(champSalaireHebdomadaire.getText()));
                            employe.setTauxCommission(Double.parseDouble(champTauxCommission.getText()));
                            employe.setVentesBrutes(Double.parseDouble(champVentesBrutes.getText()));
                        }
                        else if (payable instanceof EmployeHoraire employe) {
                            employe.setTauxHoraire(Double.parseDouble(champTauxHoraire.getText()));
                            employe.setHeuresTravaillees(Double.parseDouble(champHeures.getText()));
                        }
                        else if (payable instanceof EmployeSalarie employe) {
                            employe.setSalaireHebdomadaire(Double.parseDouble(champSalaireHebdomadaire.getText()));
                        }

                        setVisible(false);
                    } catch (NumberFormatException exception) {
                        GUIErreurDialogue guiErrorDialog = new GUIErreurDialogue(frame, "Mauvais format de données: " + exception.getMessage());
                        guiErrorDialog.setVisible(true);
                    }
                }
            });
            buttons.add(okButton);
        }
        add(contentPane, BorderLayout.CENTER);
        add(buttons, BorderLayout.SOUTH);
        pack();
    }

    private void ajouterChamp(String nom, String contenu) {
        ajouterChamp(nom, contenu, true);
    }

    private void ajouterChamp(String nom, String contenu, boolean editable) {
        JTextField champ = new JTextField(contenu);
        champ.setEditable(editable);
        frame.add(new JLabel(nom));
        frame.add(champ);
    }
}
