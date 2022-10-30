(ns caesarhu.kuafu.sat.linear-expr
  (:require [caesarhu.kuafu.ortools :refer [ortools-loader]])
  (:import [com.google.ortools.sat LinearExpr LinearArgument]))

@ortools-loader

(defn num-elements
  [^LinearExpr expr]
  (.numElements expr))

(defn get-variable-index
  [^LinearExpr expr index]
  (.getVariableIndex expr index))

(defn get-coefficient
  [^LinearExpr expr index]
  (.getCoefficient expr index))

(defn get-offset
  [^LinearExpr expr]
  (.getOffset expr))

(defn constant
  [^Long value]
  (LinearExpr/constant value))

(defn term
  [^LinearArgument expr ^Long coeff]
  (LinearExpr/term expr coeff))

(defn affine
  [^LinearArgument expr ^Long coeff ^Long offset]
  (LinearExpr/affine expr coeff offset))

(defn sum
  [exprs]
  (LinearExpr/sum (into-array exprs)))

(defn weighted-sum
  [exprs coeffs]
  (LinearExpr/weightedSum (into-array exprs) (long-array coeffs)))

