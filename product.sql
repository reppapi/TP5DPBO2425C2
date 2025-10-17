-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Waktu pembuatan: 17 Okt 2025 pada 11.34
-- Versi server: 10.4.32-MariaDB
-- Versi PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `product_db`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `product`
--

CREATE TABLE `product` (
  `id` varchar(10) NOT NULL,
  `nama` varchar(100) NOT NULL,
  `harga` double NOT NULL,
  `kategori` varchar(50) NOT NULL,
  `garansi` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `product`
--

INSERT INTO `product` (`id`, `nama`, `harga`, `kategori`, `garansi`) VALUES
('P001', 'Laptop Asus', 8500000, 'Elektronik', 'Ada'),
('P002', 'Mouse Logitech', 350000, 'Elektronik', 'Ada'),
('P003', 'Keyboard Mechanical', 750000, 'Elektronik', 'Ada'),
('P004', 'Roti Tawar', 15000, 'Makanan', 'Tidak'),
('P005', 'Susu UHT', 12000, 'Minuman', 'Tidak'),
('P006', 'Kemeja Putih', 125000, 'Pakaian', 'Tidak'),
('P007', 'Celana Jeans', 200000, 'Pakaian', 'Tidak'),
('P008', 'Pensil 2B', 3000, 'Alat Tulis', 'Tidak'),
('P009', 'Buku Tulis baru', 8000, 'Alat Tulis', 'Tidak'),
('P010', 'Air Mineral', 5000, 'Minuman', 'Tidak'),
('P011', 'Smartphone Samsung', 4500000, 'Elektronik', 'Ada'),
('P012', 'Kue Brownies', 25000, 'Makanan', 'Tidak'),
('P013', 'Jaket Hoodie', 180000, 'Pakaian', 'Tidak'),
('P014', 'Pulpen Gel', 5000, 'Alat Tulis', 'Tidak'),
('P015', 'Teh Botol', 8000, 'Minuman', 'Tidak'),
('P016', 'Eskrim tianglala', 8000, 'Makanan', 'Ada');

--
-- Indexes for dumped tables
--

--
-- Indeks untuk tabel `product`
--
ALTER TABLE `product`
  ADD PRIMARY KEY (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
