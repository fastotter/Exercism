(ns say
  (:require [clojure.string :as str]))

(def numbers
  ["zero" "one" "two" "three" "four" "five" "six" "seven" "eight" "nine"
   "ten" "eleven" "twelve" "thirteen" "fourteen" "fifteen" "sixteen" "seventeen" "eighteen" "nineteen"
   "twenty" "twenty-one" "twenty-two" "twenty-three" "twenty-four" "twenty-five" "twenty-six" "twenty-seven" "twenty-eight" "twenty-nine"
   "thirty" "thirty-one" "thirty-two" "thirty-three" "thirty-four" "thirty-five" "thirty-six" "thirty-seven" "thirty-eight" "thirty-nine"
   "forty" "forty-one" "forty-two" "forty-three" "forty-four" "forty-five" "forty-six" "forty-seven" "forty-eight" "forty-nine"
   "fifty" "fifty-one" "fifty-two" "fifty-three" "fifty-four" "fifty-five" "fifty-six" "fifty-seven" "fifty-eight" "fifty-nine"
   "sixty" "sixty-one" "sixty-two" "sixty-three" "sixty-four" "sixty-five" "sixty-six" "sixty-seven" "sixty-eight" "sixty-nine"
   "seventy" "seventy-one" "seventy-two" "seventy-three" "seventy-four" "seventy-five" "seventy-six" "seventy-seven" "seventy-eight" "seventy-nine"
   "eighty" "eighty-one" "eighty-two" "eighty-three" "eighty-four" "eighty-five" "eighty-six" "eighty-seven" "eighty-eight" "eighty-nine"
   "ninety" "ninety-one" "ninety-two" "ninety-three" "ninety-four" "ninety-five" "ninety-six" "ninety-seven" "ninety-eight" "ninety-nine"])

(def units ["ones" "thousand" "million" "billion"])

(defn num-split
  "Splits a number into a series of 3-digit numbers (e.g.,
   (num-split 101202303) => (101 202 303))."
  [num]
  (loop [number num
         nums []]
    (if (< number 1000)
      (concat [number] nums)
      (let [chip (mod number 1000)
            rem (quot number 1000)]
        (recur rem (concat [chip] nums))))))

(defn num->text
  "Given a number in the range [0..999], return a string (e.g.,
  (num->str 456) => 'four hundred fifty-six')."
  [num]
  (if (or (< num 0) (> num 999))
    (throw (IllegalArgumentException. "number outside of legal range [0..999]"))
    (if (= 0 num) "zero"
      (let [hun (quot num 100)
            rem (mod num 100)]
        (str
          (if (not= 0 hun) (str (get numbers hun) " hundred"))
          (if (and (not= 0 hun) (not= 0 rem)) " ")
          (if (not= 0 rem) (get numbers rem)))))))

(defn split-num-and-convert-to-text
  "Given a number, split it into 3-digit chunks and convert each chunk to text
   (e.g., (split-num-and-convert-to-test 101202)
       => ('one hundred one' 'two hundred two'))."
  [num]
  (map num->text (num-split num)))

(defn split-num-convert-to-text-interleave-units
  "Given a number, split it into 3-digit chunks, convert each chunk to text,
   and interleave the chunks with unit qualifiers. (e.g.,
   (sncttiu 101202303) =>
    ['one hundred one' 'million' 'two hundred two' 'thousand' 'three hundred three' 'ones'])"
  [num]
  (into []
    (reverse
      (interleave
        (seq units)
        (reverse (split-num-and-convert-to-text num))))))

(defn rm_zero_chunks
  "Iterate across collection of strings. When 'zero' found, remove it and
  the next string.
  Thus
    [('one' 'million') ('zero' 'thousand') ('twenty-three' 'ones')]
  is transformed to
    [('one' 'million') ('twenty-three' 'ones')]"
  [nums]
  (loop
    [src nums
     out []]
    (if (empty? src) out
      (recur (rest src) (if
                          (not= "zero" (first (first src)))
                          (conj out (first src))
                          out)))))

(defn number [num]
  (if (or (< num 0) (> num 999999999999))
    (throw (IllegalArgumentException. "number outside of legal range [0..999,999,999,999]"))
    (if (== num 0)
        "zero"
        (str/join " "
          (remove #{"ones"}
            (flatten
              (rm_zero_chunks
                (into [] (partition 2 (split-num-convert-to-text-interleave-units num))))))))))
