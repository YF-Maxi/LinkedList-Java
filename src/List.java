import java.io.*;
import java.util.*;

public class List<T extends Comparable<T>>
{
    Node<T> head;
    Node<T> tail;
    int listLength = 0;
    char[] bannedCharacters = { ',', '.', '(', ')', '"', ';', ':' };

    public List() // Constructor
    {
        head = null;
        tail = null;
    }

    public void AddAtTail(T input) 
    {
        // Convert to lower case to not confuse the sorting algorith at the comparison step.
        if (input instanceof String) 
        {
            input = (T)((String) input).toLowerCase();
        }

        // If the list is empty, create a new head and give it the input value.
        if (head == null)
        {
            head = new Node<T>(input);
            tail = head;
            listLength++;
        } 
        else // If the list isn't epty, add the value to a new node at the end of the list.
        {
            tail.next = new Node<T>(input);
            tail = tail.next;
            listLength++;
        }
    }

    public void AddFromTextFile(String filename) throws IOException 
    {
        // Takes a filepath and opens the file with the FileReader() function
        BufferedReader inputFile = new BufferedReader(new FileReader("C:\\Users\\"+ GetComputerUsername() +"\\Desktop\\" + filename));
        String line;

        while ((line = inputFile.readLine()) != null) // Get one line at a time from the file
        {
            Scanner stream = new Scanner(line); // Create a Scanner to read words from the line
            while (stream.hasNext()) 
            {
                String word = stream.next();
                word = RemoveBannedCharacters(word); // Remove any unwanted symbols from the word
                word = word.toLowerCase(); // Convert the word to lowercase
                
                if (!word.isEmpty()) // Add the word to the list if it is not empty
                {
                    AddAtTail((T) word); // Assuming the list holds strings, casting to T
                }
            }
            stream.close();
        }

        inputFile.close();
    }

    private String RemoveBannedCharacters(String word) 
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

    public int CountLength()
    {
        int count = 0;
        Node<T> current = head;

        while (current != null) // Move along the lsit until the end is reached and count while doing it
        {
            count++;
            current = current.next;
        }

        return count;
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

        Node<T> mergedHead = null; // The head of the merged list
        Node<T> mergedTail = null; // The tail of the merged lsit

        // Determine the initial mergedHead
        if (firstHalf.GetValue().compareTo(secondHalf.GetValue()) <= 0) 
        {
            mergedHead = firstHalf;
            firstHalf = firstHalf.next;
        } 
        else 
        {
            mergedHead = secondHalf;
            secondHalf = secondHalf.next;
        }

        mergedTail = mergedHead; // Start the mergedTail at mergedHead

        // Merge the other nodes (rebuild "chain" of nodes)
        while (firstHalf != null && secondHalf != null) 
        {
            if (firstHalf.GetValue().compareTo(secondHalf.GetValue()) <= 0) 
            {
                mergedTail.next = firstHalf; // Add the smaller value node to the merged list
                firstHalf = firstHalf.next; // Move to the next node in firstHalf
            } 
            else 
            {
                mergedTail.next = secondHalf; // Add the samller value node to the merged list
                secondHalf = secondHalf.next; // Move to the next node in secondHalf
            }
            mergedTail = mergedTail.next; // Advance the tail pointer
        }

        // Reconnect any remaining nodes
        if (firstHalf != null) 
        {
            mergedTail.next = firstHalf;
        } 
        else if (secondHalf != null) 
        {
            mergedTail.next = secondHalf;
        }

        return mergedHead;
    }

    private Node<T> StartMergeSort(Node<T> headInput)
    {
        if (headInput == null || headInput.next == null) // Safety check
        {
            return headInput;
        }

        Node<T> secondHalf = SplitListInHalf(headInput);

        headInput = StartMergeSort(headInput);  // Sort the first half
        secondHalf = StartMergeSort(secondHalf);  // Sort the second half

        return Merge(headInput, secondHalf);
    }

    public void WriteToFile(String fileName, int format) throws IOException
    {
        String spacing = ", ";
        if (format == 0) { spacing = ""; }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\Users\\"+ GetComputerUsername() +"\\Desktop\\" + fileName))) 
        {
            Node<T> current = head;
            while (current != null) 
            {
                //Convert vlaue of type T to a String using
                writer.write(String.valueOf(current.GetValue()) + spacing);
                if (format == 0) { writer.newLine(); }
                current = current.next;
            }
        }
    }

    private String GetComputerUsername()
    {
        return System.getProperty("user.name");
    }
}