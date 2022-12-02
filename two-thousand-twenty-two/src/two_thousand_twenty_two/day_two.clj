(ns two-thousand-twenty-two.day-two
  (:require [two-thousand-twenty-two.core :refer [read-resource]]
            [clojure.core.match :refer [match]]))

;; part one
(def moves {\A 'rock
            \B 'paper
            \C 'scissors
            \X 'rock
            \Y 'paper
            \Z 'scissors})

(def scores {'rock 1
             'paper 2
             'scissors 3})

(def turns
  (->> (read-resource "day_two.txt")
       (char-array)
       (seq)
       (remove #{\newline \space})
       (map #(get moves %))
       (partition 2)))

(defn winner? [a b]
  (match [a b]
    ['rock 'scissors] true
    ['paper 'rock] true
    ['scissors 'paper] true
    [_ _] nil))

(defn find-winner [a b]
  (match [(winner? a b) (winner? b a)]
    [true nil] a
    [nil true] b
    [_ _] nil))

(defn get-score [[theirs mine]]
  (let [winner (find-winner theirs mine)
        draw?  (= theirs mine)
        i-won? (= winner mine)
        their-score (+ (get scores theirs) (if draw? 3 (if i-won? 0 6)))
        my-score    (+ (get scores mine) (if draw? 3 (if i-won? 6 0)))]
    [their-score my-score]))

(->> turns
     (map get-score)
     (apply map +))

;; part two

(def moves {\A 'rock
            \B 'paper
            \C 'scissors
            \X 'lose
            \Y 'draw
            \Z 'win})

(defn beat [choice]
  (get {'rock 'paper
        'paper 'scissors
        'scissors 'rock}
       choice))

(defn concede [choice]
  (get {'rock 'scissors
        'paper 'rock
        'scissors 'paper}
       choice))

(defn pick-move [opponent decision]
  (match [decision]
    ['draw] opponent
    ['win] (beat opponent)
    ['lose] (concede opponent)))

(defn get-score [[theirs mine]]
  (let [choice (pick-move theirs mine)
        winner (find-winner theirs choice)
        draw?  (= theirs choice)
        i-won? (= winner choice)
        their-score (+ (get scores theirs) (if draw? 3 (if i-won? 0 6)))
        my-score    (+ (get scores choice) (if draw? 3 (if i-won? 6 0)))]
    [their-score my-score]))

(def turns
  (->> (read-resource "day_two.txt")
       (char-array)
       (seq)
       (remove #{\newline \space})
       (map #(get moves %))
       (partition 2)))

(->> turns
     (map get-score)
     (apply map +))
