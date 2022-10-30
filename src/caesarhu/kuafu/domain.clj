(ns caesarhu.kuafu.domain
  (:require [caesarhu.kuafu.ortools :refer [ortools-loader]])
  (:import [com.google.ortools.util Domain]))

@ortools-loader

(defn domain
  ([]
   (Domain.))
  ([^long value]
   (Domain. value))
  ([^long left ^long right]
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
  [^com.google.ortools.util.Domain d1 ^com.google.ortools.util.Domain d2]
  (.additionWith d1 d2))

(defn all-values
  []
  (Domain/allValues))

(defn complement
  [^com.google.ortools.util.Domain d]
  (.complement d))

(defn contains?
  [^com.google.ortools.util.Domain d ^long value]
  (.contains d value))

(defn delete
  [^com.google.ortools.util.Domain d]
  (.delete d))

(defn finalize
  [^com.google.ortools.util.Domain d]
  (.finalize d))

(defn flattened-intervals
  [^com.google.ortools.util.Domain d]
  (.flattenedIntervals d))

(defn intersection-with
  [^com.google.ortools.util.Domain d1 ^com.google.ortools.util.Domain d2]
  (.intersectionWith d1 d2))

(defn is-empty?
  [^com.google.ortools.util.Domain d]
  (.isEmpty d))

(defn max
  [^com.google.ortools.util.Domain d]
  (.max d))

(defn min
  [^com.google.ortools.util.Domain d]
  (.min d))

(defn negation
  [^com.google.ortools.util.Domain d]
  (.negation d))

(defn size
  [^com.google.ortools.util.Domain d]
  (.size d))

(defn union-with
  [^com.google.ortools.util.Domain d1 ^com.google.ortools.util.Domain d2]
  (.unionWith d1 d2))