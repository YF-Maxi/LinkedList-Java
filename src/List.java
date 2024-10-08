import java.io.*;
import java.util.*;

public class List<T extends Comparable<T>>
{
    Node<T> head;
    Node<T> tail;
    int listLength = 0;
    char[] bannedCharacters = { ',', '.', '(', ')', '"', ';', ':' };

    public List() 
    {
        head = null;
        tail = null;
    }

    public void AddAtTail(T input) 
    {
        if (input instanceof String) 
        {
            input = (T)((String) input).toLowerCase();
        }

        if (head == null) 
        {
            head = new Node<T>(input);
            tail = head;
        } 
        else 
        {
            tail.next = new Node<T>(input);
            tail = tail.next;
        }

        listLength++;
    }

    public void AddTextFromFile(String fileName) throws IOException 
    {
        BufferedReader inputFile = new BufferedReader(new FileReader(fileName));
        String line;

        while ((line = inputFile.readLine()) != null) // Get one line at a time from the file
        {
            Scanner stream = new Scanner(line); // Create a Scanner to read words from the line
            while (stream.hasNext()) 
            {
                String word = stream.next();
                word = removeBannedCharacters(word); // Remove any unwanted symbols from the word
                word = word.toLowerCase(); // Convert the word to lowercase
                
                if (!word.isEmpty()) // Add the word to the list if it is not empty
                {
                    AddAtTail((T) word); // Assuming the list holds strings, casting to T
                }
            }
        }

        inputFile.close();
    }

    private String removeBannedCharacters(String word) 
    {
        // Remove banned characters from the front and back of the word
        word = word.trim(); // Remove leading and trailing whitespace
        for (char banned : bannedCharacters) 
        {
            while (!word.isEmpty() && word.charAt(word.length() - 1) == banned) // Remove from the end
            { 
                word = word.substring(0, word.length() - 1);
            }
            while (!word.isEmpty() && word.charAt(0) == banned) // Remove from the front
            { 
                word = word.substring(1);
            }
        }
        return word;
    }

    public void PrintAll() 
    {
        Node<T> current = head;
        while (current != null) 
        {
            System.out.println(current.GetValue());
            current = current.next;
        }
    }

    public void MergeSort() 
    {
        head = StartMergeSort(head);
    }

    public int GetLength() 
    {
        return listLength;
    }

    public void Clear() 
    {
        head = null;
        tail = null;
        listLength = 0;
    }

    public boolean Contains(T input) 
    {
        Node<T> current = head;
        while (current != null) 
        {
            if (current.GetValue().equals(input)) 
            {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    private Node<T> SplitListInHalf(Node<T> headInput) 
    {
        if (headInput == null || headInput.next == null) 
        {
            return null;
        }

        Node<T> slowPointer = headInput;
        Node<T> fastPointer = headInput.next;

        while (fastPointer != null && fastPointer.next != null) 
        {
            fastPointer = fastPointer.next.next;
            slowPointer = slowPointer.next;
        }

        Node<T> secondHalf = slowPointer.next;
        slowPointer.next = null;
        return secondHalf;
    }

    private Node<T> Merge(Node<T> firstHalf, Node<T> secondHalf) 
    {
        if (firstHalf == null) 
        {
            return secondHalf;
        }
        if (secondHalf == null) 
        {
            return firstHalf;
        }

        Node<T> mergedList;

        if (firstHalf.GetValue().compareTo(secondHalf.GetValue()) <= 0) 
        {
            mergedList = firstHalf;
            mergedList.next = Merge(firstHalf.next, secondHalf);
        } 
        else 
        {
            mergedList = secondHalf;
            mergedList.next = Merge(firstHalf, secondHalf.next);
        }

        return mergedList;
    }

    private Node<T> StartMergeSort(Node<T> headInput) 
    {
        if (headInput == null || headInput.next == null) 
        {
            return headInput;
        }

        Node<T> secondHalf = SplitListInHalf(headInput);

        headInput = StartMergeSort(headInput);  // Sort the first half
        secondHalf = StartMergeSort(secondHalf);  // Sort the second half

        return Merge(headInput, secondHalf);
    }

    public void WriteToFile() throws IOException
    {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\Users\\Max\\Desktop\\sorted_output.txt"))) 
        {
            Node<T> current = head;
            while (current != null) 
            {
                // Convert T to a String using String.valueOf()
                writer.write(String.valueOf(current.GetValue()));
                writer.newLine();
                current = current.next;
            }
        }
    }
}