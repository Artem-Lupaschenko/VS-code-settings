import pandas as pd
from sklearn.model_selection import train_test_split
from sklearn.linear_model import LinearRegression
from sklearn.preprocessing import StandardScaler
from sklearn.pipeline import Pipeline
from sklearn.metrics import mean_absolute_error,silhouette_score
from sklearn.cluster import KMeans
import seaborn as sns
import matplotlib.pyplot as plt

# Завантаження даних
data = pd.read_csv("LR7\Crime.csv")

sns.set_theme()

data.info()
print(data.describe())

data_cor = data.loc[:, 'CrimeRate':'BelowWage'].corr()
sns.heatmap(data=data_cor, annot=True)
plt.xticks(rotation=45)
plt.show()

# Вибірка незалежних змінних (ознак)
X = data[['Youth','ExpenditureYear0']]

# Залежна змінна (рівень злочинності)
y = data['CrimeRate']

# Розбиття даних на тренувальний та тестовий набори
X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.25, random_state=0)

# Створення та навчання моделі лінійної регресії
model = Pipeline([('scale', StandardScaler()), ('lr', LinearRegression())]).fit(X_train, y_train)

# Прогнозування рівня злочинності на тестовому наборі
y_pred = model.predict(X_test)

# Вивід фактичних та 
print('y_test: ', y_test)
print('y_pred: ', y_pred)

# Оцінка точності моделі
print('Score: ', model.score(X_test, y_test))
print('Mean Absolute Error:', mean_absolute_error(y_test, y_pred))

diff_y = y_test - y_pred

# Візуалізація результатів
sns.scatterplot(diff_y)
plt.title('Різниця між фактичними та передбачуваними вихідними значеннями')
plt.show()

X = data[['ExpenditureYear0', 'StateSize']]

# Застосування KMeans для кластеризації
kmeans_pipeline = Pipeline([('scale', StandardScaler()), ('kmeans', KMeans(n_clusters=3, random_state=0))]).fit(X)

# Вивід оцінки силуету
print('Silhouette Score: ', silhouette_score(X, kmeans_pipeline.predict(X)))

# Візуалізація результатів
sns.scatterplot(x='StateSize', y='ExpenditureYear0', hue=kmeans_pipeline.predict(X), data=data, palette='viridis')
plt.title('Кластеризація регіонів')
plt.show()