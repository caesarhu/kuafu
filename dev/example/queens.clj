(ns caesarhu.example.queens
  (:require [caesarhu.kuafu.sat :as sat]
            [clojure.string :as str]))

(defn queens-solver
  [n]
  (let [model (sat/cp-model)
        solver (sat/cp-solver)
        domain (sat/domain 0 (dec n))
        queens (vec (repeatedly n #(sat/int-var model domain)))
        diag1 (map #(-> (sat/new-builder) (sat/add (queens %)) (sat/add %) sat/build) (range n))
        diag2 (map #(-> (sat/new-builder) (sat/add (queens %)) (sat/add (- %)) sat/build) (range n))
        make-row (fn [x]
                   (str/join " " (map #(if (= x %) "Q" "_") (range n))))
        make-table (fn [qv]
               (map make-row qv))]
    (sat/add-all-different model queens)
    (sat/add-all-different model diag1)
    (sat/add-all-different model diag2)
    (sat/set-all-solutions solver true)
    (reset! sat/*solutions* (list))
    (sat/solve solver model (sat/callback queens))
    (map make-table @sat/*solutions*)))

(comment
  (queens-solver 8)
  )