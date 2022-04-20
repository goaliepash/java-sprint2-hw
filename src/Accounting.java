import data.MonthlyReport;
import data.YearlyReport;

import java.util.HashMap;
import java.util.Locale;

/**
 * Класс отвечающий за ведение бухгалтерии.
 * Содержит информацию о месячных и годовых отчётах. Осуществляют сверку отчётов.
 *
 * @author Иванов Павел Александрович
 */
public class Accounting {

    private static final String SEPARATOR = ",";
    private static final String EMPTY_MONTHLY_REPORTS_MESSAGE = "Вы не добавили информацию о месячных отчётах.";
    private static final String EMPTY_YEARLY_REPORT_MESSAGE = "Вы не добавили информацию о годовом отчёте.";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_RESET = "\u001B[0m";

    private final HashMap<Integer, MonthlyReport> monthlyReports;
    private final YearlyReport yearlyReport;
    private final int year;

    public Accounting(int year) {
        monthlyReports = new HashMap<>();
        yearlyReport = new YearlyReport();
        this.year = year;
    }

    /**
     * Добавление нового месячного отчёта.
     *
     * @param month            Номер месяца. Число от 1 до 12 включительно.
     * @param csvMonthlyReport Отчёт за месяц в формате csv.
     */
    public void addMonthlyReport(int month, String csvMonthlyReport) {
        if (month < 1 || month > 12) {
            System.out.println("Ошибка! Номер месяца указан неверно, должно быть число от 1 до 12 включительно.");
        }
        MonthlyReport monthlyReport = new MonthlyReport();
        String[] lines = csvMonthlyReport.split(System.lineSeparator());
        for (int i = 1; i < lines.length; i++) {
            String[] lineContent = lines[i].split(SEPARATOR);
            monthlyReport.addData(
                    lineContent[0],
                    Boolean.parseBoolean(lineContent[1]),
                    Integer.parseInt(lineContent[2]),
                    Integer.parseInt(lineContent[3])
            );
        }
        monthlyReports.put(month, monthlyReport);
    }

    /**
     * Заполнение годового отчёта.
     *
     * @param csvYearlyReport Отчёт за год в формате csv.
     */
    public void fillYearlyReport(String csvYearlyReport) {
        String[] lines = csvYearlyReport.split(System.lineSeparator());
        int currentExpense = 0;
        int currentIncome = 0;
        for (int i = 1; i < lines.length; i++) {
            String[] lineContent = lines[i].split(SEPARATOR);
            boolean isExpense = Boolean.parseBoolean(lineContent[2]);
            if (isExpense) {
                currentExpense = Integer.parseInt(lineContent[1]);
            } else {
                currentIncome = Integer.parseInt(lineContent[1]);
            }
            if (i % 2 == 0) {
                yearlyReport.addMonthData(Integer.parseInt(lineContent[0]), currentExpense, currentIncome);
            }
        }
    }

    /**
     * Выполнить сверку отчётов.
     */
    public void verifyReports() {
        if (monthlyReports.isEmpty()) {
            System.out.println(EMPTY_MONTHLY_REPORTS_MESSAGE);
            return;
        }
        if (yearlyReport.isEmpty()) {
            System.out.println(EMPTY_YEARLY_REPORT_MESSAGE);
            return;
        }
        boolean isVerifySuccess = true;
        for (Integer month : monthlyReports.keySet()) {
            boolean isVerifyExpensesSuccess = verifyExpensesByMonth(month);
            boolean isVerifyIncomesSuccess = verifyIncomesByMonth(month);
            if (!isVerifyExpensesSuccess || !isVerifyIncomesSuccess) {
                isVerifySuccess = false;
            }
        }
        if (isVerifySuccess) {
            System.out.println(ANSI_GREEN + "Проверка успешно пройдена." + ANSI_RESET);
        }
    }

