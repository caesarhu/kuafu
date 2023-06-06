(ns caesarhu.examples.euler-005
  (:require [caesarhu.kuafu.ortools :refer [or-call]]
            [caesarhu.kuafu.sat :as sat]))

(defn euler-005
  [n]
  (let [model (sat/cp-model)
        zero (sat/int-var model 0)
        x (sat/int-var model 2 (reduce * (range 2 (inc n))) "x")
        solver (sat/cp-solver)]
    (doseq [i (range 2 (inc n))]
      (or-call model addModuloEquality zero x i))
    (or-call model minimize x)
    (.solve solver model)
    (.value solver x)))

(comment
  (time (euler-005 20))
  )
