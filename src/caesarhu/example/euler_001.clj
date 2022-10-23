(ns caesarhu.example.euler-001
  (:require [caesarhu.kuafu.sat :as sat])
  (:import [com.google.ortools.sat CpModel IntVar CpSolverSolutionCallback LinearExpr]
           [com.google.ortools.util Domain]))

(defn euler-001
  [n]
  (let [model (sat/new-model)
        zero (.newConstant model 0)
        x (.newIntVar model 1 (dec n) "x")
        y (.newIntVarFromDomain model (Domain/fromValues (long-array [3 5])) "y")]
    (.addModuloEquality model zero x y)
    (->> (sat/solutions model [x])
         :values
         (apply concat)
         set
         (reduce +))))

(comment
  (time (euler-001 1000))
  )