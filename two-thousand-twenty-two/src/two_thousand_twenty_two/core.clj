(ns two-thousand-twenty-two.core
  (:require [clojure.java.io :refer [resource]]))

(defn read-resource [file]
  (slurp (resource file)))
