import numpy as np
import pandas as pd


arr3 = np.ones((3,3), dtype=float)
arr1 = np.random.randint(1, 8, size=9).reshape((3,3))
operation1 = np.add(arr3, arr1)

print(arr1)
print(arr1.sum())