# Lab 11 – Introduction to threads (double spending problem)

Run (in `lab11_threads`): `clean package exec:java`

10 accounts × 1000 riyals = 10 000 riyals; 10 000 threads each run one random
`Transaction(randomA, randomB, randomAmount).execute()`; `App.calculateTotal()` sums the money afterwards.

**Part 1 – without synchronization:** debit and credit are read-modify-write steps on the balances,
so two threads can read the same balance and one update overwrites the other. The total is no longer
10 000 (example runs: 10 412.15, 9 960.85, 9 897.79 riyals).

**Part 2 – `synchronized` on one method:** `Transaction.transfer()` is `private static synchronized`.
Being static, its lock is the `Transaction` class itself, so only one transfer (debit + credit) runs at a
time and the total always stays 10 000 riyals. (A non-static synchronized method would not help: each
thread has its own `Transaction` object, so each would lock a different object.)
