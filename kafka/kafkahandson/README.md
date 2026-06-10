#### Topic
##### Topic
- A particular **`stream of data`**.
- Like a table in database.
- There're many topics as user want.
- A topic is identified with its **`name`**.
- The sequence of messages is called a data stream.
- **`Topics`** are can't be queried in Kafka.
- Kafka topics are **`immutable`**, we can't modifying message, reorder, or reset index.

##### Partition
- A topic are splitted in **`partitions`**.
- Messages within each partition are **`ordered`**.
- Each message within a partition gets an incremental id, called **`offset`**. And **`offset`** has only meaning in a partition.
- Data is **`assigned randomly in a partition`** unless a **`key`** is provided.

#### Producer
- **`Producers`** write data to topics. **`Producer`** knows to which **`partition`** to write to.
- **`Producer`** can choose to send a **`key`** with the message (string, number, binary, etc...).
- If **`key==null`**, data is sent round robin (partition 0, 1, 2 ... n, then partition 0).
- If **`key!=null`**, all messages for that key will always go to the same **`partition`** (hashing - default is murmur2algorithm).
- A key typically sent if you need **`message ordering for a specific field`**.
- Kafka only accepts bytes as `producer input`/`consumer output`. So we need `key/value serializer`.
- `key/value serializer` must not change during a topic lifecycle.
##### Message Structure
Key: `any` | `null`
Value: `any` | `null`
Compression type: `none` | `gzip` | `snappy` | `lz4` | `zstd`
Headers (optional): [`key_value_1`,`key_value_2`,...]
**`Partition`** + **`Offset`**
Timestamp (system or user set)

#### Consumer
- **`Consumers`** read data from a topic by **`pulling`**.
- **`Consumer`** automatically know from which broker to read.
- Data is read in order from low to high offset within each **`Partition`**.

##### Consumer Group
- All consumers in an application read data in a topic as a **`Consumer Group`**. A topic allows multiple **`Consumer Group`** reading.
- Each consumer winthin a group read from exclusive **`partions`**.
- If there're more **`consumers`** than **`partition`**, some **`consumers`** will be inactive.

##### Consumer Offset
- Kafka stores the offsets at which a consumer group has been reading.
- The offsets commited are in Kafka topic named **`__consumer_offsets`**
- When a consumer in a group has processed data received from Kafka, it should be **`periodically`** committing the offsets. So that, if a consumer dies, it will be able to read back from where it left off thanks to the committed consumer offsets.

#### Broker
- Kafka cluster is composed of multiple **`Brokers`**.
- Each **`Broker`** is identified with its id (integer).
- Each **`Broker`** contains certain topic **`Partitions`**.
- After connecting to any **`Broker`**, we will be connected to the cluster.
- **`Topic partition data`** is distributed to **`Brokers`**.
- Each **`Broker`** knows all about brokers, topics and partitions (metadata).
- **`Topic`** should have a **`replication factor`**. This way if a broker is dow, another broker can serve data. Therefore, each **`Partition`** has only one `Leader` and multiple ISR (in-sync replica).
- **`Producers`** can only send data to the `Leader` partition in topic.

