from mpi4py import MPI
import numpy as np

def dot_product(X, Y):
	return sum(x * y for x, y in zip(X, Y))

def scalar_vector_product(X, x):
	return [xi * x for xi in X]

def matrix_product(MX, MY):
	return np.dot(MX, MY)

def vector_matrix_product(X, MX):
	return np.dot(X, MX)

def vector_sum(X, Y):
	return [xi + yi for xi, yi in zip(X, Y)]

def vector_diff(X, Y):
	return [xi - yi for xi, yi in zip(X, Y)]

def calc_A(e, T, Z, MX, MR):
	MK = matrix_product(MX, MR)
	L = vector_matrix_product(T, MK)
	C = scalar_vector_product(Z, e)
	A = vector_diff(C, L)
	return A

def all_to_all(left, right, self):
	for i in range(7):
		low = (self - 1) % 8
		high = (self + 1) % 8
		if i % 2 == 1:
			comm.Recv(T[high * H:(high + 1) * H], source=left)
			comm.Send(T[high * H:(high + 1) * H], dest=right)
			high -= 1
		else:
			comm.Recv(T[low * H:(low + 1) * H], source=right)
			comm.Send(T[low * H:(low + 1) * H], dest=left)
			low += 1

comm = MPI.COMM_WORLD
world_rank = comm.Get_rank()
world_size = comm.Get_size()
if world_size != 8:
	print("Please run this application with 8 processes.")
	MPI.Finalize()
	exit(1)
N = 8
P = 8
H = N // P
Bh = np.zeros(H, dtype=int)
MX = np.zeros((N, N), dtype=int)
Zh = np.zeros(H, dtype=int)
Dh = np.zeros(H, dtype=int)
Ch = np.zeros(H, dtype=int)
MRh = np.zeros((N, H), dtype=int)
A = np.zeros((N,), dtype=int)
T = np.zeros(N, dtype=int)
e = 0
world_group = MPI.COMM_WORLD.Get_group()
ranks = [7, 0, 1]
neighbours_group = world_group.Incl(ranks)
neighbours_communicator = MPI.COMM_WORLD.Create(neighbours_group)
print(f&quot;T {world_rank + 1} has been started!&quot;)
if world_rank == 0:
B = np.ones(N, dtype=int)
comm.Send(B[H:5 * H], dest=1)
comm.Send(B[5 * H:], dest=7)
Bh = B[:H]
comm.Recv(MX, source=1)
comm.Send(MX, dest=7)
Z3h = np.zeros(3 * H, dtype=int)
D3h = np.zeros(3 * H, dtype=int)
comm.Recv(Z3h, source=1)
comm.Send(Z3h[2 * H:], dest=7)
Zh = Z3h[2 * H:3 * H]
comm.Recv(D3h, source=1)
comm.Send(D3h[2 * H:], dest=7)
Dh = D3h[2 * H:3 * H]
comm.Recv(Ch, source=1)
comm.Recv(MRh, source=1)
T[0:H] = vector_sum(Dh, Zh)
comm.Send(T[0:H], dest=7)
comm.Send(T[0:H], dest=1)
all_to_all(7, 1, 0)
e1 = vector_sum(Bh, Ch)
e_list = np.zeros(8, dtype=int)
e_list[0] = e1
comm.Recv(e_list[1:5], source=1)

comm.Recv(e_list[5:], source=7)
e = sum(e_list)
neighbours_communicator.Bcast(e, 0)
Ah = calc_A(e, T, Zh, MX, MRh)
A4h = np.zeros(4 * H, dtype=int)
A4h[3 * H:] = Ah
comm.Recv(A4h[:3 * H], source=7)
comm.Send(A4h, dest=1)

elif world_rank == 1:
B4h = np.zeros(4 * H, dtype=int)
Z4h = np.zeros(4 * H, dtype=int)
D4h = np.zeros(4 * H, dtype=int)
C2h = np.zeros(3 * H, dtype=int)
A4h = np.zeros(4 * H, dtype=int)
A8h = np.zeros(8 * H, dtype=int)
MR2h = [[0 for i in range(2 * H)] for j in range(N)]
MX = [[0 for i in range(N)] for j in range(N)]
comm.Send(MX, dest=0)
comm.Send(MX, dest=2)
comm.Recv(B4h, source=0)
comm.Send(B4h[H:], dest=2)
Bh = B4h[:H]
comm.Recv(Z4h, source=2)
comm.Send(Z4h[:3 * H], dest=0)
Zh = Z4h[3 * H:4 * H]
comm.Recv(D4h, source=2)
comm.Send(D4h[:3 * H], dest=0)
Dh = D4h[3 * H:4 * H]
comm.Recv(C2h, source=2)
comm.Send(C2h[:H], dest=0)
Ch = C2h[H:2 * H]
comm.Recv(MR2h, source=2)
MRh = MR2h[:][H:2 * H]
comm.Send(MR2h[:][:H], dest=0)
T[H:2 * H] = vector_sum(Dh, Zh)
comm.Send(T[H:2 * H], dest=0)
comm.Send(T[H:2 * H], dest=2)
all_to_all(0, 2, 1)
e2 = vector_sum(Bh, Ch)
e_list = np.zeros(4, dtype=int)
e_list[0] = e2
comm.Recv(e_list[1:], source=2)
comm.Send(e_list, dest=0)
neighbours_communicator.Bcast(e, 0)
comm.Send(e, dest=2)
Ah = calc_A(e, T, Zh, MX, MRh)
comm.Recv(A4h, source=0)
comm.Recv(A8h[2 * H:5 * H], source=1)
A8h[H:2 * H] = Ah
A8h[:H] = A4h[3 * H:]

