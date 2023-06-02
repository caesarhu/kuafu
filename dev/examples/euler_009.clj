(ns euler-009
  (:require [caesarhu.kuafu.sat :as sat]))

(defn square
  [model x]
  (let [xx (sat/int-var model 1 1000000)]
    (sat/add-multiplication-equality model xx [x x])
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
    (reset! sat/*solutions* (list))
    (sat/add-less-than model a b)
    (sat/add-less-than model b c)
    (sat/add-equality model c2 (sat/sum [a2 b2]))
    (sat/add-equality model (sat/int-var model 1000) (sat/sum [a b c]))
    (sat/solve solver model (sat/callback [a b c]))
    (->> (first @sat/*solutions*)
         (apply *))))

(comment
  (time (euler-009))
  )
