//Importam toate librariile de care avem nevoie pentru ca aplicatia sa functioneze corespunzator
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.Math;


class calculatorPredimensionareMicromotoreactor extends JFrame {
    private JTextField rotorSpeedField;
    private JTextField rotorDiameterField;
    private JTextField compressionRatioField;
    private JTextField exitAreaTextField;
    private JLabel thrustLabel;

    public calculatorPredimensionareMicromotoreactor() {
        setTitle("Estimare performante microaeroreactor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(450, 300);
        setLocationRelativeTo(null);

        // Create etichete si campuri pentru datele de intrare
        JLabel rotorSpeedLabel = new JLabel("Turatie rotor:");
        rotorSpeedField = new JTextField(7);
        JLabel rotorDiameterLabel = new JLabel("Diametru rotor compresor (mm) :");
        rotorDiameterField = new JTextField(7);
        JLabel compressionRatioLabel = new JLabel("Raport compresie compresor:");
        compressionRatioField = new JTextField(7);
        JLabel exitAreaLabel = new JLabel("Sectiune iesire ajutaj (m²):");
        exitAreaTextField = new JTextField(7);

        // Creere buton pentru calculare
        JButton calculateButton = new JButton("Calculeaza tractiune");
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculateThrust();
            }
        });

        // Creare eticheta pentru a afisa valoarea calculata
        thrustLabel = new JLabel();

        // Creare buton pentru salvarea datelor de intrare si rezultatul
        JButton saveButton = new JButton("Salvati cazul");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveData();
            }
        });

        // Creere panou si adaugarea elementelor
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(7, 2));
        panel.add(rotorSpeedLabel);
        panel.add(rotorSpeedField);
        panel.add(rotorDiameterLabel);
        panel.add(rotorDiameterField);
        panel.add(compressionRatioLabel);
        panel.add(compressionRatioField);
        panel.add(exitAreaLabel);
        panel.add(exitAreaTextField);
        panel.add(calculateButton);
        panel.add(new JLabel());
        panel.add(new JLabel("Forta de tractiune (N):"));
        panel.add(thrustLabel);
        panel.add(saveButton);

        // Adaugam fereastra
        add(panel);

        setVisible(true);
    }

    private void calculateThrust() {
        try {
            double lambda = Double.parseDouble(compressionRatioField.getText());
            double r = Double.parseDouble(rotorDiameterField.getText());
            double n = Double.parseDouble(rotorSpeedField.getText());
            double exitArea = Double.parseDouble(exitAreaTextField.getText());

            // Efectuam calculul


            double rho = 1.225; //
            double k = 1 / 1.4;
            double Pa = 101325; //presiunea atmosferica in Pascal
            double Va = 0;
            r = (r /2)/1000; //suprascriem raza ca sa fie in metri
            double ri = r/2 ;
            double v = rho * Math.pow(Math.PI, 2) * Math.pow(r, 2) * 2 * r * n;
            double thrust = ((v /60)+ v /3540) * ((((2 * Math.PI * n * ri)/60)+((Math.PI*2*r*n)/60))-Va)+(((Pa*(Math.pow(lambda, k)))-Pa)*exitArea);



            thrustLabel.setText(String.valueOf(thrust));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Va rog introduceti doar valori numerice", "Eroare", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void saveData() {
        try {
            String fileName = "caz.txt";
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));

            writer.write("Turatie rotor : " + rotorSpeedField.getText());
            writer.newLine();
            writer.write("Diametru rotor : " + rotorDiameterField.getText());
            writer.newLine();
            writer.write("Raport compresie: " + compressionRatioField.getText());
            writer.newLine();
            writer.write("Sectiune iesire ajutaj(m²): " + exitAreaTextField.getText());
            writer.newLine();
            writer.write("Forta de tractiune: " + thrustLabel.getText());

            writer.close();

            JOptionPane.showMessageDialog(this, "Caz salvat cu succes.", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Nu s-a putut salva.", "Eroare", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new calculatorPredimensionareMicromotoreactor();
            }
        });
    }
}
