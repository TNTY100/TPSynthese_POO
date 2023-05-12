package exceptions;

public class ExceptionEcheanceInsuffisante extends Exception
{
    public ExceptionEcheanceInsuffisante(int jours) {
        super("Enlever " + jours + " d'échéance nous amènera à une échéance négative.");
    }
}
