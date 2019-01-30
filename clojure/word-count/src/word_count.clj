(ns word-count)

(defn- extract-words
  [s]
  (re-seq #"\b\w+\b" s))

(defn- lower [s] (clojure.string/lower-case s))

(defn- inc-count [s m]
  (let [count (get m s)]
    (if count
      (assoc m s (inc count))
      (assoc m s 1))))

(defn word-count [s]
  (loop
    [word-seq (extract-words s)
     word-map {}]
    (if (empty? word-seq)
      word-map
      (recur
        (rest word-seq)
        (inc-count (lower (first word-seq)) word-map)))))
