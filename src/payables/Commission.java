package payables;

public interface Commission {
    double getTauxCommission();
    double getVentesBrutes();
    double getMontantCommission(double ventesBrutes);
}
