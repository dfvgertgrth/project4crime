/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package cedov4;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

/**
 *
 * @author студент
 */
public class Cedov4 extends JFrame {

    private JTextField tfId, tfLocation, tfAccused, tfOfficer, tfSearch;
    private JComboBox<String> cbCrimeType;
    private DefaultTableModel tableModel;
    private JTable table;
    private ArrayList<CrimeRecord> crimeList = new ArrayList<>();

    public Cedov4() {
        setTitle("Система учёта преступлений");
        setSize(800, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Система учёта преступлений", JLabel.CENTER);
        title.setFont(new Font("Verdana", Font.BOLD, 22));
        title.setForeground(Color.RED);
        title.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(title, BorderLayout.NORTH);

        // Панель формы
        JPanel formPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        formPanel.setBackground(new Color(255, 240, 240));

        tfId = new JTextField();
        cbCrimeType = new JComboBox<>(new String[]{"Кража", "Убийство", "Мошенничество", "Нападение", "Киберпреступление"});
        tfLocation = new JTextField();
        tfAccused = new JTextField();
        tfOfficer = new JTextField();
        tfSearch = new JTextField();

        formPanel.add(new JLabel("ID преступления:"));
        formPanel.add(tfId);
        formPanel.add(new JLabel("Тип преступления:"));
        formPanel.add(cbCrimeType);
        formPanel.add(new JLabel("Место:"));
        formPanel.add(tfLocation);
        formPanel.add(new JLabel("Имя обвиняемого:"));
        formPanel.add(tfAccused);
        formPanel.add(new JLabel("Ответственный офицер:"));
        formPanel.add(tfOfficer);

        JButton btnAdd = new JButton("Добавить запись");
        JButton btnSearch = new JButton("Поиск по типу");
        formPanel.add(btnAdd);
        formPanel.add(btnSearch);

        add(formPanel, BorderLayout.WEST);

        // Таблица
        tableModel = new DefaultTableModel(new String[]{"ID", "Тип", "Место", "Обвиняемый", "Офицер"}, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Нижняя панель
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bottomPanel.setBackground(new Color(255, 240, 240));
        bottomPanel.add(new JLabel("Поиск по типу преступления:"));
        bottomPanel.add(tfSearch);

        JButton btnShowAll = new JButton("Показать все");
        JButton btnDelete = new JButton("Удалить выбранное");
        bottomPanel.add(btnShowAll);
        bottomPanel.add(btnDelete);
        add(bottomPanel, BorderLayout.SOUTH);

        // Действия
        btnAdd.addActionListener(e -> addRecord());
        btnSearch.addActionListener(e -> searchCrimeType());
        btnDelete.addActionListener(e -> deleteRecord());
        btnShowAll.addActionListener(e -> showAllRecords());

        // Пример записи
        crimeList.add(new CrimeRecord("C101", "Кража", "Москва", "Сергей Петров", "Офицер Иванов"));
        showAllRecords();
    }

    private void addRecord() {
        String id = tfId.getText().trim();
        String crimeType = cbCrimeType.getSelectedItem().toString();
        String location = tfLocation.getText().trim();
        String accused = tfAccused.getText().trim();
        String officer = tfOfficer.getText().trim();

        if (id.isEmpty() || location.isEmpty() || accused.isEmpty() || officer.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Заполните все поля.");
            return;
        }

        crimeList.add(new CrimeRecord(id, crimeType, location, accused, officer));
        showAllRecords();

        tfId.setText("");
        tfLocation.setText("");
        tfAccused.setText("");
        tfOfficer.setText("");
    }

    private void showAllRecords() {
        tableModel.setRowCount(0);
        for (CrimeRecord cr : crimeList) {
            tableModel.addRow(new Object[]{cr.id, cr.crimeType, cr.location, cr.accusedName, cr.officer});
        }
    }

    private void searchCrimeType() {
        String search = tfSearch.getText().trim();
        tableModel.setRowCount(0);
        for (CrimeRecord cr : crimeList) {
            if (cr.crimeType.equalsIgnoreCase(search)) {
                tableModel.addRow(new Object[]{cr.id, cr.crimeType, cr.location, cr.accusedName, cr.officer});
            }
        }
    }

    private void deleteRecord() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Выберите запись для удаления.");
            return;
        }
        String id = tableModel.getValueAt(row, 0).toString();
        crimeList.removeIf(cr -> cr.id.equals(id));
        showAllRecords();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Cedov4().setVisible(true));
    }
}

// Вспомогательный класс для хранения данных
class CrimeRecord {
    String id, crimeType, location, accusedName, officer;

    CrimeRecord(String id, String crimeType, String location, String accusedName, String officer) {
        this.id = id;
        this.crimeType = crimeType;
        this.location = location;
        this.accusedName = accusedName;
        this.officer = officer;
    }
}
