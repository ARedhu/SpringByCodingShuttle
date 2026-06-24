package com.codingShuttle.jpaTutorials.jpaTuts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.converter.json.GsonBuilderUtils;

@SpringBootApplication
public class JpaTutorialsApplication {

    public static void main(String[] args) {
        SpringApplication.run(JpaTutorialsApplication.class, args);
        System.out.println("Hello Ashish...");
    }

}



// Notes:
/*
Hibernate Entity Lifecycle is the sequence of states an entity goes through (Transient, Persistent, Detached, and Removed) while interacting with the database through a Hibernate Session.





The 4 Entity States
Hibernate tracks entities in one of four distinct states:

Transient: * What it is: A brand-new Java object you just created using new.
DB Status: The database has no idea it exists (no primary key/ID, no row).
Session Status: Not associated with any Hibernate Session.
Persistent (Managed):
What it is: An object actively tracked by Hibernate. Any changes you make to this object's fields will automatically be saved to the database when the transaction commits (via "dirty checking").
DB Status: It has a matching row in the database.
Session Status: Tied to an active Session.
        Detached:
What it is: An object that used to be persistent, but its session was closed or cleared. Changes made to it are not automatically saved to the database anymore.
DB Status: It still has a matching row in the database.
Session Status: No longer tied to an active Session.
        Removed:
What it is: An object explicitly marked for deletion.
DB Status: The row will be deleted from the database when the transaction commits.
Session Status: Still tied to the session until the commit happens.

        State Transitions (The Cheat Sheet)
How an entity moves from one state to another depends on the method you call:

Transient → Persistent → save() / persist()
Persistent → Detached → close() / clear() / evict()
Detached → Persistent → merge() / update()
Persistent → Removed → remove() / delete()
Database → Persistent → find() / get()

Key Takeaway to Remember: > If an object is Persistent, Hibernate is watching it. Modify a setter, and Hibernate handles the SQL UPDATE for you. If it's Transient or Detached, you are on your own until you explicitly pass it back to the session.




        1. Dirty Checking ⭐ (Very Important)
This is one of the most asked Hibernate interview concepts.

Dirty Checking: Hibernate automatically detects changes made to a Persistent entity and executes an UPDATE query when the transaction is committed or the session is flushed.


Product p = session.find(Product.class, 1L); // Persistent
p.setPrice(500); // No update query here

// On commit/flush
// Hibernate automatically executes:
// UPDATE product SET price = 500 WHERE id = 1;
Remember:

Works only for Persistent entities.
No need to call update() after changing a Persistent object.




        2. flush() vs commit()
Many beginners confuse these.

Method        |                               What it does
flush()      |  Synchronizes the Session with the database (executes SQL) but does not commit the transaction.
commit()     |  Flushes the Session (if needed) and permanently commits the transaction.

Easy memory trick:
flush() = Send SQL
commit() = Save permanently




Understand flush() vs commit()


The "Restaurant" Analogy 🍔
Think of Hibernate like a waiter, the Database like the kitchen, and flush() vs. commit() like this:

Making changes (Dirty Checking): You change a value using a setter (user.setName("Ashish");). The waiter notes it down on their pad but hasn't gone to the kitchen yet.
        session.flush() (Sending to Kitchen): The waiter walks over and hangs the ticket in the kitchen. The kitchen starts cooking (the database executes the UPDATE or INSERT SQL queries). However, the food isn't on your table yet, and you can still cancel the order.
        transaction.commit() (Serving the Food): The food is finalized and served to you. The database permanently locks the data in, and other users can now see it.
        What flush() actually does:
Executes SQL immediately: Normally, Hibernate holds onto SQL queries and batches them at the very end to save time. Calling flush() forces Hibernate to execute those INSERT, UPDATE, or DELETE statements right that second.
Database memory vs. Disk: The database runs the queries in its own temporary transaction memory. It knows about the changes, but they are not permanent yet.
No Commit: If something goes wrong after a flush, you can still roll back the transaction, and the database will act like nothing ever happened.


        Code:

// Assume we have an existing User entity with ID 1, Name = "Rahul"
EntityManager em = entityManagerFactory.createEntityManager();
EntityTransaction tx = em.getTransaction();

try {
        tx.begin(); // Step 1: Start the transaction

// Step 2: Fetch the entity (It is now in the PERSISTENT state)
User user = em.find(User.class, 1L);

// Step 3: Change a property (In-memory change only)
    user.setName("Ashish");
// At this point, NO SQL has been sent to the DB yet.

    System.out.println("--- Before Flush ---");

// Step 4: Manually trigger flush()
    em.flush();

    System.out.println("--- After Flush / Before Commit ---");
// SQL 'UPDATE users SET name = 'Ashish' WHERE id = 1' has now EXECUTED.
// The DB knows about the change, but it is uncommitted.
// If you check the DB from another application right now, you still see "Rahul".

// Step 5: Commit the transaction
    tx.commit();
    System.out.println("--- After Commit ---");
// The data is now permanently saved.
// Everyone can now see "Ashish" in the database.

} catch (Exception e) {
        if (tx.isActive()) {
        tx.rollback(); // If an error happened after flush, this wipes it clean
    }
            } finally {
            em.close();
}




        3. persist() vs save()
You already mentioned both methods, but knowing the difference is useful.

persist()                                 |            save()
JPA standard                               |      Hibernate-specific
Returns void                               |      Returns generated ID
Preferred in modern Spring Boot/JPA        |       Older Hibernate method
Recommendation: Prefer persist() in JPA applications.





        4. merge() vs update() ⭐⭐⭐
This is another common interview question.

merge()                                     |                   update()
Safe                                        |      Can throw exception if another instance with the same ID already exists in the Session
Returns the managed entity                  |      Returns void
Copies state into a managed entity          |      Reattaches the same object
Preferred                                   |       Less commonly used nowadays
Recommendation: In modern applications, prefer merge().


// If an entity is already in the Persistent state,
// Hibernate returns it from the Session (first-level cache)
// instead of querying the database again.


When an entity is Persistent, Hibernate first checks the Session (first-level cache) before querying the database.

Case 1: Fetching the same entity again (No DB call) ✅
Code:
Product p1 = session.find(Product.class, 1L); // DB call
Product p2 = session.find(Product.class, 1L); // No DB call

Here, the second find() returns the entity from the Session cache because it is already in the Persistent state.



 */


