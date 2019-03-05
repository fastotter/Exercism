(ns flatten-array)

(defn flatten [arr]
  (remove nil? (clojure.core/flatten arr)))