    /**
     * Печать информации о всех месячных отчётах.
     */
    public void printMonthlyReportsInformation() {
        if (monthlyReports.isEmpty()) {
            System.out.println(EMPTY_MONTHLY_REPORTS_MESSAGE);
            return;
        }
        for (Integer month : monthlyReports.keySet()) {
            String monthName = getMonthName(month);
            MonthlyReport monthlyReport = monthlyReports.get(month);
            System.out.println(monthName);
            System.out.printf("Самый прибыльный продукт: %s\n", monthlyReport.getMostProfitableProduct());
            System.out.printf("Самая большая трата: %s\n", monthlyReport.getBiggestExpenseWithProductName());
        }
    }

    /**
     * Печать информации о годовом отчёте.
     */
    public void printYearlyReportInformation() {
        if (yearlyReport.isEmpty()) {
            System.out.println(EMPTY_YEARLY_REPORT_MESSAGE);
            return;
        }
        System.out.printf("Год: %d\n", this.year);
        if (monthlyReports.isEmpty()) {
            System.out.println(ANSI_YELLOW + EMPTY_MONTHLY_REPORTS_MESSAGE + " Прибыль по месяцам в текущем году напечатана не будет." + ANSI_RESET);
        } else {
            for (Integer month : monthlyReports.keySet()) {
                System.out.printf("Прибыль за %s: %d\n", getMonthName(month), yearlyReport.getProfitByMonth(month));
            }
        }
        System.out.printf(Locale.US, "Средний расход за все месяцы в году: %.2f\n", yearlyReport.getAverageExpense());
        System.out.printf(Locale.US, "Средний доход за все месяцы в году: %.2f\n", yearlyReport.getAverageIncome());
    }

    private static String getMonthName(int month) {
        switch (month) {
            case 1:
                return "Январь";
            case 2:
                return "Февраль";
            case 3:
                return "Март";
            case 4:
                return "Апрель";
            case 5:
                return "Май";
            case 6:
                return "Июнь";
            case 7:
                return "Июль";
            case 8:
                return "Август";
            case 9:
                return "Сентябрь";
            case 10:
                return "Октябрь";
            case 11:
                return "Ноябрь";
            case 12:
                return "Декабрь";
            default:
                return "Номер месяца задан неверно.";
        }
    }

    private boolean verifyExpensesByMonth(int month) {
        MonthlyReport monthlyReport = monthlyReports.get(month);
        String monthName = getMonthName(month);
        int sumOfExpensesFromMonthlyReport = monthlyReport.getSumOfExpenses();
        int sumOfExpensesFromYearlyReport = yearlyReport.getExpensesByMonth(month);
        if (sumOfExpensesFromMonthlyReport != sumOfExpensesFromYearlyReport) {
            System.out.printf(ANSI_RED + "Траты за %s не совпадают в месячном и годовом отчётах.\n" + ANSI_RESET, monthName);
            System.out.printf("Траты, указанные в месячном отчёте: %d\n", sumOfExpensesFromMonthlyReport);
            System.out.printf("Траты, указанные в годовом отчёте за %s: %d\n", monthName, sumOfExpensesFromYearlyReport);
            return false;
        }
        return true;
    }

    private boolean verifyIncomesByMonth(int month) {
        MonthlyReport monthlyReport = monthlyReports.get(month);
        String monthName = getMonthName(month);
        int sumOfIncomesFromMonthlyReport = monthlyReport.getSumOfIncomes();
        int sumOfIncomesFromYearlyReport = yearlyReport.getIncomesByMonth(month);
        if (sumOfIncomesFromMonthlyReport != sumOfIncomesFromYearlyReport) {
            System.out.printf(ANSI_RED + "Прибыль за %s не совпадает в месячном и годовом отчётах.\n" + ANSI_RESET, monthName);
            System.out.printf("Прибыль, указанная в месячном отчёте: %d\n", sumOfIncomesFromMonthlyReport);
            System.out.printf("Прибыль, указанная в годовом отчёте за %s: %d\n", monthName, sumOfIncomesFromYearlyReport);
            return false;
        }
        return true;
    }
}