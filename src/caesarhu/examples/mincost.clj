(ns caesarhu.examples.mincost
  (:require [caesarhu.kuafu.graph :as g]))

(defn mincost-test
  []
  (let [start-nodes [0, 0, 1, 1, 1, 2, 2, 3, 4]
        end-nodes [1, 2, 2, 3, 4, 3, 4, 4, 2]
        capacities [15, 8, 20, 4, 10, 15, 4, 20, 5]
        unit-costs [4, 4, 2, 2, 6, 1, 3, 2, 3]
        supplies [20, 0, 0, -5, -15]
        flow (g/min-cost-flow)]
    (doseq [i (range (count start-nodes))]
      (g/add-arc-with-capacity-and-unitcost flow (start-nodes i) (end-nodes i) (capacities i) (unit-costs i)))
    (doseq [i (range (count supplies))]
      (g/set-node-supply flow i (supplies i)))
    (g/solve flow)
    (println "Minimum cost: " (g/get-optimal-cost flow))
    (println)
    (println " Edge   Flow / Capacity  Cost")
    (doseq [i (range (g/get-num-arcs flow))
            :let [cost (* (g/get-flow flow i) (g/get-unitcost flow i))]]
      (println (g/get-tail flow i) " -> "
               (g/get-head flow i) "    "
               (g/get-flow flow i) "  /  " (g/get-capacity flow i)
               "       " cost))))

(comment
  (mincost-test)
  )
