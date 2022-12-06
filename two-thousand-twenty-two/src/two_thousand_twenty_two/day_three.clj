(ns two-thousand-twenty-two.day-three
  (:require [two-thousand-twenty-two.core :refer [read-resource char-range]]
            [clojure.string :refer [split-lines split]]
            [clojure.set :refer [intersection]]))

(def puzzle-input (split-lines (read-resource "day_three.txt")))

(def points
  (let [small (apply merge (map hash-map (char-range \a \z) (iterate inc 1)))
        large (apply merge (map hash-map (char-range \A \Z) (iterate inc 27)))]
    (merge large small)))

;; part 1
(->> puzzle-input
     (map #(partition (/ (count %) 2) %))
     (map (fn [[c1 c2]] (intersection (set c1) (set c2))))
     (map seq)
     flatten
     (map points)
     (apply +))

;; part 2
(->> puzzle-input
     (partition 3)
     (map (fn [[ruck1 ruck2 ruck3]] (intersection (set ruck1) (set ruck2) (set ruck3))))
     (map seq)
     flatten
     (map points)
     (apply +))
