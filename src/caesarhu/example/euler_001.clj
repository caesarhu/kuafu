(ns caesarhu.example.euler-001
  (:require [caesarhu.kuafu.sat :as sat]
            [caesarhu.kuafu.domain :as d]
            [caesarhu.kuafu.sat.model :as m]
            [caesarhu.kuafu.sat.solver :as s])
  (:import [com.google.ortools.sat CpSolver CpModel IntVar CpSolverSolutionCallback LinearExpr]
           [com.google.ortools.util Domain]))

(defn euler-001
  [n]
  (let [model (m/sat-model)
        zero (m/int-var model 0)
        x (m/int-var model 1 (dec n) "x")
        y (m/int-var model (d/from-values [3 5]) "y")
        values (atom (list))
        solver (s/sat-solver)]
    (.addModuloEquality model zero x y)
    (s/set-all-solutions solver true)
    (s/solve solver model (s/callback values [x]))
    @values))

(comment
  (time (euler-001 1000))
  )