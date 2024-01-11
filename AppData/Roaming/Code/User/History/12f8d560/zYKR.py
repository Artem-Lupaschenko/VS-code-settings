import matplotlib.pyplot as plt
import pandas as pd
import seaborn as sns
import numpy as np

# Завантаження даних
data = pd.read_csv("LR4\diamonds.csv")

sns.set_theme()

# 1.Побудувати стовпчикові діаграми, на яких відобразити 
# а) кількість діамантів кожного з класів якості; 
# б) максимальну ціну діамантів кожного класу якості; 
# в) середню глибину діамантів різного класу якості з різною якістю кольору.
plt.figure(figsize=(10, 6))
sns.countplot(x='cut', data=data)
plt.title("Кількість діамантів кожного класу якості")
plt.show()

plt.figure(figsize=(10, 6))
sns.barplot(x='clarity', y='price', data=data, estimator=np.max).set(ylim=(2950, 3000))
plt.title("Максимальна ціна діамантів кожного класу якості")
plt.show()

plt.figure(figsize=(10, 6))
sns.barplot(x='cut', y='depth', hue='color', data=data, ci=None).set(ylim=(60, 70))
plt.title("Середня глибина діамантів різного класу якості з різною якістю кольору")
plt.show()

# 2.Побудувати гістограму глибини діамантів у відсотках (depth), загальну і для кожного класу якості.
plt.figure(figsize=(10, 6))
sns.histplot(data['depth'], bins=30, kde=False)
plt.title("Загальна гістограма глибини діамантів у відсотках")
plt.show()

plt.figure(figsize=(10, 6))
sns.histplot(data, x='depth', hue='cut', bins=30, kde=True, multiple='stack')
plt.title("Гістограма глибини діамантів для кожного класу якості у відсотках")
plt.show()

sns.FacetGrid(data, col='cut', aspect=0.5, height=3).map(sns.histplot, 'depth', bins=30)
plt.show()

# 3.Побудувати діаграму розмаху параметру table (загальну і в залежності від якості кольору), визначити чи присутні викиди.
plt.figure(figsize=(10, 6))
sns.boxplot(x='table', data=data)
plt.title("Діаграма розмаху параметру table")
plt.show()

plt.figure(figsize=(10, 6))
sns.boxplot(x='color', y='table', data=data)
plt.title("Діаграма розмаху параметру table в залежності від якості кольору")
plt.show()

# а) Довжина і ширина
plt.figure(figsize=(10, 6))
sns.scatterplot(x='x', y='y', data=data)
plt.title("Діаграма розсіювання для довжини і ширини")
plt.show()

# б) Глибина у % і глибина у мм
plt.figure(figsize=(10, 6))
sns.scatterplot(x='depth', y='z', data=data)
plt.title("Діаграма розсіювання для глибини у % і глибини у мм")
plt.show()

# Обчислення коефіцієнта кореляції
correlation_coefficient = data[['x', 'y', 'z', 'depth']].corr()
plt.figure(figsize=(10, 6))
sns.heatmap(correlation_coefficient, annot=True)
plt.title("Теплокарта коефіцієнта кореляції")
plt.show()
print("Коефіцієнт кореляції:\n", correlation_coefficient)