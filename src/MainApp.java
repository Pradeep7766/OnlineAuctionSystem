import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class MainApp {
    public static void main(String[] args) {
        new MainMenu();
    }
}

class MainMenu extends JFrame {
    public MainMenu() {
        setTitle("Online Auction System");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(5000, 5000);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;

        JLabel welcomeLabel = new JLabel("Welcome to Online Auction System");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridwidth = 3;
        add(welcomeLabel, gbc);

        JButton registerButton = createStyledButton("Register");
        JButton loginButton = createStyledButton("Login");

        gbc.gridwidth = 1;
        gbc.gridy = 1;
        gbc.gridx = 0;
        add(registerButton, gbc);

        gbc.gridx = 1;
        add(loginButton, gbc);

        registerButton.addActionListener(e -> {
            dispose();
            new RegistrationPage();
        });

        loginButton.addActionListener(e -> {
            dispose();
            new LoginPage();
        });

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setBackground(new Color(34, 167, 240));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(120, 40));
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        return button;
    }
}

class RegistrationPage extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JComboBox<String> roleComboBox;
    private static final String USERS_FILE = "users.txt";

    public RegistrationPage() {
        setTitle("User Registration");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(5000, 5000);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;

        JLabel usernameLabel = new JLabel("Username:");
        add(usernameLabel, gbc);

        gbc.gridy++;
        usernameField = new JTextField(20);
        add(usernameField, gbc);

        gbc.gridy++;
        JLabel passwordLabel = new JLabel("Password:");
        add(passwordLabel, gbc);

        gbc.gridy++;
        passwordField = new JPasswordField(20);
        add(passwordField, gbc);

        gbc.gridy++;
        JLabel roleLabel = new JLabel("Role:");
        add(roleLabel, gbc);

        gbc.gridy++;
        roleComboBox = new JComboBox<>(new String[]{"Seller", "Bidder"});
        add(roleComboBox, gbc);

        gbc.gridy++;
        JButton registerButton = createStyledButton("Register");
        add(registerButton, gbc);

        registerButton.addActionListener(new RegisterActionListener());

        gbc.gridy++;
        addBackButton(gbc);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private class RegisterActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();
            String role = (String) roleComboBox.getSelectedItem();

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(RegistrationPage.this, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (isUsernameTaken(username)) {
                JOptionPane.showMessageDialog(RegistrationPage.this, "Username already exists. Please choose a different one.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            saveUserToFile(username, password, role);
            JOptionPane.showMessageDialog(RegistrationPage.this, "Registration successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
            dispose();
            new MainMenu();
        }

        private boolean isUsernameTaken(String username) {
            try (BufferedReader reader = new BufferedReader(new FileReader(USERS_FILE))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] userDetails = line.split("\\|");
                    if (userDetails.length > 0 && userDetails[0].equals(username)) {
                        return true;
                    }
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            return false;
        }

        private void saveUserToFile(String username, String password, String role) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(USERS_FILE, true))) {
                writer.write(username + "|" + password + "|" + role);
                writer.newLine();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void addBackButton(GridBagConstraints gbc) {
        JButton backButton = createStyledButton("Back");
        backButton.addActionListener(e -> {
            dispose();
            new MainMenu();
        });
        add(backButton, gbc);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setBackground(new Color(34, 167, 240));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(120, 40));
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        return button;
    }
}

class LoginPage extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private static final String USERS_FILE = "users.txt";

    public LoginPage() {
        setTitle("User Login");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(5000, 5000);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;

        JLabel usernameLabel = new JLabel("Username:");
        add(usernameLabel, gbc);

        gbc.gridy++;
        usernameField = new JTextField(20);
        add(usernameField, gbc);

        gbc.gridy++;
        JLabel passwordLabel = new JLabel("Password:");
        add(passwordLabel, gbc);

        gbc.gridy++;
        passwordField = new JPasswordField(20);
        add(passwordField, gbc);

        gbc.gridy++;
        JButton loginButton = createStyledButton("Login");
        add(loginButton, gbc);

        loginButton.addActionListener(new LoginActionListener());

        gbc.gridy++;
        addBackButton(gbc);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private class LoginActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(LoginPage.this, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (validateUser(username, password)) {
                JOptionPane.showMessageDialog(LoginPage.this, "Login successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                dispose();
                navigateToRolePage(username);
            } else {
                JOptionPane.showMessageDialog(LoginPage.this, "Invalid username or password.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        private boolean validateUser(String username, String password) {
            try (BufferedReader reader = new BufferedReader(new FileReader(USERS_FILE))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] userDetails = line.split("\\|");
                    if (userDetails.length == 3 && userDetails[0].equals(username) && userDetails[1].equals(password)) {
                        return true;
                    }
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            return false;
        }

        private void navigateToRolePage(String username) {
            try (BufferedReader reader = new BufferedReader(new FileReader(USERS_FILE))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] userDetails = line.split("\\|");
                    if (userDetails.length == 3 && userDetails[0].equals(username)) {
                        String role = userDetails[2];
                        if (role.equals("Seller")) {
                            new SellerPage();
                        } else if (role.equals("Bidder")) {
                            new BidderPage();
                        }
                    }
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void addBackButton(GridBagConstraints gbc) {
        JButton backButton = createStyledButton("Back");
        backButton.addActionListener(e -> {
            dispose();
            new MainMenu();
        });
        add(backButton, gbc);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setBackground(new Color(34, 167, 240));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(120, 40));
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        return button;
    }
}


class SellerPage extends JFrame {
    private JTextField itemNameField;
    private JTextField descriptionField;
    private JTextField priceField;
    private JTextField auctionDurationField;
    private JButton listItemButton;

    public SellerPage() {
        setTitle("Seller Page");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(5000, 5000);
        setLayout(new GridBagLayout());
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Item Name
        JLabel itemNameLabel = new JLabel("Item Name:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(itemNameLabel, gbc);

        itemNameField = new JTextField(20);
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(itemNameField, gbc);

        // Description
        JLabel descriptionLabel = new JLabel("Description:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(descriptionLabel, gbc);

        descriptionField = new JTextField(20);
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(descriptionField, gbc);

        // Price
        JLabel priceLabel = new JLabel("Starting Price:");
        gbc.gridx = 0;
        gbc.gridy = 4;
        add(priceLabel, gbc);

        priceField = new JTextField(20);
        gbc.gridx = 0;
        gbc.gridy = 5;
        add(priceField, gbc);

        // Auction Duration
        JLabel auctionDurationLabel = new JLabel("Auction Duration (in minutes):");
        gbc.gridx = 0;
        gbc.gridy = 6;
        add(auctionDurationLabel, gbc);

        auctionDurationField = new JTextField(20);
        gbc.gridx = 0;
        gbc.gridy = 7;
        add(auctionDurationField, gbc);

        // List Item Button
        listItemButton = new JButton("List Item");
        gbc.gridx = 0;
        gbc.gridy = 8;
        add(listItemButton, gbc);

        listItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listItem();
            }
        });

        // Back Button (adjusted to appear below the List Item button)
        gbc.gridy = 9; // Set gridY to position below listItemButton
        addBackButton(gbc);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void listItem() {
        String itemName = itemNameField.getText().trim();
        String description = descriptionField.getText().trim();
        String price = priceField.getText().trim();
        String auctionDuration = auctionDurationField.getText().trim();

        if (itemName.isEmpty() || description.isEmpty() || price.isEmpty() || auctionDuration.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all the fields.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            double startingPrice = Double.parseDouble(price);
            int duration = Integer.parseInt(auctionDuration);

            saveItemToFile(itemName, description, startingPrice, duration);
            JOptionPane.showMessageDialog(this, "Item listed successfully!");

            clearFields();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid input for price or duration.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error saving item to file.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void saveItemToFile(String itemName, String description, double price, int duration) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter("items.txt", true))) {
            writer.println(itemName + "," + description + "," + price + "," + duration);
        }
    }

    private void clearFields() {
        itemNameField.setText("");
        descriptionField.setText("");
        priceField.setText("");
        auctionDurationField.setText("");
    }

    private void addBackButton(GridBagConstraints gbc) {
        JButton backButton = createStyledButton("Back");
        backButton.addActionListener(e -> {
            dispose();
            new MainMenu();
        });
        add(backButton, gbc);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setBackground(new Color(34, 167, 240));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(120, 40));
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        return button;
    }
}



class BidderPage extends JFrame {
    private static final String ITEMS_FILE = "items.txt";
    private static final String BIDS_FILE = "bids.txt"; // File to store bids

    public BidderPage() {
        setTitle("View Items and Place Bid");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(5000, 5000);
        setLayout(new BorderLayout());

        JPanel itemPanel = new JPanel();
        itemPanel.setLayout(new BoxLayout(itemPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(itemPanel);

        loadItems(itemPanel);

        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.PLAIN, 16));
        backButton.setBackground(new Color(34, 167, 240)); // Red-Orange color
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        backButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        backButton.addActionListener(e -> {
            dispose();
            new MainMenu();
        });

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.add(backButton);

        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void loadItems(JPanel itemPanel) {
        try (BufferedReader reader = new BufferedReader(new FileReader(ITEMS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] itemDetails = line.split(",");
                if (itemDetails.length == 4) {
                    String itemName = itemDetails[0];
                    String itemDescription = itemDetails[1];
                    String itemPrice = itemDetails[2];
                    String auctionDuration = itemDetails[3];

                    JPanel itemPanelDetails = new JPanel();
                    itemPanelDetails.setLayout(new BoxLayout(itemPanelDetails, BoxLayout.Y_AXIS));
                    itemPanelDetails.setBorder(BorderFactory.createTitledBorder(itemName));

                    itemPanelDetails.add(new JLabel("Description: " + itemDescription));
                    itemPanelDetails.add(new JLabel("Price: ₹" + itemPrice));
                    itemPanelDetails.add(new JLabel("Auction Duration: " + auctionDuration + " minutes"));

                    JButton placeBidButton = new JButton("Place Bid");
                    placeBidButton.addActionListener(e -> placeBid(itemName));

                    itemPanelDetails.add(placeBidButton);

                    itemPanel.add(itemPanelDetails);
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void placeBid(String itemName) {
        String bidAmount = JOptionPane.showInputDialog(this, "Enter your bid amount:");

        if (bidAmount != null && !bidAmount.trim().isEmpty()) {
            try {
                double bid = Double.parseDouble(bidAmount.trim());

                // Save the bid to the file
                saveBidToFile(itemName, bid);

                JOptionPane.showMessageDialog(this, "Bid placed successfully!");

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid bid amount.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void saveBidToFile(String itemName, double bid) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(BIDS_FILE, true))) {
            writer.write(itemName + "|" + bid);
            writer.newLine();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}