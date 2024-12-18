import java.io.IOException;

class Program 
{
    public static void main(String[] args) throws IOException // IOException needed to be able to read from the file for some reason
    {         
        List<String> list = new List<>();
        list.AddFromTextFile("text.txt");

        long startTime = System.nanoTime();
        list.MergeSort();
        long endTime = System.nanoTime();
        double duration = (endTime - startTime) / 1_000_000_000.0;

        list.WriteToFile("sorted_output.txt", 1);

        System.out.println(list.GetLength() + " sorted words written to file on desktop");
        System.out.println("Sorting took " + duration + " seconds");
    }
}