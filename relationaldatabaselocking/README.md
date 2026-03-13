### Description
This project provides hands-on experience implementing optimistic locking and pessimistic locking in a relational database.

### Knowledge
###### Pessimistic locking:
- Pessimistic locking prevents conflicts by locking the data before it is modified so other transactions cannot access it simultaneously.

###### Optimistic locking:
- Optimistic locking assumes conflicts are rare and detects them during the update using a version check.

### Use case - Booking Room
- <b>Without locking</b>, when a user creates a booking, the system checks whether the room is available and then inserts a new record into the booking table while updating the room status in the room table. If two users try to book the same room at the same time, both requests may see the room as available and create bookings simultaneously, which can lead to double booking.
