(ns caesarhu.example.queens
  (:require [caesarhu.kuafu.domain :as d]
            [caesarhu.kuafu.sat.model :as m]
            [caesarhu.kuafu.sat.solver :as s]
            [caesarhu.kuafu.sat.linear-expr :as l]
            [clojure.string :as str]))

(defn queens-solver
  [n]
  (let [model (m/sat-model)
        solver (s/sat-solver)
        domain (d/domain 0 (dec n))
        queens (vec (repeatedly n #(m/int-var model domain)))
        diag1 (map #(.. (l/new-builder) (add (queens %)) (add %) build) (range n))
        diag2 (map #(.. (l/new-builder) (add (queens %)) (add (- %)) build) (range n))
        show-row (fn [x]
                   (str/join " " (map #(if (= x %) "Q" "_") (range n))))
        show (fn [qv]
               (map show-row qv))]
    (m/add-all-different model queens)
    (m/add-all-different model diag1)
    (m/add-all-different model diag2)
    (s/set-all-solutions solver true)
    (reset! s/*solutions* (list))
    (s/solve solver model (s/callback queens))
    (map show @s/*solutions*)))

(comment
  (queens-solver 8)
  )