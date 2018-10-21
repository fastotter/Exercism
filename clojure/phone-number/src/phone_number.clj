(ns phone-number)

(defn str->digits [string]
  (let [matcher (re-matcher #"\d+" string)]
    (loop [match (re-find matcher)
           digits ""]
      (if-not match digits
        (recur (re-find matcher) (str digits match))))))

(def bad-number (hash-map :country-code "0",
          :area-code "000",
          :exchange-code "000",
          :subscriber-code "0000"))

(defn number->map [num-string]
  (let [num (str->digits num-string)]
    (case (count num)
      10 (hash-map :country-code "1",
                   :area-code (subs num 0 3),
                   :exchange-code (subs num 3 6),
                   :subscriber-code (subs num 6 10))
      11 (hash-map :country-code (subs num 0 1),
                   :area-code (subs num 1 4),
                   :exchange-code (subs num 4 7),
                   :subscriber-code (subs num 7 11))
      bad-number)))

(defn valid-number? [number]
  (and (= "1" (number :country-code))
       (< 1 (Integer. (subs (number :area-code) 0 1)))
       (< 1 (Integer. (subs (number :exchange-code) 0 1)))
  ))
(defn number->str [number]
  (str (number :area-code) (number :exchange-code) (number :subscriber-code)))

(defn number [num-string]
  (let [number (number->map num-string)]
    (if (valid-number? number) (number->str number)
      (number->str bad-number))))

(defn area-code [num-string]
  (let [number (number->map num-string)]
    (number :area-code)))

(defn pretty-print [num-string]
  (let [num (number->map num-string)]
    (str "(" (num :area-code) ") " (num :exchange-code) "-" (num :subscriber-code))))
