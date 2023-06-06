(ns caesarhu.examples.euler-001
  (:require [caesarhu.kuafu.ortools :refer [or-call]]
            [caesarhu.kuafu.sat :as sat])
  (:import [com.google.ortools.util Domain]))

(defn euler-001
  [n]
  (let [model (sat/cp-model)
        x (sat/int-var model 1 (dec n) "x")
        y (sat/int-var model (or-call Domain fromValues [3 5]) "y")
        solver (sat/cp-solver)]
    (or-call model addModuloEquality (sat/int-var model 0) x y)
    (->> (sat/solve-all solver model x)
         :solutions
         set
         (apply +))))

(comment
  (time (euler-001 1000))
  )
