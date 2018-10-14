(ns beer-song)

(def str2 " bottles of beer on the wall, ")
(def str5 " bottles of beer.\n")
(def str1 "Take one down and pass it around, ")
(def str4 "Take it down and pass it around, ")
(def str3 " bottle of beer on the wall, ")

(defn verse
 "Returns the nth verse of the song."
 [num]
 (cond
   (> num 2) (str num str2 num str5
              str1 (- num 1) " bottles of beer on the wall.\n")
   (= num 2) (str num str2 num str5
              str1 (- num 1) " bottle of beer on the wall.\n")
   (= num 1) (str num str3 num " bottle of beer.\n"
              str4 "no more bottles of beer on the wall.\n")
   :else     (str
               "No more bottles of beer on the wall, no more bottles of beer.\n"
               "Go to the store and buy some more, 99 bottles of beer on the wall.\n")
   ))


(defn build-song [chorus-set]
  "returns a song composed of chorus-set"
  (loop [[r & more :as all] (seq chorus-set)
    song ""]
    (if all
      (do
        (println "r=" r " more=" more " all=" all)
        (recur more (str song "\n" (verse r))))
        (clojure.string/triml song))))

(defn sing
  "Given a start and an optional end, returns all verses in this interval. If
  end is not given, the whole song from start is sung."
  ([start] (sing start 0))
  ([start end] (build-song (reverse (range end (inc start))))))
