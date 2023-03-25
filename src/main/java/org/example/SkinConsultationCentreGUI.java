package org.example;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.Key;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import java.util.Base64;

public class SkinConsultationCentreGUI {
    private static JPanel panelHolder;
    private static JPanel buttonPanel;
    private static CardLayout cardLayout;
    private static JButton homeButton;
    private static JButton button1;
    private static JButton button2;
    private static JButton button3;
    private static JTextField idField;
    private static JTextField searchField;
    private static JTextField pIdField;
    private static JTextField pNameField;
    private static JTextField pSNameField;
    private static JTextField pDOBField;
    private static JTextField pMNoField;
    private static JSpinner startTimeSpinner;
    private static JSpinner endTimeSpinner;
    private static JTextArea noteField;
    private static JTextField costField;
    private static final String ALGORITHM = "AES";
    private static final String KEY = "1Hbfh667adfDEJ78";
    private static JPanel searchPanel;
    private static int deleteDoctorMedicalLicenceNumber;
    private static int foundDoctorMedicalLicenseNumber;
    private static Calendar foundDoctorAvailableDate;

    // Create a method to create the frame
    public static JFrame createFrame() {
        // Create a new JFrame
        JFrame frame = new JFrame("Skin Consultation Management System");

        // Set the size of the frame
        frame.setSize(1250, 670);
        // Set the default close operation for the frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        return frame;
    }

