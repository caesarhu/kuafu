(ns caesarhu.example.euler-001
  (:require [caesarhu.kuafu.sat.solver :refer :all]
            [caesarhu.kuafu.sat.model :refer :all]
            [caesarhu.kuafu.domain :refer [from-values]])
  (:import [com.google.ortools.sat CpModel CpSolver CpSolverSolutionCallback]))

(defn euler-001
  [n]
  (let [model (sat-model)
        zero (int-var model 0)
        x (int-var model 1 (dec n) "x")
        y (int-var model (from-values [3 5]) "y")
        solver (sat-solver)
        solutions (atom [])
        callback (fn [vars] 
                   (proxy [CpSolverSolutionCallback] []
                              (onSolutionCallback []
                                (let [solution (mapv #(value this %) vars)]
                                  (swap! solutions conj solution)))))]
    (add-modulo-equality model zero x y)
    (solve solver model (callback [x]))
    @solutions))

(comment
  (time (euler-001 1000))
  )