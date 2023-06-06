(ns caesarhu.kuafu.sat
  (:require [caesarhu.kuafu.ortools :refer [ortools-loader or-call]]
            [clojure.walk :as w])
  (:import [com.google.ortools.util Domain]
           [com.google.ortools.sat LinearExpr CpModel LinearArgument CpSolver CpSolverSolutionCallback]))

@ortools-loader

(defn rand-letter-str
  [^long len]
  (transduce
   (map (fn [_]
          (let [rnd (rand-int 52)]
            (char (+ (mod rnd 26)
                     (int (if (< rnd 26) \a \A)))))))
   str
   "" (range len)))

(defn rand-name
  []
  (rand-letter-str 10))

; common atom for solutions
(defonce ^:dynamic *solutions* (atom []))

; Domain class

(defn domain
  ([]
   (Domain.))
  ([value]
   (Domain. value))
  ([left right]
   (Domain. left right)))

; CpModel

(defn cp-model
  []
  (CpModel.))

(defn bool-var
  ([model name]
   (.newBoolVar model name))
  ([model]
   (.newBoolVar model (rand-name))))

(defmulti int-var (fn [_ & xs] (->> (map class xs) vec)))

(defmethod int-var [Long] [model value] (.newConstant model value))
(defmethod int-var [Domain String] [model d name] (.newIntVarFromDomain model d name))
(defmethod int-var [Domain] [model d] (.newIntVarFromDomain model d (rand-name)))
(defmethod int-var [Long Long String] [model lb ub name] (.newIntVar model lb ub name))
(defmethod int-var [Long Long] [model lb ub] (.newIntVar model lb ub (rand-name)))

; CpSolver

(defn cp-solver
  []
  (CpSolver.))

(defn set-all-solutions
  [solver bool]
  (.. solver (getParameters) (setEnumerateAllSolutions bool)))

(defn callback
  ([thing solutions]
   (proxy [CpSolverSolutionCallback] []
     (onSolutionCallback []
       (let [solution (cond
                        (coll? thing) (w/prewalk (fn [x]
                                                   (if (instance? LinearArgument x)
                                                     (.value this x)
                                                     x))
                                                 thing)
                        (instance? LinearArgument thing) (.value this thing)
                        :else thing)]
         (swap! solutions conj solution)))))
  ([thing]
   (callback thing *solutions*)))

(defn solve-all
  ([solver model thing]
   (let [solutions (atom [])]
     (set-all-solutions solver true)
     {:status (or-call solver solve model (callback thing solutions))
      :solutions @solutions}))
  ([model thing]
   (solve-all (cp-solver) model thing)))
