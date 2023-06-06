(ns caesarhu.examples.euler-009
  (:require [caesarhu.kuafu.ortools :refer [or-call]]
            [caesarhu.kuafu.sat :as sat])
  (:import [com.google.ortools.sat LinearExpr]))

(defn square
  [model x]
  (let [xx (sat/int-var model 1 1000000)]
    (or-call model addMultiplicationEquality xx [x x])
    xx))

(defn euler-009
  []
  (let [model (sat/cp-model)
        solver (sat/cp-solver)
        d1 (sat/domain 1 1000)
        [a b c] (repeatedly #(sat/int-var model d1))
        a2 (square model a)
        b2 (square model b)
        c2 (square model c)]
    (or-call model addLessThan a b)
    (or-call model addLessThan b c)
    (or-call model addEquality c2 (or-call LinearExpr sum [a2 b2]))
    (or-call model addEquality (sat/int-var model 1000) (or-call LinearExpr sum [a b c]))
    (->> (sat/solve-all solver model [a b c])
         :solutions
         first
         (apply *))))

(comment
  (time (euler-009))
  )
