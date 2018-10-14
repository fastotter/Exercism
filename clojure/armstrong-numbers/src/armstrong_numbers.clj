(ns armstrong-numbers)


(defn exp [x n]
  (loop [acc 1 n n]
    (if (zero? n) acc
        (recur (* x acc) (dec n)))))


(defn arm-calc [num result len]
  (if (< num 10) (+ result (exp num len))
    (recur (quot num 10) (+ result (exp (mod num 10) len)) len)))


(defn armstrong? [num]
  (let [len (count (str num))]
    (= num (arm-calc num 0 len))))
