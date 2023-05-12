package payables;

public class EmployeSalarieAvecCommission extends EmployeSalarie implements Commission {
    private double tauxCommission;
    private double ventesBrutes;

    public EmployeSalarieAvecCommission(int id, String nom, String nas, double salaire,
                                        double tauxCommission, double ventesBrutes, String memo) {
        super(id, nom, nas, salaire, memo);
        setTauxCommission(tauxCommission);
        setVentesBrutes(ventesBrutes);
    }

    public double getTauxCommission() {
        return tauxCommission;
    }

    public void setTauxCommission(double taux) {
        if (taux > 0.0 && taux < 1.0)
            tauxCommission = taux;
        else
            throw new IllegalArgumentException("Le taux de commission doit être > 0.0 et < 1.0");
    }

    public double getVentesBrutes() {
        return ventesBrutes;
    }

    public void setVentesBrutes(double ventes) {
        if (ventes >= 0.0)
            ventesBrutes = ventes;
        else
            throw new IllegalArgumentException("Le montant des ventes brutes doit être >= 0.0" );
    }

    @Override
    public double getMontantCommission(double ventesBrutes) {
        return getTauxCommission() * getVentesBrutes();
    }

    @Override
    public double getMontantPaiement() {
        return super.getMontantPaiement() + getMontantCommission(ventesBrutes);
    }

    @Override
    public String toString() {
        return String.format("%s %s; %s: %,.2f",
                getCategorieString(), super.toString(),
                "taux de commission", getTauxCommission(),
                "ventes brutes", getVentesBrutes());
    }

    public String toStringAffichage() {
        String info = super.toStringAffichage();
        info += " Commission [" + this.getTauxCommission() + "] Ventes [" + this.getVentesBrutes() + "]";
        return info;
    }

    public String toStringSauvegarde() {
        String info = String.format("ID [%3d] Nom complet [%20s] NAS [%9s] Salaire [%6.2f] Taux Commission [%4.2f] Ventes [%10.2f] Mémo [%15s] Catégorie [%20s]",
                this.getID(), this.getNomComplet(), this.getNumeroAssuranceSociale(),
                this.getSalaireHebdomadaire(), this.getTauxCommission(), this.getVentesBrutes(),
                this.getMemo(), this.getCategorieString());
        return info;
    }
}
