(ns leap)

(defn- is-divisible-by? [num divisor]
  (= (mod num divisor) 0))

(defn leap-year? [year]
  (or (is-divisible-by? year 400)
      (and (is-divisible-by? year 4) (not (is-divisible-by? year 100)))))
