(ns caesarhu.example.euler-001
  (:require [caesarhu.kuafu.domain :as d]
            [caesarhu.kuafu.sat.model :as m]
            [caesarhu.kuafu.sat.solver :as s]))

(defn euler-001
  [n]
  (let [model (m/sat-model)
        x (m/int-var model 1 (dec n) "x")
        y (m/int-var model (d/from-values [3 5]) "y")
        solver (s/sat-solver)]
    (reset! s/*solutions* (list))
    (m/add-modulo-equality model (m/int-var model 0) x y)
    (s/set-all-solutions solver true)
    (s/solve solver model (s/callback x))
    (->> @s/*solutions*
         set
         (reduce +))))

(comment
  (time (euler-001 1000))
  )