    // Create a method to create the home panel
    public static JPanel createHomePanel() {
        JPanel home = new JPanel();
        home.setBackground(new Color(1, 224, 182));

        // Create a label with the welcome message
        JLabel welcomeLabel = new JLabel("Welcome to the Skin Consultation Management System");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 28));
        welcomeLabel.setForeground(Color.BLACK);

        // Set the border of the label with padding
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(30, 80, 10, 20));

        // Center the welcome message horizontally
        welcomeLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);

        // Create an image label
        JLabel imageLabel = new JLabel(new ImageIcon("src\\main\\java\\org\\example\\skinConsultation.png"));
        // Add some glue above the image label to push it to the center of the panel
        home.add(Box.createVerticalGlue());

        // Add the image label in the center of the panel
        home.add(imageLabel);

        // Add some glue below the image label to push it to the center of the panel
        home.add(Box.createVerticalGlue());

        // Center the image label horizontally
        imageLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);

        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Add the label to the top of the panel using a BorderLayout
        home.setLayout(new BorderLayout());
        home.add(welcomeLabel, BorderLayout.NORTH);

        // Add the image label in the center of the panel
        home.add(imageLabel, BorderLayout.CENTER);
        // Return the home
        return home;
    }

    // Create a method to create the doctor information panel
    public static JPanel createDoctorInformationPanel(int deleteDoctorMedicalLicenceNumber) {
        // Retrieve the list of doctors and the number of doctors from the WestminsterSkinConsultationManager class
        Doctor[] doctors = WestminsterSkinConsultationManager.getDoctors();
        int numDoctors = WestminsterSkinConsultationManager.getNumDoctors();

        // Create a panel to hold the table
        JPanel panel1 = new JPanel();
        panel1.setBackground(new Color(1, 224, 182));

        // Create a label with the text "Doctors Information Table"
        JLabel doctorInfoLabel = new JLabel("Doctors Information Table");
        doctorInfoLabel.setFont(new Font("Arial", Font.BOLD, 24));
        doctorInfoLabel.setForeground(Color.BLACK);
        // Set the border of the label with padding
        doctorInfoLabel.setBorder(BorderFactory.createEmptyBorder(40, 80, 60, 20));
        // Center the label horizontally
        doctorInfoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        // Add the label to the top of the panel using a BorderLayout
        panel1.setLayout(new BorderLayout());
        panel1.add(doctorInfoLabel, BorderLayout.NORTH);

        // Create a JTable and set the model
        JTable table = new JTable();

        // Create a DefaultTableModel
        DefaultTableModel model = new DefaultTableModel();

        // Find the index of the doctor to be deleted
        int index = -1;
        for (int i = 0; i < numDoctors; i++) {
            if (doctors[i] != null && doctors[i].getMedicalLicenseNumber() == deleteDoctorMedicalLicenceNumber) {
                index = i;
                break;
            }
        }
        // If the doctor was found
        if (index != -1) {
            // Shift the elements in the array to the left to fill the gap
            for (int i = index; i < numDoctors - 1; i++) {
                doctors[i] = doctors[i + 1];
            }
            // Decrement the number of doctors
            numDoctors--;
            // Clear the table model
            model.setRowCount(0);
        }

        // Add columns to the model
        model.addColumn("Medical Licence No");
        model.addColumn("Name");
        model.addColumn("Surname");
        model.addColumn("Date of Birth");
        model.addColumn("Specialisation");
        model.addColumn("Mobile Number");
        model.addColumn("Available Date");

        // Add rows to the model
        for (int i = 0; i < numDoctors; i++) {
            Doctor doctor = doctors[i];
            if (doctors[i] != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                String dob = sdf.format(doctor.getDob().getTime());
                String availableDate = sdf.format(doctor.getAvailableDate().getTime());
                Object[] rowData = new Object[]{
                        doctor.getMedicalLicenseNumber(),
                        doctor.getName(),
                        doctor.getSurname(),
                        dob,
                        doctor.getSpecialisation(),
                        doctor.getMobileNumber(),
                        availableDate
                };
                model.addRow(rowData);
            }
        }

        table.setModel(model);
        // Set the preferred size of the table
        table.setPreferredSize(new Dimension(100, 500));
        // Center the text in the table cells
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // Set the font for the table header
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 14));

        // Set the preferred width for each column in the table
        TableColumn column1 = table.getColumnModel().getColumn(0);
        column1.setPreferredWidth(40);
        TableColumn column2 = table.getColumnModel().getColumn(1);
        column2.setPreferredWidth(18);
        TableColumn column3 = table.getColumnModel().getColumn(2);
        column3.setPreferredWidth(18);
        TableColumn column4 = table.getColumnModel().getColumn(3);
        column4.setPreferredWidth(20);
        TableColumn column5 = table.getColumnModel().getColumn(4);
        column5.setPreferredWidth(40);
        TableColumn column6 = table.getColumnModel().getColumn(5);
        column6.setPreferredWidth(15);
        TableColumn column7 = table.getColumnModel().getColumn(6);
        column7.setPreferredWidth(17);
        // Set the row height of the table
        table.setRowHeight(20);
        // Set the selection mode of the table to single selection
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Add the table to a scroll pane
        JScrollPane scrollPane = new JScrollPane(table);
        // Create a TableRowSorter
        TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(table.getModel());
        // Set the sorter to the table
        table.setRowSorter(sorter);
        // Create a list of sort keys
        List<RowSorter.SortKey> sortKeys = new ArrayList<>();
        // Add a sort key for the Name column
        sortKeys.add(new RowSorter.SortKey(1, SortOrder.ASCENDING));
        // Add a sort key for the Surname column
        sortKeys.add(new RowSorter.SortKey(2, SortOrder.ASCENDING));
        // Set the sort keys to the sorter
        sorter.setSortKeys(sortKeys);
        // Sort the table
        sorter.sort();
        // Add the scroll pane to the panel
        panel1.add(scrollPane, BorderLayout.CENTER);
        // Set the size of the scroll pane
        scrollPane.setSize(1000, 400);

        JPanel spacePanel = new JPanel();
        panel1.add(spacePanel, BorderLayout.SOUTH);
        spacePanel.setPreferredSize(new Dimension(100, 150));
        spacePanel.setBackground(new Color(1, 224, 182));

        // Return the panel
        return panel1;
    }

    // Create a method to create the Book A Consultation panel
    public static JPanel createBookAConsultationPanel() {
        JPanel panel2 = new JPanel();
        panel2.setBackground(new Color(1, 224, 182));
        // Create a label with the text "Book A Consultation"
        JLabel bookingInfoLabel = new JLabel("Book A Consultation");
        bookingInfoLabel.setFont(new Font("Arial", Font.BOLD, 24));
        bookingInfoLabel.setForeground(Color.BLACK);

        // Set the border of the label with padding
        bookingInfoLabel.setBorder(BorderFactory.createEmptyBorder(30, 80, 0, 20));

        // Center the label horizontally
        bookingInfoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        // Add the label to the top of the panel using a BorderLayout
        panel2.setLayout(new BorderLayout());
        panel2.add(bookingInfoLabel, BorderLayout.NORTH);
        // Create a panel to hold the input fields and buttons
        searchPanel = new JPanel();
        searchPanel.setLayout(new GridBagLayout());
        searchPanel.setVisible(true);
        //create id Label
        JLabel idLabel = new JLabel("Enter Doctor Medical Licence Number : ");
        idLabel.setFont(new Font("Arial", Font.BOLD, 16));
        //create id Text Field
        idField = new JTextField(15);
        idField.setFont(new Font("Arial", Font.PLAIN, 14));
        idField.setBorder(BorderFactory.createEmptyBorder(7, 5, 7, 5));
        // Create a Label
        JLabel searchLabel = new JLabel("Enter the Date You Need to Check : ");
        searchLabel.setFont(new Font("Arial", Font.BOLD, 16));
        // Create a text field for the Inputs
        searchField = new JTextField(15);
        searchField.setFont(new Font("Arial", Font.PLAIN, 14));
        searchField.setBorder(BorderFactory.createEmptyBorder(7, 5, 7, 5));
        // Create a Search button
        JButton searchButton = new JButton("Search");
        searchButton.setFont(new Font("Arial", Font.BOLD, 14));
        searchButton.setPreferredSize(new Dimension(10, 30));

        // Create a clear button2
        JButton clearButton = new JButton("Clear");
        clearButton.setFont(new Font("Arial", Font.BOLD, 14));
        clearButton.setPreferredSize(new Dimension(10, 30)); // set button width

        // Add the id label and text field horizontally using a GridBagConstraints
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(40, 0, 10, 0); // top, left, bottom, right
        searchPanel.add(idLabel, c);

        c.gridx = 1;
        c.gridy = 0;
        c.insets = new Insets(40, 30, 10, 0); // top, left, bottom, right
        searchPanel.add(idField, c);

        // Add the search label and text field horizontally using a GridBagConstraints
        c.gridx = 0;
        c.gridy = 1;
        c.insets = new Insets(10, 0, 0, 0); // top, left, bottom, right
        searchPanel.add(searchLabel, c);

        c.gridx = 1;
        c.gridy = 1;
        c.insets = new Insets(10, 30, 0, 0); // top, left, bottom, right
        searchPanel.add(searchField, c);

        // Add the search and clear buttons horizontally using a GridBagConstraints
        c.gridx = 0;
        c.gridy = 2;
        c.insets = new Insets(20, 0, 10, 0); // top, left, bottom, right
        searchPanel.add(searchButton, c);

        c.gridx = 1;
        c.gridy = 2;
        c.insets = new Insets(20, 30, 10, 0); // top, left, bottom, right
        searchPanel.add(clearButton, c);
        searchPanel.setBackground(new Color(1, 224, 182));
        JPanel bookPanel = new JPanel();
        bookPanel.add(searchPanel, BorderLayout.NORTH);
        bookPanel.setPreferredSize(new Dimension(100, 100));
        bookPanel.setBackground(new Color(1, 224, 182));
        panel2.add(bookPanel, BorderLayout.CENTER);

        // Create an image label
        JLabel imageLabel2 = new JLabel(new ImageIcon("src\\main\\java\\org\\example\\date.png"));
        // Add some glue above the image label to push it to the center of the panel
        bookPanel.add(Box.createVerticalGlue());
        // Add the image label in the center of the panel
        bookPanel.add(imageLabel2);
        // Center the image label horizontally
        imageLabel2.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        imageLabel2.setHorizontalAlignment(SwingConstants.CENTER);

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Clear the text
                idField.setText("");
                searchField.setText("");
            }
        });

        // Add an ActionListener to the searchButton
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the user input for the doctor's medical license number and the date to check for availability
                String medicalLicenseNumberInput = idField.getText();
                String dateToCheckInput = searchField.getText();

                // Validate the input fields
                if (medicalLicenseNumberInput.isEmpty() || dateToCheckInput.isEmpty()) {
                    JOptionPane.showMessageDialog(searchPanel, "Input Fields Cannot be Empty.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int medicalLicenseNumber;
                try {
                    medicalLicenseNumber = Integer.parseInt(medicalLicenseNumberInput);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(searchPanel, "Please Enter Integer values for the Medical Licence Number.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Calendar dateToCheck = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                try {
                    dateToCheck.setTime(sdf.parse(dateToCheckInput));
                } catch (ParseException ex) {
                    JOptionPane.showMessageDialog(searchPanel, "Please Enter Doctor Available Date this format (dd/mm/yyyy).", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Retrieve the list of doctors and the number of doctors from the WestminsterSkinConsultationManager class
                Doctor[] doctors = WestminsterSkinConsultationManager.getDoctors();
                int numDoctors = WestminsterSkinConsultationManager.getNumDoctors();

                // Iterate through the list of doctors to find the one with the specified medical license number
                boolean isDoctorFound = false;
                for (int i = 0; i < numDoctors; i++) {
                    if (doctors[i] != null) {
                        if (doctors[i].getMedicalLicenseNumber() == medicalLicenseNumber) {
                            isDoctorFound = true;
                            foundDoctorMedicalLicenseNumber = doctors[i].getMedicalLicenseNumber();
                            foundDoctorAvailableDate = doctors[i].getAvailableDate();
                            break;
                        }
                    }
                }

                // If the doctor id is not found, check if there are any doctors available on the given date
                if (!isDoctorFound) {
                    // Check if the date is available for any doctors
                    boolean isDateFound = false;
                    for (int i = 0; i < numDoctors; i++) {
                        if (doctors[i] != null) {
                            Calendar availableDate = doctors[i].getAvailableDate();
                            if (availableDate.get(Calendar.YEAR) == dateToCheck.get(Calendar.YEAR) &&
                                    availableDate.get(Calendar.MONTH) == dateToCheck.get(Calendar.MONTH) &&
                                    availableDate.get(Calendar.DATE) == dateToCheck.get(Calendar.DATE)) {
                                isDateFound = true;
                                break;
                            }
                        }
                    }

                    // If no doctor is available on the given date, display an error message
                    if (!isDateFound) {
                        JOptionPane.showMessageDialog(searchPanel, "No doctor is available on the given date.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // If there is a doctor available on the given date, choose a random doctor from the list of doctors available on the given date
                    ArrayList<Doctor> availableDoctors = new ArrayList<Doctor>();
                    for (int i = 0; i < numDoctors; i++) {
                        if (doctors[i] != null) {
                            Calendar availableDate = doctors[i].getAvailableDate();
                            if (availableDate.get(Calendar.YEAR) == dateToCheck.get(Calendar.YEAR) &&
                                    availableDate.get(Calendar.MONTH) == dateToCheck.get(Calendar.MONTH) &&
                                    availableDate.get(Calendar.DATE) == dateToCheck.get(Calendar.DATE)) {
                                availableDoctors.add(doctors[i]);
                            }
                        }
                    }
                    Random rand = new Random();
                    int randomDoctorIndex = rand.nextInt(availableDoctors.size());
                    Doctor randomDoctor = availableDoctors.get(randomDoctorIndex);

                    // Assign the global variables for the medical license number and available date of the randomly chosen doctor
                    foundDoctorMedicalLicenseNumber = randomDoctor.getMedicalLicenseNumber();
                    foundDoctorAvailableDate = randomDoctor.getAvailableDate();

                    // Display a message to the user indicating that the doctor with the given medical license number is not available, but a doctor is available on the given date
                    JOptionPane.showMessageDialog(searchPanel, "Doctor with medical license number " + medicalLicenseNumber + " is not available on the given date. " +
                            "However, doctor with medical license number " + randomDoctor.getMedicalLicenseNumber() + " is available on the given date.", "Information", JOptionPane.INFORMATION_MESSAGE);
                    // Open the patient information frame
                    JFrame patientInformationFrame = createPatientInformationFrame(randomDoctor);
                    patientInformationFrame.setVisible(true);
                    return;
                }

                // Check if the date is available for the doctor with the given medical license number
                boolean isDateAvailable = false;
                Doctor selectedDoctor = null;
                for (int i = 0; i < numDoctors; i++) {
                    if (doctors[i] != null && doctors[i].getMedicalLicenseNumber() == medicalLicenseNumber) {
                        Calendar availableDate = doctors[i].getAvailableDate();
                        if (availableDate.get(Calendar.YEAR) == dateToCheck.get(Calendar.YEAR) &&
                                availableDate.get(Calendar.MONTH) == dateToCheck.get(Calendar.MONTH) &&
                                availableDate.get(Calendar.DATE) == dateToCheck.get(Calendar.DATE)) {
                            isDateAvailable = true;
                            selectedDoctor = doctors[i];
                            break;
                        }
                    }
                }

                // If the doctor is not available on the given date, display an error message
                if (!isDateAvailable) {
                    JOptionPane.showMessageDialog(searchPanel, "Doctor with medical license number " + medicalLicenseNumber + " is not available on the given date.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // If the doctor is available on the given date, open the patient information frame
                JFrame patientInformationFrame = createPatientInformationFrame(selectedDoctor);
                patientInformationFrame.setVisible(true);
            }
        });
        return panel2;
    }

    // Create a method to create a new frame
    public static JFrame createPatientInformationFrame(Doctor doctor) {
        JFrame patientInformationFrame = new JFrame("Patient Information");
        // Set the size and layout of the frame
        patientInformationFrame.setSize(680, 510);
        patientInformationFrame.setLayout(new BorderLayout());
        JPanel fullInformation = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 15)); // hgap=20, vgap=0
        //Create a panel for the patient information form
        JPanel patientPanel = new JPanel();
        patientPanel.setLayout(new GridBagLayout());
        // Create a label with the text "Book A Consultation"
        JLabel patientInfoLabel = new JLabel("Add Patient Information");
        patientInfoLabel.setFont(new Font("Arial", Font.BOLD, 20));
        patientInfoLabel.setForeground(Color.BLACK);
        fullInformation.setBackground(new Color(211, 211, 211));
        patientPanel.setBackground(new Color(211, 211, 211));
        // Set the border of the label with padding
        patientInfoLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        // Center the label horizontally
        patientInfoLabel.setHorizontalAlignment(SwingConstants.CENTER);

        //create id Label
        JLabel pIdLabel = new JLabel("Patient id : ");
        pIdLabel.setFont(new Font("Arial", Font.BOLD, 15));
        //create id Text Field
        pIdField = new JTextField(13);
        pIdField.setFont(new Font("Arial", Font.PLAIN, 14));
        pIdField.setBorder(BorderFactory.createEmptyBorder(7, 5, 7, 5));
        // Create a Label
        JLabel pNameLabel = new JLabel("Patient Name : ");
        pNameLabel.setFont(new Font("Arial", Font.BOLD, 15));
        // Create a text field for the Inputs
        pNameField = new JTextField(13);
        pNameField.setFont(new Font("Arial", Font.PLAIN, 14));
        pNameField.setBorder(BorderFactory.createEmptyBorder(7, 5, 7, 5));
        // Create a Label
        JLabel pSNameLabel = new JLabel("Patient Surname : ");
        pSNameLabel.setFont(new Font("Arial", Font.BOLD, 15));
        // Create a text field for the Inputs
        pSNameField = new JTextField(13);
        pSNameField.setFont(new Font("Arial", Font.PLAIN, 14));
        pSNameField.setBorder(BorderFactory.createEmptyBorder(7, 5, 7, 5));
        // Create a Label
        JLabel pDOBLabel = new JLabel("Patient Date Of Birth : ");
        pDOBLabel.setFont(new Font("Arial", Font.BOLD, 15));
        // Create a text field for the Inputs
        pDOBField = new JTextField(13);
        pDOBField.setFont(new Font("Arial", Font.PLAIN, 14));
        pDOBField.setBorder(BorderFactory.createEmptyBorder(7, 5, 7, 5));
        // Create a Label
        JLabel pMNoLabel = new JLabel("Patient Mobile No : ");
        pMNoLabel.setFont(new Font("Arial", Font.BOLD, 15));
        // Create a text field for the Inputs
        pMNoField = new JTextField(13);
        pMNoField.setFont(new Font("Arial", Font.PLAIN, 14));
        pMNoField.setBorder(BorderFactory.createEmptyBorder(7, 5, 7, 5));

        // Create a Label for the start time
        JLabel startTimeLabel = new JLabel("Start time : ");
        startTimeLabel.setFont(new Font("Arial", Font.BOLD, 15));
        // Create a SpinnerDateModel with the current time as the initial value
        SpinnerDateModel startTimeModel = new SpinnerDateModel();
        startTimeModel.setCalendarField(Calendar.MINUTE);
        // Create a JSpinner using the SpinnerDateModel
        startTimeSpinner = new JSpinner(startTimeModel);
        // Set the editor for the JSpinner to a JSpinner.DateEditor that displays the time in the format HH:mm
        JSpinner.DateEditor startTimeEditor = new JSpinner.DateEditor(startTimeSpinner, "HH:mm");
        startTimeSpinner.setEditor(startTimeEditor);

        // Create a Label for the end time
        JLabel endTimeLabel = new JLabel("End time : ");
        endTimeLabel.setFont(new Font("Arial", Font.BOLD, 15));
        // Create a SpinnerDateModel with the current time as the initial value
        SpinnerDateModel endTimeModel = new SpinnerDateModel();
        endTimeModel.setCalendarField(Calendar.MINUTE);
        // Create a JSpinner using the SpinnerDateModel
        endTimeSpinner = new JSpinner(endTimeModel);
        // Set the editor for the JSpinner to a JSpinner.DateEditor that displays the time in the format HH:mm
        JSpinner.DateEditor endTimeEditor = new JSpinner.DateEditor(endTimeSpinner, "HH:mm");
        endTimeSpinner.setEditor(endTimeEditor);

        // Create a Label
        JLabel note = new JLabel("Add Some Note : ");
        note.setFont(new Font("Arial", Font.BOLD, 15));
        // Create a text field for the Inputs
        noteField = new JTextArea(4, 38);
        noteField.setFont(new Font("Arial", Font.PLAIN, 14));
        noteField.setBorder(BorderFactory.createEmptyBorder(7, 5, 7, 5));

        // Create a Label
        JLabel cost = new JLabel("Cost : ");
        cost.setFont(new Font("Arial", Font.BOLD, 15));
        // Create a text field for the Inputs
        costField = new JTextField(13);
        costField.setFont(new Font("Arial", Font.PLAIN, 14));
        costField.setBorder(BorderFactory.createEmptyBorder(7, 5, 7, 5));

        // Create a Submit button
        JButton addButton = new JButton("Book");
        addButton.setFont(new Font("Arial", Font.BOLD, 14));
        addButton.setPreferredSize(new Dimension(90, 30));
        // Create a clear button3
        JButton clearButton3 = new JButton("Clear");
        clearButton3.setFont(new Font("Arial", Font.BOLD, 14));
        clearButton3.setPreferredSize(new Dimension(90, 30));

        // Add the pid label and text field horizontally using a GridBagConstraints
        GridBagConstraints p = new GridBagConstraints();
        p.fill = GridBagConstraints.HORIZONTAL;

        // Add the name label and text field horizontally using a GridBagConstraints
        p.gridx = 0;
        p.gridy = 0;
        p.insets = new Insets(20, 0, 0, 0); // top, left, bottom, right
        patientPanel.add(pNameLabel, p);
        p.gridx = 1;
        p.gridy = 0;
        p.insets = new Insets(20, 0, 0, 0); // top, left, bottom, right
        patientPanel.add(pNameField, p);

        // Add the id label and text field horizontally using a GridBagConstraints
        p.gridx = 2;
        p.gridy = 0;
        p.insets = new Insets(20, 20, 0, 0); // top, left, bottom, right
        patientPanel.add(pIdLabel, p);
        p.gridx = 3;
        p.gridy = 0;
        p.insets = new Insets(20, 20, 0, 0); // top, left, bottom, right
        patientPanel.add(pIdField, p);

        // Add the surname label and text field horizontally using a GridBagConstraints
        p.gridx = 0;
        p.gridy = 1;
        p.insets = new Insets(20, 0, 0, 0); // top, left, bottom, right
        patientPanel.add(pSNameLabel, p);
        p.gridx = 1;
        p.gridy = 1;
        p.insets = new Insets(20, 0, 0, 0); // top, left, bottom, right
        patientPanel.add(pSNameField, p);

        // Add the start time picker horizontally using a GridBagConstraints
        p.gridx = 2;
        p.gridy = 1;
        p.insets = new Insets(20, 20, 0, 0); // top, left, bottom, right
        patientPanel.add(startTimeLabel, p);
        p.gridx = 3;
        p.gridy = 1;
        p.insets = new Insets(20, 20, 0, 0); // top, left, bottom, right
        patientPanel.add(startTimeSpinner, p);

        // Add the pMNo label and text field horizontally using a GridBagConstraints
        p.gridx = 0;
        p.gridy = 2;
        p.insets = new Insets(20, 0, 0, 0); // top, left, bottom, right
        patientPanel.add(pMNoLabel, p);
        p.gridx = 1;
        p.gridy = 2;
        p.insets = new Insets(20, 0, 0, 0); // top, left, bottom, right
        patientPanel.add(pMNoField, p);

        // Add the end time picker horizontally using a GridBagConstraints
        p.gridx = 2;
        p.gridy = 2;
        p.insets = new Insets(20, 20, 0, 0); // top, left, bottom, right
        patientPanel.add(endTimeLabel, p);
        p.gridx = 3;
        p.gridy = 2;
        p.insets = new Insets(20, 20, 0, 0); // top, left, bottom, right
        patientPanel.add(endTimeSpinner, p);

        // Add the pDOB label and text field horizontally using a GridBagConstraints
        p.gridx = 0;
        p.gridy = 3;
        p.insets = new Insets(20, 0, 10, 0); // top, left, bottom, right
        patientPanel.add(pDOBLabel, p);
        p.gridx = 1;
        p.gridy = 3;
        p.insets = new Insets(20, 0, 10, 0); // top, left, bottom, right
        patientPanel.add(pDOBField, p);

        // Add the cost label and textField horizontally using a GridBagConstraints
        p.gridx = 2;
        p.gridy = 3;
        p.insets = new Insets(20, 20, 10, 0); // top, left, bottom, right
        patientPanel.add(cost, p);
        p.gridx = 3;
        p.gridy = 3;
        p.insets = new Insets(20, 20, 10, 0); // top, left, bottom, right
        patientPanel.add(costField, p);

        fullInformation.add(patientInfoLabel, BorderLayout.NORTH);
        fullInformation.add(patientPanel, BorderLayout.CENTER);
        fullInformation.add(note);
        fullInformation.add(noteField);
        fullInformation.add(addButton);
        fullInformation.add(clearButton3);

        // Add the patient information panel to the frame
        patientInformationFrame.add(fullInformation, BorderLayout.CENTER);

        // Add an ActionListener to the addButton
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // Validate the input fields
                if (pIdField.getText().isEmpty() || pNameField.getText().isEmpty() || pSNameField.getText().isEmpty() || pDOBField.getText().isEmpty() || pMNoField.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(patientPanel, "The input fields cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Validate that the name and surname fields contain only letters
                if (!pNameField.getText().matches("[a-zA-Z]+") || !pSNameField.getText().matches("[a-zA-Z]+")) {
                    JOptionPane.showMessageDialog(patientPanel, "The name and surname fields can only contain letters.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Validate the patient id field
                try {
                    int patientId = Integer.parseInt(pIdField.getText());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(patientPanel, "The patient id field can only contain numbers.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Check if the mobile number input is in the correct format
                String mobileNumber = pMNoField.getText();
                String localNumberPattern = "^[0-9]{10}$";
                String internationalNumberPattern = "^\\+[0-9]{11}$";

                // Validate the mobile number input
                if (!mobileNumber.matches(localNumberPattern) && !mobileNumber.matches(internationalNumberPattern)) {
                    JOptionPane.showMessageDialog(patientPanel, "Please enter a valid mobile number in the format 0763689998 or +94763689998.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Validate the patient date of birth using the parseDate method
                String dobInput = pDOBField.getText();
                Calendar dob = parseDate(dobInput);
                if (dob == null) {
                    JOptionPane.showMessageDialog(patientPanel, "Please enter a valid date in the format (dd/MM/yyyy) for the patient date of birth.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String note = noteField.getText();
                if (note.isEmpty()) {
                    JOptionPane.showMessageDialog(patientPanel, "Please enter a note for the consultation.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Get the values from the text fields
                int patientId = Integer.parseInt(pIdField.getText());
                String name = pNameField.getText();
                String surname = pSNameField.getText();
                Calendar startTime = Calendar.getInstance();
                startTime.setTime((Date) startTimeSpinner.getValue());
                Calendar endTime = Calendar.getInstance();
                endTime.setTime((Date) endTimeSpinner.getValue());

                // Encrypt the note text
                String notes = noteField.getText();

                String encryptedNotes = null;
                try {
                    encryptedNotes = SkinConsultationCentreGUI.encrypt(notes);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                // Check if the desired start and end times overlap with the start and end times of any existing consultations for the specific doctor
                if (Consultation.overlapsWithExistingConsultations(foundDoctorMedicalLicenseNumber, startTime, endTime)) {
                    // The desired start and end times overlap with the start and end times of an existing consultation for the specific doctor
                    JOptionPane.showMessageDialog(patientPanel, "The doctor is not available. Please choose a different time.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                } else {
                    // Calculate the cost of the consultation
                    double cost = Consultation.calculateCost(patientId, startTime, endTime);
                    costField.setText(String.format("%.2f", cost));

                    // Create a new Consultation object and add it to the list of consultations
                    Consultation consultation = new Consultation(foundDoctorMedicalLicenseNumber, patientId, foundDoctorAvailableDate, startTime, endTime, cost, encryptedNotes);
                    Consultation.getConsultations().add(consultation);

                    // Create a Patient object
                    Patient patient = new Patient(patientId, name, surname, dob, mobileNumber);

                    // Check if the patient already exists in the list
                    if (!Patient.getPatients().contains(patient)) {
                        // Add the patient to the list
                        Patient.getPatients().add(patient);
                        // Save the patients to a file
                        Patient.savePatientsToFile("patients.csv");
                    }

                    // Clear the input fields
                    pIdField.setText("");
                    pNameField.setText("");
                    pSNameField.setText("");
                    pDOBField.setText("");
                    pMNoField.setText("");
                    noteField.setText("");

                    // Show a success message
                    JOptionPane.showMessageDialog(patientPanel, "Consultation Booking successfully! The cost of the consultation for patient ID " + patientId + " is £" + cost , "Success", JOptionPane.INFORMATION_MESSAGE);
                }
                // Hide the patient information frame
                patientInformationFrame.setVisible(false);
            }
        });

        clearButton3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Clear the text
                pNameField.setText("");
                pSNameField.setText("");
                pIdField.setText("");
                pDOBField.setText("");
                noteField.setText("");
                pMNoField.setText("");
            }
        });

        return patientInformationFrame;
    }

    // Create Consultation information panel
    public static JPanel createConsultationInformationTable() {
        JPanel panel3 = new JPanel();
        panel3.setBackground(new Color(1, 224, 182));
        // Create a label with the text "Consultation Information Table"
        JLabel consultationInfoLabel = new JLabel("Consultation Information Table");
        consultationInfoLabel.setFont(new Font("Arial", Font.BOLD, 24));
        consultationInfoLabel.setForeground(Color.BLACK);
        // Set the border of the label with padding
        consultationInfoLabel.setBorder(BorderFactory.createEmptyBorder(40, 80, 60, 20));
        // Center the label horizontally
        consultationInfoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        // Add the label to the top of the panel using a BorderLayout
        panel3.setLayout(new BorderLayout());
        panel3.add(consultationInfoLabel, BorderLayout.NORTH);

        // Create the table model
        DefaultTableModel model2 = new DefaultTableModel();

        // Add the column names to the table model
        model2.addColumn("Doctor MLicence No");
        model2.addColumn("Patient Id");
        model2.addColumn("Booked Date");
        model2.addColumn("Start Time");
        model2.addColumn("End Time");
        model2.addColumn("Cost");
        model2.addColumn("Note");

        // Get the list of consultations
        List<Consultation> consultations = Consultation.getConsultations();

        // Loop through the consultations and add their data to the table model
        for (Consultation consultation : consultations) {
            // Decrypt the notes
            String decryptedNotes = null;
            try {
                decryptedNotes = SkinConsultationCentreGUI.decrypt(consultation.getNotes());
            } catch (Exception e) {
                e.printStackTrace();
            }

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            SimpleDateFormat timeFormat = new SimpleDateFormat("h.mm a");

            // Format the date and time
            String formattedDate = dateFormat.format(consultation.getConsultationDate().getTime());
            String formattedStartTime = timeFormat.format(consultation.getStartTime().getTime());
            String formattedEndTime = timeFormat.format(consultation.getEndTime().getTime());

            // Create a row for the consultation data
            Object[] rowData = {consultation.getDoctorMedicalLicenseNo(), consultation.getPatientId(), formattedDate, formattedStartTime, formattedEndTime, consultation.getCost(), decryptedNotes};

            // Add the row to the table model
            model2.addRow(rowData);

        }
        // Create the table
        JTable table2 = new JTable();
        table2.setModel(model2);

        // Set the preferred size of the table
        table2.setPreferredSize(new Dimension(100, 500));
        // Center the text in the table cells
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < table2.getColumnCount(); i++) {
            table2.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // Set the font for the table header
        JTableHeader header = table2.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 14));

        // Set the preferred width for each column in the table
        TableColumn col1 = table2.getColumnModel().getColumn(0);
        col1.setPreferredWidth(40);
        TableColumn col2 = table2.getColumnModel().getColumn(1);
        col2.setPreferredWidth(18);
        TableColumn col3 = table2.getColumnModel().getColumn(2);
        col3.setPreferredWidth(20);
        TableColumn col4 = table2.getColumnModel().getColumn(3);
        col4.setPreferredWidth(20);
        TableColumn col5 = table2.getColumnModel().getColumn(4);
        col5.setPreferredWidth(20);
        TableColumn col6 = table2.getColumnModel().getColumn(5);
        col6.setPreferredWidth(18);
        TableColumn col7 = table2.getColumnModel().getColumn(6);
        col7.setPreferredWidth(20);
        // Set the row height of the table
        table2.setRowHeight(20);
        // Set the selection mode of the table to single selection
        table2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        // Set the font for the table header
        JTableHeader header2 = table2.getTableHeader();
        header2.setFont(new Font("Arial", Font.BOLD, 14));
        // Add the table to a scroll pane
        JScrollPane scrollPane2 = new JScrollPane(table2);
        // Add the scroll pane to the panel
        panel3.add(scrollPane2);

        JPanel viewPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 40)); // hgap=20, vgap=30
        viewPanel.setPreferredSize(new Dimension(100, 150));
        viewPanel.setBackground(new Color(1, 224, 182));
        JButton cancelButton = new JButton("Cancel Consultations");
        cancelButton.setFont(new Font("Arial", Font.BOLD, 14));
        cancelButton.setPreferredSize(new Dimension(190, 30));
        JButton saveButton = new JButton("Save Consultations");
        saveButton.setFont(new Font("Arial", Font.BOLD, 14));
        saveButton.setPreferredSize(new Dimension(180, 30));
        viewPanel.add(cancelButton);
        viewPanel.add(saveButton);
        panel3.add(viewPanel, BorderLayout.SOUTH);

        // Add an action listener to the cancel button
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the selected row index
                int selectedRow = table2.getSelectedRow();

                // Check if a row is selected
                if (selectedRow == -1) {
                    // No row is selected, show an error message
                    JOptionPane.showMessageDialog(table2, "Please select a row to cancel the consultation", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    // A row is selected, delete the row from the table and the consultations list
                    // Get the consultation object for the selected row
                    Consultation consultation = consultations.get(selectedRow);

                    // Delete the consultation from the consultations list
                    consultations.remove(consultation);

                    // Remove the row from the table model
                    model2.removeRow(selectedRow);
                }
            }
        });
        // Add an action listener to the save button
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Save the consultations to a file
                Consultation.saveConsultationsToFile("consultations.csv");
            }
        });
        return panel3;
    }
        // Create a method to create the button panel
    public static JPanel createButtonPanel() {
        // Create a panel to hold the buttons
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new BorderLayout());

        // Add empty panels as spacers
        buttonPanel.add(new JPanel(), BorderLayout.WEST);
        buttonPanel.add(new JPanel(), BorderLayout.EAST);

        // Initialize the buttons
        homeButton = new JButton("Home");
        button1 = new JButton("Doctor Information Table");
        button2 = new JButton("Book a Consultation");
        button3 = new JButton("Consultation Information Table");

        // Add the buttons to the panel
        buttonPanel.add(homeButton);
        buttonPanel.add(button1);
        buttonPanel.add(button2);
        buttonPanel.add(button3);

        JPanel buttonGridPanel = createButtonGridPanel();
        // Add the button grid panel to the button panel
        buttonPanel.add(buttonGridPanel, BorderLayout.CENTER);

        // Return the button panel
        return buttonPanel;
    }

    public static JPanel createButtonGridPanel() {
        // Create buttons
        homeButton = new JButton("Home");
        homeButton.setFont(new Font("Arial", Font.BOLD, 18));
        homeButton.setMargin(new Insets(10, 10, 10, 10));
        homeButton.setBorder(BorderFactory.createEmptyBorder(9, 113, 9, 113));
        button1 = new JButton("Doctor Information Table");
        button1.setFont(new Font("Arial", Font.BOLD, 18));
        button1.setMargin(new Insets(10, 10, 10, 10));
        button1.setBorder(BorderFactory.createEmptyBorder(9, 30, 9, 30));
        button2 = new JButton("Book A Consultation");
        button2.setFont(new Font("Arial", Font.BOLD, 18));
        button2.setMargin(new Insets(10, 10, 10, 10));
        button2.setBorder(BorderFactory.createEmptyBorder(9, 48, 9, 48));
        button3 = new JButton("Consultation Information Table");
        button3.setFont(new Font("Arial", Font.BOLD, 18));
        button3.setMargin(new Insets(10, 10, 10, 10));
        button3.setBorder(BorderFactory.createEmptyBorder(9, 7, 9, 7));

        // Add action listeners to the buttons
        homeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Show Home panel when homeButton is clicked
                cardLayout.show(panelHolder, "Home Panel");
            }
        });
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Show panel 1 when button1 is clicked
                cardLayout.show(panelHolder, "Doctor Information Panel");
            }
        });
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Show panel 2 when button2 is clicked
                cardLayout.show(panelHolder, "Book A Consultation Panel");
            }
        });
        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Show panel 3 when button3 is clicked
                cardLayout.show(panelHolder, "Consultation Information Panel");
            }
        });

        // Create the button grid panel
        JPanel buttonGridPanel = new JPanel();
        buttonGridPanel.setLayout(new BoxLayout(buttonGridPanel, BoxLayout.Y_AXIS));

        // Add some glue above the buttons
        buttonGridPanel.add(Box.createVerticalGlue());

        // Add the buttons to the button grid panel
        buttonGridPanel.add(homeButton);
        buttonGridPanel.add(Box.createVerticalStrut(30));
        buttonGridPanel.add(button1);
        buttonGridPanel.add(Box.createVerticalStrut(30));
        buttonGridPanel.add(button2);
        buttonGridPanel.add(Box.createVerticalStrut(30));
        buttonGridPanel.add(button3);

        // Add some glue below the buttons
        buttonGridPanel.add(Box.createVerticalGlue());
        return buttonGridPanel;
    }

    // Create a method to create the panelHolder
    public static JPanel createPanelHolder() {
        panelHolder = new JPanel();
        cardLayout = new CardLayout();
        panelHolder.setLayout(cardLayout);

        // Add the home panel to the panel holder
        JPanel homePanel = createHomePanel();
        panelHolder.add(homePanel, "Home Panel");

        // Add the doctor information panel to the panel holder
        JPanel doctorInformationPanel = createDoctorInformationPanel(deleteDoctorMedicalLicenceNumber);
        panelHolder.add(doctorInformationPanel, "Doctor Information Panel");

        // Add the book a consultation panel to the panel holder
        JPanel bookAConsultationPanel = createBookAConsultationPanel();
        panelHolder.add(bookAConsultationPanel, "Book A Consultation Panel");

        // Add the consultation information panel to the panel holder
        JPanel consultationInformationPanel = createConsultationInformationTable();
        panelHolder.add(consultationInformationPanel, "Consultation Information Panel");

        // Show panel
        cardLayout.show(panelHolder, "Home Panel");
        return panelHolder;
    }

    // Method to parse a date input in the format dd/mm/yyyy
    private static Calendar parseDate(String date) {
        // Try to parse the date
        try {
            // Split the date into its parts
            String[] parts = date.split("/");
            // Create a new calendar object
            Calendar calendar = Calendar.getInstance();
            // Validate the month
            int month = Integer.parseInt(parts[1]);
            if (month < 1 || month > 12) {
                // Return null if the month is invalid
                return null;
            }
            // Validate the day
            int day = Integer.parseInt(parts[0]);
            int numDaysInMonth = 31; // Assume that the month has 31 days
            if (month == 2) { // If the month is February, set the number of days to 28 or 29
                numDaysInMonth = 28;
                int year = Integer.parseInt(parts[2]);
                if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
                    // If the year is a leap year, set the number of days to 29
                    numDaysInMonth = 29;
                }
            } else if (month == 4 || month == 6 || month == 9 || month == 11) {
                // If the month is April, June, September, or November, set the number of days to 30
                numDaysInMonth = 30;
            }
            if (day < 1 || day > numDaysInMonth) {
                // Return null if the day is invalid for the given month
                return null;
            }
            // Set the year, month, and day of the calendar
            calendar.set(Integer.parseInt(parts[2]), month - 1, day);
            // Return the calendar object
            return calendar;
        } catch (Exception e) {
            // Return null if the date is invalid
            return null;
        }
    }

    public static String encrypt(String value) throws Exception {
        Key key = generateKey();
        Cipher cipher = Cipher.getInstance(SkinConsultationCentreGUI.ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedByteValue = cipher.doFinal(value.getBytes("utf-8"));
        return Base64.getEncoder().encodeToString(encryptedByteValue);
    }

    public static String decrypt(String value) throws Exception {
        Key key = generateKey();
        Cipher cipher = Cipher.getInstance(SkinConsultationCentreGUI.ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decryptedValue64 = Base64.getDecoder().decode(value);
        byte[] decryptedByteValue = cipher.doFinal(decryptedValue64);
        return new String(decryptedByteValue, "utf-8");
    }

    private static Key generateKey() throws Exception {
        return new SecretKeySpec(SkinConsultationCentreGUI.KEY.getBytes(), SkinConsultationCentreGUI.ALGORITHM);
    }

    public static void viewGui(JFrame frame) {
        // Initialize the panel holder and button panel
        panelHolder = SkinConsultationCentreGUI.createPanelHolder();
        buttonPanel = SkinConsultationCentreGUI.createButtonPanel();

        // Add the panel holder to the frame
        frame.add(panelHolder, BorderLayout.CENTER);
        // Add the button panel to the frame
        frame.add(buttonPanel, BorderLayout.EAST);

        // Display the frame
        frame.setVisible(true);
    }
}

