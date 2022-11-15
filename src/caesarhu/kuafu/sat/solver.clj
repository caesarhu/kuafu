(ns caesarhu.kuafu.sat.solver
  (:require [caesarhu.kuafu.ortools :refer [ortools-loader]])
  (:import [com.google.ortools.sat LinearArgument CpSolver CpSolverSolutionCallback]))

@ortools-loader

(defonce ^:dynamic *solutions* (atom (list)))

(defn sat-solver
  []
  (CpSolver.))

(defn stop-search
  [solver]
  (.stopSearch solver))

(defn objective-value
  [solver]
  (.objectiveValue solver))

(defn best-objective-bound
  [solver]
  (.bestObjectiveBound solver))

(defn value
  [solver expr]
  (.value solver expr))

(defn boolean-value
  [solver var]
  (.booleanValue solver var))

(defn response
  [solver]
  (.response solver))

(defn num-branches
  [solver]
  (.numBranches solver))

(defn num-conflicts
  [solver]
  (.numConflicts solver))

(defn wall-time
  [solver]
  (.wallTime solver))

(defn user-time
  [solver]
  (.userTime solver))

(defn sufficient-assumptions-for-infeasibility
  [solver]
  (.sufficientAssumptionsForInfeasibility solver))

(defn get-parameters
  [solver]
  (.getParameters solver))

(defn response-stats
  [solver]
  (.responseStats solver))

(defn get-solution-info
  [solver]
  (.getSolutionInfo solver))

(defn solve
  ([solver model]
   (.solve solver model))
  ([solver model cb]
   (.solve solver model cb)))

(defn set-all-solutions
  [solver bool]
  (.. solver (getParameters) (setEnumerateAllSolutions bool)))

(defn callback
  [thing]
  (proxy [CpSolverSolutionCallback] []
    (onSolutionCallback []
      (let [solution (cond
                       (sequential? thing) (mapv #(value this %) thing)
                       (fn? thing) (thing this)
                       (instance? LinearArgument thing) (value this thing)
                       :else (throw (Exception. "solution-callback arguments fail!")))]
        (swap! *solutions* conj solution)))))
