(ns run-length-encoding)

(defn run-length-encode
  "Encodes plain-text string with run-length-encoding."
  [plain-text]
  (if (empty? plain-text) ""
    (loop
      [ch     (first plain-text)
       cnt    1
       p-text (rest plain-text)
       e-text ""]
       (if (empty? p-text) (str e-text (if (= cnt 1) "" cnt) ch)
         (recur
           (first p-text)
           (if (= ch (first p-text)) (inc cnt) 1)
           (rest p-text)
           (if-not (= ch (first p-text)) (str e-text (if (= cnt 1) "" cnt) ch) e-text))))))

(defn- is-digit?
  "Returns true if character is a digit."
  [c]
  (<= (int \0) (int c) (int \9)))

(defn- digit-to-int
  "Converts a character digit to int (e.g., \7 -> 7)."
  [c]
  (- (int c) 48))

(defn- append-n-char-to-string
  "Creates string of repeating char length n and appends it to the given
  string."
  [string n char]
  (str string (apply str (repeat n char))))

(defn run-length-decode
  "Decodes a run-length-encoded string."
  [cipher-text]
  (if (empty? cipher-text) ""
    (loop
      [cnt         0
       t           (first cipher-text)
       c-text      (rest cipher-text)
       plain-text  ""]
       (if (empty? c-text) (append-n-char-to-string plain-text (max 1 cnt) t)
         (recur ;; t is a digit
           (if (is-digit? t) (+ (* cnt 10) (digit-to-int t)) 0) ;; cnt
           (first c-text)
           (rest c-text)
           (if (is-digit? t) plain-text (append-n-char-to-string plain-text (max 1 cnt) t)))))))
