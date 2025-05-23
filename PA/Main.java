import java.util.*;
import java.util.stream.Collectors;

public class Main {
    // ENCAPSULATION: Penggunaan private static Map untuk menyimpan data users
    private static Map<String, User> users = new HashMap<>();
    private static Admin admin = new Admin("admin", "admin123");
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            try {
                System.out.println("\n=== Sistem Manajemen Gudang ===");
                System.out.println("1. Login");
                System.out.println("2. Register");
                System.out.println("0. Keluar");
                System.out.print("Pilihan: ");
                String choice = scanner.nextLine();
        
                switch (choice) {
                    case "1": login(); break;
                    case "2": registerUser(); break;
                    case "0": 
                        System.out.println("Terima kasih telah menggunakan sistem.");
                        return;
                    default: System.out.println("Pilihan tidak valid.");
                }
            } catch (Exception e) {
                System.out.println("Terjadi kesalahan: " + e.getMessage());
                scanner.nextLine(); // Clear buffer
            }
        }
    }

    // ENCAPSULATION: Method private untuk membatasi akses
    private static void login() {
        try {
            System.out.println("\nLogin sebagai:");
            System.out.println("1. Admin");
            System.out.println("2. User");
            System.out.print("Pilihan: ");
            String pilihan = scanner.nextLine();
        
            if (!pilihan.equals("1") && !pilihan.equals("2")) {
                throw new IllegalArgumentException("Pilihan harus 1 atau 2");
            }
        
            System.out.print("Username: ");
            String username = scanner.nextLine().trim();
            if (username.isEmpty()) {
                throw new IllegalArgumentException("Username tidak boleh kosong");
            }
        
            System.out.print("Password: ");
            String password = scanner.nextLine().trim();
            if (password.isEmpty()) {
                throw new IllegalArgumentException("Password tidak boleh kosong");
            }
        
            // POLYMORPHISM: Pemanggilan method login() yang berbeda tergantung tipe akun
            if (pilihan.equals("1")) {
                if (admin.login(username, password)) {
                    adminMenu();
                } else {
                    System.out.println("Login admin gagal.");
                }
            } else {
                if (users.containsKey(username) && users.get(username).login(username, password)) {
                    userMenu(users.get(username));
                } else {
                    System.out.println("Login user gagal.");
                }
            }
        } catch (Exception e) {
            System.out.println("Error saat login: " + e.getMessage());
        }
    }
    
    private static void registerUser() {
        try {
            System.out.print("Username baru: ");
            String user = scanner.nextLine().trim();
            
            if (user.isEmpty()) {
                throw new IllegalArgumentException("Username tidak boleh kosong");
            }
            
            if (user.equalsIgnoreCase(admin.getUsername())) {
                throw new IllegalArgumentException("Username sudah digunakan");
            }

            if (users.containsKey(user)) {
                throw new IllegalArgumentException("Username sudah digunakan");
            }

            System.out.print("Password: ");
            String pass = scanner.nextLine().trim();
            
            if (pass.isEmpty()) {
                throw new IllegalArgumentException("Password tidak boleh kosong");
            }
            
            if (pass.length() < 6) {
                throw new IllegalArgumentException("Password minimal 6 karakter");
            }

            if (pass.equals(admin.getPassword())) {
                throw new IllegalArgumentException("Password ini sudah digunakan oleh user lain");
            }

            for (User u : users.values()) {
                if (u.getPassword().equals(pass)) {
                    throw new IllegalArgumentException("Password ini sudah digunakan oleh user lain");
                }
            }

            // ENCAPSULATION: Pembuatan object User dengan constructor
            users.put(user, new User(user, pass));
            System.out.println("Pendaftaran berhasil.");
        } catch (Exception e) {
            System.out.println("Error saat registrasi: " + e.getMessage());
        }
    }

    private static void adminMenu() {
        while (true) {
            try {
                System.out.println("\n--- Menu Admin ---");
                System.out.println("1. Tambah Barang");
                System.out.println("2. Lihat Barang");
                System.out.println("3. Edit Barang");
                System.out.println("4. Hapus Barang");
                System.out.println("5. Lihat Peminjam");
                System.out.println("0. Logout");
                System.out.print("Pilihan: ");
                String c = scanner.nextLine();
                
                // POLYMORPHISM: Memanggil method dari interface OperasiGudang
                switch (c) {
                    case "1": admin.tambahBarang(); break;
                    case "2": admin.lihatDaftarBarang(); break;
                    case "3": admin.editBarang(); break;
                    case "4": admin.hapusBarang(); break;
                    case "5": admin.lihatDaftarPeminjam(); break;
                    case "0": return;
                    default: System.out.println("Pilihan tidak valid.");
                }
            } catch (Exception e) {
                System.out.println("Error di menu admin: " + e.getMessage());
                scanner.nextLine(); // Clear buffer
            }
        }
    }

    private static void userMenu(User user) {
        while (true) {
            try {
                System.out.println("\n--- Menu User ---");
                System.out.println("1. Lihat Barang");
                System.out.println("2. Pinjam Barang");
                System.out.println("3. Riwayat Peminjaman");
                System.out.println("4. Kembalikan Barang");
                System.out.println("0. Logout");
                System.out.print("Pilihan: ");
                String c = scanner.nextLine();
                
                // ASSOCIATION: User berinteraksi dengan Transaksi melalui method
                switch (c) {
                    case "1": user.lihatBarang(); break;
                    case "2": user.pinjamBarang(); break;
                    case "3": user.lihatRiwayat(); break;
                    case "4": user.kembalikanBarang(); break;
                    case "0": return;
                    default: System.out.println("Pilihan tidak valid.");
                }
            } catch (Exception e) {
                System.out.println("Error di menu user: " + e.getMessage());
                scanner.nextLine(); // Clear buffer
            }
        }
    }

    // ABSTRACTION: Abstract class sebagai blueprint untuk Admin dan User
    public static abstract class Akun {
        private String username;
        private String password;

        public Akun(String username, String password) {
            if (username == null || username.trim().isEmpty()) {
                throw new IllegalArgumentException("Username tidak boleh kosong");
            }
            if (password == null || password.trim().isEmpty()) {
                throw new IllegalArgumentException("Password tidak boleh kosong");
            }
            this.username = username;
            this.password = password;
        }

        // ABSTRACTION: Abstract method yang harus diimplementasikan subclass
        public abstract boolean login(String username, String password);

        // ENCAPSULATION: Getter methods
        public String getUsername() {
            return username;
        }

        public String getPassword() {
            return password;
        }

        protected boolean checkPassword(String inputPassword) {
            return this.password.equals(inputPassword);
        }
    }

    // INTERFACE: Kontrak untuk operasi gudang
    public interface OperasiGudang {
        void tambahBarang();
        void lihatDaftarBarang();
        void editBarang();
        void hapusBarang();
        void lihatDaftarPeminjam();
    }

    // INHERITANCE: Admin mewarisi Akun dan mengimplementasikan OperasiGudang
    public static class Admin extends Akun implements OperasiGudang {
        public Admin(String username, String password) {
            super(username, password);
        }

        // POLYMORPHISM: Override method login dari Akun
        @Override
        public boolean login(String username, String password) {
            return getUsername().equals(username) && checkPassword(password);
        }

        // POLYMORPHISM: Implementasi method dari interface OperasiGudang
        @Override
        public void tambahBarang() {
            Utility.tambahBarangHelper();
        }

        @Override
        public void lihatDaftarBarang() {
            Utility.lihatBarang();
        }

        @Override
        public void editBarang() {
            Utility.editBarang();
        }

        @Override
        public void hapusBarang() {
            Utility.hapusBarang();
        }

        @Override
        public void lihatDaftarPeminjam() {
            Utility.lihatTransaksi();
        }
    }

    // INHERITANCE: User mewarisi Akun
    public static class User extends Akun {
        // COMPOSITION: User memiliki List<Transaksi>
        private List<Transaksi> riwayat = new ArrayList<>();

        public User(String username, String password) {
            super(username, password);
        }

        // POLYMORPHISM: Override method login dari Akun
        @Override
        public boolean login(String username, String password) {
            return getUsername().equals(username) && checkPassword(password);
        }

        public void lihatBarang() {
            Utility.lihatBarang();
        }

        public void pinjamBarang() {
            Transaksi t = Utility.pinjamBarangHelper(getUsername());
            if (t != null) {
                riwayat.add(t);
            }
        }

        public void lihatRiwayat() {
            if (riwayat.isEmpty()) {
                System.out.println("Belum ada riwayat peminjaman.");
                return;
            }
            // METHOD OVERRIDING: Menggunakan toString() dari Transaksi
            for (Transaksi t : riwayat) {
                System.out.println(t);
            }
        }

        public void kembalikanBarang() {
            Utility.kembalikanBarang(getUsername());
        }
    }

    // ENCAPSULATION: Class Barang dengan private fields dan public methods
    public static final class Barang {
        // STATIC MEMBER: counter untuk generate ID
        private static int counter = 1;
        private final int idBarang;
        private String nama;
        private String jenis;
        private int jumlah;

        public Barang(String nama, String jenis, int jumlah) {
            if (nama == null || nama.trim().isEmpty()) {
                throw new IllegalArgumentException("Nama barang tidak boleh kosong");
            }
            if (jenis == null || jenis.trim().isEmpty()) {
                throw new IllegalArgumentException("Jenis barang tidak boleh kosong");
            }
            if (jumlah < 0) {
                throw new IllegalArgumentException("Jumlah barang tidak boleh negatif");
            }
            
            this.idBarang = counter++;
            this.nama = nama;
            this.jenis = jenis;
            this.jumlah = jumlah;
        }

        // ENCAPSULATION: Getter methods
        public int getIdBarang() {
            return idBarang;
        }

        public String getNama() {
            return nama;
        }

        public String getJenis() {
            return jenis;
        }

        public int getJumlah() {
            return jumlah;
        }

        // ENCAPSULATION: Setter methods dengan validasi
        public void setNama(String nama) {
            if (nama == null || nama.trim().isEmpty()) {
                throw new IllegalArgumentException("Nama barang tidak boleh kosong");
            }
            this.nama = nama;
        }

        public void setJenis(String jenis) {
            if (jenis == null || jenis.trim().isEmpty()) {
                throw new IllegalArgumentException("Jenis barang tidak boleh kosong");
            }
            this.jenis = jenis;
        }

        public void setJumlah(int jumlah) {
            if (jumlah < 0) {
                throw new IllegalArgumentException("Jumlah barang tidak boleh negatif");
            }
            this.jumlah = jumlah;
        }

        // METHOD OVERRIDING: Override toString()
        @Override
        public String toString() {
            return String.format("%d - %s | Jenis: %s | Jumlah: %d", 
                idBarang, nama, jenis, jumlah);
        }
    }

    // COMPOSITION: Transaksi terdiri dari User dan Barang
    public static class Transaksi {
        private String username;
        private String namaBarang;
        private int jumlah;
        private Date tanggal;

        public Transaksi(String username, String namaBarang, int jumlah) {
            if (username == null || username.trim().isEmpty()) {
                throw new IllegalArgumentException("Username tidak boleh kosong");
            }
            if (namaBarang == null || namaBarang.trim().isEmpty()) {
                throw new IllegalArgumentException("Nama barang tidak boleh kosong");
            }
            if (jumlah <= 0) {
                throw new IllegalArgumentException("Jumlah harus lebih dari 0");
            }
            
            this.username = username;
            this.namaBarang = namaBarang;
            this.jumlah = jumlah;
            this.tanggal = new Date();
        }

        public void setJumlah(int jumlah) {
            if (jumlah < 0) {
                throw new IllegalArgumentException("Jumlah tidak boleh negatif");
            }
            this.jumlah = jumlah;
        }

        // METHOD OVERRIDING: Override toString()
        @Override
        public String toString() {
            return String.format("[%s] User: %s meminjam %d %s", 
                tanggal, username, jumlah, namaBarang);
        }

        // ENCAPSULATION: Getter methods
        public String getUsername() {
            return username;
        }

        public String getNamaBarang() {
            return namaBarang;
        }

        public int getJumlah() {
            return jumlah;
        }
    }

    // SINGLETON PATTERN (Polos): Utility class dengan semua method static
    public static class Utility {
        // ENCAPSULATION: Data barang dan transaksi disimpan secara private
        private static List<Barang> daftarBarang = new ArrayList<>();
        private static List<Transaksi> transaksiList = new ArrayList<>();
        private static Scanner scanner = new Scanner(System.in);

        public static void tambahBarangHelper() {
            try {
                System.out.print("Nama barang: ");
                String nama = scanner.nextLine().trim();
                if (nama.isEmpty()) {
                    throw new IllegalArgumentException("Nama barang tidak boleh kosong");
                }
                
                System.out.print("Jenis barang: ");
                String jenis = scanner.nextLine().trim();
                if (jenis.isEmpty()) {
                    throw new IllegalArgumentException("Jenis barang tidak boleh kosong");
                }
                
                System.out.print("Jumlah: ");
                int jumlah = Integer.parseInt(scanner.nextLine());
                if (jumlah <= 0) {
                    throw new IllegalArgumentException("Jumlah harus lebih dari 0");
                }

                // COMPOSITION: Membuat objek Barang baru
                Barang b = new Barang(nama, jenis, jumlah);
                daftarBarang.add(b);
                System.out.println("Barang berhasil ditambahkan.\n");
            } catch (NumberFormatException e) {
                System.out.println("Jumlah harus berupa angka");
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        public static void lihatBarang() {
            if (daftarBarang.isEmpty()) {
                System.out.println("Belum ada barang yang tersedia di gudang.");
                return;
            }
            for (Barang b : daftarBarang) {
                System.out.println(b);
            }
        }

        public static void editBarang() {
            try {
                lihatBarang();
                if (daftarBarang.isEmpty()) return;
                
                System.out.print("ID Barang yang ingin diedit: ");
                int id = Integer.parseInt(scanner.nextLine());
                
                for (Barang b : daftarBarang) {
                    if (b.getIdBarang() == id) {
                        System.out.print("Nama baru (" + b.getNama() + "): ");
                        String nama = scanner.nextLine().trim();
                        if (!nama.isEmpty()) {
                            b.setNama(nama);
                        }
                        
                        System.out.print("Jenis baru (" + b.getJenis() + "): ");
                        String jenis = scanner.nextLine().trim();
                        if (!jenis.isEmpty()) {
                            b.setJenis(jenis);
                        }
                        
                        System.out.print("Jumlah baru (" + b.getJumlah() + "): ");
                        String jumlahStr = scanner.nextLine().trim();
                        if (!jumlahStr.isEmpty()) {
                            int jumlah = Integer.parseInt(jumlahStr);
                            b.setJumlah(jumlah);
                        }
                        
                        System.out.println("Barang berhasil diperbarui.");
                        return;
                    }
                }
                System.out.println("Barang dengan ID " + id + " tidak ditemukan.");
            } catch (NumberFormatException e) {
                System.out.println("Input jumlah harus berupa angka");
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        public static void hapusBarang() {
            try {
                lihatBarang();
                if (daftarBarang.isEmpty()) return;
                
                System.out.print("ID Barang yang ingin dihapus: ");
                int id = Integer.parseInt(scanner.nextLine());
                
                boolean removed = daftarBarang.removeIf(b -> b.getIdBarang() == id);
                if (removed) {
                    System.out.println("Barang berhasil dihapus.");
                } else {
                    System.out.println("Barang tidak ditemukan.");
                }
            } catch (NumberFormatException e) {
                System.out.println("ID harus berupa angka");
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        public static Transaksi pinjamBarangHelper(String username) {
            try {
                lihatBarang();
                if (daftarBarang.isEmpty()) return null;
                
                System.out.print("ID barang yang ingin dipinjam: ");
                int id = Integer.parseInt(scanner.nextLine());
                
                System.out.print("Jumlah: ");
                int jumlah = Integer.parseInt(scanner.nextLine());
                if (jumlah <= 0) {
                    throw new IllegalArgumentException("Jumlah harus lebih dari 0");
                }

                for (Barang b : daftarBarang) {
                    if (b.getIdBarang() == id) {
                        if (b.getJumlah() >= jumlah) {
                            b.setJumlah(b.getJumlah() - jumlah);
                            // COMPOSITION: Membuat transaksi baru
                            Transaksi t = new Transaksi(username, b.getNama(), jumlah);
                            transaksiList.add(t);
                            System.out.println("Barang berhasil dipinjam.");
                            return t;
                        } else {
                            throw new IllegalArgumentException("Stok tidak cukup. Stok tersedia: " + b.getJumlah());
                        }
                    }
                }
                throw new IllegalArgumentException("Barang tidak ditemukan");
            } catch (NumberFormatException e) {
                System.out.println("Input harus berupa angka");
                return null;
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                return null;
            }
        }

        public static void kembalikanBarang(String username) {
            try {
                // ASSOCIATION: Menggunakan stream untuk filter transaksi user
                List<Transaksi> pinjamanUser = transaksiList.stream()
                        .filter(t -> t.getUsername().equals(username))
                        .collect(Collectors.toList());
                
                if (pinjamanUser.isEmpty()) {
                    System.out.println("Anda tidak memiliki riwayat peminjaman.");
                    return;
                }

                System.out.println("\nBarang yang sedang Anda pinjam:");
                pinjamanUser.forEach(t -> System.out.println(
                    "- " + t.getNamaBarang() + " (Jumlah: " + t.getJumlah() + ")"));

                System.out.print("\nNama barang yang dikembalikan: ");
                String nama = scanner.nextLine().trim();
                
                // ASSOCIATION: Mencari transaksi untuk barang tertentu
                Optional<Transaksi> transaksiOpt = pinjamanUser.stream()
                        .filter(t -> t.getNamaBarang().equalsIgnoreCase(nama))
                        .findFirst();
                
                if (!transaksiOpt.isPresent()) {
                    System.out.println("Anda tidak meminjam barang tersebut.");
                    return;
                }

                Transaksi transaksi = transaksiOpt.get();
                
                System.out.print("Jumlah yang dikembalikan (maks " + transaksi.getJumlah() + "): ");
                int jumlah = Integer.parseInt(scanner.nextLine());
                
                if (jumlah <= 0) {
                    System.out.println("Jumlah harus lebih dari 0.");
                    return;
                }
                
                if (jumlah > transaksi.getJumlah()) {
                    System.out.println("Anda tidak bisa mengembalikan lebih dari yang dipinjam.");
                    return;
                }

                // COMPOSITION: Mengupdate stok barang dan transaksi
                for (Barang b : daftarBarang) {
                    if (b.getNama().equalsIgnoreCase(nama)) {
                        b.setJumlah(b.getJumlah() + jumlah);
                        
                        if (jumlah == transaksi.getJumlah()) {
                            transaksiList.remove(transaksi);
                        } else {
                            transaksi.setJumlah(transaksi.getJumlah() - jumlah);
                        }
                        
                        System.out.println("Berhasil mengembalikan " + jumlah + " " + nama);
                        return;
                    }
                }
                
                System.out.println("Barang tidak ditemukan di gudang.");
            } catch (NumberFormatException e) {
                System.out.println("Jumlah harus berupa angka.");
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        public static void lihatTransaksi() {
            if (transaksiList.isEmpty()) {
                System.out.println("Belum ada peminjaman.");
                return;
            }
            for (Transaksi t : transaksiList) {
                System.out.println(t);
            }
        }
    }
}