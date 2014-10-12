(ns cljs-memory-game.core
  (:require-macros [cljs.core.async.macros :refer [go]]
                   [dommy.macros :refer [node sel sel1]])
  (:require [dommy.utils :as utils]
            [dommy.core :as dommy]
            [cljs.core.async :refer [put! chan <! >!]]))

; channel to store card clicks on
(def card-clicks-chan (chan))

(def cards
  {"1" :a "2" :b "3" :c "4" :b "5" :a "6" :c})

(defn get-id [card]
  (dommy/attr card :id))

(defn get-card-val [card]
  (cards (get-id card)))

(defn can-select-card? [card]
  (not
    (or (dommy/has-class? card :selected)
        (dommy/has-class? card :matched)
        (> (count (sel :.selected)) 1))))


(defn card-click [event]
  (let [card (.-currentTarget event)]
    (when (can-select-card? card)
      (dommy/add-class! card :selected)
      (dommy/append! card (name (get-card-val card)))
      (put! card-clicks-chan card))))

(defn reset-cards! []
  (doall (map #(dommy/set-html! % "")
              (sel :.selected)))
  (doall (map #(dommy/remove-class! % :selected)
              (sel :.selected))))


(defn match? [card1 card2]
  (= (get-card-val card1) (get-card-val card2)))

(defn check-match [card1 card2]
  (if (match? card1 card2)
    (doall (map #(dommy/add-class! % :matched)
                [card1 card2]))))

(defn init []
  (doseq [element (sel :.card)]
    (dommy/listen! element :click card-click))
  (go (while true
    (let [card1 (<! card-clicks-chan)
          card2 (<! card-clicks-chan)]
      (check-match card1 card2)
      (js/setTimeout reset-cards! 1000)))))

(init)
