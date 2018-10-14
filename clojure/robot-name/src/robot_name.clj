(ns robot-name)

(defn gen-robot-name []
  (str
    (apply str (take 2 (repeatedly #(char (+ 65 (rand-int 26))))))
    (apply str (take 3 (repeatedly #(rand-int 10))))))

(defn robot [] ;; <- arglist goes here
  ;; your code goes here
  )

(defn robot-name [robot] ;; <- arglist goes here
      ;; your code goes here
      )

(defn reset-name [robot] ;; <- arglist goes here
  ;; your code goes here
  )
