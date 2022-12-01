(ns two-thousand-twenty-two.day-one-test
  (:require [two-thousand-twenty-two.day-one :refer [sum-top]]
            [clojure.test :refer :all]))

(deftest part-one
  (testing "Returns the highest number of calories for given input"
    (is (= 69281 (sum-top 1)))))

(deftest part-two
  (testing "Returns the sum of the calories for the top n elves"
    (is (= 201524 (sum-top 3)))))
