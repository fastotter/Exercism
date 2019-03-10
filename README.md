# Exercism

Just a place to store Exercism programs as I learn Clojure.

#### anagram
- Uses ```keep``` to filter a sequence to another sequence of items we want to
keep/isolate

#### fib
- Uses ```lazy-seq``` to create a lazy sequence of the Fibonacci Sequence.
- This is NOT an Exercism program.

#### isbn-verifier
- Uses ```map``` to convert a collection of ISBN digit characters to a collection of integers
and uses ```map``` to multiple each number in collection to by a number in another
collection, e.g.,
```
(\1 \4 \8 \X) => (1 4 8 10)
(map * '(1 2 3) '(2 3 4)) => (2 6 12)
```
- Uses ```reduce``` to sum numbers in a collection.
- Uses ```str/join, str/split and re-pattern``` to remove all '-' characters from a string.

#### raindrops
- Uses ``` filer zero? rem range``` and ```inc``` to convert a number to its factors (in a series)
- Uses a map and ```map``` to convert specific numbers in a series to words

#### run-length-encoding
- Uses ```loop recur``` to transform strings, e.g.,
```
(encode "aaabcdde") => "3abc2de"
(decode "5xy3z") => "xxxxxyzzz"
```

#### say
Converts number to text, e.g.,
```
(number 101000202000)
;;=> "one hundred one billion two hundred two thousand"
```
- Uses ```throw``` to throw an exception when number out of range
- Uses ```loop recur``` several times
- Uses ```interleave``` to merge two sequences of strings
- Uses ```partition``` to group sequence of strings into pairs
- Uses ```flatten``` to flatten nested pairs of strings into a sequence of strings

#### series
Given a string of digits, output all the contiguous substrings of length n in that string in the order that they appear.
```
(slices "marcus" 4)
;;=> ["marc" "arcu" "rcus"]
```
- Uses ```loop recur``` to iterate across the string
- Uses ```subs``` to get a substring out of the string

#### word-count
- Uses ```re-seq``` to create sequence of words from one string.
- Uses ```lower-case``` to change mixed case string to all-lower-case string.
- Uses a map to count number of objects (in this case strings)
- Uses ```loop recur``` to create the map (^)
