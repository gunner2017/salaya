import java.util.*;
import java.io.*;

public class Test1 {
    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new FileReader("data.txt"));
        String str;
        List<String> list = new ArrayList<String>();
        while((str = br.readLine()) != null){
        list.add(str);
    }

    String[] doc = list.toArray(new String[list.size()]);

    TF_IDF test = new TF_IDF(doc);

    String[] wordvt = test.getWordVector();
    double[][] realtfidf = test.getTF_IDFMatrix();

//print word vector
//System.out.println(Arrays.toString(wordvt));

//print tf-idf matrix
//System.out.println(Arrays.deepToString(realtfidf));

//write to a file
    PrintWriter fw = new PrintWriter("output.csv");

        for (int i = 0; i < wordvt.length; i++) {
            fw.printf("%s,", wordvt[i]);
        }
        fw.println();

    for (double[] r : realtfidf) {
    for (int i = 0; i < r.length; i++) {
        fw.printf("%5f,", r[i]);
    }
        fw.println();
    }

        fw.close();

    }
}