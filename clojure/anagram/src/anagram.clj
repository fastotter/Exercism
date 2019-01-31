(ns anagram
  (:require [clojure.string :as str]))

(defn anagram? [word prospect]
  (let
    [lc-word (str/lower-case word)
     lc-prospect (str/lower-case prospect)]
    (if (= lc-word lc-prospect)
      false
      (if (= (sort (seq lc-word)) (sort (seq lc-prospect)))
        true
        false))))

(defn anagrams-for [word prospect-list]
  (keep #(if (anagram? word %) %) prospect-list))
