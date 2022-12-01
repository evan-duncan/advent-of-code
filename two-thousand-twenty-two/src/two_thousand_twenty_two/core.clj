(ns two-thousand-twenty-two.core
  (:require [clojure.java.io :refer [resource]]))

(defn read-resource
  "Read a file from PROJECT_ROOT/resources"
  [file]
  (slurp (resource file)))
