package payables;

public class EmployeSalarie extends Employe {
	private double salaireHebdomadaire;

	public EmployeSalarie(int id, String nom, String nas,
			double salaire, String memo) {
		super(id, nom, nas, memo);
		categorie = Categorie.EmployeSalarie;
		setSalaireHebdomadaire(salaire);
	}

	public double getSalaireHebdomadaire() {
		return salaireHebdomadaire;
	}

	public void setSalaireHebdomadaire(double salaire) {
		if (salaire >= 0.0)
			salaireHebdomadaire = salaire;
		else
			throw new IllegalArgumentException("Le salaire hebdomadaire doit être >= 0.0" );
	}

	@Override
	public double getMontantPaiement() {
		return getSalaireHebdomadaire();
	}

	@Override
	public String toString() {
		return String.format("%s: %s%n%s: %,.2f",
				getCategorieString(), super.toString(), "salaire hebdomadaire", getSalaireHebdomadaire());
	}

	public String toStringAffichage() {
			String info = super.toStringAffichage();
			info += " Salaire [" + this.getSalaireHebdomadaire()  + "]";
			return info;
	}

	public String toStringSauvegarde() {
		String info = String.format("ID [%3d] Nom complet [%20s] NAS [%9s] Salaire [%6.2f] Mémo [%15s] Catégorie [%20s]",
				this.getID(), this.getNomComplet(), this.getNumeroAssuranceSociale(),
				this.getSalaireHebdomadaire(), this.getMemo(), this.getCategorieString());
		return info;
	}
}
