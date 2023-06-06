(ns caesarhu.examples.maxflow
  (:require [caesarhu.kuafu.graph :as g]))

(defn maxflow-test
  []
  (let [start-nodes [0, 0, 0, 1, 1, 2, 2, 3, 3]
        end-nodes [1, 2, 3, 2, 4, 3, 4, 2, 4]
        capacities [20, 30, 10, 40, 30, 10, 20, 5, 20]
        flow-test (g/max-flow)]
    (doseq [i (range (count start-nodes))]
      (g/add-arc-with-capacity flow-test (start-nodes i) (end-nodes i) (capacities i)))
    (g/solve flow-test 0 4)
    (println "Max. flow: " (g/get-optimal-flow flow-test))
    (println "  Arc     Flow / Capacity")
    (doseq [i (range (g/get-num-arcs flow-test))]
      (println (g/get-tail flow-test i) " -> "
               (g/get-head flow-test i) "    "
               (g/get-flow flow-test i) "  /  " (g/get-capacity flow-test i)))))

(comment
  (maxflow-test)
  )
