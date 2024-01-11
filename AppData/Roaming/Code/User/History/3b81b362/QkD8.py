import matplotlib.pyplot as plt
import pandas as pd
import numpy as np
import seaborn as sns

sns.set_theme()

# Завдання 1: Читання файлу та зміна назв стовпців
data = pd.read_excel("LR6\Version 1.xlsx", usecols=range(1, 6), names=["Date", "MeanTemp", "Humidity", "WindSpeed", "MeanPressure"])

# Переводимо стовпець дат в datetime
print('\nПереводимо стовпець дат в datetime')
data['Date'] = pd.to_datetime(data['Date'], errors='coerce')
print(data[data.Date.isna()])
print(data[32:35])
data['Date'].fillna(pd.to_datetime('2013-02-02'), inplace=True)
print(data[32:35])

# Дивимось кількість унікальних значень в стовпці Date
print(f'\nDescribe для дат:\n{data["Date"].describe()}')

# Видаляємо дублікати дат
print('\nВидаляємо дублікати дат')
print(f'Duplicates: {data[data.duplicated(subset="Date", keep=False)]}')
data.drop_duplicates(subset='Date', inplace=True, ignore_index=True)
data.set_index('Date', inplace=True)

# Дивимось тип та наявність неправильних даних в стовпцях
print(f'\nInfo даних:')
data.info()

print('\nОбробляємо дані в інших стовпцях')
# Переводимо всі дані в стовпцях MeanTemp та WindSpeed в числа
data['MeanTemp'] = pd.to_numeric(data['MeanTemp'], errors='coerce')
data['WindSpeed'] = pd.to_numeric(data['WindSpeed'], errors='coerce')
print(f'Неправильні дані:\n{data[data.MeanTemp.isna() | data.Humidity.isna() | data.WindSpeed.isna() | data.MeanPressure.isna()]}')
# Заповнюємо дані з NaN ковзною медіаною
data.WindSpeed.fillna(data.WindSpeed.rolling(7, min_periods=0).median(), inplace=True)
# Видаляємо всі інші рядки з NaN
data.dropna(inplace=True)
print(f'Перевірка виправлених даних за допомогою rolling:\n{data.loc["2013-08-10":"2013-08-15"]}')
print(f'Неправильні дані:\n{data[data.MeanTemp.isna() | data.Humidity.isna() | data.WindSpeed.isna() | data.MeanPressure.isna()]}')
print(f'Info даних:')
data.info()

print('\nОбробляємо дані в інших стовпцях')
# Дивимось правильність даних для числових стовпців
print(f'Describe для даних:\n{data.describe(include="all")}')
# Перевіряємо  правильність даних для числових стовпців
print(data.MeanTemp.nsmallest(10))
print(data.MeanTemp.nlargest(10))

data = data.assign(MeanTemp=lambda x: x.MeanTemp.clip(x.MeanTemp.nsmallest(2).iloc[1], x.MeanTemp.nlargest(3).iloc[2]), 
									 Humidity=lambda x: x.Humidity.replace(x.Humidity.min(), 0), 
									 MeanPressure=lambda x: x.MeanPressure.clip(x.MeanPressure.nsmallest(2).iloc[1], x.MeanPressure.nlargest(2).iloc[1]))

print(data.MeanTemp.nsmallest(10))
print(data.MeanTemp.nlargest(10))

print(data.Humidity.nsmallest(10))

print(data.MeanPressure.nsmallest(10))
print(data.MeanPressure.nlargest(10))

# data.info()
# print(f'Corrupted data: {data[data.MeanTemp.isna() | data.Humidity.isna() | data.WindSpeed.isna() | data.MeanPressure.isna()]}')

print(data.describe(include="all"))

print(data)
# Завдання 2: Попередня обробка даних
# Видалення рядків з пропущеними значеннями у стовпці Date
# data = data.dropna(subset=["Date"])

# # Заміна відсутніх значень у стовпці WindSpeed на середнє значення
# mean_wind_speed = data["WindSpeed"].mean()
# data["WindSpeed"].fillna(mean_wind_speed, inplace=True)

# Вивід оновлених даних
plt.figure(figsize=(12, 6))
sns.lineplot(x='Date', y='MeanTemp', data=data)
plt.title('Зміна середньої денної температури')
plt.show()