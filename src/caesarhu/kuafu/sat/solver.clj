(ns caesarhu.kuafu.sat.solver
  (:require [caesarhu.kuafu.ortools :refer [ortools-loader]])
  (:import [com.google.ortools.sat CpModel CpSolver CpSolverSolutionCallback]))

@ortools-loader

(defn sat-solver
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
  [s expr]
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
  ([^CpSolver s ^CpModel model]
   (.solve s model))
  ([^CpSolver s ^CpModel model cb]
   (.solve s model cb)))

(defn set-all-solutions
  [^CpSolver s bool]
  (.. s (getParameters) (setEnumerateAllSolutions bool)))

(defn callback
  [values thing]
  (let [cb (proxy [CpSolverSolutionCallback] []
             (onSolutionCallback []
               (let [solution (cond
                                (sequential? thing) (mapv #(value this %) thing)
                                (fn? thing) (thing this)
                                :else (throw (Exception. "solution-callback arguments fail!")))]
                 (swap! values conj solution))))]
    cb))
