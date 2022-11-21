(ns caesarhu.example.euler-004
  (:require [caesarhu.kuafu.sat :as sat]))

(defn euler-004
  []
  (let [model (sat/cp-model)
        a (sat/int-var model 1 9 "a")
        b (sat/int-var model 0 9 "b")
        c (sat/int-var model 0 9 "c")
        p (sat/int-var model 900000 999999 "p")
        x (sat/int-var model 100 999 "x")
        y (sat/int-var model 100 999 "y")
        solver (sat/cp-solver)]
    (sat/add-equality model p (sat/weighted-sum [a b c c b a] [100000 10000 1000 100 10 1]))
    (sat/maximize model p)
    (sat/add-multiplication-equality model p x y)
    (sat/solve solver model)
    (sat/value solver p)))

(comment
  (time (euler-004))
  )