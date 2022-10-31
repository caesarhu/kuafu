(ns caesarhu.example.euler-004
  (:require [caesarhu.kuafu.domain :as d]
            [caesarhu.kuafu.sat.model :as m]
            [caesarhu.kuafu.sat.solver :as s]
            [caesarhu.kuafu.sat.linear-expr :as l]))

(defn palindrome
  []
  (let [model (m/sat-model)
        a (m/int-var model 1 9 "a")
        b (m/int-var model 0 9 "b")
        c (m/int-var model 0 9 "c")
        p (m/int-var model 900000 999999 "p")
        x (m/int-var model 100 999 "x")
        y (m/int-var model 100 999 "y")
        values (atom [])
        solver (s/sat-solver)]
    (m/add-equality model p (l/weighted-sum [a b c c b a] [100000 10000 1000 100 10 1]))
    (m/maximize model p)
    (m/add-multiplication-equality model p x y)
    (s/solve solver model (s/callback values [p]))
    (tap> (s/get-solution-info solver))
    @values))

(comment
  (time (palindrome))
  )