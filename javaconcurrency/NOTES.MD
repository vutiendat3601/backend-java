###### TERMS
- Conccurrency = Multitasking


## Why we need Threads?
### Responsiveness
- **`Achieved by concurrency`**
- Fast, continuous response from application
- Low request latency
### Performance
- **`Achieved by parallelism`**
- We can create an illusion of multiple tasks executing in parallel using just a `single core`.
- With `multiple cores` we can truly run tasks complete in parallel.
- Performance impact
  - Compling a complex task much faster
  - Finish more work in the same period of time

## Multithreading Caveat
- Multithreaded programming is fundamentally different from single threaded programming

## OS Process, Thread
- Process is also sometimes called context
- In a process, it includes:
  - Process metadata
  - Files opening with the process
  - Code
  - Data (Heap)
  - At least a `main thread`
- In a thread, it includes:
  - Stack: memory where methods ared called, arguments are passed, local variables are stored
  - Instruction pointer: Point to next instruction to execute
  - What threads share in a process:
    - `[Process metadata, Files, Code, Data]`.
  - *** State of thread's execution = Stack + Instruction pointer

### Context switch
- Context switch is not cheap, it's the price of multitasking.
- When switching:
  - Store data for one thread.
  - Restore data for another thread.

### Thread Termination
- Threads consume resources: memory, kernal, CPU cycles, cache.
- Thread is misbehaving, we want to stop it.
- The application will not stop as long as at least one thread is still running.

#### Thread Interruption
##### When we can interrupt a thread:
1. If the thread is executing a method that throws an InterruptedException.
2. If the thread's code is handling the interrupt signal explicitly (Thread.currentThread().isInterrupted()).

#### Daemon Thread
- Background threads that do not prevent the application from exiting if the main thread terminates.

### Thread Join
- Different threads run independently
- Order of execution is out of our control
- Using thread join to:
  - More control over independent threads
  - Safely collect, aggregate results
  - Gracefully handle runaway threads using Thread.join()

## WHEN to prefer Multithreaded architecture
- Tasks share a lot of data
- Threads are much faster to create and destroy
- Switching between threads of the same process is faster

## WHEN to prefer Multi-process architecture
- Security, stability are of higher importance
- Tasks are unrelated to each other

## Performance
### Latency
- The time to completion of a task. Measured in `timeUnit` (ms).
### Throughput
- The amount of tasks completed in a given period. Measured in `numberOfTasks/timeUnit` (1000 requests/second).

## Concurrency Challenge
### Race condition
- A concurrency bug that occurs when a program's behavior or outcome depends on the timing or order of execution of multiple threads or processes accessing shared resources.
### Critical Section
- A critical section is a part of code that accesses shared resources and must be protected to prevent race conditions when multiple threads execute concurrently.
### Synchronized
- Locking mechanism, used to restrict access to a critical section or entire method to a single thread at a time.
- 2 ways to use `synchronized`:
  + On method definition
  + On block of code
- `synchronized` block is reentrant. A thread cannot prevent itself from entering a critical section.

### Atomic Operation
- All reference assignments are atomic `[Object obj = new Object()]`
- Getters/setters are atomic
- All primitive assigments are atomic except long and double. Because `[long,double]` uses 2 halves (32-bits).
- `[long,double]` add `volatile` keyword to guarantee atomic
- Classes in the `java.util.concurrent.atomic` are atomic

### Deadlock
- Conditions for Deadlock
  + Mutual exclusion - Only one thread can have exclusive access to a resource
  + Hold and wait - At least one thread is holding a resource and is waiting for another resource
  + Non-preemptive allocation - A resource is released only after the thread is done using it
  + Circular wait - A chain of at least 2 threads each one is holding one resource and waiting for another resource

- Solution deadlock
  + Avoid Circular wait - Enforce a strict order in lock aquisition (recommendation)
  + Deadlock detection - Watchdog
  + Thread interruption (not possible with synchronized)
  + tryLock operations (not possible with synchronized)


## Locking in Java
### ReentrantLock
- Works just like the synchronized keyword applied on an object
- Requires explicit locking and unlocking

### ReentrantLock capacities
- Query methods - For testing
  + getQueuedThreads(), getOwner(), isHeldByCurrentThread(), isLocked()
  + Every production code needs to be thoroughly tested
  + Control the lock's fairness
  + lockInterruptibly()
  + tryLock()

### ReentrantReadWriteLock
- Race conditions require
  + Multiple threads sharing a resource
  + At least one thread modifying the resource

### ReentrantReadWriteLock capacities
- Mutual Exclusion between readers and writers:
  + If a write lock is acquired, no thread can acquire a read lock
  + If at least one thread holds a read lock, no thread can acquire a write lock
