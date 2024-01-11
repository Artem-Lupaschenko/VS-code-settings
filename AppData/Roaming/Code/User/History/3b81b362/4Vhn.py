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

# Вивід даних до обробки на діаграмі
plt.figure(figsize=(12, 6))
sns.lineplot(x='Date', y='MeanTemp', data=data)
plt.title('MeanTemp')
plt.show()
plt.figure(figsize=(12, 6))
sns.lineplot(x='Date', y='Humidity', data=data)
plt.title('Humidity')
plt.show()
plt.figure(figsize=(12, 6))
sns.lineplot(x='Date', y='WindSpeed', data=data)
plt.title('WindSpeed')
plt.show()
plt.figure(figsize=(12, 6))
sns.lineplot(x='Date', y='MeanPressure', data=data)
plt.title('MeanPressure')
plt.show()

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

print('\nЗмінюємо неточні дані в числових стовпцях')
# Дивимось правильність даних для числових стовпців
print(f'Describe для даних:\n{data.describe(include="all")}')
# Перевіряємо різницю 10 найбільших та найменших значень в MeanTemp
print(f'Найменші значення в MeanTemp:\n{data.MeanTemp.nsmallest(10)}')
print(f'Найбільші значення в MeanTemp:\n{data.MeanTemp.nlargest(10)}')
# Перевіряємо різницю 10 найбільших та найменших значень в MeanPressure
print(f'Найменші значення в MeanPressure:\n{data.MeanPressure.nsmallest(10)}')
print(f'Найбільші значення в MeanPressure:\n{data.MeanPressure.nlargest(10)}')
# Змінюємо неточні дані 
data = data.assign(MeanTemp=lambda x: x.MeanTemp.clip(x.MeanTemp.nsmallest(2).iloc[1], x.MeanTemp.nlargest(3).iloc[2]), 
									 Humidity=lambda x: x.Humidity.replace(x.Humidity.min(), 0), 
									 MeanPressure=lambda x: x.MeanPressure.clip(x.MeanPressure.nsmallest(2).iloc[1], x.MeanPressure.nlargest(2).iloc[1]))
# Перевіряємо різницю 10 найбільших та найменших значень в MeanTemp
print(f'Найменші значення в MeanTemp:\n{data.MeanTemp.nsmallest(10)}')
print(f'Найбільші значення в MeanTemp:\n{data.MeanTemp.nlargest(10)}')
# Перевіряємо різницю 10 найменших значень в Humidity
print(f'Найменші значення в Humidity:\n{data.Humidity.nsmallest(10)}')
# Перевіряємо різницю 10 найбільших та найменших значень в MeanPressure
print(f'Найменші значення в MeanPressure:\n{data.MeanPressure.nsmallest(10)}')
print(f'Найбільші значення в MeanPressure:\n{data.MeanPressure.nlargest(10)}')
print(f'Describe для оновлених даних:\n{data.describe(include="all")}')

# Вивід оновлених даних на діаграмі
plt.figure(figsize=(12, 6))
sns.lineplot(x='Date', y='MeanTemp', data=data)
plt.title('MeanTemp')
plt.show()
plt.figure(figsize=(12, 6))
sns.lineplot(x='Date', y='Humidity', data=data)
plt.title('Humidity')
plt.show()
plt.figure(figsize=(12, 6))
sns.lineplot(x='Date', y='WindSpeed', data=data)
plt.title('WindSpeed')
plt.show()
plt.figure(figsize=(12, 6))
sns.lineplot(x='Date', y='MeanPressure', data=data)
plt.title('MeanPressure')
plt.show()