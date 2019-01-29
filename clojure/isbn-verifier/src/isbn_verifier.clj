(ns isbn-verifier
  (:require [clojure.string :as str]))

(defn remove-char
  [string char]
  (str/join (str/split string (re-pattern char))))

(defn valid-isbn10-format?
  "Returns true if string is valid ISBN-10 format. Treats dashes as invalid
  ISBN format; remove dashes from ISBN string before testing with this
  function."
  [string]
  (if (first (re-seq #"^\d{9}[\dXx]{1}$" string)) true false))

(defn isbn-digit-to-int
  "Converts an ISBN character digit to int (e.g., \7 -> 7)."
  [c]
  (if (= c \X) 10 (- (int c) 48)))

(defn isbn? [isbn] ;; <- arglist goes here
  (let
    [isbn-without-dashes (remove-char isbn "-")]
    (if (valid-isbn10-format? isbn-without-dashes)
      (let
        [isbn-math-result (mod
                            (->>
                              (map * (map isbn-digit-to-int(seq isbn-without-dashes)) (reverse (range 1 11)))
                              (reduce +))
                            11)]
        (if (= 0 isbn-math-result) true false))
      false)))
