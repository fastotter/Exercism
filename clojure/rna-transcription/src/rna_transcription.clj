(ns rna-transcription
  (:require [clojure.string :as str]))

(defn to-rna [dna]
  (let [rna-char-seq (map {\A \U \C \G \T \A \G \C} dna)]
    (if (some nil? rna-char-seq) (assert false) (str/join rna-char-seq))))