A8h[5 * H:] = A4h[:3 * H]
print(A8h)
elif world_rank == 2:
Z = np.ones(N, dtype=int)
D = np.ones(N, dtype=int)
Z4h = np.zeros(4 * H, dtype=int)
Z3h = np.zeros(3 * H, dtype=int)
A3h = np.zeros(3 * H, dtype=int)
D4h = np.zeros(4 * H, dtype=int)
C3h = np.zeros(4 * H, dtype=int)
MR3h = [[0 for i in range(3 * H)] for j in range(N)]
MR2h = [[0 for i in range(2 * H)] for j in range(N)]
B3h = np.zeros(3 * H, dtype=int)
Z3h = Z[4 * H:7 * H]
Z4h[:H] = Z[7 * H:]
Z4h[H:2 * H] = Z[:H]
Z4h[2 * H:] = Z[2 * H:4 * H]
D3h = D[4 * H:7 * H]
D4h[:H] = D[7 * H:]
D4h[H:2 * H] = D[:H]
D4h[2 * H:] = D[2 * H:4 * H]
comm.Send(Z4h, dest=1)
comm.Send(Z3h, dest=3)
comm.Send(D4h, dest=1)
comm.Send(D3h, dest=3)
comm.Recv(MX, source=1)
comm.Send(MX, dest=3)
comm.Recv(C3h, source=3)
comm.Send(C3h[:2 * H], dest=1)
Ch = C3h[2 * H:3 * H]
comm.Recv(MR3h, source=3)
comm.Send(MR3h[:][:2 * H], dest=1)
MRh = MR3h[:][2 * H:3 * H]
comm.Recv(B3h, source=1)
comm.Send(B3h[H:], dest=3)
Bh = B3h[:H]
T[2 * H:3 * H] = vector_sum(Dh, Zh)
comm.Send(T[2 * H:3 * H], dest=1)
comm.Send(T[2 * H:3 * H], dest=3)
all_to_all(1, 3, 2)
e3 = vector_sum(Bh, Ch)
e_list = np.zeros(3, dtype=int)
e_list[0] = e3
comm.Recv(e_list[1:], source=3)
comm.Send(e_list, dest=1)
comm.Recv(e, 1)
comm.Send(e, dest=3)
Ah = calc_A(e, T, Zh, MX, MRh)
comm.Recv(A3h[H:], source=3)
A3h[:H] = Ah
comm.Send(A3h, dest=1)

elif world_rank == 3:
C = np.ones(N, dtype=int)
Z3h = np.ones(3 * H, dtype=int)
D3h = np.ones(3 * H, dtype=int)
MR = [[1 for i in range(N)] for j in range(N)]
B2h = np.ones(2 * H, dtype=int)
A2h = np.ones(2 * H, dtype=int)
comm.Send(C[:3 * H], dest=2)
Ch = C[3 * H:4 * H]
comm.Send(MR[:][:3 * H], dest=2)
MRh = MR[:][3 * H:4 * H]
comm.Send(C[4 * H:].reshape(4 * H), dest=4)
comm.Send(MR[:][4 * H:], dest=4)
comm.Recv(Z3h, source=2)
comm.Send(Z3h[H:], dest=4)
Zh = Z3h[:H]
comm.Recv(D3h, source=2)
comm.Send(D3h[H:], dest=4)
Dh = D3h[:H]
comm.Recv(MX, source=2)
comm.Send(MX, dest=4)
comm.Recv(B2h, source=2)
comm.Send(B2h[H:], dest=4)
Bh = B2h[:H]
T[3 * H:4 * H] = vector_sum(Dh, Zh)
comm.Send(T[3 * H:4 * H], dest=2)
comm.Send(T[3 * H:4 * H], dest=4)
all_to_all(2, 4, 3)
e4 = vector_sum(Bh, Ch)
e_list = np.zeros(2, dtype=int)
e_list[0] = e4
comm.Recv(e_list[1:], source=4)
comm.Send(e_list, dest=2)
comm.Recv(e, 2)
comm.Send(e, dest=4)
Ah = calc_A(e, T, Zh, MX, MRh)
comm.Recv(A2h[H:], source=4)
A2h[:H] = Ah
comm.Send(A2h, dest=2)
elif world_rank == 4:
C4h = np.zeros(4 * H, dtype=int)
MR4h = [[0 for i in range(4 * H)] for j in range(N)]
MR3h = [[0 for i in range(3 * H)] for j in range(N)]
Z2h = np.zeros(2 * H, dtype=int)
D2h = np.zeros(2 * H, dtype=int)
comm.Recv(C4h, source=3)
comm.Send(C4h[H:], dest=5)
Ch = C4h[:H]
comm.Recv(MR4h, source=3)
M3h = MR4h[:][H:]
MRh = MR4h[:][:H]

