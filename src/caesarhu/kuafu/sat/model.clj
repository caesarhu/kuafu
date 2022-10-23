(ns caesarhu.kuafu.sat.model
  (:require [caesarhu.kuafu.util :refer [rand-name]])
  (:import [com.google.ortools.sat CpModel Literal]
           [com.google.ortools.util Domain]))

(defn model
  []
  (CpModel.))

(defn bool-var
  ([^CpModel model ^java.lang.String name]
   (.newBoolVar model name))
  ([^CpModel model]
   (.newBoolVar model (rand-name))))

(defmulti int-var (fn [_ & xs] (->> (map class xs) vec)))

(defmethod int-var [Long] [^CpModel model value] (.newConstant model value))
(defmethod int-var [Domain String] [^CpModel model d name] (.newIntVarFromDomain model d name))
(defmethod int-var [Domain] [^CpModel model d] (.newIntVarFromDomain model d (rand-name)))
(defmethod int-var [Long Long String] [^CpModel model lb ub name]
  (.newIntVar model lb ub name))
(defmethod int-var [Long Long] [^CpModel model lb ub]
  (.newIntVar model lb ub (rand-name)))

(defn true-literal
  [^CpModel model]
  (.trueLiteral model))

(defn false-literal
  [^CpModel model]
  (.falseLiteral model))

(defn add-bool-or
  [^CpModel model literals]
  (.addBoolOr model (into-array literals)))

(defn add-at-least-one
  [^CpModel model literals]
  (.addAtLeastOne model (into-array literals)))

(defn add-at-most-one
  [^CpModel model literals]
  (.addAtMostOne model (into-array literals)))

(defn add-exactly-one
  [^CpModel model literals]
  (.addExactlyOne model (into-array literals)))

(defn add-bool-and
  [^CpModel model literals]
  (.addBoolAnd model (into-array literals)))

(defn add-bool-xor
  [^CpModel model literals]
  (.addBoolXor model (into-array literals)))

(defn add-implication
  [^CpModel model ^Literal a ^Literal b]
  (.addImplication model a b))

(defn add-linear-expression-in-domain
  [^CpModel model expr d]
  (.addLinearExpressionInDomain model expr d))

(defn add-linear-constraint
  [^CpModel model expr lb ub]
  (.addLinearConstraint model expr lb ub))

(defn add-equality
  [^CpModel model expr value]
  (.addEquality model expr value))

(defn add-less-or-equal
  [^CpModel model expr value]
  (.addLessOrEqual model expr value))

(defn add-less-than
  [^CpModel model expr value]
  (.addLessThan model expr value))

(defn add-greater-or-equal
  [^CpModel model expr value]
  (.addGreaterOrEqual model expr value))

(defn add-greater-than
  [^CpModel model expr value]
  (.addGreaterThan model expr value))

(defn add-different
  [^CpModel model expr value]
  (.addDifferent model expr value))

(defn add-all-different
  [^CpModel model expressions]
  (.addAllDifferent model (into-array expressions)))

(defn add-element
  [^CpModel model index values target]
  (.addElement model index values target))

(defn add-circuit
  [^CpModel model]
  (.addCircuit model))

(defn add-multiple-circuit
  [^CpModel model]
  (.addMultipleCircuit model))

(defn add-allowed-assignments
  [^CpModel model variables]
  (.addAllowedAssignments model (into-array variables)))

(defn add-forbidden-assignments
  [^CpModel model variables]
  (.addForbiddenAssignments model (into-array variables)))

(defn add-automaton
  [^CpModel model transitionVariables startingState finalStates]
  (.addAutomaton model (into-array transitionVariables) startingState (long-array finalStates)))

(defn add-inverse
  [^CpModel model variables inverseVariables]
  (.addInverse model (into-array variables) (into-array inverseVariables)))

(defn addReservoirConstraint
  [^CpModel model minLevel maxLevel]
  (.addReservoirConstraint model minLevel maxLevel))

(defn add-map-domain
  [^CpModel model var booleans offset]
  (.addMapDomain model var (into-array booleans) offset))

(defn addMinEquality
  [^CpModel model target exprs]
  (.addMinEquality model target (into-array exprs)))