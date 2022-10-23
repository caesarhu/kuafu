(ns caesarhu.kuafu.sat.solver
  (:import [com.google.ortools.sat CpModel CpSolver CpSolverSolutionCallback]))

(defn solver
  []
  (CpSolver.))

(defn solve
  [^CpModel model vars]
  (let [solver (solver)
        status (str (.solve solver model))]
    {:values (map #(.value solver %) vars) :status status}))

(defn solutions
  [^CpModel model vars]
  (let [counter (atom 0)
        values (atom [])
        cb (proxy [CpSolverSolutionCallback] []
             (onSolutionCallback []
               (swap! values conj (mapv #(.value this %) vars))
               (swap! counter inc)))
        solver (solver)
        _ (.. solver (getParameters) (setEnumerateAllSolutions true))
        status (str (.solve solver model cb))]
    {:count @counter :values @values :status status}))