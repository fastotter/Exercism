(ns all-your-base)


(defn powers-of
  "Given a `base` return a lazy sequence of powers-of-base numbers.
  Returns nil when `base` < 2.

  Example:
  ```
  (take 10 (powers-of 2))
  ;;=> (1 2 4 8 16 32 64 128 256 512)
  ```"
  [base]
  (when (> base 1)
    (iterate (partial * base) 1)))


(defn base-10
  "Given `base` and a collection of `digits` return a base 10 number.

  Example:
  ```
  (base-10 2 '(1 0 1 1))
  ;;=> 11
  ```"
  [base digits]
  (apply + (map * (reverse digits) (powers-of base))))


(defn base-valid? [base] (> base 1))


(defn convert [from-base digits to-base]

  ;; validate inputs
  (if (and (base-valid? from-base) (base-valid? to-base)
           (every? #(<= 0 % (dec from-base)) digits))

    (let [base-10-value (base-10 from-base digits)
          result (loop [q base-10-value
                        d '()]
                   (if (zero? q)
                     d
                     (recur (quot q to-base) (conj d (rem q to-base)))))]
      (if (and (empty? result) (seq digits)) ;; happens when all digits are 0
        '(0)
        result))

    nil)) ;; one or more inputs invalid, return nil
