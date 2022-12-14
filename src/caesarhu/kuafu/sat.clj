(ns caesarhu.kuafu.sat
  (:require [caesarhu.kuafu.ortools :refer [ortools-loader]]
            [caesarhu.kuafu.util :refer [rand-name]]
            [camel-snake-kebab.core :as csk])
  (:import [com.google.ortools.util Domain]
           [com.google.ortools.sat LinearExpr CpModel LinearArgument CpSolver CpSolverSolutionCallback]))

@ortools-loader

(defonce ^:dynamic *solutions* (atom nil))

; java Domain class

(defn domain
  ([]
   (Domain.))
  ([value]
   (Domain. value))
  ([left right]
   (Domain. left right)))

(defn from-flat-intervals
  [flat-intervals]
  (Domain/fromFlatIntervals flat-intervals))

(defn from-intervals
  [intervals]
  (Domain/fromIntervals intervals))

(defn from-values
  [values]
  (Domain/fromValues (long-array values)))

(defn addition-with
  [d1 d2]
  (.additionWith d1 d2))

(defn all-values
  []
  (Domain/allValues))

(defn complement
  [d]
  (.complement d))

(defn contains?
  [d value]
  (.contains d value))

(defn flattened-intervals
  [d]
  (.flattenedIntervals d))

(defn intersection-with
  [d1 d2]
  (.intersectionWith d1 d2))

(defn is-empty?
  [d]
  (.isEmpty d))

(defn dmax
  [d]
  (.max d))

(defn dmin
  [d]
  (.min d))

(defn negation
  [d]
  (.negation d))

(defn size
  [d]
  (.size d))

(defn union-with
  [d1 d2]
  (.unionWith d1 d2))

; LinearExpr

(defn num-elements
  [expr]
  (.numElements expr))

(defn get-variable-index
  [expr index]
  (.getVariableIndex expr index))

(defn get-coefficient
  [expr index]
  (.getCoefficient expr index))

(defn get-offset
  [expr]
  (.getOffset expr))

(defn constant
  [value]
  (LinearExpr/constant value))

(defn term
  [expr coeff]
  (LinearExpr/term expr coeff))

(defn affine
  [expr coeff offset]
  (LinearExpr/affine expr coeff offset))

(defn sum
  [exprs]
  (LinearExpr/sum (into-array exprs)))

(defn weighted-sum
  [exprs coeffs]
  (LinearExpr/weightedSum (into-array exprs) (long-array coeffs)))

(defn new-builder
  []
  (LinearExpr/newBuilder))

; LinearExprBuilder functions

(defn build
  [builder]
  (.build builder))

(defn add
  [builder expr]
  (.add builder expr))

(defn add-sum
  [builder exprs]
  (.addSum builder (into-array exprs)))

(defn add-term
  [builder expr coeff]
  (.addTerm builder expr coeff))

(defn add-weighted-sum
  [builder exprs coeffs]
  (.addWeightedSum builder exprs coeffs))

; Constraint

(defn only-enforce-if
  [lit]
  (if (sequential? lit)
    (.onlyEnforceIf (into-array lit))
    (.onlyEnforceIf lit)))

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
(defmethod int-var [Long Long String] [model lb ub name]
  (.newIntVar model lb ub name))
(defmethod int-var [Long Long] [model lb ub]
  (.newIntVar model lb ub (rand-name)))

(defn new-fixed-interval
  ([model start size name]
   (.newFixedInterval model start size name))
  ([model start size]
   (.newFixedInterval model start size (rand-name))))

(defn interval-var
  ([model start size end name]
   (.newIntervalVar model start size end name))
  ([model start size end]
   (.newIntervalVar model start size end (rand-name))))

(defn fixed-size-interval-var
  ([model start size name]
   (.newFixedSizeIntervalVar model start size name))
  ([model start size]
   (.newFixedSizeIntervalVar model start size (rand-name))))

(defn new-optional-interval-var
  ([model start size end isPresent name]
   (.newOptionalIntervalVar model start size end isPresent name))
  ([model start size end isPresent]
   (.newOptionalIntervalVar model start size end isPresent (rand-name))))

(defn new-optional-fixed-size-interval-var
  ([model start size isPresent name]
   (.newOptionalFixedSizeIntervalVar model start size isPresent name))
  ([model start size isPresent]
   (.newOptionalFixedSizeIntervalVar model start size isPresent (rand-name))))

(defn new-optional-fixed-interval
  ([model start size isPresent name]
   (.newOptionalFixedInterval model start size isPresent name))
  ([model start size isPresent]
   (.newOptionalFixedInterval model start size isPresent (rand-name))))

