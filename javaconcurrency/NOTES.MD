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
  - Stack
  - Instruction pointer
  - What threads share in a process:
    - `[Process metadata, Files, Code, Data]`.

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
2. If the thread's code is handling the interrupt signal explicitly.

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
- The amount of tasks completed in a given period. Measured in `numberOfTasks/timeUnit` (1000 requests/second)