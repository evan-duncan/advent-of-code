(ns two-thousand-twenty-two.day-one
  (:require [two-thousand-twenty-two.core :refer [read-resource]]
            [clojure.string :refer [split split-lines]]))

(def puzzle-input (read-resource "day_one.txt"))

(defn calculate-calories-per-elf [input]
  (->> (split puzzle-input #"\n{2}")
       (map #(split-lines %))
       (map #(apply list %))
       (map #(map (fn [s] (Integer/parseInt s)) %))
       (map #(apply + %))))

;; Part one
;; Find the elf with the highest number of calories
(->> (calculate-calories-per-elf puzzle-input)
     (apply max))

;; Part two
;; Find the top three elves
(->> (calculate-calories-per-elf puzzle-input)
     (sort >)
     (take 3)
     (apply +))
