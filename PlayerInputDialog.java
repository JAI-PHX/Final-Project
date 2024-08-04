import javax.swing.*;

import java.awt.*;

import java.util.ArrayList;

import java.util.List;

//PlayerInputDialog is used to get player names
public class PlayerInputDialog extends JDialog {

    private List<JTextField> nameFields;

    private boolean confirmed;

    public PlayerInputDialog(JFrame parent) {

        super(parent, "Enter Player Names", true);

        nameFields = new ArrayList<>();

        confirmed = false;

        //Use GritLayout for user names
        setLayout(new GridLayout(4, 2));

        for (int i = 0; i < 3; i++) {

            //Jlabel for player names
            add(new JLabel("Player " + (i + 1) + " Name:"));

            //JTextField for username input
            JTextField nameField = new JTextField();

            nameFields.add(nameField);

            add(nameField);
        }

        //JButton for username confirmation
        JButton confirmButton = new JButton("Confirm");

        confirmButton.addActionListener(e -> {

            confirmed = true;

            setVisible(false);

        });

        add(confirmButton);

        setSize(400, 200);

        setLocationRelativeTo(parent);
    }

    public List<String> getPlayerNames() {

        List<String> names = new ArrayList<>();

        if (confirmed) {

            for (JTextField nameField : nameFields) {

                names.add(nameField.getText().trim());

            }
        }
        return names;
    }

    public boolean isConfirmed() {

        return confirmed;
    }
}
