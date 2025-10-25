// DMart.java — Complete professional system with validation, address management, profile editing
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import dMartData.DMartDatabase;

public class DMart extends JFrame implements ActionListener 
{
    String[] pmod = {"Cash", "Card", "Net-Banking", "UPI", "E Wallet", "Cheque", "Pay Later", "Pay with Rewards"};
    
    int subcatIndex = 0, catIndex = 0;
    String[][] cart = new String[100][3]; // [name, price, qty]
    CardLayout cardLayout = new CardLayout();
    JPanel mainPanel = new JPanel(cardLayout);

    // Form fields & UI
    JTextField cfnameField, clnameField, emailField, PnoField, pincodeField, ageField;
    JButton[] minusBtn = new JButton[5];
    JButton[] plusBtn = new JButton[5];
    JLabel[] qtyLabel = new JLabel[5];
    JLabel[] subtotalLabel = new JLabel[5];
    int[] currentQty = new int[5];
    
    int x = 0, TotalSum;
    int[] AllSum = new int[100];

    JComboBox<String> categoryCombo;
    JComboBox<String> subcategoryCombo;
    JComboBox<String> paymodeBox;
    
    JRadioButton maleButton, femaleButton, otherButton, nowButton, laterButton, tomorrowButton;
    ButtonGroup deliveryGroup, genderGroup, addressGroup;
    JPanel payment_buttonPanel, cartbottomPanel, iteamPanel, purchasePanel, cartPanel, cartitemPanel, startPanel, paymentPanel, billPanel, cidPanel, genderPanel, deliveryPanel;
    JLabel heading;
    JButton regesterButton, payButton, paybackButton, cartButton, addButton, clearButton, startButton, paymentButton, backButton, editProfileButton;
    JLabel totalLabel;
    JCheckBox bagCheck, termsCheck;
    JTextArea adressArea;

    // Payment form fields
    JTextField cardNameField, cardNumberField, cardExpField, cardCVVField;
    JCheckBox saveCardCheck;
    JTextField upiIdField, netBankingField, ewalletField;

    // Address management
    List<String> savedAddresses = new ArrayList<>();
    JRadioButton useRegisteredAddress, useNewAddress;
    JTextArea newAddressArea;
    JPanel addressSelectionPanel;

    // State/city UI
    JComboBox<String> stateCombo, cityCombo;

    // top bar and theme
    JPanel topBar;
    JToggleButton darkModeToggle;
    JPanel parentPanel;
    boolean darkMode = false;

    // Orders history with live countdown
    List<Order> orders = new ArrayList<>();
    JPanel ordersPanel;
    DefaultListModel<String> ordersListModel = new DefaultListModel<>();
    JList<String> ordersJList;
    Timer countdownTimer;

    // registration/flow flags
    boolean isRegistered = false;

    // sizing constants
    private final Dimension BTN_BIG = new Dimension(140, 42);
    private final Font BTN_FONT = new Font("SansSerif", Font.PLAIN, 14);

    // Random helper
    private final Random rnd = new Random();

    // Date formatter
    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static final DateTimeFormatter timeOnlyFmt = DateTimeFormatter.ofPattern("HH:mm");
    private static final DateTimeFormatter dateFmt = DateTimeFormatter.ofPattern("MM-dd");

