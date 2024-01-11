import numpy as np
import pandas as pd
from scipy import stats

# Завантаження даних
data = pd.read_csv("LR2\Birthweight.csv")
# Визначення рівня значущості
alpha = 0.05


print("\nЗавдання 1: Знайти середній вік матерів і батьків і порівняти ці середні значення")

# Знайдемо середні значення
avg_mage = np.mean(np.array(data['mage']))
avg_fage = np.mean(np.array(data['fage']))

print(f"Середній вік матерів: {avg_mage}")
print(f"Середній вік батьків: {avg_fage}")

# Порівняння середніх значень
if avg_mage > avg_fage:
    print("Середній вік матерів більше середнього віку батьків\n")
elif avg_mage < avg_fage:
    print("Середній вік матерів менше середнього віку батьків\n")
else:
    print("Середній вік матерів дорівнює середньому віку батьків\n")

print("Завдання 2: Перевірити чи нормально розподілена вага дітей")

# Перевірка нормальності розподілу ваги
p_value = stats.normaltest(np.array(data['Birthweight']))[1]

print(f"Розв'язок: stats.normaltest(data['Birthweight'])")
print(f"Значення p: {p_value}")

# Визначення, чи має вага дітей нормальний розподіл
if p_value > alpha:
    print("Вага дітей має нормальний розподіл.\n")
else:
    print("Вага дітей не має нормального розподілу.\n")

print("Завдання 3: Перевірити за допомогою статистичних гіпотез чи у матерів, що палять, легші діти")

# Розділити дані на групи: палють і не палють
smoke_group = data[data['smoker'] == 1]['Birthweight']
no_smoke_group = data[data['smoker'] == 0]['Birthweight']

# Перевірка нормальності розподілу для обох груп
if (stats.normaltest(smoke_group).pvalue > alpha and stats.normaltest(no_smoke_group).pvalue > alpha):
    print(f"Дані мають нормальний розподіл")
    print(f"Розв'язок: stats.ttest_ind(smoke_group, no_smoke_group, alternative='less')")
    # Використовуємо t-тест Стьюдента для незалежних даних, які мають нормальний розподіл
    p_value = stats.ttest_ind(smoke_group, no_smoke_group, alternative='less')[1]
else:
    print(f"Дані не мають нормального розподілу")
    print(f"Розв'язок: stats.mannwhitneyu(smoke_group, no_smoke_group, alternative='less')")
    # Використовуємо тест Манна-Уітні для незалежних даних, які не мають нормальний розподіл
    p_value = stats.mannwhitneyu(smoke_group, no_smoke_group, alternative='less')[1]

print(f"Значення p: {p_value}")

# Визначення, чи легші діти у матерів, що палять
if p_value < alpha:
    print("Приймаємо альтернативну гіпотезу: Вага дітей у матерів, що палять, менше ваги дітей у матерів, що не палять\n")
else:
    print("Приймаємо основну гіпотезу: Вага дітей у матерів, що палять, рівна вазі дітей у матерів, що не палять\n")

print("Завдання 4: Чи є зв’язок між зростом матері та дитини? Визначити за допомогою кореляційного аналізу")

# Знайдемо ріст матерів та їх дітей
mother_height = data['mheight']
child_length = data['Length']

# Перевірка нормальності розподілу для обох змінних
if (stats.normaltest(mother_height).pvalue > alpha and stats.normaltest(child_length).pvalue > alpha):
    print(f"Дані мають нормальний розподіл")
    print(f"Розв'язок: stats.pearsonr(mother_height, child_length)")
    # Використовуємо метод обчислення коефіцієнта кореляції Пірсона для даних, які мають нормальний розподіл
    p_value = stats.pearsonr(mother_height, child_length)[1]
else:
    print(f"Дані не мають нормального розподілу")
    print(f"Розв'язок: stats.spearmanr(mother_height, child_length)")
    # Використовуємо метод для обчислення коефіцієнта кореляції Спірмена для даних, які не мають нормальний розподіл
    p_value = stats.spearmanr(mother_height, child_length)[1]

print(f"Значення p: {p_value}")

# Визначення, чи є зв'язок між зростом матері та дитини
if p_value < alpha:
    print("Приймаємо альтернативну гіпотезу: кореляційний зв'язок між даними є\n")
else:
    print("Приймаємо основну гіпотезу: кореляційного зв'язку між даними немає\n")