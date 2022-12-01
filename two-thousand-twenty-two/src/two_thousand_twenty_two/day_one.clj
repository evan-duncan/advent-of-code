(ns two-thousand-twenty-two.day-one
  (:require [two-thousand-twenty-two.core :refer [read-resource]]
            [clojure.string :refer [split split-lines]]
            [clojure.walk :refer [postwalk]]))

(defn- integer<-string [s]
  (Integer/parseInt s))

(defn- integers<-strings [coll]
  (postwalk #(if (string? %) (integer<-string %) %) coll))

(defn- partition-data [elves]
  (->> (split elves #"\n{2}")
       (map #(split-lines %))))

(defn- calculate-calories-per-elf [elves]
  (->> (partition-data elves)
       (integers<-strings)
       (map #(apply + %))))

(defn sum-top
  "Sum the top n elves' calories"
  [n]
  (->> (calculate-calories-per-elf (read-resource "day_one.txt"))
       (sort >)
       (take n)
       (apply +)))
