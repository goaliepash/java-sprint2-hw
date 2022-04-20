package data;

import java.util.ArrayList;

/**
 * Класс для создания месячного отчёта.
 * Месячный отчёт содержит информацию о всех тратах, произведённых в течение календарного месяца.
 *
 * @author Иванов Павел Александрович
 */
public class MonthlyReport {

    private final ArrayList<Data> dataList;

    public MonthlyReport() {
        dataList = new ArrayList<>();
    }

    /**
     * Добавить новую строку с данными в месячный отчёт.
     *
     * @param itemName  Название товара.
     * @param isExpense Булево значение: true, если запись означает трату, false, если доход.
     * @param quantity  Количество закупленного или проданного товара.
     * @param sumOfOne  Стоимость одной единицы товара. Целое число.
     */
    public void addData(String itemName, boolean isExpense, int quantity, int sumOfOne) {
        Data data = new Data(itemName, isExpense, quantity, sumOfOne);
        dataList.add(data);
    }

    /**
     * Получить название самого прибыльного продукта в текущем отчёте.
     *
     * @return Название самого прибыльного продукта.
     */
    public String getMostProfitableProduct() {
        String mostProfitableProduct = "";
        int maxSumOfProduct = 0;
        for (Data data : dataList) {
            if (!data.isExpense) {
                int currentSumOfProduct = data.quantity * data.sumOfOne;
                if (currentSumOfProduct > maxSumOfProduct) {
                    maxSumOfProduct = currentSumOfProduct;
                    mostProfitableProduct = data.itemName;
                }
            }
        }
        return mostProfitableProduct;
    }

    /**
     * Получить самую большую трату за месяц и название товара.
     *
     * @return Самая большая трата за месяц и название товара.
     */
    public String getBiggestExpenseWithProductName() {
        String productName = "";
        int maxExpense = 0;
        for (Data data : dataList) {
            if (data.isExpense) {
                int currentExpense = data.quantity * data.sumOfOne;
                if (currentExpense > maxExpense) {
                    maxExpense = currentExpense;
                    productName = data.itemName;
                }
            }
        }
        return String.format("%d за \"%s\"", maxExpense, productName);
    }

    /**
     * Получить сумму всех затрат за текущий месяц.
     *
     * @return Сумма всех затрат.
     */
    public int getSumOfExpenses() {
        int sumOfExpenses = 0;
        for (Data data : dataList) {
            if (data.isExpense) {
                sumOfExpenses += data.quantity * data.sumOfOne;
            }
        }
        return sumOfExpenses;
    }

    /**
     * Получить прибыль за текущий месяц.
     *
     * @return Вся прибыль за текущий месяц.
     */
    public int getSumOfIncomes() {
        int sumOfIncomes = 0;
        for (Data data : dataList) {
            if (!data.isExpense) {
                sumOfIncomes += data.quantity * data.sumOfOne;
            }
        }
        return sumOfIncomes;
    }

    private static class Data {

        String itemName;
        boolean isExpense;
        int quantity;
        int sumOfOne;

        Data(String itemName, boolean isExpense, int quantity, int sumOfOne) {
            this.itemName = itemName;
            this.isExpense = isExpense;
            this.quantity = quantity;
            this.sumOfOne = sumOfOne;
        }
    }
}