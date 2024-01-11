import matplotlib.pyplot as plt
import pandas as pd
import seaborn as sns

# Завантаження даних
data = pd.read_csv("LR5\Delhi_Climate.csv", index_col=['date'], parse_dates=True)

sns.set_theme()

# а) Загальний графік
plt.figure(figsize=(12, 6))
sns.lineplot(x='date', y='meantemp', data=data)
plt.title('Зміна середньої денної температури')
plt.show()

# б) За 2014 рік
plt.figure(figsize=(12, 6))
sns.lineplot(x='date', y='meantemp', data=data.loc['2014'])
plt.title('Зміна середньої денної температури у 2014 році')
plt.show()

# в) За квітень 2013 року
plt.figure(figsize=(12, 6))
sns.lineplot(x='date', y='meantemp', data=data.loc['2013-4'])
plt.title('Зміна середньої денної температури у квітні 2013 року')
plt.show()

# г) За листопад 2013 – травень 2015
plt.figure(figsize=(12, 6))
sns.lineplot(x='date', y='meantemp', data=data.loc['2013-11':'2015-5'])
plt.title('Зміна середньої денної температури з листопада 2013 по травень 2015')
plt.show()

# д) За 2015 та 2016 на одному графіку (паралельно)
plt.figure(figsize=(12, 6))
data['month'] = data.index.strftime('%b')
sns.lineplot(x='month', y='meantemp', data=data.loc['2015'])
sns.lineplot(x='month', y='meantemp', data=data.loc['2016'])
plt.title('Зміна середньої денної температури у 2015 та 2016 роках')
plt.show()

# а) За 2016 рік
humidity_2016 = data.resample('Y')['humidity'].mean().loc['2016']
print(f'Середнє значення вологості за 2016 рік:\n{humidity_2016}')

# # б) За кожний місяць
average_humidity_by_month = data.resample('M')['humidity'].mean()
print(f'Середнє значення вологості за кожний місяць:\n{average_humidity_by_month}')

# в) За кожні два тижні весни та літа 2014 року
average_humidity_by_two_weeks = data.loc['2014-03':'2014-08'].resample('2W')['humidity'].mean()
print(f'Середнє значення вологості за кожні два тижні весни та літа 2014 року:\n{average_humidity_by_two_weeks}')

# г) Зміни вологості у відсотках за кожен день впродовж літа 2015 року
humidity_changes_2015 = data.loc['2015-06':'2015-08']['humidity'].pct_change() * 100
print(f'Зміни вологості у відсотках за кожен день впродовж літа 2015 року:\n{humidity_changes_2015}')
plt.figure(figsize=(12, 6))
humidity_changes_2015.plot()
plt.title('Зміни вологості у відсотках за кожен день впродовж літа 2015 року')
plt.show()

# д) Знайти та зобразити графічно ковзне середнє вологості за 2013 рік з вікном в місяць
rolling_average_humidity_2013 = data.loc['2013']['humidity'].rolling('30D').mean()
print(f'Ковзне середнє вологості за 2013 рік з вікном в місяць:\n{rolling_average_humidity_2013}')
plt.figure(figsize=(12, 6))
rolling_average_humidity_2013.plot()
plt.title('Ковзне середнє вологості за 2013 рік з вікном в місяць')
plt.show()