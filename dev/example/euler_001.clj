(ns caesarhu.example.euler-001
  (:require [caesarhu.kuafu.sat :as sat]))

(defn euler-001
  [n]
  (let [model (sat/cp-model)
        x (sat/int-var model 1 (dec n) "x")
        y (sat/int-var model (sat/from-values [3 5]) "y")
        solver (sat/cp-solver)]
    (reset! sat/*solutions* (list))
    (sat/add-modulo-equality model (sat/int-var model 0) x y)
    (sat/set-all-solutions solver true)
    (sat/solve solver model (sat/callback x))
    (->> @sat/*solutions*
         set
         (reduce +))))

(comment
  (time (euler-001 1000))
  )