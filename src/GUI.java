import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GUI extends JFrame{

    private JTextField userInputField;
    private JButton storeButton;
    private JLabel resultLabel;

    public GUI() {
        // Set up the JFrame
        setTitle("User Input GUI");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 300);
        setLocationRelativeTo(null);

        // Create components
        userInputField = new JTextField(20);
        storeButton = new JButton("Enter a term or link to look up then press me :)");
        resultLabel = new JLabel("");

        // Set layout manager
        setLayout(new GridLayout(3, 1));

        // Add components to the content pane
        add(userInputField);
        add(storeButton);
        add(resultLabel);

        // Add action listener to the button
        storeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                // When the button is clicked, store the user input
                String userInput = userInputField.getText();
                WebPage page1 = new WebPage("https://en.wikipedia.org/wiki/Bluefish");
                WebPage page2 = new WebPage("https://en.wikipedia.org/wiki/Black_sea_bass");
                WebPage page3 = new WebPage("https://en.wikipedia.org/wiki/Scup");
                WebPage page4 = new WebPage("https://en.wikipedia.org/wiki/Ocean_sunfish");
                WebPage page5 = new WebPage("https://en.wikipedia.org/wiki/Striped_bass");
                WebPage page6 = new WebPage("https://en.wikipedia.org/wiki/Blue_mackerel");
                WebPage page7 = new WebPage("https://en.wikipedia.org/wiki/Tautog");
                WebPage page8 = new WebPage("https://en.wikipedia.org/wiki/Red_drum");
                WebPage page9 = new WebPage("https://en.wikipedia.org/wiki/Eastern_oyster");
                WebPage page10 = new WebPage("https://en.wikipedia.org/wiki/Hard_clam");
                ArrayList<WebPage> webpages = new ArrayList<>();
                webpages.add(page1);webpages.add(page2);webpages.add(page3);webpages.add(page4);webpages.add(page5);webpages.add(page6);webpages.add(page7);webpages.add(page8);webpages.add(page9);webpages.add(page10);


                String[] inputArray1 = page1.turnToArray(page1.fetchAndCleanContent());
                String[] inputArray2 = page2.turnToArray(page2.fetchAndCleanContent());
                String[] inputArray3 = page3.turnToArray(page3.fetchAndCleanContent());
                String[] inputArray4 = page4.turnToArray(page4.fetchAndCleanContent());
                String[] inputArray5 = page5.turnToArray(page5.fetchAndCleanContent());
                String[] inputArray6 = page6.turnToArray(page6.fetchAndCleanContent());
                String[] inputArray7 = page7.turnToArray(page7.fetchAndCleanContent());
                String[] inputArray8 = page8.turnToArray(page8.fetchAndCleanContent());
                String[] inputArray9 = page9.turnToArray(page9.fetchAndCleanContent());
                String[] inputArray10 = page10.turnToArray(page10.fetchAndCleanContent());


                TFIDF tfidf = new TFIDF();
                ArrayList<List<String>> corpus = new ArrayList<>();
                corpus.add(Arrays.asList(inputArray1));
                corpus.add(Arrays.asList(inputArray2));
                corpus.add(Arrays.asList(inputArray3));
                corpus.add(Arrays.asList(inputArray4));
                corpus.add(Arrays.asList(inputArray5));
                corpus.add(Arrays.asList(inputArray6));
                corpus.add(Arrays.asList(inputArray7));
                corpus.add(Arrays.asList(inputArray8));
                corpus.add(Arrays.asList(inputArray9));
                corpus.add(Arrays.asList(inputArray10));

                HT tfTable1 = tfidf.calculateTFTable(inputArray1);
                HT tfTable2 = tfidf.calculateTFTable(inputArray2);
                HT tfTable3 = tfidf.calculateTFTable(inputArray3);
                HT tfTable4 = tfidf.calculateTFTable(inputArray4);
                HT tfTable5 = tfidf.calculateTFTable(inputArray5);
                HT tfTable6 = tfidf.calculateTFTable(inputArray6);
                HT tfTable7 = tfidf.calculateTFTable(inputArray7);
                HT tfTable8 = tfidf.calculateTFTable(inputArray8);
                HT tfTable9 = tfidf.calculateTFTable(inputArray9);
                HT tfTable10 = tfidf.calculateTFTable(inputArray10);



                HT idfTable1 = tfidf.calculateIDFTable(corpus, inputArray1);
                HT idfTable2 = tfidf.calculateIDFTable(corpus, inputArray2);
                HT idfTable3 = tfidf.calculateIDFTable(corpus, inputArray3);
                HT idfTable4 = tfidf.calculateIDFTable(corpus, inputArray4);
                HT idfTable5 = tfidf.calculateIDFTable(corpus, inputArray5);
                HT idfTable6 = tfidf.calculateIDFTable(corpus, inputArray6);
                HT idfTable7 = tfidf.calculateIDFTable(corpus, inputArray7);
                HT idfTable8 = tfidf.calculateIDFTable(corpus, inputArray8);
                HT idfTable9 = tfidf.calculateIDFTable(corpus, inputArray9);
                HT idfTable10 = tfidf.calculateIDFTable(corpus, inputArray10);

                HT tfidfTable1 = tfidf.calculateTFIDFTable(tfTable1, idfTable1);
                HT tfidfTable2 = tfidf.calculateTFIDFTable(tfTable2, idfTable2);
                HT tfidfTable3 = tfidf.calculateTFIDFTable(tfTable3, idfTable3);
                HT tfidfTable4 = tfidf.calculateTFIDFTable(tfTable4, idfTable4);
                HT tfidfTable5 = tfidf.calculateTFIDFTable(tfTable5, idfTable5);
                HT tfidfTable6 = tfidf.calculateTFIDFTable(tfTable6, idfTable6);
                HT tfidfTable7 = tfidf.calculateTFIDFTable(tfTable7, idfTable7);
                HT tfidfTable8 = tfidf.calculateTFIDFTable(tfTable8, idfTable8);
                HT tfidfTable9 = tfidf.calculateTFIDFTable(tfTable9, idfTable9);
                HT tfidfTable10 = tfidf.calculateTFIDFTable(tfTable10, idfTable10);


                ArrayList<HT> tfidfTable = new ArrayList<>();
                tfidfTable.add(tfidfTable1);
                tfidfTable.add(tfidfTable2);
                tfidfTable.add(tfidfTable3);
                tfidfTable.add(tfidfTable4);
                tfidfTable.add(tfidfTable5);
                tfidfTable.add(tfidfTable6);
                tfidfTable.add(tfidfTable7);
                tfidfTable.add(tfidfTable8);
                tfidfTable.add(tfidfTable9);
                tfidfTable.add(tfidfTable10);


                int index = tfidf.getDoc(tfidfTable, userInput);

                if (index == -1){
                    if (userInput.startsWith("https")){
                        WebPage searchPage = new WebPage(userInput);
                        corpus.add(Arrays.asList(searchPage.turnToArray(searchPage.fetchAndCleanContent())));
                        String[] searchPageArr = searchPage.turnToArray(searchPage.fetchAndCleanContent());
                        HT tfidfSearchTable = tfidf.calculateTFIDFTable(tfidf.calculateTFTable(searchPageArr), tfidf.calculateIDFTable(corpus, searchPageArr));
                        int i = tfidf.getWebpage(tfidfTable, tfidfSearchTable);

                        WebPage webPageOne = webpages.get(i);
                        tfidfTable.remove(i);
                        webpages.remove(i);
                        WebPage webPageTwo = webpages.get(tfidf.getWebpage(tfidfTable,tfidfSearchTable));

                        resultLabel.setText("I would recommend: " + webPageOne + " and " + webPageTwo);
                    } else { resultLabel.setText("I would recommend: " + "https://en.wikipedia.org/wiki/Uh_Oh");}
                } else {


                    WebPage webPageOne = webpages.get(index);
                    tfidfTable.remove(index);
                    webpages.remove(index);
                    WebPage webPageTwo = webpages.get(tfidf.getDoc(tfidfTable,userInput));

                    resultLabel.setText("I would recommend: " + webPageOne + " and " + webPageTwo);

                }






                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GUI().setVisible(true);
            }
        });
    }
}
