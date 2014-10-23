(ns cljs-memory-game.card
  (:require-macros [dommy.macros :refer [sel]])
  (:require [dommy.core :as dommy]))

(def cards
  {"1" :a "2" :b "3" :c "4" :b "5" :a "6" :c})

(defn get-id [card]
  (dommy/attr card :id))

(defn get-val [card]
  (cards (get-id card)))

(defn match? [card1 card2]
  (= (get-val card1) (get-val card2)))

(defn can-select? [card]
  (not
    (or (dommy/has-class? card :selected)
        (dommy/has-class? card :matched)
        (> (count (sel :.selected)) 1))))

(defn check-match [card1 card2]
  (if (match? card1 card2)
    (doall (map #(dommy/add-class! % :matched)
                [card1 card2]))))

(defn reset-all! []
  (doall (map #(dommy/set-html! % "")
              (sel :.selected)))
  (doall (map #(dommy/remove-class! % :selected)
              (sel :.selected))))
