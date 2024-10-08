import java.io.IOException;

class Program 
{
    public static void main(String[] args) throws IOException // IOException needed to be able to read from the file for some reason
    {         
        List<String> list = new List<>();
        list.AddTextFromFile("C:\\Users\\Max\\Desktop\\text.txt");

        long startTime = System.nanoTime();
        list.MergeSort();  // Perform the sorting operation
        long endTime = System.nanoTime();
        double duration = (endTime - startTime) / 1_000_000_000.0;

        list.WriteToFile();
        System.out.println(list.GetLength() + " sorted words written to file: C:\\Users\\Max\\Desktop\\sorted_output.txt");

        System.out.println("Sorting took " + duration + " seconds");
    }
}