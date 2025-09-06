import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalTime;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import dMartData.DMartDatabase;

public class DMart extends JFrame implements ActionListener 
{
    String[] qty = {"0", "1", "2", "3", "4", "5", "6", "8", "9", "10"};
    String[] pmod = {"Cash", "Card", "Net-Banking", "UPI", "E Wallet", "Cheque", "Pay Later", "Pay with Rewalrds"};
    
    int subcatIndex, catIndex, iteamIndex;
    String[][] cart = new String[100][3];
    CardLayout cardLayout = new CardLayout();
    JPanel mainPanel = new JPanel(cardLayout);

    JTextField cfnameField, clnameField, cityField, stateField, emailField, PnoField, pincodeField, ageField;
    JLabel[] iteamLable = new JLabel[5];
    JLabel[] priceLable = new JLabel[5];
    JComboBox<String>[] qtyBox = new JComboBox[5];
    boolean isIteamPanelShown = false;
    int x = 0, TotalSum;
    int[] AllSum = new int[100];

    JComboBox<String> categoryCombo;
    JComboBox<String> subcategoryCombo;
    JComboBox<String> paymodeBox;
    
    JRadioButton maleButton, femaleButton, otherButton, nowButton, laterButton, tomorrowButton;
    ButtonGroup deliveryGroup, genderGroup;
    JPanel payment_buttonPanel, cartbottomPanel, selectionPanel, iteamPanel, purchasePanel, cartPanel, cartitemPanel, startPanel, paymentPanel, billPanel, cidPanel, genderPanel, deliveryPanel;
    JLabel heading;
    JButton regesterButton, payButton, paybackButton, cartButton, addButton, clearButton, startButton, paymentButton, backButton;
    JLabel totalLabel;
    JCheckBox bagCheck, termsCheck;
    JTextArea adressArea;

