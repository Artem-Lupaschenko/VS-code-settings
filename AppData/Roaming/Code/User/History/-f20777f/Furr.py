import numpy as np
import pandas as pd

data = pd.read_csv("iris.csv")
petal = np.array(data["petal_width"])

arr1 = np.array([3, 2, 6, 8, 2, 5], dtype=float)
arr2 = np.arange(0,16, 1.5)
arr3 = np.ones((3,3), dtype=float)
arr4 = np.zeros(7, dtype=bool)
print("\nГенерація невипадкових масивів різними способами:")
print("arr1 = " + arr1)
print(arr2)
print(arr3)
print(arr4)

randArr1 = np.linspace(0, 5, 10)
randArr2 = np.random.random(4)
randArr3 = np.random.randint(0, 20, (5,4))
randArr4 = np.empty((2, 4))
print("\nГенерація випадкових масивів різними способами:")
print(randArr1)
print(randArr2)
print(randArr3)
print(randArr4)

elem1 = arr1[3]
elem2 = arr1[-1]
elem3 = randArr3[0, 2]
elem4 = arr1[0:4]
elem5 = randArr3[0:2, 0:3]
print("\nЗвернення до елементів масиву за допомогою індексів, виділення підмасивів:")
print(elem1)
print(elem2)
print(elem3)
print(elem4)
print(elem5)

operation1 = np.add(arr3, 3)
operation2 = np.subtract(arr3, 3)
operation3 = np.multiply(arr3, 3)
operation4 = np.divide(arr3, 3)
operation5 = np.power(arr3, 3)
operation6 = np.mod(arr3, 3)
operation7 = np.negative(arr3)
operation8 = np.add.reduce(randArr3, 1)
operation9 = np.add.accumulate(randArr3, 1)
operation10 = np.multiply.outer(arr1, arr2)
print("\nОсновні арифметичні операції над масивами, методи reduce, accumulate, outer:")
print(operation1)
print(operation2)
print(operation3)
print(operation4)
print(operation5)
print(operation6)
print(operation7)
print(operation8)
print(operation9)
print(operation10)

stat1 = np.min(petal)
stat2 = np.max(petal)
stat3 = np.mean(petal)
stat4 = np.std(petal)
stat5 = np.median(petal)
stat6 = np.percentile(petal, 25)
stat7 = np.percentile(petal, 75)
print("\nВирахунок статистичних характеристик:")
print(stat1)
print(stat2)
print(stat3)
print(stat4)
print(stat5)
print(stat6)
print(stat7)


