import java.io.IOException;

class Program {
    public static void main(String[] args) throws IOException // IOException needed to be able to read from the file for some reason
    {
        int testRuns = 100;
        double totalDuration = 0;
        String outputFileName = "sorted_output.txt";

        for (int i = 0; i < testRuns; i++) {
            List<String> list = new List<>();
            list.AddFromTextFile("text.txt");

            // Time the sorting algorithm
            long startTime = System.nanoTime();
            list.MergeSort();
            long endTime = System.nanoTime();

            double duration = (endTime - startTime) / 1_000_000_000.0; // Calculate duration
            totalDuration += duration;

            list.WriteToFile(outputFileName, 1);
            System.out.println(list.GetLength() + " sorted words written to file " + outputFileName + " on the desktop. Operation duration: " + duration + " seconds.");
        }

        double averageTime = totalDuration / testRuns;
        System.out.println("\nSorted " + testRuns + " times. Average time: " + averageTime + " seconds ("+ averageTime * 1000 + " milliseconds).");
    }
}