(ns caesarhu.kuafu.sat.linear-expr
  (:require [caesarhu.kuafu.ortools :refer [ortools-loader]])
  (:import [com.google.ortools.sat LinearExpr LinearArgument]))

@ortools-loader

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