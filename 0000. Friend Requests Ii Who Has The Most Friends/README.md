<h2><a href="https://leetcode.com/problems/friend-requests-ii-who-has-the-most-friends">0000. Friend Requests Ii Who Has The Most Friends</a></h2>

<p>Table: <code>RequestAccepted</code></p>

<pre>+----------------+---------+
| Column Name    | Type    |
+----------------+---------+
| requester_id   | int     |
| accepter_id    | int     |
| accept_date    | date    |
+----------------+---------+
(requester_id, accepter_id) is the primary key (combination of columns with unique values) for this table.
This table contains the ID of the user who sent the request, the ID of the user who received the request, and the date when the request was accepted.
</pre>

<p>&nbsp;</p>

<p>Write a solution to find the people who have the most friends and the most friends number.</p>

<p>The test cases are generated so that only one person has the most friends.</p>

<p>The result format is in the following example.</p>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>

<pre><strong>Input:</strong> 
RequestAccepted table:
+--------------+-------------+-------------+
| requester_id | accepter_id | accept_date |
+--------------+-------------+-------------+
| 1            | 2           | 2016/06/03  |
| 1            | 3           | 2016/06/08  |
| 2            | 3           | 2016/06/08  |
| 3            | 4           | 2016/06/09  |
+--------------+-------------+-------------+
<strong>Output:</strong> 
+----+-----+
| id | num |
+----+-----+
| 3  | 3   |
+----+-----+
<strong>Explanation:</strong> 
The person with id 3 is a friend of people 1, 2, and 4, so he has three friends in total, which is the most number than any others.
</pre>

<p>&nbsp;</p>
<p><strong>Follow up:</strong> In the real world, multiple people could have the same most number of friends. Could you find all these people in this case?</p>


---

# 🛍️ Friend-Requests-Ii-Who-Has-The-Most-Friends | Explained

## Approach 1: Union All with Aggregation and Limiting
### Intuition
Think of a friendship as a handshake. If Alice shakes hands with Bob, both Alice and Bob have participated in a handshake. In the `RequestAccepted` table, each row represents a handshake where one person initiated (`requester_id`) and the other accepted (`accepter_id`). 

To find out who has the most friends, we must count how many handshakes each person participated in. This means we must look at both the `requester_id` and the `accepter_id` columns together. If we stack both columns on top of each other into a single column of IDs, we can simply count how many times each unique ID appears.

### Algorithm Visualized
```mermaid
graph TD
    subgraph Step 1: Input Table (RequestAccepted)
        R1[requester_id: 1, accepter_id: 2]
        R2[requester_id: 1, accepter_id: 3]
        R3[requester_id: 2, accepter_id: 3]
    end

    subgraph Step 2: UNION ALL (Stack columns into 'temp')
        U1[id: 2]
        U2[id: 3]
        U3[id: 3]
        U4[id: 1]
        U5[id: 1]
        U6[id: 2]
    end

    subgraph Step 3: GROUP BY & COUNT
        G1[id: 1 -> Count: 2]
        G2[id: 2 -> Count: 2]
        G3[id: 3 -> Count: 2]
    end

    subgraph Step 4: ORDER BY DESC & LIMIT 1
        Winner[id: 1, num: 2]
    end

    R1 -->|Extract Accepter| U1
    R2 -->|Extract Accepter| U2
    R3 -->|Extract Accepter| U3
    R1 -->|Extract Requester| U4
    R2 -->|Extract Requester| U5
    R3 -->|Extract Requester| U6

    U1 & U2 & U3 & U4 & U5 & U6 --> G1 & G2 & G3
    G1 & G2 & G3 --> Winner
```

### Approach
1. **Combine the IDs**: Use a `UNION ALL` operation to combine the `accepter_id` and `requester_id` into a single column named `id`. We must use `UNION ALL` instead of `UNION` because `UNION` automatically removes duplicate rows. In this problem, duplicates are exactly what we want to count (every duplicate appearance represents an additional friendship).
2. **Derived Table**: Treat the combined results as a temporary derived table called `temp`.
3. **Aggregate**: Group the rows in `temp` by the `id` column.
4. **Count**: Count the occurrences of each `id` using `COUNT(id)` (aliased as `num`).
5. **Sort and Limit**: Order the grouped results by `num` in descending order (`DESC`) and use `LIMIT 1` to retrieve only the record of the user with the highest count.

### Detailed Code Analysis
- **Lines 4-5**: 
  ```sql
  select accepter_id as id
  from RequestAccepted
  ```
  Extracts all users who *accepted* a friend request and aliases the column to `id`.
- **Line 6**: 
  ```sql
  union all
  ```
  Combines the result of the query above with the query below without discarding duplicate records.
- **Lines 7-8**: 
  ```sql
  select requester_id as id
  from RequestAccepted
  ```
  Extracts all users who *sent* a friend request and aliases the column to `id`.
- **Line 9**: 
  ```sql
  ) temp
  ```
  Wraps the combined rows into an alias dataset named `temp`.
- **Lines 2, 10-11**: 
  ```sql
  select id,count(id)as num
  ...
  group by id
  order by num desc limit 1;
  ```
  Groups the combined dataset by each unique `id`. `count(id)` tallies how many times each ID appears. The results are ordered from the highest number of friends to the lowest. `limit 1` slices the first row containing the user with the maximum friendships.

### Code
```sql
# Write your MySQL query statement below
select id, count(id) as num
from (
    select accepter_id as id
    from RequestAccepted
    union all
    select requester_id as id
    from RequestAccepted
) temp
group by id
order by num desc 
limit 1;
```

### Complexity
- **Time Complexity:** $\mathcal{O}(N \log N)$ where $N$ is the number of rows in the `RequestAccepted` table. 
  - Combining the datasets using `UNION ALL` takes linear time $\mathcal{O}(N)$.
  - Grouping and counting takes $\mathcal{O}(N)$ using a hash-based aggregation.
  - Sorting the aggregated records to find the maximum takes $\mathcal{O}(U \log U)$ time, where $U$ is the number of unique user IDs. In the worst-case scenario where everyone has at least one friend, $U \approx N$.
- **Space Complexity:** $\mathcal{O}(N)$ to store the intermediate rows of the unioned subquery `temp` in-memory prior to sorting and grouping.

---

## 🕵️‍♂️ Follow-up Questions

### 1. What if there is a tie for the most friends, and we want to return all of them?
The current solution uses `LIMIT 1`, which arbitrary returns only one user if multiple users share the maximum number of friends. 

To handle ties and return all users who have the maximum number of friends, we can use a **Window Function** like `DENSE_RANK()` or compare against a subquery representing the `MAX` count:

```sql
WITH FriendCounts AS (
    SELECT id, COUNT(id) AS num
    FROM (
        SELECT accepter_id AS id FROM RequestAccepted
        UNION ALL
        SELECT requester_id AS id FROM RequestAccepted
    ) temp
    GROUP BY id
),
RankedFriends AS (
    SELECT id, num,
           DENSE_RANK() OVER (ORDER BY num DESC) AS rnk
    FROM FriendCounts
)
SELECT id, num
FROM RankedFriends
WHERE rnk = 1;
```

### 2. Is there an alternative way to write this query without a `UNION`?
Yes, but it is typically more verbose. You would have to left-join or full-outer-join aggregation tables of requesters and accepters. The `UNION ALL` approach is the industry standard for this pattern because it avoids complex join conditions on non-unique keys and scales gracefully with simple database engine optimizers.