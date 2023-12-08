package taskagıt;

import java.util.Random;
import java.util.Scanner;

public class deneme {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
		
        System.out.println("Taş Kağıt Makas oyununa hoş geldiniz!");

        // Oyun modunu seçen fonksiyon çağrılır
        String oyunModu = secimAlOyunModu(scanner);

        
        // Geçerli bir oyun modu kontrol edilir
        if (!oyunModu.equals("bilgisayar") && !oyunModu.equals("oyuncu")) {
            System.out.println("Geçersiz bir oyun modu seçtiniz.");
            scanner.close();
            return;
        }

        
        // Oyun döngüsü başlar
        do {
            Oyun oyun = new Oyun(oyunModu, scanner, random);
            oyun.baslat();

            System.out.print("Yeniden oynamak istiyor musunuz? (Evet için 'e' / Hayır için 'h'): ");
            scanner.nextLine(); 
            String tekrarOynamak = scanner.nextLine();

            if (!tekrarOynamak.equalsIgnoreCase("e")) {
                System.out.println("Oyun sona erdi. Teşekkür ederiz!");
                break;
            }

        } while (true);

        scanner.close();
    }

	
	// Kullanıcının oyun modunu seçtiği fonksiyon
    public static String secimAlOyunModu(Scanner scanner) {
        System.out.print("Oyunu bilgisayara karşı mı yoksa başka bir oyuncuya karşı mı oynamak istersiniz? (bilgisayar/oyuncu): ");
        return scanner.nextLine().toLowerCase();
    }
}


//Oyuncu sınıfı
class Oyuncu {
    private String ad;
    
    public Oyuncu(String ad) {
        this.ad = ad;
    }
    
    public String getAd() {
        return ad;
    }
}

//Tur sınıfı
class Tur {
    private int oyuncu1Secim;
    private int oyuncu2Secim;
    
    public Tur(int oyuncu1Secim, int oyuncu2Secim) {
        this.oyuncu1Secim = oyuncu1Secim;
        this.oyuncu2Secim = oyuncu2Secim;
    }
    
    public int getOyuncu1Secim() {
        return oyuncu1Secim;
    }
    
    public int getOyuncu2Secim() {
        return oyuncu2Secim;
    }
}


//Oyun sınıfı
class Oyun {
    private String oyunModu;
    private Scanner scanner;
    private Random random;
    
    public Oyun(String oyunModu, Scanner scanner, Random random) {
        this.oyunModu = oyunModu;
        this.scanner = scanner;
        this.random = random;
    }
    
    
    // Oyunun başladığı metot
    public void baslat() {
        String oyuncu1, oyuncu2;

        if (oyunModu.equals("bilgisayar")) {
            oyuncu1 = "Siz";
            oyuncu2 = "Bilgisayar";
        } else {
            oyuncu1 = secimAlOyuncu(scanner, 1);
            oyuncu2 = secimAlOyuncu(scanner, 2);
        }

        int toplamTurSayisi = secimAlToplamTur(scanner);

        if (toplamTurSayisi < 1 || toplamTurSayisi > 10) {
            System.out.println("Geçersiz tur sayısı girdiniz. 1 ile 10 arasında bir değer giriniz.");
            return;
        }

        int oyuncu1Skor = 0;
        int oyuncu2Skor = 0;

        String[] secimler = {"Taş", "Kağıt", "Makas"};

        for (int tur = 1; tur <= toplamTurSayisi; tur++) {
            System.out.println("\n--- Tur " + tur + " ---");

            int oyuncu1Secim, oyuncu2Secim;

            if (oyunModu.equals("bilgisayar")) {
                oyuncu1Secim = secimAlOyuncu(scanner, "Siz");
                oyuncu2Secim = random.nextInt(3);
            } else {
                oyuncu1Secim = secimAlOyuncu(scanner, "Birinci oyuncu");
                oyuncu2Secim = secimAlOyuncu(scanner, "İkinci oyuncu");
            }

            Tur turNesnesi = new Tur(oyuncu1Secim, oyuncu2Secim);
            System.out.println(oyuncu1 + ": " + secimler[turNesnesi.getOyuncu1Secim()]);
            System.out.println(oyuncu2 + ": " + secimler[turNesnesi.getOyuncu2Secim()]);

            if (turNesnesi.getOyuncu1Secim() == turNesnesi.getOyuncu2Secim()) {
                System.out.println("Berabere!");
            } else if ((turNesnesi.getOyuncu1Secim() == 0 && turNesnesi.getOyuncu2Secim() == 2) ||
                    (turNesnesi.getOyuncu1Secim() == 1 && turNesnesi.getOyuncu2Secim() == 0) ||
                    (turNesnesi.getOyuncu1Secim() == 2 && turNesnesi.getOyuncu2Secim() == 1)) {
                System.out.println(oyuncu1 + " kazandı!");
                oyuncu1Skor++;
            } else {
                System.out.println(oyuncu2 + " kazandı!");
                oyuncu2Skor++;
            }

            System.out.println("Skor - " + oyuncu1 + ": " + oyuncu1Skor + " " + oyuncu2 + ": " + oyuncu2Skor);
        }

        if (oyuncu1Skor > oyuncu2Skor) {
            System.out.println(oyuncu1 + " toplamda kazandı!");
        } else if (oyuncu2Skor > oyuncu1Skor) {
            System.out.println(oyuncu2 + " toplamda kazandı!");
        } else {
            System.out.println("Toplamda berabere!");
        }
    }

    public String secimAlOyuncu(Scanner scanner, int oyuncuNo) {
        System.out.print(oyuncuNo + ". oyuncunun adını girin: ");
        return scanner.nextLine();
    }

    public int secimAlToplamTur(Scanner scanner) {
        int toplamTurSayisi;
        do {
            System.out.print("Oynamak istediğiniz tur sayısını girin (1 ile 10 arasında): ");
            toplamTurSayisi = scanner.nextInt();
        } while (toplamTurSayisi < 1 || toplamTurSayisi > 10);
        return toplamTurSayisi;
    }

    public int secimAlOyuncu(Scanner scanner, String oyuncu) {
        System.out.println(oyuncu + ", lütfen seçiminizi yapın (Taş - 0, Kağıt - 1, Makas - 2): ");
        while (true) {
            String secimStr = scanner.nextLine();
            try {
                int secim = Integer.parseInt(secimStr);
                if (secim >= 0 && secim <= 2) {
                    return secim;
                } else {
                    System.out.println("Geçersiz bir seçim yaptınız. Lütfen tekrar deneyin.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Geçersiz bir giriş yaptınız. Lütfen sayısal bir değer girin.");
            }
        }
    }
}
