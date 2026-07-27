<h2><a href="https://leetcode.com/problems/investments-in-2016">585. Investments in 2016</a></h2>

<p>Table: <code>Insurance</code></p>

<pre>+-------------+-------+
| Column Name | Type  |
+-------------+-------+
| pid         | int   |
| tiv_2015    | float |
| tiv_2016    | float |
| lat         | float |
| lon         | float |
+-------------+-------+
pid is the primary key (column with unique values) for this table.
Each row of this table contains information about one policy where:
pid is the policyholder's policy ID.
tiv_2015 is the total investment value in 2015 and tiv_2016 is the total investment value in 2016.
lat is the latitude of the policy holder's city. It's guaranteed that lat is not NULL.
lon is the longitude of the policy holder's city. It's guaranteed that lon is not NULL.
</pre>

<p>&nbsp;</p>

<p>Write a solution to report the sum of all total investment values in 2016 <code>tiv_2016</code>, for all policyholders who:</p>

<ul>
	<li>have the same <code>tiv_2015</code> value as one or more other policyholders, and</li>
	<li>are not located in the same city as any other policyholder (i.e., the (<code>lat, lon</code>) attribute pairs must be unique).</li>
</ul>

<p>Round <code>tiv_2016</code> to <strong>two decimal places</strong>.</p>

<p>The&nbsp;result format is in the following example.</p>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>

<pre><strong>Input:</strong> 
Insurance table:
+-----+----------+----------+-----+-----+
| pid | tiv_2015 | tiv_2016 | lat | lon |
+-----+----------+----------+-----+-----+
| 1   | 10       | 5        | 10  | 10  |
| 2   | 20       | 20       | 20  | 20  |
| 3   | 10       | 30       | 20  | 20  |
| 4   | 10       | 40       | 40  | 40  |
+-----+----------+----------+-----+-----+
<strong>Output:</strong> 
+----------+
| tiv_2016 |
+----------+
| 45.00    |
+----------+
<strong>Explanation:</strong> 
The first record in the table, like the last record, meets both of the two criteria.
The tiv_2015 value 10 is the same as the third and fourth records, and its location is unique.

The second record does not meet any of the two criteria. Its tiv_2015 is not like any other policyholders and its location is the same as the third record, which makes the third record fail, too.
So, the result is the sum of tiv_2016 of the first and last record, which is 45.
</pre>


---

# 🛍️ Investments-in-2016 | Explained

## Approach 1: Subqueries with Aggregate Group Filtering (`HAVING` Clause & Tuple Matching)

### Intuition
Imagine you are an auditor analyzing policyholder data for an insurance company. You need to calculate the sum of 2016 investment values (`tiv_2016`), but only for policyholders who meet two strict rules:

1. **Shared Investment Profile:** The policyholder shares their 2015 investment amount (`tiv_2015`) with at least one other person. (They are not alone in their 2015 investment tier).
2. **Unique Geographic Location:** The policyholder's physical location—defined by the pair `(lat, lon)`—is completely unique across the entire dataset. (No two policyholders reside at the exact same coordinates).

To solve this, we can independently extract the list of non-unique `tiv_2015` values and the list of unique `(lat, lon)` pairs using `GROUP BY` and `HAVING` filters. Then, we filter the original dataset against these two subquery sets simultaneously and sum up the qualifying `tiv_2016` values.

### Algorithm Visualized

```mermaid
flowchart TD
    A[Insurance Table] --> B[Subquery 1: Group by tiv_2015]
    A --> C[Subquery 2: Group by lat, lon]
    
    B -->|HAVING count > 1| D[List of Shared tiv_2015 Values]
    C -->|HAVING count = 1| E[List of Unique lat, lon Pairs]
    
    A --> F{Filter Main Rows}
    D -->|tiv_2015 IN list| F
    E -->|(lat, lon) IN list| F
    
    F -->|Matching Rows| G[SUM tiv_2016]
    G --> H[ROUND to 2 Decimals]
    H --> I[Final Result: tiv_2016]
```

### Approach
1. **Identify Shared 2015 Investments:** 
   Group the `Insurance` table by `tiv_2015` and filter for groups where `COUNT(*) > 1`. This returns all `tiv_2015` values shared by multiple policyholders.
2. **Identify Unique Locations:** 
   Group the `Insurance` table by `(lat, lon)` tuple pairs and filter for groups where `COUNT(*) = 1`. This returns all geographic locations occupied by exactly one policyholder.
