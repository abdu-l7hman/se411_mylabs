# Lab 12 – Threads and locks

Run (in `lab12_threads_locks`):
- Fixed thread pool: `clean package exec:java`
- Cached thread pool: `clean package exec:java -Dexec.mainClass=edu.psu.se411.lab12_threads.AppCachedThreadPool`
- Deadlock demo: `clean package exec:java -Dexec.mainClass=edu.psu.se411.lab12_threads.AppDeadlockDemo`

`simulation/Simulation` holds the code shared by the three programs.

## a) Fixed vs cached thread pool (1 000 000 transactions)
The starting code used `newFixedThreadPool(nbTransactions)` (one thread per transaction);
the fixed pool now has one thread per CPU core.

Example run (2-core machine; your numbers will differ):

| Accounts | Fixed pool | Cached pool |
|---|---|---|
| 1000 | 550 ms, 8 threads | 4 036 ms, 63 threads |
| 10 | 431 ms, 8 threads | 3 956 ms, 131 threads |

**Observation:** the fixed pool is much faster. The tasks are tiny and there are a million of them,
so the cached pool keeps creating new threads whenever all its threads are busy; creating and
scheduling those threads costs more than the work itself. With only 10 accounts there is more
contention: threads block on the same locks, the cached pool creates even more threads (131 vs 63),
and those extra threads cannot run in parallel anyway because they wait for the same accounts.
A fixed pool sized to the number of cores bounds this overhead. In both cases the money is conserved.

## b) Locks instead of `synchronized`
Each `CryptoAccount` owns a `ReentrantLock`. A transaction locks both accounts, moves the money, then
unlocks them in `finally` blocks.

## c) What could go wrong?
**Deadlock.** If transaction T1 (A → B) locks A and transaction T2 (B → A) locks B at the same time,
T1 waits for B while T2 waits for A, forever. `AppDeadlockDemo` shows it with `LockingStrategy.NAIVE`
(lock sender, then receiver): the run gets stuck and the JVM reports the deadlocked threads.

## d) Resource ordering
`LockingStrategy.ORDERED` (the default): accounts have a total order by id (`compareTo`), and a
transaction always locks the smaller id first. Two transactions on the same pair of accounts
therefore lock in the same order, so a circular wait is impossible and the program always finishes
with the money conserved.
