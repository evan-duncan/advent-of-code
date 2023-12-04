(ns two-thousand-twenty-two.day-eight
  (:require [two-thousand-twenty-two.core :refer [with-resource integers<-strings]]
            [clojure.string :refer [split-lines split]]))

(defn visible?
  "Check adjacent nodes to see if a given node is visible.
   A node is visible if the left, right, up, and down nodes
   are less than the given node"
  [node edges]
  (some? (some true? (map #(< % node) edges))))

(defn adjacent-nodes
  "Get the adjacent nodes left, right, up, and down
   for the given node. Give the node's index and row index."
  [node node-index row-index matrix]
  (let [left (get (get matrix row-index []) (- 1 node-index) -1)
        right (get (get matrix row-index []) (+ 1 node-index) -1)
        up (get (get matrix (- 1 row-index) []) node-index -1)
        down (get (get matrix (+ 1 row-index) []) node-index -1)]
    [left right up down]))

(with-resource [data "day_eight.txt"]
  (let [lines (split-lines data)
        matrix (->> lines (map #(split % #"")) (map integers<-strings))]
    (for [[row-index row] (map-indexed vector matrix)]
      (for [[col-index col] (map-indexed vector row)
            :when (visible? col (adjacent-nodes col col-index row-index matrix))]
        col))))
