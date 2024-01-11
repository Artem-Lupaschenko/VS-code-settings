import numpy as np
import pandas as pd

data = pd.read_csv("LR1\iris.csv")
petal = np.array(data["petal_width"])

arr1 = np.array([3, 2, 6, 8, 2, 5], dtype=float)
arr2 = np.arange(0,16, 1.5)
arr3 = np.ones((3,3), dtype=float)
arr4 = np.zeros(7, dtype=bool)
print("\nГенерація невипадкових масивів різними способами:")
print(f"arr1 = {arr1}")
print(f"arr2 = {arr2}")
print(f"arr3 = \n{arr3}")
print(f"arr4 = {arr4}")

randArr1 = np.linspace(0, 5, 10)
randArr2 = np.random.random(4)
randArr3 = np.random.randint(0, 20, (5,4))
randArr4 = np.empty((2, 4))
print("\nГенерація випадкових масивів різними способами:")
print(f"randArr1 = \n{randArr1}")
print(f"randArr2 = {randArr2}")
print(f"randArr3 = \n{randArr3}")
print(f"randArr4 = \n{randArr4}")

elem1 = arr1[3]
elem2 = arr1[-1]
elem3 = randArr3[0, 2]
elem4 = arr1[0:4]
elem5 = randArr3[0:2, 0:3]
print("\nЗвернення до елементів масиву за допомогою індексів, виділення підмасивів:")
print(f"arr1[3] = {elem1}")
print(f"arr1[-1] = {elem2}")
print(f"randArr3[0, 2] = {elem3}")
print(f"arr1[0:4] = {elem4}")
print(f"randArr3[0:2, 0:3] = \n{elem5}")

operation1 = np.add(arr3, 3)
operation2 = np.subtract(arr3, 3)
operation3 = np.multiply(arr3, 3)
operation4 = np.divide(arr3, 3)
operation5 = np.power(arr1, 3)
operation6 = np.mod(arr1, 3)
operation7 = np.negative(arr3)
operation8 = np.add.reduce(randArr3, 1)
operation9 = np.add.accumulate(randArr3, 1)
operation10 = np.multiply.outer(arr1, arr2)
print("\nОсновні арифметичні операції над масивами, методи reduce, accumulate, outer:")
print(f"arr3 + 3 = \n{operation1}")
print(f"arr3 - 3 = \n{operation2}")
print(f"arr3 * 3 = \n{operation3}")
print(f"arr3 / 3 = \n{operation4}")
print(f"arr1 ** 3 = {operation5}")
print(f"arr1 % 3 = {operation6}")
print(f"-arr3 = \n{operation7}")
print(f"np.add.reduce(randArr3, 1) = {operation8}")
print(f"np.add.accumulate(randArr3, 1) = \n{operation9}")
print(f"np.multiply.outer(arr1, arr2) = \n{operation10}")

stat1 = np.min(petal)
stat2 = np.max(petal)
stat3 = np.mean(petal)
stat4 = np.std(petal)
stat5 = np.median(petal)
stat6 = np.percentile(petal, 25)
stat7 = np.percentile(petal, 75)
print("\nВирахунок статистичних характеристик:")
print(f"min = {stat1}")
print(f"max = {stat2}")
print(f"mean = {stat3}")
print(f"std = {stat4}")
print(f"median = {stat5}")
print(f"percentile25 = {stat6}")
print(f"percentile75 = {stat7}")


