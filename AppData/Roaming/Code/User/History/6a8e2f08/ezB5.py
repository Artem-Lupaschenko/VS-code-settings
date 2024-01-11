import numpy as np
import pandas as pd


arr3 = np.ones((3,3), dtype=float)
operation1 = np.add(arr3, np.random.randint(low=5, size=9).reshape((3,3)))

print(operation1)