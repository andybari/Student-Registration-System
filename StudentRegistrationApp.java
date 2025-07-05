import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.regex.Pattern;

public class StudentRegistrationApp extends JFrame {
    // GUI Components
    private JTextField regIdField, nameField, facultyField, projectField, contactField, emailField;
    private JButton registerBtn, clearBtn, exitBtn;
    private JLabel statusLabel;
    
    // Database connection
    private Connection conn;
    
    // Email validation pattern
    private static final Pattern EMAIL_PATTERN = 
        Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    
    // Phone validation pattern (10-15 digits)
    private static final Pattern PHONE_PATTERN = 
        Pattern.compile("^[0-9]{10,15}$");
    
    public StudentRegistrationApp() {
        initializeGUI();
        connectToDatabase();
    }
    
    private void initializeGUI() {
        setTitle("VU Innovation & Technology Exhibition - Student Registration");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // Create main panel
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        
        // Title
        JLabel titleLabel = new JLabel("Student Registration System");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 0, 20, 0);
        mainPanel.add(titleLabel, gbc);
        
        // Reset grid width
        gbc.gridwidth = 1;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Registration ID
        gbc.gridx = 0; gbc.gridy = 1;
        mainPanel.add(new JLabel("Registration ID:"), gbc);
        gbc.gridx = 1;
        regIdField = new JTextField(20);
        mainPanel.add(regIdField, gbc);
        
        // Student Name
        gbc.gridx = 0; gbc.gridy = 2;
        mainPanel.add(new JLabel("Student Name:"), gbc);
        gbc.gridx = 1;
        nameField = new JTextField(20);
        mainPanel.add(nameField, gbc);
        
        // Faculty
        gbc.gridx = 0; gbc.gridy = 3;
        mainPanel.add(new JLabel("Faculty:"), gbc);
        gbc.gridx = 1;
        facultyField = new JTextField(20);
        mainPanel.add(facultyField, gbc);
        
        // Project Title
        gbc.gridx = 0; gbc.gridy = 4;
        mainPanel.add(new JLabel("Project Title:"), gbc);
        gbc.gridx = 1;
        projectField = new JTextField(20);
        mainPanel.add(projectField, gbc);
        
        // Contact Number
        gbc.gridx = 0; gbc.gridy = 5;
        mainPanel.add(new JLabel("Contact Number:"), gbc);
        gbc.gridx = 1;
        contactField = new JTextField(20);
        mainPanel.add(contactField, gbc);
        
        // Email Address
        gbc.gridx = 0; gbc.gridy = 6;
        mainPanel.add(new JLabel("Email Address:"), gbc);
        gbc.gridx = 1;
        emailField = new JTextField(20);
        mainPanel.add(emailField, gbc);
        
        // Buttons Panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        registerBtn = new JButton("Register");
        clearBtn = new JButton("Clear");
        exitBtn = new JButton("Exit");
        
        // Style buttons
        registerBtn.setBackground(new Color(0, 123, 255));
        registerBtn.setForeground(Color.WHITE);
        registerBtn.setPreferredSize(new Dimension(100, 30));
        
        clearBtn.setBackground(new Color(108, 117, 125));
        clearBtn.setForeground(Color.WHITE);
        clearBtn.setPreferredSize(new Dimension(100, 30));
        
        exitBtn.setBackground(new Color(220, 53, 69));
        exitBtn.setForeground(Color.WHITE);
        exitBtn.setPreferredSize(new Dimension(100, 30));
        
        buttonPanel.add(registerBtn);
        buttonPanel.add(clearBtn);
        buttonPanel.add(exitBtn);
        
