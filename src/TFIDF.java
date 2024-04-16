import java.util.*;

public class TFIDF {
    //Map a string to a Double(the Term Frequency score)
    public HT calculateTFTable(String[] inputArray) {
        HT frequencyTable = new HT();
        //Loop the input array
        for (String term : inputArray) {
            if (frequencyTable.contains(term)){
                //Increment count if found
                frequencyTable.add(term, frequencyTable.getDouble(term) + 1.0);
            } else {
                //Add to map if not already there
                frequencyTable.add(term, 1.0);
            }
        }
        //Go through hashmap and divide each value by the number of terms in the document
        for (Object term : frequencyTable.getKeySet()) {
            frequencyTable.add(term, frequencyTable.getDouble(term) /inputArray.length);
        }
        return frequencyTable;
    }

    //Map a String to a Double(the Inverse Document Frequency score)
    //Goal: assign an idf value for each string in the hashmap
    public HT calculateIDFTable(ArrayList<List<String>> corpus, String[] inputArray){
        //HashMap<String, Double> IDFMap = new HashMap<>();
        HT idfTable = new HT();
        for (String term : inputArray){
            double count = 0;
            //look for the term then if found, we increment, we don't care about how many times it's in each document, just if it is there.
            //eg. The total number of documents (divided) by the number of documents containing the term
            for (int i = 0; i < corpus.size() - 1; i++) { //loop through the corpus and check to see if the arraylist of terms contains the term.
                if(corpus.get(i).contains(term)){
                    count++; //increment our count for that term.
                }
            }
            //As per, https://en.wikipedia.org/wiki/Tf%E2%80%93idf, we add 1 to the numerator and denominator to avoid a divide by zero error
            double idf = Math.log((1 + corpus.size())/(1+ count));
            idfTable.add(term, idf);
        }
        return idfTable;
    }




    public HT calculateTFIDFTable(HT tfMap, HT idfMap){
        HT tfidfTable = new HT();
        for (Object key : tfMap.getKeySet()) {
            double value1 = tfMap.getDouble(key);
            double value2 = idfMap.getDouble(key);
            tfidfTable.add(key, (value1 * value2));
        }
        return tfidfTable;
    }

    public double combineThing(HashMap<String, Double> tfidfMap){
        double total = 0;
        for ( Double num : tfidfMap.values() ) {
            total = total + num;
        }
        return total/tfidfMap.size();
    }


    //Given a term, find the document that best fits it. Try looking through the
    public int getDoc(ArrayList<HT> corpus, String term){
        if (term.contains(" ")){
            int ind = term.indexOf(" ");
            term = term.substring(0, ind);
        }
        double tfidf = 0;
        int index = - 1;
        for (int i = 0; i < corpus.size(); i++) {
            if(corpus.get(i).contains(term)){
                //If the TFIDF value is bigger, we save the value and the index.
                if (tfidf < corpus.get(i).getDouble(term)){
                    tfidf = corpus.get(i).getDouble(term);
                    index = i;
                }
            }
        }
        return index;
    }


    //Assume we add the webpage to the corpus and produce a tfidf map in our main
    //Use find term to compare each doc and take the highest tfidf average value
    public int getWebpage(ArrayList<HT> tfidfCollection, HT tfidfMap) {
        int index = - 1;
        //Look through each doc in corpus
        //double average = 0;
        double average = 0;
        for (int i = 0; i < tfidfCollection.size(); i++) {

            //compare the value for each term
            ArrayList<Double> ls = new ArrayList<>();
            //Loop through the entire keyset
            for (Object term:tfidfMap.getKeySet()) {
                //If the tfidf map contains the term we're looking for, then we add the tfidf score for it to the list.
                //This will hopefully only account for the rare terms in the page we're looking up, this could be adjusted
                if(tfidfCollection.get(i).contains(term) && tfidfMap.getDouble(term) > 0.0){
                    double val = tfidfCollection.get(i).getDouble(term);
                    //This is to prevent a high denominator and low numerator just because of common terms we don't care about.
                    if (val > 0.01){
                        ls.add(val);
                    }
                }
            }
            //Add all the values in ls and divide by the size of ls to get the average
            double total = sum(ls)/ls.size();
            if (total > average){
                average = total;
                index = i;
            }
        }
        return index;
    }

