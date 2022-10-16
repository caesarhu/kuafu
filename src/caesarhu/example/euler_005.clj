(ns caesarhu.example.euler-005
  (:require [caesarhu.kuafu.cp-sat :as sat])
  (:import [com.google.ortools.sat CpModel IntVar CpSolverSolutionCallback LinearExpr]
           [com.google.ortools.util Domain]))

(defn euler-005
  [n]
  (let [model (sat/new-model)
        zero (.newConstant model 0)
        x (.newIntVar model 2 (reduce * (range 2 (inc n))) "x")]
    (doseq [i (range 2 (inc n))]
      (.addModuloEquality model zero x i))
    (.minimize model x)
    (sat/solve model [x])))

(comment
  (time (euler-005 20))
  )