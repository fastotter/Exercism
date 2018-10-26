(ns meetup
  (:import (java.util Calendar GregorianCalendar)))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; utility functions needed by function meetup
(defn inc-day [cal]
  "increment by a day the given Java GregorianCalendar"
  (.add cal Calendar/DAY_OF_MONTH 1) cal)

(defn day-key [day-num]
  "given Java Calendar DAY_OF_WEEK (1..7), return day-of-week key (e.g., :sunday)"
  (nth [:notday :sunday :monday :tuesday :wednesday :thursday :friday :saturday]
    day-num))

(defn gen-month-day-keys [year month]
  "Given year and month (0..11) generate a calender for the month
   that looks like [[1 :sunday] [2 :monday] [3 :tuesday]...]"
  (loop [month-cal []
         cal (GregorianCalendar. year month 1)
         date 1]
    (if (not= (.get cal Calendar/MONTH) month) month-cal
      (recur (conj month-cal (vector date (day-key (.get cal Calendar/DAY_OF_WEEK))))
             (inc-day cal)
             (inc date)))))

(defn filter-cal-by-weekday [year month weekday-key]
  "Given year, month (0..11) and weekday-key (e.g., :thursday) return a
  vector of just those days. E.g.,

  user=>(filter-cal-by-weekday 2012 0 :wednesday)
  [[4 :wednesday] [11 :wednesday] [18 :wednesday] [25 :wednesday]]"
  (vec (filter #(= (second %) weekday-key) (gen-month-day-keys year month))))

(defn find-date [year month weekday spec]
  "Given year, month (0..11), weekday (e.g., :tuesday) and spec (e.g., :second),
  return the day-of-month."
  (let [filter-cal-vec (filter-cal-by-weekday year month weekday)]
    (case spec
      :first  (first (nth filter-cal-vec 0))
      :second (first (nth filter-cal-vec 1))
      :third  (first (nth filter-cal-vec 2))
      :fourth (first (nth filter-cal-vec 3))
      :fifth  (first (nth filter-cal-vec 4))
      :teenth (first (first (filter #(and (> (first %) 12) (< (first %) 20)) filter-cal-vec)))
      :last   (first (last filter-cal-vec)))))
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn meetup [month year weekday spec]
  "Given month (1..12), year, weekday (e.g., :sunday) and spec (e.g., :teenth),
  return vector of form [yyyy mm dd]."
  (vector year month (find-date year (dec month) weekday spec)))

;; Resources:
;;   https://www.mkyong.com/java/java-date-and-calendar-examples/
