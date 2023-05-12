package payables;
import exceptions.ExceptionEcheanceInsuffisante;

public abstract class Payable {
	private final int ID;
	protected Categorie categorie;
	protected int echeanceJours;
	protected String memo;

	public Payable (int id, String memo) {
		this.ID = id;
		this.echeanceJours = 0;
		this.memo = memo;
	}

	public Payable (int id) {
		this.ID = id;
		this.echeanceJours = 0;
		this.memo = "";
	}

	public int getID() {
		return ID;
	}

	public abstract double getMontantPaiement();

	public int getEcheanceJours() {
		return echeanceJours;
	}

	public void setEcheanceJours(int echeanceJours) {
		this.echeanceJours = echeanceJours;
	}

	public void augmenterEcheance(int jours) {
		echeanceJours += jours;
	}

	public void diminuerEcheance(int jours) throws ExceptionEcheanceInsuffisante {
		if (jours > echeanceJours) {
			throw new ExceptionEcheanceInsuffisante(jours);
		}
		this.echeanceJours -= jours;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getCategorieString() {
		return categorie.toString();
	}

	public String toStringAffichage() {
		String info = String.format("ID [%3d] Catégorie [%25s] Mémo [%15s] Échéance [%3d] Paiement [%10.2f]",
				this.ID, this.getCategorieString(), this.getMemo(), this.getEcheanceJours(), this.getMontantPaiement());
		return info;
	}

	public abstract String toStringSauvegarde();
}