    public DMart() 
    {
        // Thread Colock
        Thread clockThread = new Thread(() -> 
        {
            while (true)
            {
                try 
                {
                    LocalDate currentDate = LocalDate.now();
                    LocalTime currentTime = LocalTime.now().withNano(0);
                    setTitle("DMart Shopping Center   " + currentDate + "   " + currentTime);
                    Thread.sleep(1000);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        });
        clockThread.start();


        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(mainPanel);



        //Start Panel -1
        startPanel = new JPanel(new BorderLayout(10, 10));
        heading = new JLabel("DMart", JLabel.CENTER);
        heading.setFont(new Font("Monospaced", Font.BOLD, 106));
        startPanel.add(heading, BorderLayout.CENTER);

        heading = new JLabel("Welcome To DMart - Your Money Our Property -Aaryan2R", JLabel.CENTER);
        heading.setFont(new Font("SansSerif", Font.BOLD, 26));
        startPanel.add(heading, BorderLayout.NORTH);

        startButton = new JButton("Start Shoping");
        startButton.addActionListener(this);
        startPanel.add(startButton, BorderLayout.SOUTH);



        //Regestration Panel -2
        cidPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        cidPanel.add(new JLabel("Enter Your First Name: ")); 
        cfnameField = new JTextField(); 
        cidPanel.add(cfnameField);

        cidPanel.add(new JLabel("Enter Your Last Name: ")); 
        clnameField = new JTextField(); 
        cidPanel.add(clnameField);

        cidPanel.add(new JLabel("Enter Your Age: ")); 
        ageField = new JTextField(); 
        cidPanel.add(ageField);

        cidPanel.add(new JLabel("Enter Your Email ID: ")); 
        emailField = new JTextField(); 
        cidPanel.add(emailField);
        cidPanel.add(new JLabel("Enter Your Phone No. : ")); 
        PnoField = new JTextField(); 
        cidPanel.add(PnoField);

        cidPanel.add(new JLabel("Select Your Gender: "));
        genderPanel = new JPanel();
        maleButton = new JRadioButton("Male"); 
        femaleButton = new JRadioButton("Female"); 
        otherButton = new JRadioButton("Others");
        genderGroup = new ButtonGroup();
        genderGroup.add(maleButton); 
        genderGroup.add(femaleButton); 
        genderGroup.add(otherButton);
        genderPanel.add(maleButton); 
        genderPanel.add(femaleButton); 
        genderPanel.add(otherButton);
        cidPanel.add(genderPanel);

        cidPanel.add(new JLabel("Enter Your State Name: ")); 
        stateField = new JTextField(); 
        cidPanel.add(stateField);

        cidPanel.add(new JLabel("Enter Your City Name: ")); 
        cityField = new JTextField(); 
        cidPanel.add(cityField);

        cidPanel.add(new JLabel("Enter Your Pin Code : ")); 
        pincodeField = new JTextField(); 
        cidPanel.add(pincodeField);

        cidPanel.add(new JLabel("Enter Your Complete Address"));
        adressArea = new JTextArea(4, 20);
        JScrollPane scrollPane = new JScrollPane(adressArea);
        cidPanel.add(scrollPane);

        termsCheck = new JCheckBox("I Accept T&C");
        cidPanel.add(termsCheck);
        regesterButton = new JButton("Regester");
        regesterButton.addActionListener(this);
        cidPanel.add(regesterButton);



        //Shopping Panel -3
        purchasePanel = new JPanel(new BorderLayout(10, 10));
        JPanel topPanel = new JPanel(new GridLayout(0, 5, 10, 10));

        topPanel.add(new JLabel("Select Category"));
        categoryCombo = new JComboBox<>(DMartDatabase.category);
        topPanel.add(categoryCombo);

        topPanel.add(new JLabel("Select Sub-Category"));
        subcategoryCombo = new JComboBox<>(DMartDatabase.subcategory[0]);
        topPanel.add(subcategoryCombo);

        cartButton = new JButton("Cart");
        cartButton.addActionListener(this);
        topPanel.add(cartButton);

        categoryCombo.addActionListener(e -> {
            subcategoryCombo.removeAllItems();
            catIndex = categoryCombo.getSelectedIndex();
            for (String sub : DMartDatabase.subcategory[catIndex])
            {
                subcategoryCombo.addItem(sub);
            }
            subcatIndex = 0;
        });

        subcategoryCombo.addActionListener(e -> {
            subcatIndex = subcategoryCombo.getSelectedIndex();
            updateIteamPanel();
        });

        iteamPanel = new JPanel(new GridLayout(6, 3, 10, 10));
        purchasePanel.add(topPanel, BorderLayout.NORTH);
        purchasePanel.add(iteamPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        addButton = new JButton("Add to Cart");
        addButton.addActionListener(this);
        bottomPanel.add(addButton);

        clearButton = new JButton("Clear Selection");
        clearButton.addActionListener(e -> {
            for (int i = 0; i < 5; i++) {
                String itemName = DMartDatabase.item[catIndex][subcatIndex][i];
                boolean inCart = false;
                for (int j = 0; j < x; j++) {
                    if (cart[j][0] != null && cart[j][0].equals(itemName)) {
                        inCart = true;
                        break;
                    }
                }
                if (!inCart) {
                    qtyBox[i].setSelectedIndex(0);
                }
            }
        });
        bottomPanel.add(clearButton);
        totalLabel = new JLabel("Total Price: ₹0.00");
        bottomPanel.add(totalLabel);
        purchasePanel.add(bottomPanel, BorderLayout.SOUTH);



        //Cart Panel -4
        cartPanel = new JPanel(new BorderLayout(10, 10));
        heading = new JLabel("Verify Items in Your Cart", JLabel.CENTER);
        heading.setFont(new Font("SansSerif", Font.BOLD, 16));
        cartPanel.add(heading, BorderLayout.NORTH);
        cartitemPanel = new JPanel();
        cartPanel.add(cartitemPanel, BorderLayout.CENTER);
        cartbottomPanel = new JPanel();
        cartPanel.add(cartbottomPanel, BorderLayout.SOUTH);




        //Payment Panel -5
        paymentPanel = new JPanel(new GridLayout(0, 1, 10, 10));
        heading = new JLabel("Select Payment And Delivery Info", JLabel.CENTER);
        heading.setFont(new Font("SansSerif", Font.BOLD, 16));
        paymentPanel.add(heading);


        paymentPanel.add(new JLabel("Select Your Delivery Schedule"));

        deliveryPanel = new JPanel();
        nowButton = new JRadioButton("Within 30 min."); 
        laterButton = new JRadioButton("After 2-4 hr."); 
        tomorrowButton = new JRadioButton("Tomorrow Morning");
        deliveryGroup = new ButtonGroup();
        deliveryGroup.add(nowButton); 
        deliveryGroup.add(laterButton); 
        deliveryGroup.add(tomorrowButton);
        deliveryPanel.add(nowButton); 
        deliveryPanel.add(laterButton); 
        deliveryPanel.add(tomorrowButton);
        paymentPanel.add(deliveryPanel);


        paymodeBox = new JComboBox<>(pmod);
        paymentPanel.add(paymodeBox);

        bagCheck = new JCheckBox("I don't need a Paper Bag");
        paymentPanel.add(bagCheck);

        payment_buttonPanel = new JPanel();
        paybackButton = new JButton("Back");
        paybackButton.addActionListener(e -> cardLayout.show(mainPanel, "Cart"));
        payButton = new JButton("Pay");
        payButton.addActionListener(e -> generateBill());
        payment_buttonPanel.add(paybackButton);
        payment_buttonPanel.add(payButton);
        paymentPanel.add(payment_buttonPanel);




        //Bill Panel -5
        billPanel = new JPanel();
        billPanel.setLayout(new BoxLayout(billPanel, BoxLayout.Y_AXIS));
        JButton doneButton = new JButton("Done");
        doneButton.addActionListener(e -> System.exit(0));
        billPanel.add(doneButton);



        //Card Layout Add all Panel
        mainPanel.add(startPanel, "Start");
        mainPanel.add(cidPanel, "Regestration");
        mainPanel.add(purchasePanel, "Shooping");
        mainPanel.add(cartPanel, "Cart");
        mainPanel.add(paymentPanel, "Payment");
        mainPanel.add(billPanel, "Bill");


        setVisible(true);
        cardLayout.show(mainPanel, "Start");
    }

    public void updateCartPanel()
    {
        cartitemPanel.removeAll();
        cartitemPanel.setLayout(new GridLayout(0, 4, 10, 10));
        cartitemPanel.add(new JLabel("Item")); cartitemPanel.add(new JLabel("Price (₹)"));
        cartitemPanel.add(new JLabel("Quantity")); cartitemPanel.add(new JLabel("Total"));

        TotalSum = 0;
        for (int i = 0; i < x; i++)
        {
            cartitemPanel.add(new JLabel(cart[i][0]));
            cartitemPanel.add(new JLabel(cart[i][1]));
            cartitemPanel.add(new JLabel(cart[i][2]));
            int ip = (int) Double.parseDouble(cart[i][1]);
            int iq = Integer.parseInt(cart[i][2]);
            AllSum[i] = ip * iq;
            cartitemPanel.add(new JLabel("₹" + AllSum[i]));
            TotalSum += AllSum[i];
        }

        cartbottomPanel.removeAll();
        cartbottomPanel.setLayout(new BorderLayout(10, 10));
        cartbottomPanel.add(new JLabel("Total: ₹" + TotalSum), BorderLayout.NORTH);

        backButton = new JButton("Back");
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "Shooping"));
        cartbottomPanel.add(backButton, BorderLayout.WEST);

        paymentButton = new JButton("Continue To Payment");
        paymentButton.addActionListener(e -> cardLayout.show(mainPanel, "Payment"));
        cartbottomPanel.add(paymentButton, BorderLayout.EAST);

        cartPanel.revalidate(); cartPanel.repaint();
    }

    public void updateIteamPanel()
    {
        iteamPanel.removeAll();
        iteamPanel.setLayout(new GridLayout(6, 3, 10, 10));
        iteamPanel.add(new JLabel("Item")); iteamPanel.add(new JLabel("Price (₹)")); iteamPanel.add(new JLabel("Quantity"));

        if (catIndex >= 0 && subcatIndex >= 0)
        {
            for (int i = 0; i < 5; i++)
            {
                String itemName = DMartDatabase.item[catIndex][subcatIndex][i];
                iteamPanel.add(new JLabel(itemName));
                iteamPanel.add(new JLabel("₹ " + DMartDatabase.price[catIndex][subcatIndex][i]));
                qtyBox[i] = new JComboBox<>(qty);
                qtyBox[i].setSelectedIndex(0);

                for (int j = 0; j < x; j++)
                {
                    if (cart[j][0] != null && cart[j][0].equals(itemName))
                    {
                        int prevQty = Integer.parseInt(cart[j][2]);
                        qtyBox[i].setSelectedIndex(prevQty);
                        break;
                    }
                }

                iteamPanel.add(qtyBox[i]);
            }
        }

        revalidate(); repaint();
    }

    public void generateBill()
    {
        LocalDate currentDate = LocalDate.now();
        LocalTime currentTime = LocalTime.now().withNano(0);
        billPanel.removeAll();

        billPanel.add(new JLabel("Date of Order" + currentDate + "\nTime of Order: " + currentTime));
        billPanel.add(new JLabel("Customer Name: " + cfnameField.getText() + " " + clnameField.getText()));
        billPanel.add(new JLabel("Phone No: " + PnoField.getText()));
        billPanel.add(new JLabel("Email: " + emailField.getText()));
        billPanel.add(new JLabel("Address: " + adressArea.getText()));
        billPanel.add(new JLabel("Delivery Time: " + (nowButton.isSelected() ? "Within 30 min" : laterButton.isSelected() ? "After 2–4 hr" : "Tomorrow Morning")));
        billPanel.add(new JLabel("Payment Mode: " + paymodeBox.getSelectedItem()));
        billPanel.add(new JLabel("Need Bag: " + (bagCheck.isSelected() ? "No" : "Yes")));
        billPanel.add(new JLabel("----- Items -----"));

        for (int i = 0; i < x; i++)
        {
            billPanel.add(new JLabel((i + 1) + ". " + cart[i][0] + " x " + cart[i][2] + " = ₹" + AllSum[i]));
        }

        billPanel.add(new JLabel("Total Amount: ₹" + TotalSum));
        JButton doneButton = new JButton("Done");
        doneButton.addActionListener(e -> System.exit(0));
        billPanel.add(doneButton);

        cardLayout.show(mainPanel, "Bill");
        billPanel.revalidate(); billPanel.repaint();
    }

    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == addButton)
        {
            for (int i = 0; i < 5; i++)
            {
                int q = qtyBox[i].getSelectedIndex();
                if (q > 0)
                {
                    boolean alreadyExists = false;
                    for (int j = 0; j < x; j++)
                    {
                        if (cart[j][0] != null && cart[j][0].equals(DMartDatabase.item[catIndex][subcatIndex][i]))
                        {
                            alreadyExists = true;
                            break;
                        }
                    }
                    if (!alreadyExists)
                    {
                        cart[x][0] = DMartDatabase.item[catIndex][subcatIndex][i];
                        cart[x][1] = String.valueOf(DMartDatabase.price[catIndex][subcatIndex][i]);
                        cart[x][2] = String.valueOf(q);
                        x++;
                    }
                }
            }
        }
        else if (e.getSource() == cartButton)
        {
            updateCartPanel();
            cardLayout.show(mainPanel, "Cart");
        }
        else if (e.getSource() == startButton)
        {
            cardLayout.show(mainPanel, "Regestration");
        }
        else if (e.getSource() == regesterButton)
        {
            if (!termsCheck.isSelected())
            {
                JOptionPane.showMessageDialog(DMart.this, "You must accept the terms and conditions.", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }
            else
            {
                cardLayout.show(mainPanel, "Shooping");
            }
        }
    }

    public static void main(String[] args)
    {
        new DMart();
    }
}
