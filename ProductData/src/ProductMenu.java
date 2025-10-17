import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.Color;
import java.awt.Font;

public class ProductMenu extends JFrame {
    public static void main(String[] args) {
        // buat object window
        ProductMenu menu = new ProductMenu();

        // atur ukuran window
        menu.setSize(700, 600);

        // letakkan window di tengah layar
        menu.setLocationRelativeTo(null);

        // isi window
        menu.setContentPane(menu.mainPanel);

        // ubah warna background
        menu.getContentPane().setBackground(Color.WHITE);

        // tampilkan window
        menu.setVisible(true);

        // agar program ikut berhenti saat window diclose
        menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    // index baris yang diklik
    private int selectedIndex = -1;
    // list untuk menampung semua produk
    private Database database;

    private JPanel mainPanel;
    private JTextField idField;
    private JTextField namaField;
    private JTextField hargaField;
    private JTable productTable;
    private JButton addUpdateButton;
    private JButton cancelButton;
    private JComboBox<String> kategoriComboBox;
    private JRadioButton garansiAdaRadio;
    private JRadioButton garansiTidakRadio;
    private ButtonGroup garansiGroup;
    private JButton deleteButton;
    private JLabel titleLabel;
    private JLabel idLabel;
    private JLabel namaLabel;
    private JLabel hargaLabel;
    private JLabel kategoriLabel;
    private JLabel garansiLabel;

    // constructor
    public ProductMenu() {
        database = new Database();

        // isi tabel produk
        productTable.setModel(setTable());

        // ubah styling title
        titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD, 20f));

        // atur isi combo box
        String[] kategoriData = {"???", "Elektronik", "Makanan", "Minuman", "Pakaian", "Alat Tulis"};
        kategoriComboBox.setModel(new DefaultComboBoxModel<>(kategoriData));

        // radio button garansi
        // bikin group untuk radio button
        garansiGroup = new ButtonGroup();
        garansiGroup.add(garansiAdaRadio);
        garansiGroup.add(garansiTidakRadio);

        // default pilihan
        garansiTidakRadio.setSelected(true);

        // sembunyikan button delete
        deleteButton.setVisible(false);

