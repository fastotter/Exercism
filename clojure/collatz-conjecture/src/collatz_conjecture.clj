(ns collatz-conjecture)

(defn collatz [num]
  (if (< num 1) (throw (Exception. "input must be > 0"))
    (loop
      [cnt     0
       new-num num]
       (cond
         (= new-num 1) cnt
         (even? new-num) (recur (inc cnt) (/ new-num 2))
         (odd?  new-num) (recur (inc cnt) (+ 1 (* new-num 3)))
         :else cnt))))
