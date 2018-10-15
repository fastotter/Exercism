(ns robot-name)

(def names (atom #{}))

(defn save-name [name]
  (swap! names conj name))

(defn name-exists? [name]
  (contains? @names name))

(defn gen-robot-name []
  (str
    (apply str (take 2 (repeatedly #(char (+ 65 (rand-int 26))))))
    (apply str (take 3 (repeatedly #(rand-int 10))))))

(defn gen-unique-robot-name []
  (loop [name (gen-robot-name)]
    (if (name-exists? name) (recur gen-robot-name)
      name)))

(defn robot []
  (let [name (gen-unique-robot-name)]
    (save-name name)
    (hash-map "name" name)))

(defn robot-name [robot]
  (robot "name"))

(defn reset-name [robot]
  (let [name (gen-unique-robot-name)]
    (save-name name)
    (assoc robot "name" name)))
