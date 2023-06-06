(ns caesarhu.kuafu.ortools
  (:require [redelay.core :refer [state status stop]])
  (:import [com.google.ortools Loader]))

(def ortools-loader
  "Load native libraries needed for using ortools-java."
  (state (Loader/loadNativeLibraries)))

(defn arg-transfer
  [arg]
  (if (sequential? arg)
    (if (int? (first arg))
      (long-array arg)
      (into-array arg))
    arg))

(defmacro or-call
  {:clj-kondo/ignore [:unresolved-symbol :type-mismatch]}
  [klass member & args*]
  `(. ~klass ~member ~@(map #(list arg-transfer %) args*)))
