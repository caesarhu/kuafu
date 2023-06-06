(ns caesarhu.kuafu.ortools
  "ortools-loader and or-call"
  (:require [redelay.core :refer [state status stop]])
  (:import [com.google.ortools Loader]))

(def ortools-loader
  "Load native libraries needed for using ortools-java."
  (state (Loader/loadNativeLibraries)))

(defn args-transfer
  "transfer clojure sequence to java array"
  [args]
  (if (sequential? args)
    (if (int? (first args))
      (long-array args)
      (into-array args))
    args))

(defmacro or-call
  "or-call convert clojure sequence to java array for or-tools function call."
  {:clj-kondo/ignore [:unresolved-symbol :type-mismatch]}
  [klass member & args*]
  `(. ~klass ~member ~@(map #(list args-transfer %) args*)))
