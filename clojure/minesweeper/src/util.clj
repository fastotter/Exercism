(ns util)

(defn ->prn
  "Threaded prn form for debugging convenience.
  Do (prn label x), and return x."
  [x label] (prn label x) x)

(defn ->>prn
  "Threaded prn form for debugging convenience.
  Do (prn label x), and return x."
  [label x] (prn label x) x)
