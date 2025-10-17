# TP5DPBO2425C2

# 📝 Tugas Praktikum 5  

## Janji  
Saya **Repa Pitriani** dengan NIM **2402499** mengerjakan Tugas Praktikum 5 dalam mata kuliah **Desain dan Pemrograman Berorientasi Objek** untuk keberkahanNya, maka saya tidak melakukan kecurangan seperti yang telah dispesifikasikan. Aamiin.  

---

## Penjelasan Umum  
Program ini merupakan **lanjutan dari TP4**.  
- Pada TP4, data produk masih disimpan di **ArrayList**.  
- Pada TP5, program sudah menggunakan **database MySQL** sebagai penyimpanan utama.  

## Perubahan / Tambahan dari TP4  
- CRUD langsung ke database (insert, update, delete, select).  
- Validasi input kosong sebelum insert / update.  
- Validasi ID duplikat → tidak bisa menambahkan produk dengan ID yang sudah ada di database.  
- JTable sekarang diisi langsung dari hasil query database, bukan lagi dari ArrayList.  

---

## Struktur Kelas  

### 1. Product.java  
Kelas ini berfungsi sebagai **model data produk**.  

**Atribut:**  
- `id : String` → ID unik produk.  
- `nama : String` → nama produk.  
- `harga : double` → harga produk.  
- `kategori : String` → kategori produk (Elektronik, Makanan, Minuman, Pakaian, Alat Tulis).  
- `garansi : String` → status garansi ("Ada" / "Tidak").  

**Method:**  
- Constructor.  
- Getter dan Setter untuk semua atribut.  

### 2. Database.java  
Kelas ini bertugas **menghubungkan aplikasi dengan MySQL**.  

**Atribut:**  
- `connection` → objek untuk membuka koneksi ke database.  
- `statement` → objek untuk menjalankan query SQL.  

**Method:**  
- `Database()` → constructor, otomatis buka koneksi begitu objek dibuat.  
- `selectQuery(String query)` → ambil data (SELECT), hasil berupa ResultSet.  
- `insertUpdateDeleteQuery(String query)` → eksekusi query INSERT, UPDATE, DELETE.  

### 3. ProductMenu.java  
Kelas ini adalah **GUI utama** (`extends JFrame`) untuk mengelola data produk.  

**Komponen GUI:**  
- JTable → menampilkan daftar produk dari database.  
- JTextField → input ID, Nama, Harga.  
- JComboBox → memilih kategori produk.  
- JRadioButton → memilih status garansi.  
- JButton → Add/Update, Delete, Cancel.  

**Fitur Utama:**  
- `insertData()` → tambah produk baru (dengan validasi kosong + validasi ID duplicate).  
- `updateData()` → ubah produk yang dipilih, juga ada validasi ID duplicate jika ID diubah.  
- `deleteData()` → hapus produk yang dipilih (dengan konfirmasi dialog).  
- `clearForm()` → reset form input.  
- `setTable()` → load data produk dari database ke JTable.  

Program menampilkan data produk lewat tabel dengan kolom No, ID, Nama, Harga, Kategori, dan Garansi. Setiap perubahan data langsung diperbarui dengan `setTable()`.  
Produk baru bisa ditambah lewat form input dan tombol **Add**, lalu data tersimpan dengan `insertData()` dan tabel otomatis ter-update. Kalau ingin mengubah data, cukup klik baris tabel, form terisi otomatis, tombol berubah jadi **Update**, lalu simpan dengan `updateData()`.  
Untuk menghapus, pilih baris dan tekan **Delete**, konfirmasi Yes, maka data hilang melalui `deleteData()`. Sedangkan tombol **Cancel** dipakai untuk mengosongkan form dan mengembalikan tombol ke kondisi awal.  

---

## Kesimpulan  
TP5 menyempurnakan TP4 dengan cara:  
- Memindahkan penyimpanan data dari ArrayList ke MySQL.  
- Menambahkan validasi input & validasi duplikasi ID.  
- Struktur kelas tetap sama, namun CRUD sudah sepenuhnya terhubung ke database.  

