(ns two-thousand-twenty-two.core
  (:require [clojure.java.io :refer [resource]]
            [clojure.walk :refer [postwalk]]))

(defn read-resource
  "Read a file from PROJECT_ROOT/resources"
  [file]
  (slurp (resource file)))

(defmacro with-resource [bindings & body]
  `(let [~(first bindings) (read-resource ~(last bindings))] ~@body))

(defn inclusive-range [start stop]
  (range start (inc stop)))

(defn char-range [start stop]
  (map char (inclusive-range (int start) (int stop))))

(defn integer<-string [s]
  (Integer/parseInt s))

(defn integers<-strings [coll]
  (postwalk #(if (string? %) (integer<-string %) %) coll))
