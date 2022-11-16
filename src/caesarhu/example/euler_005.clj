(ns caesarhu.example.euler-005
  (:require [caesarhu.kuafu.domain :as d]
            [caesarhu.kuafu.sat.model :as m]
            [caesarhu.kuafu.sat.solver :as s]))

(defn euler-005
  [n]
  (let [model (m/sat-model)
        zero (m/int-var model 0)
        x (m/int-var model 2 (reduce * (range 2 (inc n))) "x")
        solver (s/sat-solver)]
    (doseq [i (range 2 (inc n))]
      (m/add-modulo-equality model zero x i))
    (m/minimize model x)
    (s/solve solver model)
    (s/value solver x)))

(comment
  (time (euler-005 20))
  )