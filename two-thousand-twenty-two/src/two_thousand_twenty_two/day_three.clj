(ns two-thousand-twenty-two.day-three
  (:require [two-thousand-twenty-two.core :refer [read-resource]]
            [clojure.string :refer [split-lines split]]
            [clojure.set :refer [intersection]]))

(def puzzle-input (read-resource "day_three.txt"))

(defn char-range [start end]
  (map char (range (int start) (inc (int end)))))

(def points
  (let [small (apply merge (map hash-map (char-range \a \z) (iterate inc 1)))
        large (apply merge (map hash-map (char-range \A \Z) (iterate inc 27)))]
    (merge large small)))

;; part 1
(let [rucksacks (->> puzzle-input split-lines (map #(partition (/ (count %) 2) %)))
      duplicates (map (fn [[c1 c2]] (intersection (set c1) (set c2))) rucksacks)]
  (->> duplicates (map seq) flatten (map points) (apply +)))

;; part 2
(->> puzzle-input
     split-lines
     (partition 3)
     (map (fn [[ruck1 ruck2 ruck3]] (intersection (set ruck1) (set ruck2) (set ruck3))))
     (map seq)
     flatten
     (map points)
     (apply +))
