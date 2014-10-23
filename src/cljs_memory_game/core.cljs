(ns cljs-memory-game.core
  (:require-macros [cljs.core.async.macros :refer [go]]
                   [dommy.macros :refer [sel]])
  (:require [dommy.core :as dommy]
            [cljs-memory-game.card :as card]
            [cljs.core.async :refer [put! chan <! >!]]))

(defn card-click [event clicks-chan]
  (let [card (.-currentTarget event)]
    (when (card/can-select? card)
      (dommy/add-class! card :selected)
      (dommy/append! card (name (card/get-val card)))
      (put! clicks-chan card))))

(defn init []
  (let [clicks-chan (chan)]
    (doseq [element (sel :.card)]
      (dommy/listen! element :click #(card-click % clicks-chan)))
    (go (while true
      (let [card1 (<! clicks-chan)
            card2 (<! clicks-chan)]
        (card/check-match card1 card2)
        (js/setTimeout card/reset-all! 1000))))))

(init)
