(ns caesarhu.examples.euler-004
  (:require [caesarhu.kuafu.ortools :refer [or-call]]
            [caesarhu.kuafu.sat :as sat])
  (:import [com.google.ortools.sat LinearExpr]))

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
    (or-call model addEquality p (or-call LinearExpr weightedSum [a b c c b a] [100000 10000 1000 100 10 1]))
    (or-call model maximize p)
    (or-call model addMultiplicationEquality p x y)
    (or-call solver solve model)
    (.value solver p)))

(comment
  (time (euler-004))
  )
