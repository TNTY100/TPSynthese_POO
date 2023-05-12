package exceptions;

public class ExceptionPayableIntrouvable extends Exception
{
    public ExceptionPayableIntrouvable(int ID) {

        super("Payable " + ID + " n'est pas dans la BD.");
    }
}
