package employee;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SearchForm extends JFrame implements ActionListener {

    public JTextField corpKeySearch;
    public JPanel container;
    public JButton buttonSearch;
    public JPanel panneauTable;
    public JTable employees;
    public JScrollPane scrollTable;
    public JOptionPane erreurMessage = new JOptionPane();
    DefaultTableModel model = new DefaultTableModel();
    MySqlConnection connection = new MySqlConnection () ;
    XlsFile xlsFile = new XlsFile() ;

    public SearchForm() {
        createGUI();
        initializeTable();

        this.setTitle("titre");
        this.setSize(500, 600);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {

        boolean erreur = false ;
        String errorMessage = " ";
        String corpKey = corpKeySearch.getText();

        if (corpKey.isEmpty()) {
            erreur = true ;
            errorMessage = "Corp Key and Name can't be empty" ;
        }

        if (corpKey.indexOf("'") != -1) {
            errorMessage = " character ' is not authorized in corp key" ;
            erreur = true ;
        }

        if (!erreur) {

            MySqlConnection.searchEmployee(corpKey);

            String corpKeyFound = MySqlConnection.personne.corpKey;
            String nameFound = MySqlConnection.personne.name;
            String emailFound = MySqlConnection.personne.email;
            String tribeFound = MySqlConnection.personne.tribe;

            if (corpKeyFound == null) {
                erreurMessage.showMessageDialog(null,
                        "Person not found ", "Error ",
                        JOptionPane.WARNING_MESSAGE);
            } else {
//   Display in JTABLE
                model.addRow(new String[]{corpKeyFound, nameFound, emailFound, tribeFound});
//   Write in XL
                xlsFile.XlsWrite(corpKeyFound, nameFound, emailFound, tribeFound);
            }
        } else {
            erreurMessage.showMessageDialog(null,
                    errorMessage,
                    " Error ",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

     private void createGUI() {
            scrollTable = new JScrollPane(employees) ;
            buttonSearch.addActionListener(this);
            panneauTable.add(scrollTable, BorderLayout.SOUTH) ;
            this.setContentPane(container);
     }

     private void initializeTable() {
            employees.setModel(model);
            final String[] columnNames = {"Corp Key", "Name", "Email", "Tribe"};
            model.setColumnIdentifiers(columnNames);
     }

    public static void main(String[] args) {
        new SearchForm() ;
    }
}



