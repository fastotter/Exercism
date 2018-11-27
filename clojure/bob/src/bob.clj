(ns bob
  (:require [clojure.string :as str]))

(def ^{:private true} bob-response-map
  { :statement { :normal "Whatever." :shouted "Whoa, chill out!"}
    :question  { :normal "Sure."     :shouted "Calm down, I know what I'm doing!"}
    :silence   { :normal "Fine. Be that way!"}})

(defn- all-uppercase?
  "returns true if all letters are uppercase.
   return false if there is a least one lowercase letter or no letters"
  [s]
  (let
    [upper (not (empty? (re-seq #"[A-Z]+" s)))
     lower (not (empty? (re-seq #"[a-z]+" s)))]
     (and upper (not lower))))

(defn- comm-level
  "returns :shouted or :normal based on input string"
  [s]
  (cond
    (all-uppercase? s) :shouted
    :else :normal))

(defn- comm-type
  "returns :silence, :question, or :statement based on input string"
  [s]
  (let [trimmed-str (str/trim s)]
    (cond
      (= (count trimmed-str) 0) :silence
      (str/ends-with? trimmed-str "?") :question
      :else :statement)))

(defn response-for [s]
  (get (bob-response-map (comm-type s)) (comm-level s)))
