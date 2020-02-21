(ns minesweeper
  (:require [clojure.string :as str]))

(defn input [board]
  (let [str (str/split-lines board)
        row-len (count (first str))
        brd (to-array (flatten (map #(str/split % #"") str)))]
    {:row-len row-len :board brd}))

(defn output [{:keys [row-len board]}]
  (reduce #(-> (subvec %2 0 )) '() board)
  )

(defn draw [board] ;; <- arglist goes here
  board
)
