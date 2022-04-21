package data;

import java.util.HashMap;

/**
 * Класс для создания годового отчёта.
 * Годовой отчёт содержит информацию о всех тратах, произведённых в течение года.
 *
 * @author Иванов Павел Александрович
 */
public class YearlyReport {

    private final HashMap<Integer, MonthData> dataHashMap;

    public YearlyReport() {
        dataHashMap = new HashMap<>();
    }

    /**
     * Добавление данных за один месяц.
     *
     * @param month   Номер месяца. Должен входить в диапазон [1, 12].
     * @param expense Затраты за месяц.
     * @param income  Прибыль за месяц.
     */
    public void addMonthData(int month, Integer expense, Integer income) {
        MonthData monthData = new MonthData(expense, income);
        if (month < 1 || month > 12) {
            System.out.println("Ошибка! Номер месяца должен быть в диапазоне [1, 12].");
        } else if (dataHashMap.containsKey(month)) {
            dataHashMap.replace(month, monthData);
        } else {
            dataHashMap.put(month, monthData);
        }
    }

    /**
     * Проверка на пустоту годового отчёта.
     *
     * @return true, если в отчёт ничего не добавлено, иначе - false.
     */
    public boolean isEmpty() {
        return dataHashMap.isEmpty();
    }

    /**
     * Получение прибыли за указнный месяц.
     *
     * @param month Порядковый номер месяца. Число от 1 до 12 включительно.
     * @return Прибыль за указнный месяц.
     */
    public int getProfitByMonth(int month) {
        if (month < 1 || month > 12) {
            System.out.println("Номер месяца указан неверно.");
            return Integer.MIN_VALUE;
        }
        MonthData monthData = dataHashMap.get(month);
        return monthData.getProfit();
    }

    /**
     * Получение трат за указнный месяц.
     *
     * @param month Номер месяца.
     * @return Траты за указанный месяц.
     */
    public int getExpensesByMonth(int month) {
        return dataHashMap.get(month).expense;
    }

    /**
     * Получение прибыли за указанный месяц.
     *
     * @param month Номер месяца.
     * @return Прибыль за указанный месяц.
     */
    public int getIncomesByMonth(int month) {
        return dataHashMap.get(month).income;
    }

    /**
     * Получение среднего расхода за все месяцы в году.
     *
     * @return Средний расход за все месяцы в году.
     */
    public double getAverageExpense() {
        double sumOfExpense = getSumOfExpenses();
        return sumOfExpense / dataHashMap.size();
    }

    /**
     * Получение средней прибыли за все месяцы в году.
     *
     * @return Средняя прибыль за все месяцы в году.
     */
    public double getAverageIncome() {
        double sumOfIncome = getSumOfIncomes();
        return sumOfIncome / dataHashMap.size();
    }

    private int getSumOfExpenses() {
        int sumOfExpense = 0;
        for (MonthData monthData : dataHashMap.values()) {
            sumOfExpense += monthData.expense;
        }
        return sumOfExpense;
    }

    private int getSumOfIncomes() {
        int sumOfIncome = 0;
        for (MonthData monthData : dataHashMap.values()) {
            sumOfIncome += monthData.income;
        }
        return sumOfIncome;
    }

    private static class MonthData {

        final int expense;
        final int income;

        MonthData(int expense, int income) {
            this.expense = expense;
            this.income = income;
        }

        int getProfit() {
            return income - expense;
        }
    }
}
