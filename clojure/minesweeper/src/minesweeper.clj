(ns minesweeper
  "Given a 2-D minesweeper board with mines (or no mines) update the board with
  numbers in cells that represent the number of adjacent cells that have mines.

  The implementation uses a vector (a 1-D array) to represent the 2-D board/array.
  The indexes used to access cells are zero based.

  So a 3x3 minesweeper board would have these indexes.

  [0][1][3]
  [4][5][6]
  [7][8][9]"

  (:require [clojure.string :as str]
            [util :refer :all]))

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
  "Given the dimensions of a two-dimension array calculate the indexes for
  the adjacent cells for every cell in the 2D array. For each adjacent
  cell a single integer index is created because this namespace uses a vector
  (i.e., a 1-D array) to represent the 2-D minesweeper board.

  Example:
  Input: row-cnt: 2, col-cnt: 2
  Output: [ [1 2 3] [0 2 3] [0 1 3] [0 1 2] ]
  "
  [row-cnt col-cnt]
  (-> (map #(adjacent-tiles row-cnt col-cnt %) (range (* row-cnt col-cnt)))
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

(defn add-numbers [{:keys [col-cnt row-cnt board indexes] :as board-map}]
  (let [limit (* col-cnt row-cnt)]
    (loop [idx 0
           brd board]
      (if (= idx limit)
        (assoc board-map :board brd)
        (recur (inc idx)
               (count-adj-mines idx brd (nth indexes idx)))))))

(defn input [board]
  (let [str (str/split-lines board)
        col-cnt (count (first str))
        row-cnt (count str)
        brd (vec (replace {" " 0} (to-array (flatten (map #(str/split % #"") str)))))
        adjacent-indexes (gen-ati col-cnt row-cnt)]
    {:col-cnt col-cnt :row-cnt row-cnt :board brd :indexes adjacent-indexes}))

(defn output [{:keys [col-cnt board]}]
  (if (= board [""])
    ""
    (->>
     (partition col-cnt board)
     (interpose line-separator)
     flatten
     (replace {0 " "})
     str/join)))

(defn draw [board]
  (-> board
      input
      add-numbers
      output))