        // saat tombol add/update ditekan
        addUpdateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedIndex == -1) {
                    insertData();
                } else {
                    updateData();
                }
            }
        });

        // saat tombol delete ditekan
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteData();
            }
        });

        // saat tombol cancel ditekan
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearForm();
            }
        });

        // saat salah satu baris tabel ditekan
        productTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // ubah selectedIndex menjadi baris tabel yang diklik
                selectedIndex = productTable.getSelectedRow();

                // simpan value textfield dan combo box
                String curId = productTable.getModel().getValueAt(selectedIndex, 1).toString();
                String curNama = productTable.getModel().getValueAt(selectedIndex, 2).toString();
                String curHarga = productTable.getModel().getValueAt(selectedIndex, 3).toString();
                String curKategori = productTable.getModel().getValueAt(selectedIndex, 4).toString();
                String curGaransi = productTable.getModel().getValueAt(selectedIndex, 5).toString();

                // ubah isi textfield dan combo box
                idField.setText(curId);
                namaField.setText(curNama);
                hargaField.setText(curHarga);
                kategoriComboBox.setSelectedItem(curKategori);

                // radio button garansi
                if (curGaransi.equals("Ada")) {
                    garansiAdaRadio.setSelected(true);
                } else {
                    garansiTidakRadio.setSelected(true);
                }

                // ubah button "Add" menjadi "Update"
                addUpdateButton.setText("Update");

                // tampilkan button delete
                deleteButton.setVisible(true);

            }
        });
    }

    public final DefaultTableModel setTable() {
        // tentukan kolom tabel
        Object[] cols = {"No", "ID Produk", "Nama", "Harga", "Kategori", "Garansi"};

        // buat objek tabel dengan kolom yang sudah dibuat
        DefaultTableModel tmp = new DefaultTableModel(null, cols);

        // isi tabel dengan listProduct
        try {
            ResultSet resultSet = database.selectQuery("SELECT * FROM product");
            int i = 0;
            while (resultSet.next()) {
                Object[] row = {
                        i + 1,
                        resultSet.getString("id"),
                        resultSet.getString("nama"),
                        String.format("%.2f", resultSet.getDouble("harga")),
                        resultSet.getString("kategori"),
                        resultSet.getString("garansi")
                };
                tmp.addRow(row);
                i++;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error loading data: " + e.getMessage());
        }
        return tmp;
    }

    public void insertData() {
        // VALIDASI: Cek field kosong
        if (idField.getText().isEmpty() || namaField.getText().isEmpty() ||
                hargaField.getText().isEmpty() || kategoriComboBox.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(null, "Semua field harus diisi!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            // ambil value dari textfield dan combobox
            String id = idField.getText();
            String nama = namaField.getText();
            double harga = Double.parseDouble(hargaField.getText());
            String kategori = kategoriComboBox.getSelectedItem().toString();
            String garansi = garansiAdaRadio.isSelected() ? "Ada" : "Tidak";

            // VALIDASI: Cek ID duplikat
            ResultSet check = database.selectQuery("SELECT * FROM product WHERE id = '" + id + "'");
            if (check.next()) {
                JOptionPane.showMessageDialog(null, "ID sudah ada! Gunakan ID lain.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // tambahkan data ke dalam list
            String sql = "INSERT INTO product VALUES ('" + id + "', '" + nama + "', " + harga + ", '" + kategori + "', '" + garansi + "')";
            database.insertUpdateDeleteQuery(sql);

            // update tabel
            productTable.setModel(setTable());

            // bersihkan form
            clearForm();

            // feedback
            JOptionPane.showMessageDialog(null, "Data berhasil ditambahkan");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Harga harus berupa angka!", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Database error: " + ex.getMessage());
        }
    }

    public void updateData() {
        // Validasi field kosong
        if (idField.getText().isEmpty() || namaField.getText().isEmpty() ||
                hargaField.getText().isEmpty() || kategoriComboBox.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(null, "Semua field harus diisi!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            // ambil data dari form
            String id = idField.getText();
            String nama = namaField.getText();
            double harga = Double.parseDouble(hargaField.getText());
            String kategori = kategoriComboBox.getSelectedItem().toString();
            String garansi = garansiAdaRadio.isSelected() ? "Ada" : "Tidak";

            // Ambil ID lama dari tabel (sebelum diupdate)
            String oldId = productTable.getModel().getValueAt(selectedIndex, 1).toString();

            // Cek duplikat hanya jika ID diubah
            if (!id.equals(oldId)) {
                ResultSet check = database.selectQuery("SELECT * FROM product WHERE id = '" + id + "'");
                if (check.next()) {
                    JOptionPane.showMessageDialog(null, "ID sudah ada! Gunakan ID lain.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            String sql = "UPDATE product SET id='" + id + "', nama='" + nama + "', harga=" + harga +
                    ", kategori='" + kategori + "', garansi='" + garansi + "' WHERE id='" + oldId + "'";
            database.insertUpdateDeleteQuery(sql);

            // update tabel
            productTable.setModel(setTable());

            // bersihkan form
            clearForm();

            // Beri feedback sukses
            JOptionPane.showMessageDialog(null, "Data berhasil diupdate");

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Harga harus berupa angka!", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Database error: " + ex.getMessage());
        }
    }

    public void deleteData() {
        if (selectedIndex >= 0) {
            // munculin konfirmasi dulu
            int confirm = JOptionPane.showConfirmDialog(
                    null,
                    "Apakah Anda yakin ingin menghapus produk ini?",
                    "Konfirmasi Delete",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
            );

            if (confirm == JOptionPane.YES_OPTION) {
                // Ambil ID dari baris yang dipilih repa baikh
                String idToDelete = productTable.getModel().getValueAt(selectedIndex, 1).toString();

                String sql = "DELETE FROM product WHERE id='" + idToDelete + "'";
                database.insertUpdateDeleteQuery(sql);

                // update tabel
                productTable.setModel(setTable());

                // bersihkan form
                clearForm();

                // feedback
                System.out.println("Delete berhasil");
                JOptionPane.showMessageDialog(null, "Data berhasil dihapus");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Pilih data yang ingin dihapus terlebih dahulu");
        }
    }

    public void clearForm() {
        // kosongkan semua texfield dan combo box
        idField.setText("");
        namaField.setText("");
        hargaField.setText("");
        kategoriComboBox.setSelectedItem(0);
        garansiAdaRadio.setSelected(true);

        // ubah button "Update" menjadi "Add"
        addUpdateButton.setText("Add");

        // sembunyikan button delete
        deleteButton.setVisible(false);

        // ubah selectedIndex menjadi -1 (tidak ada baris yang dipilih)
        selectedIndex = -1;
    }
}
