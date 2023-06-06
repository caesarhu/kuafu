(ns caesarhu.examples.queens
  (:require [caesarhu.kuafu.ortools :refer [or-call]]
            [caesarhu.kuafu.sat :as sat]
            [clojure.string :as str])
  (:import [com.google.ortools.sat LinearExpr]))

(defn queens-solver
  [n]
  (let [model (sat/cp-model)
        solver (sat/cp-solver)
        domain (sat/domain 0 (dec n))
        queens (vec (repeatedly n #(sat/int-var model domain)))
        diag1 (map #(-> (or-call LinearExpr newBuilder)
                        (or-call add (queens %))
                        (or-call add %)
                        (or-call build)) (range n))
        diag2 (map #(-> (or-call LinearExpr newBuilder)
                        (or-call add (queens %))
                        (or-call add (- %))
                        (or-call build)) (range n))
        make-row (fn [x]
                   (str/join " " (map #(if (= x %) "Q" "_") (range n))))
        make-table (fn [qv]
                     (map make-row qv))]
    ;(sat/add-all-different model queens)
    (or-call model addAllDifferent queens)
    ;(sat/add-all-different model diag1)
    (or-call model addAllDifferent diag1)
    ;(sat/add-all-different model diag2)
    (or-call model addAllDifferent diag2)
    (->> (sat/solve-all solver model queens)
         :solutions
         (map make-table))))

(comment
  (queens-solver 8)
  )