comm.Send(MR3h, dest=5)
comm.Recv(Z2h, source=3)
comm.Send(Z2h[H:], dest=5)
Zh = Z2h[:H]
comm.Recv(D2h, source=3)
comm.Send(D2h[H:], dest=5)
Dh = D2h[:H]
comm.Recv(MX, source=3)
comm.Send(MX, dest=5)
comm.Recv(Bh, source=3)
T[4 * H:5 * H] = vector_sum(Dh, Zh)
comm.Send(T[4 * H:5 * H], dest=3)
comm.Send(T[4 * H:5 * H], dest=5)
all_to_all(3, 5, 4)
e5 = vector_sum(Bh, Ch)
comm.Send(e5, dest=3)
comm.Recv(e, 3)
Ah = calc_A(e, T, Zh, MX, MRh)
comm.Send(Ah, dest=3)
elif world_rank == 5:
C3h = np.zeros(3 * H, dtype=int)
MR3h = [[0 for i in range(3 * H)] for j in range(N)]
MR2h = [[0 for i in range(2 * H)] for j in range(N)]
comm.Recv(Bh, source=6)
comm.Recv(C3h, source=4)
comm.Send(C3h[H:], dest=6)
Ch = C3h[:H]
comm.Recv(MR3h, source=4)
MR2h = MR3h[:][H: 3 * H]
MRh = MR3h[:H]
comm.Send(MR2h, dest=6)
comm.Recv(Zh, source=4)
comm.Recv(Dh, source=4)
comm.Recv(MX, source=4)
T[5 * H:6 * H] = vector_sum(Dh, Zh)
comm.Send(T[5 * H:6 * H], dest=4)
comm.Send(T[5 * H:6 * H], dest=6)
all_to_all(4, 6, 5)
e6 = vector_sum(Bh, Ch)
comm.Send(e6, dest=6)
comm.Recv(e, 6)
Ah = calc_A(e, T, Zh, MX, MRh)
comm.Send(Ah, dest=6)
elif world_rank == 6:
B2h = np.zeros(2 * H, dtype=int)
C2h = np.zeros(2 * H, dtype=int)
MR2h = [[0 for i in range(2 * H)] for j in range(N)]
A2h = np.ones(2 * H, dtype=int)
comm.Recv(B2h, source=7)
comm.Send(B2h[:H], dest=5)
Bh = B2h[H:2 * H]

comm.Recv(MX, source=7)
comm.Recv(Zh, source=7)
comm.Recv(Dh, source=7)
comm.Recv(C2h, source=5)
comm.Send(C2h[H:], dest=7)
Ch = C2h[:H]
comm.Recv(MR2h, source=5)
MRh = MR2h[:][:H]
comm.Send(MRh[:][H:], dest=7)
T[6 * H:7 * H] = vector_sum(Dh, Zh)
comm.Send(T[6 * H:7 * H], dest=5)
comm.Send(T[6 * H:7 * H], dest=7)
all_to_all(5, 7, 6)
e7 = vector_sum(Bh, Ch)
e_list = np.zeros(2, dtype=int)
e_list[1] = e7
comm.Recv(e_list[0], source=5)
comm.Send(e_list, dest=7)
comm.Recv(e, 7)
Ah = calc_A(e, T, Zh, MX, MRh)
comm.Recv(A2h[:H], source=5)
A2h[H:] = Ah
comm.Send(A2h, dest=7)

elif world_rank == 7:
B3h = np.zeros(3 * H, dtype=int)
Z2h = np.zeros(2 * H, dtype=int)
D2h = np.zeros(2 * H, dtype=int)
A3h = np.ones(3 * H, dtype=int)
comm.Recv(B3h, source=0)
comm.Send(B3h[:2 * H], dest=6)
Bh = B3h[2 * H:]
comm.Recv(MX, source=0)
comm.Send(MX, dest=6)
comm.Recv(Z2h, source=0)
comm.Send(Z2h[H:], dest=6)
Zh = Z2h[:H]
comm.Recv(D2h, source=0)
comm.Send(D2h[H:], dest=6)
Dh = D2h[:H]
comm.Recv(Ch, source=6)
comm.Recv(MRh, source=6)
T[7 * H:8 * H] = vector_sum(Dh, Zh)
comm.Send(T[7 * H:8 * H], dest=6)
comm.Send(T[7 * H:8 * H], dest=0)
all_to_all(6, 0, 7)
e8 = vector_sum(Bh, Ch)
e_list = np.zeros(3, dtype=int)
e_list[3] = e8
comm.Recv(e_list[:3], source=6)
comm.Send(e_list, dest=0)
comm.Recv(e, source=0)

Ah = calc_A(e, T, Zh, MX, MRh)
comm.Recv(A3h[:2 * H], source=5)
A3h[2 * H:] = Ah
comm.Send(A3h, dest=0)
print(f&quot;T {world_rank + 1} has been finished!&quot;)
MPI.Finalize()