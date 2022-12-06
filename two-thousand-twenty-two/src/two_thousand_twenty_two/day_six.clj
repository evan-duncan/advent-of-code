(ns two-thousand-twenty-two.day-six
  (:require [two-thousand-twenty-two.core :refer [with-resource]]
            [clojure.string :refer [index-of]]))

(defn bom-finder [n]
  (fn [s]
    (->> (partition n 1 s)
         (filter #(apply distinct? %))
         first
         (apply str))))

(def bom (bom-finder 4))
(def mom (bom-finder 14))

(with-resource [data "day_six.txt"]
  (+ 4  (index-of data (bom data))))

(with-resource [data "day_six.txt"]
  (+ 14 (index-of data (mom data))))
