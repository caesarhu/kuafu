(ns caesarhu.kuafu.sat
  "ortools sat主要類別如Domain、CpModel、CpSolver及LinearExpr等，相關函數太多了，
所以不一一轉換，直接以ortools/or-all取用相關函數，另將最常用的幾個函數轉換以求方便。"
  (:require [caesarhu.kuafu.ortools :refer [ortools-loader or-call]]
            [clojure.walk :as w])
  (:import [com.google.ortools.util Domain]
           [com.google.ortools.sat LinearExpr CpModel LinearArgument CpSolver CpSolverSolutionCallback]))

@ortools-loader

; common atom for solutions
(defonce ^:dynamic *solutions* (atom []))

; Domain class

(defn domain
  "ortools Domain constructor."
  ([]
   (Domain.))
  ([value]
   (Domain. value))
  ([left right]
   (Domain. left right)))

; CpModel

(defn cp-model
  "ortools cpModel constructor."
  []
  (CpModel.))

(defn bool-var
  "ortools cpModel BoolVar"
  ([model name]
   (.newBoolVar model name))
  ([model]
   (.newBoolVar model (str (gensym)))))

(defmulti int-var
  "ortools cpModel IntVar"
  (fn [_ & xs] (->> (map class xs) vec)))

(defmethod int-var [Long] [model value] (.newConstant model value))
(defmethod int-var [Domain String] [model d name] (.newIntVarFromDomain model d name))
(defmethod int-var [Domain] [model d] (.newIntVarFromDomain model d (str (gensym))))
(defmethod int-var [Long Long String] [model lb ub name] (.newIntVar model lb ub name))
(defmethod int-var [Long Long] [model lb ub] (.newIntVar model lb ub (str (gensym))))

; CpSolver

(defn cp-solver
  "ortools cpSolver constructor."
  []
  (CpSolver.))

(defn set-all-solutions
  "set cpSolver setEnumerateAllSolutions field."
  [solver bool]
  (.. solver (getParameters) (setEnumerateAllSolutions bool)))

(defn callback
  "Simple CpSolverSolutionCallback for cpSolver."
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
  "Simple solve all solutions function."
  ([solver model thing]
   (let [solutions (atom [])]
     (set-all-solutions solver true)
     {:status (or-call solver solve model (callback thing solutions))
      :solutions @solutions}))
  ([model thing]
   (solve-all (cp-solver) model thing)))
