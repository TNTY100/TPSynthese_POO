package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUIErreurDialogue extends JDialog {
    public GUIErreurDialogue(JFrame frame, String message) {
        super(frame, "Erreur", ModalityType.DOCUMENT_MODAL);

        JButton okButton = new JButton("Ok");
        okButton.addActionListener(e -> setVisible(false));

        Container dialogContainer = getContentPane();
        dialogContainer.setLayout(new BorderLayout());

        JPanel labelPanel = new JPanel(new FlowLayout());
        labelPanel.add(new JLabel(message));
        dialogContainer.add(labelPanel, BorderLayout.CENTER);

        JPanel okButtonPanel = new JPanel(new FlowLayout());
        okButtonPanel.add(okButton);
        dialogContainer.add(okButtonPanel, BorderLayout.SOUTH);

        pack();
    }
}
