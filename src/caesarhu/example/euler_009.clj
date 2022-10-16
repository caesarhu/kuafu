(ns caesarhu.example.euler-009
  (:require [caesarhu.kuafu.cp-sat :as sat])
  (:import [com.google.ortools.sat CpModel IntVar CpSolverSolutionCallback LinearExpr]
           [com.google.ortools.util Domain]))

(defn euler-009
  []
  (let [model (sat/new-model)
        target (.newConstant model 1000)
        a (.newIntVar model 1 1000 "a")
        b (.newIntVar model 1 1000 "b")
        c (.newIntVar model 1 1000 "c")
        a2 (.newIntVar model 1 1000000000 "a2")
        b2 (.newIntVar model 1 1000000000 "b2")
        c2 (.newIntVar model 1 1000000000 "c2")]
    (.addMultiplicationEquality model a2 a a)
    (.addMultiplicationEquality model b2 b b)
    (.addMultiplicationEquality model c2 c c)
    (.addEquality model c2 (LinearExpr/sum (into-array [a2 b2])))
    (.addEquality model target (LinearExpr/sum (into-array [a b c])))
    (->> (sat/solve model [a b c])
         :values
         (reduce *))))

(comment
  (time (euler-009))
  )