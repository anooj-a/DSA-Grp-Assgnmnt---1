# CIT300 Data Structures and Algortihms (2C)
## CIT300 – Graded Practical Assignment 1

### Byte Builders

### University Student Record and Campus Route Management System

# 1. Group Members

#    -----------------------------------------------------------------------------------------------------------------------------
#   |       Name        |  Student ID  | Assigned Responsibility                                        | Individual Contribution
#   |-------------------|--------------|----------------------------------------------------------------|-------------------------
    | MS. Anooj Ahamed  | 23DA2 - 0963 | Linked list & student-record management, Documentation         | Leader & Video Editing 
    | MN. Musab Ahamed  | 23DA2 - 0531 | Stack & queue implementation, Graph, campus locations, BFS/DFS | Guidance & Verification
    | MN. Afraj Ahamath | 23DA2 - 0545 | BST & hashing/search functionality                             | Video Editing
    | MAM. Asfak        | 23DA2 - 0987 | Main.java & Intergration, Testing                              | Supervision
    
All members:  validation, testing, debugging, GitHub collaboration.

## 2. How to Compile and Run

From the `src` folder:

```bash
javac Main.java
java Main
```

Requires Java 8 or later (no external libraries used).

## 3. Project Structure

```
CIT300_Assignment1/
├── README.md
└── src/
    ├── Student.java            # Student record model
    ├── StudentLinkedList.java  # Custom singly linked list (Requirement 2, 12)
    ├── ActionStack.java        # Custom array-based stack (Requirement 3)
    ├── ServiceQueue.java       # Custom linked queue (Requirement 4)
    ├── StudentBST.java         # Binary search tree by Student ID (Requirement 5)
    ├── HashTable.java          # Custom separate-chaining hash table (Requirement 6)
    ├── CampusGraph.java        # Adjacency-list graph + BFS/DFS (Requirement 7–11)
    └── Main.java               # Menu-driven console interface (Requirement 13–14)
```

## 4. Requirement Coverage

# |   #  |                         Requirement                                        | Where implemented               |
# |------|--------------------------------------------------------------------------- |---------------------------------|
  | 1    | Store Student ID, Name, Programme, Marks                                   | `Student.java`                  |
  | 2    | Linked list to store/manage records                                        | `StudentLinkedList.java`        |
  | 3    | Stack for recent actions/history                                           | `ActionStack.java`              |
  | 4    | Queue for service requests                                                 | `ServiceQueue.java`             |
  | 5    | BST to organize/search by Student ID                                       | `StudentBST.java`               |
  | 6    | Hashing for fast ID search                                                 | `HashTable.java`                |
  | 7–11 | Graph of campus locations,                                                 |                                 |
         | adjacency list, add/remove, display, BFS/DFS                               | `CampusGraph.java`              |
  | 12   | Add/update/delete/search/display students                                  | `Main.java` menu options 1–4, 9 |
  | 13   | Menu-driven interface with input validation                                | `Main.java`                     |
  | 14   | Handles invalid input, duplicate IDs/locations,                            |                                 |
  |      | missing records, invalid marks, unavailable connections                    | Validated throughout `Main.java`| 
  |      |                                                                            | and the structure               |

## 5. Design Notes

- All core data structures (linked list, stack, queue, BST, hash table) are implemented **from scratch** rather than relying on `java.util` equivalents, to demonstrate the concepts required by the module. The graph uses `java.util` collections (`ArrayDeque`, `LinkedHashMap`) only as internal containers for the adjacency list and BFS queue, which is standard practice for graph implementations.
- The linked list, BST, and hash table are kept in sync on every add/update/delete so that Display (linked list), Display (BST), and Search (hashing) always reflect the same data.
- Marks are validated to fall within 0–100; IDs and locations are checked for duplicates before insertion; deletions and connection removals check for existence first and report a clear error otherwise.