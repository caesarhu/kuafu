(ns caesarhu.example.euler-004
  (:require [caesarhu.kuafu.cp-sat :as sat])
  (:import [com.google.ortools.sat CpModel IntVar CpSolverSolutionCallback LinearExpr]
           [com.google.ortools.util Domain]))

(defn palindrome
  []
  (let [model (sat/new-model)
        a (.newIntVar model 1 9 "a")
        b (.newIntVar model 0 9 "b")
        c (.newIntVar model 0 9 "c")
        p (.newIntVar model 900000 999999 "c")
        x (.newIntVar model 100 999 "x")
        y (.newIntVar model 100 999 "y")]
    (.addEquality model p (LinearExpr/weightedSum (into-array [a b c c b a]) (long-array [100000 10000 1000 100 10 1])))
    (.maximize model p)
    (.addMultiplicationEquality model p x y)
    (->> (sat/solve model [p])
         :values)))

(comment
  (time (palindrome))
  )