# Student-Registration-System
VU Innovation &amp; Technology Exhibition - Student Registration System
# VU Innovation & Technology Exhibition - Student Registration System

A Java-based desktop application for managing student registrations for Victoria University's Innovation and Technology Exhibition. This application provides a user-friendly GUI interface and integrates with Microsoft Access database for data storage.

## 🎯 Features

- **User-friendly GUI** built with Java Swing
- **Database Integration** with Microsoft Access
- **Input Validation** with real-time error checking
- **Data Integrity** protection against duplicate entries
- **Professional Interface** with color-coded status messages
- **Secure Database Operations** using PreparedStatements

## 📋 Prerequisites

- **Java Development Kit (JDK) 8 or higher**
- **Microsoft Access** (for database creation)
- **UCanAccess JDBC Driver** (included in lib directory)

## 🚀 Installation

### 1. Clone the Repository
```bash
git clone https://github.com/andybari/vu-exhibition-registration.git
cd vu-exhibition-registration
```

### 2. Database Setup
1. Open Microsoft Access
2. Create a new database named `VUE_Exhibition.accdb`
3. Create a table named `Participants` with the following structure:

| Field Name | Data Type | Field Size | Properties |
|------------|-----------|------------|------------|
| ID | AutoNumber | - | Primary Key |
| RegistrationID | Short Text | 20 | Required, Indexed (No Duplicates) |
| StudentName | Short Text | 100 | Required |
| Faculty | Short Text | 100 | Required |
| ProjectTitle | Short Text | 200 | Required |
| ContactNumber | Short Text | 15 | Required |
| EmailAddress | Short Text | 100 | Required |
| DateRegistered | Date/Time | - | Default Value: Now() |

4. Save the database file in the project root directory

### 3. Required Dependencies
The following JAR files are included in the `lib` directory:
- `ucanaccess-5.0.1.jar`
- `commons-lang3-3.8.1.jar`
- `commons-logging-1.2.jar`
- `hsqldb-2.5.0.jar`
- `jackcess-3.0.1.jar`

## 🏃‍♂️ Running the Application

### Compile and Run
```bash
# Compile the application
javac -cp "lib/*" StudentRegistrationApp.java

# Run the application
java -cp ".:lib/*" StudentRegistrationApp
```

### On Windows
```cmd
# Compile
javac -cp "lib/*" StudentRegistrationApp.java

# Run
java -cp ".;lib/*" StudentRegistrationApp
```

## 📖 Usage

### Registration Process
1. **Launch the application**
2. **Fill in all required fields:**
   - Registration ID (unique identifier)
   - Student Name (letters and spaces only)
   - Faculty
   - Project Title
   - Contact Number (10-15 digits)
   - Email Address (valid email format)
3. **Click "Register"** to save the student information
4. **Use "Clear"** to reset all fields
5. **Use "Exit"** to close the application

### Input Validation
The application validates:
- ✅ All fields are required
- ✅ Email format validation
- ✅ Phone number format (10-15 digits)
- ✅ Name contains only letters and spaces
- ✅ Duplicate Registration ID prevention

## 🏗️ Project Structure

```
vu-exhibition-registration/
├── README.md
├── StudentRegistrationApp.java
├── VUE_Exhibition.accdb
├── lib/
│   ├── ucanaccess-5.0.1.jar
│   ├── commons-lang3-3.8.1.jar
│   ├── commons-logging-1.2.jar
│   ├── hsqldb-2.5.0.jar
│   └── jackcess-3.0.1.jar
├── screenshots/
│   ├── main-interface.png
│   ├── registration-success.png
│   └── validation-error.png
└── docs/
    └── database-structure.sql
```
![image](https://github.com/user-attachments/assets/c1e04f66-a12c-4675-ba06-710f27d5bc85)


## 🔧 Technical Details

### Database Connection
```java
String connectionUrl = "jdbc:ucanaccess://VUE_Exhibition.accdb";
Connection conn = DriverManager.getConnection(connectionUrl);
```

### Key Components
- **GUI Framework:** Java Swing with GridBagLayout
- **Database:** Microsoft Access (.accdb)
- **JDBC Driver:** UCanAccess
- **Validation:** Regex patterns for email and phone
- **Security:** PreparedStatement for SQL injection prevention

## 🐛 Troubleshooting

### Common Issues

**1. Database Connection Failed**
- Ensure `VUE_Exhibition.accdb` is in the project root
- Check that Access is not keeping the database file open
- Verify all JAR files are in the lib directory

**2. Compilation Errors**
- Ensure all JAR files are in the classpath
- Check Java version compatibility (JDK 8+)
- Verify database file path is correct

**3. Registration Fails**
- Check database table structure matches requirements
- Ensure all fields are properly validated
