import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import org.apache.commons.text.similarity.CosineSimilarity;
import org.apache.commons.text.similarity.JaccardSimilarity;


import java.lang.annotation.Documented;

public class WebPage {
    //information regarding the webpage that I might want to store and retrieve.
    public String url;
    public WebPage(String url){this.url = url;}

    public String getURL(){return url;}

    public String fetchAndCleanContent() throws Exception{
        //Fetch the page content
        Document doc = Jsoup.connect(url).get();

        //Clean the content and remove unwanted elements
        Element contentElement = doc.select("#mw-content-text").first(); // This can be adjusted based on Wikipedia's structure

        //Remove citations, references, and other elements if needed.
        contentElement.select(".reference").remove();


        String contentElementText = contentElement.text();
        if (contentElementText.contains("Retrieved from")){
            contentElementText  = contentElementText.substring(0, contentElementText.indexOf("Retrieved from"));
        }

        //Extract and return the text.
        return contentElementText.toLowerCase();
    }

    public String[] turnToArray(String content){
        return content.split("\\s+");
    }

    public static double calculateSimilarity(String content1, String content2){
        //Tokenize the content, aka split into words
        String[] tokens1 = content1.split("\\s+");
        String[] tokens2 = content2.split("\\s+");

        //Turn the String Arrays into CharSequence
        CharSequence charSeqTokens1 = String.join(" ", tokens1);
        CharSequence charSeqTokens2 = String.join(" ", tokens2);

        //Calculate Jaccard similarity using Apache Commons Text Library
        JaccardSimilarity jaccardSimilarity = new JaccardSimilarity();
        double similarity = jaccardSimilarity.apply(charSeqTokens1, charSeqTokens2);
        return similarity;
    }

    public String toString(){
        return url;
    }

    public static void main(String[] args) {
        try {
            WebPage page1 = new WebPage("https://en.wikipedia.org/wiki/Toyota_4Runner");
            WebPage page2 = new WebPage("https://en.wikipedia.org/wiki/Doug_Lea");
            WebPage page3 = new WebPage("https://en.wikipedia.org/wiki/Julius_Caesar");
            WebPage page4 = new WebPage("https://en.wikipedia.org/wiki/Lex_Fridman");
            String page1Content = page1.fetchAndCleanContent();
            String page2Content = page2.fetchAndCleanContent();
            String page3Content = page3.fetchAndCleanContent();
            String page4Content = page4.fetchAndCleanContent();
            System.out.println("These two pages should be identical therefore a score of 1: " + calculateSimilarity(page1Content, page2Content));
            System.out.println("These two pages are that of Doug Lea and Julius Ceasar: " + calculateSimilarity(page1Content, page3Content));
            System.out.println("These two pages are that of Doug Lea and Lex Fridman: " + calculateSimilarity(page1Content, page4Content));
            System.out.println("Notice how Lex Fridman and Doug Lea are more similar, this is likely becuase they are both computer scientists and researchers");

            System.out.println("Page 1 Content is as follows: \n" + page1Content);
            System.out.println("Page 4 Content is as follows: \n" + page4Content);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
