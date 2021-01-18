import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class FlashCardPlayer{

        private JTextArea display;
        private JButton Button;
        private ArrayList<FlashCard> cardlist;
        private JFrame frame;
        private FlashCard currentCard;
        private int currentCardIndex;
        private boolean isShowAnswer;

        public FlashCardPlayer() {
            //builds the Gui interface same as BUILDER

            frame = new JFrame("FLASH CARD PLAYER");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            JPanel mainPanel = new JPanel();

            Font greatFont = new Font("Modern Love", Font.BOLD, 20);

            // //QuestiON AND ANSWER area
            display = new JTextArea(10, 20);
            display.setLineWrap(true);
            display.setWrapStyleWord(true);
            display.setFont(greatFont);

            Button = new JButton("SHOW ANSWER");
            Button.addActionListener(new NextCardListener());


            JScrollPane qScroll  = new JScrollPane(display);
            qScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

            mainPanel.add(qScroll);
            mainPanel.add(Button);
            mainPanel.setBackground(Color.green);

            JMenuBar menuBar = new JMenuBar();
            JMenu menu = new JMenu("FILE");
            JMenuItem loaditem = new JMenuItem("Load Card");

            menu.add(loaditem);

            loaditem.addActionListener(new loaditemListener());
            menuBar.add(menu);

            frame.setJMenuBar(menuBar);
            frame.getContentPane().add(BorderLayout.CENTER, mainPanel);
            frame.setVisible(true);
            frame.setSize(400,500);

            cardlist = new ArrayList<FlashCard>();
        }


        public static void main(String[] args) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    new FlashCardPlayer();
                }
            });
        }


    private class loaditemListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            //creates a file dialog with file chooser to load
            JFileChooser loaditem = new JFileChooser();
            loaditem.showOpenDialog(frame);
            //shows the dialog box to load the file
            loadFile(loaditem.getSelectedFile());
            //calls the loadFile method which takes the selected file  as argument
        }

        }


    private void loadFile(File selectedFile) {

        cardlist = new ArrayList<FlashCard>();
        try {

            BufferedReader reader = new BufferedReader(new FileReader(selectedFile));
            // It reads he text from a character-based input stream which is wrapped with Filereader which holds the selectedfile
            String line = null;
            while ((line = reader.readLine()) != null) {
                makeCard(line);
                //Until the BufferReader reads the line it goes on creating card with makecard method which take
                //each line as parameter
            }
            reader.close();
            //when line == null ,the reader stops .
        } catch(Exception ex) {
            System.out.println("couldn’t read the card file");
            ex.printStackTrace();
        }
        // now time to start by showing the first card
        showNextCard();
    }

    private void makeCard(String lineToParse) {

         //Each line of text corresponds to a single flashcard, but we have to parse out the
        //question and answer as separate pieces. We use the String split() method to break the
        //line into two tokens (one for the question and one for the answer).

        String[] result = lineToParse.split("/");
        //Split the card at "/" into 2 parts using regex function with index [0] as question and [1] as answer.
        FlashCard card = new FlashCard(result[0], result[1]);
        //Creates the card with seperated question and answer.
        cardlist.add(card);
        //Puts the card in the cardlist
        System.out.println("made a card");

        }


    class NextCardListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

                if (isShowAnswer) {
                    //Check the isShowAnswer boolean flag tosee if they’re currently viewing a question
                    //or an answer, and do the appropriate thing depending on the answer.

                    // show the answer because they’ve seen the question
                    display.setText(currentCard.getAnswer());
                    //displays the answer
                    Button.setText("Next Card");
                    //sets the button text to next card
                    isShowAnswer = false;
                    //makes the boolean isShowAnswer to false
                } else {
                    // show the next question
                    if (currentCardIndex < cardlist.size()) {
                        //when currentCardIndex is less than size of cardlist it calles the showNextCard method.
                        showNextCard();
                    }
                    else {
                        // there are no more cards!
                        display.setText("That was your last card");
                        // dispalys the following statement when there are no more cards
                        Button.setEnabled(false);
                        // it disables the button.
                    } } }
        }


    private void showNextCard() {

        currentCard = cardlist.get(currentCardIndex);//gets the content stored(question) in the cardlist array with index CurrentCardIndex
        currentCardIndex++; //increases the index of currentCardIndex
        display.setText(currentCard.getQuestion());//displays the question
        Button.setText("Show Answer"); // sets the button to show answer
        isShowAnswer = true;  //set the boolen is showAnswer to true
    }

}

