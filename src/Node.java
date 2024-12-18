public class Node <T>
{
    private T value;

    public Node (T inputValue) 
    {
        value = inputValue;
        next = null;
    }

    public Node<T> next;

    public T GetValue()
    {
        return value;
    }

    public void SetValue(T inputValue)
    {
        value = inputValue;
    }
}