package inventaire;

import exceptions.*;
import payables.*;

public class GestionnaireInventaire {
    private BaseDonnees baseDonnees;

    public GestionnaireInventaire() {
        baseDonnees = new BaseDonnees();
    }

    public void ajouterPayable(Payable p) throws ExceptionPayableExisteDeja {
        baseDonnees.inserer(p);
    }

    public void retirerPayable(int ID) throws ExceptionPayableIntrouvable {
        baseDonnees.enlever(ID);
    }

    public void augmenterEcheancePayable(int ID, int echeance) throws ExceptionPayableIntrouvable {
        Payable payable = getPayable(ID);
        payable.augmenterEcheance(echeance);
    }

    public void diminuerEcheancePayable(int ID, int echeance) throws ExceptionPayableIntrouvable, ExceptionEcheanceInsuffisante {
        Payable payable = getPayable(ID);
        payable.diminuerEcheance(echeance);
    }

    public Payable getPayable(int ID) throws ExceptionPayableIntrouvable {
        Payable payable = baseDonnees.trouverParID(ID);
        if (payable != null)
            return payable;
        else
            throw new ExceptionPayableIntrouvable(ID);
    }

    public Payable[] getTableauPayables() {
        return baseDonnees.getTableauPayables();
    }
}
