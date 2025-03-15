import java.util.ArrayList;
import java.util.Scanner;

class Entitas {
    protected String id;
    protected String nama;

    public Entitas(String id, String nama) {
        this.id = id;
        this.nama = nama;
    }

    public String getId() {
        return id;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNama() {
        return nama;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Nama: " + nama;
    }
}

class Pengguna extends Entitas {
    private String peran;

    public Pengguna(String id, String nama, String peran) {
        super(id, nama);
        this.peran = peran;
    }

    public String getPeran() {
        return peran;
    }

    @Override
    public String toString() {
        return super.toString() + ", Peran: " + peran;
    }
}

class Stok extends Entitas {
    private int jumlah;

    public Stok(String id, String nama, int jumlah) {
        super(id, nama);
        this.jumlah = jumlah;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }

    public int getJumlah() {
        return jumlah;
    }

    @Override
    public String toString() {
        return super.toString() + ", Jumlah: " + jumlah;
    }
}

public class SistemCoffeeShop {
    private static ArrayList<Pengguna> daftarPengguna = new ArrayList<>();
    private static ArrayList<Stok> daftarStok = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean berjalan = true;
        while (berjalan) {
            System.out.println("\n--- Sistem Pengelolaan Coffee Shop ---");
            System.out.println("1. Kelola Pengguna");
            System.out.println("2. Kelola Stok");
            System.out.println("3. Keluar");
            System.out.print("Pilih menu: ");
            int pilihan = scanner.nextInt();
            scanner.nextLine();

            switch (pilihan) {
                case 1:
                    kelolaPengguna();
                    break;
                case 2:
                    kelolaStok();
                    break;
                case 3:
                    berjalan = false;
                    System.out.println("Keluar dari sistem.");
                    break;
                default:
                    System.out.println("Pilihan tidak valid.");
            }
        }
    }

    private static void kelolaPengguna() {
        boolean kembali = false;
        while (!kembali) {
            System.out.println("\n--- Kelola Pengguna ---");
            System.out.println("1. Tambah Pengguna");
            System.out.println("2. Lihat Pengguna");
            System.out.println("3. Ubah Pengguna");
            System.out.println("4. Hapus Pengguna");
            System.out.println("5. Kembali");
            System.out.print("Pilih menu: ");
            int pilihan = scanner.nextInt();
            scanner.nextLine();

            switch (pilihan) {
                case 1:
                    tambahPengguna();
                    break;
                case 2:
                    lihatPengguna();
                    break;
                case 3:
                    ubahPengguna();
                    break;
                case 4:
                    hapusPengguna();
                    break;
                case 5:
                    kembali = true;
                    break;
                default:
                    System.out.println("Pilihan tidak valid.");
            }
        }
    }

    private static void kelolaStok() {
        boolean kembali = false;
        while (!kembali) {
            System.out.println("\n--- Kelola Stok ---");
            System.out.println("1. Tambah Stok");
            System.out.println("2. Lihat Stok");
            System.out.println("3. Ubah Stok");
            System.out.println("4. Hapus Stok");
            System.out.println("5. Kembali");
            System.out.print("Pilih menu: ");
            int pilihan = scanner.nextInt();
            scanner.nextLine();

            switch (pilihan) {
                case 1:
                    tambahStok();
                    break;
                case 2:
                    lihatStok();
                    break;
                case 3:
                    ubahStok();
                    break;
                case 4:
                    hapusStok();
                    break;
                case 5:
                    kembali = true;
                    break;
                default:
                    System.out.println("Pilihan tidak valid.");
            }
        }
    }

    private static void tambahPengguna() {
        System.out.print("Masukkan ID Pengguna: ");
        String id = scanner.nextLine();
        System.out.print("Masukkan Nama Pengguna: ");
        String nama = scanner.nextLine();
        System.out.print("Masukkan Peran Pengguna: ");
        String peran = scanner.nextLine();
        daftarPengguna.add(new Pengguna(id, nama, peran));
        System.out.println("Pengguna berhasil ditambahkan.");
    }

    private static void lihatPengguna() {
        if (daftarPengguna.isEmpty()) {
            System.out.println("Tidak ada pengguna.");
            return;
        }
        for (Pengguna pengguna : daftarPengguna) {
            System.out.println(pengguna);
        }
    }

    private static void ubahPengguna() {
        System.out.print("Masukkan ID Pengguna yang akan diubah: ");
        String id = scanner.nextLine();
        for (Pengguna pengguna : daftarPengguna) {
            if (pengguna.getId().equals(id)) {
                System.out.print("Masukkan Nama Baru: ");
                pengguna.setNama(scanner.nextLine());
                System.out.println("Pengguna berhasil diubah.");
                return;
            }
        }
        System.out.println("Pengguna tidak ditemukan.");
    }

    private static void hapusPengguna() {
        System.out.print("Masukkan ID Pengguna yang akan dihapus: ");
        String id = scanner.nextLine();
        daftarPengguna.removeIf(pengguna -> pengguna.getId().equals(id));
        System.out.println("Pengguna berhasil dihapus.");
    }

    private static void tambahStok() {
        System.out.print("Masukkan ID Stok: ");
        String id = scanner.nextLine();
        System.out.print("Masukkan Nama Barang: ");
        String nama = scanner.nextLine();
        System.out.print("Masukkan Jumlah: ");
        int jumlah = scanner.nextInt();
        scanner.nextLine();
        daftarStok.add(new Stok(id, nama, jumlah));
        System.out.println("Stok berhasil ditambahkan.");
    }

    private static void lihatStok() {
        if (daftarStok.isEmpty()) {
            System.out.println("Tidak ada stok.");
            return;
        }
        for (Stok stok : daftarStok) {
            System.out.println(stok);
        }
    }

    private static void ubahStok() {
        System.out.print("Masukkan ID Stok yang akan diubah: ");
        String id = scanner.nextLine();
        for (Stok stok : daftarStok) {
            if (stok.getId().equals(id)) {
                System.out.print("Masukkan Nama Baru: ");
                stok.setNama(scanner.nextLine());
                System.out.print("Masukkan Jumlah Baru: ");
                stok.setJumlah(scanner.nextInt());
                scanner.nextLine();
                System.out.println("Stok berhasil diubah.");
                return;
            }
        }
        System.out.println("Stok tidak ditemukan.");
    }

    private static void hapusStok() {
        System.out.print("Masukkan ID Stok yang akan dihapus: ");
        String id = scanner.nextLine();
        daftarStok.removeIf(stok -> stok.getId().equals(id));
        System.out.println("Stok berhasil dihapus.");
    }
}
