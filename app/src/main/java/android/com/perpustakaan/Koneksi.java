package android.com.perpustakaan;

public class Koneksi {

    public static final String koneksi = "http://192.168.43.25/perpusbackend/";
    //public static final String koneksi = "http://192.168.43.216/perpusbackend/";

    public static final String simpankategori = koneksi+"simpan_kategori.php";
    public static final String tampil_kategori = koneksi+"tampil_kategori.php";
    public static final String edit_kategori = koneksi+"edit_kategori.php";
    public static final String hapus_kategori = koneksi+"hapus_kategori.php";
    public static final String cari_kategori = koneksi+"cari_kategori.php";

    public static final String simpan_buku = koneksi+"simpan_buku.php";
    public static final String tampil_buku = koneksi+"tampil_buku.php";
    public static final String tampil_buku_by_id= koneksi+"tampil_buku_by_id.php";
    public static final String edit_buku = koneksi+"edit_buku.php";
    public static final String hapus_buku = koneksi+"hapus_buku.php";
    public static final String cari_buku = koneksi+"cari_buku.php";

    public static final String tampil_pinjam = koneksi+"tampil_pinjam.php";
    public static final String simpan_pinjam = koneksi+"simpan_pinjam.php";
    public static final String update_status_pinjam = koneksi+"update_status_pinjam.php";
    public static final String cari_pinjam = koneksi+"cari_pinjam.php";

    public static final String id_kategori_buku = "id_kategori_buku";
    public static final String nama_kategori = "nama_kategori";

    public static final String id_buku = "id_buku";
    public static final String cover_buku = "cover_buku";
    public static final String judul_buku = "judul_buku";
    public static final String penulis = "penulis";
    public static final String stok = "stok";

    public static final String id_pinjam = "id_pinjam";
    public static final String nama_peminjam = "nama_peminjam";
    public static final String jumlah_pinjam = "jumlah_pinjam";
    public static final String waktu_pinjam = "waktu_pinjam";
    public static final String waktu_kembali = "waktu_kembali";
    public static final String status_pinjam = "status_pinjam";


}