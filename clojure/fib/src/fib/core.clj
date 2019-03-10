(ns fib.core)

(defn fib-seq
  ([]
   (fib-seq 0 1))
  ([a b]
   (lazy-seq (cons a (fib-seq b (+ a b))))))

(defn fib
  [n]
  (take (inc n) (fib-seq)))
