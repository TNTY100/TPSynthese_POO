package inventaire;

import exceptions.*;
import payables.*;

public class BaseDonnees {
    private static class NoeudPayable {
        public final Payable payable;
        private NoeudPayable suivant;

        public NoeudPayable(Payable payable, NoeudPayable suivant) {
            this.payable = payable;
            this.suivant = suivant;
        }

        public NoeudPayable getNoeudSuivant() {
            return suivant;
        }

        public void setNoeudSuivant(NoeudPayable s) {
            suivant = s;
        }
    }

    private NoeudPayable tete;
    private int nombrePayables;

    public BaseDonnees() {
        tete = null;
        nombrePayables = 0;
    }

    public void inserer(Payable p) throws ExceptionPayableExisteDeja {
        if (trouverParID(p.getID()) != null) {
            throw new ExceptionPayableExisteDeja(p.getID());
        }
        tete = new NoeudPayable(p, tete);
        nombrePayables++;
    }

    public Payable trouverParID(int ID) {
        NoeudPayable courant = tete;
        while (courant != null) {
            if (courant.payable.getID() == ID) {
                return courant.payable;
            }
            courant = courant.getNoeudSuivant();
        }
        return null;
    }

    public void enlever(int ID) throws ExceptionPayableIntrouvable {
        if (tete == null) {
            throw new ExceptionPayableIntrouvable(ID);
        }

        if (tete.payable.getID() == ID) {
            tete = tete.getNoeudSuivant();
            nombrePayables--;
            return;
        }

        NoeudPayable courant = tete;
        while (courant.getNoeudSuivant() != null) {
            if (courant.getNoeudSuivant().payable.getID() == ID) {
                courant.setNoeudSuivant(courant.getNoeudSuivant().getNoeudSuivant());
                nombrePayables--;
                return;
            }
            courant = courant.getNoeudSuivant();
        }

        throw new ExceptionPayableIntrouvable(ID);
    }

    public Payable[] getTableauPayables() {
        Payable[] payablesArray = new Payable[nombrePayables];
        int index = 0;
        NoeudPayable courant = tete;
        while (courant != null) {
            payablesArray[index] = courant.payable;
            courant = courant.getNoeudSuivant();
            index++;
        }
        return payablesArray;
    }
}
