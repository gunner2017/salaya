import java.util.*;
import java.io.*;

public class TF_IDF {
 
    int numOfWords;
    double[] idfVector;
    double[][] tfIdfMatrix;
    double[][] tfMatrix;
    String[] wordVector;
    int docLength[];
 
    public TF_IDF(String[] docs) throws Exception {

BufferedReader br = new BufferedReader(new FileReader("ThaiStopWords.txt"));
String line;
List<String> stopWord = new ArrayList<String>();

while((line = br.readLine()) != null){
stopWord.add(line);
}

        // STEP 1, scan all words and count the number of different words
        // mapWordToIdx maps word to its vector index
        HashMap<String, Integer> mapWordToIdx = new HashMap<>();
        int nextIdx = 0;
        for (String doc : docs) {
            for (String word : doc.split("\\|")) {
String key = word.trim();
                if (!mapWordToIdx.containsKey(key) && !stopWord.contains(key)) {
                    mapWordToIdx.put(key, nextIdx);
                    nextIdx++;
                }
            }
        }
 
        numOfWords = mapWordToIdx.size();
 
        // STEP 2, create word vector where wordVector[i] is the actual word
        wordVector = new String[numOfWords];
        for (String word : mapWordToIdx.keySet()) {
            int wordIdx = mapWordToIdx.get(word);
            wordVector[wordIdx] = word;
        }
 
        // STEP 3, create doc word vector where docCountVector[i] is number of
        // docs containing word of index i
        // and doc length vector
        int[] docCountVector = new int[numOfWords];
        docLength = new int[docs.length];
        // lastDocWordVector is auxilary vector keeping track of last doc index
        // containing the word
        int[] lastDocWordVector = new int[numOfWords];
        for (int wordIdx = 0; wordIdx < numOfWords; wordIdx++) {
            lastDocWordVector[wordIdx] = -1;
        }
        for (int docIdx = 0; docIdx < docs.length; docIdx++) {
            String doc = docs[docIdx];
            String[] words = doc.split("\\|");
            for (String word : words) {
String key = word.trim();
                docLength[docIdx] = words.length;
if (!stopWord.contains(key)) {
                int wordIdx = mapWordToIdx.get(key);
                if (lastDocWordVector[wordIdx] < docIdx) {
                    lastDocWordVector[wordIdx] = docIdx;
                    docCountVector[wordIdx]++;
                }
}
            }
        }
 
        // STEP 4, compute IDF vector based on docCountVector
        idfVector = new double[numOfWords];
        for (int wordIdx = 0; wordIdx < numOfWords; wordIdx++) {
            idfVector[wordIdx] = Math.log10(1 + (double) docs.length / (docCountVector[wordIdx]));
        }
 
        // STEP 5, compute term frequency matrix, tfMatrix[docIdx][wordIdx]
        tfMatrix = new double[docs.length][];
        for (int docIdx = 0; docIdx < docs.length; docIdx++) {
            tfMatrix[docIdx] = new double[numOfWords];
        }
        for (int docIdx = 0; docIdx < docs.length; docIdx++) {
            String doc = docs[docIdx];
            for (String word : doc.split("\\|")) {
String key = word.trim();
if (!stopWord.contains(key)) {
                int wordIdx = mapWordToIdx.get(key);
                tfMatrix[docIdx][wordIdx] = tfMatrix[docIdx][wordIdx] + 1;
}
            }
        }
        // normalize idfMatrix by deviding corresponding doc length
        for (int docIdx = 0; docIdx < docs.length; docIdx++) {
            for (int wordIdx = 0; wordIdx < numOfWords; wordIdx++) {
                tfMatrix[docIdx][wordIdx] = tfMatrix[docIdx][wordIdx] / docLength[docIdx];
            }
        }
 
        // STEP 6, compute final TF-IDF matrix
        // tfIdfMatrix[docIdx][wordIdx] = tfMatrix[docIdx][wordIdx] * idfVector[wordIdx]
        tfIdfMatrix = new double[docs.length][];
        for (int docIdx = 0; docIdx < docs.length; docIdx++) {
            tfIdfMatrix[docIdx] = new double[numOfWords];
        }
 
        for (int docIdx = 0; docIdx < docs.length; docIdx++) {
            for (int wordIdx = 0; wordIdx < numOfWords; wordIdx++) {
                tfIdfMatrix[docIdx][wordIdx] = tfMatrix[docIdx][wordIdx] * idfVector[wordIdx];
            }
        }
 
    }
 
    public double[][] getTF_IDFMatrix() {
        return tfIdfMatrix;
    }
 
    public String[] getWordVector() {
        return wordVector;
    }
 
} 
