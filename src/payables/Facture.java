package payables;

public class Facture extends Payable {
	private final String numeroPiece;
	private String descriptionPiece;
	private int quantite;
	private double prixParItem;

	public Facture(int id, String numero, String description, int nombre, double prix, String memo) {
		super(id, memo);
		categorie = Categorie.Facture;
		numeroPiece = numero;
		descriptionPiece = description;
		setQuantite(nombre);
		setPrixParItem(prix);
	}

	public String getNumeroPiece() {
		return numeroPiece;
	}

	public String getDescriptionPiece() {
		return descriptionPiece;
	}

	public void setDescriptionPiece(String description) {
		descriptionPiece = description;
	}

	public int getQuantite() {
		return quantite;
	}

	public void setQuantite(int nombre) {
		if (nombre >= 0)
			quantite = nombre;
		else
			throw new IllegalArgumentException("La quantité doit être >= 0");
	}

	public double getPrixParItem() {
		return prixParItem;
	}

	public void setPrixParItem(double prix) {
		if ( prix >= 0.0 )
			prixParItem = prix;
		else
			throw new IllegalArgumentException("Le prix doit être >= 0");
	}

	@Override
	public double getMontantPaiement() {
		return getQuantite() * getPrixParItem();
	}

	@Override
	public String toString() {
		return String.format("%s: %n%s: %s (%s) %n%s: %d %n%s: %,.2f", 
				getCategorieString(), "numéro de la pièce", getNumeroPiece(), getDescriptionPiece(),
				"quantité", getQuantite(), "prix par item", getPrixParItem());
	}

	public String toStringAffichage() {
		return super.toStringAffichage() +
				 " Code [" + this.getNumeroPiece()
				+ "] Description [" + this.getDescriptionPiece()
				+ "] Quantité [" + this.getQuantite()
				+ "] Prix [" + this.getPrixParItem() + "]";
	}

	public String toStringSauvegarde() {
		String info = String.format("ID [%3d] Numéro [%15s] Description [%25s] Nombre [%3d] Prix [%10.2f] Mémo [%15s] Catégorie [%20s]",
				this.getID(), this.numeroPiece, this.getDescriptionPiece(), this.getQuantite(), this.getPrixParItem(), this.getMemo(), this.getCategorieString());
		return info;
	}
}
