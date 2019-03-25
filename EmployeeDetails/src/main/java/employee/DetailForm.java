package employee;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DetailForm extends JFrame {

    public JTextField corpKeytext;
    public JPanel container;
    public JTextField nametext ;
    public JTextField emailtext;
    public JTextField tribetext;
    public JButton buttonSubmit;
    public JOptionPane erreurMessage = new JOptionPane() ;

    public DetailForm() {

        buttonSubmit.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                String corpKey = corpKeytext.getText() ;
                String name = nametext.getText() ;
                String email = emailtext.getText() ;
                String tribe = tribetext.getText() ;
                boolean erreur = false ;
                String errorMessage = " ";

                if (name.isEmpty() || corpKey.isEmpty()) {
                    erreur = true ;
                    errorMessage = "Corp Key and Name can't be empty" ;
                }

                if (corpKey.length() > 10) {
                    erreur = true ;
                    errorMessage = "Corp Key too long" ;
                }

                if (corpKey.indexOf("'") != -1) {
                    errorMessage = " character ' is not authorized in corp key" ;
                    erreur = true ;
                }

                if (!erreur) {
                    new MySqlConnection();
                    MySqlConnection.searchEmployee(corpKey);
                    String corpKeyFound = MySqlConnection.personne.corpKey;
                    System.out.println("corpkey trouvé " + corpKeyFound);

                    if (corpKeyFound != null) {
                        erreur = true;
                        errorMessage = "Corp Key already exist";
                    }
                }

                if (erreur) {
                    erreurMessage.showMessageDialog(null,
                            errorMessage,
                            " Error ",
                            JOptionPane.WARNING_MESSAGE);
                } else {
                    MySqlConnection.openDB();
                    MySqlConnection.insertEmployee(corpKey, name, tribe, email);

                }

            }
        });

    }

    public static void main(String[] args) {

        JFrame detailform = new JFrame("Detail Form") ;
        detailform.setSize(400, 600);
        detailform.setContentPane(new DetailForm().container); ;
        detailform.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        detailform.setVisible(true);

// Verification of employees list
//        System.out.println("liste des employés");
//        MySqlConnection.readAllEmployee();


    }

}
