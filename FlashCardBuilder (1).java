import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

public class FlashCardBuilder {
    private JTextArea question;
    private JTextArea answer;
    private ArrayList<FlashCard> cardlist;
    private JFrame frame;


    public FlashCardBuilder() {
        //Building the  GUI interface

        frame = new JFrame("Flash Card") ;
        ////Creates a new, initially invisible Frame with the specified title.
        JPanel mainPanel = new JPanel();
        //Create a Jpanel to hold everything
        Font greatFont = new Font("Modern Love", Font.BOLD, 20);
        //It is the class in AWT package,The Font class represents fonts, which are used to render text in a visible way.
        //Creates a new Font from the specified name, style and point size

        // //Question area
        question = new JTextArea(6, 20);
        //Constructs a new empty TextArea with the specified number of rows and columns.
        question.setLineWrap(true);
        // It Sets the line-wrapping policy of the text area.
        question.setWrapStyleWord(true);
        question.setFont(greatFont);// It sets the font of the text.

        //Answer area
        answer = new JTextArea(6, 20);
        answer.setLineWrap(true);
        answer.setWrapStyleWord(true);
        answer.setFont(greatFont);

        //JscrollPane
        JScrollPane qJscrollPane = new JScrollPane(question);
        //It sets the are whether it can be scrolled or not
        qJscrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        //only vertical scroll bar
        JScrollPane aJscrollPane = new JScrollPane(answer);
        aJscrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        //An implementation of the Icon interface that paints Icons from Images.
        // As parameter is string type,it creates an ImageIcon from the specified file
        ImageIcon image = new ImageIcon("src/submit.jpeg");
        Icon icon1 = new ImageIcon("src/que.jpeg");
        Icon icon2 = new ImageIcon("src/ans.jpeg");


        JButton nextButton = new JButton(image);
        //Creates a button with initial text and an icon. Created with one parameter "icon"
        nextButton.addActionListener(new NextCardListener());
        //This registers your interest with the button. This says to the button, “Add me to your list of listeners”.
        // The argument you pass MUST be an object from a class that implements ActionList
        //NextCardListener is a class that implements ActionList
        cardlist = new ArrayList<FlashCard>();
        //initiansiate the cardlist with an arraylist

        JLabel qJLabel = new JLabel("Question:",icon1,SwingConstants.HORIZONTAL);
        //A display area for a short text string or an image, or both .
        //Create a labels
        JLabel aJLabel = new JLabel("Answer:",icon2,SwingConstants.HORIZONTAL);



        //It create a MenuBar at top of the mainpanel
        JMenuBar menubar = new JMenuBar();
        JMenu fileMenu = new JMenu("FILE");
        // It creates the items for the MenuBar
        JMenuItem newMenuItem = new JCheckBoxMenuItem("NEW");
        JMenuItem saveMenuItem = new JCheckBoxMenuItem("SAVE");
        //It adds the items to the Filemenu
        fileMenu.add(newMenuItem);
        fileMenu.add(saveMenuItem);
        //It adds the filemenu which have the menu items to the mainBar
        menubar.add(fileMenu);

        //It Adds the Action listener
        newMenuItem.addActionListener(new NewMenuItemListener());
        saveMenuItem.addActionListener(new NewSaveItemListener());
        frame.setJMenuBar(menubar);

        //ADD components to main panel
        mainPanel.add(qJLabel);
        mainPanel.add(qJscrollPane);
        mainPanel.add(aJLabel);
        mainPanel.add(aJscrollPane);
        mainPanel.add(nextButton);
        mainPanel.setBackground(Color.CYAN); //sets color

        //Add components to the frame
        frame.getContentPane().add(BorderLayout.CENTER, mainPanel); //returns the content from the mainPanel
        frame.setSize(450, 600);//sets the size of the Frame
        frame.setVisible(true);// set the frame to be visible which is by default invisible
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // set to close the program when we close the frame.

    }


    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new FlashCardBuilder();
            }
        });
    }

        class NextCardListener implements ActionListener {
            public void actionPerformed(ActionEvent e) {
            //create a new flashcard
                FlashCard card = new FlashCard(question.getText(), answer.getText());
                cardlist.add(card);
                System.out.println(cardlist.size());
                //clear the previous card and generate a empty JTextfield
                clearcard();
            }
        }

    private void clearcard() {
        //sets both question and answer fields empty and request focus to question.
          question.setText("");
          answer.setText("");
          question.requestFocus();
    }

    class NewMenuItemListener implements ActionListener{
        @Override
            public void actionPerformed(ActionEvent e) {
                //It creates the action to be performed when we click on the new menu item.
                System.out.println("NEW MENU CLICKED");
                cardlist.clear();
                clearcard(); //calls the clear card method
            }
        }

        class NewSaveItemListener implements ActionListener{
            //Implement the ActionListener interface’s actionPerformed() method..
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Save Menu CLICKED");
                FlashCard card = new FlashCard(question.getText(), answer.getText());
                cardlist.add(card);
                //It add the questions and answer in a arraylist from the text fields.
                JFileChooser fileSave = new JFileChooser();
                //creates a file dialog with file chooser to save
                fileSave.showSaveDialog(frame);
                //opening the save dialog and to save

                saveFile(fileSave.getSelectedFile()); // //call the savefile method which writs the file
                clearcard();
            }
        }

        public void saveFile(File selectedfile){
          try {
              BufferedWriter writer = new BufferedWriter(new FileWriter(selectedfile) );//the bufferwriter wraps the filewriter class for buffering
              // Writes text to a charcter output stream buffering charcters so as to provide for the efficient writing
              // of single charcters,arrays and strings.
              for (FlashCard card : cardlist) {
                  //It creates the format to save the questions and answers
                  writer.write(card.getQuestion() + "/"); //to seperate the question with answer
                  writer.write(card.getAnswer() + "\n"); //to jump to next line after writing the answer
              }
                  writer.close(); //to close the writer(BufferWriter)
          }catch (Exception e){
              System.out.println("Couldn't write to file");
          }
    }
}







