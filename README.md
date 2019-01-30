# Exercism

Just a place to store Exercism programs as I learn Clojure.

### isbn-verifier
- Uses map to convert a collection of ISBN digit characters to a collection of integers
and uses map to multiple each number in collection to by a number in another
collection, e.g.,
```
(\1 \4 \8 \X) => (1 4 8 10)
(map * '(1 2 3) '(2 3 4)) => (2 6 12)
```
- Uses reduce to sum numbers in a collection.
- Uses str/join, str/split and re-pattern to remove all '-' characters from a string.


### run-length-encoding
- Uses loop recur to transform strings, e.g.,
```
(encode "aaabcdde") => "3abc2de"
(decode "5xy3z") => "xxxxxyzzz"
```

### word-count
- Uses re-seq to create sequence of words from one string.
- Uses lower-case to change mixed case string to all-lower-case string.
- Uses a map to count number of objects (in this case strings)
- Uses loop recur to create the map (^)
