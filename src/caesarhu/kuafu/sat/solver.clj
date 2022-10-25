(ns caesarhu.kuafu.sat.solver
  (:import [com.google.ortools.sat CpModel CpSolver CpSolverSolutionCallback]))

(defn solver
  []
  (CpSolver.))

(defn stop-search
  [^CpSolver s]
  (.stopSearch s))

(defn objective-value
  [^CpSolver s]
  (.objectiveValue s))

(defn best-objective-bound
  [^CpSolver s]
  (.bestObjectiveBound s))

(defn value
  [^CpSolver s expr]
  (.value s expr))

(defn boolean-value
  [^CpSolver s var]
  (.booleanValue s var))

(defn response
  [^CpSolver s]
  (.response s))

(defn num-branches
  [^CpSolver s]
  (.numBranches s))

(defn num-conflicts
  [^CpSolver s]
  (.numConflicts s))

(defn wall-time
  [^CpSolver s]
  (.wallTime s))

(defn user-time
  [^CpSolver s]
  (.userTime s))

(defn sufficient-assumptions-for-infeasibility
  [^CpSolver s]
  (.sufficientAssumptionsForInfeasibility s))

(defn get-parameters
  [^CpSolver s]
  (.getParameters s))

(defn response-stats
  [^CpSolver s]
  (.responseStats s))

(defn get-solution-info
  [^CpSolver s]
  (.getSolutionInfo s))

(defn solve
  [^CpModel model vars]
  (let [solver (solver)
        status (str (.solve solver model))]
    {:values (map #(value solver %) vars) :status status}))

(defn solutions
  [^CpModel model vars]
  (let [values (atom [])
        cb (proxy [CpSolverSolutionCallback] []
             (onSolutionCallback []
               (swap! values conj (mapv #(value this %) vars))))
        solver (solver)
        _ (.. solver (getParameters) (setEnumerateAllSolutions true))
        status (str (.solve solver model cb))]
    {:count (count @values) :values @values :status status}))