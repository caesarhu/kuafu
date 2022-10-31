(ns caesarhu.kuafu.domain
  (:require [caesarhu.kuafu.ortools :refer [ortools-loader]])
  (:import [com.google.ortools.util Domain]))

@ortools-loader

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

(defn delete
  [d]
  (.delete d))

(defn finalize
  [d]
  (.finalize d))

(defn flattened-intervals
  [d]
  (.flattenedIntervals d))

(defn intersection-with
  [d1 d2]
  (.intersectionWith d1 d2))

(defn is-empty?
  [d]
  (.isEmpty d))

(defn max
  [d]
  (.max d))

(defn min
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