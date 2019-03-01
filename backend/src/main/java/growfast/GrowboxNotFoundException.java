package growfast;

class GrowboxNotFoundException extends RuntimeException
{
    GrowboxNotFoundException(String id)
    {
        super("Growbox with the id " + id + " not found");
    }
}
