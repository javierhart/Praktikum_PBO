import java.util.ArrayList;
import java.util.Scanner;

abstract class Entitas {
    private String id;
    private String nama;

    public Entitas(String id, String nama) {
        this.id = id;
        this.nama = nama;
    }

    public String getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setNama(String nama, boolean log) {
        this.nama = nama;
        if (log) {
            System.out.println("Nama telah diubah menjadi: " + nama);
        }
    }

    public abstract void tampilkanInfo();

    @Override
    public String toString() {
        return "ID: " + id + ", Nama: " + nama;
    }
}

class Pengguna extends Entitas {
    private final String peran;
    private double gaji;

    public Pengguna(String id, String nama, String peran, double gaji) {
        super(id, nama);
        this.peran = peran;
        this.gaji = gaji;
    }

    public String getPeran() {
        return peran;
    }

    public double getGaji() {
        return gaji;
    }

    public void setGaji(double gaji) {
        this.gaji = gaji;
    }

    @Override
    public void tampilkanInfo() {
        System.out.println(this);
    }

    @Override
    public final String toString() {
        return super.toString() + ", Peran: " + peran + ", Gaji: " + gaji;
    }
}

final class Stok extends Entitas {
    private int jumlah;

    public Stok(String id, String nama, int jumlah) {
        super(id, nama);
        this.jumlah = jumlah;
    }

    public int getJumlah() {
        return jumlah;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }

    @Override
    public void tampilkanInfo() {
        System.out.println(this);
    }

    @Override
    public String toString() {
        return super.toString() + ", Jumlah: " + jumlah;
    }
}

public class SistemCoffeeShop {
    private static final Scanner scanner = new Scanner(System.in);
    private static final ArrayList<Pengguna> daftarPengguna = new ArrayList<>();
    private static final ArrayList<Stok> daftarStok = new ArrayList<>();

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
                case 1 -> kelolaPengguna();
                case 2 -> kelolaStok();
                case 3 -> {
                    berjalan = false;
                    System.out.println("Keluar dari sistem.");
                }
                default -> System.out.println("Pilihan tidak valid.");
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
                case 1 -> tambahPengguna();
                case 2 -> lihatPengguna();
                case 3 -> ubahPengguna();
                case 4 -> hapusPengguna();
                case 5 -> kembali = true;
                default -> System.out.println("Pilihan tidak valid.");
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
                case 1 -> tambahStok();
                case 2 -> lihatStok();
                case 3 -> ubahStok();
                case 4 -> hapusStok();
                case 5 -> kembali = true;
                default -> System.out.println("Pilihan tidak valid.");
            }
        }
    }

    private static void tambahPengguna() {
        System.out.print("Masukan ID Karyawan: ");
        String id = scanner.nextLine();

        System.out.print("Masukan Nama Karyawan: ");
        String nama = scanner.nextLine();

        System.out.print("Masukan Peran Karyawan: ");
        String peran = scanner.nextLine();

        double gaji;
        while (true) {
            System.out.print("Masukan Gaji Karyawan: ");
            String gajiInput = scanner.nextLine();

            if (gajiInput.matches("\\d+(\\.\\d{1,2})?")) {
                gaji = Double.parseDouble(gajiInput);
                break;
            } else {
                System.out.println("Input gaji tidak valid! Masukkan angka dengan format yang benar (contoh: 2500 atau 2500.75).");
            }
        }

        daftarPengguna.add(new Pengguna(id, nama, peran, gaji));
        System.out.println("Karyawan berhasil ditambahkan.");
    }

    private static void lihatPengguna() {
        if (daftarPengguna.isEmpty()) {
            System.out.println("Tidak ada pengguna.");
            return;
        }
        daftarPengguna.forEach(Pengguna::tampilkanInfo);
    }

    private static void ubahPengguna() {
        System.out.print("Masukkan ID Pengguna yang akan diubah: ");
        String id = scanner.nextLine();
        for (Pengguna pengguna : daftarPengguna) {
            if (pengguna.getId().equals(id)) {
                System.out.print("Masukkan Nama Baru: ");
                String namaBaru = scanner.nextLine();
                pengguna.setNama(namaBaru, true);
                System.out.println("Note: Peran tidak dapat diubah karena atribut final.");
                System.out.println("Pengguna berhasil diubah.");
                return;
            }
        }
        System.out.println("Pengguna tidak ditemukan.");
    }

    private static void hapusPengguna() {
        System.out.print("Masukkan ID Pengguna yang akan dihapus: ");
        String id = scanner.nextLine();
        daftarPengguna.removeIf(p -> p.getId().equals(id));
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
        daftarStok.forEach(Stok::tampilkanInfo);
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
        daftarStok.removeIf(s -> s.getId().equals(id));
        System.out.println("Stok berhasil dihapus.");
    }
}