    public DMart() 
    {
        // Clock thread
        Thread clockThread = new Thread(() -> {
            while (true) {
                try {
                    LocalDate currentDate = LocalDate.now();
                    LocalTime currentTime = LocalTime.now().withNano(0);
                    setTitle("DMart Shopping Center   " + currentDate + "   " + currentTime);
                    Thread.sleep(1000);
                } catch (InterruptedException e) { e.printStackTrace(); }
            }
        });
        clockThread.start();

        setSize(1000, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        parentPanel = new JPanel(new BorderLayout());
        createTopBar();
        parentPanel.add(topBar, BorderLayout.NORTH);
        parentPanel.add(mainPanel, BorderLayout.CENTER);
        setContentPane(parentPanel);

        // START PANEL
        startPanel = new JPanel(new BorderLayout(10,10));
        startPanel.setOpaque(true);
        
        JPanel topSection = new JPanel(new BorderLayout());
        JLabel subHeading = new JLabel("Welcome To DMart - Your Money Our Property - Aaryan2R", JLabel.CENTER);
        subHeading.setFont(new Font("SansSerif", Font.BOLD, 26));
        topSection.add(subHeading, BorderLayout.CENTER);
        
        editProfileButton = new JButton("✎ Edit Profile");
        editProfileButton.setPreferredSize(new Dimension(130, 35));
        editProfileButton.addActionListener(e -> {
            if (!isRegistered) {
                JOptionPane.showMessageDialog(this, "Please register first!", "Info", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            cardLayout.show(mainPanel, "Registration");
        });
        JPanel editPanel = new JPanel();
        editPanel.add(editProfileButton);
        topSection.add(editPanel, BorderLayout.EAST);
        
        startPanel.add(topSection, BorderLayout.NORTH);
        
        JLabel titleLabel = new JLabel("DMart", JLabel.CENTER);
        titleLabel.setFont(new Font("Monospaced", Font.BOLD, 106));
        startPanel.add(titleLabel, BorderLayout.CENTER);
        startButton = createBigButton("Start Shopping");
        startButton.addActionListener(this);
        startPanel.add(startButton, BorderLayout.SOUTH);

        // REGISTRATION PANEL
        cidPanel = new JPanel(new GridLayout(0,2,10,10));
        cidPanel.add(new JLabel("Enter Your First Name:")); cfnameField = new JTextField(); cidPanel.add(cfnameField);
        cidPanel.add(new JLabel("Enter Your Last Name:")); clnameField = new JTextField(); cidPanel.add(clnameField);
        cidPanel.add(new JLabel("Enter Your Age (12-110):")); ageField = new JTextField(); cidPanel.add(ageField);
        cidPanel.add(new JLabel("Enter Your Email ID:")); emailField = new JTextField(); cidPanel.add(emailField);
        cidPanel.add(new JLabel("Enter Your Phone No. (10 digits):")); PnoField = new JTextField(); cidPanel.add(PnoField);
        cidPanel.add(new JLabel("Select Your Gender:"));
        genderPanel = new JPanel();
        maleButton = new JRadioButton("Male"); femaleButton = new JRadioButton("Female"); otherButton = new JRadioButton("Others");
        genderGroup = new ButtonGroup(); genderGroup.add(maleButton); genderGroup.add(femaleButton); genderGroup.add(otherButton);
        genderPanel.add(maleButton); genderPanel.add(femaleButton); genderPanel.add(otherButton); cidPanel.add(genderPanel);

        cidPanel.add(new JLabel("Enter Your State:"));
        stateCombo = new JComboBox<>(DMartDatabase.states);
        cidPanel.add(stateCombo);
        cidPanel.add(new JLabel("Enter Your City:"));
        cityCombo = new JComboBox<>(DMartDatabase.cities[0]);
        cidPanel.add(cityCombo);
        stateCombo.addActionListener(ev -> {
            int idx = stateCombo.getSelectedIndex();
            if (idx < 0 || idx >= DMartDatabase.cities.length) return;
            cityCombo.removeAllItems();
            for (String c : DMartDatabase.cities[idx]) cityCombo.addItem(c);
        });

        cidPanel.add(new JLabel("Enter Your Pin Code (6 digits):")); pincodeField = new JTextField(); cidPanel.add(pincodeField);
        cidPanel.add(new JLabel("Enter Your Complete Address")); adressArea = new JTextArea(4,20); cidPanel.add(new JScrollPane(adressArea));
        termsCheck = new JCheckBox("I Accept Terms & Conditions"); cidPanel.add(termsCheck);
        regesterButton = createBigButton("Register"); regesterButton.addActionListener(this); cidPanel.add(regesterButton);

        // PURCHASE PANEL
        purchasePanel = new JPanel(new BorderLayout(10,10));
        JPanel topPanel = new JPanel(new GridLayout(0,5,10,10));
        topPanel.add(new JLabel("Select Category"));
        categoryCombo = new JComboBox<>(DMartDatabase.category); topPanel.add(categoryCombo);
        topPanel.add(new JLabel("Select Sub-Category"));
        subcategoryCombo = new JComboBox<>(DMartDatabase.subcategory[0]); topPanel.add(subcategoryCombo);
        cartButton = createBigButton("Cart"); cartButton.addActionListener(this); topPanel.add(cartButton);
        categoryCombo.addActionListener(e -> {
            catIndex = categoryCombo.getSelectedIndex();
            subcategoryCombo.removeAllItems();
            for (String s : DMartDatabase.subcategory[catIndex]) subcategoryCombo.addItem(s);
            subcategoryCombo.setSelectedIndex(0);
            subcatIndex = 0;
            updateIteamPanel();
        });
        subcategoryCombo.addActionListener(e -> { subcatIndex = subcategoryCombo.getSelectedIndex(); updateIteamPanel(); });

        iteamPanel = new JPanel(new GridLayout(6,5,10,10));
        purchasePanel.add(topPanel, BorderLayout.NORTH);
        purchasePanel.add(new JScrollPane(iteamPanel), BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        addButton = createBigButton("Add to Cart"); addButton.addActionListener(this); bottomPanel.add(addButton);
        clearButton = createBigButton("Clear Selection");
        clearButton.addActionListener(e -> clearCurrentSelection());
        bottomPanel.add(clearButton);
        totalLabel = new JLabel("Total Price: ₹0.00"); bottomPanel.add(totalLabel);
        purchasePanel.add(bottomPanel, BorderLayout.SOUTH);

        // CART PANEL
        cartPanel = new JPanel(new BorderLayout(10,10));
        heading = new JLabel("Verify Items in Your Cart", JLabel.CENTER); heading.setFont(new Font("SansSerif", Font.BOLD, 16));
        cartPanel.add(heading, BorderLayout.NORTH);
        cartitemPanel = new JPanel();
        cartPanel.add(new JScrollPane(cartitemPanel), BorderLayout.CENTER);
        cartbottomPanel = new JPanel(); cartPanel.add(cartbottomPanel, BorderLayout.SOUTH);

        // PAYMENT PANEL
        paymentPanel = new JPanel(new BorderLayout(15,15));
        paymentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel paymentHeading = new JLabel("Payment & Delivery Options", JLabel.CENTER); 
        paymentHeading.setFont(new Font("SansSerif", Font.BOLD, 20));
        paymentPanel.add(paymentHeading, BorderLayout.NORTH);
        
        JPanel centerContent = new JPanel(new BorderLayout(10,10));
        
        // Address Section
        JPanel addressSection = new JPanel(new BorderLayout(10,10));
        addressSection.setBorder(BorderFactory.createTitledBorder("Delivery Address"));
        addressSelectionPanel = new JPanel();
        addressSelectionPanel.setLayout(new BoxLayout(addressSelectionPanel, BoxLayout.Y_AXIS));
        addressGroup = new ButtonGroup();
        useRegisteredAddress = new JRadioButton("Use registered address");
        useNewAddress = new JRadioButton("Use different address");
        addressGroup.add(useRegisteredAddress);
        addressGroup.add(useNewAddress);
        addressSelectionPanel.add(useRegisteredAddress);
        addressSelectionPanel.add(useNewAddress);
        useRegisteredAddress.setSelected(true);
        
        newAddressArea = new JTextArea(3, 30);
        newAddressArea.setBorder(BorderFactory.createTitledBorder("New Address"));
        newAddressArea.setEnabled(false);
        
        useNewAddress.addActionListener(e -> newAddressArea.setEnabled(true));
        useRegisteredAddress.addActionListener(e -> newAddressArea.setEnabled(false));
        
        addressSection.add(addressSelectionPanel, BorderLayout.NORTH);
        addressSection.add(new JScrollPane(newAddressArea), BorderLayout.CENTER);
        centerContent.add(addressSection, BorderLayout.NORTH);
        
        // Payment Method Section
        JPanel paymentSection = new JPanel(new BorderLayout(10,10));
        paymentSection.setBorder(BorderFactory.createTitledBorder("Payment Method"));
        JPanel payModePanel = new JPanel(new GridLayout(1,2,10,10));
        payModePanel.add(new JLabel("Select Payment Mode:"));
        paymodeBox = new JComboBox<>(pmod);
        payModePanel.add(paymodeBox);
        paymentSection.add(payModePanel, BorderLayout.NORTH);
        
        JPanel dynamicPaymentArea = new JPanel(new GridLayout(0,2,12,12));
        dynamicPaymentArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        cardNameField = new JTextField(20); 
        cardNumberField = new JTextField(20); 
        cardExpField = new JTextField(5); 
        cardCVVField = new JTextField(4); 
        saveCardCheck = new JCheckBox("Save card for future purchases");
        upiIdField = new JTextField(20); 
        netBankingField = new JTextField(20); 
        ewalletField = new JTextField(20);
        paymentSection.add(dynamicPaymentArea, BorderLayout.CENTER);
        
        centerContent.add(paymentSection, BorderLayout.CENTER);
        
        // Delivery Section
        JPanel deliverySection = new JPanel(new BorderLayout(10,10));
        deliverySection.setBorder(BorderFactory.createTitledBorder("Delivery Schedule"));
        JPanel deliveryOptions = new JPanel(new GridLayout(3,1,8,8));
        nowButton = new JRadioButton("Express Delivery (Within 30 minutes)"); 
        laterButton = new JRadioButton("Standard Delivery (2-4 hours)"); 
        tomorrowButton = new JRadioButton("Next Day Delivery (Tomorrow Morning 9-12 AM)");
        deliveryGroup = new ButtonGroup(); 
        deliveryGroup.add(nowButton); deliveryGroup.add(laterButton); deliveryGroup.add(tomorrowButton);
        deliveryOptions.add(nowButton); deliveryOptions.add(laterButton); deliveryOptions.add(tomorrowButton);
        deliverySection.add(deliveryOptions, BorderLayout.CENTER);
        
        JPanel extraOptions = new JPanel();
        bagCheck = new JCheckBox("Eco-Friendly: I don't need a paper bag");
        extraOptions.add(bagCheck);
        deliverySection.add(extraOptions, BorderLayout.SOUTH);
        
        centerContent.add(deliverySection, BorderLayout.SOUTH);
        paymentPanel.add(centerContent, BorderLayout.CENTER);
        
        payment_buttonPanel = new JPanel();
        paybackButton = createBigButton("Back to Cart"); 
        paybackButton.addActionListener(e -> cardLayout.show(mainPanel, "Cart"));
        payButton = createBigButton("Proceed to Pay"); 
        payment_buttonPanel.add(paybackButton); payment_buttonPanel.add(payButton);
        paymentPanel.add(payment_buttonPanel, BorderLayout.SOUTH);

        paymodeBox.addActionListener(ev -> {
            dynamicPaymentArea.removeAll();
            String mode = (String) paymodeBox.getSelectedItem();
            if ("Card".equals(mode)) {
                dynamicPaymentArea.add(new JLabel("Cardholder Name:")); dynamicPaymentArea.add(cardNameField);
                dynamicPaymentArea.add(new JLabel("Card Number (12-19 digits):")); dynamicPaymentArea.add(cardNumberField);
                dynamicPaymentArea.add(new JLabel("Expiry Date (MM/YY):")); dynamicPaymentArea.add(cardExpField);
                dynamicPaymentArea.add(new JLabel("CVV Code (3-4 digits):")); dynamicPaymentArea.add(cardCVVField);
                dynamicPaymentArea.add(new JLabel("")); dynamicPaymentArea.add(saveCardCheck);
            } else if ("UPI".equals(mode)) {
                dynamicPaymentArea.add(new JLabel("UPI ID / VPA:")); dynamicPaymentArea.add(upiIdField);
                dynamicPaymentArea.add(new JLabel("Example: username@paytm")); dynamicPaymentArea.add(new JLabel(""));
            } else if ("Net-Banking".equals(mode)) {
                dynamicPaymentArea.add(new JLabel("Select Your Bank:")); dynamicPaymentArea.add(netBankingField);
                dynamicPaymentArea.add(new JLabel("You'll be redirected to bank portal")); dynamicPaymentArea.add(new JLabel(""));
            } else if ("E Wallet".equals(mode)) {
                dynamicPaymentArea.add(new JLabel("Wallet Account ID:")); dynamicPaymentArea.add(ewalletField);
                dynamicPaymentArea.add(new JLabel("PhonePe / Paytm / Amazon Pay")); dynamicPaymentArea.add(new JLabel(""));
            } else if ("Pay Later".equals(mode)) {
                dynamicPaymentArea.add(new JLabel("Pay on Delivery")); dynamicPaymentArea.add(new JLabel(""));
                dynamicPaymentArea.add(new JLabel("Cash or Card at doorstep")); dynamicPaymentArea.add(new JLabel(""));
            } else {
                dynamicPaymentArea.add(new JLabel("Selected: " + mode)); dynamicPaymentArea.add(new JLabel(""));
                dynamicPaymentArea.add(new JLabel("No additional details required")); dynamicPaymentArea.add(new JLabel(""));
            }
            dynamicPaymentArea.revalidate(); dynamicPaymentArea.repaint();
            applyThemeToAll();
        });

        // PAY button logic
        payButton.addActionListener(ev -> {
            try {
                if (x == 0) { JOptionPane.showMessageDialog(this, "Cart is empty. Please add items before proceeding.", "Cart Empty", JOptionPane.WARNING_MESSAGE); return; }
                
                // Get delivery address
                String deliveryAddress;
                if (useRegisteredAddress.isSelected()) {
                    deliveryAddress = adressArea.getText();
                } else {
                    deliveryAddress = newAddressArea.getText().trim();
                    if (deliveryAddress.isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Please enter delivery address.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
                
                String mode = (String) paymodeBox.getSelectedItem();
                
                if ("Card".equals(mode)) {
                    if (!isAlphaOnly(cardNameField.getText())) { JOptionPane.showMessageDialog(this, "Invalid cardholder name (letters only).", "Error", JOptionPane.ERROR_MESSAGE); return; }
                    String cn = cardNumberField.getText().replaceAll("\\s+",""); 
                    if (!cn.matches("\\d{12,19}")) { JOptionPane.showMessageDialog(this,"Card number must be 12-19 digits.","Error",JOptionPane.ERROR_MESSAGE); return; }
                    if (!cardExpField.getText().matches("(0[1-9]|1[0-2])\\/(\\d{2})")) { JOptionPane.showMessageDialog(this,"Expiry must be in MM/YY format.","Error",JOptionPane.ERROR_MESSAGE); return; }
                    if (!cardCVVField.getText().matches("\\d{3,4}")) { JOptionPane.showMessageDialog(this,"CVV must be 3-4 digits.","Error",JOptionPane.ERROR_MESSAGE); return; }
                } else if ("UPI".equals(mode) && upiIdField.getText().trim().isEmpty()) { 
                    JOptionPane.showMessageDialog(this,"Please enter your UPI ID.","Error",JOptionPane.ERROR_MESSAGE); return; 
                } else if ("Net-Banking".equals(mode) && netBankingField.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this,"Please enter bank details.","Error",JOptionPane.ERROR_MESSAGE); return;
                } else if ("E Wallet".equals(mode) && ewalletField.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this,"Please enter wallet ID.","Error",JOptionPane.ERROR_MESSAGE); return;
                }

                if (!nowButton.isSelected() && !laterButton.isSelected() && !tomorrowButton.isSelected()) {
                    JOptionPane.showMessageDialog(this, "Please select a delivery schedule.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (!validateRegistration()) return;
                
                DeliveryETA eta = chooseDeliveryETA();
                int total = computeTotalFromCart();
                String paymentInfo = buildPaymentInfoDisplay(mode);
                String orderId = generateOrderId();
                
                Order order = new Order(LocalDateTime.now(), snapshotCart(), total, paymentInfo, eta.displayText, eta.arrivalDateTime, orderId, deliveryAddress, mode);
                orders.add(order);
                updateOrdersList();
                generateBillWithInfo(paymentInfo, eta, orderId, deliveryAddress);
                
                // Reset for new order
                resetForNewOrder();
                
                cardLayout.show(mainPanel, "Bill");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Payment failed: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });

        // BILL PANEL
        billPanel = new JPanel();
        billPanel.setLayout(new BoxLayout(billPanel, BoxLayout.Y_AXIS));

        // ORDERS PANEL
        ordersPanel = new JPanel(new BorderLayout(8,8));
        JLabel ordersHeading = new JLabel("My Orders", JLabel.CENTER);
        ordersHeading.setFont(new Font("SansSerif", Font.BOLD, 18));
        ordersPanel.add(ordersHeading, BorderLayout.NORTH);
        
        ordersListModel = new DefaultListModel<>();
        ordersJList = new JList<>(ordersListModel);
        ordersJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        ordersPanel.add(new JScrollPane(ordersJList), BorderLayout.CENTER);
        JPanel ordersBtns = new JPanel();
        JButton viewOrderBtn = createBigButton("View Details");
        viewOrderBtn.addActionListener(e -> {
            int idx = ordersJList.getSelectedIndex();
            if (idx < 0) { JOptionPane.showMessageDialog(this, "Please select an order to view.", "Info", JOptionPane.INFORMATION_MESSAGE); return; }
            Order o = orders.get(idx);
            JOptionPane.showMessageDialog(this, o.detailedText(), "Order #" + o.orderId, JOptionPane.INFORMATION_MESSAGE);
        });
        JButton backFromOrders = createBigButton("Back");
        backFromOrders.addActionListener(e -> cardLayout.show(mainPanel, "Start"));
        ordersBtns.add(viewOrderBtn); ordersBtns.add(backFromOrders);
        ordersPanel.add(ordersBtns, BorderLayout.SOUTH);

        // Start countdown timer
        countdownTimer = new Timer(1000, e -> updateOrdersList());
        countdownTimer.start();

        mainPanel.add(startPanel, "Start");
        mainPanel.add(cidPanel, "Registration");
        mainPanel.add(purchasePanel, "Shopping");
        mainPanel.add(cartPanel, "Cart");
        mainPanel.add(paymentPanel, "Payment");
        mainPanel.add(billPanel, "Bill");
        mainPanel.add(ordersPanel, "Orders");

        updateIteamPanel();
        applyThemeToAll();

        setVisible(true);
        cardLayout.show(mainPanel, "Start");
    }

    private String generateOrderId() {
        LocalDateTime now = LocalDateTime.now();
        int itemCount = x;
        int qtySum = 0;
        for (int i = 0; i < x; i++) {
            if (cart[i][2] != null) qtySum += Integer.parseInt(cart[i][2]);
        }
        
        // Create unique 8-digit ID based on: time + items + qty + random
        int part1 = (now.getHour() * 100 + now.getMinute()) % 10000; // 4 digits from time
        int part2 = (itemCount * 100 + qtySum) % 100; // 2 digits from items/qty
        int part3 = rnd.nextInt(100); // 2 random digits
        
        return String.format("%04d%02d%02d", part1, part2, part3);
    }

    private void resetForNewOrder() {
        // Clear cart
        clearCart();
        
        // Reset all quantities in shopping panel
        for (int i = 0; i < 5; i++) {
            currentQty[i] = 0;
            if (qtyLabel[i] != null) qtyLabel[i].setText("0");
            if (subtotalLabel[i] != null) subtotalLabel[i].setText("₹0");
        }
        
        // Reset payment fields
        cardNameField.setText("");
        cardNumberField.setText("");
        cardExpField.setText("");
        cardCVVField.setText("");
        upiIdField.setText("");
        netBankingField.setText("");
        ewalletField.setText("");
        saveCardCheck.setSelected(false);
        
        // Reset delivery options
        deliveryGroup.clearSelection();
        bagCheck.setSelected(false);
        
        // Reset address selection
        useRegisteredAddress.setSelected(true);
        newAddressArea.setText("");
        newAddressArea.setEnabled(false);
        
        // Reset payment mode
        paymodeBox.setSelectedIndex(0);
        
        // Update displays
        totalLabel.setText("Total Price: ₹0.00");
        updateIteamPanel();
        updateCartPanel();
    }

    private void clearCurrentSelection() {
        try {
            if (catIndex >= 0 && subcatIndex >= 0) {
                for (int i = 0; i < 5; i++) {
                    String visibleName = DMartDatabase.item[catIndex][subcatIndex][i];
                    boolean found = false;
                    for (int j = 0; j < x; j++) {
                        if (cart[j][0] != null && cart[j][0].equals(visibleName)) { found = true; break; }
                    }
                    if (!found) {
                        currentQty[i] = 0;
                        if (qtyLabel[i] != null) qtyLabel[i].setText("0");
                        if (subtotalLabel[i] != null) subtotalLabel[i].setText("₹0");
                    }
                }
                updateShoppingTotal();
            }
        } catch (Exception ex) { 
            JOptionPane.showMessageDialog(this, "Clear failed: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE); 
        }
    }

    private static class DeliveryETA {
        String displayText;
        LocalDateTime arrivalDateTime;
        DeliveryETA(String d, LocalDateTime dt) { displayText = d; arrivalDateTime = dt; }
    }

    private DeliveryETA chooseDeliveryETA() {
        if (nowButton.isSelected()) {
            int mins = rnd.nextInt(21) + 10;
            LocalDateTime arrival = LocalDateTime.now().plusMinutes(mins);
            return new DeliveryETA(formatMinutesToTime(mins), arrival);
        } else if (laterButton.isSelected()) {
            int mins = rnd.nextInt(121) + 120;
            LocalDateTime arrival = LocalDateTime.now().plusMinutes(mins);
            return new DeliveryETA(formatMinutesToTime(mins), arrival);
        } else {
            LocalDateTime tomorrow = LocalDate.now().plusDays(1).atTime(rnd.nextInt(3) + 9, rnd.nextInt(60));
            return new DeliveryETA("Tomorrow " + timeOnlyFmt.format(tomorrow), tomorrow);
        }
    }

    private String formatMinutesToTime(int totalMins) {
        int hrs = totalMins / 60;
        int mins = totalMins % 60;
        if (hrs == 0) return mins + " min";
        if (mins == 0) return hrs + " hr";
        return hrs + " hr " + mins + " min";
    }

    private String getRemainingTime(LocalDateTime arrival) {
        LocalDateTime now = LocalDateTime.now();
        if (now.isAfter(arrival)) return "Delivered";
        
        Duration duration = Duration.between(now, arrival);
        long totalMins = duration.toMinutes();
        
        if (totalMins <= 0) return "Delivered";
        
        long hrs = totalMins / 60;
        long mins = totalMins % 60;
        
        if (hrs == 0) return mins + " min remaining";
        if (mins == 0) return hrs + " hr remaining";
        return hrs + " hr " + mins + " min remaining";
    }

    private void updateOrdersList() {
        ordersListModel.clear();
        for (Order o : orders) {
            ordersListModel.addElement(o.summaryLineWithCountdown());
        }
    }

    private List<String> snapshotCart() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < x; i++) {
            if (cart[i][0] == null) continue;
            list.add(cart[i][0] + " × " + cart[i][2] + " = ₹" + AllSum[i]);
        }
        return list;
    }

    private class Order {
        LocalDateTime timestamp;
        List<String> items;
        int total;
        String paymentInfo;
        String deliveryText;
        LocalDateTime arrivalTime;
        String orderId;
        String deliveryAddress;
        String paymentMode;
        
        Order(LocalDateTime t, List<String> items, int total, String paymentInfo, String deliveryText, 
              LocalDateTime arrival, String orderId, String address, String mode) {
            this.timestamp = t; 
            this.items = items; 
            this.total = total; 
            this.paymentInfo = paymentInfo;
            this.deliveryText = deliveryText; 
            this.arrivalTime = arrival;
            this.orderId = orderId;
            this.deliveryAddress = address;
            this.paymentMode = mode;
        }
        
        String summaryLineWithCountdown() {
            String timeStr = getRemainingTime(arrivalTime);
            String status = timeStr.equals("Delivered") ? "Paid" : "Due";
            String date = dateFmt.format(timestamp);
            return date + " | " + timeOnlyFmt.format(timestamp) + " | #" + orderId + 
                   " | " + status + " ₹" + total + " | " + timeStr;
        }
        
        String detailedText() {
            StringBuilder sb = new StringBuilder();
            sb.append("Order ID: #").append(orderId).append("\n");
            sb.append("Order Date: ").append(dateFmt.format(timestamp)).append("\n");
            sb.append("Order Time: ").append(timeOnlyFmt.format(timestamp)).append("\n");
            sb.append("Status: ").append(getRemainingTime(arrivalTime)).append("\n");
            sb.append("Delivery ETA: ").append(deliveryText).append("\n");
            sb.append("Delivery Address: ").append(deliveryAddress).append("\n\n");
            sb.append("Items Ordered:\n");
            for (String s : items) sb.append(" • ").append(s).append("\n");
            sb.append("\nTotal Amount: ₹").append(total).append("\n");
            sb.append("Payment Mode: ").append(paymentMode).append("\n");
            sb.append("Payment Details: ").append(paymentInfo).append("\n");
            return sb.toString();
        }
    }

    private String buildPaymentInfoDisplay(String mode) {
        if ("Card".equals(mode)) {
            String cn = cardNumberField.getText().replaceAll("\\s+","");
            String last4 = cn.length() >= 4 ? cn.substring(cn.length()-4) : cn;
            return "Card ending in " + last4;
        } else if ("UPI".equals(mode)) {
            return maskMiddle(upiIdField.getText());
        } else if ("Net-Banking".equals(mode)) {
            return maskMiddle(netBankingField.getText());
        } else if ("E Wallet".equals(mode)) {
            return maskMiddle(ewalletField.getText());
        } else return mode;
    }

    private String maskMiddle(String s) {
        if (s == null) return "";
        s = s.trim();
        if (s.length() <= 4) return s;
        if (s.length() <= 8) return "****" + s.substring(s.length()-4);
        return s.substring(0,2) + "****" + s.substring(s.length()-2);
    }

    private void generateBillWithInfo(String paymentInfo, DeliveryETA eta, String orderId, String deliveryAddress) {
        billPanel.removeAll();
        billPanel.setLayout(new BoxLayout(billPanel, BoxLayout.Y_AXIS));
        billPanel.add(Box.createVerticalStrut(10));
        
        JLabel successLabel = new JLabel("✓ Order Placed Successfully!", JLabel.CENTER);
        successLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        successLabel.setForeground(new Color(0, 150, 0));
        billPanel.add(successLabel);
        billPanel.add(Box.createVerticalStrut(5));
        
        JLabel orderIdLabel = new JLabel("Order ID: #" + orderId, JLabel.CENTER);
        orderIdLabel.setFont(new Font("Monospaced", Font.BOLD, 16));
        billPanel.add(orderIdLabel);
        billPanel.add(Box.createVerticalStrut(15));
        
        billPanel.add(new JLabel("Order Time: " + timeOnlyFmt.format(LocalTime.now())));
        billPanel.add(new JLabel("Customer: " + cfnameField.getText() + " " + clnameField.getText()));
        billPanel.add(new JLabel("Phone: " + PnoField.getText()));
        billPanel.add(new JLabel("Email: " + emailField.getText()));
        billPanel.add(new JLabel("Delivery Address: " + deliveryAddress));
        billPanel.add(Box.createVerticalStrut(10));
        
        JLabel etaLabel = new JLabel("Estimated Delivery: " + eta.displayText);
        etaLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        billPanel.add(etaLabel);
        billPanel.add(new JLabel("Payment: " + paymentInfo));
        billPanel.add(Box.createVerticalStrut(10));
        
        JLabel itemsHeading = new JLabel("Order Items:");
        itemsHeading.setFont(new Font("SansSerif", Font.BOLD, 14));
        billPanel.add(itemsHeading);
        
        int total = computeTotalFromCart();
        for (int i = 0; i < x; i++) {
            if (cart[i][0] == null) continue;
            billPanel.add(new JLabel("  " + (i+1) + ". " + cart[i][0] + " × " + cart[i][2] + " = ₹" + AllSum[i]));
        }
        billPanel.add(Box.createVerticalStrut(10));
        
        JLabel totalLabel = new JLabel("Total Amount: ₹" + total);
        totalLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        billPanel.add(totalLabel);
        billPanel.add(Box.createVerticalStrut(20));
        
        JPanel bottom = new JPanel();
        JButton printBtn = createBigButton("Print Bill");
        printBtn.addActionListener(e -> printBill(orderId));
        bottom.add(printBtn);
        
        JButton viewOrdersBtn = createBigButton("Track Order");
        viewOrdersBtn.addActionListener(e -> cardLayout.show(mainPanel, "Orders"));
        bottom.add(viewOrdersBtn);
        
        JButton doneBtn = createBigButton("Done");
        doneBtn.addActionListener(e -> cardLayout.show(mainPanel, "Start"));
        bottom.add(doneBtn);
        billPanel.add(bottom);
        
        applyThemeToAll();
        billPanel.revalidate(); billPanel.repaint();
    }

    private void printBill(String orderId) {
        final StringBuilder sb = new StringBuilder();
        sb.append("DMart Shopping Center\n=====================\n");
        sb.append("Order ID: #").append(orderId).append("\n");
        sb.append("Customer: ").append(cfnameField.getText()).append(" ").append(clnameField.getText()).append("\n");
        sb.append("Phone: ").append(PnoField.getText()).append("\n");
        sb.append("Address: ").append(adressArea.getText()).append("\n\n");
        sb.append("Items:\n");
        for (int i = 0; i < x; i++) {
            if (cart[i][0] == null) continue;
            sb.append(String.format("%d. %s x %s = ₹%d\n", i+1, cart[i][0], cart[i][2], AllSum[i]));
        }
        sb.append("\nTotal: ₹").append(TotalSum).append("\n=====================\nThank you!");
        final String printText = sb.toString();
        PrinterJob job = PrinterJob.getPrinterJob();
        job.setPrintable((g, pf, pageIndex) -> {
            if (pageIndex > 0) return Printable.NO_SUCH_PAGE;
            Graphics2D g2 = (Graphics2D) g;
            g2.translate(pf.getImageableX(), pf.getImageableY());
            int y = 0;
            for (String line : printText.split("\n")) { y += 15; g2.drawString(line, 0, y); }
            return Printable.PAGE_EXISTS;
        });
        boolean ok = job.printDialog();
        if (ok) {
            try { job.print(); } catch (PrinterException ex) { JOptionPane.showMessageDialog(this, "Print failed: " + ex.getMessage()); }
        }
    }

    private void clearCart() {
        for (int i = 0; i < x; i++) { cart[i][0] = null; cart[i][1] = null; cart[i][2] = null; AllSum[i] = 0; }
        x = 0; TotalSum = 0;
    }

    private void removeCartIndex(int idx) {
        if (idx < 0 || idx >= x) return;
        for (int k = idx; k < x - 1; k++) {
            cart[k][0] = cart[k+1][0]; cart[k][1] = cart[k+1][1]; cart[k][2] = cart[k+1][2]; AllSum[k]=AllSum[k+1];
        }
        cart[x-1][0] = null; cart[x-1][1] = null; cart[x-1][2] = null; AllSum[x-1]=0; x--;
    }

    public void updateCartPanel()
    {
        cartitemPanel.removeAll();
        cartitemPanel.setLayout(new GridLayout(0,1,6,6));
        JPanel header = new JPanel(new GridLayout(1,5,10,10));
        header.add(new JLabel("Item")); header.add(new JLabel("Price (₹)")); header.add(new JLabel("Quantity")); header.add(new JLabel("Total")); header.add(new JLabel("Actions"));
        cartitemPanel.add(header);
        TotalSum = 0;
        for (int i = 0; i < x; i++) {
            if (cart[i][0] == null) continue;
            String name = cart[i][0]; int price = (int) Double.parseDouble(cart[i][1]); int q = Integer.parseInt(cart[i][2]);
            AllSum[i] = price * q; TotalSum += AllSum[i];
            JPanel row = new JPanel(new GridLayout(1,5,8,8));
            row.add(new JLabel(name)); row.add(new JLabel(String.valueOf(price))); row.add(new JLabel(String.valueOf(q))); row.add(new JLabel("₹" + AllSum[i]));
            JPanel actions = new JPanel();
            JButton minus = new JButton("-"); JButton plus = new JButton("+"); JButton remove = new JButton("Remove");
            minus.setPreferredSize(new Dimension(40,30)); plus.setPreferredSize(new Dimension(40,30)); remove.setPreferredSize(new Dimension(80,30));
            final int idx = i;
            minus.addActionListener(ev -> { int cur = Integer.parseInt(cart[idx][2]); if (cur > 1) cart[idx][2]=String.valueOf(cur-1); else removeCartIndex(idx); computeTotalFromCart(); updateCartPanel(); updateIteamPanel(); });
            plus.addActionListener(ev -> { int cur = Integer.parseInt(cart[idx][2]); if (cur < 10) cart[idx][2]=String.valueOf(cur+1); computeTotalFromCart(); updateCartPanel(); updateIteamPanel(); });
            remove.addActionListener(ev -> { removeCartIndex(idx); computeTotalFromCart(); updateCartPanel(); updateIteamPanel(); });
            actions.add(minus); actions.add(plus); actions.add(remove);
            row.add(actions); cartitemPanel.add(row);
        }
        cartbottomPanel.removeAll();
        cartbottomPanel.setLayout(new BorderLayout(10,10));
        cartbottomPanel.add(new JLabel("Total: ₹" + TotalSum), BorderLayout.NORTH);
        JPanel bottomBtns = new JPanel();
        backButton = createBigButton("Back"); backButton.addActionListener(e -> cardLayout.show(mainPanel, "Shopping")); bottomBtns.add(backButton);
        paymentButton = createBigButton("Continue To Payment"); paymentButton.addActionListener(e -> { paymodeBox.setSelectedIndex(0); paymodeBox.getActionListeners()[0].actionPerformed(null); cardLayout.show(mainPanel, "Payment"); }); bottomBtns.add(paymentButton);
        cartbottomPanel.add(bottomBtns, BorderLayout.SOUTH);
        applyThemeToAll(); cartPanel.revalidate(); cartPanel.repaint();
        totalLabel.setText("Total Price: ₹" + TotalSum);
    }

    public void updateIteamPanel()
    {
        iteamPanel.removeAll(); 
        iteamPanel.setLayout(new GridLayout(6,5,10,10));
        iteamPanel.add(new JLabel("Item")); 
        iteamPanel.add(new JLabel("Price (₹)")); 
        iteamPanel.add(new JLabel("Quantity"));
        iteamPanel.add(new JLabel("Subtotal"));
        iteamPanel.add(new JLabel("Actions"));
        
        if (catIndex >= 0 && subcatIndex >= 0) {
            for (int i = 0; i < 5; i++) {
                String itemName = DMartDatabase.item[catIndex][subcatIndex][i];
                int itemPrice = DMartDatabase.price[catIndex][subcatIndex][i];
                
                iteamPanel.add(new JLabel(itemName));
                iteamPanel.add(new JLabel("₹" + itemPrice));
                
                // Check if item is in cart and set quantity
                boolean foundInCart = false;
                for (int j = 0; j < x; j++) {
                    if (cart[j][0] != null && cart[j][0].equals(itemName)) {
                        currentQty[i] = Integer.parseInt(cart[j][2]);
                        foundInCart = true; 
                        break;
                    }
                }
                if (!foundInCart) currentQty[i] = 0;
                
                qtyLabel[i] = new JLabel(String.valueOf(currentQty[i]), JLabel.CENTER);
                qtyLabel[i].setFont(new Font("SansSerif", Font.BOLD, 14));
                iteamPanel.add(qtyLabel[i]);
                
                subtotalLabel[i] = new JLabel("₹" + (currentQty[i] * itemPrice));
                subtotalLabel[i].setFont(new Font("SansSerif", Font.BOLD, 14));
                iteamPanel.add(subtotalLabel[i]);
                
                // Action buttons
                JPanel actionPanel = new JPanel();
                final int index = i;
                minusBtn[i] = new JButton("-");
                minusBtn[i].setPreferredSize(new Dimension(45, 30));
                minusBtn[i].addActionListener(e -> {
                    if (currentQty[index] > 0) {
                        currentQty[index]--;
                        qtyLabel[index].setText(String.valueOf(currentQty[index]));
                        subtotalLabel[index].setText("₹" + (currentQty[index] * itemPrice));
                        updateShoppingTotal();
                    }
                });
                
                plusBtn[i] = new JButton("+");
                plusBtn[i].setPreferredSize(new Dimension(45, 30));
                plusBtn[i].addActionListener(e -> {
                    if (currentQty[index] < 10) {
                        currentQty[index]++;
                        qtyLabel[index].setText(String.valueOf(currentQty[index]));
                        subtotalLabel[index].setText("₹" + (currentQty[index] * itemPrice));
                        updateShoppingTotal();
                    }
                });
                
                actionPanel.add(minusBtn[i]);
                actionPanel.add(plusBtn[i]);
                iteamPanel.add(actionPanel);
            }
        }
        applyThemeToAll(); 
        revalidate(); 
        repaint();
    }

    private void updateShoppingTotal() {
        int total = 0;
        if (catIndex >= 0 && subcatIndex >= 0) {
            for (int i = 0; i < 5; i++) {
                int itemPrice = DMartDatabase.price[catIndex][subcatIndex][i];
                total += currentQty[i] * itemPrice;
            }
        }
        totalLabel.setText("Total Price: ₹" + total);
    }

    private int computeTotalFromCart() {
        int total = 0;
        for (int i = 0; i < x; i++) {
            if (cart[i][0] == null) continue;
            int price = (int) Double.parseDouble(cart[i][1]);
            int q = Integer.parseInt(cart[i][2]);
            AllSum[i] = price * q;
            total += AllSum[i];
        }
        TotalSum = total;
        return total;
    }

    private boolean validateRegistration() {
        try {
            // First name validation
            String firstName = cfnameField.getText().trim();
            if (firstName.isEmpty() || !isAlphaOnly(firstName)) { 
                JOptionPane.showMessageDialog(this, "First name must contain only letters and spaces.", "Invalid Input", JOptionPane.ERROR_MESSAGE); 
                return false; 
            }
            
            // Last name validation
            String lastName = clnameField.getText().trim();
            if (lastName.isEmpty() || !isAlphaOnly(lastName)) { 
                JOptionPane.showMessageDialog(this, "Last name must contain only letters and spaces.", "Invalid Input", JOptionPane.ERROR_MESSAGE); 
                return false; 
            }
            
            // Age validation
            String ageStr = ageField.getText().trim();
            if (ageStr.isEmpty() || !ageStr.matches("\\d+")) {
                JOptionPane.showMessageDialog(this, "Age must be a number.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            int age = Integer.parseInt(ageStr);
            if (age < 12) {
                JOptionPane.showMessageDialog(this, "Too young to operate this app! Come back when you're older.", "Age Restriction", JOptionPane.WARNING_MESSAGE);
                return false;
            }
            if (age > 110) {
                JOptionPane.showMessageDialog(this, "Too old! Just die ;)", "Age Limit Exceeded", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            if (age < 12 || age > 110) {
                JOptionPane.showMessageDialog(this, "Age must be between 12 and 110.", "Invalid Age", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            
            // Email validation
            if (!isValidEmail(emailField.getText())) { 
                JOptionPane.showMessageDialog(this, "Please enter a valid email address (e.g., user@example.com).", "Invalid Email", JOptionPane.ERROR_MESSAGE); 
                return false; 
            }
            
            // Phone number validation - exactly 10 digits
            String phone = PnoField.getText().trim();
            if (!phone.matches("\\d{10}")) { 
                JOptionPane.showMessageDialog(this, "Phone number must be exactly 10 digits.", "Invalid Phone", JOptionPane.ERROR_MESSAGE); 
                return false; 
            }
            
            // Gender validation
            if (!maleButton.isSelected() && !femaleButton.isSelected() && !otherButton.isSelected()) {
                JOptionPane.showMessageDialog(this, "Please select your gender.", "Missing Information", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            
            // Pincode validation - exactly 6 digits
            String pincode = pincodeField.getText().trim();
            if (!pincode.matches("\\d{6}")) {
                JOptionPane.showMessageDialog(this, "Pin code must be exactly 6 digits.", "Invalid Pincode", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            
            // Address validation
            if (adressArea.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter your complete address.", "Missing Address", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            
            return true;
        } catch (NumberFormatException ex) { 
            JOptionPane.showMessageDialog(this, "Invalid number format in age field.", "Input Error", JOptionPane.ERROR_MESSAGE); 
            return false; 
        } catch (Exception ex) { 
            JOptionPane.showMessageDialog(this, "Registration validation failed: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE); 
            return false; 
        }
    }

    private boolean isAlphaOnly(String s) { 
        return s != null && s.trim().matches("[A-Za-z ]+"); 
    }
    
    private boolean isValidEmail(String email) { 
        if (email == null || email.trim().isEmpty()) return false; 
        String regex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$"; 
        return Pattern.compile(regex).matcher(email.trim()).matches(); 
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton) {
            try {
                if (catIndex >= 0 && subcatIndex >= 0) {
                    int addedCount = 0;
                    for (int i = 0; i < 5; i++) {
                        int q = currentQty[i];
                        if (q > 0) {
                            String itemName = DMartDatabase.item[catIndex][subcatIndex][i];
                            int price = DMartDatabase.price[catIndex][subcatIndex][i];
                            boolean found = false;
                            for (int j = 0; j < x; j++) {
                                if (cart[j][0] != null && cart[j][0].equals(itemName)) { 
                                    cart[j][2] = String.valueOf(q); 
                                    found = true; 
                                    break; 
                                }
                            }
                            if (!found) { 
                                cart[x][0]=itemName; 
                                cart[x][1]=String.valueOf(price); 
                                cart[x][2]=String.valueOf(q); 
                                x++; 
                            }
                            addedCount++;
                        }
                    }
                    if (addedCount > 0) {
                        int total = computeTotalFromCart(); 
                        totalLabel.setText("Total Price: ₹" + total);
                        updateIteamPanel(); 
                        updateCartPanel();
                        JOptionPane.showMessageDialog(this, addedCount + " item(s) added to cart successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(this, "Please select at least one item with quantity > 0", "No Items Selected", JOptionPane.WARNING_MESSAGE);
                    }
                }
            } catch (Exception ex) { 
                JOptionPane.showMessageDialog(this, "Add to cart failed: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE); 
            }
        } else if (e.getSource() == cartButton) {
            updateCartPanel(); 
            cardLayout.show(mainPanel, "Cart");
        } else if (e.getSource() == startButton) {
            if (isRegistered) cardLayout.show(mainPanel, "Shopping"); 
            else cardLayout.show(mainPanel, "Registration");
        } else if (e.getSource() == regesterButton) {
            try {
                if (!validateRegistration()) return;
                if (!termsCheck.isSelected()) { 
                    JOptionPane.showMessageDialog(this, "Please accept Terms & Conditions to continue.", "Terms Required", JOptionPane.WARNING_MESSAGE); 
                    return; 
                }
                isRegistered = true;
                regesterButton.setText("Update Details");
                
                // Save registered address
                savedAddresses.clear();
                savedAddresses.add(adressArea.getText());
                
                JOptionPane.showMessageDialog(this, "Registration successful! Welcome to DMart.", "Success", JOptionPane.INFORMATION_MESSAGE);
                cardLayout.show(mainPanel, "Shopping");
            } catch (Exception ex) { 
                JOptionPane.showMessageDialog(this, "Registration failed: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE); 
            }
        }
    }

    private void createTopBar() {
        topBar = new JPanel(new BorderLayout());
        topBar.setOpaque(true);
        JLabel appTitle = new JLabel("  DMart Shopping Center");
        appTitle.setFont(new Font("SansSerif", Font.BOLD, 14));
        topBar.add(appTitle, BorderLayout.WEST);

        JPanel right = new JPanel(new BorderLayout());
        JButton ordersBtn = new JButton("My Orders");
        ordersBtn.setPreferredSize(new Dimension(100,30));
        ordersBtn.addActionListener(e -> cardLayout.show(mainPanel, "Orders"));
        right.add(ordersBtn, BorderLayout.WEST);

        darkModeToggle = new JToggleButton("Dark Mode");
        darkModeToggle.setPreferredSize(new Dimension(110, 30));
        darkModeToggle.addActionListener(e -> { 
            darkMode = darkModeToggle.isSelected(); 
            applyThemeToAll(); 
            parentPanel.revalidate(); 
            parentPanel.repaint(); 
        });
        right.add(darkModeToggle, BorderLayout.EAST);
        right.setOpaque(false);
        topBar.add(right, BorderLayout.EAST);
    }

    private JButton createBigButton(String text) {
        JButton b = new JButton(text); 
        b.setPreferredSize(BTN_BIG); 
        b.setFont(BTN_FONT); 
        b.setFocusPainted(false); 
        return b;
    }

    private void applyThemeToAll() {
        Color panelBg = darkMode ? new Color(34,34,34) : new Color(245,245,245);
        Color compBg = darkMode ? new Color(60,63,65) : new Color(225,225,225);
        Color fieldBg = darkMode ? new Color(50,50,50) : Color.WHITE;
        Color textFg = darkMode ? Color.WHITE : Color.BLACK;
        Color borderColor = darkMode ? new Color(80,80,80) : new Color(180,180,180);
        
        parentPanel.setBackground(panelBg); 
        topBar.setBackground(panelBg); 
        mainPanel.setBackground(panelBg);
        
        applyThemeRecursive(parentPanel, panelBg, compBg, fieldBg, textFg, borderColor);
        totalLabel.setForeground(textFg);
        
        // Update quantity labels in shopping panel
        for (int i = 0; i < 5; i++) {
            if (qtyLabel[i] != null) qtyLabel[i].setForeground(textFg);
            if (subtotalLabel[i] != null) subtotalLabel[i].setForeground(textFg);
        }
    }

    private void applyThemeRecursive(Component comp, Color panelBg, Color compBg, Color fieldBg, Color textFg, Color borderColor) {
        if (comp == null) return;
        
        if (comp instanceof JPanel) { 
            JPanel p=(JPanel)comp; 
            p.setOpaque(true); 
            p.setBackground(panelBg); 
        }
        if (comp instanceof JLabel) {
            ((JLabel)comp).setForeground(textFg);
        }
        if (comp instanceof JButton) { 
            JButton b=(JButton)comp; 
            b.setBackground(compBg); 
            b.setForeground(textFg); 
            b.setBorder(BorderFactory.createLineBorder(borderColor)); 
            b.setOpaque(true); 
        }
        if (comp instanceof JTextField) { 
            JTextField tf=(JTextField)comp; 
            tf.setBackground(fieldBg); 
            tf.setForeground(textFg); 
            tf.setBorder(BorderFactory.createLineBorder(borderColor)); 
            tf.setOpaque(true); 
        }
        if (comp instanceof JTextArea) { 
            JTextArea ta=(JTextArea)comp; 
            ta.setBackground(fieldBg); 
            ta.setForeground(textFg); 
            ta.setBorder(BorderFactory.createLineBorder(borderColor)); 
            ta.setOpaque(true); 
        }
        if (comp instanceof JComboBox) {
            JComboBox<?> cb = (JComboBox<?>)comp;
            cb.setBackground(compBg);
            cb.setForeground(textFg);
            cb.setBorder(BorderFactory.createLineBorder(borderColor));
        }
        if (comp instanceof JRadioButton) {
            JRadioButton rb = (JRadioButton)comp;
            rb.setBackground(panelBg);
            rb.setForeground(textFg);
        }
        if (comp instanceof JCheckBox) {
            JCheckBox cb = (JCheckBox)comp;
            cb.setBackground(panelBg);
            cb.setForeground(textFg);
        }
        if (comp instanceof JToggleButton) {
            JToggleButton tb = (JToggleButton)comp;
            tb.setBackground(compBg);
            tb.setForeground(textFg);
            tb.setBorder(BorderFactory.createLineBorder(borderColor));
        }
        if (comp instanceof JList) {
            JList<?> list = (JList<?>)comp;
            list.setBackground(fieldBg);
            list.setForeground(textFg);
            list.setBorder(BorderFactory.createLineBorder(borderColor));
        }
        if (comp instanceof JScrollPane) {
            JScrollPane sp = (JScrollPane)comp;
            sp.getViewport().setBackground(panelBg);
            sp.setBorder(BorderFactory.createLineBorder(borderColor));
        }
        
        if (comp instanceof JComponent) {
            Component[] children = ((JComponent)comp).getComponents();
            for (Component c : children) {
                applyThemeRecursive(c, panelBg, compBg, fieldBg, textFg, borderColor);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new DMart());
    }
}