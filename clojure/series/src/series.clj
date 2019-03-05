(ns series)

(defn slices
  [string length]
  (if (= length 0) [""]
    (loop
      [start 0
       end length
       coll []]
      (if
        (> end (count string))
        coll
        (recur (inc start) (inc end) (conj coll (subs string start end)))))))
