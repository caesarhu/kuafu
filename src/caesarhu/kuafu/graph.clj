(ns caesarhu.kuafu.graph
  (:require [caesarhu.kuafu.ortools :refer [ortools-loader]])
  (:import [com.google.ortools.graph MaxFlow MinCostFlow]))

@ortools-loader

; Class MaxFlow wrapper

(defn max-flow
  []
  (MaxFlow.))

(defn add-arc-with-capacity
  [max-flow tail head capacity]
  (.addArcWithCapacity max-flow tail head capacity))

(defn get-capacity
  [max-flow arc]
  (.getCapacity max-flow arc))

(defn get-flow
  [max-flow arc]
  (.getFlow max-flow arc))

(defn get-head
  [max-flow arc]
  (.getHead max-flow arc))

(defn get-tail
  [max-flow arc]
  (.getTail max-flow arc))

(defn get-num-arcs
  [max-flow]
  (.getNumArcs max-flow))

(defn get-num-nodes
  [max-flow]
  (.getNumNodes max-flow))

(defn get-optimal-flow
  [max-flow]
  (.getOptimalFlow max-flow))

(defn set-arc-capacity
  [max-flow arc capacity]
  (.setArcCapacity max-flow arc capacity))

; Class MinCostFlow wrapper

(defn min-cost-flow
  []
  (MinCostFlow.))

(defn add-arc-with-capacity-and-unitcost
  [min-cost-flow tail head capacity unitcost]
  (.addArcWithCapacityAndUnitCost min-cost-flow tail head capacity unitcost))

(defn get-maximum-flow
  [min-cost-flow]
  (.getMaximumFlow min-cost-flow))

(defn get-optimal-cost
  [min-cost-flow]
  (.getOptimalCost min-cost-flow))

(defn get-unitcost
  [min-cost-flow arc]
  (.getUnitCost min-cost-flow arc))

(defn get-supply
  [min-cost-flow node]
  (.getSupply min-cost-flow node))

(defn set-node-supply
  [min-cost-flow node supply]
  (.setNodeSupply min-cost-flow node supply))

(defn solve-max-flow-with-min-cost
  [min-cost-flow]
  (.solveMaxFlowWithMinCost min-cost-flow))

; solve function for MaxFlow and MinCostFlow

(defn solve
  ([max-flow source sink]
   (.solve max-flow source sink))
  ([min-cost-flow]
   (.solve min-cost-flow)))