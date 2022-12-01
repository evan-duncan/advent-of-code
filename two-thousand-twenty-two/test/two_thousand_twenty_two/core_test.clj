(ns two-thousand-twenty-two.core-test
  (:require [clojure.test :refer :all]
            [two-thousand-twenty-two.core :refer [read-resource]]))

(deftest reading-resource-files
  (testing "It reads a resource file"
    (is (= "Hello, world!\n" (read-resource "test/data.txt")))))