(defn true-literal
  [model]
  (.trueLiteral model))

(defn false-literal
  [model]
  (.falseLiteral model))

(defn add-bool-or
  [model literals]
  (.addBoolOr model (into-array literals)))

(defn add-at-least-one
  [model literals]
  (.addAtLeastOne model (into-array literals)))

(defn add-at-most-one
  [model literals]
  (.addAtMostOne model (into-array literals)))

(defn add-exactly-one
  [model literals]
  (.addExactlyOne model (into-array literals)))

(defn add-bool-and
  [model literals]
  (.addBoolAnd model (into-array literals)))

(defn add-bool-xor
  [model literals]
  (.addBoolXor model (into-array literals)))

(defn add-implication
  [model a b]
  (.addImplication model a b))

(defn add-linear-expression-in-domain
  [model expr d]
  (.addLinearExpressionInDomain model expr d))

(defn add-linear-constraint
  [model expr lb ub]
  (.addLinearConstraint model expr lb ub))

(defn add-equality
  [model expr value]
  (.addEquality model expr value))

(defn add-less-or-equal
  [model expr value]
  (.addLessOrEqual model expr value))

(defn add-less-than
  [model expr value]
  (.addLessThan model expr value))

(defn add-greater-or-equal
  [model expr value]
  (.addGreaterOrEqual model expr value))

(defn add-greater-than
  [model expr value]
  (.addGreaterThan model expr value))

(defn add-different
  [model expr value]
  (.addDifferent model expr value))

(defn add-all-different
  [model expressions]
  (.addAllDifferent model (into-array expressions)))

(defn add-element
  [model index values target]
  (.addElement model index values target))

(defn add-circuit
  [model]
  (.addCircuit model))

(defn add-multiple-circuit
  [model]
  (.addMultipleCircuit model))

(defn add-allowed-assignments
  [model variables]
  (.addAllowedAssignments model (into-array variables)))

(defn add-forbidden-assignments
  [model variables]
  (.addForbiddenAssignments model (into-array variables)))

(defn add-automaton
  [model transitionVariables startingState finalStates]
  (.addAutomaton model (into-array transitionVariables) startingState (long-array finalStates)))

(defn add-inverse
  [model variables inverseVariables]
  (.addInverse model (into-array variables) (into-array inverseVariables)))

(defn add-reservoir-constraint
  [model minLevel maxLevel]
  (.addReservoirConstraint model minLevel maxLevel))

(defn add-map-domain
  [model var booleans offset]
  (.addMapDomain model var (into-array booleans) offset))

(defn add-min-equality
  [model target exprs]
  (.addMinEquality model target (into-array exprs)))

(defn add-max-equality
  [model target exprs]
  (.addMaxEquality model target (into-array exprs)))

(defn add-division-equality
  [model target exprs num denom]
  (.addDivisionEquality model target exprs num denom))

(defn add-abs-equality
  [model target expr]
  (.addAbsEquality model target expr))

(defn add-modulo-equality
  [model target var mod]
  (.addModuloEquality  model target var mod))

(defn add-multiplication-equality
  ([model target exprs]
   (.addMultiplicationEquality model target (into-array exprs)))
  ([model target left right]
   (.addMultiplicationEquality model target left right)))

(defn add-no-overlap
  [model intervalVars]
  (.addNoOverlap model (into-array intervalVars)))

(defn add-no-overlap-2d
  [model]
  (.addNoOverlap2D model))

(defn add-cumulative
  [model capacity]
  (.addCumulative model capacity))

(defn add-hint
  [model var value]
  (.addHint model var value))

(defn clear-hints
  [model]
  (.clearHints model))

(defn add-assumption
  [model lit]
  (.addAssumption model lit))

(defn add-assumptions
  [model literals]
  (.addAssumption model (into-array literals)))

(defn clear-assumptions
  [model]
  (.clearAssumptions model))

(defn minimize
  [model expr]
  (.minimize model expr))

(defn maximize
  [model expr]
  (.maximize model expr))

(defn clear-objective
  [model]
  (.clearObjective model))

(defn has-objective
  [model]
  (.hasObjective model))

(defn add-decision-strategy
  [model variables varStr domStr]
  (.addDecisionStrategy model variables varStr domStr))

(defn model-stats
  [model]
  (.modelStats model))

(defn validate
  [model]
  (.validate model))

(defn export-to-file
  [model file]
  (.exportToFile model file))

(defn model
  [model]
  (.model model))

(defn negated
  [model index]
  (.negated model index))

(defn get-builder
  [model]
  (.getBuilder model))

; CpSolver

(defn cp-solver
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
