import pandas as pd
import numpy as np
from sklearn.model_selection import GridSearchCV, train_test_split
from sklearn.linear_model import LogisticRegression
from sklearn.preprocessing import LabelBinarizer
from sklearn.metrics import accuracy_score, classification_report, confusion_matrix
from sklearn.ensemble import RandomForestClassifier
from sklearn.preprocessing import StandardScaler

# Зчитування даних з CSV файлу
data = pd.read_csv('LR8\possum.csv')

# Вибір необхідних ознак для класифікації
features = ['age', 'hdlngth', 'skullw', 'totlngth', 'taill', 'footlgth']

# Кодування змінної 'sex' бінарними значеннями (0 - жіноча, 1 - чоловіча)
le = LabelBinarizer()
data['sex'] = le.fit_transform(data['sex'])

# Визначення кількості екземплярів різних класів
female_count = len(data[data['sex'] == 0])
male_count = len(data[data['sex'] == 1])

# Відношення кількості екземплярів класу жінок до класу чоловіків
balance_ratio = female_count / male_count
print(f"Відношення кількості екземплярів класу жінок до класу чоловіків: {balance_ratio}")

# Розділення даних на тренувальний та тестовий набори
X_train, X_test, y_train, y_test = train_test_split(data[features], data['sex'], random_state=0)

X_train = 

# Створення та тренування моделі логістичної регресії
model = LogisticRegression(class_weight={0:1, 1:balance_ratio})
model.fit(X_train, y_train)

# Прогнозування класів на тестовому наборі
y_pred = model.predict(X_test)

# Оцінка точності моделі
accuracy = accuracy_score(y_test, y_pred)
print(f'LogisticRegression accuracy: {accuracy}')
print(classification_report(y_test, y_pred))

# Визначення гіперпараметрів для перебору
param_grid = {'C': np.logspace(-1, 1, num=10)}

# Створення об'єкту GridSearchCV
grid_search = GridSearchCV(model, param_grid, cv=5)

# Пошук оптимальних гіперпараметрів
grid_search.fit(X_train, y_train)

# Виведення оптимальних гіперпараметрів
print("Best Parameters:", grid_search.best_params_)

# Оцінка моделі на тестовому наборі
accuracy = grid_search.score(X_test, y_test)
print(f'Grid accuracy: {accuracy}')

# Розділення даних на тренувальний та тестовий набори
X_train, X_test, y_train, y_test = train_test_split(data[features], data['sex'], random_state=0)

# Використання іншого алгоритму класифікації
forest_model = RandomForestClassifier(n_estimators=100, random_state=0)
forest_model.fit(X_train, y_train)

# Зробити прогнози на тестовому наборі
y_pred = forest_model.predict(X_test)

# Оцінка точності
accuracy = accuracy_score(y_test, y_pred)
print(f'RandomForestClassifier accuracy: {accuracy}')

# Оцінка інших метрик
print(classification_report(y_test, y_pred))
print(confusion_matrix(y_test, y_pred))

