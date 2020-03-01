(ns minesweeper
  (:require [clojure.string :as str]
            [util]))

(def line-separator (System/getProperty "line.separator"))

(defn up-inbounds? [idx cols] (>= idx cols))
(defn left-inbounds? [idx cols] (not= (rem idx cols) 0))
(defn right-inbounds? [idx cols] (not= (rem (inc idx) cols) 0))
(defn down-inbounds? [idx rows cols] (< idx (* (dec rows) cols)))

(defn adjacent-tiles
  [rows cols idx]
  (let [up? (up-inbounds? idx cols)
        left? (left-inbounds? idx cols)
        right? (right-inbounds? idx cols)
        down? (down-inbounds? idx rows cols)]
    (cond-> []
      (and up? left?) (conj (- idx cols 1)) ; up-left
      up? (conj (- idx cols)) ; up
      (and up? right?) (conj (inc (- idx cols))) ; up-right
      right? (conj (inc idx)) ; right
      (and right? down?) (conj (+ idx cols 1)) ; right-down
      down? (conj (+ idx cols)) ; down
      (and left? down?) (conj (+ idx cols -1)) ; down-left
      left? (conj (dec idx)) ; left
      )))

(defn gen-ati
  [rows cols]
  (-> (map #(adjacent-tiles rows cols %) (range (* rows cols)))
      vec))

(def MINE "*")

(defn count-adj-mines
  "Counts the number of mines around a single cell identified by parameter idx.
  And it puts the count in the cell and returns the board.
  If the cell is a mine, there's nothing to do, just return the board as-is."
  [idx ;; an integer in range 0 to (count brd)
   brd ;; e.g., [0 0 "*" 0 0 0 0 0], where "*" is a mine and the rest are integers rep mine cnt
   adj-indexes] ;; e.g., [1 4 3], the indexes of the cells adjacent to idx
  (if (= MINE (nth brd idx))
    brd
    (loop
        [adj-idx 0
         adj-mine-count 0]
      (if (= adj-idx (count adj-indexes))
        (assoc brd idx adj-mine-count)
        (recur (inc adj-idx)
               (if (= MINE (nth brd (nth adj-indexes adj-idx)))
                 (inc adj-mine-count)
                 adj-mine-count))))))

(defn add-numbers [{:keys [row-len row-cnt board indexes] :as board-map}]
  (let [limit (* row-len row-cnt)]
    (loop [idx 0
           brd board]
      (if (= idx limit)
        (assoc board-map :board brd)
        (recur (inc idx)
               (count-adj-mines idx brd (nth indexes idx)))))))

(defn input [board]
  (let [str (str/split-lines board)
        row-len (count (first str))
        row-cnt (count str)
        brd (vec (replace {" " 0} (to-array (flatten (map #(str/split % #"") str)))))
        adjacent-indexes (gen-ati row-len row-cnt)]
    {:row-len row-len :row-cnt row-cnt :board brd :indexes adjacent-indexes}))

(defn output [{:keys [row-len board]}]
  (if (= board [""])
    ""
    (let [grouped (partition row-len board)]
      (-> (reduce
           (fn [a b] (conj a (vec b) line-separator))
           [] grouped)
          flatten
          drop-last
          (#(replace {0 " "} %))
          vec
          str/join
          ))))

(defn draw [board] ;; <- arglist goes here
  (-> board
      input
      add-numbers
      output))
