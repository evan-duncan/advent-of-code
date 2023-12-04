(ns two-thousand-twenty-two.day-seven
  (:require [two-thousand-twenty-two.core :refer [with-resource integers<-strings]]
            [clojure.string :refer [split split-lines join trim replace]]))

(cond
  (re-matches #"cd\s+[\/ \w]+" "cd /")
  "foo"
  :else false)

(defn stdout<-command [s]
  (if-let [cmd (not-empty (first s))]
    (list cmd (rest s))
    nil))

(defn process-commands [s]
  (map stdout<-command (filter #(not= % '[""]) s)))

(defn change-directory? [s]
  (not (nil? (re-matches #"cd\s+[\/ \w \.]+" s))))

(defn assoc-in-tree [tree path]
  (assoc-in tree path '{}))

(defn tree-key [cwd command]
  (let [parts (split command #" ")
        cwd-parts (if (= "/" cwd)
                    (list "")
                    (split cwd #"\/"))]
    (concat cwd-parts (rest parts))))

(join "/" (tree-key "/" "cd gts"))

(defn file-system-tree<-commands [commands]
  (loop [tree '{}
         cwd "/"
         cmds commands]
    (if (empty? cmds)
      tree
      (let [command (first cmds)
            stm (first command)]
        (cond
          (change-directory? (first command))
          (recur (assoc-in-tree tree (tree-key cwd stm))
                 (join "/" (tree-key cwd stm))
                 (rest cmds))
          :else (recur tree cwd (rest cmds)))))))

(defn dir-size [cmd]
  (apply +
         (integers<-strings
          (remove empty? (-> (join " " cmd) (replace #"[a-z\.]" "") (split #" "))))))

(split "   1   3   4" #" ")

(with-resource [data "day_seven.txt"]
  (->> (split data #"\$ ")
       (map #(split % #"cd "))
       flatten
       (map split-lines)
       (remove #(= 1 (count %)))
       (map dir-size)
       (filter #(<= % 100000))
       (apply +)))
