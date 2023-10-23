package gui;

import engines.AllocationGenerator;
import engines.CalendarGenerator;
import sun.applet.Main;
import util.ResourcesReader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Hashtable;

public class MainWindow extends JFrame {

    public MainWindow() {
        initComponent();
        setTitle("Resources Map Generator");
    }

    private void initComponent() {

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        // START Generate CSV file
        JPanel generateMappingPanel = new JPanel();
        generateMappingPanel.setLayout(new BoxLayout(generateMappingPanel, BoxLayout.PAGE_AXIS));

        JLabel yearLbl = new JLabel("Year:  ");
        JTextField yearTxt = new JTextField(4);
        JLabel monthLbl = new JLabel(" Month: ");
        JTextField monthTxt = new JTextField(2);

        JPanel csvInputPanel = new JPanel();
        csvInputPanel.setLayout(new BoxLayout(csvInputPanel, BoxLayout.LINE_AXIS));

        JLabel outFilenameLabel = new JLabel(" Output csv filename: ");
        JTextField outFilenameTxt = new JTextField(10);

        csvInputPanel.add(yearLbl);
        csvInputPanel.add(yearTxt);
        csvInputPanel.add(monthLbl);
        csvInputPanel.add(monthTxt);
        csvInputPanel.add(outFilenameLabel);
        csvInputPanel.add(outFilenameTxt);

        JButton generateCsvButton = new JButton("GENERATE MAPPING");

        JPanel csvTitlePanel = new JPanel();
        csvTitlePanel.add(new JLabel("CSV allocation generation:"));

        generateMappingPanel.add(csvTitlePanel);
        generateMappingPanel.add(Box.createVerticalStrut(10));
        generateMappingPanel.add(csvInputPanel);
        generateMappingPanel.add(Box.createVerticalStrut(10));
        generateMappingPanel.add(generateCsvButton);

        // END Generate CSV file

        // START Generate calendar file

        JPanel generateCalendarPanel = new JPanel();
        generateCalendarPanel.setLayout(new BoxLayout(generateCalendarPanel, BoxLayout.PAGE_AXIS));

        JButton inputCsvButton = new JButton("Input file: NONE");

        JLabel outCalendarLabel = new JLabel("Output calendar filename: ");
        JTextField outCalendarTxt = new JTextField(10);

        JPanel outCalFilenamePanel = new JPanel();
        outCalFilenamePanel.setLayout(new BoxLayout(outCalFilenamePanel, BoxLayout.LINE_AXIS));
        outCalFilenamePanel.add(outCalendarLabel);
        outCalFilenamePanel.add(outCalendarTxt);

        JButton generateCalendarButton = new JButton("GENERATE CALENDAR");

        JPanel htmlTitlePanel = new JPanel();
        htmlTitlePanel.add(new JLabel("HTML calendar generation:"));

        generateCalendarPanel.add(htmlTitlePanel);
        generateCalendarPanel.add(Box.createVerticalStrut(10));
        generateCalendarPanel.add(inputCsvButton);
        generateCalendarPanel.add(outCalFilenamePanel);
        generateCalendarPanel.add(generateCalendarButton);

        // END Generate calendar file

        JPanel logPanel = new JPanel();
        logPanel.setLayout(new BorderLayout());
        JTextArea logText = new JTextArea(5,70);
        JScrollPane pane = new JScrollPane(logText);

        logPanel.add(new JLabel("Log: "), BorderLayout.NORTH);
        logPanel.add(pane, BorderLayout.CENTER);

        // Add to the main panel
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.PAGE_AXIS));
        formPanel.add(generateMappingPanel);
        formPanel.add(Box.createVerticalStrut(10));
        formPanel.add(new JSeparator(SwingConstants.HORIZONTAL));
        formPanel.add(Box.createVerticalStrut(10));
        formPanel.add(generateCalendarPanel);
        formPanel.add(Box.createVerticalStrut(10));

        formPanel.add(new JSeparator(SwingConstants.HORIZONTAL));

        mainPanel.add(formPanel, BorderLayout.NORTH);
        mainPanel.add(logPanel, BorderLayout.CENTER);

        add(mainPanel);
        pack();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        generateCsvButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int year = Integer.parseInt(yearTxt.getText());
                    int month = Integer.parseInt(monthTxt.getText());

                    Hashtable<String, Integer> resources =
                            ResourcesReader.readResourcesFromCSV("conf/resources.csv");

                    String outfile = outFilenameTxt.getText();

                    if (outfile.trim().isEmpty()) {
                        logText.append("Empty csv filename! Cannot generate!");
                        return;
                    }

                    if (!outfile.endsWith("csv")) {
                        outfile += ".csv";
                    }

                    AllocationGenerator generator = new AllocationGenerator();
                    generator.generateCalendarAllocation(outfile, year, month, resources);

                    inputCsvButton.setText("Input file: " + outfile);
                    logText.setText("Generated " + outfile);
                    repaint();

                } catch (Exception ex) {
                    logText.append("\nCould not generate: " + ex.getMessage());
                    repaint();
                }
            }
        });

        inputCsvButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                 JFileChooser fc = new JFileChooser();
                 int returnVal = fc.showOpenDialog(MainWindow.this);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fc.getSelectedFile();
                    inputCsvButton.setText("Input file: " + file.getName());

                } else {
                    logText.append("Open command cancelled by user.");
                    repaint();
                }
            }
        });

        generateCalendarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    String txt = inputCsvButton.getText();
                    String inputCsv = txt.split(":")[1].trim();

                    if (inputCsv.isEmpty()) {
                        logText.append("\nPlease supply a valid csv filename!");
                        return;
                    }

                    String outputhtml = outCalendarTxt.getText().trim();

                    if (outputhtml.isEmpty()) {
                        logText.append("\nPlease supply a valid output filename!");
                        repaint();
                        return;
                    }

                    if (!outputhtml.endsWith("html")) {
                        outputhtml += ".html";
                    }

                    CalendarGenerator generator = new CalendarGenerator();
                    generator.generateCalendarHTML(inputCsv, "Calendário Presencial equipa ASD", outputhtml);

                    logText.setText("Generated " + outputhtml);
                    pack();
                    repaint();

                } catch (Exception ex) {
                    logText.append("\nCould not generate: " + ex.getMessage());
                    repaint();
                }
            }
        });
    }

    public static void main(String[] args) {
        MainWindow mw = new MainWindow();
        mw.setLocationRelativeTo(null);

        mw.setVisible(true);
    }

}