        gbc.gridx = 0; gbc.gridy = 7; gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 0, 0, 0);
        mainPanel.add(buttonPanel, gbc);
        
        // Status Label
        statusLabel = new JLabel(" ");
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        statusLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        gbc.gridy = 8;
        gbc.insets = new Insets(10, 0, 0, 0);
        mainPanel.add(statusLabel, gbc);
        
        add(mainPanel, BorderLayout.CENTER);
        
        // Add action listeners
        registerBtn.addActionListener(new RegisterButtonListener());
        clearBtn.addActionListener(new ClearButtonListener());
        exitBtn.addActionListener(e -> System.exit(0));
        
        // Set window properties
        setSize(500, 400);
        setLocationRelativeTo(null);
        setResizable(false);
    }
    
    private void connectToDatabase() {
        try {
            // UCanAccess JDBC driver connection string
            String dbPath = "VUE_Exhibition.accdb";
            String connectionUrl = "jdbc:ucanaccess://" + dbPath;
            conn = DriverManager.getConnection(connectionUrl);
            setStatus("Connected to database successfully", Color.GREEN);
        } catch (SQLException e) {
            setStatus("Database connection failed: " + e.getMessage(), Color.RED);
            e.printStackTrace();
        }
    }
    
    private void setStatus(String message, Color color) {
        statusLabel.setText(message);
        statusLabel.setForeground(color);
    }
    
    private boolean validateInputs() {
        // Check for empty fields
        if (regIdField.getText().trim().isEmpty() ||
            nameField.getText().trim().isEmpty() ||
            facultyField.getText().trim().isEmpty() ||
            projectField.getText().trim().isEmpty() ||
            contactField.getText().trim().isEmpty() ||
            emailField.getText().trim().isEmpty()) {
            
            setStatus("Error: All fields are required!", Color.RED);
            return false;
        }
        
        // Validate email format
        if (!EMAIL_PATTERN.matcher(emailField.getText().trim()).matches()) {
            setStatus("Error: Invalid email format!", Color.RED);
            return false;
        }
        
        // Validate phone number format
        if (!PHONE_PATTERN.matcher(contactField.getText().trim()).matches()) {
            setStatus("Error: Contact number must be 10-15 digits!", Color.RED);
            return false;
        }
        
        // Validate name contains only letters and spaces
        if (!nameField.getText().trim().matches("[a-zA-Z\\s]+")) {
            setStatus("Error: Student name should contain only letters and spaces!", Color.RED);
            return false;
        }
        
        return true;
    }
    
    private boolean isDuplicateRegistration(String regId) {
        try {
            String query = "SELECT COUNT(*) FROM Participants WHERE RegistrationID = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, regId);
            ResultSet rs = pstmt.executeQuery();
            rs.next();
            return rs.getInt(1) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    private class RegisterButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (conn == null) {
                setStatus("Error: No database connection!", Color.RED);
                return;
            }
            
            if (!validateInputs()) {
                return;
            }
            
            String regId = regIdField.getText().trim();
            String name = nameField.getText().trim();
            String faculty = facultyField.getText().trim();
            String project = projectField.getText().trim();
            String contact = contactField.getText().trim();
            String email = emailField.getText().trim();
            
            // Check for duplicate registration ID
            if (isDuplicateRegistration(regId)) {
                setStatus("Error: Registration ID already exists!", Color.RED);
                return;
            }
            
            try {
                String insertQuery = "INSERT INTO Participants (RegistrationID, StudentName, Faculty, ProjectTitle, ContactNumber, EmailAddress) VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement pstmt = conn.prepareStatement(insertQuery);
                pstmt.setString(1, regId);
                pstmt.setString(2, name);
                pstmt.setString(3, faculty);
                pstmt.setString(4, project);
                pstmt.setString(5, contact);
                pstmt.setString(6, email);
                
                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected > 0) {
                    setStatus("Student registered successfully!", Color.GREEN);
                    clearFields();
                } else {
                    setStatus("Error: Registration failed!", Color.RED);
                }
                
            } catch (SQLException ex) {
                setStatus("Database error: " + ex.getMessage(), Color.RED);
                ex.printStackTrace();
            }
        }
    }
    
    private class ClearButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            clearFields();
            setStatus("Fields cleared", Color.BLUE);
        }
    }
    
    private void clearFields() {
        regIdField.setText("");
        nameField.setText("");
        facultyField.setText("");
        projectField.setText("");
        contactField.setText("");
        emailField.setText("");
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new StudentRegistrationApp().setVisible(true);
        });
    }
}