(ns two-thousand-twenty-two.day-four
  (:require [two-thousand-twenty-two.core :refer [with-resource inclusive-range]]
            [clojure.set :refer [intersection]]
            [clojure.string :refer [split split-lines replace]]))

(defn process [data]
  (->> (split (replace data #"[-,\n]" " ") #" ")
       (map #(Integer/parseInt %))
       (partition 2)
       (map #(apply inclusive-range %))
       (partition 2)))

(defn within? [first-range second-range]
  (and (<= (first first-range) (first second-range))
       (<= (last second-range) (last first-range))))

(defn either-within? [first-range second-range]
  (or (within? first-range second-range)
      (within? second-range first-range)))

(with-resource [data "day_four.txt"]
  ;; part one
  (->> (process data)
       (map #(apply either-within? %))
       (filter true?)
       count))

(with-resource [data "day_four.txt"]
  (->> (process data)
       (map #(map (fn [l] (set l)) %))
       (map #(apply intersection %))
       (map #(empty? %))
       (filter false?)
       count))
