import pandas as pd

# Зчитати дані з CSV-файлу
data = pd.read_csv("LR3/bike.csv")

# Вивести інформацію про набір даних
print("Інформація про набір даних:")
print(data.info())

# Основні статистичні характеристики
print("\nОсновні статистичні характеристики:")
print(data.describe())

# Типи ознак
print("\nТипи ознак:")
print(data.dtypes)

# Зробити зріз та зберегти копію частини набору даних
subset_data = data.loc[0:5, ["Marital Status", "Gender", "Income", "Age"]].copy()

# Призначити власні індекси рядків та стовпців
subset_data.index = range(1, len(subset_data))
subset_data.columns = ["Status", "Sex", "Earnings", "Years"]

# Додати новий рядок
new_row = pd.DataFrame({"Status": ["Single"], "Sex": ["Female"], "Earnings": [25000], "Years": [30]})
subset_data = pd.concat([subset_data, new_row], ignore_index=True)

# Вивести результат
print("\nНовий DataFrame:")
print(subset_data)

#а) Знайти медіанний дохід клієнтів для кожної з професій:
median_income_by_occupation = data.groupby("Occupation")["Income"].median()
print("\nМедіанний дохід для кожної з професій:")
print(median_income_by_occupation)

#б) Порахувати кількість та вивести дані всіх заміжніх жінок, старших за 35 років:
married_women_over_35 = data[(data["Marital Status"] == "Married") & (data["Gender"] == "Female") & (data["Age"] > 35)]
print("\nЗаміжні жінки старше 35 років:")
print(married_women_over_35)

#в) Додати новий стовпець, який містить дохід на кожного члена сім’ї:
data["Income per Person"] = data["Income"] / (data["Children"] + (data["Marital Status"] == "Married") + 1)
print("\nНовий стовпець 'Income per Person':")
print(data["Income per Person"])

#г) Додати новий стовпець, який містить середній вік для людей з різним рівнем освіти:
data["Average Age by Education"] = data.groupby("Education")["Age"].transform(lambda x: x.mean())
print("\nНовий стовпець 'Average Age by Education':")
print(data["Average Age by Education"])