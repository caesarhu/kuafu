(ns caesarhu.kuafu
  (:require [caesarhu.kuafu.cp-sat :as sat]))

(defn foo
  "I don't do a whole lot."
  [x]
  (prn x "Hello, World!"))

(defn sat-sample
  []
  (let [model (sat/new-model)
        x (.newIntVar model 0 2 "x")
        y (.newIntVar model 0 2 "y")
        z (.newIntVar model 0 2 "z")]
    (.addDifferent model x y) 
    (sat/solutions model [x y z])))

(comment
  (sat-sample)
  )
