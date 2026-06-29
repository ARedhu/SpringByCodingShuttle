package com.codingShuttle.jpaTutorials.jpaTuts.repositories;

import com.codingShuttle.jpaTutorials.jpaTuts.entities.PatientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientRepository extends JpaRepository<PatientEntity, Long> {

/*
    N+1 Query Problem

    When we fetch a list of parent entities (e.g., Doctors) and later access their child entities (e.g., Appointments), Hibernate may execute:

            • 1 query to fetch all parent entities.
            • N additional queries to fetch the child entities for each parent.

    This results in a total of 1 + N database queries.

            Example:
    Suppose we fetch all doctors and then access each doctor's appointments.

    Query 1:
    SELECT * FROM doctors;

    Query 2:
    SELECT * FROM appointments WHERE doctor_id = 1;

    Query 3:
    SELECT * FROM appointments WHERE doctor_id = 2;

...

    Query N+1:
    SELECT * FROM appointments WHERE doctor_id = N;

    Problem:
    The issue is not that Hibernate fetches the child data. The issue is that it executes a separate database query for each parent entity, resulting in many unnecessary database round trips.

            Optimization:
    Instead of executing N separate queries, Hibernate can fetch the required data using techniques such as JOIN FETCH, @EntityGraph, or Batch Fetching, reducing the number of database queries.

    One sentence I'd especially recommend remembering for interviews:
    The N+1 problem is one query to fetch the parent entities and one additional query for each parent entity to fetch its associated children.

*/



    // Way-1: JOIN FETCH
    // We write our own JPQL
    @Query("SELECT DISTINCT p from PatientEntity p JOIN FETCH p.appointmentEntityList")
    List<PatientEntity> findAllPatientsWithAppointments();


/*    # Why do we use DISTINCT with JOIN FETCH?
    When we use `JOIN FETCH` on a `@OneToMany` relationship, the generated SQL performs a JOIN between the parent and child tables.
    Since SQL returns one row for every parent-child combination, the same parent entity appears multiple times if it has multiple child entities.

### Example
    Suppose we have:
    Patient:
        * Ashish
        * Rahul

    Appointments:
            * Ashish → Fever
            * Ashish → Cold
            * Ashish → Headache
            * Rahul → Flu

    JPQL:
    @Query("""
        SELECT p
        FROM PatientEntity p
        JOIN FETCH p.appointmentEntityList
""")
```

    Generated SQL (simplified):
            ```sql
    SELECT p.*, a.*
    FROM patients p
    JOIN appointments a
    ON p.id = a.patient_id;
```

    SQL Result:
            | Patient | Appointment |
            | ------- | ----------- |
            | Ashish  | Fever       |
            | Ashish  | Cold        |
            | Ashish  | Headache    |
            | Rahul   | Flu         |

    Notice that **Ashish appears three times** because he has three appointments.
    Without `DISTINCT`, the returned list may contain duplicate references to the same `PatientEntity`.

            ```java
[
    Patient(Ashish),
    Patient(Ashish),
    Patient(Ashish),
    Patient(Rahul)
]
        ```

    To remove these duplicate parent entities, we use:

            ```java
    @Query("""
SELECT DISTINCT p
FROM PatientEntity p
JOIN FETCH p.appointmentEntityList
""")
```

    Now the result becomes:

            ```java
[
    Patient(Ashish),
    Patient(Rahul)
]
        ```

    Each patient's `appointmentEntityList` still contains **all** of its appointments.

            ### Important Points

* `DISTINCT` removes duplicate **parent entities**, not child entities.
* It does **not** remove any appointments from the collection.
            * It is commonly used with `JOIN FETCH` on `@OneToMany` and `@ManyToMany` relationships.
* It is usually **not required** for `@ManyToOne` or `@OneToOne` fetch joins because those relationships do not produce duplicate parent rows.

### Interview Definition

`DISTINCT` is used with `JOIN FETCH` to ensure that each parent entity appears only once in the result list, even though the SQL JOIN returns multiple rows for parents having multiple child entities.

 */

    // -----------------------------------------------------------

/*
    How are Child Entities Accessed after using JOIN FETCH?
    JOIN FETCH loads both the parent and its associated child entities in a single database query.

    Hibernate creates the following objects:

    Ashish
   ├── Fever
   ├── Cold
   └── Headache

    Rahul
   └── Flu
    All the appointments are already stored inside each PatientEntity.

    Therefore, when we access: patient.getAppointmentEntityList();
    Hibernate does not execute another SQL query.
    Instead, it simply returns the already-loaded collection from the Persistence Context.
 */

    // -----------------------------------------------------------

/*
    Question: Then why not to use  @OneToMany(fetch = FetchType.EAGER)
    Ans: Because the child entities are fetched every time the parent is loaded, whether you need them or not. You cannot easily control it for different use cases.
    While in case of JOIN FETCH Child entities are fetched only for that specific query.

 */
}