    public static double sum(ArrayList<Double> lst){
        double total = 0.0;
        for (Double num:lst) {
            total = total + num;
        }
        return total;
    }




    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(System.in);
            WebPage page1 = new WebPage("https://en.wikipedia.org/wiki/Toyota_4Runner");
            WebPage page2 = new WebPage("https://en.wikipedia.org/wiki/Nissan_Skyline_GT-R");
            WebPage page3 = new WebPage("https://en.wikipedia.org/wiki/Nissan_GT-R");
            WebPage page4 = new WebPage("https://en.wikipedia.org/wiki/Mitsubishi_Lancer_Evolution");
            WebPage page5 = new WebPage("https://en.wikipedia.org/wiki/Porsche_918_Spyder");
            WebPage page6 = new WebPage("https://en.wikipedia.org/wiki/Mercedes-Benz_SLR_McLaren");
            WebPage page7 = new WebPage("https://en.wikipedia.org/wiki/Toyota_Hilux");
            WebPage page8 = new WebPage("https://en.wikipedia.org/wiki/Subaru_Impreza");
            WebPage page9 = new WebPage("https://en.wikipedia.org/wiki/Honda_S2000");
            WebPage page10 = new WebPage("https://en.wikipedia.org/wiki/Mazda_RX-7");


            String page1Content = page1.fetchAndCleanContent();
            String page2Content = page2.fetchAndCleanContent();
            String page3Content = page3.fetchAndCleanContent();
            String page4Content = page4.fetchAndCleanContent();
            String page5Content = page5.fetchAndCleanContent();
            String page6Content = page6.fetchAndCleanContent();
            String page7Content = page7.fetchAndCleanContent();
            String page8Content = page8.fetchAndCleanContent();
            String page9Content = page9.fetchAndCleanContent();
            String page10Content = page10.fetchAndCleanContent();


            String[] stringArr1 = page1.turnToArray(page1Content);
            List<String> stringList1 = Arrays.asList(stringArr1);
            String[] stringArr2 = page2.turnToArray(page2Content);
            List<String> stringList2 = Arrays.asList(stringArr2);
            String[] stringArr3 = page3.turnToArray(page3Content);
            List<String> stringList3 = Arrays.asList(stringArr3);
            String[] stringArr4 = page4.turnToArray(page4Content);
            List<String> stringList4 = Arrays.asList(stringArr4);
            String[] stringArr5 = page5.turnToArray(page5Content);
            List<String> stringList5 = Arrays.asList(stringArr5);
            String[] stringArr6 = page6.turnToArray(page6Content);
            List<String> stringList6 = Arrays.asList(stringArr6);
            String[] stringArr7 = page7.turnToArray(page7Content);
            List<String> stringList7 = Arrays.asList(stringArr7);
            String[] stringArr8 = page8.turnToArray(page8Content);
            List<String> stringList8 = Arrays.asList(stringArr8);
            String[] stringArr9 = page9.turnToArray(page9Content);
            List<String> stringList9 = Arrays.asList(stringArr9);
            String[] stringArr10 = page10.turnToArray(page10Content);
            List<String> stringList10 = Arrays.asList(stringArr10);


            ArrayList<List<String>> corpus = new ArrayList<>();
            corpus.add(stringList1);
            corpus.add(stringList2);
            corpus.add(stringList3);
            corpus.add(stringList4);
            corpus.add(stringList5);
            corpus.add(stringList6);
            corpus.add(stringList7);
            corpus.add(stringList8);
            corpus.add(stringList9);
            corpus.add(stringList10);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
