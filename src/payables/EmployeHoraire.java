package payables;

public class EmployeHoraire extends Employe {
	private double tauxHoraire;
	private double heuresTravaillees;

	public EmployeHoraire(int id, String nom, String nas,
						  double tauxHoraire, double heuresTravaillees, String memo) {
		super(id, nom, nas, memo);
		categorie = Categorie.EmployeHoraire;
		setTauxHoraire(tauxHoraire);
		setHeuresTravaillees(heuresTravaillees);
	}

	public double getTauxHoraire() {
		return tauxHoraire;
	}

	public void setTauxHoraire(double tauxHoraire) {
		if (tauxHoraire >= 0.0)
			this.tauxHoraire = tauxHoraire;
		else
			throw new IllegalArgumentException("Le salaire horaire doit être >= 0.0" );
	}

	public double getHeuresTravaillees() {
		return heuresTravaillees;
	}

	public void setHeuresTravaillees(double heuresTravaillees) {
		if ((heuresTravaillees >= 0.0) && (heuresTravaillees <= 168.0))
			this.heuresTravaillees = heuresTravaillees;
		else
			throw new IllegalArgumentException("Les heures travaillées doivent être >= 0.0 et <= 168.0" );
	}

	@Override
	public double getMontantPaiement() {
		if (getHeuresTravaillees() <= 40)
			return getTauxHoraire() * getHeuresTravaillees();
		else
			return 40 * getTauxHoraire() + (getHeuresTravaillees() - 40) * getTauxHoraire() * 1.5;
	}

	@Override
	public String toString() {
		return String.format( "%s: %s%n%s: %,.2f; %s: %,.2f",
			getCategorieString(), super.toString(), "taux horaire", getTauxHoraire(),
				"heures travaillées", getHeuresTravaillees() );
	}

	public String toStringAffichage() {
		String info = super.toStringAffichage();
		info += " Salaire [" + this.getTauxHoraire()
				+ "] Heures [" + this.getHeuresTravaillees()
				+ "]";
		return info;
	}

	public String toStringSauvegarde() {
		String info = String.format("ID [%3d] Nom complet [%20s] NAS [%9s] Taux Horaire [%4.2f] Heures travaillées [%4.2f] Mémo [%15s] Catégorie [%20s]",
				this.getID(), this.getNomComplet(), this.getNumeroAssuranceSociale(),
				this.getTauxHoraire(), this.getHeuresTravaillees(), this.getMemo(), this.getCategorieString());
		return info;
	}
}
