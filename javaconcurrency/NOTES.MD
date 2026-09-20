###### TERMS
- Conccurrency = Multitasking


## Why we need Threads?
### Responsiveness
- **`Achieved by concurrency`**
- Fast, continuous response from application
- Low request latency
### Performance
- **`Achieved by parallelism`**
- We can create an illusion of multiple tasks executing in parallel using just a `single core`
- With `multiple cores` we can truly run tasks complete in parallel
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
    - `[Process metadata, Files, Code, Data]`

### Context switch
- Context switch is not cheap, it's the price of multitasking
- When switching:
  - Store data for one thread
  - Restore data for another thread

## WHEN to prefer Multithreaded architecture
- Tasks share a lot of data
- Threads are much faster to create and destroy
- Switching between threads of the same process is faster

## WHEN to prefer Multi-process architecture
- Security, stability are of higher importance
- Tasks are unrelated to each other
