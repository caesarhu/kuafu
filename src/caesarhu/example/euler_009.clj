(ns caesarhu.example.euler-009
  (:require [caesarhu.kuafu.domain :as d]
            [caesarhu.kuafu.sat.model :as m]
            [caesarhu.kuafu.sat.solver :as s]
            [caesarhu.kuafu.sat.linear-expr :as l]))

(defn square
  [model x]
  (let [xx (m/int-var model 1 1000000)]
    (m/add-multiplication-equality model xx [x x])
    xx))

(defn euler-009
  []
  (let [model (m/sat-model)
        solver (s/sat-solver)
        d1 (d/domain 1 1000)
        [a b c] (repeatedly #(m/int-var model d1))
        a2 (square model a)
        b2 (square model b)
        c2 (square model c)]
    (reset! s/*solutions* (list))
    (m/add-less-than model a b)
    (m/add-less-than model b c)
    (m/add-equality model c2 (l/sum [a2 b2]))
    (m/add-equality model (m/int-var model 1000) (l/sum [a b c]))
    (s/solve solver model (s/callback [a b c]))
    (->> (first @s/*solutions*)
         (apply *))))

(comment
  (time (euler-009))
  )
