(ns caesarhu.kuafu
  (:require [caesarhu.kuafu.cp-sat :as sat])
  (:import [com.google.ortools Loader]
           [com.google.ortools.linearsolver MPSolver]
           [com.google.ortools.sat CpModel IntVar CpSolverSolutionCallback LinearExpr]))

(Loader/loadNativeLibraries)

(defn sat-sample
  []
  (let [model (sat/new-model)
        x (.newBoolVar model "x")
        y (.newBoolVar model "y")
        z (.newBoolVar model "z")]
    (.addExactlyOne model [x y z]) 
    (sat/solutions model [x y z])))

(def solver (. MPSolver createSolver "GLOP"))

(comment
  (sat-sample)
  )
