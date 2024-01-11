from mpi4py import MPI
import numpy as np
import sys

# Метод для генерації матриці з одиницями по головній діагоналі
def generateMatrix(size, values):
	matrix = np.full((size, size), values, dtype=int)
	np.fill_diagonal(matrix, 1)
	return matrix

# Метод для генерації вектора з послідовними значеннями
def generateVector(fromValue, count):
	return np.arange(fromValue, fromValue + count, dtype=int)

# Метод обчислення MOh
def calcMAh(MD, MR, MX, MZ, a):
	# Обчислення MX*MR
	result1 = np.dot(MX, MR)
	# Обчислення MZ*MD
	result2 = np.dot(MZ, MD)
	# Обчислення a*(MZ*MD)
	result3 = result2 * a
	# Обчислення MA = MX*MR - a*(MZ*MD)
	return result1-result3

n = 8
comm = MPI.COMM_WORLD
p = comm.Get_rank()
P = comm.Get_size()
H = n // P
prev = p-1
next = p+1

print(f"T{p+1}: Started")
startTime = MPI.Wtime()
if p == 0:
	MD = generateMatrix(n, 3)
	C = generateVector(3, n)

	MR = np.empty((n, H), dtype=int)
	MX = np.empty((n, n), dtype=int)
	MZ = np.empty((n, n), dtype=int)

	comm.Send(C[H:].copy(), dest=next)
	C = np.delete(C, np.s_[H:])
	comm.Send(MD[:, H:].copy(), dest=next)
	MD = np.delete(MD, np.s_[H:], axis=1)
	comm.Recv(MR, source=next)

	a1 = np.min(C)
	comm.send(a1, dest=next)

	comm.Recv(MX, source=next)
	comm.Recv(MZ, source=next)
	a = comm.recv(source=next)

	MAh = calcMAh(MD, MR, MX, MZ, a)
	comm.Send(MAh.copy(), dest=next)

elif p == 1:
	MA =np.empty((n, n), dtype=int)
	MX = generateMatrix(n, 4)
	MZ = generateMatrix(n, 5)

	C3h = np.empty(3*H, dtype=int)
	MD3h = np.empty((n, 3*H), dtype=int)
	MR2h = np.empty((n, 2*H), dtype=int)
	MA2h = np.empty((n, 2*H), dtype=int)

	comm.Recv(C3h, source=prev)
	comm.Recv(MD3h, source=prev)
	comm.Send(C3h[H:].copy(), dest=next)
	C = np.delete(C3h, np.s_[H:])
	comm.Send(MD3h[:, H:].copy(), dest=next)
	MD = np.delete(MD3h, np.s_[H:], axis=1)
	comm.Recv(MR2h, source=next)
	comm.Send(MR2h[:, :H].copy(), dest=prev)
	MR = np.delete(MR2h, np.s_[:H], axis=1)

	a2 = np.min(C)
	a1 = comm.recv(source=prev)
	a = np.min([a1, a2])
	a3 = comm.recv(source=next)
	a4 = comm.recv(source=next)
	a= np.min([a, a3])
	a= np.min([a, a4])
	
	comm.Send(MX.copy(), dest=prev)
	comm.Send(MX.copy(), dest=next)
	comm.Send(MZ.copy(), dest=prev)
	comm.Send(MZ.copy(), dest=next)
	comm.send(a, dest=prev)
	comm.send(a, dest=next)

	MAh = calcMAh(MD, MR, MX, MZ, a)
	MA[:, H:2*H] = MAh
	comm.Recv(MAh, source=prev)
	comm.Recv(MA2h, source=next)
	MA[:, :H] = MAh
	MA[:, 2*H:] = MA2h
	print(f"MA = {MA}")

elif p == 2:
	C2h = np.empty(2*H, dtype=int)
	MD2h = np.empty((n, 2*H), dtype=int)
	MR3h = np.empty((n, 3*H), dtype=int)
	MX = np.empty((n, n), dtype=int)
	MZ = np.empty((n, n), dtype=int)
	MA2h = np.empty((n, 2*H), dtype=int)
	MAh = np.empty((n, H), dtype=int)
	
	comm.Recv(MR3h, source=next)
	comm.Recv(C2h, source=prev)
	comm.Recv(MD2h, source=prev)
	comm.Send(MR3h[:, :2*H].copy(), dest=prev)
	MR = np.delete(MR3h, np.s_[:2*H], axis=1)
	comm.Send(C2h[H:].copy(), dest=next)
	C = np.delete(C2h, np.s_[H:])
	comm.Send(MD2h[:, H:].copy(), dest=next)
	MD = np.delete(MD2h, np.s_[H:], axis=1)

	a3 = np.min(C)
	a4 = comm.recv(source=next)
	comm.send(a3, dest=prev)
	comm.send(a4, dest=prev)

	comm.Recv(MX, source=prev)
	comm.Send(MX.copy(), dest=next)
	comm.Recv(MZ, source=prev)
	comm.Send(MZ.copy(), dest=next)
	a = comm.recv(source=prev)
	comm.send(a, dest=next)

	MA2h[:, :H]=calcMAh(MD, MR, MX, MZ, a)
	comm.Recv(MAh, source=next)
	MA2h[:, H:]=MAh
	comm.Send(MA2h.copy(), dest=prev)

elif p == 3:
	MR = generateMatrix(n, 2)

	C = np.empty(H, dtype=int)
	MD = np.empty((n, H), dtype=int)
	MX = np.empty((n, n), dtype=int)
	MZ = np.empty((n, n), dtype=int)

	comm.Send(MR[:, :3*H].copy(), dest=prev)
	MR = np.delete(MR, np.s_[:3*H], axis=1)
	comm.Recv(C, source=prev)
	comm.Recv(MD, source=prev)

	a4 = np.min(C)
	comm.send(a4, dest=prev)

	comm.Recv(MX, source=prev)
	comm.Recv(MZ, source=prev)
	a = comm.recv(source=prev)

	MAh = calcMAh(MD, MR, MX, MZ, a)
	comm.Send(MAh.copy(), dest=prev)

print(f"T{p+1}: Finished; Execution Time: {MPI.Wtime() - startTime} seconds")
MPI.Finalize()
