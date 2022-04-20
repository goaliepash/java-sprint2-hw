import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final Accounting accounting = new Accounting(2021);

    public static void main(String[] args) {
        while (true) {
            printMenu();
            int command = scanner.nextInt();
            switch (command) {
                case 1:
                    readAllMonthlyReports();
                    break;
                case 2:
                    readYearlyReport();
                    break;
                case 3:
                    accounting.verifyReports();
                    break;
                case 4:
                    accounting.printMonthlyReportsInformation();
                    break;
                case 5:
                    accounting.printYearlyReportInformation();
                    break;
                case 0:
                    System.out.println("Программа завершена.");
                    scanner.close();
                    return;
                default:
                    System.out.println("Такой команды в программе нет.");
                    break;
            }
        }
    }

    private static void printMenu() {
        System.out.println("Что Вы хотите сделать?");
        System.out.println("1 - Считать все месячные отчёты.");
        System.out.println("2 - Считать годовой отчёт.");
        System.out.println("3 - Сверить отчёты.");
        System.out.println("4 - Вывести информацию о всех месячных отчётах.");
        System.out.println("5 - Вывести информацию о годовом отчёте.");
        System.out.println("0 - Выйти из программы.");
    }

    private static void readAllMonthlyReports() {
        for (int i = 1; i <= 3; i++) {
            String csvMonthlyReport = readFileContentsOrNull("resources/m.20210" + i + ".csv");
            if (csvMonthlyReport != null) {
                accounting.addMonthlyReport(i, csvMonthlyReport);
            }
        }
    }

    private static void readYearlyReport() {
        String csvYearlyReport = readFileContentsOrNull("resources/y.2021.csv");
        if (csvYearlyReport != null) {
            accounting.fillYearlyReport(csvYearlyReport);
        }
    }

    private static String readFileContentsOrNull(String path) {
        try {
            return Files.readString(Path.of(path));
        } catch (IOException e) {
            System.out.println("Невозможно прочитать файл с отчётом. Возможно, файл не находится в нужной директории.");
            return null;
        }
    }
}