3. **Filter and Aggregate:** 
   Select rows from `Insurance` where the row's `tiv_2015` is present in the shared investment set **AND** the row's `(lat, lon)` combination is present in the unique location set.
4. **Format Output:** 
   Sum the filtered `tiv_2016` values and round the final result to 2 decimal places using `ROUND(..., 2)`.

### Detailed Code Analysis

```sql
SELECT ROUND(SUM(tiv_2016), 2) AS tiv_2016
FROM Insurance 
WHERE tiv_2015 IN (
    SELECT tiv_2015 
    FROM Insurance 
    GROUP BY tiv_2015
    HAVING COUNT(*) > 1
) 
AND (lat, lon) IN (
    SELECT lat, lon 
    FROM Insurance 
    GROUP BY lat, lon
    HAVING COUNT(*) = 1
);
```

* **Line 1: `SELECT ROUND(SUM(tiv_2016), 2) AS tiv_2016`**
  Computes the sum of `tiv_2016` for all rows passing the `WHERE` criteria. `ROUND(..., 2)` ensures the output strictly matches currency/floating-point format requirements (2 decimal places).
* **Lines 4–8: Subquery 1 (`tiv_2015 IN (...)`)**
  * `GROUP BY tiv_2015`: Clusters rows with identical 2015 investment amounts together.
  * `HAVING COUNT(*) > 1`: Eliminates single-occurrence investment values, keeping only values held by 2 or more policyholders.
* **Lines 10–14: Subquery 2 (`(lat, lon) IN (...)`)**
  * `GROUP BY lat, lon`: Clusters rows sharing identical coordinate pairs.
  * `HAVING COUNT(*) = 1`: Eliminates duplicate locations, keeping only coordinates associated with a single policyholder.
  * `(lat, lon) IN (...)`: Uses MySQL's native tuple-comparison capability to check multi-column matches against the materialised result set.

### Code

```sql
SELECT ROUND(SUM(tiv_2016), 2) AS tiv_2016
FROM Insurance 
WHERE tiv_2015 IN (
    SELECT tiv_2015 
    FROM Insurance 
    GROUP BY tiv_2015
    HAVING COUNT(*) > 1
) 
AND (lat, lon) IN (
    SELECT lat, lon 
    FROM Insurance 
    GROUP BY lat, lon
    HAVING COUNT(*) = 1
);
```

### Complexity

- **Time Complexity:** $\mathcal{O}(N \log N)$
  - Executing the subqueries requires grouping rows by `tiv_2015` and `(lat, lon)`. Database engines perform this using hashing or sorting ($\mathcal{O}(N \log N)$ or $\mathcal{O}(N)$).
  - Evaluating the `IN` predicates for $N$ rows against materialized lookup tables takes $\mathcal{O}(1)$ average time per row.
  - Overall time complexity is dominated by the aggregation and sorting operations: $\mathcal{O}(N \log N)$.
- **Space Complexity:** $\mathcal{O}(N)$
  - The subqueries create temporary in-memory hash tables / materialized result sets to hold intermediate group counts for `tiv_2015` and `(lat, lon)`. In the worst case, these take space proportional to the number of rows $N$.

---

## 🕵️‍♂️ Follow-up Questions (Optional)

### 1. How would you optimize this query using SQL Window Functions to avoid multiple subqueries?
**Answer:** We can compute the partition counts inline using window functions inside a Common Table Expression (CTE) or derived table. This avoids evaluating multiple separate subqueries and allows the database engine to scan the primary table in a single unified pass:

```sql
WITH AnalyzedInsurance AS (
    SELECT 
        tiv_2016,
        COUNT(*) OVER(PARTITION BY tiv_2015) AS tiv_2015_cnt,
        COUNT(*) OVER(PARTITION BY lat, lon) AS location_cnt
    FROM Insurance
)
SELECT ROUND(SUM(tiv_2016), 2) AS tiv_2016
FROM AnalyzedInsurance
WHERE tiv_2015_cnt > 1 
  AND location_cnt = 1;
```

### 2. What are the indexing implications of the tuple `(lat, lon) IN (...)` construct?
**Answer:** Tuple `IN` clauses can sometimes prevent MySQL's optimizer from efficiently utilizing range scans on composite indexes. To maximize indexing performance on large datasets, creating a composite index on `(lat, lon)` or `(tiv_2015)` allows the query engine to satisfy the `GROUP BY` operations via index scans rather than full table scans and temporary tables.