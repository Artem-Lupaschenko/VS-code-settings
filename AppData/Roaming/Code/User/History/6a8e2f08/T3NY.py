import numpy as np
import pandas as pd
from scipy import stats
import matplotlib.pyplot as plt
import seaborn as sns

# Згенеруємо приклад даних
data = pd.DataFrame({
    'Category': ['A', 'B', 'C', 'D'],
    'Value': [3, 8, 2, 5]
})

# Встановимо стиль seaborn
sns.set(style="whitegrid")

# Побудуємо стовпчикову діаграму
sns.barplot(x='Category', y='Value', data=data)