(ns caesarhu.example.euler-009
  (:require [caesarhu.kuafu.domain :as d]
            [caesarhu.kuafu.sat.model :as m]
            [caesarhu.kuafu.sat.solver :as s]
            [caesarhu.kuafu.sat.linear-expr :as l]))

(defn euler-009
  []
  (let [model (m/sat-model)
        solver (s/sat-solver)
        d1 (d/domain 1 1000)
        d2 (d/domain 1 1000000)
        [a b c] (repeatedly #(m/int-var model d1))
        [a2 b2 c2] (repeatedly #(m/int-var model d2))]
    (reset! s/*solutions* (list))
    (m/add-less-than model a b)
    (m/add-less-than model b c)
    (m/add-multiplication-equality model a2 [a a])
    (m/add-multiplication-equality model b2 [b b])
    (m/add-multiplication-equality model c2 [c c])
    (m/add-equality model c2 (l/sum [a2 b2]))
    (m/add-equality model (m/int-var model 1000) (l/sum [a b c]))
    (s/solve solver model (s/callback [a b c]))
    (->> (first @s/*solutions*)
         (apply *))))

(comment
  (time (euler-009))
  )
