(ns two-thousand-twenty-two.day-five
  (:require [two-thousand-twenty-two.core :refer [with-resource read-resource integers<-strings]]
            [clojure.string :refer [split split-lines replace]]))

(defn rotate [stacks]
  (->> stacks
       reverse
       rest
       reverse
       (apply interleave)
       (partition (dec (count stacks)))))

(defn get-stacks [data]
  (->> (split-lines data)
       (map #(replace % #" {3}" "[?]"))
       (map #(replace % #" " ""))
       (map #(re-seq #"\[.\]" %))
       (map #(map (fn [i] (if (= i "[?]")
                            nil
                            i)) %))
       (map vec)))

(defn steps<-move [s]
  (map integers<-strings (re-seq #"\d+" s)))

(defn domove [stacks [ct from to]]
  (let [from-idx (dec from)
        to-idx (dec to)
        crates (take ct (take-nth from-idx stacks))]
    (println (str "from-index " from-idx))
    (println (str "to-index " to-idx))
    (println (str "count: " ct))
    (-> (map vec stacks)
        (update to-idx #(reduce conj % crates))
        (update from-idx #(take-last (- (count %) ct) %)))))

(defn domoves [stacks moves]
  (if (empty? moves)
    stacks
    (let [move (first moves)
          rest (rest moves)]
      (recur (domove stacks move) rest))))

(with-resource [data "day_five.txt"]
  (let [parts       (split data #"\n{2}")
        stacks      (map #(remove nil? %) (rotate (get-stacks (first parts))))
        steps       (map steps<-move (split-lines (last parts)))]
    (domoves (map vec stacks) steps)))
