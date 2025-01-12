import java.io.IOException;

class Program 
{
    public static void main(String[] args) throws IOException // IOException needed to be able to read from the file for some reason
    {
        int testRuns = 1000;
        double totalDuration = 0;
        String outputFilename = "sorted_output.txt";
        List<String> list = new List<>();

        for (int i = 0; i < testRuns; i++) 
        {
            list = new List<>();
            list.AddFromTextFile("text.txt");

            // Time the sorting algorithm
            long startTime = System.nanoTime();
            list.MergeSort();
            long endTime = System.nanoTime();

            double duration = (endTime - startTime) / 1_000_000_000.0; // Calculate duration
            totalDuration += duration;

            System.out.println(list.GetLength() + " sorted words written to file " + outputFilename + " on the desktop. Operation duration: " + duration + " seconds.");
        }

        list.WriteToFile(outputFilename, 1);

        double averageTime = totalDuration / testRuns;
        System.out.println("\nSorted " + testRuns + " times. Average time: " + averageTime + " seconds ("+ averageTime * 1000 + " milliseconds).");
    }
}