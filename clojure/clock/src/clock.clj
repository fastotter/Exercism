(ns clock)

(defn clock->string [clock]
  (format "%02d:%02d" (quot clock 60) (mod clock 60))
)

(def minutes-per-day (* 60 24))

(defn clock [hours minutes]
  (mod (+ (* hours 60) minutes) minutes-per-day)
)

(defn add-time [clock time]
  (mod (+ clock time) minutes-per-day)
)
