package payables;

public abstract class Employe extends Payable {
	private final String nomComplet, numeroAssuranceSociale;

	public Employe(int id, String nom, String nas, String memo) {
		super(id, memo);
		this.nomComplet = nom;
		numeroAssuranceSociale = nas;
	}

	public String getNomComplet() {
		return nomComplet;
	}

	public String getNumeroAssuranceSociale() {
		return numeroAssuranceSociale;
	}

	@Override
	public String toString() {
		return String.format( "%s \nnuméro d'assurance sociale: %s",
				getNomComplet(), getNumeroAssuranceSociale());
	}

	public String toStringAffichage() {
		String info = super.toStringAffichage();
		info += String.format(" Nom complet [%20s] NAS [%9s]",
				this.getNomComplet(), this.getNumeroAssuranceSociale());
		return info;
	}

	public abstract String toStringSauvegarde();
}
