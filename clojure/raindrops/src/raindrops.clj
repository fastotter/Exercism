(ns raindrops
  (:require [clojure.string :as s]))

(def ^:private dict {3 "Pling" 5 "Plang" 7 "Plong"})

(defn convertnum
  [num]
  (get dict num))

(defn factors
  [num]
  (filter #(zero? (rem num %)) (range 1 (inc num))))

(defn convert
  [num]
  (let
    [rain (apply str (remove nil? (map #(convertnum %) (factors num))))]
    (if (s/blank? rain) (str num) rain)))
