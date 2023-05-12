package exceptions;

public class ExceptionPayableExisteDeja extends Exception
{
    public ExceptionPayableExisteDeja(int ID) {

        super("Payable " + ID + " est déjà présent dans la base de données.");
    }
}
